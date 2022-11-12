/**
 * 05/10/2022
 * Lớp tài khoản
 */
package dto;

/**
 * @author 20077931_Trần Thanh Đại
 *
 */
public class DTO_TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    public DTO_TaiKhoan() {
        super();
        // TODO Auto-generated constructor stub
    }
    public DTO_TaiKhoan(String tenDangNhap, String matKhau) {
        super();
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }
    public String getTenDangNhap() {
        return tenDangNhap;
    }
    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
    public String getMatKhau() {
        return matKhau;
    }
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((tenDangNhap == null) ? 0 : tenDangNhap.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DTO_TaiKhoan other = (DTO_TaiKhoan) obj;
        if (tenDangNhap == null) {
            if (other.tenDangNhap != null)
                return false;
        } else if (!tenDangNhap.equals(other.tenDangNhap))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "DTO_TaiKhoan [tenDangNhap=" + tenDangNhap + ", matKhau="
                + matKhau + "]";
    }

}
