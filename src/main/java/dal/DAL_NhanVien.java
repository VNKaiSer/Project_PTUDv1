package dal;

import db.ConnectDB;
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
 *
 * @author 20077931_Trần Thanh Đại
 */
public class DAL_NhanVien {
    public DAL_NhanVien(){

    }
    /**
     * Hàm get danh sách nhân viên
     * @return Arraylist<DTO_NhanVien>
     */
    public ArrayList<DTO_NhanVien> getDSNhanVien() throws SQLException, ParseException {
        // tạo kết nối
        ArrayList<DTO_NhanVien> dsNhanVien = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM NhanVien";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_NhanVien tmp;
            String maNV = rs.getString(1);
            String tenNV = rs.getString(2);

            String date = rs.getString(3);
            Date ngayVaoLam = new SimpleDateFormat("yyy-MM-dd").parse(date);
            Boolean phai = rs.getBoolean(4);
            date = rs.getString(5);
            Date ngaySinh = new SimpleDateFormat("yyy-MM-dd").parse(date);
            String sdt = rs.getString(6);
            String email = rs.getString(7);
            String diaChi = rs.getString(8);
            double luongCoBan  = rs.getDouble(9);
            tmp = new DTO_NhanVien(maNV, tenNV, ngayVaoLam, phai, ngaySinh, sdt, email, diaChi, luongCoBan);
            dsNhanVien.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        //ObservableList<DTO_NhanVien>  tmp =FXCollections.observableArrayList(dsNhanVien);
        return dsNhanVien;
    }
    /**
     * Hàm thêm một nhân viên vào database
     * @param nhanvien DTO_NhanVien
     *
     */
    public  void insertNhanVien(DTO_NhanVien nhanvien) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,nhanvien.getMaNhanVien());
        ppsm.setString(2,nhanvien.getTenNhanVien());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(nhanvien.getNgayVaoLam());
        ppsm.setString(3,dateTmp);
        ppsm.setBoolean(4, nhanvien.isPhai());
        dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(nhanvien.getNgaySinh());
        ppsm.setString(5, dateTmp);
        ppsm.setString(6, nhanvien.getSoDienThoai());
        ppsm.setString(7, nhanvien.getEmail());
        ppsm.setString(8,nhanvien.getDiaChi());
        ppsm.setDouble(9,nhanvien.getLuongCoBan());
        ppsm.setDouble(9,nhanvien.getLuongCoBan());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm xóa một nhân viên
     * @param maNV String
     *
     */
    public void deleteNhanVien(String maNV) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE NhanVien WHERE maNhanVien = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,maNV);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
    /**
     * Hàm cập nhật thông tin một nhân viên
     * @param nhanVien DTO_NhanVien
     *
     */
    public void updateNhanVien(DTO_NhanVien nhanVien) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "UPDATE NhanVien " +
                "SET tenNhanVien = ?, ngayVaoLam = ?, phai = ?, [ ngaySinh ] = ?, SDT = ?, email = ?, diaChi = ?, luongCoBan = ?" +
                "\nWHERE maNhanVien = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,nhanVien.getTenNhanVien());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(nhanVien.getNgayVaoLam());
        ppsm.setString(2,dateTmp);
        ppsm.setBoolean(3, nhanVien.isPhai());
        dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(nhanVien.getNgaySinh());
        ppsm.setString(4, dateTmp);
        ppsm.setString(5, nhanVien.getSoDienThoai());
        ppsm.setString(6, nhanVien.getEmail());
        ppsm.setString(7,nhanVien.getDiaChi());
        ppsm.setDouble(8,nhanVien.getLuongCoBan());
        ppsm.setString(11,nhanVien.getMaNhanVien());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
}
