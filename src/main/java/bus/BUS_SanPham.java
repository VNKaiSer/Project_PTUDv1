package bus;

import dal.DAL_SanPham;
import dto.DTO_SanPham;

import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_SanPham {
    private DAL_SanPham dal_sanPham;
    public BUS_SanPham(){
        dal_sanPham = new DAL_SanPham();
    }

    public ArrayList<DTO_SanPham> getAllSanPham() throws SQLException, ParseException {
        return dal_sanPham.getDSSanPham();
    }
    public void insertSanPham(DTO_SanPham sp) throws SQLException {
        dal_sanPham.insertSanPham(sp);
    }
    public void deleteSanPham(String maSP) throws SQLException {
        dal_sanPham.deleteSanPham(maSP);
    }
}
