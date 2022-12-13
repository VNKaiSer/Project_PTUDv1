package dto;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class DTO_PhieuLuongCongNhan extends DTO_PhieuLuongCaNhan{
	private int tongSoSanPham;
	private DTO_CongNhan congNhan;

	private DTO_CongDoan congDoanLam;

	public DTO_PhieuLuongCongNhan(int tongSoSanPham, DTO_CongNhan congNhan) {
		this.tongSoSanPham = tongSoSanPham;
		this.congNhan = congNhan;
	}

	public DTO_PhieuLuongCongNhan(String maPhieuLuongNV, int soNgayCong, int nam, int thang, double tienThuong, double tienPhat, float thue, double tienTrachNhiem, double tienPhuCap, double tamUng, double tongLuong, double thucNhan, int tongSoSanPham, DTO_CongNhan congNhan, DTO_CongDoan cd) {
		super(maPhieuLuongNV, soNgayCong, nam, thang, tienThuong, tienPhat, thue, tienTrachNhiem, tienPhuCap, tamUng, tongLuong, thucNhan);
		this.tongSoSanPham = tongSoSanPham;
		this.congNhan = congNhan;
		this.congDoanLam = cd;
		tinhTongLuong();
		thucLanh();
	}

	public DTO_PhieuLuongCongNhan() {
		// super();
		// TODO Auto-generated constructor stub
	}

	public void setTongSoSanPham(int tongSoSanPham) {
		this.tongSoSanPham = tongSoSanPham;
	}

	public void setCongNhan(DTO_CongNhan congNhan) {
		this.congNhan = congNhan;
	}

	public int getTongSoSanPham() {
		return tongSoSanPham;
	}

	public DTO_CongNhan getCongNhan() {
		return congNhan;
	}

	public SimpleStringProperty tenNhanVienProperty(){
		return new SimpleStringProperty(congNhan.getTenCongNhan());
	}

	public SimpleStringProperty chucVuProperty(){
		return new SimpleStringProperty("Công Nhân");
	}

	@Override
	public String toString() {
		return "DTO_PhieuLuongCongNhan{" +
				"tongSoSanPham=" + tongSoSanPham +
				", congNhan=" + congNhan +
				", congDoanLam=" + congDoanLam +
				'}';
	}

	@Override
	public void tinhTongLuong() {
		tongLuong = tongSoSanPham * congDoanLam.getDonGiaCongDoan() * 1.0;
	}

	@Override
	public void thucLanh() {
		if (soNgayCong >= 26){
			tienThuong = 200000;
			tienTrachNhiem = 300000;
		}

		thue = (float) (tongLuong * 0.08);
		thucNhan = tongLuong - thue - tamUng + tienThuong - tienPhat;
	}

	public DTO_CongDoan getCongDoanLam() {
		return congDoanLam;
	}

	public void setCongDoanLam(DTO_CongDoan congDoanLam) {
		this.congDoanLam = congDoanLam;
	}
}
