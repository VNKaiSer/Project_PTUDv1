package bus;

import dto.DTO_ChiTietCongDoan;
import dto.DTO_CongDoan;
import dto.DTO_SanPham;

import java.sql.SQLException;

public class testBus {
    public static void main(String[] args) {
        BUS_ChiTietCongDoan bus_chiTietCongDoan = new BUS_ChiTietCongDoan();
        DTO_SanPham sp = new DTO_SanPham("1");
        DTO_CongDoan cd = new DTO_CongDoan("1");
        try {
            bus_chiTietCongDoan.insertCTCongDoan(new DTO_ChiTietCongDoan(sp,cd,"1",1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
