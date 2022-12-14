package bus;

import dal.DAL_ThongKe;

import java.sql.SQLException;
import java.util.HashMap;

public class BUS_ThongKe {
    private DAL_ThongKe dal_thongKe;
    public BUS_ThongKe(){
        dal_thongKe = new DAL_ThongKe();
    }
    public HashMap<Integer, Integer> getThongKeDiLamTheoNgay(String date) throws SQLException {
        return dal_thongKe.thongKeTheoCaTheoNgay(date);
    }

    public HashMap<Integer, Integer> getThongKeDiLamTheoTuan(String date) throws SQLException {
        return dal_thongKe.thongKeTheoCaTheoTuan(date);
    }

    public HashMap<Integer, Integer> getThongKeDiLamTheoThang(String date) throws SQLException {
        return dal_thongKe.thongKeTheoCaTheoThang(date);
    }

    public HashMap<Integer, Integer> getThongKeDiLamTheoKhoangTG(String s1, String s2) throws SQLException {
        return dal_thongKe.getThongKeDiLamTheoKhoangTG(s1, s2);
    }

    public HashMap<String, Integer> getTopCongNhan(String date) throws SQLException {
        return  dal_thongKe.getTopCongNhanTheoNgay(date);
    }


    public HashMap<String, Integer> getTopCongNhanTuan(String date) throws SQLException {
        return dal_thongKe.getTopCongNhanTheoTuan(date);
    }

    public HashMap<String, Integer> getTopCongNhanThang(String date) throws SQLException {
        return dal_thongKe.getTopCongNhanTheoThang(date);
    }

    public HashMap<String, Integer> getTopCongNhanKhoang(String date, String date2) throws SQLException {
        return dal_thongKe.getTopCongNhanTheoKhoang(date, date2);
    }
}
