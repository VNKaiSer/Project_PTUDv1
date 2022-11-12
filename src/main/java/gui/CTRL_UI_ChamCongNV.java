package gui;

import bus.BUS_ChamCongNhanVien;
import bus.BUS_NhanVien;
import dto.DTO_BCCNhanVien;
import dto.DTO_NhanVien;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CTRL_UI_ChamCongNV implements Initializable {

    @FXML
    private Button btnLuu;

    @FXML
    private TableColumn<?, ?> ghiChuCol;

    @FXML
    private TableColumn<?, ?> hienDienCol;

    @FXML
    private TableColumn<?, ?> maNhanVienCol;

    @FXML
    private TableColumn<?, ?> ngayCCCol;

    @FXML
    private TableView<DTO_BCCNhanVien> tblBCCCN;

    @FXML
    private TableColumn<?, ?> tenCol;

    @FXML
    private DatePicker txtNGayChamCong;

    @FXML
    private TextField txtTimCongNhan;

    private BUS_NhanVien bus_nhanVien;

    private boolean nowCC = true;

    private ArrayList<DTO_BCCNhanVien> dsBCCDaCham;
    private ArrayList<DTO_BCCNhanVien> dsBCCHienTai;

    private Dialog dialog;

    private BUS_ChamCongNhanVien bus_chamCongNhanVien;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // khởi tạo các bus và các danh sách
        bus_nhanVien = new BUS_NhanVien();
        bus_chamCongNhanVien = new BUS_ChamCongNhanVien();
        dsBCCHienTai = new ArrayList<>();
        dsBCCDaCham = new ArrayList<>();

        LocalDate ngayHienTai = Instant.ofEpochMilli(new Date().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        txtNGayChamCong.setValue(ngayHienTai);

        maNhanVienCol.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        tenCol.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        ngayCCCol.setCellValueFactory(new PropertyValueFactory<>("ngayChamCong"));
        hienDienCol.setCellValueFactory(new PropertyValueFactory<>("hienDien"));
        ghiChuCol.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        loadDanhSachChamCongHomNay();

        // băt sự kiện trên các componet
        txtNGayChamCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // ngày chấm công lơn hơn ngày hiện tại
                Date ngayCC = Date.from(txtNGayChamCong.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                if (df.format(ngayCC).equals(df.format(new Date()))){
                    // đã chấm cho hôm nay rồi
                    if (!nowCC){
                        loadDanhSachChamCongDaCham();
                        return;
                    }
                    nowCC = true;
                    tblBCCCN.setDisable(false);
                    loadDanhSachChamCongHomNay();
                    return;
                }
                if (ngayCC.after(new Date())) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Ngày chấm công phải bé hơn ngày hiện tại");
                    Optional<ButtonType> a = er.showAndWait();
                    txtNGayChamCong.setValue(ngayHienTai);
                }
                else if (ngayCC.before(new Date())){
                    loadDanhSachChamCongDaCham();
                    tblBCCCN.setDisable(true);
                }


            }
        });

        tblBCCCN.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int idx = tblBCCCN.getSelectionModel().getSelectedIndex();
                if (idx == -1)
                    return;

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Dialog_ChamCongNhanVien.fxml"));
                DialogPane addDialog = null;
                try {
                    addDialog = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dialog = new Dialog<>();
                dialog.setDialogPane(addDialog);
                CTRL_Dialog_CCNV ctrl_dialog_ccnv = fxmlLoader.getController();

                DTO_BCCNhanVien selectCN = tblBCCCN.getSelectionModel().getSelectedItem();
                ctrl_dialog_ccnv.setData(selectCN.getHienDien(), selectCN.getGhiChu());
                Optional clickedButton = dialog.showAndWait();

                if (ctrl_dialog_ccnv.getCheckLuu()) {
                    selectCN.setGhiChu(ctrl_dialog_ccnv.getGhiChu());
                    selectCN.setHienDien(ctrl_dialog_ccnv.getTrangThai());
                    tblBCCCN.getItems().set(idx, selectCN);
                }
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (nowCC){
                    insertToDataBase();
                    nowCC = false;
                    tblBCCCN.setDisable(true);

                }
            }
        });


    }

    private void loadDanhSachChamCongDaCham() {
        tblBCCCN.getItems().clear();
        tblBCCCN.setItems(FXCollections.observableArrayList(bus_chamCongNhanVien.getDSChamCongTheoNgay(txtNGayChamCong.getValue().toString())));
    }

    private void loadDanhSachChamCongHomNay() {
        dsBCCHienTai = new ArrayList<>();
        ArrayList<DTO_NhanVien> dsNV = bus_nhanVien.getAllDSNhanVien();
        int numNhanVien = dsNV.size();
        for (int i = 0; i < numNhanVien; i++) {
            String maBCC = taoMaBCC(dsNV.get(i).getMaNhanVien());
            dsBCCHienTai.add(new DTO_BCCNhanVien(dsNV.get(i), 1, new Date(), maBCC, ""));
        }
        tblBCCCN.setItems(FXCollections.observableArrayList(dsBCCHienTai));

    }

    void closeDialog() {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

    void insertToDataBase(){
        try {
            bus_chamCongNhanVien.insertBCCNVToDatabase(dsBCCHienTai);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String taoMaBCC(String maNV){
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        return "CCNV"+date +maNV;
    }


}
