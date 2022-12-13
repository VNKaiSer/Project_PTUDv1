package bus;

import dal.DAL_NhanVien;
import dto.DTO_CongNhan;
import dto.DTO_NhanVien;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_NhanVien {
    private DAL_NhanVien dal_nhanVien;
    private ArrayList<DTO_NhanVien> dsNV;

    public BUS_NhanVien(){
        dal_nhanVien = new DAL_NhanVien();
        dsNV = new ArrayList<>();
    }

    public ArrayList<DTO_NhanVien> getAllDSNhanVien(){
        try {
            dsNV = dal_nhanVien.getDSNhanVien();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dsNV;
    }
    public  void insertNhanVien(DTO_NhanVien nv) throws SQLException {
        dal_nhanVien.insertNhanVien(nv);
    }

    public void deleteNhanVien(String maNV) throws SQLException {
        dal_nhanVien.deleteNhanVien(maNV);
    }
    public void updateNhanVien(DTO_NhanVien nv){
        try {
            dal_nhanVien.updateNhanVien(nv);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
