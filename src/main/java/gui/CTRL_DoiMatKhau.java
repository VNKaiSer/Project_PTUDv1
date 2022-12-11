package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CTRL_DoiMatKhau implements Initializable {
    @FXML
    private Button btnQuayLaiDangNhap;
    @FXML
    private Label lblChangPS;
    
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
    }
}
