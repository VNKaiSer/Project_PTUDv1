/**
 * 30/09/2022
 * Lớp Bảng Chấm Công Công Nhân
 */
package dto;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public class DTO_BCCCongNhan {
	private DTO_CongNhan congNhan;
	private int hienDien;
	private int soLuongSanPhamLamDuoc;
	private Date ngayChamCong;
	private String maBCCCN;

	private String ghiChu;

	public DTO_BCCCongNhan() {
		// super();
		// TODO Auto-generated constructor stub
	}

	public DTO_BCCCongNhan(DTO_CongNhan congNhan, int hienDien,
			int soLuongSanPhamLamDuoc, Date ngayChamCong, String maBCCCN, String ghiChu) {
		// super();
		this.congNhan = congNhan;
		this.hienDien = hienDien;
		this.soLuongSanPhamLamDuoc = soLuongSanPhamLamDuoc;
		this.ngayChamCong = ngayChamCong;
		this.maBCCCN = maBCCCN;
		this.ghiChu = ghiChu;
	}

	public DTO_CongNhan getCongNhan() {
		return congNhan;
	}

	public void setCongNhan(DTO_CongNhan congNhan) {
		this.congNhan = congNhan;
	}

	public int getHienDien() {
		return hienDien;
	}

	public void setHienDien(int hienDien) {
		this.hienDien = hienDien;
	}

	public int getSoLuongSanPhamLamDuoc() {
		return soLuongSanPhamLamDuoc;
	}

	public void setSoLuongSanPhamLamDuoc(int soLuongSanPhamLamDuoc) {
		this.soLuongSanPhamLamDuoc = soLuongSanPhamLamDuoc;
	}

	public Date getNgayChamCong() {
		return ngayChamCong;
	}

	public void setNgayChamCong(Date ngayChamCong) {
		this.ngayChamCong = ngayChamCong;
	}

	public String getMaBCCCN() {
		return maBCCCN;
	}

	public void setMaBCCCN(String maBCCCN) {
		this.maBCCCN = maBCCCN;
	}
	public SimpleStringProperty maCongNhanProperty() {
		return new SimpleStringProperty(congNhan.getMaCongNhan());
	}

	public SimpleStringProperty tenCongNhanProperty() {
		return new SimpleStringProperty(congNhan.getTenCongNhan());
	}

	public SimpleStringProperty ngayChamCongProperty() {
		return new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(ngayChamCong));
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maBCCCN == null) ? 0 : maBCCCN.hashCode());
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
		DTO_BCCCongNhan other = (DTO_BCCCongNhan) obj;
		if (maBCCCN == null) {
			if (other.maBCCCN != null)
				return false;
		} else if (!maBCCCN.equals(other.maBCCCN))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTO_BCCCongNhan [congNhan=" + congNhan + ", hienDien="
				+ hienDien + ", soLuongSanPhamLamDuoc=" + soLuongSanPhamLamDuoc
				+ ", ngayChamCong=" + ngayChamCong + ", maBCCCN=" + maBCCCN
				+ "]";
	}

}
