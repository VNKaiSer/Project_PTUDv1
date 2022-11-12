/**
 * 30/09/2022
 * Lớp Bảng Phân Công
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public class DTO_BangPhanCong {

    private DTO_CongDoan congDoan;
    private DTO_CongNhan congNhan;
    private Date ngayPhanCong;
    private Date ngayKetThuc;
    private Date ngayBatDau;
    private int ca;
    private DTO_SanPham SanPham;
    public DTO_BangPhanCong() {
        super();
        // TODO Auto-generated constructor stub
    }
    public DTO_BangPhanCong(DTO_CongDoan congDoan, DTO_CongNhan congNhan,
                            Date ngayPhanCong, Date ngayKetThuc, Date ngayBatDau, int ca,
                            DTO_SanPham sanPham) {
        super();
        this.congDoan = congDoan;
        this.congNhan = congNhan;
        this.ngayPhanCong = ngayPhanCong;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayBatDau = ngayBatDau;
        this.ca = ca;
        SanPham = sanPham;
    }
    public DTO_CongDoan getCongDoan() {
        return congDoan;
    }
    public void setCongDoan(DTO_CongDoan congDoan) {
        this.congDoan = congDoan;
    }
    public DTO_CongNhan getCongNhan() {
        return congNhan;
    }
    public void setCongNhan(DTO_CongNhan congNhan) {
        this.congNhan = congNhan;
    }
    public Date getNgayPhanCong() {
        return ngayPhanCong;
    }
    public void setNgayPhanCong(Date ngayPhanCong) {
        this.ngayPhanCong = ngayPhanCong;
    }
    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }
    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    public Date getNgayBatDau() {
        return ngayBatDau;
    }
    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    public int getCa() {
        return ca;
    }
    public void setCa(int ca) {
        this.ca = ca;
    }
    public DTO_SanPham getSanPham() {
        return SanPham;
    }
    public void setSanPham(DTO_SanPham sanPham) {
        SanPham = sanPham;
    }
    public SimpleStringProperty ngayBatDauProperty(){
        return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngayBatDau));
    }
    public SimpleStringProperty ngayKetThucProperty(){
        return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngayKetThuc));
    }
    public SimpleStringProperty maCongNhanProperty(){
        return new SimpleStringProperty(getCongNhan().getMaCongNhan());
    }
    public SimpleStringProperty tenCongNhanProperty(){
        return new SimpleStringProperty(getCongNhan().getTenCongNhan());
    }
    public SimpleStringProperty soLuongSPProperty(){
        return new SimpleStringProperty(String.valueOf(getSanPham().getSoLuongYeuCau()));
    }
    public SimpleStringProperty maCongDoanProperty(){
        return new SimpleStringProperty(getCongDoan().getMaCongDoan());
    }
    public SimpleStringProperty tenCongDoanProperty(){
        return new SimpleStringProperty(getCongDoan().getTenCongDoan());
    }
    public SimpleStringProperty caLamProperty(){
        String caLam;
        if (ca == 1)
            caLam = "Sáng";
        else if (ca == 2)
            caLam = "Chiều";
        else  {
            caLam = "Tối";
        }
        return new SimpleStringProperty(caLam);
    }
    @Override
    public String toString() {
        return "DTO_BangPhanCong [congDoan=" + congDoan + ", congNhan="
                + congNhan + ", ngayPhanCong=" + ngayPhanCong
                + ", ngayKetThuc=" + ngayKetThuc + ", ngayBatDau=" + ngayBatDau
                + ", ca=" + ca + ", SanPham=" + SanPham + "]";
    }






}
