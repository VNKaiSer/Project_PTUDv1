package gui;

import dto.DTO_SanPham;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CTL_GUI_DialogInforSP implements Initializable {

    @FXML
    private CheckBox cboHoanThanh;

    @FXML
    private ImageView imgv_anhSP;

    @FXML
    private TextField txtChatLieu;

    @FXML
    private TextField txtMaSP;

    @FXML
    private TextField txtSoCongDoan;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTenSanPham;

    @FXML
    private Button btnThoat;


    public void setData(DTO_SanPham sp) throws IOException {
        txtMaSP.setText(sp.getMaSanPham());
        txtTenSanPham.setText(sp.getTenSanPham());
        txtChatLieu.setText(sp.getChatLieu());
        txtSoLuong.setText(sp.getSoLuongYeuCau()+"");
        txtSoCongDoan.setText(sp.getSoCongDoan()+"");


        InputStream is = new ByteArrayInputStream(sp.getHinhAnh());
        Image image = new Image(is);
        imgv_anhSP.setImage(image);
    }

    public void closeDialog(Event event){
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnThoat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeDialog(event);
            }
        });
    }
}
