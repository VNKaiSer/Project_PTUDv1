package dal;

import db.ConnectDB;
import dto.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DAL_BangPhanCong {

    private  DAL_CongDoan dal_congDoan;
    private  DAL_CongNhan dal_congNhan;
    private  DAL_SanPham dal_sanPham;
    private DAL_CNDuocPhanCong dal_cnDuocPhanCong;


    public DAL_BangPhanCong(){
        dal_congNhan = new DAL_CongNhan();
        dal_congDoan = new DAL_CongDoan();
        dal_sanPham = new DAL_SanPham();
        dal_cnDuocPhanCong = new DAL_CNDuocPhanCong();
    }
    /**
     * Hàm trả về danh sách bảng phân công
     * ArrayList<DTO_BangPhanCong>
     * @return DTO_BangPhanCong
     */
    public ArrayList<DTO_BangPhanCong> getDSBangPhanCong() throws SQLException, ParseException {
        ArrayList<DTO_BangPhanCong> dsBPC = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM BangPhanCong";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_BangPhanCong tmp;
            String datePhanCong = rs.getString(1);
            Date ngayPhanCong = new SimpleDateFormat("yyyy-MM-dd").parse(datePhanCong);
            DTO_CongNhan tmpCongNhan = findCongNhan(rs.getString(2));
            DTO_CongDoan tmpCongDoan = findCongDoan(rs.getString(3));
            String dateKetThuc = rs.getString(4);
            Date ngayKetThuc = new SimpleDateFormat("yyyy-MM-dd").parse(dateKetThuc);
            String dateBatDau = rs.getString(5);
            Date ngayBatDau = new SimpleDateFormat("yyyy-MM-dd").parse(dateBatDau);
            int ca = rs.getInt(6);
            DTO_SanPham tmpSanPham = findSanPham(rs.getString(7));

            tmp = new DTO_BangPhanCong(tmpCongDoan,tmpCongNhan,ngayPhanCong,ca,tmpSanPham);

            dsBPC.add(tmp);

        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsBPC;
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
     * Hàm get tìm kiếm một công nhân
     * @param maCN String
     * @return DTO_CongNhan
     */
    private DTO_CongNhan findCongNhan(String maCN) throws SQLException, ParseException {
        ArrayList<DTO_CongNhan> dsCN = dal_congNhan.getDSCongNhan();
        for (DTO_CongNhan it: dsCN) {
            if(maCN.equals(it.getMaCongNhan()))
                return it;
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
    /**
     * Hàm thêm một bảng phân công vào database
     * @param bangPhanCong DTO_BangPhanCong
     *
     */
    public void insertBangPhanCong(DTO_BangPhanCong bangPhanCong) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO BangPhanCong VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(bangPhanCong.getNgayPhanCong());
        ppsm.setString(1,dateTmp);
        ppsm.setString(2,bangPhanCong.getCongNhan().getMaCongNhan());
        ppsm.setString(3, bangPhanCong.getCongDoan().getMaCongDoan());
        ppsm.setInt(4,bangPhanCong.getCa());
        ppsm.setString(5, bangPhanCong.getSanPham().getMaSanPham());
        ppsm.setInt(6,bangPhanCong.getSoLuongPhanCong());
        ppsm.setBoolean(7, bangPhanCong.getTrangThai());

        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm xóa một bảng phân công
     * @param ngayPhanCong String
     *
     */
    public void deleteBangPhanCong(String ngayPhanCong) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE BangPhanCong WHERE ngayPhanCong = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,ngayPhanCong);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    public ArrayList<DTO_BangPhanCong> getBPCTheoNgayPhanCong(String ngayPC,String maSanPham) throws SQLException {
        ArrayList<DTO_BangPhanCong> ds = new ArrayList<DTO_BangPhanCong>();
        ConnectDB.getInstance().connect();
        try {
            String sql = "select * from BangPhanCong where ngayPhanCong = ? and maSanPham = ?";
            PreparedStatement state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, ngayPC);
            state.setString(2, maSanPham);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                DTO_BangPhanCong tmp;
                String datePhanCong = rs.getString(1);
                Date ngayPhanCong = new SimpleDateFormat("yyy-MM-dd").parse(datePhanCong);
                DTO_CongNhan tmpCongNhan = findCongNhan(rs.getString(2));
                DTO_CongDoan tmpCongDoan = findCongDoan(rs.getString(3));
                int ca = rs.getInt(4);
                DTO_SanPham tmpSanPham = findSanPham(rs.getString(5));
                int soLuong = rs.getInt(6);


                tmp = new DTO_BangPhanCong(tmpCongDoan,tmpCongNhan,ngayPhanCong,ca,tmpSanPham,soLuong,false);

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
    public int checkPhanCong(String ma,String ngay) throws SQLException {
        int flag = 0;
        ConnectDB.getInstance();
        ConnectDB.getInstance().connect();
        PreparedStatement state = null;
        try {
            String sql = "select maCongDoan from ChiTietCongDoan where maSanPham = ?";
            state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, ma);

            ResultSet rs = state.executeQuery();
            while(rs.next()){
                ArrayList<DTO_CNDuocPhanCong> dsCN = dal_cnDuocPhanCong.getCNTheoCongDoanvaSanPham(rs.getString(1),ma,ngay);
                if(dsCN.isEmpty()){
                    flag = 1;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                state.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return flag;
    }
    public int LaySoLuong(String ma) throws SQLException {
        int soluong = 0;
        ConnectDB.getInstance();
        ConnectDB.getInstance().connect();
        PreparedStatement state = null;
        try {
            String sql = "select tienDo from tienDoSanPham where maSanPham = ?";
            state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, ma);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
               soluong = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                state.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return soluong;
    }
    public int LaySoCongDoan(String ma) throws SQLException {
        int soluong = 0;
        ConnectDB.getInstance();
        ConnectDB.getInstance().connect();
        PreparedStatement state = null;
        try {
            String sql = "select count(maCongDoan) from ChiTietCongDoan where maSanPham = ?";
            state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, ma);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                soluong = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                state.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return soluong;
    }
    public int laySoLuongSanPham(String maCN, String date, int ca) throws SQLException {
        int soLuong = 0;
        String sql = "{?= call laySoLuongPhanCong(?,?, ?)}";
        ConnectDB.getInstance().connect();
        CallableStatement cstm = ConnectDB.getConnection().prepareCall(sql);
        cstm.setString(2, maCN);
        cstm.setString(3, date);
        cstm.setInt(4, ca);
        cstm.registerOutParameter(1, Types.INTEGER);
        cstm.execute();
        soLuong = cstm.getInt(1);
        return  soLuong;
    }

    /**
     * Hàm lấy mã công nhân làm trong một ca để tạo phiếu lương
     * @param date
     * @param ca
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getCongNhanTheoCa(String date, int ca) throws SQLException {
        String sql = "SELECT * FROM BangPhanCong  \n" +
                "WHERE ca = ? and ngayPhanCong = ? ";
        ArrayList<String> listMaCN = new ArrayList<>();

        ConnectDB.getInstance().connect();
        PreparedStatement cstm = ConnectDB.getConnection().prepareCall(sql);
        cstm.setString(2, date);
        cstm.setInt(1, ca);
        ResultSet rs = cstm.executeQuery();
        while (rs.next()){
            listMaCN.add(rs.getString(2));
        }
        return listMaCN;
    }

}
