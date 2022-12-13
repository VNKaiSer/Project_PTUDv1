package gui;

import bus.BUS_TaiKhoan;
import bus.Service.SendOTP;
import dto.DTO_TaiKhoan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTL_UI_RestPass implements Initializable {
    private int nl = 3;
    @FXML
    private Button btnQuayLaiDangNhap;
    @FXML
    private Label lblChangPS;
    @FXML
    private Button btn_XacNhan;
    @FXML
    private TextField txt_NhapLaiMK;

    @FXML
    private TextField txt_matKhau;
    private String maNV;
    private BUS_TaiKhoan bus_taiKhoan = new BUS_TaiKhoan();
    @FXML
    private Button btnQuenPass;
    @FXML
    private Button btnCheck;

    @FXML
    private TextField btnMatKhauMoi;

    @FXML
    private TextField txtMaNhanVien;

    @FXML
    private TextField txtOTP;

    private SendOTP sendOTP;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtMaNhanVien.requestFocus();
        sendOTP = new SendOTP();
        btn_XacNhan.setDisable(true);
        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String maNV = txtMaNhanVien.getText();
                if (maNV.length() == 0){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng nhập mã nhân viên cần lấy lại mật khẩu");
                    a.showAndWait();
                    txtMaNhanVien.requestFocus();
                    return;
                } else {
                    try {
                        String tenNV = new BUS_TaiKhoan().getTenNguoiDangNhan(maNV);
                        if (tenNV == "NULL" || tenNV == ""){
                            Alert a = new Alert(Alert.AlertType.ERROR, "Không tồn tại mã nhân viên này");
                            a.showAndWait();
                            txtMaNhanVien.requestFocus();
                            return;
                        } else{
                            String sdt = new BUS_TaiKhoan().getSDT(maNV);
                            Alert a = new Alert(Alert.AlertType.INFORMATION, "OTP đã được gửi về số điện thoại "+ sdt.substring(0,3)+"***"+sdt.substring(sdt.length() - 2,sdt.length()));
                            a.showAndWait();
                            sendOTP.sendOTP(sdt);
                            btn_XacNhan.setDisable(false);
                            txt_NhapLaiMK.setDisable(false);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        btnQuayLaiDangNhap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UI_DangNhap.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage secondStage = new Stage();
                    secondStage.setScene(scene);
                    secondStage.setTitle("Login");
                    Stage thisStage = (Stage) lblChangPS.getScene().getWindow();
                    thisStage.hide();
                    secondStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_XacNhan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String maNV = txtMaNhanVien.getText();
                String matKhau = txt_NhapLaiMK.getText();
                if (matKhau.length() == 0){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Mật khẩu mới không được để trống");
                    txt_NhapLaiMK.requestFocus();
                    a.showAndWait();
                    return;
                }
                DTO_TaiKhoan tk = new DTO_TaiKhoan(maNV, matKhau);
                if (sendOTP.getOTP().equals(txtOTP.getText())){
                    System.out.println(sendOTP.getOTP() + " " + txtOTP.getText());
                    new BUS_TaiKhoan().updateTaiKhoan(tk);
                    Alert a = new Alert(Alert.AlertType.ERROR, "Thành công");
                    a.showAndWait();
                    return;
                }  else {
                    if (nl == 0){
                        Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng thử lại sau.");
                        a.showAndWait();
                        return;
                    }
                    Alert a = new Alert(Alert.AlertType.ERROR, "Mã OTP không hợp lệ, Số lần nhập lại của bạn còn: "+(--nl));
                    a.showAndWait();
                    return;
                }
            }
        });
    }
    public String setMaNhanVien(String maNV){
        return this.maNV = maNV;
    }
}
