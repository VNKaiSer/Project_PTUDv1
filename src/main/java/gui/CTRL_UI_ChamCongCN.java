package gui;

import bus.BUS_ChamCongCN;
import bus.BUS_CongNhan;
import bus.BUS_PhanCong;
import dto.DTO_BCCCongNhan;
import dto.DTO_CongNhan;
import dto.DTO_TBW_ChamCongCongNhan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CTRL_UI_ChamCongCN implements Initializable {
    private final int COMAT = 1;
    private final int PHEP = 2;
    private final int VANG = 0;
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
    private BUS_ChamCongCN bus_chamCongCN;
    private boolean[] chotCa;
    private ArrayList<DTO_CongNhan> dsBanDau;
    private HashMap<String, Integer> dicCongNhanSoLuong;


    /**
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chotCa = new boolean[]{false, false, false};
        btnLuu.setDisable(true);
        handleChamSanPham();
        // khởi tạo các bus và các danh sách
        cboMaCaLam.setItems(FXCollections.observableArrayList("Tất cả", "1", "2", "3"));
        cboMaCaLam.setValue("Tất cả");
        cboTenCaLam.setValue("Tất cả");
        tblBCCCN.setDisable(true);
        cboTenCaLam.setItems(FXCollections.observableArrayList("Tất cả", "Sáng", "Chiều", "Tối"));
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

        // loading bảng chấm công
        try {
            loadDanhSachChamCongHomNay();
            dsBanDau = new BUS_CongNhan().getDSCongNhan();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // đọc dữ liệu vào hash map


        // băt sự kiện trên các componet
        /*txtNgayChamCong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // ngày chấm công lơn hơn ngày hiện tại
                Date ngayCC = Date.from(txtNgayChamCong.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                if (df.format(ngayCC).equals(df.format(new Date()))) {
                    // đã chấm cho hôm nay rồi
                    if (!nowCC) {
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
                } else if (ngayCC.before(new Date())) {
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
        });*/
//        txtNgayChamCong.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//        });

        cboMaCaLam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (chotCa[0] == true && cboMaCaLam.getSelectionModel().getSelectedIndex() == 1){
                    tblBCCCN.setDisable(true);
                    btnLuu.setDisable(true);
                    return;
                }

                if (chotCa[1] == true && cboMaCaLam.getSelectionModel().getSelectedIndex() == 2){
                    tblBCCCN.setDisable(true);
                    btnLuu.setDisable(true);
                    return;
                }

                if (chotCa[2] == true && cboMaCaLam.getSelectionModel().getSelectedIndex() == 3){
                    tblBCCCN.setDisable(true);
                    btnLuu.setDisable(true);
                    return;
                }
                if (cboMaCaLam.getSelectionModel().getSelectedIndex() == 0) {
                    try {
                        tblBCCCN.setItems(coverDaCham(FXCollections.observableArrayList(bus_chamCongCN.getDSBCCNTheoNgay(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(txtNgayChamCong.getValue())))));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                if (!chotCa[0] && cboMaCaLam.getSelectionModel().getSelectedIndex() != 1) {
                    Alert a3 = new Alert(Alert.AlertType.ERROR, "Chưa được chấm công cho ca này", ButtonType.OK);
                    a3.showAndWait();
                    cboMaCaLam.setValue("1");
                    return;
                }

                if (!chotCa[1] && cboMaCaLam.getSelectionModel().getSelectedIndex() == 3) {
                    Alert a3 = new Alert(Alert.AlertType.ERROR, "Chưa được chấm công cho ca này", ButtonType.OK);
                    a3.showAndWait();
                    cboMaCaLam.setValue("2");
                    return;
                }

                cboTenCaLam.getSelectionModel().select(cboMaCaLam.getSelectionModel().getSelectedIndex());
//                btnLuu.setDisable(cboMaCaLam.getSelectionModel().getSelectedIndex() == 0);
                tblBCCCN.setDisable(false);
                btnLuu.setDisable(false);
                if (cboMaCaLam.getSelectionModel().getSelectedIndex() == 0) {
                    try {
                        tblBCCCN.setDisable(true);
                        loadDanhSachChamCongHomNay();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        tblBCCCN.setDisable(false);
                        layDanhSachCongNhanTheoCa();
                    } catch (SQLException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (cboMaCaLam.getSelectionModel().getSelectedIndex() == 3) {
                        capNhatSanPhamCuaNhanVien();
                        Alert a4 = new Alert(Alert.AlertType.INFORMATION, "Hoàn thành chấm công bạn có chắc muốn lưu không", ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> rs = a4.showAndWait();
                        if (rs.get() == ButtonType.YES) {
                            insertToDataBase();
                            Alert a3 = new Alert(Alert.AlertType.INFORMATION, "Chấm công thành công", ButtonType.OK);
                            a3.showAndWait();
                            tblBCCCN.setDisable(true);
                        }
                    } else {
                        Alert a = new Alert(Alert.AlertType.WARNING, "Mỗi ca chỉ được chấm công một lần! Bạn có chắc chắn muốn lưu không?", ButtonType.NO, ButtonType.YES);
                        Optional<ButtonType> bt = a.showAndWait();
                        // nếu nhấn vào nút yes
                        if (bt.get() == ButtonType.YES) {
                            int getCaCham = cboMaCaLam.getSelectionModel().getSelectedIndex();
                            // nếu đã chấm công rồi
                            if (chotCa[getCaCham - 1]) {
                                Alert a2 = new Alert(Alert.AlertType.ERROR, "Ca này bạn đã chấm công rồi");
                                a.showAndWait();
                            } else {
                                capNhatSanPhamCuaNhanVien();
                                tblBCCCN.setDisable(true);
                                btnLuu.setDisable(true);
                                chotCa[getCaCham - 1] = true;
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }

    private void loadDanhSachChamCongDaCham() throws SQLException, ParseException {
        tblBCCCN.setDisable(true);
        tblBCCCN.getItems().clear();
        tblBCCCN.setItems(cover(FXCollections.observableArrayList(bus_chamCongCN.getDSBCCNTheoNgay(txtNgayChamCong.getValue().toString()))));
    }

    private void loadDanhSachChamCongHomNay() throws SQLException, ParseException {
        // check đã chấm công hôm nay hay chưa
        ArrayList<DTO_BCCCongNhan> checkTMP = bus_chamCongCN.getDSBCCNTheoNgay(txtNgayChamCong.getValue().toString());
        if (!checkTMP.isEmpty()) {
            loadDanhSachChamCongDaCham();
            chotCa[0] = true; chotCa[1] = true; chotCa[2] = true;
            btnLuu.setVisible(false);
            return;
        }
        dsBCCHienTai = new ArrayList<>();
        HashSet<DTO_CongNhan> dsCN = bus_congNhan.getDSCongNhanDuocPhanCong(txtNgayChamCong.getValue().toString());
        List<DTO_CongNhan> list = new ArrayList<DTO_CongNhan>(dsCN);
        int numNhanVien = dsCN.size();
        for (int i = 0; i < numNhanVien; i++) {
            String maBCC = "";
            int soLuong = 0;
            dsBCCHienTai.add(new DTO_BCCCongNhan(list.get(i), 1, 0, new Date(), maBCC, ""));
        }
        tblBCCCN.setItems(cover(FXCollections.observableArrayList(dsBCCHienTai)));
    }

    void closeDialog() {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

    /**
     * Tạo phiếu lương và lưu vào database
     *
     * @throws SQLException
     */
    void insertToDataBase() throws SQLException {

        // Tạo phiếu lương cho nhân viên
        Set<String> keySet = dicCongNhanSoLuong.keySet();
        dsBCCHienTai = new ArrayList<>();
        for (String key : keySet) {
            int hienDien = 0;
            if (dicCongNhanSoLuong.get(key) == 0)
                hienDien = 0;
            else
                hienDien = 1;
            DTO_CongNhan tmp_CN = findCongNhan(key);
            Date ngayCC = Date.from(txtNgayChamCong.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            DTO_BCCCongNhan tmp = new DTO_BCCCongNhan(tmp_CN, hienDien, dicCongNhanSoLuong.get(key), ngayCC, taoMaBCC(tmp_CN.getMaCongNhan()), "");
            dsBCCHienTai.add(tmp);
        }
        bus_chamCongCN.insertBCCNVToDatabase(dsBCCHienTai);
        cboMaCaLam.setValue("Tất cả");
    }

    private String taoMaBCC(String maNV) {
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        return "CCCN" + date + maNV;
    }

    private ObservableList<DTO_TBW_ChamCongCongNhan> cover(ObservableList<DTO_BCCCongNhan> bcc) throws SQLException {
        ObservableList<DTO_TBW_ChamCongCongNhan> list = FXCollections.observableArrayList();
        for (DTO_BCCCongNhan it :
                bcc) {
            ComboBox<String> cbo = new ComboBox<>();
            TextField t = new TextField(it.getGhiChu());
            TextField t1 = new TextField(String.valueOf(it.getSoLuongSanPhamLamDuoc()));
            cbo.setItems(FXCollections.observableArrayList("Vắng", "Có mặt", "Phép"));
            cbo.getSelectionModel().select(it.getHienDien());

            DTO_TBW_ChamCongCongNhan tmp = new DTO_TBW_ChamCongCongNhan(it.getCongNhan().getMaCongNhan(), it.getCongNhan().getTenCongNhan(), cbo, t, t1, new BUS_PhanCong().getSoLuongPC(it.getCongNhan().getMaCongNhan(), txtNgayChamCong.getValue().toString(), cboMaCaLam.getSelectionModel().getSelectedIndex()));
            list.add(tmp);
        }
        return list;
    }

    private ObservableList<DTO_TBW_ChamCongCongNhan> coverDaCham(ObservableList<DTO_BCCCongNhan> bcc) throws SQLException {
        ObservableList<DTO_TBW_ChamCongCongNhan> list = FXCollections.observableArrayList();
        for (DTO_BCCCongNhan it :
                bcc) {
            ComboBox<String> cbo = new ComboBox<>();
            TextField t = new TextField(it.getGhiChu());
            TextField t1 = new TextField(String.valueOf(it.getSoLuongSanPhamLamDuoc()));
            cbo.setItems(FXCollections.observableArrayList("Vắng", "Có mặt", "Phép"));
            cbo.getSelectionModel().select(it.getHienDien());

            DTO_TBW_ChamCongCongNhan tmp = new DTO_TBW_ChamCongCongNhan(it.getCongNhan().getMaCongNhan(), it.getCongNhan().getTenCongNhan(), cbo, t, t1, it.getSoLuongSanPhamLamDuoc());
            list.add(tmp);
        }
        return list;
    }

    public void layDanhSachCongNhanTheoCa() throws SQLException, ParseException {
        String ngayPC = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(txtNgayChamCong.getValue());
        ArrayList<String> maNhanVienTheoCa = new BUS_PhanCong().getDSMaCongNhanTheoCa(ngayPC, Integer.parseInt(cboMaCaLam.getValue()));

        loadDanhSachChamCongHomNay();
        ObservableList<DTO_TBW_ChamCongCongNhan> tmp = tblBCCCN.getItems();


        ArrayList<DTO_TBW_ChamCongCongNhan> modelCNByCa = new ArrayList<>();

        for (String it : maNhanVienTheoCa) {
            for (DTO_TBW_ChamCongCongNhan cn :
                    tmp) {
                if (cn.getMaCongNhan().equals(it))
                    modelCNByCa.add(cn);
            }
        }
        tblBCCCN.getItems().removeAll();
        tblBCCCN.setItems(FXCollections.observableArrayList(modelCNByCa));
    }

    /**
     * Hàm khởi tạo map lưu số lượng sản phẩm của công nhân
     */
    public void handleChamSanPham() {
        dicCongNhanSoLuong = new HashMap<>();
        for (DTO_TBW_ChamCongCongNhan it :
                tblBCCCN.getItems()) {
            dicCongNhanSoLuong.put(it.getMaCongNhan(), 0);
        }
    }

    public void capNhatSanPhamCuaNhanVien() {
        int i = 0;

        for (DTO_TBW_ChamCongCongNhan it :
                tblBCCCN.getItems()) {
            dicCongNhanSoLuong.merge(it.getMaCongNhan(), Integer.parseInt(it.getSoLuongSanPhamLamDuoc().getText()), Integer::sum);
        }
    }

    private DTO_CongNhan findCongNhan(String maCN) {
        for (DTO_CongNhan it :
                dsBanDau) {
            if (maCN.equals(it.getMaCongNhan())) {
                return it;
            }
        }
        return null;
    }
}
