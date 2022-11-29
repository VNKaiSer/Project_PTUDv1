package bus;

import dal.DAL_CongNhan;
import dto.DTO_CongNhan;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_CongNhan {
    private DAL_CongNhan dal_congNhan;
    public BUS_CongNhan(){
        dal_congNhan = new DAL_CongNhan();
    }

    public ArrayList<DTO_CongNhan> getDSCongNhan() throws SQLException, ParseException {
        return dal_congNhan.getDSCongNhan();
    }

    public  void insertCongNhan(DTO_CongNhan cn) throws SQLException {
        dal_congNhan.insertCongNhan(cn);
    }

    public void deleteCongNhan(String maCN) throws SQLException {
        dal_congNhan.deleteCongNhan(maCN);
    }

    public void updateCongNhan(DTO_CongNhan cn) throws SQLException {
        dal_congNhan.updateCongNhan(cn);
    }
    public ArrayList<DTO_CongNhan> getDSCongNhanChuaDuocPhanCong(String maSP,String maCD, String ca) {
        try {
            return dal_congNhan.getDSCongNhanChuaDuocPhanCong(maSP,maCD,ca);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
