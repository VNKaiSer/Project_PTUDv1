package dal;

import db.ConnectDB;

import dto.DTO_CNDuocPhanCong;
import dto.DTO_CongDoan;
import dto.DTO_CongNhan;
import dto.DTO_SanPham;
import javafx.scene.control.CheckBox;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAL_CNDuocPhanCong {
    private  DAL_CongDoan dal_congDoan;
    private  DAL_CongNhan dal_congNhan;
    private  DAL_SanPham dal_sanPham;
    public DAL_CNDuocPhanCong(){
        dal_congNhan = new DAL_CongNhan();
        dal_congDoan = new DAL_CongDoan();
        dal_sanPham = new DAL_SanPham();
    }
    /**
     * Hàm trả về danh sách công nhân được phân công
     * ArrayList<DTO_CNDuocPhanCong>
     * @return DTO_CNDuocPhanCong
     */
    public ArrayList<DTO_CNDuocPhanCong> getDSCNDuocPhanCong() throws SQLException, ParseException {
        ArrayList<DTO_CNDuocPhanCong> ds = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM CNDuocPhanCong";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_CNDuocPhanCong tmp;
            DTO_CongNhan tmpcongNhan = findCongNhan(rs.getString(1));
            DTO_CongDoan tmpCongDoan = findCongDoan(rs.getString(2));
            DTO_SanPham tmpSanPham = findSanPham(rs.getString(3));
            int ca = rs.getInt(4);
            tmp = new DTO_CNDuocPhanCong(tmpcongNhan,tmpCongDoan,tmpSanPham,ca);
            ds.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return ds;
    }
    /**
     * Hàm get tìm kiếm một công nhân
     * @param maCN String
     * @return DTO_CongNhan
     */
    private DTO_CongNhan findCongNhan(String maCN) throws SQLException, ParseException {
        ArrayList<DTO_CongNhan> tmp = dal_congNhan.getDSCongNhan();
        for (DTO_CongNhan it:
                tmp) {
            if (it.getMaCongNhan().equals(maCN)){
                return it;
            }
        }
        return null;
    }
    /**
     * Hàm get tìm kiếm một công đoạn
     * @param maCD String
     * @return DTO_CongDoan
     */
    private DTO_CongDoan findCongDoan(String maCD) throws SQLException, ParseException {
        ArrayList<DTO_CongDoan> tmp = dal_congDoan.getDSCongDoan();
        for (DTO_CongDoan it:
                tmp) {
            if (it.getMaCongDoan().equals(maCD)){
                return it;
            }
        }
        return null;
    }
    /**
     * Hàm get tìm kiếm một sản phẩm
     * @param maSP String
     * @return DTO_SanPham
     */
    private DTO_SanPham findSanPham(String maSP) throws SQLException, ParseException {
        ArrayList<DTO_SanPham> tmp = dal_sanPham.getDSSanPham();
        for (DTO_SanPham it:
                tmp) {
            if (it.getMaSanPham().equals(maSP)){
                return it;
            }
        }
        return null;
    }
    public void insertCNDuocPhanCong(DTO_CNDuocPhanCong cnDuocPhanCong) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO CNDuocPhanCong VALUES(?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,cnDuocPhanCong.getCongNhan().getMaCongNhan());
        ppsm.setString(2,cnDuocPhanCong.getCongDoan().getMaCongDoan());
        ppsm.setString(3, cnDuocPhanCong.getSanPham().getMaSanPham());
        ppsm.setInt(4,cnDuocPhanCong.getCa());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm xóa một công nhân
     * @param maCN String
     *
     */
    public void deleteCongNhanDuocPhanCong(String maCN,String maCD,String maSP,String ca) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE CNDuocPhanCong WHERE maCongNhan = ? and maCongDoan = ? and maSanPham = ? and ca = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,maCN);
        ppsm.setString(2,maCD);
        ppsm.setString(3,maSP);
        ppsm.setString(4,ca);

        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    public ArrayList<DTO_CNDuocPhanCong> getCNTheoCa(String maSP, String maCD ,String c) throws SQLException {
        ArrayList<DTO_CNDuocPhanCong> ds = new ArrayList<DTO_CNDuocPhanCong>();
        ConnectDB.getInstance().connect();
        try {
            String sql = "select * from CNDuocPhanCong where (maSanPham = ? and maCongDoan = ? and ca = ?) ";
            PreparedStatement state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, maSP);
            state.setString(2, maCD);
            state.setString(3, c);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                DTO_CNDuocPhanCong tmp;
                DTO_CongNhan tmpcongNhan = findCongNhan(rs.getString(1));

                tmp = new DTO_CNDuocPhanCong(tmpcongNhan);
                ds.add(tmp);

            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        finally{
            try {
                ConnectDB.getConnection().close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return ds;
    }
    public ArrayList<DTO_CNDuocPhanCong> getCNTheoCongDoanvaSanPham(String maCD, String maSP) throws SQLException {
        ArrayList<DTO_CNDuocPhanCong> ds = new ArrayList<DTO_CNDuocPhanCong>();
        ConnectDB.getInstance().connect();
        try {
            String sql = "select * from CNDuocPhanCong where maCongDoan = ? and maSanPham = ?";
            PreparedStatement state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, maCD);
            state.setString(2, maSP);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                DTO_CNDuocPhanCong tmp;
                DTO_CongNhan tmpcongNhan = findCongNhan(rs.getString(1));

                tmp = new DTO_CNDuocPhanCong(tmpcongNhan);
                ds.add(tmp);

            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        finally{
            try {
                ConnectDB.getConnection().close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return ds;
    }
    public ArrayList<DTO_CNDuocPhanCong> getCNTheoSanPham(String maSP) throws SQLException {
        ArrayList<DTO_CNDuocPhanCong> ds = new ArrayList<DTO_CNDuocPhanCong>();
        ConnectDB.getInstance().connect();
        try {
            String sql = "select * from CNDuocPhanCong where maSanPham = ?";
            PreparedStatement state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, maSP);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                DTO_CNDuocPhanCong tmp;
                DTO_CongNhan tmpcongNhan = findCongNhan(rs.getString(1));
                DTO_CongDoan tmpCongDoan = findCongDoan(rs.getString(2));
                DTO_SanPham tmpSanPham = findSanPham(rs.getString(3));
                int ca = rs.getInt(4);
                tmp = new DTO_CNDuocPhanCong(tmpcongNhan,tmpCongDoan,tmpSanPham,ca);
                ds.add(tmp);

            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        finally{
            try {
                ConnectDB.getConnection().close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return ds;
    }
}
