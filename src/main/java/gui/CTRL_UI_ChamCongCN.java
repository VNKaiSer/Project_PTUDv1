package gui;

import bus.BUS_ChamCongCN;
import bus.BUS_ChamCongNhanVien;
import bus.BUS_CongNhan;
import bus.BUS_NhanVien;
import dto.DTO_BCCCongNhan;
import dto.DTO_BCCNhanVien;
import dto.DTO_CongNhan;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTRL_UI_ChamCongCN implements Initializable {
    @FXML
    private Button btnIn;

    @FXML
    private Button btnLuu;

    @FXML
    private ComboBox<?> cboMaCaLam;

    @FXML
    private ComboBox<?> cboMaCongDoan;

    @FXML
    private TableColumn<?, ?> hienDienCol;

    @FXML
    private TableColumn<?, ?> hoTenCol;

    @FXML
    private TableColumn<?, ?> maCaLamCol;

    @FXML
    private TableColumn<?, ?> maCongNhanCol;

    @FXML
    private TableColumn<?, ?> ngayChamCongCol;

    @FXML
    private TableColumn<?, ?> soLuongCol;

    @FXML
    private TableColumn<?, ?> tenCongDoan;

    @FXML
    private DatePicker txtNgayChamCong;

    @FXML
    private TextField txtTimCongNhan;
    @FXML
    private TableColumn<?, ?> ghiChuCol;
    @FXML
    private TableView<DTO_BCCCongNhan> tblBCCCN;
    private BUS_CongNhan bus_congNhan;

    private boolean nowCC = true;

    private ArrayList<DTO_BCCCongNhan> dsBCCDaCham;
    private ArrayList<DTO_BCCCongNhan> dsBCCHienTai;

    private Dialog dialog;

    private  BUS_ChamCongCN bus_chamCongCN;

    private final int COMAT = 1;
    private final int PHEP = 2;
    private final int VANG = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // khởi tạo các bus và các danh sách
        bus_congNhan = new BUS_CongNhan();
        try {
            bus_chamCongCN = new BUS_ChamCongCN();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dsBCCHienTai = new ArrayList<>();
        dsBCCDaCham = new ArrayList<>();

        LocalDate ngayHienTai = Instant.ofEpochMilli(new Date().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        txtNgayChamCong.setValue(ngayHienTai);

        maCongNhanCol.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
        ngayChamCongCol.setCellValueFactory(new PropertyValueFactory<>("ngayChamCong"));
        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuongSanPhamLamDuoc"));
        hienDienCol.setCellValueFactory(new PropertyValueFactory<>("hienDien"));
        ghiChuCol.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        try {
            loadDanhSachChamCongHomNay();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // băt sự kiện trên các componet
        txtNgayChamCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // ngày chấm công lơn hơn ngày hiện tại
                Date ngayCC = Date.from(txtNgayChamCong.getValue().atStartOfDay()
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
                    try {
                        loadDanhSachChamCongHomNay();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                if (ngayCC.after(new Date())) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Ngày chấm công phải bé hơn ngày hiện tại");
                    Optional<ButtonType> a = er.showAndWait();
                    txtNgayChamCong.setValue(ngayHienTai);
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
                fxmlLoader.setLocation(getClass().getResource("Dialog_ChamCongCongNhan.fxml"));
                DialogPane addDialog = null;
                try {
                    addDialog = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dialog = new Dialog<>();
                dialog.setDialogPane(addDialog);
                CTRL_Dialog_CCCN ctrl_dialog_ccnv = fxmlLoader.getController();

                DTO_BCCCongNhan selectCN = tblBCCCN.getSelectionModel().getSelectedItem();
                ctrl_dialog_ccnv.setData(selectCN.getHienDien(), selectCN.getGhiChu(), selectCN.getSoLuongSanPhamLamDuoc());
                Optional clickedButton = dialog.showAndWait();

                if (ctrl_dialog_ccnv.getCheckLuu()) {
                    selectCN.setGhiChu(ctrl_dialog_ccnv.getGhiChu());
                    selectCN.setHienDien(ctrl_dialog_ccnv.getTrangThai());
                    selectCN.setSoLuongSanPhamLamDuoc(ctrl_dialog_ccnv.getSoLuong());
                    tblBCCCN.getItems().set(idx, selectCN);
                }
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (nowCC){
                    // cập nhật chấm công
                    Alert a = new Alert(Alert.AlertType.WARNING,"Chỉ chấm công được một lần bạn có chắc chắn LƯU", ButtonType.YES, ButtonType.NO);
                    for (DTO_BCCCongNhan it:
                            dsBCCHienTai) {
                        //it.setHienDien();
                    }
                    insertToDataBase();
                    nowCC = false;
                    tblBCCCN.setDisable(true);

                }
            }
        });


    }

    private void loadDanhSachChamCongDaCham() {
        tblBCCCN.getItems().clear();
        tblBCCCN.setItems(FXCollections.observableArrayList(bus_chamCongCN.getDSBCCNTheoNgay(txtNgayChamCong.getValue().toString())));
    }

    private void loadDanhSachChamCongHomNay() throws SQLException, ParseException {
        // check đã chấm công hôm nay hay chưa
        ArrayList<DTO_BCCCongNhan> checkTMP = bus_chamCongCN.getDSBCCNTheoNgay(txtNgayChamCong.getValue().toString());
        if (!checkTMP.isEmpty()){
            loadDanhSachChamCongDaCham();
            return;
        }
        dsBCCHienTai = new ArrayList<>();
        ArrayList<DTO_CongNhan> dsCN = bus_congNhan.getDSCongNhan();
        int numNhanVien = dsCN.size();
        for (int i = 0; i < numNhanVien; i++) {
            String maBCC = taoMaBCC(dsCN.get(i).getMaCongNhan());
            int soLuong = 0;
            dsBCCHienTai.add(new DTO_BCCCongNhan(dsCN.get(i), 1,0, new Date(), maBCC, ""));
        }
        tblBCCCN.setItems(FXCollections.observableArrayList(dsBCCHienTai));

    }

    void closeDialog() {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

    void insertToDataBase(){
        bus_chamCongCN.insertBCCNVToDatabase(dsBCCHienTai);
    }

    private String taoMaBCC(String maNV){
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        return "CCCN"+date +maNV;
    }

}
