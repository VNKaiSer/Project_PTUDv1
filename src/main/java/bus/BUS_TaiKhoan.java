package bus;

import dal.DAL_TaiKhoan;

import java.sql.SQLException;

public class BUS_TaiKhoan {
    private DAL_TaiKhoan dal_taiKhoan = new DAL_TaiKhoan();

    public int dangNhap(String username, String pass) throws SQLException {
        return dal_taiKhoan.dangNhap(username, pass);
    }

    /**
     *
     * @param ma
     * @return
     * @throws SQLException
     */
    public String getTenNguoiDangNhan(String ma) throws SQLException {
        return  dal_taiKhoan.getName(ma);
    }
}
