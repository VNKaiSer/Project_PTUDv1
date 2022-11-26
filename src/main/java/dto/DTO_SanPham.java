/**
 * 30-9-2022
 * Lớp Sản phẩm
 *
 */
package dto;

import java.util.ArrayList;
/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public class DTO_SanPham {
	private String maSanPham;
	private String tenSanPham;
	private int soCongDoan;
	private int soLuongYeuCau;
	private String chatLieu;
	private ArrayList<DTO_CongDoan> dsCongDoan;
	private boolean trangThai;

	private byte [] hinhAnh;

	public DTO_SanPham() {
		// super();
		// TODO Auto-generated constructor stub
	}
	public DTO_SanPham(String maSanPham) {
		super();
		this.maSanPham = maSanPham;
	}
	public DTO_SanPham(String maSanPham, String tenSanPham, int soCongDoan,
					   int soLuongYeuCau,String chatLieu) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.soCongDoan = soCongDoan;
		this.soLuongYeuCau = soLuongYeuCau;
		this.chatLieu = chatLieu;
	}

	public DTO_SanPham(String maSanPham, String tenSanPham, int soCongDoan,
			int soLuongYeuCau, ArrayList<DTO_CongDoan> dsCongDoan) {
		// super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.soCongDoan = soCongDoan;
		this.soLuongYeuCau = soLuongYeuCau;
		this.dsCongDoan = dsCongDoan;
		this.trangThai = false;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public int getSoCongDoan() {
		return soCongDoan;
	}

	public void setSoCongDoan(int soCongDoan) {
		this.soCongDoan = soCongDoan;
	}

	public int getSoLuongYeuCau() {
		return soLuongYeuCau;
	}

	public void setSoLuongYeuCau(int soLuongYeuCau) {
		this.soLuongYeuCau = soLuongYeuCau;
	}

	public ArrayList<DTO_CongDoan> getDsCongDoan() {
		return dsCongDoan;
	}

	public void setDsCongDoan(ArrayList<DTO_CongDoan> dsCongDoan) {
		this.dsCongDoan = dsCongDoan;
	}
	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public byte[] getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(byte[] hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((maSanPham == null) ? 0 : maSanPham.hashCode());
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
		DTO_SanPham other = (DTO_SanPham) obj;
		if (maSanPham == null) {
			if (other.maSanPham != null)
				return false;
		} else if (!maSanPham.equals(other.maSanPham))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTO_SanPham [maSanPham=" + maSanPham + ", tenSanPham="
				+ tenSanPham + ", soCongDoan=" + soCongDoan
				+ ", soLuongYeuCau=" + soLuongYeuCau + ", dsCongDoan="
				+ dsCongDoan + "]";
	}

}
