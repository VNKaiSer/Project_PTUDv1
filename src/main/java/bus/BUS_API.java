package bus;

import db.ConnectAPI;
import javafx.collections.ObservableList;

public class BUS_API {
    private ConnectAPI connectAPI;
    public BUS_API(){
        connectAPI = new ConnectAPI();
    }

    public ObservableList<String> getDanhSachTinh(){
        return connectAPI.getDsTinh();
    }

    public ObservableList<String> getDanhSachHuyen(String tenTinh){
        return connectAPI.getDsHuyen(tenTinh);
    }

    public ObservableList<String> getDanhSachDuong(String tenHuyen){
        return connectAPI.getDuong(tenHuyen);
    }


}
