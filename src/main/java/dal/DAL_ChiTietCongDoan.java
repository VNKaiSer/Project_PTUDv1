package dal;

import db.ConnectDB;
import dto.DTO_ChiTietCongDoan;
import dto.DTO_CongDoan;
import dto.DTO_SanPham;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author 20077931_Trần Thanh Đại
 */
public class DAL_ChiTietCongDoan {
    private final DAL_SanPham dal_sanPham;
    private final DAL_CongDoan dal_congDoan;


    public DAL_ChiTietCongDoan() {
        dal_sanPham = new DAL_SanPham();
        dal_congDoan = new DAL_CongDoan();
    }

    /**
     * Hàm trả về danh sách chi tiết công đoạn
     * ArrayList<DTO_ChiTietCongDoan>
     *
     * @return DTO_ChiTietCongDoan
     */
    public ArrayList<DTO_ChiTietCongDoan> getDSChiTietCongDoan() throws SQLException, ParseException {
        ArrayList<DTO_ChiTietCongDoan> dsChiTietCongDoan = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM chitietcongdoan";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_ChiTietCongDoan tmp;
            DTO_SanPham tmpSanPham = findSanPham(rs.getString(1));
            DTO_CongDoan tmpCongDoan = findCongDoan(rs.getString(2));
            String maChiTietCongDoan = rs.getString(3);


            tmp = new DTO_ChiTietCongDoan(tmpSanPham, tmpCongDoan, maChiTietCongDoan);
            dsChiTietCongDoan.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsChiTietCongDoan;
    }

    /**
     * Hàm get tìm kiếm một sản phẩm
     *
     * @param maSP String
     * @return DTO_SanPham
     */
    private DTO_SanPham findSanPham(String maSP) throws SQLException, ParseException {
        ArrayList<DTO_SanPham> tmp = dal_sanPham.getDSSanPham();
        for (DTO_SanPham it :
                tmp) {
            if (it.getMaSanPham().equals(maSP)) {
                return it;
            }
        }
        return null;
    }

    /**
     * Hàm get tìm kiếm một công đoạn
     *
     * @param maCD String
     * @return DTO_CongDoan
     */
    private DTO_CongDoan findCongDoan(String maCD) throws SQLException, ParseException {
        ArrayList<DTO_CongDoan> tmp = dal_congDoan.getDSCongDoan();
        for (DTO_CongDoan it :
                tmp) {
            if (it.getMaCongDoan().equals(maCD)) {
                return it;
            }
        }
        return null;
    }

    /**
     * Hàm thêm một chi tiết công đoạn vào database
     *
     * @param chiTietCongDoan DTO_ChiTietCongDoan
     */
    public void insertChiTietCongDoan(DTO_ChiTietCongDoan chiTietCongDoan) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO chiTietCongDoan VALUES(?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, chiTietCongDoan.getSanPham().getMaSanPham());
        ppsm.setString(2, chiTietCongDoan.getCongDoan().getMaCongDoan());
        ppsm.setString(3, chiTietCongDoan.getMaCTCongDoan());

        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm xóa một chi tiết công đoạn
     *
     * @param maChiTietCongDoan String
     */
    public void deleteChiTietCongDoan(String maChiTietCongDoan) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE chiTietCongDoan WHERE maCTCongDoan = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, maChiTietCongDoan);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm cập nhật thông tin chi tiết công đoạn
     *
     * @param ChiTietCongDoan DTO_ChiTietCongDoan
     */
    public void updateChiTietCongDoan(DTO_ChiTietCongDoan ChiTietCongDoan) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "UPDATE chiTietCongDoan " +
                "SET maSanPham = ?, maCongDoan = ?" +
                "WHERE maCTCongDoan = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, ChiTietCongDoan.getSanPham().getMaSanPham());
        ppsm.setString(2, ChiTietCongDoan.getCongDoan().getMaCongDoan());
        ppsm.setString(3, ChiTietCongDoan.getMaCTCongDoan());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm trả về danh sách công đoạn theo mã sản phẩm
     * ArrayList<DTO_CongDoan>
     *
     * @return DTO_CongDoan
     */
    public ArrayList<DTO_ChiTietCongDoan> getDSCongDoanTheoMaSP(String ma) throws SQLException {
        ArrayList<DTO_ChiTietCongDoan> ds = new ArrayList<DTO_ChiTietCongDoan>();
        ConnectDB.getInstance();
        ConnectDB.getInstance().connect();
        PreparedStatement state = null;
        try {
            String sql = "select * from ChiTietCongDoan where maSanPham = ?";
            state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, ma);

            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                DTO_ChiTietCongDoan tmp;
                DTO_SanPham tmpSanPham = findSanPham(rs.getString(1));
                DTO_CongDoan tmpCongDoan = findCongDoan(rs.getString(2));
                String maChiTietCongDoan = rs.getString(3);


                tmp = new DTO_ChiTietCongDoan(tmpSanPham, tmpCongDoan, maChiTietCongDoan);
                ds.add(tmp);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                state.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return ds;
    }
}
