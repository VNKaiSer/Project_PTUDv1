package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DTO_TBW_ChamCongNhanVien {
    private final SimpleStringProperty maNhanVien;
    private final SimpleStringProperty hoTen;
    private ComboBox<String> hienDien;
    private final TextField ghiChu;

    public DTO_TBW_ChamCongNhanVien(String maNhanVien, String hoTen, ComboBox<String> hienDien, TextField ghiChu) {
        this.maNhanVien = new SimpleStringProperty(maNhanVien);
        this.hoTen = new SimpleStringProperty(hoTen);
        this.hienDien = hienDien;
        this.ghiChu = ghiChu;
    }

    public String getMaNhanVien() {
        return maNhanVien.get();
    }

    public SimpleStringProperty maNhanVienProperty() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien.set(maNhanVien);
    }

    public String getHoTen() {
        return hoTen.get();
    }

    public SimpleStringProperty hoTenProperty() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen.set(hoTen);
    }

    public ComboBox<String> getHienDien() {
        return hienDien;
    }

    public void setHienDien(ComboBox<String> hienDien) {
        this.hienDien = hienDien;
    }

    public TextField getGhiChu() {
        return ghiChu;
    }
}
