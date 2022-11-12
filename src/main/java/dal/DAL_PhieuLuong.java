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
            DTO_NhanVien nhanVien = findNhanVien(rs.getString(14));
            DTO_PhieuLuongNhanVien tmp = new DTO_PhieuLuongNhanVien(maPL, soNgayCong, nam, thang, tienThuong, tienPhat, thue, tienTrachNhiem, tienPhuCap, tamUng, tongLuong, thucNhan, luongCoBan, nhanVien);
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

    private ArrayList<DTO_ThongKeDiemDanh> dsTK;
    public ArrayList<DTO_ThongKeDiemDanh> layDanhSachPhieuLuong(int thang, int nam) throws SQLException {
        ConnectDB.getInstance().connect();
        // lấy danh sách điểm danh nhân viên
        String sql = "SELECT * FROM tinhLuongNV(?, ?)";
        dsTK = new ArrayList<>();
        CallableStatement ctsm = ConnectDB.getConnection().prepareCall(sql);
        ctsm.setInt(1, thang);
        ctsm.setInt(2, nam);
        ResultSet rs = ctsm.executeQuery();
        while(rs.next()){
           DTO_NhanVien nv = findNhanVien(rs.getString(1));
           int coMat = rs.getInt(2);
           int phep = rs.getInt(3);
           int vang = rs.getInt(4);
           DTO_ThongKeDiemDanh tmp = new DTO_ThongKeDiemDanh(nv, coMat, phep, vang);

           dsTK.add(tmp);
        }

        // của thằng công nhân
        //begin
        //ConnectDB.getInstance().connect();
        String sqlCN = "SELECT * FROM tinhLuongCN(?, ?)";
        CallableStatement ctsm1 = ConnectDB.getConnection().prepareCall(sqlCN);
        ctsm1.setInt(1, thang);
        ctsm1.setInt(2, nam);
        ResultSet rs1 = ctsm1.executeQuery();
        while(rs1.next()){
            DTO_CongNhan cn = findCongNhan(rs1.getString(1));
            int coMat = rs1.getInt(2);
            int phep = rs1.getInt(3);
            int vang = rs1.getInt(4);
            DTO_ThongKeDiemDanh tmp = new DTO_ThongKeDiemDanh(cn, coMat, phep, vang);

            dsTK.add(tmp);
        }

        //end
        ConnectDB.getInstance().disConnect();
        return dsTK;
    }

    public ArrayList<DTO_PhieuLuongCaNhan> sinhDanhSachPhieuLuong(int thang, int nam) throws SQLException {
        ArrayList<DTO_PhieuLuongCaNhan> dsPLC = new ArrayList<>();
        // tạo phiếu lương cho nhân viên
        for (DTO_NhanVien it:
             dsNV) {
            String maPL = "PL" + it.getMaNhanVien() + thang + nam;
            int soCong =  tinhSoNgayCong(it)[1];
            double tienThuong = 0;
            double tienPhat = 0;
            float thue = (float) 0.08;
            double tienTN = tinhSoNgayCong(it)[1] >= 26 ? 100000 : 0;
            double tienPC = tinhSoNgayCong(it)[1]* 50000;
            double tamUng = 0;
            double tongLuong = 0;
            double thucNhan = 0;
            double luongCB = it.getLuongCoBan();
            DTO_PhieuLuongCaNhan pl = new DTO_PhieuLuongNhanVien(maPL, soCong, nam, thang, tienThuong, tienPhat, thue, tienTN, tienPC, tamUng, tongLuong, thucNhan, luongCB, it);
            dsPLC.add(pl);

        }
        for (DTO_CongNhan it:
             dsCN) {
            String maPL = "PL" + it.getMaCongNhan() + thang + nam;
            int soCong =  tinhSoNgayCong(it)[1];
            double tienThuong = 0;
            double tienPhat = 0;
            float thue = (float) 0.08;
            double tienTN = tinhSoNgayCong(it)[1] >= 26 ? 100000 : 0;
            double tienPC = tinhSoNgayCong(it)[1]* 50000;
            double tamUng = 0;
            double tongLuong = 0;
            double thucNhan = 0;
            int soSP = tinhSoSP(thang, nam, it);
            DTO_PhieuLuongCaNhan pl = new DTO_PhieuLuongCongNhan(maPL, soCong, nam, thang, tienThuong, tienPhat, thue, tienTN, tienPC, tamUng, tongLuong, thucNhan, soSP, it);
            dsPLC.add(pl);
        }

        return dsPLC;

    }

    /**
     * Hàm trả về số ngày nghỉ có mặt hay phép của nhân viên
     * @param obj
     * @return
     */
    private int[] tinhSoNgayCong(Object obj){

        int co = 0;
        int vang = 0;
        int phep = 0;
        for (DTO_ThongKeDiemDanh it:
             dsTK) {
            if (it.getCaNhan().equals(obj)){
                co += it.getCoMat();
                vang += it.getCoMat();
                phep += it.getCoPhep();
            }
        }
        return new int[]{co, vang, phep};
    }

    /**
     * Hàm lấy số sản phẩm của một nhân viên
     * @param thang
     * @param nam
     * @param obj
     * @return
     * @throws SQLException
     */
    private int tinhSoSP(int thang, int nam, DTO_CongNhan obj) throws SQLException {
        String sql = "{?= call dbo.soSanPhamCua1CongNhan(?,?,?)}";
        ConnectDB.getInstance().connect();
        CallableStatement cstm = ConnectDB.getConnection().prepareCall(sql);
        cstm.setInt(2, thang);
        cstm.setInt(3, nam);
        cstm.setString(4, obj.getMaCongNhan());
        cstm.registerOutParameter(1, Types.INTEGER);
        cstm.execute();
        return  cstm.getInt(1);
    }

    public void saveToDataBase(DTO_PhieuLuongCaNhan pl) throws SQLException {
        ConnectDB.getInstance().connect();
        if (pl instanceof  DTO_PhieuLuongNhanVien){
            String sql = "INSERT INTO PhieuLuongNhanVien\n" +
                         "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
            ppsm.setString(1, pl.getMaPhieuLuongNV());
            ppsm.setDouble(2, ((DTO_PhieuLuongNhanVien) pl).getLuongCoBan());
            ppsm.setDouble(3, pl.getTienThuong());
            ppsm.setDouble(4, pl.getTienPhat());
            ppsm.setDouble(5, pl.getThue());
            ppsm.setDouble(6, pl.getTienTrachNhiem());
            ppsm.setDouble(7, pl.getTienPhuCap());
            ppsm.setInt(8, pl.getSoNgayCong());
            ppsm.setDouble(9, pl.getTamUng());
            ppsm.setDouble(10, pl.getTongLuong());
            ppsm.setDouble(11, pl.getThucNhan());
            ppsm.setInt(12, pl.getThang());
            ppsm.setInt(13, pl.getNam());
            ppsm.setString(14, ((DTO_PhieuLuongNhanVien) pl).getNhanVien().getMaNhanVien());

            ppsm.execute();

        } else {
            String sql = "INSERT INTO PhieuLuongCongNhan\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
            ppsm.setString(1, pl.getMaPhieuLuongNV());
            ppsm.setInt(2, ((DTO_PhieuLuongCongNhan) pl).getTongSoSanPham());
            ppsm.setDouble(3, pl.getTienThuong());
            ppsm.setDouble(4, pl.getTienPhat());
            ppsm.setDouble(5, pl.getThue());
            ppsm.setDouble(6, pl.getTienTrachNhiem());
            ppsm.setDouble(7, pl.getTienPhuCap());
            ppsm.setInt(8, pl.getSoNgayCong());
            ppsm.setDouble(9, pl.getTamUng());
            ppsm.setDouble(10, pl.getTongLuong());
            ppsm.setDouble(11, pl.getThucNhan());
            ppsm.setInt(12, pl.getThang());
            ppsm.setInt(13, pl.getNam());
            ppsm.setString(14, ((DTO_PhieuLuongCongNhan) pl).getCongNhan().getMaCongNhan());

            ppsm.execute();

        }
    }

    public DTO_ThongKeDiemDanh getTKDD(Object obj, int thang, int nam) throws SQLException {
        System.out.println(layDanhSachPhieuLuong(thang, nam));
        for (DTO_ThongKeDiemDanh it:
                dsTK) {
            if (it.getCaNhan().equals(obj)){
                return  it;
            }
        }
        return null;
    }


}
