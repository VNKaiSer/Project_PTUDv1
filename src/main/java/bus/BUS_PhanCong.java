package bus;

import dal.DAL_BangPhanCong;
import dal.DAL_CNDuocPhanCong;
import dto.DTO_BangPhanCong;
import dto.DTO_CNDuocPhanCong;
import dto.DTO_CongNhan;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class BUS_PhanCong {
    private DAL_BangPhanCong dal_bangPhanCong = new DAL_BangPhanCong();
    public BUS_PhanCong(){
        dal_bangPhanCong = new DAL_BangPhanCong();
    }

    public ArrayList<DTO_BangPhanCong> getDSBPC() throws SQLException, ParseException {
        return dal_bangPhanCong.getDSBangPhanCong();
    }
    public ArrayList<DTO_BangPhanCong> getDSBPCtheoNgayPhanCong(String ngayPC,String maSP){
        try {
            return dal_bangPhanCong.getBPCTheoNgayPhanCong(ngayPC,maSP);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*public ArrayList<DTO_CongNhan> getMaCongNhan(Date ngayPhanCong, int ca, String maCongDoan) throws SQLException, ParseException {
        return dal_bangPhanCong.getMaCongNhan(ngayPhanCong, ca, maCongDoan);
    }*/
    public  void insertBPC(DTO_BangPhanCong bpc){
        try {
            dal_bangPhanCong.insertBangPhanCong(bpc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void deleteBPC(String ngayPhanCong) throws SQLException {
        dal_bangPhanCong.deleteBangPhanCong(ngayPhanCong);
    }
    public int checkPhanCong(String ma,String ngay){
        try {
            return dal_bangPhanCong.checkPhanCong(ma,ngay);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSoLuongPC(String maCN, String date, int ca) throws SQLException {
        return dal_bangPhanCong.laySoLuongSanPham(maCN, date, ca);
    }
    public int getSoLuong(String maSP){
        try {
            return dal_bangPhanCong.LaySoLuong(maSP);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getSoCD(String maSP){
        try {
            return dal_bangPhanCong.LaySoCongDoan(maSP);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getDSMaCongNhanTheoCa(String date, int ca) throws SQLException {
        return dal_bangPhanCong.getCongNhanTheoCa(date, ca);
    }

}
