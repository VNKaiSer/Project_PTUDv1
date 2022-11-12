package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CTRL_Dialog_ThemNV implements Initializable {
    @FXML
    public ComboBox<String> cboGioiTinh;



    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboGioiTinh.setItems(listGioiTinh);

    }



}
