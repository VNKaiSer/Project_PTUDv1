package gui;
import bus.*;
import dto.*;
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
    private TableColumn<DTO_BangPhanCong, String> col_ngayBatDau;

    @FXML
    private TableColumn<DTO_BangPhanCong, String> col_ngayKetThuc;

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
    private DatePicker dtk_ngayBatDau;

    @FXML
    private DatePicker dtk_ngayKetThuc;

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
        Date ngay = new Date();
        LocalDate ngayHienTai = Instant.ofEpochMilli(ngay.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        dtk_ngayPhanCong.setValue(ngayHienTai);
        cbo_ca.setItems(listCa);
        try {
            loadCBoSanPham();
            loadBPC();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        handleEvent();


    }
    public void handleEvent(){
        dtk_ngayPhanCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadBPC();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dtk_ngayKetThuc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Date ngayBD = Date.from(dtk_ngayBatDau.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                Date ngayKT = Date.from(dtk_ngayKetThuc.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                if(ngayKT.compareTo(ngayBD)<=0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Ngày kết thúc không được nhỏ hơn ngày bắt đầu");
                    Optional<ButtonType> clickedButton = alert.showAndWait();
                    return;

                }
            }
        });
        cbo_sanPham.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(dtk_ngayPhanCong.getValue()==null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Vui lòng chọn ngày phân công trước");
                    Optional<ButtonType> clickedButton = alert.showAndWait();
                    return;
                }

            }
        });
        cbo_sanPham.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadCBoCongDoan(cbo_sanPham.getSelectionModel().getSelectedItem());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                tbl_CNChuaPhanCong.getItems().clear();
                tbl_CNDaPhanCong.getItems().clear();
            }
        });

        cbo_ca.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadCongNhan();

            }
        });
        btn_them.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(tbl_CNChuaPhanCong.getItems().size()>0){
                    int idx = tbl_CNChuaPhanCong.getSelectionModel().getSelectedIndex();
                    if(idx!=-1){
                        String maCN = tbl_CNChuaPhanCong.getColumns().get(0).getCellObservableValue(idx).getValue().toString();
                        tbl_CNChuaPhanCong.getItems().remove(idx);
                        Date ngayPhanCong = null;
                        try {
                            ngayPhanCong = getNgayPhanCong();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        if(ngayPhanCong==null){
                            ngayPhanCong = new Date();
                        }
                        try {
                            bus_cnDuocPhanCong.insertCNDuocPhanCong(new DTO_CNDuocPhanCong(new DTO_CongNhan(maCN),new DTO_CongDoan(cbo_congDoan.getSelectionModel().getSelectedItem()),new DTO_SanPham(cbo_sanPham.getSelectionModel().getSelectedItem()),Integer.parseInt(cbo_ca.getSelectionModel().getSelectedItem())));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        loadTBLCongNhanDaPhanCongTheoCa();
                    }
                }

            }
        });
        cbo_congDoan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tbl_CNChuaPhanCong.getItems().clear();
                tbl_CNDaPhanCong.getItems().clear();
            }
        });
        btn_bo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(tbl_CNDaPhanCong.getItems().size()>0){
                    int idx = tbl_CNDaPhanCong.getSelectionModel().getSelectedIndex();
                    if(idx!=-1){
                        String maCN = tbl_CNDaPhanCong.getColumns().get(0).getCellObservableValue(idx).getValue().toString();
                        try {
                            bus_cnDuocPhanCong.deleteCNDuocPhanCong(maCN);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        loadTBLCongNhanDaPhanCongTheoCa();
                        loadTBLCongNhanChuaPhanCong();
                    }
                }
            }
        });
        btn_Luu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(dtk_ngayBatDau.getValue()==null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Vui lòng chọn ngày bắt đầu");
                    Optional<ButtonType> clickedButton = alert.showAndWait();
                    return;
                }
                else if(dtk_ngayKetThuc.getValue()==null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Vui lòng chọn ngày kết thúc");
                    Optional<ButtonType> clickedButton = alert.showAndWait();
                    return;
                }
                else {
                    try {
                        luuBangPhanCong();
                        loadBPC();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        });
    }
    private void loadCBoSanPham() throws SQLException, ParseException {
        cbo_sanPham.getItems().clear();
        ArrayList<DTO_SanPham> ds = bus_sanPham.getAllSanPham();
        for (DTO_SanPham sp : ds){
            cbo_sanPham.getItems().addAll(sp.getMaSanPham());
        }

    }
    private void loadCBoCongDoan(String ma) throws SQLException, ParseException {
        cbo_congDoan.getItems().clear();
        ArrayList<DTO_ChiTietCongDoan> ds = bus_chiTietCongDoan.getDSCongDoanTheoSP(ma);
        for (DTO_ChiTietCongDoan cd : ds){
            cbo_congDoan.getItems().addAll(cd.getCongDoan().getMaCongDoan());
        }

    }
    private void loadTBLCongNhanChuaPhanCong(){
        tbl_CNChuaPhanCong.getItems().clear();
        try {
            ArrayList<DTO_CongNhan> ds = bus_congNhan.getDSCongNhanChuaDuocPhanCong(cbo_sanPham.getSelectionModel().getSelectedItem(),cbo_congDoan.getSelectionModel().getSelectedItem(),Integer.parseInt(cbo_ca.getSelectionModel().getSelectedItem()));
            listCongNhan = FXCollections.observableArrayList(ds);
            col_maCNChuaPhanCong.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
            col_tenCNChuaPhanCong.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));

            tbl_CNChuaPhanCong.setItems(listCongNhan);
        } catch (Exception e) {

        }
    }
    private void loadTBLCongNhanDaPhanCongTheoCa(){
        tbl_CNDaPhanCong.getItems().clear();
        try {
            ArrayList<DTO_CNDuocPhanCong> ds = bus_cnDuocPhanCong.getDSCNDuocPhanCongTheoCa(cbo_sanPham.getSelectionModel().getSelectedItem(),cbo_congDoan.getSelectionModel().getSelectedItem(),Integer.parseInt(cbo_ca.getSelectionModel().getSelectedItem()));
            listCongNhanDuocPhanCong = FXCollections.observableArrayList(ds);
            col_maCNDaPhanCong.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
            col_tenCNDaPhanCong.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
            tbl_CNDaPhanCong.setItems(listCongNhanDuocPhanCong);

        } catch (Exception e) {

        }
    }
    private Date getNgayPhanCong() throws SQLException, ParseException {
        ArrayList<DTO_BangPhanCong> list = bus_phanCong.getDSBPC();
        Date ngayPC = Date.from(dtk_ngayPhanCong.getValue().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        Date ngayPhanCong = null;
        for (DTO_BangPhanCong bpc : list){
            if(ngayPC.compareTo(bpc.getNgayBatDau())>=0){
                if(ngayPC.compareTo(bpc.getNgayKetThuc())<=0){
                    ngayPhanCong = bpc.getNgayPhanCong();
                    break;
                }
                else {
                    ngayPhanCong = null;
                }
            }

        }

        return ngayPhanCong;
    }
    private void luuBangPhanCong() throws SQLException, ParseException {

        if(getNgayPhanCong() != null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String ngay = df.format(getNgayPhanCong());
            bus_phanCong.deleteBPC(ngay);
            ArrayList<DTO_CNDuocPhanCong> dsCN = bus_cnDuocPhanCong.getDSCNDuocPhanCong();
            for (DTO_CNDuocPhanCong cn : dsCN){
                Date ngayPC = getNgayPhanCong();
                String maCongNhan = cn.getCongNhan().getMaCongNhan();
                //String ten = cn.getTen();
                String maCongDoan = cn.getCongDoan().getMaCongDoan();
                String maSanPham = cn.getSanPham().getMaSanPham();
                //String tenCongDoan = cn.getCongDoan().getTenCongDoan();
                Date ngayKT = Date.from(dtk_ngayKetThuc.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                Date ngayBD = Date.from(dtk_ngayBatDau.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                int ca = cn.getCa();
                bus_phanCong.insertBPC(new DTO_BangPhanCong(new DTO_CongDoan(maCongDoan),new DTO_CongNhan(maCongNhan),ngayPC,ngayKT,ngayBD,ca, new DTO_SanPham(maSanPham)));
            }

        }
        else {
            ArrayList<DTO_CNDuocPhanCong> dsCN = bus_cnDuocPhanCong.getDSCNDuocPhanCong();
            for (DTO_CNDuocPhanCong cn : dsCN){
                Date ngayPC = Date.from(dtk_ngayPhanCong.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                String maCongNhan = cn.getCongNhan().getMaCongNhan();
                //String ten = cn.getTen();
                String maCongDoan = cn.getCongDoan().getMaCongDoan();
                String maSanPham = cn.getSanPham().getMaSanPham();
                //String tenCongDoan = cn.getCongDoan().getTenCongDoan();
                Date ngayKT = Date.from(dtk_ngayKetThuc.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                Date ngayBD = Date.from(dtk_ngayBatDau.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                int ca = cn.getCa();
                bus_phanCong.insertBPC(new DTO_BangPhanCong(new DTO_CongDoan(maCongDoan),new DTO_CongNhan(maCongNhan),ngayPC,ngayKT,ngayBD,ca, new DTO_SanPham(maSanPham)));
            }
        }


    }
    private void loadBPC() throws SQLException, ParseException {
        tbl_DSBangPhanCong.getItems().clear();
        if(getNgayPhanCong() != null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String ngay = df.format(getNgayPhanCong());
            try {
                ArrayList<DTO_BangPhanCong> ds = bus_phanCong.getDSBPCtheoNgayPhanCong(ngay);
                listBPC = FXCollections.observableArrayList(ds);
                col_maCongNhan.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
                col_tenCongNhan.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
                col_maCongDoan.setCellValueFactory(new PropertyValueFactory<>("maCongDoan"));
                col_tenCongDoan.setCellValueFactory(new PropertyValueFactory<>("tenCongDoan"));
                col_soLuongYeuCau.setCellValueFactory(new PropertyValueFactory<>("soLuongSP"));
                col_caLam.setCellValueFactory(new PropertyValueFactory<>("ca"));
                col_ngayBatDau.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
                col_ngayKetThuc.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
                tbl_DSBangPhanCong.setItems(listBPC);

                for (DTO_BangPhanCong bpc : ds){
                    LocalDate ngayBD = Instant.ofEpochMilli(bpc.getNgayBatDau().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    dtk_ngayBatDau.setValue(ngayBD);
                    LocalDate ngayKT = Instant.ofEpochMilli(bpc.getNgayKetThuc().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    dtk_ngayKetThuc.setValue(ngayKT);
                }

            } catch (Exception e) {

            }
        }

    }
    private void loadCongNhan(){
        loadTBLCongNhanChuaPhanCong();
        loadTBLCongNhanDaPhanCongTheoCa();

    }
}
