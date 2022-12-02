package bus;

import dal.DAL_PhieuLuong;
import dto.DTO_PhieuLuongCaNhan;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_TinhLuong {
    DAL_PhieuLuong dal_phieuLuong;
    public BUS_TinhLuong() throws SQLException, ParseException {
        dal_phieuLuong = new DAL_PhieuLuong();
    }

    public ArrayList<DTO_PhieuLuongCaNhan> getDSPL(int thang, int nam) throws SQLException {
        return dal_phieuLuong.getDanhSachPhieuLuongTheoThangNam(thang, nam);
    }

    public boolean kiemTraChamCong(int thang, int nam) throws SQLException {
        return dal_phieuLuong.kiemTraChamCong(thang, nam);
    }

    public void saveDataBase(DTO_PhieuLuongCaNhan items) throws SQLException {
            dal_phieuLuong.saveToDataBase(items);
        }

}
