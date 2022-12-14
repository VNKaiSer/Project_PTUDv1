package gui;

import bus.BUS_TaiKhoan;
import gui.splashscreen.SplashScreen;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTRL_DangNhap implements Initializable {
    @FXML
    private TextField txtPassShow;
    @FXML
    private CheckBox showPss;

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUser;

    @FXML
    private Button btnDangNhap;

    private String nguoiDangNhap;
    private int tk = 999;
    @FXML
    private Button btnQuenPass;

    @FXML
    private Label lblLogin;
    @FXML
    private Button btnCheck;

    @FXML
    private TextField btnMatKhauMoi;

    @FXML
    private Button btnQuayLaiDangNhap;

    @FXML
    private Button btn_XacNhan;

    @FXML
    private Label lblChangPS;

    @FXML
    private TextField txtMaNhanVien;

    @FXML
    private TextField txtOTP;

    public int getTk() {
        return tk;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtPassShow.setVisible(false);
        btnQuenPass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        btnDangNhap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (dangNhap()){
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("mm-v3.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setTitle("Main Menu");
                            stage.setScene(scene);
                            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                            CTRL_MainMenu ctrlMainMenu = fxmlLoader.getController();
                            ctrlMainMenu.setName(nguoiDangNhap);
                            ctrlMainMenu.setMaNV(txtUser.getText());
                            if (tk == 1) ctrlMainMenu.isQuanLy();
                            else if (tk == 2) ctrlMainMenu.isNhanVien();
                            new SplashScreen(null, true).disPlaySplashCard();
                            Thread.sleep(3700);
                            stage.show();
                            //new main().start(new Stage());
                        } catch (IOException e) {
                            Logger logger = Logger.getLogger(getClass().getName());
                            logger.log(Level.SEVERE, "Failed to create new Window.", e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        showPss.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean ovl, Boolean nvl) ->{
            if (showPss.isSelected()){
               txtPassShow.setText((txtPass.getText()));
               txtPassShow.setVisible(true);
               txtPass.setVisible(false);
               return;
            } else {
                txtPass.setText((txtPassShow.getText()));
                txtPass.setVisible(true);
                txtPassShow.setVisible(false);
            }
        });

        btnQuenPass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UI_RestPassWord.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage secondStage = new Stage();
                    secondStage.setScene(scene);
                    secondStage.setTitle("Login");
                    Stage thisStage = (Stage) lblLogin.getScene().getWindow();
                    thisStage.hide();
                    secondStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    private boolean dangNhap() throws SQLException {
        boolean check = false;
        tk = new BUS_TaiKhoan().dangNhap(txtUser.getText(), txtPass.getText());
       if (tk == 1){
           nguoiDangNhap = "Quản Lý";
           return true;
       } else if(tk == -1){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Không tồn tại tên tài khoản này", ButtonType.APPLY);
           alert.showAndWait();
           return false;
       } else if(tk == -2){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Sai mật khẩu", ButtonType.APPLY);
           alert.showAndWait();
           return false;
       }else if (tk == 2){
           nguoiDangNhap = getName();
           return true;
       } else {
           nguoiDangNhap = "";
           return true;
       }

    }

    /**
     * Hàm lấy tên
     * @return
     */
    private String getName() throws SQLException {
        return new BUS_TaiKhoan().getTenNguoiDangNhan(txtUser.getText());
    }

}

