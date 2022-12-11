package bus;

import dal.DAL_BangChamCongCN;
import dto.DTO_BCCCongNhan;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_ChamCongCN {
    private DAL_BangChamCongCN dal_bangChamCongCN;

    public BUS_ChamCongCN() throws SQLException, ParseException {
        dal_bangChamCongCN = new DAL_BangChamCongCN();
    }

    public ArrayList<DTO_BCCCongNhan> getDSBCCN() throws SQLException, ParseException {
        return dal_bangChamCongCN.getDSBangCCCN();
    }

    public ArrayList<DTO_BCCCongNhan> getDSBCCNTheoNgay(String ngay) throws SQLException, ParseException {
        return dal_bangChamCongCN.getDsTheoNgay(ngay);
    }


    public void insertBCCNVToDatabase(ArrayList<DTO_BCCCongNhan> dsBCCHienTai) throws SQLException {
        for (DTO_BCCCongNhan it :
                dsBCCHienTai) {
            dal_bangChamCongCN.insertBCCNhanVien(it);
        }
    }
}
