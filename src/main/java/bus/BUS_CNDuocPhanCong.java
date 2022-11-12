package bus;

import dal.DAL_CNDuocPhanCong;
import dto.DTO_CNDuocPhanCong;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_CNDuocPhanCong {
    private DAL_CNDuocPhanCong dal_cnDuocPhanCong = new DAL_CNDuocPhanCong();
    public  void deleteCNDuocPhanCong(String maCN) throws SQLException {
        dal_cnDuocPhanCong.deleteCongNhanDuocPhanCong(maCN);
    }
    public  void insertCNDuocPhanCong(DTO_CNDuocPhanCong cn) throws SQLException {
        dal_cnDuocPhanCong.insertCNDuocPhanCong(cn);
    }
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCong() throws SQLException, ParseException {
        return dal_cnDuocPhanCong.getDSCNDuocPhanCong();
    }
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCongTheoCa(String maSP,String maCD , int ca) throws SQLException, ParseException {
        return dal_cnDuocPhanCong.getCNTheoCa(maSP, maCD, ca);
    }
}
