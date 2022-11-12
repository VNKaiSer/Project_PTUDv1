package bus;

import dal.DAL_BangChamCongNV;
import dto.DTO_BCCNhanVien;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_ChamCongNhanVien  {
    private DAL_BangChamCongNV dal_bangChamCongNV;

    public BUS_ChamCongNhanVien(){
        try {
            dal_bangChamCongNV = new DAL_BangChamCongNV();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<DTO_BCCNhanVien> getDSChamCongTheoNgay(String ngay){
        return dal_bangChamCongNV.getDsTheoNgay(ngay);
    }

    public void insertBCCNVToDatabase(ArrayList<DTO_BCCNhanVien> ds) throws SQLException {
        for (DTO_BCCNhanVien it:
             ds) {
            dal_bangChamCongNV.insertBCCNhanVien(it);
        }

    }
}
