package gui;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassShow.setVisible(false);
        btnDangNhap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (dangNhap()){
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("MainMenu.fxml"));
                        /*
                         * if "fx:controller" is not set in fxml
                         * fxmlLoader.setController(NewWindowController);
                         */
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setTitle("Main Menu");
                        stage.setScene(scene);
                        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                        stage.show();
                    } catch (IOException e) {
                        Logger logger = Logger.getLogger(getClass().getName());
                        logger.log(Level.SEVERE, "Failed to create new Window.", e);
                    }
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

    }


    private boolean dangNhap() {
        boolean check = false;
        if (txtUser.getText().equals("admin") && txtPass.getText().equals("admin")) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "User or Pass Wrong", ButtonType.APPLY);
        alert.showAndWait();

        return false;
    }

}

