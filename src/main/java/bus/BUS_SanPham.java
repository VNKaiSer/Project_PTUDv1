package bus;

import dal.DAL_SanPham;
import dto.DTO_SanPham;

import java.io.FileNotFoundException;
import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_SanPham {
    private DAL_SanPham dal_sanPham;
    public BUS_SanPham(){
        dal_sanPham = new DAL_SanPham();
    }

    public ArrayList<DTO_SanPham> getAllSanPham( ){
        try {
            return dal_sanPham.getDSSanPham();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<DTO_SanPham> getSPTheoMaSP(String maSP) {
        try {
            return dal_sanPham.getSanPhamTheoMaSanPham(maSP);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertSanPham(DTO_SanPham sp) throws SQLException, FileNotFoundException {
        dal_sanPham.insertSanPham(sp);
    }
    public void deleteSanPham(String maSP) throws SQLException {
        dal_sanPham.deleteSanPham(maSP);
    }
    public void updateSP(DTO_SanPham sp){
        try {
            dal_sanPham.updateSanPham(sp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
