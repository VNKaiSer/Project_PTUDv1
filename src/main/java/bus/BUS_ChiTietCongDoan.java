package bus;

import dal.DAL_ChiTietCongDoan;
import dto.DTO_ChiTietCongDoan;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class BUS_ChiTietCongDoan {
    private DAL_ChiTietCongDoan dal_chiTietCongDoan;
    private ArrayList<DTO_ChiTietCongDoan> listChiTietCongDoan;

     public BUS_ChiTietCongDoan(){
        dal_chiTietCongDoan = new DAL_ChiTietCongDoan();
    }

    public void insertCTCongDoan(DTO_ChiTietCongDoan ct) throws SQLException {
         dal_chiTietCongDoan.insertChiTietCongDoan(ct);
    }

    public ArrayList<DTO_ChiTietCongDoan> getAllChiTietCongDoan() throws SQLException, ParseException {
        listChiTietCongDoan = dal_chiTietCongDoan.getDSChiTietCongDoan();
        return listChiTietCongDoan;
    }

    public ArrayList<DTO_ChiTietCongDoan> getDSTheoMaSP(String maSP){
         ArrayList<DTO_ChiTietCongDoan> rs = new ArrayList<>();
        for (DTO_ChiTietCongDoan it:
                listChiTietCongDoan) {
            if (it.getSanPham().getMaSanPham().equals(maSP)){
                rs.add(it);
            }
        }
        return rs;
    }

    public void rmDSTheoMaSP(ArrayList<DTO_ChiTietCongDoan> it)  {
       listChiTietCongDoan.removeAll(it);
    }

    public void luuCTCDCuaSanPham(ObservableList<DTO_ChiTietCongDoan> list) throws SQLException {
        for (DTO_ChiTietCongDoan it:
             list) {

            dal_chiTietCongDoan.insertChiTietCongDoan(it);
        }
    }

    public void addCTCD(DTO_ChiTietCongDoan dt){
         listChiTietCongDoan.add(dt);
    }

    public void rmCTCD(DTO_ChiTietCongDoan dt){
        listChiTietCongDoan.remove(dt);
    }
    public ArrayList<DTO_ChiTietCongDoan> getDSCongDoanTheoSP(String maSP) throws SQLException, ParseException {
        return dal_chiTietCongDoan.getDSCongDoanTheoMaSP(maSP);
    }

    public int checkCongDoan(String maSP) throws SQLException {
         return dal_chiTietCongDoan.checkPhanCD(maSP);
    }
}
