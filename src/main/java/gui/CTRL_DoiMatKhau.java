package gui;

import bus.BUS_TaiKhoan;
import dto.DTO_TaiKhoan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTRL_DoiMatKhau implements Initializable {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                String matKhau = txt_matKhau.getText();
                String nhapLaiMK = txt_NhapLaiMK.getText();
                if(matKhau.equals("")||nhapLaiMK.equals("")){
                    Alert wn = new Alert(Alert.AlertType.WARNING, "Lỗi", ButtonType.APPLY);
                    wn.setContentText("Vui long không để trống thông tin");
                    Optional<ButtonType> showWN = wn.showAndWait();
                    return;
                }
                else{
                    if(matKhau.equals(nhapLaiMK)){
                        String ma = maNV;
                        bus_taiKhoan.updateTaiKhoan(new DTO_TaiKhoan(ma,matKhau));
                        Alert wn = new Alert(Alert.AlertType.INFORMATION, "Success", ButtonType.APPLY);
                        wn.setContentText("Đổi mật khẩu thành công");
                        Optional<ButtonType> showWN = wn.showAndWait();
                        return;
                    }
                    else {
                        Alert wn = new Alert(Alert.AlertType.WARNING, "Dữ liệu không phù hợp", ButtonType.APPLY);
                        wn.setContentText("Mật khẩu nhập lại không khớp");
                        Optional<ButtonType> showWN = wn.showAndWait();
                        return;
                    }
                }

            }
        });
    }
    public String setMaNhanVien(String maNV){
        return this.maNV = maNV;
    }
}
