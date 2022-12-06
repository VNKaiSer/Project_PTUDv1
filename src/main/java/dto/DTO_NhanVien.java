/**
 * 05/10/2022
 * Lớp Nhân Viên
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 20077931_Trần Thanh Đại
 *
 */
public class DTO_NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private Date ngayVaoLam;
    private boolean phai;
    private Date ngaySinh;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private double luongCoBan;

    public DTO_NhanVien() {
        super();
        // TODO Auto-generated constructor stub
    }
    public DTO_NhanVien(String maNhanVien) {
        super();
        this.maNhanVien = maNhanVien;
    }

    public DTO_NhanVien(String maNhanVien, String tenNhanVien, Date ngayVaoLam,
                        boolean phai, Date ngaySinh, String soDienThoai, String email,
                        String diaChi, double luongCoBan) {
        super();
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngayVaoLam = ngayVaoLam;
        this.phai = phai;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.luongCoBan = luongCoBan;
    }
    public String getMaNhanVien() {
        return maNhanVien;
    }
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    public String getTenNhanVien() {
        return tenNhanVien;
    }
    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }
    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }
    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }
    public boolean isPhai() {
        return phai;
    }
    public void setPhai(boolean phai) {
        this.phai = phai;
    }
    public Date getNgaySinh() {
        return ngaySinh;
    }
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public double getLuongCoBan() {
        return luongCoBan;
    }
    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }
    public SimpleStringProperty gioiTinhProperty(){
        return phai ? new SimpleStringProperty("Nam") : new SimpleStringProperty("Nữ");
    }
    public  SimpleStringProperty ngayVaoLamProperty(){
        return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngayVaoLam));
    }

    public  SimpleStringProperty ngaySinhProperty(){
        return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngaySinh));
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((maNhanVien == null) ? 0 : maNhanVien.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DTO_NhanVien other = (DTO_NhanVien) obj;
        if (maNhanVien == null) {
            if (other.maNhanVien != null)
                return false;
        } else if (!maNhanVien.equals(other.maNhanVien))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "DTO_NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien="
                + tenNhanVien + ", ngayVaoLam=" + ngayVaoLam + ", phai=" + phai
                + ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai
                + ", email=" + email + ", diaChi=" + diaChi + ", luongCoBan="
                + luongCoBan ;
    }

}

