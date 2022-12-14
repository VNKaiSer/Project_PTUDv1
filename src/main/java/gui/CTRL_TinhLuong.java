package gui;

import bus.BUS_TinhLuong;
import bus.Service.PdfFileExporter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import dto.DTO_PhieuLuongCaNhan;
import dto.DTO_PhieuLuongCongNhan;
import dto.DTO_PhieuLuongNhanVien;
import dto.DTO_ThongKeDiemDanh;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class CTRL_TinhLuong implements Initializable {

    @FXML
    private ComboBox<String> cboChucVu;

    @FXML
    private Button btnHuy;

    @FXML
    private Button btnLuu;

    @FXML
    private Button btnSua;

    @FXML
    private ComboBox<String> cboNam;

    @FXML
    private TableColumn<?, ?> chucVuCol;

    @FXML
    private TableColumn<?, ?> ghiChuCol;

    @FXML
    private TableColumn<?, ?> hoTenCol;

    @FXML
    private TableColumn<?, ?> phuCapCol;

    @FXML
    private TableColumn<?, ?> soCongCol;

    @FXML
    private TableColumn<?, ?> tamUngCol;

    @FXML
    private TableColumn<?, ?> thucNhanCol;

    @FXML
    private TableColumn<?, ?> thueCol;

    @FXML
    private TableColumn<?, ?> tienPhatCol;

    @FXML
    private TableColumn<?, ?> tienTrachNhiemCol;

    @FXML
    private TableColumn<?, ?> tongCol;

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtLuongCoBan;

    @FXML
    private TextField txtLuongTamUng;

    @FXML
    private TextField txtMaNhanVien;

    @FXML
    private TextField txtSoNgayCong;

    @FXML
    private ComboBox<String> cboThang;

    @FXML
    private TextField txtThucNhan;

    @FXML
    private TextField txtThue;

    @FXML
    private TextField txtTienPhat;

    @FXML
    private TextField txtTienTrachNhiem;

    @FXML
    private TextField txtTienTroCap;

    @FXML
    private TextField txtTim;

    @FXML
    private TextField txtTongLuong;

    @FXML
    private TableView<DTO_PhieuLuongCaNhan> tblTinhLuong;

    private BUS_TinhLuong bus_tinhLuong;
    ObservableList<String> listChucVu = FXCollections.observableArrayList("Nh??n vi??n", "C??ng nh??n");
    ObservableList<String> listNam;
    ObservableList<String> listThang;

    @FXML
    private Label lblLuongCoBan;

    @FXML
    private Button btnHoanThanh;

    @FXML
    private Button btnInPhieu;

    private boolean checkHT = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // kh???i t???o combobox
        listThang = FXCollections.observableArrayList();
        cboChucVu.setItems(listChucVu);
        for (int i = 1; i < 13; i++) {
            listThang.add(String.valueOf(i));
        }
        cboThang.setItems(listThang);
        cboThang.setValue("1");
        listNam = FXCollections.observableArrayList();
        for (int i = 2018; i < 2030; i++) {
            listNam.add(String.valueOf(i));
        }
        cboNam.setItems(listNam);
        cboNam.setValue(String.valueOf(new Date().getYear() + 1900));


        // load d??? li???u c???a b???ng
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        chucVuCol.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        phuCapCol.setCellValueFactory(new PropertyValueFactory<>("tienPhuCap"));
        tienTrachNhiemCol.setCellValueFactory(new PropertyValueFactory<>("tienTrachNhiem"));
        soCongCol.setCellValueFactory(new PropertyValueFactory<>("soNgayCong"));
        tongCol.setCellValueFactory(new PropertyValueFactory<>("tongLuong"));
        thueCol.setCellValueFactory(new PropertyValueFactory<>("thue"));
        tienPhatCol.setCellValueFactory(new PropertyValueFactory<>("tienPhat"));
        tamUngCol.setCellValueFactory(new PropertyValueFactory<>("tamUng"));
        thucNhanCol.setCellValueFactory(new PropertyValueFactory<>("thucNhan"));

        try {
            bus_tinhLuong = new BUS_TinhLuong();
            ArrayList<DTO_PhieuLuongCaNhan> dsPL = bus_tinhLuong.getDSPL(9, 2022);
            tblTinhLuong.setItems(FXCollections.observableArrayList(dsPL));
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        handleEvent();

    }

    /**
     * H??m s??? l?? s??? ki???n trong giao di???n t??nh l????ng
     */
    private void handleEvent(){
        cboNam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int nam = new Date().getYear() + 1900;
                int thang = new Date().getMonth();
                // n???u n??m ch???m l???n h??n n??m hi???n t???i
                if (Integer.parseInt(cboNam.getValue()) > nam){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Ch??a ???????c t??nh l????ng cho ng??y n??y",ButtonType.CANCEL);
                    a.showAndWait();
                    return;
                }

                // xem l???i c??c phi???u l????ng c??
                if (Integer.parseInt(cboNam.getValue()) < nam){
                    try {
                        loadTable();
                    } catch (SQLException e) {
                        //throw new RuntimeException(e);
                    }
                }

            }
        });

        cboThang.setOnAction(new EventHandler<ActionEvent>() {
            String nam = String.valueOf(new Date().getYear() + 1900);
            String thang = String.valueOf(new Date().getMonth() + 1);
            @Override
            public void handle(ActionEvent event) {
                btnSua.setDisable(true);
                // ???????c ph??p s???a l????ng v???i th??ng hi???n t???i
                if (cboNam.getValue().equals(nam) && Objects.equals(cboThang.getValue(), thang)){
                    // cho t??nh l????ng thao  t??c s???a
                        btnSua.setDisable(false);
                        btnHoanThanh.setDisable(false);
                    try {
                        sinhPhieuLuong(Integer.parseInt(thang),Integer.parseInt(nam));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    try {
                        loadTable();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


        });

        // s??? ki???n tr??n b???ng
        tblTinhLuong.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();

                if (idx == -1){
                    return;
                }

                DTO_PhieuLuongCaNhan tmp = tblTinhLuong.getSelectionModel().getSelectedItem();
                if (tmp instanceof DTO_PhieuLuongNhanVien){

                    txtMaNhanVien.setText(((DTO_PhieuLuongNhanVien) tmp).getNhanVien().getMaNhanVien());
                    txtHoTen.setText(((DTO_PhieuLuongNhanVien) tmp).getNhanVien().getTenNhanVien());
                    cboChucVu.setValue("Nh??n vi??n");
                    txtThue.setText("8%");
                    txtLuongCoBan.setText(String.valueOf(((DTO_PhieuLuongNhanVien) tmp).getLuongCoBan()));
                    lblLuongCoBan.setText("L????ng c?? b???n");
                } else {
                    txtMaNhanVien.setText(((DTO_PhieuLuongCongNhan) tmp).getCongNhan().getMaCongNhan());
                    txtHoTen.setText(((DTO_PhieuLuongCongNhan) tmp).getCongNhan().getMaCongNhan());
                    cboChucVu.setValue("C??ng nh??n");
                    txtThue.setText("8%");
                    txtLuongCoBan.setText(String.valueOf(((DTO_PhieuLuongCongNhan) tmp).getTongSoSanPham()));
                    lblLuongCoBan.setText("S??? s???n ph???m");
                }
                txtTienTroCap.setText(String.valueOf(tmp.getTienPhuCap()));
                txtTienTrachNhiem.setText(String.valueOf(tmp.getTienTrachNhiem()));
                txtTienPhat.setText(String.valueOf(tmp.getTienPhat()));
                txtSoNgayCong.setText(String.valueOf(tmp.getSoNgayCong()));
                txtLuongTamUng.setText(String.valueOf(tmp.getTamUng()));
                txtTongLuong.setText(String.valueOf(tmp.getTongLuong()));
                txtThucNhan.setText(String.valueOf(tmp.getThucNhan()));
            }
        });

        // s??? ki???n tr??n n??t s???a
        btnSua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // hi???n th??? c??c ?? ???????c s???a

                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();

                if (idx == -1){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui l??ng ch???n d??ng c???n s???a", ButtonType.CANCEL);
                    return;
                }

                txtLuongTamUng.setDisable(false);
                txtTienPhat.setDisable(false);

                // hi???n th??? 2 n??t
                btnSua.setDisable(true);
                btnHuy.setDisable(false);
                btnLuu.setDisable(false);
                // k cho ng?????i d??ng ch???n ?? kh??c
                tblTinhLuong.setDisable(true);
            }
        });

        btnHuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnSua.setDisable(false);
                btnHuy.setDisable(true);
                btnLuu.setDisable(true);

                //
                txtLuongTamUng.setDisable(true);
                txtTienPhat.setDisable(true);
                //
                tblTinhLuong.setDisable(false);
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DTO_PhieuLuongCaNhan select = tblTinhLuong.getSelectionModel().getSelectedItem();
                select.setTamUng(Double.parseDouble(txtLuongTamUng.getText()));
                select.setTienPhat(Double.parseDouble(txtTienPhat.getText()));
                tblTinhLuong.getItems().set(tblTinhLuong.getSelectionModel().getSelectedIndex(), select);

                btnSua.setDisable(false);
                btnHuy.setDisable(true);
                btnLuu.setDisable(true);

                //
                txtLuongTamUng.setDisable(true);
                txtTienPhat.setDisable(true);
                //
                tblTinhLuong.setDisable(false);
            }
        });

        btnHoanThanh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    bus_tinhLuong.saveDataBase(tblTinhLuong.getItems());
                    checkHT = true;
                    btnHoanThanh.setDisable(true);
                    btnSua.setDisable(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnInPhieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();
                if (idx == -1){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui l??ng ch???n nh??n vi??n c???n xu???t", ButtonType.CANCEL);
                    a.showAndWait();
                }

                DTO_PhieuLuongCaNhan pl = tblTinhLuong.getSelectionModel().getSelectedItem();
                int thang = tblTinhLuong.getSelectionModel().getSelectedItem().getThang();
                int nam = tblTinhLuong.getSelectionModel().getSelectedItem().getNam();
                if (pl instanceof DTO_PhieuLuongCongNhan) {
                    try {
                        exportPhieuLuong(pl, bus_tinhLuong.getTKDD(((DTO_PhieuLuongCongNhan) pl).getCongNhan(),thang, nam) );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        exportPhieuLuong(pl, bus_tinhLuong.getTKDD(((DTO_PhieuLuongNhanVien) pl).getNhanVien(),thang, nam));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        // s??? ki???n ch??? ???????c nh???p s??? tr??n c??c ?? textfield
        txtLuongTamUng.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtLuongTamUng.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txtLuongTamUng.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txtLuongTamUng.setText(newValue);
            } else {
                txtLuongTamUng.setText(newValue);
            }
        });

        txtTienPhat.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTienPhat.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txtTienPhat.getText());
                boolean firstPointFound = false;

                newValue = aus.toString();
                txtTienPhat.setText(newValue);
            } else {
                txtTienPhat.setText(newValue);
            }
        });
    }

    /**
     * H??m s??? l?? load l???i b???ng t??nh l????ng
     */
    private void loadTable() throws SQLException {
        String nam = cboNam.getValue();
        String thang = cboThang.getValue();
        ArrayList<DTO_PhieuLuongCaNhan> dsPL = bus_tinhLuong.getDSPL(Integer.parseInt(thang), Integer.parseInt(nam));
        tblTinhLuong.getItems().removeAll();
        tblTinhLuong.setItems(FXCollections.observableArrayList(dsPL));
    }

    /**
     * H??m sinh phi???u l????ng
     */
    private void sinhPhieuLuong(int thang, int nam) throws SQLException {
        tblTinhLuong.getItems().removeAll();
        tblTinhLuong.setItems(FXCollections.observableArrayList(bus_tinhLuong.sinhDanhSachPhieuLuong(thang, nam)));
    }

    private void exportPhieuLuong(DTO_PhieuLuongCaNhan plcn, DTO_ThongKeDiemDanh dd){
        new PdfFileExporter().exportPDF(plcn, dd);
    }



}
