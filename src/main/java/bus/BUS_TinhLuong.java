package bus;

import dal.DAL_PhieuLuong;
import dto.DTO_PhieuLuongCaNhan;
import dto.DTO_ThongKeDiemDanh;
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

    public ArrayList<DTO_ThongKeDiemDanh> getDSDD(int thang, int nam) throws SQLException {
        return dal_phieuLuong.layDanhSachPhieuLuong(thang, nam);
    }

    public ArrayList<DTO_PhieuLuongCaNhan> sinhDanhSachPhieuLuong(int thang, int nam) throws SQLException {
        dal_phieuLuong.layDanhSachPhieuLuong(thang, nam);
        return  dal_phieuLuong.sinhDanhSachPhieuLuong(thang, nam);
    }

    public void saveDataBase(ObservableList<DTO_PhieuLuongCaNhan> list) throws SQLException {
        for (DTO_PhieuLuongCaNhan it:
             list) {
            dal_phieuLuong.saveToDataBase(it);
        }
    }

    public DTO_ThongKeDiemDanh getTKDD(Object obj, int thang, int nam) throws SQLException {
        return dal_phieuLuong.getTKDD(obj, thang, nam);
    }
}
