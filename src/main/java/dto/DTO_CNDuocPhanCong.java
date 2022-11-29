package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.util.Date;

public class DTO_CNDuocPhanCong {
    private DTO_CongNhan congNhan;
    private DTO_CongDoan congDoan;
    private DTO_SanPham sanPham;
    private int ca;
   // private CheckBox select;

    public DTO_CNDuocPhanCong() {
        super();
        // TODO Auto-generated constructor stub
    }
    public DTO_CNDuocPhanCong(DTO_CongNhan congNhan, DTO_CongDoan congDoan,
                              DTO_SanPham sanPham, int ca) {
        super();
        this.congNhan = congNhan;
        this.congDoan = congDoan;
        this.sanPham = sanPham;
        this.ca = ca;
    }
    public DTO_CNDuocPhanCong(DTO_CongNhan congNhan) {
        super();
        this.congNhan = congNhan;
    }

    /*public DTO_CNDuocPhanCong(DTO_CongNhan congNhan, CheckBox select) {
        // super();
        this.congNhan = congNhan;
        this.select = select;
    }*/
    /*public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }*/

    public DTO_CongNhan getCongNhan() {
        return congNhan;
    }
    public void setCongNhan(DTO_CongNhan congNhan) {
        this.congNhan = congNhan;
    }
    public DTO_CongDoan getCongDoan() {
        return congDoan;
    }
    public void setCongDoan(DTO_CongDoan congDoan) {
        this.congDoan = congDoan;
    }
    public DTO_SanPham getSanPham() {
        return sanPham;
    }
    public void setSanPham(DTO_SanPham sanPham) {
        this.sanPham = sanPham;
    }
    public int getCa() {
        return ca;
    }
    public void setCa(int ca) {
        this.ca = ca;
    }
    public SimpleStringProperty maCongNhanProperty(){
        return new SimpleStringProperty(getCongNhan().getMaCongNhan());
    }
    public SimpleStringProperty tenCongNhanProperty(){
        return new SimpleStringProperty(getCongNhan().getTenCongNhan());
    }
    @Override
    public String toString() {
        return "DTO_CNDuocPhanCong [congNhan=" + congNhan + ", congDoan="
                + congDoan + ", sanPham=" + sanPham + ", ca=" + ca +"]";
    }



}
