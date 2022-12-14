package bus;

import dal.DAL_CongDoan;
import dto.DTO_CongDoan;

import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_CongDoan {
    private DAL_CongDoan dal_congDoan;

    public BUS_CongDoan() {
        dal_congDoan = new DAL_CongDoan();
    }

    public ArrayList<DTO_CongDoan> getAllCongDoan(){
        try {
            return  dal_congDoan.getDSCongDoan();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<DTO_CongDoan> getDSCongDoanTheoMaSP(String ma) throws SQLException, ParseException {
        return  dal_congDoan.getDSCongDoanTheoMaSP(ma);
    }


    public void insertCongDoan(DTO_CongDoan data) throws SQLException, ParseException {
        dal_congDoan.insertCongDoan(data);
    }

    public void removeCD(String ma) {
        try {
            dal_congDoan.deleteCongDoan(ma);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(DTO_CongDoan dt) throws SQLException {
        dal_congDoan.updateCongDoan(dt);
    }
}
