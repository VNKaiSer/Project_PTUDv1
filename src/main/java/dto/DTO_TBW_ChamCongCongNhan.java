package dto;

import bus.BUS_PhanCong;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DTO_TBW_ChamCongCongNhan {
    private final SimpleStringProperty maCongNhan;
    private final SimpleStringProperty tenCongNhan;
    private ComboBox<String> hienDien;
    private TextField soLuongSanPhamLamDuoc;
    private TextField ghiChu;
    private int soSanPhamDuocPhanCong;
    private int soLuongNumber;

    public DTO_TBW_ChamCongCongNhan(String maCongNhan, String tenCongNhan, ComboBox<String> hienDien, TextField soLuongSanPhamLamDuoc, TextField ghiChuv, int soSanPhamDuocPhanCong) {
        this.maCongNhan = new SimpleStringProperty(maCongNhan);
        this.tenCongNhan = new SimpleStringProperty(tenCongNhan);
        this.hienDien = hienDien;
        this.soLuongSanPhamLamDuoc = soLuongSanPhamLamDuoc;
        this.ghiChu = ghiChuv;
        this.soSanPhamDuocPhanCong = soSanPhamDuocPhanCong;
        setConstain();
    }

    public String getMaCongNhan() {
        return maCongNhan.get();
    }

    public SimpleStringProperty maCongNhanProperty() {
        return maCongNhan;
    }

    public void setMaCongNhan(String maCongNhan) {
        this.maCongNhan.set(maCongNhan);
    }

    public String getTenCongNhan() {
        return tenCongNhan.get();
    }

    public SimpleStringProperty tenCongNhanProperty() {
        return tenCongNhan;
    }

    public void setTenCongNhan(String tenCongNhan) {
        this.tenCongNhan.set(tenCongNhan);
    }

    public ComboBox<String> getHienDien() {
        return hienDien;
    }

    public void setHienDien(ComboBox<String> hienDien) {
        this.hienDien = hienDien;
    }



    public TextField getSoLuongSanPhamLamDuoc() {
        return soLuongSanPhamLamDuoc;
    }

    public void setSoLuongSanPhamLamDuoc(TextField soLuongSanPhamLamDuoc) {
        this.soLuongSanPhamLamDuoc = soLuongSanPhamLamDuoc;
    }

    public int getSoSanPhamDuocPhanCong() {
        return soSanPhamDuocPhanCong;
    }

    public void setSoSanPhamDuocPhanCong(int soSanPhamDuocPhanCong) {
        this.soSanPhamDuocPhanCong = soSanPhamDuocPhanCong;
    }

    public TextField getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(TextField ghiChu) {
        this.ghiChu = ghiChu;
    }

    private void setConstain(){

        soLuongSanPhamLamDuoc.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                soLuongSanPhamLamDuoc.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(soLuongSanPhamLamDuoc.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                soLuongSanPhamLamDuoc.setText(newValue);
            } else {
                soLuongSanPhamLamDuoc.setText(newValue);
            }
        });

        hienDien.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hienDien.getSelectionModel().getSelectedIndex() == 0 || hienDien.getSelectionModel().getSelectedIndex() == 2){
                    soLuongSanPhamLamDuoc.setText("0");
                    soLuongSanPhamLamDuoc.setDisable(true);
                } else {
                    soLuongSanPhamLamDuoc.setText(soSanPhamDuocPhanCong+"");
                    soLuongSanPhamLamDuoc.setDisable(false);
                }
            }
        });

        // Kiểm tra khi người dùng rời khỏi ô sản phẩm có nhập đúng hay không
        soLuongSanPhamLamDuoc.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    if (Integer.parseInt(soLuongSanPhamLamDuoc.getText()) > soSanPhamDuocPhanCong + 5){
                        Alert a = new Alert(Alert.AlertType.ERROR, "Số sản phẩm quá lớn.\n Lượng sản phẩm của nhân viên phải từ [0,"+(soSanPhamDuocPhanCong+5)+"]", ButtonType.CANCEL);
                        a.showAndWait();
                        soLuongSanPhamLamDuoc.requestFocus();
                        return;
                    }

                }

            }
        });

        soLuongSanPhamLamDuoc.setText(soSanPhamDuocPhanCong+"");
        ghiChu.setText("");
    }
}
