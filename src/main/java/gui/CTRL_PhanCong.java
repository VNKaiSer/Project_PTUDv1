package gui;
import bus.*;
import dto.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.apache.xmlbeans.impl.common.XMLChar.isInvalid;

public class CTRL_PhanCong implements Initializable {

    @FXML
    private Button btn_Luu;

    @FXML
    private Button btn_bo;

    @FXML
    private Button btn_them;

    @FXML
    private Button btn_xuatDS;

    @FXML
    private ComboBox<String> cbo_ca;

    @FXML
    private ComboBox<String> cbo_congDoan;

    @FXML
    private ComboBox<String> cbo_sanPham;



    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_caLam;

    @FXML
    private TableColumn<DTO_CongNhan, String> col_maCNChuaPhanCong;

    @FXML
    private TableColumn<DTO_CNDuocPhanCong, String> col_maCNDaPhanCong;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_maCongDoan;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_maCongNhan;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_ngayPhanCong;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_maSanPham;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_soLuongYeuCau;

    @FXML
    private TableColumn<DTO_CongNhan, String> col_tenCNChuaPhanCong;

    @FXML
    private TableColumn<DTO_CNDuocPhanCong, String> col_tenCNDaPhanCong;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_tenCongDoan;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_tenCongNhan;
    @FXML
    private TableColumn<DTO_CNDuocPhanCong, String> col_soLuongPhanCong;

    @FXML
    private DatePicker dtk_ngayPhanCong;

    @FXML
    private TableView<DTO_CongNhan> tbl_CNChuaPhanCong;

    @FXML
    private TableView<DTO_CNDuocPhanCong> tbl_CNDaPhanCong;

    @FXML
    private TableView<DTO_BangPhanCong> tbl_DSBangPhanCong;

    @FXML
    private TextField txt_Tim;

    @FXML
    private TextField txt_timCNChuaDuocPhanCong;

    @FXML
    private TextField txt_timCNDaDuocPhanCong;
    private BUS_SanPham bus_sanPham = new BUS_SanPham();
    private BUS_CongDoan bus_congDoan = new BUS_CongDoan();
    private BUS_CongNhan bus_congNhan = new BUS_CongNhan();
    private BUS_PhanCong bus_phanCong = new BUS_PhanCong();
    private BUS_ChiTietCongDoan bus_chiTietCongDoan = new BUS_ChiTietCongDoan();
    private BUS_CNDuocPhanCong bus_cnDuocPhanCong = new BUS_CNDuocPhanCong();
    private ObservableList<DTO_SanPham> listSanPham;
    ObservableList<String> listCa = FXCollections.observableArrayList("1", "2","3");
    private ObservableList<DTO_CongNhan> listCongNhan;
    private ObservableList<DTO_CNDuocPhanCong> listCongNhanDuocPhanCong;
    private ObservableList<DTO_BangPhanCong> listBPC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbl_CNDaPhanCong.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbl_CNChuaPhanCong.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Date ngay = new Date();
        LocalDate ngayHienTai = Instant.ofEpochMilli(ngay.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        dtk_ngayPhanCong.setValue(ngayHienTai);
        cbo_ca.setItems(listCa);
        try {
            loadCBoSanPham();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        handleEvent();


    }
    public void handleEvent(){


        cbo_sanPham.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadCBoCongDoan(cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6));
                    cbo_ca.getSelectionModel().clearSelection();
                    loadBPC();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                tbl_CNChuaPhanCong.getItems().clear();
                tbl_CNDaPhanCong.getItems().clear();
            }
        });
        cbo_congDoan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cbo_ca.getSelectionModel().clearSelection();
                tbl_CNDaPhanCong.getItems().clear();
                tbl_CNChuaPhanCong.getItems().clear();
                if(checkSoLuongPhanCong()==0){
                    btn_them.setDisable(true);
                }
                else {
                    btn_them.setDisable(false);
                }

            }
        });
        cbo_congDoan.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(cbo_sanPham.getValue()==null){
                    tbl_CNChuaPhanCong.getItems().clear();
                    tbl_CNDaPhanCong.getItems().clear();
                    Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
                    wn.setContentText("Vui lòng chọn sản phẩm cần phân công");
                    Optional<ButtonType> showWN = wn.showAndWait();
                    return;

                }
            }
        });
        cbo_ca.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(cbo_congDoan.getValue()==null){
                    tbl_CNChuaPhanCong.getItems().clear();
                    tbl_CNDaPhanCong.getItems().clear();
                    Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
                    wn.setContentText("Vui lòng chọn công đoạn cần phân công");
                    Optional<ButtonType> showWN = wn.showAndWait();
                    return;
                }
            }
        });
        cbo_ca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tbl_CNChuaPhanCong.getItems().clear();
                tbl_CNDaPhanCong.getItems().clear();
                loadCongNhanChuaPhanCong();
                loadCongNhanDaPhanCong();
            }
        });

        btn_them.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog dialog = new TextInputDialog("");

                dialog.setTitle("Phân công số lượng");
                dialog.setContentText("Số lượng:");
                dialog.setHeaderText(null);

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> {
                        int soLuong = Integer.parseInt(name);
                        them(soLuong);
                });
                tbl_CNChuaPhanCong.getItems().clear();
                tbl_CNDaPhanCong.getItems().clear();
                loadCongNhanChuaPhanCong();
                loadCongNhanDaPhanCong();
                if(checkSoLuongPhanCong()==0){
                    btn_them.setDisable(true);
                }
                else {
                    btn_them.setDisable(false);
                }
            }
        });
        btn_bo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bo();
                tbl_CNChuaPhanCong.getItems().clear();
                tbl_CNDaPhanCong.getItems().clear();
                loadCongNhanChuaPhanCong();
                loadCongNhanDaPhanCong();
                if(checkSoLuongPhanCong()==0){
                    btn_them.setDisable(true);
                }
                else {
                    btn_them.setDisable(false);
                }
            }
        });
        btn_Luu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                luuBPC();
                loadBPC();
            }
        });

    }
    private void loadCBoSanPham() throws SQLException, ParseException {
        cbo_sanPham.getItems().clear();
        ArrayList<DTO_SanPham> ds = bus_sanPham.getAllSanPham();
        for (DTO_SanPham sp : ds){
            cbo_sanPham.getItems().addAll(sp.getMaSanPham()+"-"+sp.getTenSanPham());
        }

    }
    private void loadCBoCongDoan(String ma) throws SQLException, ParseException {
        cbo_congDoan.getItems().clear();
        ArrayList<DTO_ChiTietCongDoan> ds = bus_chiTietCongDoan.getDSCongDoanTheoSP(ma);
        for (DTO_ChiTietCongDoan cd : ds){
            cbo_congDoan.getItems().addAll(cd.getCongDoan().getMaCongDoan()+"-"+cd.getCongDoan().getTenCongDoan());
        }

    }
    private void loadCongNhanChuaPhanCong(){
        String maSanPham = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
        String maCongDoan = "";
        if(cbo_congDoan.getValue()==null){
            maCongDoan="";
        }
        else {
            maCongDoan = cbo_congDoan.getSelectionModel().getSelectedItem().substring(0, 5);
        }
        String ca = cbo_ca.getSelectionModel().getSelectedItem();
        ArrayList<DTO_CongNhan> ds = bus_congNhan.getDSCongNhanChuaDuocPhanCong(maSanPham,maCongDoan,ca);
        listCongNhan=FXCollections.observableArrayList(ds);
        col_maCNChuaPhanCong.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
        col_tenCNChuaPhanCong.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
        tbl_CNChuaPhanCong.setItems(listCongNhan);
    }
    private void them(int soLuong){
        ObservableList<DTO_CongNhan> selectedItems = tbl_CNChuaPhanCong.getSelectionModel().getSelectedItems();
        for(int i = 0;i<selectedItems.size();i++){
            String maSP = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
            String maCD = cbo_congDoan.getSelectionModel().getSelectedItem().substring(0,5);
            DTO_CongNhan cn = new DTO_CongNhan(selectedItems.get(i).getMaCongNhan());
            DTO_CongDoan cd = new DTO_CongDoan(maCD);
            DTO_SanPham sp = new DTO_SanPham(maSP);
            ArrayList<DTO_CNDuocPhanCong> ds = bus_cnDuocPhanCong.getDSCNDuocPhanCongTheoCongDOanvaSanPham(maCD,maSP);
            int SL=0;
            for (DTO_CNDuocPhanCong cnDuocPhanCong : ds){
                SL=SL+cnDuocPhanCong.getSoLuongPhanCong();
            }
            String ca = cbo_ca.getSelectionModel().getSelectedItem();
            ArrayList<DTO_SanPham> dsSP = bus_sanPham.getSPTheoMaSP(cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6));
            int slYC = 0;
            for (DTO_SanPham sanPham: dsSP){
                slYC = sanPham.getSoLuongYeuCau();
            }

            if(soLuong*selectedItems.size() <= slYC-SL){

                DTO_CNDuocPhanCong cnChua = new DTO_CNDuocPhanCong(cn,cd,sp,Integer.parseInt(ca),soLuong);
                bus_cnDuocPhanCong.insertCNDuocPhanCong(cnChua);
            }
            else{
                Alert wn = new Alert(Alert.AlertType.WARNING, "Dữ liệu không phù hợ", ButtonType.APPLY);
                wn.setContentText("Vui lòng nhập số lượng phân công sao cho nhỏ hơn hoặc bằng "+ (slYC-SL)/selectedItems.size());
                Optional<ButtonType> showWN = wn.showAndWait();
                return;
            }

        }
    }
    private void loadCongNhanDaPhanCong(){
        String maSanPham = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
        String maCongDoan = "";
        if(cbo_congDoan.getValue()==null){
            maCongDoan="";
        }
        else {
            maCongDoan = cbo_congDoan.getSelectionModel().getSelectedItem().substring(0, 5);
        }
        String ca = cbo_ca.getSelectionModel().getSelectedItem();
        ArrayList<DTO_CNDuocPhanCong> ds = bus_cnDuocPhanCong.getDSCNDuocPhanCongTheoCa(maSanPham,maCongDoan,ca);
        listCongNhanDuocPhanCong=FXCollections.observableArrayList(ds);
        col_maCNDaPhanCong.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
        col_tenCNDaPhanCong.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
        col_soLuongPhanCong.setCellValueFactory(new PropertyValueFactory<>("soLuongPhanCong"));
        tbl_CNDaPhanCong.setItems(listCongNhanDuocPhanCong);
    }
    private void bo(){
        ObservableList<DTO_CNDuocPhanCong> selectedItems = tbl_CNDaPhanCong.getSelectionModel().getSelectedItems();
        for(int i = 0;i<selectedItems.size();i++){
            String maCN = selectedItems.get(i).getCongNhan().getMaCongNhan();
            String maCD = cbo_congDoan.getSelectionModel().getSelectedItem().substring(0,5);
            String maSP = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
            String ca = cbo_ca.getSelectionModel().getSelectedItem();
            bus_cnDuocPhanCong.deleteCNDuocPhanCong(maCN,maCD,maSP,ca);
        }
    }
    private void luuBPC(){
        String maSP = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
        if(bus_phanCong.checkPhanCong(maSP)==1){
            Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
            wn.setContentText("Vui lòng phân công đầy đủ các công đoạn trong sản phẩm có mã: "+maSP);
            Optional<ButtonType> showWN = wn.showAndWait();
            return;
        }
        else {
            ArrayList<DTO_CNDuocPhanCong> dsCN = bus_cnDuocPhanCong.getDSCNDuocPhanCongTheoSanPham(maSP);
            for (DTO_CNDuocPhanCong cn : dsCN){
                DTO_CongNhan CongNhan = new DTO_CongNhan(cn.getCongNhan().getMaCongNhan());
                DTO_CongDoan congDoan = new DTO_CongDoan(cn.getCongDoan().getMaCongDoan());
                DTO_SanPham sanPham = new DTO_SanPham(maSP);
                Date ngayPC = Date.from(dtk_ngayPhanCong.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                int ca = cn.getCa();
                int soLuongPhanCong = cn.getSoLuongPhanCong();
                DTO_BangPhanCong tmp = new DTO_BangPhanCong(congDoan,CongNhan,ngayPC,ca,sanPham,soLuongPhanCong,false);
                bus_phanCong.insertBPC(tmp);
            }
        }

    }
    private void loadBPC(){
        tbl_DSBangPhanCong.getItems().clear();
        Date ngayPC = Date.from(dtk_ngayPhanCong.getValue().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String ngay = df.format(ngayPC);
        String maSanPham = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
        ArrayList<DTO_BangPhanCong> ds = bus_phanCong.getDSBPCtheoNgayPhanCong(ngay,maSanPham);
        listBPC = FXCollections.observableArrayList(ds);
        col_ngayPhanCong.setCellValueFactory(new PropertyValueFactory<>("ngayPhanCong"));
        col_maCongNhan.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
        col_tenCongNhan.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
        col_maCongDoan.setCellValueFactory(new PropertyValueFactory<>("maCongDoan"));
        col_tenCongDoan.setCellValueFactory(new PropertyValueFactory<>("tenCongDoan"));
        col_maSanPham.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        col_soLuongYeuCau.setCellValueFactory(new PropertyValueFactory<>("soLuongPhanCong"));
        col_caLam.setCellValueFactory(new PropertyValueFactory<>("ca"));
        tbl_DSBangPhanCong.setItems(listBPC);

    }
    private int checkSoLuongPhanCong(){
        String maSP = cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6);
        String maCD = cbo_congDoan.getSelectionModel().getSelectedItem().substring(0,5);
        ArrayList<DTO_CNDuocPhanCong> ds = bus_cnDuocPhanCong.getDSCNDuocPhanCongTheoCongDOanvaSanPham(maCD,maSP);
        int SL=0;
        for (DTO_CNDuocPhanCong cn : ds){
            SL=SL+cn.getSoLuongPhanCong();
        }
        ArrayList<DTO_SanPham> dsSP = bus_sanPham.getSPTheoMaSP(cbo_sanPham.getSelectionModel().getSelectedItem().substring(0,6));
        int slYC = 0;
        for (DTO_SanPham sanPham: dsSP){
            slYC = sanPham.getSoLuongYeuCau();
        }
        if(SL>=slYC){
            return 0;
        }
        else {
            return 1;
        }
    }
}
