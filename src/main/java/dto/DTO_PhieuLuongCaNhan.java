/**
 * 05/10/2022
 * Lớp Phiếu lương nhân viên
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;
import java.util.Objects;

/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public abstract class  DTO_PhieuLuongCaNhan{
    protected String maPhieuLuongNV;
    protected int soNgayCong;
    protected int nam;
    protected int thang;
    protected double tienThuong;
    protected double tienPhat;
    protected float thue;
    // đi làm đủ 26 ngày thưởng 100k
    protected double tienTrachNhiem;
    // mỗi ngày đi làm được phụ cấp 50k (tiền xăng xe)
    protected double tienPhuCap;
    protected double tamUng;
    protected double tongLuong;
    protected double thucNhan;

    public DTO_PhieuLuongCaNhan() {
    }

    public DTO_PhieuLuongCaNhan(String maPhieuLuongNV, int soNgayCong, int nam, int thang, double tienThuong, double tienPhat, float thue, double tienTrachNhiem, double tienPhuCap, double tamUng, double tongLuong, double thucNhan) {
        this.maPhieuLuongNV = maPhieuLuongNV;
        this.soNgayCong = soNgayCong;
        this.nam = nam;
        this.thang = thang;
        this.tienThuong = tienThuong;
        this.tienPhat = tienPhat;
        this.thue = thue;
        this.tienTrachNhiem = tienTrachNhiem;
        this.tienPhuCap = tienPhuCap;
        this.tamUng = tamUng;
        this.tongLuong = tongLuong;
        this.thucNhan = thucNhan;
    }

    public void setMaPhieuLuongNV(String maPhieuLuongNV) {
        this.maPhieuLuongNV = maPhieuLuongNV;
    }

    public void setSoNgayCong(int soNgayCong) {
        this.soNgayCong = soNgayCong;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public void setTienThuong(double tienThuong) {
        this.tienThuong = tienThuong;
    }

    public void setTienPhat(double tienPhat) {
        this.tienPhat = tienPhat;
    }

    public void setThue(float thue) {
        this.thue = thue;
    }

    public void setTienTrachNhiem(double tienTrachNhiem) {
        this.tienTrachNhiem = tienTrachNhiem;
    }

    public void setTienPhuCap(double tienPhuCap) {
        this.tienPhuCap = tienPhuCap;
    }

    public void setTamUng(double tamUng) {
        this.tamUng = tamUng;
    }

    public void setTongLuong(double tongLuong) {
        this.tongLuong = tongLuong;
    }

    public void setThucNhan(double thucNhan) {
        this.thucNhan = thucNhan;
    }

    public String getMaPhieuLuongNV() {
        return maPhieuLuongNV;
    }

    public int getSoNgayCong() {
        return soNgayCong;
    }

    public int getNam() {
        return nam;
    }

    public int getThang() {
        return thang;
    }

    public double getTienThuong() {
        return tienThuong;
    }

    public double getTienPhat() {
        return tienPhat;
    }

    public float getThue() {
        return thue;
    }

    public double getTienTrachNhiem() {
        return tienTrachNhiem;
    }

    public double getTienPhuCap() {
        return tienPhuCap;
    }

    public double getTamUng() {
        return tamUng;
    }

    public double getTongLuong() {
        return tongLuong;
    }

    public double getThucNhan() {
        return thucNhan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DTO_PhieuLuongNhanVien that)) return false;
        return getMaPhieuLuongNV().equals(that.getMaPhieuLuongNV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaPhieuLuongNV());
    }

    @Override
    public String toString() {
        return "DTO_PhieuLuongNhanVien{" +
                "maPhieuLuongNV='" + maPhieuLuongNV + '\'' +
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
                '}';
    }

    public abstract void tinhTongLuong();
    public abstract void thucLanh();

    class DTO_ThongKeDiemDanh {
        private Object caNhan;
        private int coMat;
        private int vang;
        private int coPhep;

        public DTO_ThongKeDiemDanh() {
        }

        public DTO_ThongKeDiemDanh(Object caNhan, int coMat, int vang, int coPhep) {
            this.caNhan = caNhan;
            this.coMat = coMat;
            this.vang = vang;
            this.coPhep = coPhep;
        }

        public Object getCaNhan() {
            return caNhan;
        }

        public int getCoMat() {
            return coMat;
        }

        public int getVang() {
            return vang;
        }

        public int getCoPhep() {
            return coPhep;
        }

        public void setCaNhan(Object caNhan) {
            this.caNhan = caNhan;
        }

        public void setCoMat(int coMat) {
            this.coMat = coMat;
        }

        public void setVang(int vang) {
            this.vang = vang;
        }

        public void setCoPhep(int coPhep) {
            this.coPhep = coPhep;
        }
    }


}
