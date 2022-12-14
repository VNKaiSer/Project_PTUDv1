package dal;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DAL_ThongKe {
    public ArrayList<Integer> thongKeTheoCaBieuDoTron(){
        String sql = "SELECT        BangChamCongCN.hienDien, COUNT(*)\n" +
                "FROM            BangChamCongCN inner join BangPhanCong on BangChamCongCN.maCongNhan = BangPhanCong.maCongNhan\n" +
                "WHERE ngayChamCong = '2022-12-12'\n" +
                "GROUP BY BangChamCongCN.hienDien\n";
        return null;
    }
}
