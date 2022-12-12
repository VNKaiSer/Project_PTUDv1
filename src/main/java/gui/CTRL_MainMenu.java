package gui;

import bus.BUS_API;
import db.ConnectAPI;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CTRL_MainMenu implements Initializable {
    @FXML
    private Label Menu;
    @FXML
    private MenuItem mni_ChamCong;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane Slider;

    @FXML
    private MenuItem mni_chiaCongDoan;
    @FXML
    private BorderPane pn_Feature;

    @FXML
    private MenuItem mni_themCongNhan;

    @FXML
    private MenuItem mni_themNhanVien;

    @FXML
    private MenuItem mni_thongTinNV;

    @FXML
    private MenuItem mni_PhanCong;
    @FXML
    private MenuItem mni_CCNV;
    @FXML
    private MenuItem mni_TinhLuong;
    @FXML
    private MenuItem mni_SanPham;

    @FXML
    private MenuButton lblName;

    @FXML
    private MenuItem mni_DangXuat;
    @FXML
    private MenuItem mni_doiMatKhau;
    private Parent cn;

    private String name;
    @FXML
    private MenuButton mnbt_nhanVien;

    @FXML
    private MenuButton mnbt_QuanLy;

    private String maNV;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cn = FXMLLoader.load(getClass().getResource("UI_CongNhan_FXMLv2.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Slider.setTranslateX(-200);
        Menu.setOnMouseClicked(mouseEvent -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(Slider);

            slide.setToX(0);
            slide.play();

            Slider.setTranslateX(-200);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                pn_Feature.setPrefSize(580, 1190);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(mouseEvent -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(Slider);

            slide.setToX(-200);
            slide.play();

            Slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });

        acctionChoi();
    }

    private void acctionChoi(){
        mni_themCongNhan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pn_Feature.setCenter(cn);
            }
        });

        mni_chiaCongDoan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_ChiaCongDoan.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_ChamCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_ChamCongNhanVien.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_themNhanVien.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_NhanVien_FX_Ver3.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_DangXuat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UI_DangNhap.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());

                Stage secondStage = new Stage();
                secondStage.setScene(scene);
                secondStage.setTitle("Second Form");
                Stage thisStage = (Stage) MenuBack.getScene().getWindow();
                thisStage.hide();
                secondStage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_PhanCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_PhanCong.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_CCNV.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_ChamCongCongNhan.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_TinhLuong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_TinhLuong.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_SanPham.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadScene("UI_SanPham_FXML.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mni_doiMatKhau.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UI_DoiMatKhau.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());

                    Stage secondStage = new Stage();
                    secondStage.setScene(scene);
                    secondStage.setTitle("Login");
                    Stage thisStage = (Stage) MenuBack.getScene().getWindow();
                    CTRL_DoiMatKhau ctrl_doiMatKhau = fxmlLoader.getController();
                    ctrl_doiMatKhau.setMaNhanVien(maNV);
                    secondStage.showAndWait();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }

    private void loadScene(String sc) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(sc));
        pn_Feature.setCenter(root);
    }
    public void setName(String name) {
        lblName.setText(name);
    }

    public void isQuanLy(){
        mnbt_nhanVien.setVisible(false);
    }
    public void isNhanVien(){
        mnbt_QuanLy.setVisible(false);
    }
    public void setMaNV(String maNV){
        this.maNV = maNV;
    }
    public String getMaNhanVien(){
        return maNV;
    }
}
