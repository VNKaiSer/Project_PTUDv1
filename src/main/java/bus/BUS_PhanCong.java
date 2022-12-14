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
    public ArrayList<DTO_BangPhanCong> getDSBPCtheoNgayPhanCong(String ngayPC) throws SQLException, ParseException {
        return dal_bangPhanCong.getBPCTheoNgayPhanCong(ngayPC);
    }
    /*public ArrayList<DTO_CongNhan> getMaCongNhan(Date ngayPhanCong, int ca, String maCongDoan) throws SQLException, ParseException {
        return dal_bangPhanCong.getMaCongNhan(ngayPhanCong, ca, maCongDoan);
    }*/
    public  void insertBPC(DTO_BangPhanCong bpc) throws SQLException {
        dal_bangPhanCong.insertBangPhanCong(bpc);
    }
    public  void deleteBPC(String ngayPhanCong) throws SQLException {
        dal_bangPhanCong.deleteBangPhanCong(ngayPhanCong);
    }

}
