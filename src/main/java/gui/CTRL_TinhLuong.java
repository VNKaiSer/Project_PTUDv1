package gui;

import bus.BUS_TinhLuong;
import bus.Service.PdfFileExporter;
import dto.DTO_PhieuLuongCaNhan;
import dto.DTO_PhieuLuongCongNhan;
import dto.DTO_PhieuLuongNhanVien;
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

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class CTRL_TinhLuong implements Initializable {

    private final DecimalFormat df = new DecimalFormat("###,###");
    ObservableList<String> listChucVu = FXCollections.observableArrayList("Nhân viên", "Công nhân");
    ObservableList<String> listNam;
    ObservableList<String> listThang;
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
    @FXML
    private Label lblLuongCoBan;
    @FXML
    private Button btnInPhieu;
    private final boolean checkHT = false;

    /**
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hiện thị nút
        btnSua.setDisable(false);
        //btnHoanThanh.setDisable(false);


        // khởi tạo combobox
        listThang = FXCollections.observableArrayList();
        cboChucVu.setItems(listChucVu);
        for (int i = 1; i < 13; i++) {
            listThang.add(String.valueOf(i));
        }
        cboThang.setItems(listThang);
        cboThang.setValue(String.valueOf(new Date().getMonth() + 1));
        listNam = FXCollections.observableArrayList();
        for (int i = 2018; i < 2030; i++) {
            listNam.add(String.valueOf(i));
        }
        cboNam.setItems(listNam);
        cboNam.setValue(String.valueOf(new Date().getYear() + 1900));


        // load dữ liệu của bảng
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
            // cboThang.getValue()), Integer.parseInt(cboNam.getValue())
            ArrayList<DTO_PhieuLuongCaNhan> dsPL = bus_tinhLuong.getDSPL(Integer.parseInt(cboThang.getValue()), Integer.parseInt(cboNam.getValue()));
            //System.out.println(dsPL);
            tblTinhLuong.setItems(FXCollections.observableArrayList(dsPL));
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        handleEvent();

    }

    /**
     * Hàm sử lý sự kiện trong giao diện tính lương
     */
    private void handleEvent() {
        cboNam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int nam = new Date().getYear() + 1900;
                int thang = new Date().getMonth();
                // nếu năm chấm lớn hơn năm hiện tại
                if (Integer.parseInt(cboNam.getValue()) > nam) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Chưa được tính lương cho ngày này", ButtonType.CANCEL);
                    a.showAndWait();
                    return;
                }

                // xem lại các phiếu lương cũ
                if (Integer.parseInt(cboNam.getValue()) < nam) {
                    try {
                        loadTable();
                    } catch (SQLException e) {
                        //throw new RuntimeException(e);
                    }
                }

            }
        });

        cboThang.setOnAction(new EventHandler<ActionEvent>() {
            final String nam = String.valueOf(new Date().getYear() + 1900);
            final String thang = String.valueOf(new Date().getMonth() + 1);

            @Override
            public void handle(ActionEvent event) {
                btnSua.setDisable(true);
                // được phép sửa lương với tháng hiện tại
                if (cboNam.getValue().equals(nam) && Objects.equals(cboThang.getValue(), thang)) {
                    // cho tính lương thao  tác sửa
                    btnSua.setDisable(false);
                    //btnHoanThanh.setDisable(false);
                    try {
                        sinhPhieuLuong(Integer.parseInt(thang), Integer.parseInt(nam));
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

        // sự kiện trên bảng
        tblTinhLuong.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();

                if (idx == -1) {
                    return;
                }

                DTO_PhieuLuongCaNhan tmp = tblTinhLuong.getSelectionModel().getSelectedItem();
                if (tmp instanceof DTO_PhieuLuongNhanVien) {

                    txtMaNhanVien.setText(((DTO_PhieuLuongNhanVien) tmp).getNhanVien().getMaNhanVien());
                    txtHoTen.setText(((DTO_PhieuLuongNhanVien) tmp).getNhanVien().getTenNhanVien());
                    cboChucVu.setValue("Nhân viên");
                    txtThue.setText("8%");
                    txtLuongCoBan.setText(String.valueOf(((DTO_PhieuLuongNhanVien) tmp).getLuongCoBan()));
                    lblLuongCoBan.setText("Lương cơ bản");
                } else {
                    txtMaNhanVien.setText(((DTO_PhieuLuongCongNhan) tmp).getCongNhan().getMaCongNhan());
                    txtHoTen.setText(((DTO_PhieuLuongCongNhan) tmp).getCongNhan().getMaCongNhan());
                    cboChucVu.setValue("Công nhân");
                    txtThue.setText("8%");
                    txtLuongCoBan.setText(String.valueOf(((DTO_PhieuLuongCongNhan) tmp).getTongSoSanPham()));
                    lblLuongCoBan.setText("Số sản phẩm");
                }
                txtTienTroCap.setText(df.format(tmp.getTienPhuCap()));
                txtTienTrachNhiem.setText(df.format(tmp.getTienTrachNhiem()));
                txtTienPhat.setText(df.format(tmp.getTienPhat()));
                txtSoNgayCong.setText(df.format(tmp.getSoNgayCong()));
                txtLuongTamUng.setText(df.format(tmp.getTamUng()));
                txtTongLuong.setText(df.format(tmp.getTongLuong()));
                txtThucNhan.setText(df.format(tmp.getThucNhan()));
            }
        });

        // sự kiện trên nút sửa
        btnSua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // hiển thị các ô được sửa

                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();

                if (idx == -1) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dòng cần sửa", ButtonType.CANCEL);
                    a.showAndWait();
                    return;
                }

                txtLuongTamUng.setDisable(false);
                txtTienPhat.setDisable(false);

                // hiện thị 2 nút
                btnSua.setDisable(true);
                btnHuy.setDisable(false);
                btnLuu.setDisable(false);
                // k cho người dùng chọn ô khác
                tblTinhLuong.setDisable(true);
                btnInPhieu.setDisable(true);
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
                btnInPhieu.setDisable(false);
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DTO_PhieuLuongCaNhan select = tblTinhLuong.getSelectionModel().getSelectedItem();
                select.ungLuong(Double.parseDouble(txtLuongTamUng.getText().replaceAll(",", "")));
                select.setTienPhat(Double.parseDouble(txtTienPhat.getText().replaceAll(",", "")));

                try {
                    bus_tinhLuong.saveDataBase(select);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();

                tblTinhLuong.getItems().set(idx, select);
                try {
                    tblTinhLuong.setItems(FXCollections.observableArrayList(bus_tinhLuong.getDSPL(Integer.parseInt(cboThang.getValue()), Integer.parseInt(cboNam.getValue()))));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                txtTongLuong.setText(df.format((select.getTongLuong())));
                txtThucNhan.setText(df.format(select.getThucNhan()));

                btnSua.setDisable(false);
                btnHuy.setDisable(true);
                btnLuu.setDisable(true);

                //
                txtLuongTamUng.setDisable(true);
                txtTienPhat.setDisable(true);
                //
                tblTinhLuong.setDisable(false);
                btnInPhieu.setDisable(false);
            }
        });

//        btnHoanThanh.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    bus_tinhLuong.saveDataBase(tblTinhLuong.getItems());
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                checkHT = true;
//                btnHoanThanh.setDisable(true);
//                btnSua.setDisable(true);
//            }
//        });

        btnInPhieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int idx = tblTinhLuong.getSelectionModel().getSelectedIndex();
                if (idx == -1) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn nhân viên cần xuất", ButtonType.CANCEL);
                    a.showAndWait();
                }

                DTO_PhieuLuongCaNhan pl = tblTinhLuong.getSelectionModel().getSelectedItem();
                int thang = tblTinhLuong.getSelectionModel().getSelectedItem().getThang();
                int nam = tblTinhLuong.getSelectionModel().getSelectedItem().getNam();
                exportPhieuLuong(pl);


            }
        });

        // sự kiện chỉ được nhập số trên các ô textfield
        txtLuongTamUng.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtLuongTamUng.setText(newValue.replaceAll("[^\\d,]", ""));
                boolean firstPointFound = false;
                newValue = txtLuongTamUng.getText();
                txtLuongTamUng.setText(newValue);
            } else {
                txtLuongTamUng.setText(newValue);
            }
        });

        txtTienPhat.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTienPhat.setText(newValue.replaceAll("[^\\d,]", ""));
                boolean firstPointFound = false;

                newValue = txtTienPhat.getText();
                txtTienPhat.setText(newValue);
            } else {
                txtTienPhat.setText(newValue);
            }
        });
    }

    private void sinhPhieuLuong(int thang, int nam) throws SQLException {
        tblTinhLuong.getItems().removeAll();
        tblTinhLuong.setItems(FXCollections.observableArrayList(bus_tinhLuong.getDSPL(thang, nam)));
    }

    /**
     * Hàm sử lý load lại bảng tính lương
     */
    private void loadTable() throws SQLException {
        String nam = cboNam.getValue();
        String thang = cboThang.getValue();
        ArrayList<DTO_PhieuLuongCaNhan> dsPL = bus_tinhLuong.getDSPL(Integer.parseInt(thang), Integer.parseInt(nam));
        tblTinhLuong.getItems().removeAll();
        tblTinhLuong.setItems(FXCollections.observableArrayList(dsPL));
    }

    /**
     * Hàm sinh phiếu lương
     */
//    private void sinhPhieuLuong(int thang, int nam) throws SQLException {
//        tblTinhLuong.getItems().removeAll();
//        tblTinhLuong.setItems(FXCollections.observableArrayList(bus_tinhLuong.sinhDanhSachPhieuLuong(thang, nam)));
//    }
    private void exportPhieuLuong(DTO_PhieuLuongCaNhan plcn) {
        new PdfFileExporter().exportPDF(plcn);
    }


}
