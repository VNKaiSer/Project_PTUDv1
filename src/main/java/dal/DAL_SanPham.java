package dal;

import db.ConnectDB;
import dto.DTO_SanPham;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author 20077931_Trần Thanh Đại
 */
public class DAL_SanPham {

    public DAL_SanPham(){

    }
    /**
     * Hàm get danh sách sản phẩm
     * @return Arraylist<DTO_SanPham>
     */
    public ArrayList<DTO_SanPham> getDSSanPham() throws SQLException, ParseException {
        // tạo kết nối
        ArrayList<DTO_SanPham> dsSanPham = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM SanPham";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_SanPham tmp;
            String maSP = rs.getString(1);
            String tenSP = rs.getString(2);
            int soCongDoan = rs.getInt(3);
            int soLuong = rs.getInt(4);
            String chatLieu = rs.getString(5);
            byte [] hinhAnh = rs.getBytes(7);

            tmp = new DTO_SanPham(maSP,tenSP,soCongDoan,soLuong,chatLieu);
            tmp.setHinhAnh(hinhAnh);
            dsSanPham.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsSanPham;
    }
    /**
     * Hàm thêm một sản phẩm vào database
     * @param sanPham DTO_SanPham
     *
     */
    public  void insertSanPham(DTO_SanPham sanPham) throws SQLException, FileNotFoundException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO SanPham VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, sanPham.getMaSanPham());
        ppsm.setString(2, sanPham.getTenSanPham());
        ppsm.setInt(3, sanPham.getSoCongDoan());
        ppsm.setInt(4, sanPham.getSoLuongYeuCau());
        ppsm.setString(5, sanPham.getChatLieu());
        ppsm.setBoolean(6, sanPham.isTrangThai());

        ppsm.setBytes(7, sanPham.getHinhAnh());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm xóa một sản phẩm
     * @param maSP String
     *
     */
    public void deleteSanPham(String maSP) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE SanPham WHERE maSanPham = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,maSP);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm cập nhật thông tin một sản phẩm
     * @param sanPham DTO_SanPham
     *
     */
    public void updateSanPham(DTO_SanPham sanPham) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql ="UPDATE SanPham " +
                "SET tenSanPham = ?, soCongDoan = ?, soLuong = ?, chatLieu = ? " +
                "WHERE maSanPham = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,sanPham.getTenSanPham());
        ppsm.setInt(2, sanPham.getSoCongDoan());
        ppsm.setInt(3, sanPham.getSoLuongYeuCau());
        ppsm.setString(4, sanPham.getChatLieu());
        ppsm.setString(5,sanPham.getMaSanPham());

        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
}
