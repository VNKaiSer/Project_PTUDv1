package bus;

import dal.DAL_CNDuocPhanCong;
import dto.DTO_CNDuocPhanCong;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_CNDuocPhanCong {
    private DAL_CNDuocPhanCong dal_cnDuocPhanCong = new DAL_CNDuocPhanCong();
    public  void deleteCNDuocPhanCong(String maCN, String maCD, String maSP, String ca){
        try {
            dal_cnDuocPhanCong.deleteCongNhanDuocPhanCong(maCN,maCD,maSP,ca);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void insertCNDuocPhanCong(DTO_CNDuocPhanCong cn){
        try {
            dal_cnDuocPhanCong.insertCNDuocPhanCong(cn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCong() throws SQLException, ParseException {
        return dal_cnDuocPhanCong.getDSCNDuocPhanCong();
    }
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCongTheoCa(String maSP,String maCD , String ca) {
        try {
            return dal_cnDuocPhanCong.getCNTheoCa(maSP, maCD, ca);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCongTheoSanPham(String maSP) {
        try {
            return dal_cnDuocPhanCong.getCNTheoSanPham(maSP);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCongTheoCongDOanvaSanPham(String maCD,String maSP) {
        try {
            return dal_cnDuocPhanCong.getCNTheoCongDoanvaSanPham(maCD,maSP);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
