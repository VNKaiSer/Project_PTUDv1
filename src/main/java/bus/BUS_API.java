package bus;

import bus.Service.ReadJSONDiaChi;
import db.ConnectAPI;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class BUS_API {
    private ReadJSONDiaChi readJSONDiaChi;
    public BUS_API(){
        readJSONDiaChi = new ReadJSONDiaChi();
    }

    public ArrayList<String> getDanhSachTinh(){
        return readJSONDiaChi.getDSTinh();
    }

    public ArrayList<String> getDanhSachHuyen(String tenTinh){
        return readJSONDiaChi.getDSHuyen(tenTinh);
    }

    public ArrayList<String> getDanhSachDuong(String tenHuyen){
        return readJSONDiaChi.getDSDuong(tenHuyen);
    }


}
