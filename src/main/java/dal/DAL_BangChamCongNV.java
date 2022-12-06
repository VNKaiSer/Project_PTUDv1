package dal;

import db.ConnectDB;
import dto.DTO_BCCNhanVien;
import dto.DTO_NhanVien;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 20077931_Trần Thanh Đại
 */
public class DAL_BangChamCongNV {
    private DAL_NhanVien dal_nhanVien;
    private ArrayList<DTO_BCCNhanVien> dsCBCCNV;

    public DAL_BangChamCongNV() throws SQLException, ParseException {
        dal_nhanVien = new DAL_NhanVien();
        dsCBCCNV = init();
    }

    public ArrayList<DTO_BCCNhanVien> getDSBangCCNV() throws SQLException{
        return dsCBCCNV;
    }
    /**
     * Hàm trả về danh sách bảng chấm công nhân viên
     * ArrayList<DTO_BCCNhanVien>
     *
     * @return DTO_BCCNhanVien
     */
    public ArrayList<DTO_BCCNhanVien> init() throws SQLException, ParseException {
        dsCBCCNV = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM BangChamCongNV";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_BCCNhanVien tmp;
            String maBCCNV = rs.getString(1);
            int hienDien = rs.getInt(2);

            String date = rs.getString(3);
            Date ngayChamCong = new SimpleDateFormat("yyy-MM-dd").parse(date);
            DTO_NhanVien tmpNhanVien = findNhanVien(rs.getString(4));
            String ghiChu = rs.getString(5);

            tmp = new DTO_BCCNhanVien(tmpNhanVien, hienDien, ngayChamCong, maBCCNV, ghiChu);
            dsCBCCNV.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsCBCCNV;
    }

    /**
     * Hàm get tìm kiếm một nhân viên
     *
     * @param maNV String
     * @return DTO_NhanVien
     */
    private DTO_NhanVien findNhanVien(String maNV) throws SQLException, ParseException {
        ArrayList<DTO_NhanVien> tmp = dal_nhanVien.getDSNhanVien();
        for (DTO_NhanVien it :
                tmp) {
            if (it.getMaNhanVien().equals(maNV)) {
                return it;
            }
        }
        return null;
    }

    /**
     * Hàm thêm một bảng chấm công nhân viên vào database
     *
     * @param bcccnhanvien DTO_BCCNhanVien
     */
    public void insertBCCNhanVien(DTO_BCCNhanVien bcccnhanvien) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO BangChamCongNV VALUES(?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, bcccnhanvien.getMaBCCNV());
        ppsm.setInt(2, bcccnhanvien.getHienDien());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(bcccnhanvien.getNgayChamCong());
        ppsm.setString(3, dateTmp);
        ppsm.setString(4, bcccnhanvien.getNhanVien().getMaNhanVien());
        ppsm.setString(5, bcccnhanvien.getGhiChu());

        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm xóa một bảng chấm công nhân viên
     *
     * @param maBCCNhanVien String
     */
    public void deleteBCCNhanVien(String maBCCNhanVien) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE BangChamCongNV WHERE maBCCNV = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, maBCCNhanVien);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm cập nhật thông tin bảng chấm công nhân viên
     *
     * @param BCCNhanVien DTO_BCCNhanVien
     */
    public void updateBCCNhanVien(DTO_BCCNhanVien BCCNhanVien) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "UPDATE BangChamCongNV " +
                "SET hienDien = ?, ngayChamCong = ?, maNhanVien = ? " +
                "WHERE maBCCNV = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setInt(1, BCCNhanVien.getHienDien());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(BCCNhanVien.getNgayChamCong());
        ppsm.setString(2, dateTmp);
        ppsm.setString(3, BCCNhanVien.getNhanVien().getMaNhanVien());
        ppsm.setString(4, BCCNhanVien.getMaBCCNV());
        ppsm.setString(5, BCCNhanVien.getGhiChu());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm lấy danh sách chấm công nhân viên theo ngày
     *
     * @param ncc
     * @return ArrayList<DTO_BCCNhanVien>
     */
    public ArrayList<DTO_BCCNhanVien> getDsTheoNgay(String ncc) {
        ArrayList<DTO_BCCNhanVien> dsCCTheoNgay = new ArrayList<>();
        for (DTO_BCCNhanVien it :
                dsCBCCNV) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(it.getNgayChamCong());
            if (date.equals(ncc))
                dsCCTheoNgay.add(it);
        }
        return dsCCTheoNgay;
    }

}
