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


    public DTO_PhieuLuongNhanVien(String maPhieuLuongNV, int soNgayCong, int nam, int thang, double tienThuong, double tienPhat, float thue, double tienTrachNhiem, double tienPhuCap, double tamUng, double tongLuong, double thucNhan, double luongCoBan, DTO_NhanVien nhanVien, int vang, int phep) {
        super(maPhieuLuongNV, soNgayCong, nam, thang, tienThuong, tienPhat, thue, tienTrachNhiem, tienPhuCap, tamUng, tongLuong, thucNhan);
        this.nhanVien = nhanVien;
        this.luongCoBan = nhanVien.getLuongCoBan();
        this.vang = vang;
        this.phep = phep;
        tinhTongLuong();
        thucLanh();

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
    public void tinhTongLuong() {
        this.tongLuong = ( luongCoBan / 26 ) * soNgayCong + tienPhuCap;

    }

    @Override
    public void thucLanh() {
        if (soNgayCong >= 26){
            tienThuong = 200000;
            tienTrachNhiem = 300000;
        }
        tienPhat = (vang * (luongCoBan / 26) + (phep * (luongCoBan / 52)));

        thue = (float) (tongLuong * 0.08);
        thucNhan = tongLuong - thue - tamUng + tienThuong - tienPhat;
    }

    @Override
    public String toString() {
        return "DTO_PhieuLuongNhanVien{" +
                "luongCoBan=" + luongCoBan +
                ", nhanVien=" + nhanVien +
                ", maPhieuLuongNV='" + maPhieuLuong + '\'' +
                ", soNgayCong=" + soNgayCong +
                ", nam=" + nam +
                ", thang=" + thang +
                ", tienThuong=" + tienThuong +
                ", tienPhat=" + tienPhat +
                ", thue=" + thue +
                ", tienTrachNhiem=" + tienTrachNhiem +
                ", tienPhuCap=" + tienPhuCap +
                ", tamUng=" + tamUng +
                ", tongLuong=" + tongLuong +
                ", thucNhan=" + thucNhan +
                ", coMat=" + coMat +
                ", vang=" + vang +
                ", phep=" + phep +
                '}';
    }
}
