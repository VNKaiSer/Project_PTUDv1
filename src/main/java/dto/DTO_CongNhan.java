/**
 * 30/09/2022
 * Lớp Công nhân
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public class DTO_CongNhan {
	private String maCongNhan;
	private String tenCongNhan;
	private Date ngayVaoLam;
	private boolean phai;
	private String soDienThoai;
	private Date ngaySinh;
	private String email;
	private String diaChi;
	private String trinhDo;

	public DTO_CongNhan() {
		// super();
		// TODO Auto-generated constructor stub
	}

	public DTO_CongNhan(String maCongNhan, String tenCongNhan, Date ngayVaoLam,
			boolean phai, String soDienThoai, Date ngaySinh, String email,
			String diaChi, String trinhDo) {
		// super();
		this.maCongNhan = maCongNhan;
		this.tenCongNhan = tenCongNhan;
		this.ngayVaoLam = ngayVaoLam;
		this.phai = phai;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.email = email;
		this.diaChi = diaChi;
		this.trinhDo = trinhDo;
	}

	public DTO_CongNhan(String maCongNhan) {
		this.maCongNhan = maCongNhan;
	}

	public String getMaCongNhan() {
		return maCongNhan;
	}

	public void setMaCongNhan(String maCongNhan) {
		this.maCongNhan = maCongNhan;
	}

	public String getTenCongNhan() {
		return tenCongNhan;
	}

	public void setTenCongNhan(String tenCongNhan) {
		this.tenCongNhan = tenCongNhan;
	}

	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public boolean isPhai() {
		return phai;
	}

	public void setPhai(boolean phai) {
		this.phai = phai;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getTrinhDo() {
		return trinhDo;
	}

	public void setTrinhDo(String trinhDo) {
		this.trinhDo = trinhDo;
	}

	public SimpleStringProperty phaiProperty(){
		return phai ? new SimpleStringProperty("Nam") : new SimpleStringProperty("Nữ");
	}

	public  SimpleStringProperty ngayVaoLamProperty(){
		return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngayVaoLam));
	}

	public  SimpleStringProperty ngaySinhProperty(){
		return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngaySinh));
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((maCongNhan == null) ? 0 : maCongNhan.hashCode());
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
		DTO_CongNhan other = (DTO_CongNhan) obj;
		if (maCongNhan == null) {
			if (other.maCongNhan != null)
				return false;
		} else if (!maCongNhan.equals(other.maCongNhan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTO_CongNhan [maCongNhan=" + maCongNhan + ", tenCongNhan="
				+ tenCongNhan + ", ngayVaoLam=" + ngayVaoLam + ", phai=" + phai
				+ ", soDienThoai=" + soDienThoai + ", ngaySinh=" + ngaySinh
				+ ", email=" + email + ", diaChi=" + diaChi + ", trinhDo="
				+ trinhDo + "]";
	}

}
