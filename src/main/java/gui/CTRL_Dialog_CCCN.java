package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CTRL_Dialog_CCCN extends DialogPane implements Initializable {
    private ObservableList<Integer> luaChonChamCong = FXCollections.observableArrayList(0 ,1, 2);
    @FXML
    private Button btnHuy;

    @FXML
    private Button btnLuu;

    @FXML
    private ChoiceBox<Integer> cboLCCC;

    @FXML
    private TextArea txtChiChu;

    @FXML
    private TextField txtSoLuong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboLCCC.setItems(luaChonChamCong);
        btnHuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                checkLuu = false;
                stopDialog(actionEvent);
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                checkLuu = true;
                stopDialog(actionEvent);
            }
        });

        cboLCCC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cboLCCC.getValue() != 1){
                    txtSoLuong.setDisable(true);
                    return;
                }
                txtSoLuong.setDisable(false);
            }
        });

    }

    private void stopDialog(Event event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    public int getTrangThai(){
        return cboLCCC.getValue();
    }

    public String getGhiChu(){
        return txtChiChu.getText();
    }

    public boolean getCheckLuu(){
        return checkLuu;
    }

    public int getSoLuong(){
        return Integer.parseInt(txtSoLuong.getText());
    }

    public void setData(int tt, String gc, int soLuong){
        txtChiChu.setText(gc);
        cboLCCC.setValue(tt);
        txtSoLuong.setText(soLuong+"");
    }

    private boolean checkLuu = false;
}
