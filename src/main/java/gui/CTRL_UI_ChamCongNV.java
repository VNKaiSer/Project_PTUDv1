package gui;

import bus.BUS_ChamCongNhanVien;
import bus.BUS_NhanVien;
import dto.DTO_BCCCongNhan;
import dto.DTO_BCCNhanVien;
import dto.DTO_NhanVien;
import dto.DTO_TBW_ChamCongNhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

public class CTRL_UI_ChamCongNV implements Initializable {

    @FXML
    private Button btnLuu;

    @FXML
    private TableColumn<?, ?> ghiChuCol;

    @FXML
    private TableColumn<DTO_TBW_ChamCongNhanVien, ComboBox<String>> hienDienCol;

    @FXML
    private TableColumn<?, ?> maNhanVienCol;

    @FXML
    private TableColumn<?, ?> ngayCCCol;

    @FXML
    private TableView<DTO_TBW_ChamCongNhanVien> tblBCCCN;

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

    private ComboBox<String> cboChamCong;

    private final int COMAT = 1;
    private final int PHEP = 2;
    private final int VANG = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // khởi tạo các bus và các danh sách
        bus_nhanVien = new BUS_NhanVien();
        bus_chamCongNhanVien = new BUS_ChamCongNhanVien();
        dsBCCHienTai = new ArrayList<>();
        dsBCCDaCham = new ArrayList<>();
        cboChamCong = new ComboBox<>();
        cboChamCong.setItems(FXCollections.observableArrayList("Đi làm","Vắng","Có phép"));
        LocalDate ngayHienTai = Instant.ofEpochMilli(new Date().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        txtNGayChamCong.setValue(ngayHienTai);

        maNhanVienCol.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        tenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        hienDienCol.setCellValueFactory(new PropertyValueFactory<DTO_TBW_ChamCongNhanVien, ComboBox<String>>("hienDien"));
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

//        tblBCCCN.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                int idx = tblBCCCN.getSelectionModel().getSelectedIndex();
//                if (idx == -1)
//                    return;
//
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("Dialog_ChamCongNhanVien.fxml"));
//                DialogPane addDialog = null;
//                try {
//                    addDialog = fxmlLoader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                dialog = new Dialog<>();
//                dialog.setDialogPane(addDialog);
//                CTRL_Dialog_CCNV ctrl_dialog_ccnv = fxmlLoader.getController();
//
//                DTO_BCCNhanVien selectCN = tblBCCCN.getSelectionModel().getSelectedItem();
//                ctrl_dialog_ccnv.setData(selectCN.getHienDien(), selectCN.getGhiChu());
//                Optional clickedButton = dialog.showAndWait();
//
//                if (ctrl_dialog_ccnv.getCheckLuu()) {
//                    selectCN.setGhiChu(ctrl_dialog_ccnv.getGhiChu());
//                    selectCN.setHienDien(ctrl_dialog_ccnv.getTrangThai());
//                    tblBCCCN.getItems().set(idx, selectCN);
//                }
//            }
//        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (nowCC){
                    // cập nhật chấm công
                    Alert a = new Alert(Alert.AlertType.WARNING,"Chỉ chấm công được 1 lần bạn có chắc chắn LƯU", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.NO || !result.isPresent()){
                        return;
                    }
                    int i = 0;
                    for (DTO_BCCNhanVien it:
                            dsBCCHienTai) {

                        it.setHienDien(tblBCCCN.getItems().get(i).getHienDien().getSelectionModel().getSelectedIndex());
                        it.setGhiChu(tblBCCCN.getItems().get(i).getGhiChu().getText());
                        i++;
                    }
                    insertToDataBase();
                    nowCC = false;
                    tblBCCCN.setDisable(true);

                }
            }
        });


    }

    private void loadDanhSachChamCongDaCham() {
        tblBCCCN.setDisable(true);
        tblBCCCN.getItems().clear();
        tblBCCCN.setItems(cover(FXCollections.observableArrayList(bus_chamCongNhanVien.getDSChamCongTheoNgay(txtNGayChamCong.getValue().toString()))));
    }

    private void loadDanhSachChamCongHomNay() {
        ArrayList<DTO_BCCNhanVien> checkTMP = bus_chamCongNhanVien.getDSChamCongTheoNgay(txtNGayChamCong.getValue().toString());
        System.out.println(!checkTMP.isEmpty());
        if (!checkTMP.isEmpty()){
            loadDanhSachChamCongDaCham();
            return;
        }
        dsBCCHienTai = new ArrayList<>();
        ArrayList<DTO_NhanVien> dsNV = bus_nhanVien.getAllDSNhanVien();
        int numNhanVien = dsNV.size();
        for (int i = 0; i < numNhanVien; i++) {
            String maBCC = taoMaBCC(dsNV.get(i).getMaNhanVien());
            dsBCCHienTai.add(new DTO_BCCNhanVien(dsNV.get(i), 1, new Date(), maBCC, ""));
        }
        tblBCCCN.setItems(cover(FXCollections.observableArrayList(dsBCCHienTai)));

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

    private ObservableList<DTO_TBW_ChamCongNhanVien> cover(ObservableList<DTO_BCCNhanVien> bcc){
        ObservableList<DTO_TBW_ChamCongNhanVien> list = FXCollections.observableArrayList();
        for (DTO_BCCNhanVien it:
                bcc) {
            ComboBox<String> cbo = new ComboBox<>();
            TextField t = new TextField(it.getGhiChu());

            cbo.setItems(FXCollections.observableArrayList("Vắng","Có mặt","Phép"));
            cbo.getSelectionModel().select(it.getHienDien());
            DTO_TBW_ChamCongNhanVien tmp = new DTO_TBW_ChamCongNhanVien(it.getNhanVien().getMaNhanVien(), it.getNhanVien().getTenNhanVien(), cbo, t);
            list.add(tmp);
        }
        return list;
    }


}
