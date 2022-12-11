package gui;

import bus.BUS_ChamCongCN;
import bus.BUS_CongNhan;
import bus.BUS_PhanCong;
import bus.BUS_TinhLuong;
import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CTRL_UI_ChamCongCN implements Initializable {
    @FXML
    private Button btnIn;

    @FXML
    private Button btnLuu;

    @FXML
    private ComboBox<String> cboMaCaLam;

    @FXML
    private ComboBox<String> cboTenCaLam;

    @FXML
    private TableColumn<?, ?> hienDienCol;

    @FXML
    private TableColumn<?, ?> hoTenCol;

    @FXML
    private TableColumn<?, ?> maCaLamCol;

    @FXML
    private TableColumn<?, ?> maCongNhanCol;


    @FXML
    private TableColumn<?, ?> soLuongCol;

    @FXML
    private TableColumn<?, ?> tenCongDoan;

    @FXML
    private DatePicker txtNgayChamCong;

    @FXML
    private TextField txtTimCongNhan;
    @FXML
    private TableColumn<?, ?> ghiChuCol;
    @FXML
    private TableView<DTO_TBW_ChamCongCongNhan> tblBCCCN;
    private BUS_CongNhan bus_congNhan;

    private boolean nowCC = true;

    private ArrayList<DTO_BCCCongNhan> dsBCCDaCham;
    private ArrayList<DTO_BCCCongNhan> dsBCCHienTai;

    private Dialog dialog;

    private  BUS_ChamCongCN bus_chamCongCN;

    private final int COMAT = 1;
    private final int PHEP = 2;
    private final int VANG = 0;
    private HashMap<String, Integer> dicCongNhanSoLuong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dicCongNhanSoLuong = new HashMap<>();
        // khởi tạo các bus và các danh sách
        cboMaCaLam.setItems(FXCollections.observableArrayList("Tất cả","1","2","3"));
        cboMaCaLam.setValue("Tất cả");
        cboTenCaLam.setValue("Tất cả");
        cboTenCaLam.setItems(FXCollections.observableArrayList("Tất cả","Sáng","Chiều","Tối"));
        bus_congNhan = new BUS_CongNhan();
        try {
            bus_chamCongCN = new BUS_ChamCongCN();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dsBCCHienTai = new ArrayList<>();
        dsBCCDaCham = new ArrayList<>();

        LocalDate ngayHienTai = Instant.ofEpochMilli(new Date().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        txtNgayChamCong.setValue(ngayHienTai);

        maCongNhanCol.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuongSanPhamLamDuoc"));
        hienDienCol.setCellValueFactory(new PropertyValueFactory<>("hienDien"));
        ghiChuCol.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        try {
            loadDanhSachChamCongHomNay();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            loadDanhSachChamCongHomNay();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // băt sự kiện trên các componet
        txtNgayChamCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // ngày chấm công lơn hơn ngày hiện tại
                Date ngayCC = Date.from(txtNgayChamCong.getValue().atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant());
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                if (df.format(ngayCC).equals(df.format(new Date()))){
                    // đã chấm cho hôm nay rồi
                    if (!nowCC){
                        try {
                            loadDanhSachChamCongDaCham();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                    nowCC = true;
                    tblBCCCN.setDisable(false);
                    try {
                        loadDanhSachChamCongHomNay();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                if (ngayCC.after(new Date())) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Ngày chấm công phải bé hơn ngày hiện tại");
                    Optional<ButtonType> a = er.showAndWait();
                    txtNgayChamCong.setValue(ngayHienTai);
                }
                else if (ngayCC.before(new Date())){
                    try {
                        loadDanhSachChamCongDaCham();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    tblBCCCN.setDisable(true);
                }
            }
        });

        cboMaCaLam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cboTenCaLam.getSelectionModel().select(cboMaCaLam.getSelectionModel().getSelectedIndex());
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    insertToDataBase();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }

    private void loadDanhSachChamCongDaCham() throws SQLException, ParseException {
        System.out.println(tblBCCCN.getItems());
        tblBCCCN.setDisable(true);
        tblBCCCN.getItems().clear();
        tblBCCCN.setItems(cover(FXCollections.observableArrayList(bus_chamCongCN.getDSBCCNTheoNgay(txtNgayChamCong.getValue().toString()))));
    }

    private void loadDanhSachChamCongHomNay() throws SQLException, ParseException {
        // check đã chấm công hôm nay hay chưa
        ArrayList<DTO_BCCCongNhan> checkTMP = bus_chamCongCN.getDSBCCNTheoNgay(txtNgayChamCong.getValue().toString());
        if (!checkTMP.isEmpty()){
            loadDanhSachChamCongDaCham();
            return;
        }
        dsBCCHienTai = new ArrayList<>();
        ArrayList<DTO_CongNhan> dsCN = bus_congNhan.getDSCongNhanDuocPhanCong(txtNgayChamCong.getValue().toString());
        int numNhanVien = dsCN.size();
        for (int i = 0; i < numNhanVien; i++) {
            String maBCC = taoMaBCC(dsCN.get(i).getMaCongNhan());
            int soLuong = 0;
            dsBCCHienTai.add(new DTO_BCCCongNhan(dsCN.get(i), 1,0, new Date(), maBCC, ""));
        }
        tblBCCCN.setItems(cover(FXCollections.observableArrayList(dsBCCHienTai)));
    }

    void closeDialog() {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

    void insertToDataBase() throws SQLException {
        bus_chamCongCN.insertBCCNVToDatabase(dsBCCHienTai);
    }

    private String taoMaBCC(String maNV){
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        return "CCCN"+date +maNV;
    }

    private ObservableList<DTO_TBW_ChamCongCongNhan> cover(ObservableList<DTO_BCCCongNhan> bcc) throws SQLException {
        ObservableList<DTO_TBW_ChamCongCongNhan> list = FXCollections.observableArrayList();
        for (DTO_BCCCongNhan it:
                bcc) {
            ComboBox<String> cbo = new ComboBox<>();
            TextField t = new TextField(it.getGhiChu());
            TextField t1 = new TextField(String.valueOf(it.getSoLuongSanPhamLamDuoc()));
            cbo.setItems(FXCollections.observableArrayList("Vắng","Có mặt","Phép"));
            cbo.getSelectionModel().select(it.getHienDien());
            DTO_TBW_ChamCongCongNhan tmp = new DTO_TBW_ChamCongCongNhan(it.getCongNhan().getMaCongNhan(), it.getCongNhan().getTenCongNhan(), cbo, t, t1);
            tmp.setSoSanPhamDuocPhanCong(new BUS_PhanCong().getSoLuongPC(it.getCongNhan().getMaCongNhan(), txtNgayChamCong.getValue().toString()));

            list.add(tmp);
        }
        return list;
    }





}
