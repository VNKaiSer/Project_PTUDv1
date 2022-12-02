package dal;

import db.ConnectDB;
import dto.*;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class DAL_PhieuLuong {
    private  ArrayList<DTO_NhanVien> dsNV;
    private ArrayList<DTO_CongNhan> dsCN;

    public DAL_PhieuLuong() throws SQLException, ParseException {
        dsNV = new DAL_NhanVien().getDSNhanVien();
        dsCN = new DAL_CongNhan().getDSCongNhan();
    }

    /**
     * Hàm lấy danh sách phiếu lương của công nhân nhân viên theo
     *
     * @return ArrayList<DTO_PhieuLuongCaNhan>
     */
    public ArrayList<DTO_PhieuLuongCaNhan> getDanhSachPhieuLuongTheoThangNam(int thang, int nam) throws SQLException {
        ConnectDB.getInstance().connect();
        // lấy danh sánh phiếu lương của nhân viên

        ArrayList<DTO_PhieuLuongCaNhan> dsPL = new ArrayList<>();
        String sql = "SELECT * FROM PhieuLuongNhanVien \n" +
                "WHERE thang = ? and nam = ?";

        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setInt(1, thang);
        ppsm.setInt(2, nam);
        ResultSet rs = ppsm.executeQuery();

        while (rs.next()) {
            String maPL = rs.getString(1);
            int luongCoBan = rs.getInt(2);
            double tienThuong = rs.getDouble(3);
            double tienPhat = rs.getDouble(4);
            float thue = rs.getFloat(5);
            double tienTrachNhiem = rs.getDouble(6);
            double tienPhuCap = rs.getDouble(7);
            int soNgayCong = rs.getInt(8);
            double tamUng = rs.getDouble(9);
            double tongLuong = rs.getDouble(10);
            double thucNhan = rs.getDouble(11);
            int vang = rs.getInt(15);
            int phep = rs.getInt(16);
            DTO_NhanVien nhanVien = findNhanVien(rs.getString(14));
            DTO_PhieuLuongNhanVien tmp = new DTO_PhieuLuongNhanVien(maPL, soNgayCong, nam, thang, tienThuong, tienPhat, thue, tienTrachNhiem, tienPhuCap, tamUng, tongLuong, thucNhan, luongCoBan, nhanVien,vang, phep);
//            tmp.setPhep(phep);
//            tmp.setCoMat(soNgayCong);
//            tmp.setVang(vang);
            dsPL.add(tmp);

        }

        // lấy danh sách phiêếu lương của công nhân
        //begin
        sql = "SELECT * FROM PhieuLuongCongNhan \n" +
                "WHERE thang = ? and nam = ?";
        ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setInt(1, thang);
        ppsm.setInt(2, nam);
        rs = ppsm.executeQuery();

        while (rs.next()) {
            String maPL = rs.getString(1);
            int luongCoBan = rs.getInt(2);
            double tienThuong = rs.getDouble(3);
            double tienPhat = rs.getDouble(4);
            float thue = rs.getFloat(5);
            double tienTrachNhiem = rs.getDouble(6);
            double tienPhuCap = rs.getDouble(7);
            int soNgayCong = rs.getInt(8);
            double tamUng = rs.getDouble(9);
            double tongLuong = rs.getDouble(10);
            double thucNhan = rs.getDouble(11);
            DTO_CongNhan congNhan = findCongNhan(rs.getString(14));
            DTO_PhieuLuongCongNhan tmp = new DTO_PhieuLuongCongNhan(maPL, soNgayCong, nam, thang, tienThuong, tienPhat, thue, tienTrachNhiem, tienPhuCap, tamUng, tongLuong, thucNhan, luongCoBan, congNhan);
            dsPL.add(tmp);
        }


        //end
        ConnectDB.getConnection().close();
        return  dsPL;
    }

    private DTO_NhanVien findNhanVien(String maNhanVien) {
        for (DTO_NhanVien it: dsNV
             ) {
            if (it.getMaNhanVien().equals(maNhanVien)){
                return it;
            }
        }
        return  null;
    }

    private DTO_CongNhan findCongNhan(String maCN){
        for (DTO_CongNhan it: dsCN
        ) {
            if (it.getMaCongNhan().equals(maCN)){
                return it;
            }
        }
        return  null;
    }



    public void saveToDataBase(DTO_PhieuLuongCaNhan pl) throws SQLException {
        ConnectDB.getInstance().connect();
        if (pl instanceof  DTO_PhieuLuongNhanVien){
            String sql = "UPDATE PhieuLuongNhanVien\n" +
                    "SET tamUng = ?, tienPhat =? \n" +
                    "WHERE maPLNV = ?";
            PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
            ppsm.setDouble(1, pl.getTamUng());
            ppsm.setDouble(2, pl.getTienPhat());
            ppsm.setString(3, pl.getMaPhieuLuong());
            ppsm.execute();

        } else {
            String sql = "UPDATE PhieuLuongCongNhan\n" +
                    "SET tamUng = ?, tienPhat =? \n" +
                    "WHERE maPLCN = ?";
            PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
            ppsm.setDouble(1, pl.getTamUng());
            ppsm.setDouble(2, pl.getTienPhat());
            ppsm.setString(3, pl.getMaPhieuLuong());
            ppsm.execute();

            ppsm.execute();

        }
    }

    public boolean kiemTraChamCong(int thang, int nam) throws SQLException {
        ConnectDB.getInstance().connect();
        boolean check;
        String sql ="SELECT * FROM KiemTraChamCong\n" +
                    "WHERE thang = ? and nam = ? ";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setInt(1, thang);
        ppsm.setInt(2, nam);
        ResultSet rs = ppsm.executeQuery();
        rs.next();
        return rs.getBoolean(3);
    }




}
