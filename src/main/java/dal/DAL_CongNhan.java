/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import db.ConnectDB;
import dto.DTO_CongNhan;
import javafx.scene.control.CheckBox;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DatPC
 */
public class DAL_CongNhan {

    public DAL_CongNhan() {

    }

    /**
     * Hàm get danh sách công nhân
     * @return Arraylist<DTO_CongNhan>
     */
    public ArrayList<DTO_CongNhan> getDSCongNhan() throws SQLException, ParseException {
        // tạo kết nối 
        ArrayList<DTO_CongNhan> dsCongNhan = new ArrayList<>();
        ConnectDB.getInstance().connect();
        //scrip sql
        String sql = "SELECT * FROM CongNhan";
        Statement stm = ConnectDB.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        // get dữ liệu
        while (rs.next()) {
            DTO_CongNhan tmp;
            String maCN = rs.getString(1);
            String tenCN = rs.getString(2);

            String date = rs.getString(3);
            Date ngayVaoLam = new SimpleDateFormat("yyy-MM-dd").parse(date);
            Boolean phai = rs.getBoolean(4);
            date = rs.getString(5);
            Date ngaySinh = new SimpleDateFormat("yyy-MM-dd").parse(date);
            String sdt = rs.getString(6);
            String email = rs.getString(7);
            String diaChi = rs.getString(8);
            String trinhDo = rs.getString(9);
            tmp = new DTO_CongNhan(maCN, tenCN, ngayVaoLam, true, sdt, ngaySinh, email, diaChi, trinhDo);
            dsCongNhan.add(tmp);
        }
        // đóng kết nối 
        ConnectDB.getConnection().close();
        //ObservableList<DTO_CongNhan>  tmp =FXCollections.observableArrayList(dsCongNhan);
        return dsCongNhan;
    }
    
    /**
     * Hàm thêm một công nhân vào database
     * @param congNhan DTO_CongNhan
     *
     */
    public  void insertCongNhan(DTO_CongNhan congNhan) throws SQLException {
        // gọi kết nối
        ConnectDB.getInstance().connect();
        String sql = "INSERT INTO CongNhan VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,congNhan.getMaCongNhan());
        ppsm.setString(2,congNhan.getTenCongNhan());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(congNhan.getNgayVaoLam());
        ppsm.setString(3,dateTmp);
        ppsm.setBoolean(4, congNhan.isPhai());
        dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(congNhan.getNgaySinh());
        ppsm.setString(5, dateTmp);
        ppsm.setString(6, congNhan.getSoDienThoai());
        ppsm.setString(7, congNhan.getEmail());
        ppsm.setString(8,congNhan.getDiaChi());
        ppsm.setString(9,congNhan.getTrinhDo());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm xóa một công nhân
     * @param maCN String
     *
     */
    public void deleteCongNhan(String maCN) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "DELETE CongNhan WHERE maCongNhan = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,maCN);
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm cập nhật thông tin một công nhân
     * @param congNhan DTO_CongNhan
     *
     */
    public void updateCongNhan(DTO_CongNhan congNhan) throws SQLException {
        ConnectDB.getInstance().connect();
        String sql = "UPDATE CongNhan " +
                     "SET tenCongNhan = ?, ngayVaoLam = ?, phai = ?, [ ngaySinh ] = ?, SDT = ?, email = ?, diaChi = ?, trinhDo = ? " +
                     "WHERE maCongNhan = ?";
        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);
        ppsm.setString(1,congNhan.getTenCongNhan());
        String dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(congNhan.getNgayVaoLam());
        ppsm.setString(2,dateTmp);
        ppsm.setBoolean(3, congNhan.isPhai());
        dateTmp = new SimpleDateFormat("yyyy-MM-dd").format(congNhan.getNgaySinh());
        ppsm.setString(4, dateTmp);
        ppsm.setString(5, congNhan.getSoDienThoai());
        ppsm.setString(6, congNhan.getEmail());
        ppsm.setString(7,congNhan.getDiaChi());
        ppsm.setString(8,congNhan.getTrinhDo());
        ppsm.setString(9,congNhan.getMaCongNhan());
        ppsm.execute();
        // đóng kết nối
        ConnectDB.getConnection().close();
    }

    /**
     * Hàm get danh sách công nhân chưa được phân công
     * @return Arraylist<DTO_CongNhan>
     */
    public ArrayList<DTO_CongNhan> getDSCongNhanChuaDuocPhanCong(String maSP, String maCD, String ca) throws SQLException, ParseException {
        ArrayList<DTO_CongNhan> ds = new ArrayList<DTO_CongNhan>();
        ConnectDB.getInstance().connect();
        try {
            String sql = "select *from CongNhan where maCongNhan not in(select maCongNhan from CNDuocPhanCong where (maSanPham = ? and maCongDoan = ? and ca = ?) or maCongDoan not in(?) or maSanPham not in (?)  )";
            PreparedStatement state = ConnectDB.getConnection().prepareStatement(sql);
            state.setString(1, maSP);
            state.setString(2, maCD);
            state.setString(3, ca);
            state.setString(4, maCD);
            state.setString(5, maSP);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                DTO_CongNhan tmp;
                String maCN = rs.getString(1);
                String tenCN = rs.getString(2);


                tmp = new DTO_CongNhan(maCN, tenCN);
                ds.add(tmp);

            }
        } catch (SQLException  e) {
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
