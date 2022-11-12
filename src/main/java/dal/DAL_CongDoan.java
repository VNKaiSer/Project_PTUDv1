package dal;

import db.ConnectDB;
import dto.DTO_BCCNhanVien;
import dto.DTO_CongDoan;
import dto.DTO_NhanVien;
import dto.DTO_SanPham;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 20077931_Trần Thanh Đại
 */
public class DAL_CongDoan {
    private  DAL_SanPham dal_sanPham;
    public DAL_CongDoan(){
        dal_sanPham = new DAL_SanPham();
    }
    /**
     * Hàm trả về danh sách công đoạn
     * ArrayList<DTO_CongDoan>
     * @return DTO_CongDoan
     */
    public ArrayList<DTO_CongDoan> getDSCongDoan() throws SQLException, ParseException {
        ArrayList<DTO_CongDoan> dsCD = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM CongDoan";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_CongDoan tmp;
            String maCongDoan = rs.getString(1);
            String tenCongDoan = rs.getString(2);
            double donGiaCongDoan = rs.getDouble(3);

            tmp = new DTO_CongDoan(maCongDoan,tenCongDoan,donGiaCongDoan);
            dsCD.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsCD;
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
    /**
     * Hàm thêm một công đoạn vào database
     * @param congDoan DTO_CongDoan
     *
     */
    public  void insertCongDoan(DTO_CongDoan congDoan) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO CongDoan VALUES(?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,congDoan.getMaCongDoan());
        ppsm.setString(2,congDoan.getTenCongDoan());
        ppsm.setDouble(3,congDoan.getDonGiaCongDoan());


        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm xóa một công đoạn
     * @param maCD String
     *
     */
    public void deleteCongDoan(String maCD) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE CongDoan WHERE maCongDoan = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,maCD);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm cập nhật thông tin công đoạn
     * @param congDoan DTO_CongDoan
     *
     */
    public void updateCongDoan(DTO_CongDoan congDoan) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "UPDATE CongDoan " +
                "SET tenCongDoan= ?, donGiaCongDoan = ?, thuTuCongDoan = ?, maSanPham = ? " +
                "WHERE maCongDoan = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, congDoan.getTenCongDoan());
        ppsm.setDouble(2, congDoan.getDonGiaCongDoan());
        ppsm.setString(3,congDoan.getMaCongDoan());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm trả về danh sách công đoạn theo mã sản phẩm
     * ArrayList<DTO_CongDoan>
     * @return DTO_CongDoan
     */
    public ArrayList<DTO_CongDoan> getDSCongDoanTheoMaSP(String ma) throws SQLException {
        ArrayList<DTO_CongDoan> ds = new ArrayList<DTO_CongDoan>();
        ConnectDB.getInstance();
        ConnectDB.getInstance().connect();
        PreparedStatement state = null;
        try {
            String sql = "select * from CongDoan where maSanPham = ?";
            state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, ma);

            ResultSet rs = state.executeQuery();
            while(rs.next()){
                DTO_CongDoan tmp;
                String maCongDoan = rs.getString(1);
                String tenCongDoan = rs.getString(2);
                double donGiaCongDoan = rs.getDouble(3);

                tmp = new DTO_CongDoan(maCongDoan,tenCongDoan,donGiaCongDoan);
                ds.add(tmp);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                state.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return ds;
    }
}
