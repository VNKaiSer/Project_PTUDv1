/**
 * 05/10/2022
 * Lớp Phiếu lương nhân viên
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public class DTO_PhieuLuongNhanVien extends DTO_PhieuLuongCaNhan{
    private double luongCoBan;
    private DTO_NhanVien nhanVien;


    public DTO_PhieuLuongNhanVien(String maPhieuLuongNV, int soNgayCong, int nam, int thang, double tienThuong, double tienPhat, float thue, double tienTrachNhiem, double tienPhuCap, double tamUng, double tongLuong, double thucNhan, double luongCoBan, DTO_NhanVien nhanVien) {
        super(maPhieuLuongNV, soNgayCong, nam, thang, tienThuong, tienPhat, thue, tienTrachNhiem, tienPhuCap, tamUng, tongLuong, thucNhan);
        tinhTongLuong();
        thucLanh();
        this.luongCoBan = luongCoBan;
        this.nhanVien = nhanVien;
    }

    public DTO_PhieuLuongNhanVien(){
        super();
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public DTO_NhanVien getNhanVien() {
        return nhanVien;
    }

    public SimpleStringProperty tenNhanVienProperty(){
        return new SimpleStringProperty(nhanVien.getTenNhanVien());
    }

    public SimpleStringProperty chucVuProperty(){
        return new SimpleStringProperty("Nhân Viên");
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public void setNhanVien(DTO_NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }




    @Override
    public String toString() {
        return "DTO_PhieuLuongNhanVien{" +
                "luongCoBan=" + luongCoBan +
                ", nhanVien=" + nhanVien +
                '}';
    }

    @Override
    public void tinhTongLuong() {
        double tienTru = 0;
        if (soNgayCong < 26){
            tienTru = (( 26 - soNgayCong ) * luongCoBan )/ 26;
        }
        tongLuong = luongCoBan + tienThuong + tienPhuCap - tienPhat - tienTru;
    }

    @Override
    public void thucLanh() {
        thucNhan = tongLuong - tongLuong*thue - tamUng;
    }


}
