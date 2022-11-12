package dal;

import db.ConnectDB;
import dto.DTO_BCCCongNhan;
import dto.DTO_BCCNhanVien;
import dto.DTO_CongNhan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAL_BangChamCongCN {
    private  DAL_CongNhan dal_congNhan;
    private ArrayList<DTO_BCCCongNhan> dsCBCCCN;
    public DAL_BangChamCongCN() throws SQLException, ParseException {
        dal_congNhan = new DAL_CongNhan();
        dsCBCCCN = init();
    }

    /**
     * Hàm trả về danh sách bảng chấm công
     * ArrayList<DTO_BCCCongNhan>
     * @return DTO_CongNhan
     */
    public ArrayList<DTO_BCCCongNhan> getDSBangCCCN() throws SQLException, ParseException {
        ArrayList<DTO_BCCCongNhan> dsCBCCCN = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM BangChamCongCN";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_BCCCongNhan tmp;
            String maBCCCN = rs.getString(1);
            int hienDien = rs.getInt(2);

            String date = rs.getString(3);
            Date ngayChamCong = new SimpleDateFormat("yyy-MM-dd").parse(date);
            DTO_CongNhan tmpCongNhan = findCongNhan(rs.getString(4));

            int soSP = rs.getInt(5);
            String ghiChu = rs.getString(6);
            tmp = new DTO_BCCCongNhan(tmpCongNhan, hienDien, soSP,ngayChamCong, maBCCCN, ghiChu);
                    dsCBCCCN.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsCBCCCN;
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

    public ArrayList<DTO_BCCCongNhan> init() throws SQLException, ParseException {
        dsCBCCCN = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM BangChamCongCN";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_BCCCongNhan tmp;
            String maBCCNV = rs.getString(1);
            int hienDien = rs.getInt(2);

            String date = rs.getString(3);
            Date ngayChamCong = new SimpleDateFormat("yyy-MM-dd").parse(date);
            DTO_CongNhan tmpCN = findCongNhan(rs.getString(4));
            String ghiChu = rs.getString(6);
            int soLuong = rs.getInt(5);

            tmp = new DTO_BCCCongNhan(tmpCN, hienDien, soLuong,ngayChamCong,maBCCNV, ghiChu);
            dsCBCCCN.add(tmp);
        }
        // đóng kết nối
        ConnectDB.getConnection().close();
        return dsCBCCCN;
    }

    public ArrayList<DTO_BCCCongNhan> getDsTheoNgay(String ncc) {
        ArrayList<DTO_BCCCongNhan> dsCCTheoNgay = new ArrayList<>();
        for (DTO_BCCCongNhan it :
                dsCBCCCN) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(it.getNgayChamCong());
            if (date.equals(ncc))
                dsCCTheoNgay.add(it);
        }
        return dsCCTheoNgay;
    }

    public void insertBCCNhanVien(DTO_BCCCongNhan bcccn) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO BangChamCongCN VALUES(?,?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1, bcccn.getMaBCCCN());
        ppsm.setInt(2, bcccn.getHienDien());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(bcccn.getNgayChamCong());
        ppsm.setString(3, dateTmp);
        ppsm.setString(4, bcccn.getCongNhan().getMaCongNhan());
        ppsm.setInt(5, bcccn.getSoLuongSanPhamLamDuoc());
        ppsm.setString(6, bcccn.getGhiChu());

        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }
}
