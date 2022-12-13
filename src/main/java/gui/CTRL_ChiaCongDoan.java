package gui;

import bus.BUS_ChiTietCongDoan;
import bus.BUS_CongDoan;
import bus.BUS_SanPham;
import dto.DTO_ChiTietCongDoan;
import dto.DTO_CongDoan;
import dto.DTO_SanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTRL_ChiaCongDoan implements Initializable {
    @FXML
    private TableColumn<?, ?> maSanPhamCol;

    @FXML
    private TableColumn<?, ?> soCongDoanCol;

    @FXML
    private TableColumn<?, ?> soLuongCol;
    @FXML
    private TableView<DTO_SanPham> tblSanPham;

    @FXML
    private TableColumn<?, ?> tenCongDoanCol;

    @FXML
    private TableColumn<?, ?> tenSanPhamCol;

    @FXML
    private TextField txtMaSanPham;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTenCongDoan;

    @FXML
    private TextField txtTenSanPham;

    @FXML
    private TextField txtTimCongDoan;

    @FXML
    private TextField txtTimSanPham;

    private DTO_CongDoan congDoanClick;

    private DTO_SanPham sanPhamClick;

    private BUS_SanPham bus_sanPham;

    private BUS_CongDoan bus_congDoan;

    private ObservableList<DTO_SanPham> modelSanPham;

    private ObservableList<DTO_CongDoan> modelCongDoan;

    private ObservableList<DTO_ChiTietCongDoan> modelCTCongDoan;

    private BUS_ChiTietCongDoan bus_chiTietCongDoan;


    @FXML
    private TableView<DTO_CongDoan> tblCongDoan;

    @FXML
    private TableColumn<?, ?> donGiaCol;

    @FXML
    private TableColumn<?, ?> donGiaCongDoanCol;

    @FXML
    private TableColumn<?, ?> maCongDoanCol;
    @FXML
    private TextField txtMaCongDoan;

    @FXML
    private Button btnThem;

    @FXML
    private TableColumn<?, ?> tenCongDoanPCDCol;
    @FXML
    private TableColumn<?, ?> tenSanPhamPCDCol;

    @FXML
    private TableColumn<?, ?> maSanPhamPCDCol;
    @FXML
    private TableColumn<?, ?> maCongDoanPCDCol;

    @FXML
    private TableColumn<?, ?> donGiaPCDCol;

    @FXML
    private TableView<DTO_ChiTietCongDoan> tblPCCD;

    @FXML
    private TableColumn<DTO_ChiTietCongDoan, ?> soLuongYeuCauPCDCol;

    private ObservableList<DTO_ChiTietCongDoan> ct_CongDoanBanDau;

    @FXML
    private Button btnDatLai;

    @FXML
    private Button btnLuu;

    @FXML
    private Button btnXoa;
    private ArrayList<DTO_CongDoan> listCongDoan;
    @FXML
    private Button btnThemCD;
    @FXML
    private Button btnSuaGia;
    @FXML
    private Button btnXoaCD;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bus_chiTietCongDoan = new BUS_ChiTietCongDoan();
        bus_sanPham = new BUS_SanPham();
        bus_congDoan = new BUS_CongDoan();
        tblCongDoan.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        loadSanPham();
        loadCongDong();
        loadCTCongDoan();
        handleEvent();

    }

    /***
     * Phương thức load dữ liệu lên bảng sản phẩm
     */
    private void loadSanPham() {
        ArrayList<DTO_SanPham> listSanPham = bus_sanPham.getAllSanPham();
        maSanPhamCol.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        tenSanPhamCol.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        soCongDoanCol.setCellValueFactory(new PropertyValueFactory<>("soCongDoan"));
        soLuongCol.setCellValueFactory(new PropertyValueFactory<>("soLuongYeuCau"));

        modelSanPham = FXCollections.observableArrayList();


        for (DTO_SanPham it :
                listSanPham) {
            if (!it.isTrangThai())
                modelSanPham.add(it);
        }

        tblSanPham.setItems(modelSanPham);

    }

    /***
     * Phương thức load dữ liệu lên bản công đoạn
     */
    private void loadCongDong() {

        try {
            listCongDoan = new BUS_CongDoan().getAllCongDoan();
            maCongDoanCol.setCellValueFactory(new PropertyValueFactory<>("maCongDoan"));
            tenCongDoanCol.setCellValueFactory(new PropertyValueFactory<>("tenCongDoan"));
            donGiaCongDoanCol.setCellValueFactory(new PropertyValueFactory<>("donGiaCongDoan"));
            modelCongDoan = FXCollections.observableArrayList(listCongDoan);
            tblCongDoan.setItems(modelCongDoan);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Đăng kí sự kiện cho các component
     */
    private void handleEvent() {

        // Sự kiện trên bảng sản phẩm
        tblSanPham.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                listCongDoan = bus_congDoan.getAllCongDoan();


                sanPhamClick = tblSanPham.getSelectionModel().getSelectedItem();
                txtMaSanPham.setText(sanPhamClick.getMaSanPham());
                txtTenSanPham.setText(sanPhamClick.getTenSanPham());
                txtSoLuong.setText(sanPhamClick.getSoLuongYeuCau() + "");

                //tblPCCD.getItems().clear();
                tblPCCD.setItems(FXCollections.observableArrayList(bus_chiTietCongDoan.getDSTheoMaSP(sanPhamClick.getMaSanPham())));
                sinhThuTuCongDoan(sanPhamClick.getSoCongDoan());

                modelCongDoan = FXCollections.observableArrayList(listCongDoan);
                for (DTO_ChiTietCongDoan it:
                     tblPCCD.getItems()) {
                    modelCongDoan.remove(it.getCongDoan());
                }
                tblCongDoan.setItems(modelCongDoan);

                ct_CongDoanBanDau = tblPCCD.getItems();
                // Ẩn đi các nút khi sản phẩm đã được chia công đoaạn

                if(checkCongDoan() == 1){
                    btnXoa.setDisable(true);
                    btnLuu.setDisable(true);
                    btnDatLai.setDisable(true);
                } else {
                    btnXoa.setDisable(false);
                    btnLuu.setDisable(false);
                    btnDatLai.setDisable(false);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

        });

        // Sự kiện trên bảng công đoạn
        tblCongDoan.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            tblSanPham.getSelectionModel().clearSelection();
            congDoanClick = tblCongDoan.getSelectionModel().getSelectedItem();
            txtMaCongDoan.setText(congDoanClick.getMaCongDoan());
            txtTenCongDoan.setText(congDoanClick.getTenCongDoan());
        });

        // Sự kiện trên nút thêm
        btnThem.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (sanPhamClick == null) {
                Alert al = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn sản phẩm", ButtonType.APPLY);
                al.showAndWait();
                return;
            }

            if (sanPhamClick.getSoCongDoan() <= tblPCCD.getItems().size()) {
                Alert al = new Alert(Alert.AlertType.ERROR, "Sản phẩm này đã đầy đủ công đoạn", ButtonType.APPLY);
                al.showAndWait();
                return;
            }


            if (congDoanClick == null) {
                Alert al = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn công đoạn", ButtonType.APPLY);
                al.showAndWait();
                return;
            }
            try {
                handleThemCT();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }


        });

        // Sự kiện trên nút đặt lại
        btnDatLai.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    tblCongDoan.setItems(FXCollections.observableArrayList(bus_congDoan.getAllCongDoan()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // xoa chitietcongdoan da them
                bus_chiTietCongDoan.rmDSTheoMaSP(bus_chiTietCongDoan.getDSTheoMaSP(sanPhamClick.getMaSanPham()));
                tblPCCD.getItems().clear();
                tblPCCD.setItems(FXCollections.observableArrayList());
            }
        });

        // sự kiện tìm kiếm trên các table view
        fillTableSanPham();
        filerTableCongDoan();

        btnXoa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // xóa trong RAM
                bus_chiTietCongDoan.rmCTCD(tblPCCD.getSelectionModel().getSelectedItem());
                modelCongDoan.add(tblPCCD.getSelectionModel().getSelectedItem().getCongDoan());
                tblPCCD.getItems().remove(tblPCCD.getSelectionModel().getSelectedItem());

            }
        });

        // Sự kiện trên nút lưu
        btnLuu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (tblPCCD.getItems().size() < sanPhamClick.getSoCongDoan()) {
                        System.out.println(tblCongDoan.getItems().size() +"  "+ sanPhamClick.getSoCongDoan());
                        Alert al = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn đủ số công đoạn trước khi lưu", ButtonType.APPLY);
                        al.showAndWait();
                        return;
                    }
                    bus_chiTietCongDoan.luuCTCDCuaSanPham(tblPCCD.getItems());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Sự kiện khi thêm một công đoạn mới
        btnThemCD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Diaglog_CongDoan.fxml"));
                    DialogPane addDialog = fxmlLoader.load();
                    Dialog dialog = new Dialog<>();
                    dialog.setDialogPane(addDialog);
                    dialog.setTitle("Thêm Công Đoạn");
                    CTL_DiaglogCongDoan controller = fxmlLoader.getController();
                    controller.setData(true, new DTO_CongDoan());
                    Optional<ButtonType> clickedButton = dialog.showAndWait();

                    if (clickedButton.get() == ButtonType.OK) {
                        bus_congDoan.insertCongDoan(controller.getData());
                        modelCongDoan = FXCollections.observableArrayList(bus_congDoan.getAllCongDoan());
                        tblCongDoan.setItems(modelCongDoan);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


            }
        });

        // Sự kiện trên nút sửa giá
        btnSuaGia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tblCongDoan.getSelectionModel().getSelectedItem() == null) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn công đoạn cần sửa", ButtonType.OK);
                    return;
                }


                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Diaglog_CongDoan.fxml"));
                DialogPane addDialog = null;
                try {
                    addDialog = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Dialog dialog = new Dialog<>();
                dialog.setDialogPane(addDialog);
                dialog.setTitle("Sửa Giá Công Đoạn");
                CTL_DiaglogCongDoan controller = fxmlLoader.getController();
                DTO_CongDoan congDoanSua = tblCongDoan.getSelectionModel().getSelectedItem();
                controller.setData(false, congDoanSua);
                Optional<ButtonType> clickedButton = dialog.showAndWait();

                if (clickedButton.get() == ButtonType.OK) {
                    modelCongDoan.remove(tblCongDoan.getSelectionModel().getSelectedItem());
                    modelCongDoan.add(controller.getData());
                    tblCongDoan.setItems(modelCongDoan);
                    //bus_congDoan.update();
                }
            }
        });

        // Sự kiện trên nút xóa công đoạn
        btnXoaCD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tblCongDoan.getSelectionModel().getSelectedItem() == null) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn công đoạn cần sửa", ButtonType.OK);
                } else {
                    try {
                        bus_congDoan.removeCD(tblCongDoan.getSelectionModel().getSelectedItem().getMaCongDoan());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    modelCongDoan.remove(tblCongDoan.getSelectionModel().getSelectedItem());
                    tblCongDoan.getSelectionModel().clearSelection();
                }
            }
        });


    }

    /**
     * Hàm loading công đoạn khi khởi động chương trình
     * @param model
     */
    private void loadCongDoan(ObservableList<DTO_ChiTietCongDoan> model) {
        maSanPhamPCDCol.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        tenSanPhamPCDCol.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        soCongDoanCol.setCellValueFactory(new PropertyValueFactory<>("soCongDoan"));
        maCongDoanPCDCol.setCellValueFactory(new PropertyValueFactory<>("maCongDoan"));
        tenCongDoanPCDCol.setCellValueFactory(new PropertyValueFactory<>("tenCongDoan"));
        donGiaCongDoanCol.setCellValueFactory(new PropertyValueFactory<>("donGiaCongDoan"));
        soLuongYeuCauPCDCol.setCellValueFactory(new PropertyValueFactory<>("soLuongYeuCau"));
        donGiaPCDCol.setCellValueFactory(new PropertyValueFactory<>("donGiaCongDoan"));

        tblPCCD.setItems(model);


    }

    /**
     * Hàm khowri tạo bảng CTCD
     */
    private void loadCTCongDoan() {
        ArrayList<DTO_ChiTietCongDoan> listCTCD;
        try {


            listCTCD = bus_chiTietCongDoan.getAllChiTietCongDoan();
            //ct_CongDoanBanDau = listCTCD;
            //modelCTCongDoan = FXCollections.observableArrayList(listCTCD);
            loadCongDoan(modelCTCongDoan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hàm thêm vào table chia công đoạn
     */
    private void handleThemCT() throws SQLException, ParseException {

        ObservableList<DTO_CongDoan> listCDThem = FXCollections.observableArrayList();
        listCDThem = tblCongDoan.getSelectionModel().getSelectedItems();
        //Nếu số công đoạn đã chọn quá nhiều
        if (listCDThem.size() + tblPCCD.getItems().size() > sanPhamClick.getSoCongDoan()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Số lượng công đoạn đã chọn vượt quá số công đoạn của sản phẩm", ButtonType.CANCEL);
            a.showAndWait();
            return;
        }

        /**
         * Kiểm tra thử là click vào một công đoạn hay nhiều công đoạn
         */
        int i = 0;
        if (listCDThem.size() == 1) {
            try {

                DTO_ChiTietCongDoan ct = new DTO_ChiTietCongDoan(sanPhamClick, congDoanClick, sinhMaCTCD(sanPhamClick.getMaSanPham(), congDoanClick.getMaCongDoan(), i++)/*sinhMaCTCD(tblSanPham.getSelectionModel().getSelectedItem().getMaSanPham(), tblCongDoan.getSelectionModel().getSelectedItem().getMaCongDoan(), cboThuTu.getValue())*/);
                bus_chiTietCongDoan.addCTCD(ct);
                modelCongDoan.remove(tblCongDoan.getSelectionModel().getSelectedItem());
                tblPCCD.getItems().add(ct);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else { // Nhiều công đoạn
            for (DTO_CongDoan it : listCDThem) {
                DTO_ChiTietCongDoan ct = new DTO_ChiTietCongDoan(sanPhamClick, it, sinhMaCTCD(sanPhamClick.getMaSanPham(), it.getMaCongDoan(), i++)/*sinhMaCTCD(tblSanPham.getSelectionModel().getSelectedItem().getMaSanPham(), tblCongDoan.getSelectionModel().getSelectedItem().getMaCongDoan(), cboThuTu.getValue())*/);
                bus_chiTietCongDoan.addCTCD(ct);
                tblPCCD.getItems().add(ct);
            }
            // xóa các công đoạn ra khỏi table

            modelCongDoan = FXCollections.observableArrayList(bus_congDoan.getAllCongDoan());
            modelCongDoan.removeAll(listCDThem);


            tblCongDoan.setItems(FXCollections.observableArrayList(modelCongDoan));
        }


    }

    private void fillTableSanPham() {
        FilteredList<DTO_SanPham> filteredListSanPhams = new FilteredList<>(modelSanPham, b -> true);
        txtTimSanPham.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListSanPhams.setPredicate(dto_sanPham -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (dto_sanPham.getMaSanPham().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else // Does not match.
                    if (dto_sanPham.getTenSanPham().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else return String.valueOf(dto_sanPham.getSoCongDoan()).indexOf(lowerCaseFilter) != -1;
            });
        });
        SortedList<DTO_SanPham> sortedData = new SortedList<>(filteredListSanPhams);
        sortedData.comparatorProperty().bind(tblSanPham.comparatorProperty());
        tblSanPham.setItems(sortedData);
    }

    /**
     * Hàm tìm kiếm trên bang công đoạn
     */
    private void filerTableCongDoan() {
        FilteredList<DTO_CongDoan> filteredListCongDoan = new FilteredList<>(modelCongDoan, b -> true);
        txtTimCongDoan.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListCongDoan.setPredicate(dto_congDoan -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (dto_congDoan.getMaCongDoan().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else // Does not match.
                    if (dto_congDoan.getTenCongDoan().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else return String.valueOf(dto_congDoan.getDonGiaCongDoan()).indexOf(lowerCaseFilter) != -1;
            });
        });
        SortedList<DTO_CongDoan> sortedData = new SortedList<>(filteredListCongDoan);
        sortedData.comparatorProperty().bind(tblCongDoan.comparatorProperty());
        tblCongDoan.setItems(sortedData);
    }

    private String sinhMaCTCD(String maSP, String maCD, int stt) {
        return "CTCD" + maSP + maCD + stt;
    }

    /**
     * Hàm sinh thứ tự cho công đoạn
     * @param slcd
     */
    private void sinhThuTuCongDoan(int slcd) {
        String hienThi = slcd < tblPCCD.getItems().size() + 1 ? "" : String.valueOf(tblPCCD.getItems().size() + 1);
    }

    /**
     * Hàm kiểm tra công đoạn đã chia hoàn thành hay chưa
     * @return int
     * @throws SQLException
     */
    private int checkCongDoan() throws SQLException {
        return bus_chiTietCongDoan.checkCongDoan(tblSanPham.getSelectionModel().getSelectedItem().getMaSanPham());
    }

}
