/**
 * 05/10/2022
 * Lớp Bảng chấm công nhân viên
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 20077931_Trần Thanh Đại
 */
public class DTO_BCCNhanVien {
    private DTO_NhanVien nhanVien;
    private int hienDien;
    private Date ngayChamCong;
    private String maBCCNV;

    private String ghiChu;

    public DTO_BCCNhanVien() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DTO_BCCNhanVien(DTO_NhanVien nhanVien, int hienDien,
                           Date ngayChamCong, String maBCCNV, String ghiChu) {
        super();
        this.nhanVien = nhanVien;
        this.hienDien = hienDien;
        this.ngayChamCong = ngayChamCong;
        this.maBCCNV = maBCCNV;
        this.ghiChu = ghiChu;
    }

    public DTO_NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(DTO_NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public int getHienDien() {
        return hienDien;
    }

    public void setHienDien(int hienDien) {
        this.hienDien = hienDien;
    }

    public Date getNgayChamCong() {
        return ngayChamCong;
    }

    public void setNgayChamCong(Date ngayChamCong) {
        this.ngayChamCong = ngayChamCong;
    }

    public String getMaBCCNV() {
        return maBCCNV;
    }

    public void setMaBCCNV(String maBCCNV) {
        this.maBCCNV = maBCCNV;
    }

    public SimpleStringProperty maNhanVienProperty() {
        return new SimpleStringProperty(nhanVien.getMaNhanVien());
    }

    public SimpleStringProperty tenNhanVienProperty() {
        return new SimpleStringProperty(nhanVien.getTenNhanVien());
    }

    public SimpleStringProperty ngayChamCongProperty() {
        return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngayChamCong));
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maBCCNV == null) ? 0 : maBCCNV.hashCode());
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
        DTO_BCCNhanVien other = (DTO_BCCNhanVien) obj;
        if (maBCCNV == null) {
            if (other.maBCCNV != null)
                return false;
        } else if (!maBCCNV.equals(other.maBCCNV))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DTO_BCCNhanVien [nhanVien=" + nhanVien + ", hienDien="
                + hienDien + ", ngayChamCong=" + ngayChamCong + ", maBCCNV="
                + maBCCNV + "]";
    }


}
