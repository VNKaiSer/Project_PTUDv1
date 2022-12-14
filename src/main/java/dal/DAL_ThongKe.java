package dal;

import db.ConnectDB;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DAL_ThongKe {
    public HashMap<Integer, Integer> thongKeTheoCaTheoNgay(String date ) throws SQLException {
        HashMap<Integer, Integer> dic = new HashMap<>();
        String sql = "SELECT   BangChamCongCN.hienDien\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    ngayChamCong = ? \n" +
                "GROUP BY BangChamCongCN.hienDien,BangChamCongCN.maCongNhan";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.merge(rs.getInt(1),rs.getInt(1),Integer::sum);
        }
        return dic;
    }
    public HashMap<Integer, Integer> thongKeTheoCaTheoTuan(String date ) throws SQLException {
        HashMap<Integer, Integer> dic = new HashMap<>();
        String sql = "DECLARE @tuan int \n" +
                "SET @tuan = datepart(week, ?);\n" +
                "SELECT   BangChamCongCN.hienDien\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    datepart(week, ngayChamCong) = @tuan\n" +
                "GROUP BY BangChamCongCN.hienDien,BangChamCongCN.maCongNhan";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.merge(rs.getInt(1),rs.getInt(1),Integer::sum);
        }
        return dic;
    }

    public static void main(String[] args) throws SQLException {
        HashMap<Integer, Integer> dic =  new DAL_ThongKe().thongKeTheoCaTheoNgay("2022-12-12");
        System.out.println(dic.get(1));
    }


    public HashMap<Integer, Integer> thongKeTheoCaTheoThang(String date) throws SQLException {
        HashMap<Integer, Integer> dic = new HashMap<>();
        String sql = "DECLARE @thang int \n" +
                "SET @thang = datepart(month, ?);\n" +
                "SELECT   BangChamCongCN.hienDien\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    datepart(month, ngayChamCong) = @thang\n" +
                "GROUP BY BangChamCongCN.hienDien,BangChamCongCN.maCongNhan";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()) {
            dic.merge(rs.getInt(1), rs.getInt(1), Integer::sum);
        }
        return dic;
    }

    public HashMap<Integer, Integer> getThongKeDiLamTheoKhoangTG(String date, String d2) throws SQLException {
        HashMap<Integer, Integer> dic = new HashMap<>();
        String sql = "SELECT   BangChamCongCN.hienDien\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    ngayChamCong between ? and ?\n" +
                "GROUP BY BangChamCongCN.hienDien,BangChamCongCN.maCongNhan";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ppsm.setString(2, d2);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.merge(rs.getInt(1),rs.getInt(1),Integer::sum);
        }
        return dic;
    }

    public HashMap<String, Integer> getTopCongNhanTheoNgay(String date) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "SELECT TOP(5)  CongNhan.tenCongNhan, sum(BangChamCongCN.soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan inner join CongNhan on BangPhanCong.maCongNhan = CongNhan.maCongNhan\n" +
                "WHERE    ngayChamCong = ?\n" +
                "GROUP BY BangChamCongCN.hienDien,CongNhan.tenCongNhan\n" +
                "ORDER BY sum(BangChamCongCN.soSanPhamLamDuoc) desc";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getTopCongNhanTheoTuan(String date) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "DECLARE @tuan int\n" +
                "SET @tuan = DATEPART(WEEK, ?)\n" +
                "SELECT TOP(5)  CongNhan.tenCongNhan, sum(BangChamCongCN.soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan inner join CongNhan on BangPhanCong.maCongNhan = CongNhan.maCongNhan\n" +
                "WHERE    DATEPART(WEEK,ngayChamCong) = @tuan\n" +
                "GROUP BY BangChamCongCN.hienDien,CongNhan.tenCongNhan\n" +
                "ORDER BY sum(BangChamCongCN.soSanPhamLamDuoc) desc";

        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getTopCongNhanTheoThang(String date) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "DECLARE @tuan int\n" +
                "SET @tuan = DATEPART(MONTH, ?)\n" +
                "SELECT TOP(5)  CongNhan.tenCongNhan, sum(BangChamCongCN.soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan inner join CongNhan on BangPhanCong.maCongNhan = CongNhan.maCongNhan\n" +
                "WHERE    DATEPART(MONTH,ngayChamCong) = @tuan\n" +
                "GROUP BY BangChamCongCN.hienDien,CongNhan.tenCongNhan\n" +
                "ORDER BY sum(BangChamCongCN.soSanPhamLamDuoc) desc";

        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getTopCongNhanTheoKhoang(String date, String date2) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "SELECT TOP(5)  CongNhan.tenCongNhan, sum(BangChamCongCN.soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan inner join CongNhan on BangPhanCong.maCongNhan = CongNhan.maCongNhan\n" +
                "WHERE    ngayChamCong between ? and ?\n" +
                "GROUP BY BangChamCongCN.hienDien,CongNhan.tenCongNhan\n" +
                "ORDER BY sum(BangChamCongCN.soSanPhamLamDuoc) desc";

        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ppsm.setString(2, date2);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getSanPhamTheoNgay(String date) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "SELECT   ca, sum(soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    ngayChamCong = ?\n" +
                "GROUP BY ca";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getSanPhamTheoTuan(String date) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "DECLARE @tuan int\n" +
                "SET @tuan = DATEPART(WEEK, ?)\n" +
                "SELECT   ca, sum(soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    DATEPART(WEEK, ngayChamCong) = @tuan\n" +
                "GROUP BY ca";

        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getSanTheoThang(String date) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "DECLARE @tuan int\n" +
                "SET @tuan = DATEPART(MONTH, ?)\n" +
                "SELECT   ca, sum(soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE    DATEPART(MONTH, ngayChamCong) = @tuan\n" +
                "GROUP BY ca";

        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }

    public HashMap<String, Integer> getSanPhamTheoKhoang(String date, String date2) throws SQLException {
        HashMap<String, Integer> dic = new HashMap<>();
        String sql = "SELECT TOP(5)  CongNhan.tenCongNhan, sum(BangChamCongCN.soSanPhamLamDuoc)\n" +
                "FROM     BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan inner join CongNhan on BangPhanCong.maCongNhan = CongNhan.maCongNhan\n" +
                "WHERE    ngayChamCong between ? and ?\n" +
                "GROUP BY BangChamCongCN.hienDien,CongNhan.tenCongNhan\n" +
                "ORDER BY sum(BangChamCongCN.soSanPhamLamDuoc) desc";

        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, date);
        ppsm.setString(2, date2);
        ResultSet rs = ppsm.executeQuery();
        while (rs.next()){
            dic.put(rs.getString(1),rs.getInt(2));
        }
        return dic;
    }
}
