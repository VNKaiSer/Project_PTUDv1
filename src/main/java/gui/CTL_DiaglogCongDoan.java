package gui;

import dto.DTO_CongDoan;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CTL_DiaglogCongDoan implements Initializable {
    private boolean choice;
    private DTO_CongDoan cd;
    // Hello tú :vv
    @FXML
    private TextField txtCongDoan;

    @FXML
    private TextField txtDonGia;

    /**
     * 1 là thêm 0 là sửa
     * @param choice
     * @param cd
     */
    public void setData(boolean choice, DTO_CongDoan cd){
        this.choice = choice;
        this.cd = cd;
        if (!choice){
            txtCongDoan.setText(cd.getTenCongDoan());
            txtDonGia.setText(cd.getDonGiaCongDoan()+"");
        }else {
            txtCongDoan.setDisable(false);
        }

    }

    public DTO_CongDoan getData(){
        if (!choice){
            cd.setDonGiaCongDoan(Double.parseDouble(txtDonGia.getText()));
        } else {
            cd = new DTO_CongDoan("",txtCongDoan.getText(), Double.parseDouble(txtDonGia.getText()));
        }
        return this.cd;
    }





    /**
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtDonGia.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtDonGia.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txtDonGia.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txtDonGia.setText(newValue);
            } else {
                txtDonGia.setText(newValue);
            }
        });
        if (choice){ // thêm
            txtCongDoan.setDisable(false);
        } else {// sửa
            txtCongDoan.setDisable(true);
        }
    }


}
