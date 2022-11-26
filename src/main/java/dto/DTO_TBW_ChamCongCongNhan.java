package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DTO_TBW_ChamCongCongNhan {
    private final SimpleStringProperty maCongNhan;
    private final SimpleStringProperty tenCongNhan;
    private ComboBox<String> hienDien;
    private TextField soLuongSanPhamLamDuoc;
    private TextField ghiChu;

    public DTO_TBW_ChamCongCongNhan(String maCongNhan, String tenCongNhan, ComboBox<String> hienDien, TextField soLuongSanPhamLamDuoc, TextField ghiChuv) {
        this.maCongNhan = new SimpleStringProperty(maCongNhan);
        this.tenCongNhan = new SimpleStringProperty(tenCongNhan);
        this.hienDien = hienDien;
        this.soLuongSanPhamLamDuoc = soLuongSanPhamLamDuoc;
        this.ghiChu = ghiChuv;
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

    public TextField getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(TextField ghiChu) {
        this.ghiChu = ghiChu;
    }
}
