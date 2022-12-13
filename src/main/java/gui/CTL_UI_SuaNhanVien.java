package gui;

import bus.BUS_NhanVien;
import dto.DTO_NhanVien;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTL_UI_SuaNhanVien implements Initializable {

    @FXML
    private Button btnDatLai;

    @FXML
    private Button btnDong;

    @FXML
    private Button btnThem;

    @FXML
    private ComboBox<String> cboGioiTinh;

    @FXML
    private ComboBox<String> cboHuyen;

    @FXML
    private ComboBox<String> cboPhuong;

    @FXML
    private ComboBox<String> cboTinh;

    @FXML
    private DialogPane dinalogPane;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtMaNV;

    @FXML
    private DatePicker txtNgaySinh;

    @FXML
    private DatePicker txtNgayVaoLam;

    @FXML
    private TextField txtSoDienThoai;

    @FXML
    private TextField txt_luongCoBan;

    private CTRL_UI_NhanVien tmpDianalog;

    private boolean checkFrom = false;
    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");

    private boolean checkLuu = false;
    private boolean checkXoa = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // thêm các sự kiện kiểm lỗi cho các componet
        try {
            txtMaNV.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtMaNV.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtMaNV.getText().toString().equals("")) {
                        txtMaNV.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtHoTen.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtHoTen.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtHoTen.getText().toString().equals("")) {
                        txtHoTen.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtNgayVaoLam.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent keyEvent) {
                    txtNgayVaoLam.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtNgayVaoLam.getValue().toString().equals(null)) {
                        txtNgayVaoLam.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }

                    Date ngayVaoLam = Date.from(txtNgayVaoLam.getValue().atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant());
                    if (getDiffDate(ngayVaoLam, new Date()) <= 0) {
                        Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                        er.setContentText("Ngày vào làm phải bé hơn ngày hiện tại");
                        txtNgayVaoLam.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                        Optional<ButtonType> a = er.showAndWait();
                    }
                }
            });

            txtNgaySinh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent keyEvent) {
                    txtNgaySinh.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtNgaySinh.getValue().toString().equals(null)) {
                        txtNgaySinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }

                    Date ngaySinh = Date.from(txtNgaySinh.getValue().atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant());
                    if (new Date().getYear() - ngaySinh.getYear() < 18 || new Date().getYear() - ngaySinh.getYear() > 60) {
                        Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                        er.setContentText("Nhân viên phải từ 18-60 tuổi");
                        txtNgaySinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                        Optional<ButtonType> a = er.showAndWait();
                    }
                }
            });

            cboGioiTinh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboGioiTinh.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboGioiTinh.getValue().toString().equals("")) {
                        cboGioiTinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txt_luongCoBan.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    txt_luongCoBan.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txt_luongCoBan.getText().equals("")) {
                        txt_luongCoBan.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtSoDienThoai.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtSoDienThoai.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtSoDienThoai.getText().toString().equals("")) {
                        txtSoDienThoai.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtSoDienThoai.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txtSoDienThoai.setText(newValue.replaceAll("[^\\d,]", ""));/*The comma here "[^\\d,]" can be changed with the dot*/
                    StringBuilder aus = new StringBuilder();
                    aus.append(txtSoDienThoai.getText());
                    boolean firstPointFound = false;

                    for (int i = 0; i < aus.length(); i++) {
                        if (aus.charAt(i) == ',') {/*Change the , with . if you want the . to be the decimal separator*/
                            if (!firstPointFound) {
                                firstPointFound = true;
                            } else {
                                aus.deleteCharAt(i);
                            }
                        }
                    }
                    newValue = aus.toString();
                    txtSoDienThoai.setText(newValue);
                } else {
                    txtSoDienThoai.setText(newValue);
                }
            });

            txtEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (txtEmail.getText().toString().equals("")) {
                        txtEmail.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                    if (!txtEmail.getText().toString().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")) {
                        txtEmail.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    } else {
                        txtEmail.setStyle("-fx-border-color: GREEN;");
                        checkFrom = true;
                    }
                }
            });

            txtDiaChi.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtDiaChi.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtDiaChi.getText().toString().equals("")) {
                        txtDiaChi.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            cboTinh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboTinh.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboTinh.getValue().toString().equals("")) {
                        cboTinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            cboHuyen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboHuyen.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboHuyen.getValue().toString().equals("")) {
                        cboHuyen.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            cboPhuong.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboPhuong.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboPhuong.getValue().toString().equals("")) {
                        cboPhuong.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });
        } catch (Exception ex) {

        }



        cboTinh.setItems(FXCollections.observableArrayList(CTL_UI_CongNhan.connectAPI.getDanhSachTinh()));
        cboTinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tinh = cboTinh.getValue();
                cboHuyen.setDisable(false);
                String codeProvince = cboTinh.getValue().toString();
                cboHuyen.setItems(FXCollections.observableArrayList( CTL_UI_CongNhan.connectAPI.getDanhSachHuyen(codeProvince)));
            }
        });

        cboHuyen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String huyen = cboTinh.getValue();
                cboPhuong.setDisable(false);
                String codeDis = cboHuyen.getValue().toString();
                cboPhuong.setItems(FXCollections.observableArrayList(CTL_UI_CongNhan.connectAPI.getDanhSachDuong(codeDis)));
            }
        });


        cboGioiTinh.setItems(listGioiTinh);
    }

    /***
     * Dừng dialog được mở lên
     */
    public void stopDialog() {
        tmpDianalog = new CTRL_UI_NhanVien();
        tmpDianalog.closeDialogSua();

    }

    /***
     * Hàm lưu thông tin sửa nhân viên
     */
    public void luuNhanVien() {
        // lấy thông tin  công nhân
        try {
            if (txtNgayVaoLam.getValue() == null)
                checkFrom = false;
            if (txtNgaySinh.getValue() == null)
                checkFrom = false;
            if (cboGioiTinh.getValue() == null)
                checkFrom = false;
            if (cboTinh.getValue() == null)
                checkFrom = false;
            if (cboHuyen.getValue() == null)
                checkFrom = false;
            if (cboPhuong.getValue() == null)
                checkFrom = false;

            if (!checkFrom) {
                Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
                wn.setContentText("Vui lòng chỉnh sửa thông tin các trường bị đánh dấu");
                Optional<ButtonType> showWN = wn.showAndWait();
                return;
            }

            String maCN = txtMaNV.getText();
            String tenCN = txtHoTen.getText();
            String ngaySinh = txtNgaySinh.getValue().toString();
            String ngayVaoLam = txtNgayVaoLam.getValue().toString();
            String gioiTinh = cboGioiTinh.getSelectionModel().getSelectedItem();
            boolean phai = true;
            if(gioiTinh.equalsIgnoreCase("Nữ")){
                phai = false;
            }
            String luong = txt_luongCoBan.getText();
            String sdt = txtSoDienThoai.getText().toString();
            String email = txtEmail.getText().toString();
            String diaChi = txtDiaChi.getText().toString() +"," + cboPhuong.getValue()+ ", " +cboHuyen.getValue() + ", "+cboTinh.getValue();
            Date ngaySinhDate = new SimpleDateFormat("YYYY-MM-dd").parse(ngaySinh);
            Date ngayVaoLamDate = new SimpleDateFormat("YYYY-MM-dd").parse(ngayVaoLam);

            // Tạo đối tượng
            DTO_NhanVien tmp = new DTO_NhanVien(maCN,tenCN,ngayVaoLamDate,phai,ngaySinhDate,sdt,email,diaChi,Double.parseDouble(luong));
            BUS_NhanVien bus_nhanVien = new BUS_NhanVien();
            cn_Sua = tmp;
            bus_nhanVien.updateNhanVien(tmp);
            stopDialog();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Hàm xuất thông báo Aler sửa nhân viên thành công
     */
    public void setData(DTO_NhanVien cn, String dc) {
        // lưu thông tin nhân viên sửa
        cn_Sua = cn;
        String diaChi [] = dc.split(",");
        this.dc = dc;

        txtMaNV.setText(cn.getMaNhanVien());
        txtMaNV.setStyle("-fx-border-color: GREEN;");
        txtHoTen.setText(cn.getTenNhanVien());
        txtHoTen.setStyle("-fx-border-color: GREEN;");
        txtEmail.setText(cn.getEmail());
        txtEmail.setStyle("-fx-border-color: GREEN;");
        txtSoDienThoai.setText(cn.getSoDienThoai());
        txtSoDienThoai.setStyle("-fx-border-color: GREEN;");
        LocalDate ngaySinh = Instant.ofEpochMilli(cn.getNgaySinh().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        txtNgaySinh.setValue(ngaySinh);
        txtNgaySinh.setStyle("-fx-border-color: GREEN;");

        LocalDate ngayVaoLam = Instant.ofEpochMilli(cn.getNgayVaoLam().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        txtNgayVaoLam.setValue(ngayVaoLam);
        txtNgayVaoLam.setStyle("-fx-border-color: GREEN;");

        cboGioiTinh.setValue(cn.isPhai() ? "Nam" : "Nữ");
        cboGioiTinh.setStyle("-fx-border-color: GREEN;");
        txt_luongCoBan.setText(cn.getLuongCoBan()+"");
        txt_luongCoBan.setStyle("-fx-border-color: GREEN;");

        cboTinh.setValue(diaChi[3]);
        cboTinh.setStyle("-fx-border-color: GREEN;");
        cboHuyen.setValue(diaChi[2]);
        cboHuyen.setStyle("-fx-border-color: GREEN;");
        cboPhuong.setValue(diaChi[1]);
        cboPhuong.setStyle("-fx-border-color: GREEN;");
        txtDiaChi.setText(diaChi[0]);
        txtDiaChi.setStyle("-fx-border-color: GREEN;");
        checkFrom = true;


    }

    private int getDiffDate(Date olderDate, Date newerDate) {
        int diffInDays = (int) ((newerDate.getTime() - olderDate.getTime())
                / (1000 * 60 * 60 * 24));
        return diffInDays;
    }

    public void handleKhoiPhuc() {
        setData(cn_Sua, dc);
    }

    public DTO_NhanVien getCn_Sua() {
        return cn_Sua;
    }

    private DTO_NhanVien cn_Sua;
    private String dc;

}
