/**
 * 30/09/2022
 * Lớp Công đoạn
 */
package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author 20116031_Võ Tấn Đạt
 *
 */
public class DTO_CongDoan {
	private String maCongDoan;
	private String tenCongDoan;
	private double donGiaCongDoan;

	public DTO_CongDoan() {
	}

	public DTO_CongDoan(String maCongDoan) {
		this.maCongDoan = maCongDoan;
	}
	public DTO_CongDoan(String tenCongDoan,
						double donGiaCongDoan) {

		this.tenCongDoan = tenCongDoan;
		this.donGiaCongDoan = donGiaCongDoan;
	}
	public DTO_CongDoan(String maCongDoan, String tenCongDoan,
						double donGiaCongDoan) {

		this.maCongDoan = maCongDoan;
		this.tenCongDoan = tenCongDoan;
		this.donGiaCongDoan = donGiaCongDoan;
	}

	public String getMaCongDoan() {
		return maCongDoan;
	}

	public void setMaCongDoan(String maCongDoan) {
		this.maCongDoan = maCongDoan;
	}

	public String getTenCongDoan() {
		return tenCongDoan;
	}

	public void setTenCongDoan(String tenCongDoan) {
		this.tenCongDoan = tenCongDoan;
	}

	public double getDonGiaCongDoan() {
		return donGiaCongDoan;
	}

	public void setDonGiaCongDoan(double donGiaCongDoan) {
		this.donGiaCongDoan = donGiaCongDoan;
	}

	public  SimpleStringProperty donGiaCongDoanProperty(){
		return new SimpleStringProperty(new DecimalFormat("###,###VNĐ").format(donGiaCongDoan));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((maCongDoan == null) ? 0 : maCongDoan.hashCode());
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
		DTO_CongDoan other = (DTO_CongDoan) obj;
		if (maCongDoan == null) {
			if (other.maCongDoan != null)
				return false;
		} else if (!maCongDoan.equals(other.maCongDoan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTO_CongDoan [maCongDoan=" + maCongDoan + ", tenCongDoan="
				+ tenCongDoan + ", donGiaCongDoan=" + donGiaCongDoan;
	}

}
