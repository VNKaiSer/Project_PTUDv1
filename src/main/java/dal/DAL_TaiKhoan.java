package dal;
/**
 *
 * @author 20077931_Trần Thanh Đại
 */
import db.ConnectDB;
import dto.DTO_TaiKhoan;

import java.sql.*;

public class DAL_TaiKhoan {
    public DAL_TaiKhoan(){

    }
    /**
     * Hàm thêm một tài khoản
     * @param taiKhoan DTO_TaiKhoan
     *
     */
    public  void insertTaiKhoan(DTO_TaiKhoan taiKhoan) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO TaiKhoan VALUES(?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,taiKhoan.getTenDangNhap());
        ppsm.setString(2,taiKhoan.getMatKhau());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm xóa một tài khoản
     * @param tenDangNhap String
     *
     */
    public void deleteTaiKhoan(String tenDangNhap) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE TaiKhoan WHERE tenDangNhap = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,tenDangNhap);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm cập nhật tài khoản
     * @param taiKhoan DTO_TaiKhoan
     *
     */
    public void updateTaiKhoan(DTO_TaiKhoan taiKhoan) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "UPDATE TaiKhoan " +
                     "SET matKhau = ?  " +
                     "WHERE tenDangNhap = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);


        ppsm.setString(1,taiKhoan.getMatKhau());
        ppsm.setString(2,taiKhoan.getTenDangNhap());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm đăng nhập
     * @param user
     * @param pass
     * @return
     * @throws SQLException
     */
    public int dangNhap(String user, String pass) throws SQLException {
        String sql = "{?= call dangNhap(?,?)}";
        ConnectDB.getInstance().connect();
        CallableStatement cstm = ConnectDB.getConnection().prepareCall(sql);
        cstm.setString(2, user);
        cstm.setString(3, pass);
        cstm.registerOutParameter(1, Types.INTEGER);
        cstm.execute();
        return  cstm.getInt(1);
    }

    /**
     * Hàm lấy tên của nhân viên đăng nhập
     * @param name
     * @return
     * @throws SQLException
     */
    public String getName(String name) throws SQLException {
        String sql = "SELECT        tenNhanVien\n" +
                     "FROM          NhanVien\n" +
                     "WHERE maNhanVien = ?";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, name);
        ResultSet rs = ppsm.executeQuery();
        if (rs.next())
            return rs.getString(1);
        else
            return "";

    }

    public String getSDT(String name) throws SQLException {
        String sql = "SELECT        SDT\n" +
                     "FROM          NhanVien\n" +
                     "WHERE maNhanVien = ?";
        ConnectDB.getInstance().connect();
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, name);
        ResultSet rs = ppsm.executeQuery();
        rs.next();
        return rs.getString(1);
    }


}
