package dto;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author 20077931_Trần Thanh Đại
 *
 */
public class DTO_ChiTietCongDoan {
    private DTO_SanPham sanPham;
    private DTO_CongDoan congDoan;
    private String maCTCongDoan;
    public DTO_ChiTietCongDoan() {
        super();
        // TODO Auto-generated constructor stub
    }
    public DTO_ChiTietCongDoan(DTO_SanPham maSanPham, DTO_CongDoan maCongDoan,
                               String maCTCongDoan) {
        super();
        this.sanPham = maSanPham;
        this.congDoan = maCongDoan;
        this.maCTCongDoan = maCTCongDoan;
    }
    public DTO_SanPham getSanPham() {
        return sanPham;
    }
    public void setMaSanPham(DTO_SanPham maSanPham) {
        this.sanPham = maSanPham;
    }
    public DTO_CongDoan getCongDoan() {
        return congDoan;
    }
    public void setMaCongDoan(DTO_CongDoan maCongDoan) {
        this.congDoan = maCongDoan;
    }
    public String getMaCTCongDoan() {
        return maCTCongDoan;
    }
    public void setMaCTCongDoan(String maCTCongDoan) {
        this.maCTCongDoan = maCTCongDoan;
    }

    // Truy xuất dữ liệu trên table view
    public SimpleStringProperty maSanPhamProperty(){ return new SimpleStringProperty(sanPham.getMaSanPham());}
    public SimpleStringProperty tenSanPhamProperty(){ return new SimpleStringProperty(sanPham.getTenSanPham());}
    public SimpleStringProperty soLuongYeuCauProperty(){ return new SimpleStringProperty(sanPham.getSoLuongYeuCau()+"");}
    public SimpleStringProperty maCongDoanProperty(){return new SimpleStringProperty(congDoan.getMaCongDoan());}
    public SimpleStringProperty tenCongDoanProperty(){return new SimpleStringProperty(congDoan.getTenCongDoan());}
    public SimpleStringProperty donGiaCongDoanProperty(){return  congDoan.donGiaCongDoanProperty();}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((maCTCongDoan == null) ? 0 : maCTCongDoan.hashCode());
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
        DTO_ChiTietCongDoan other = (DTO_ChiTietCongDoan) obj;
        if (maCTCongDoan == null) {
            if (other.maCTCongDoan != null)
                return false;
        } else if (!maCTCongDoan.equals(other.maCTCongDoan))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "DTO_ChiTietCongDoan [maSanPham=" + sanPham + ", maCongDoan="
                + congDoan + ", maCTCongDoan=" + maCTCongDoan
                + ", thuTuCongDoan="  + "]";
    }
}
