package gui;

import bus.BUS_API;
import bus.BUS_CongNhan;
import db.ConnectDB;
import dto.DTO_CongNhan;
import dto.DTO_NhanVien;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTL_UI_CongNhan implements Initializable {
    @FXML
    private Button btnLamMoi;

    @FXML
    private TextField txtHoTen;
    @FXML

    private Button btnXuatDanhSach;

    @FXML
    private Button btn_themDanhSach;
    @FXML
    private TableColumn<DTO_CongNhan, String> diaChiCol;

    @FXML
    private TableColumn<DTO_CongNhan, String> emailCol;

    @FXML
    private TableColumn<DTO_CongNhan, Boolean> gioiTinhCol;

    @FXML
    private TableColumn<DTO_CongNhan, String> hoTenCol;

    @FXML
    private TableColumn<DTO_CongNhan, String> maCNCol;

    @FXML
    private TableColumn<DTO_CongNhan, String> ngaySinhCol;

    @FXML
    private TableColumn<DTO_CongNhan, String> ngayVaoLamCol;

    @FXML
    private TableColumn<?, ?> so;

    @FXML
    private TableColumn<DTO_CongNhan, String> soDienThoaiCol;

    @FXML
    private TableColumn<DTO_CongNhan, String> trinhDoCol;

    @FXML
    private TableView<DTO_CongNhan> tblCongNhan;
    @FXML
    private Button btnXoa;

    private ObservableList<DTO_CongNhan> listCongNhan;

    public static Dialog<ButtonType> dialogSua;

    @FXML
    public ComboBox<String> cboGioiTinh;

    @FXML
    public ComboBox<String> cboTrinhDo;
    @FXML
    private Button btnSua;
    @FXML
    private Button btnThem;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;


    @FXML
    private TextField txtMaCN;

    @FXML
    private DatePicker txtNgaySinh;

    @FXML
    private DatePicker txtNgayVaoLam;

    @FXML
    private TextField txtSoDienThoai;

    static BUS_API connectAPI;
    @FXML
    private ComboBox<String> cboHuyen;

    @FXML
    private ComboBox<String> cboPhuong;

    @FXML
    private ComboBox<String> cboTinh;
    private boolean checkFrom = false;
    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
    ObservableList<String> listTrinhDo = FXCollections.observableArrayList("Trung học phổ thông", "Cao đẳng", "Đại học", "Cao học");

    @FXML
    private TextField txtTimCongNhan;
    private boolean checkLuu = false;
    private boolean checkXoa = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectAPI = new BUS_API();
        // thêm các sự kiện kiểm lỗi cho các componet
        try {
            // table
            maCNCol.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
            hoTenCol.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
            diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
            gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("phai"));
            ngayVaoLamCol.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
            ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
            soDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            trinhDoCol.setCellValueFactory(new PropertyValueFactory<>("trinhDo"));

            txtMaCN.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtMaCN.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtMaCN.getText().toString().equals("")) {
                        txtMaCN.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtHoTen.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtHoTen.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtHoTen.getText().toString().equals("")) {
                        txtHoTen.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtNgayVaoLam.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent keyEvent) {
                    txtNgayVaoLam.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtNgayVaoLam.getValue().toString().equals(null)) {
                        txtNgayVaoLam.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }

                    Date ngayVaoLam = Date.from(txtNgayVaoLam.getValue().atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant());
                    if (getDiffDate(ngayVaoLam, new Date()) <= 0) {
                        Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                        er.setContentText("Ngày vào làm phải bé hơn ngày hiện tại");
                        txtNgayVaoLam.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                        Optional<ButtonType> a = er.showAndWait();
                    }
                }
            });

            txtNgaySinh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent keyEvent) {
                    txtNgaySinh.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtNgaySinh.getValue()==null) {
                        txtNgaySinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }

                    Date ngaySinh = Date.from(txtNgaySinh.getValue().atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant());
                    if (new Date().getYear() - ngaySinh.getYear() < 18 || new Date().getYear() - ngaySinh.getYear() > 60) {
                        Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                        er.setContentText("Nhân viên phải từ 18-60 tuổi");
                        txtNgaySinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                        Optional<ButtonType> a = er.showAndWait();
                    }
                }
            });

            cboGioiTinh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboGioiTinh.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboGioiTinh.getValue().toString().equals("")) {
                        cboGioiTinh.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            cboTrinhDo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboTrinhDo.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboTrinhDo.getValue().toString().equals("")) {
                        cboTrinhDo.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtSoDienThoai.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtSoDienThoai.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtSoDienThoai.getText().toString().equals("")) {
                        txtSoDienThoai.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });

            txtSoDienThoai.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txtSoDienThoai.setText(newValue.replaceAll("[^\\d,]", ""));
                    StringBuilder aus = new StringBuilder();
                    aus.append(txtSoDienThoai.getText());
                    boolean firstPointFound = false;

//                    for (int i = 0; i < aus.length(); i++) {
//                        if (aus.charAt(i) == '') {Change the , with . if you want the . to be the decimal separator
//                            if (!firstPointFound) {
//                                firstPointFound = true;
//                            } else {
//                                aus.deleteCharAt(i);
//                            }
//                        }
//                    }
                    newValue = aus.toString();
                    txtSoDienThoai.setText(newValue);
                } else {
                    txtSoDienThoai.setText(newValue);
                }
            });

            txtEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (txtEmail.getText().toString().equals("")) {
                        txtEmail.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                    if (!txtEmail.getText().toString().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")) {
                        txtEmail.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    } else {
                        txtEmail.setStyle("-fx-border-color: GREEN;");
                        checkFrom = true;
                    }
                }
            });

            txtDiaChi.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txtDiaChi.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (txtDiaChi.getText().toString().equals("")) {
                        txtDiaChi.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });


            cboPhuong.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cboPhuong.setStyle("-fx-border-color: GREEN;");
                    checkFrom = true;
                    if (cboPhuong.getValue().equals("")) {
                        cboPhuong.setStyle("-fx-border-color: RED;");
                        checkFrom = false;
                    }
                }
            });
        } catch (Exception ignored) {

        }


        cboGioiTinh.setItems(listGioiTinh);
        cboTrinhDo.setItems(listTrinhDo);

        cboTinh.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachTinh()));
        cboTinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tinh = cboTinh.getValue();
                cboHuyen.setDisable(false);
                String codeProvince = cboTinh.getValue();
                cboHuyen.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachHuyen(codeProvince)));
                cboTinh.setStyle("-fx-border-color: GREEN;");
                checkFrom = true;
                if (cboTinh.getValue().equals("")) {
                    cboTinh.setStyle("-fx-border-color: RED;");
                    checkFrom = false;
                }
            }
        });

        cboHuyen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String huyen = cboTinh.getValue();
                cboPhuong.setDisable(false);
                String codeDis = cboHuyen.getValue();
                cboPhuong.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachDuong(codeDis)));
                cboHuyen.setStyle("-fx-border-color: GREEN;");
                checkFrom = true;
                if (cboHuyen.getValue().equals("")) {
                    cboHuyen.setStyle("-fx-border-color: RED;");
                    checkFrom = false;
                }
            }
        });

        refeshTable();

        filerTableCongDoan();

        handleEvent();
    }

    private String taoMaCN() throws SQLException, ParseException {
        int count = tblCongNhan.getItems().size();
        Date ngaySinh = Date.from(txtNgaySinh.getValue().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String ngayHT = df.format(ngaySinh);
        String namSinh = ngayHT.substring(6);
        String gioiTinh = "1";
        if(cboGioiTinh.getSelectionModel().getSelectedItem().equalsIgnoreCase("Nữ")){
            gioiTinh = "0";
        }
        String ma = "null";
        if(count==0){
            ma = "CN"+gioiTinh+namSinh+"0001";
        }
        else{
            for (DTO_CongNhan n : listCongNhan) {
                int stt = Integer.parseInt(n.getMaCongNhan().substring(7));
                if (count<=stt) {
                    count = stt+1;
                    String soThuTu = String.valueOf(count);
                    if(soThuTu.length()==1){
                        soThuTu="000"+count;
                    } else if (soThuTu.length()==2) {
                        soThuTu="00"+count;
                    } else if (soThuTu.length()==3) {
                        soThuTu="0"+count;
                    } else if (soThuTu.length()==4) {
                        soThuTu = String.valueOf(count);
                    }
                    ma = "CN"+gioiTinh+namSinh+soThuTu;
                }
            }
        }
        return ma;
    }

    @FXML
    /***
     * Hàm mở dialog  thêm công nhân
     */
    public void handleEvent() {
        btnThem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checkLuu) {
                    // đổi nút
                    btnThem.setText("Lưu");
                    checkLuu = true;
                    //Image img = new Image(getClass().getResourceAsStream("img/save-file_white.png").toString());
                    //imgvThem = new ImageView(img);
                    btnXoa.setText("Hủy");
                    checkXoa = false;
                    btnSua.setDisable(true);
                    btnLamMoi.setDisable(true);

                    txtMaCN.setDisable(false);
                    txtHoTen.setDisable(false);
                    txtSoDienThoai.setDisable(false);
                    txtEmail.setDisable(false);
                    txtDiaChi.setDisable(false);
                    txtNgaySinh.setDisable(false);
                    txtNgayVaoLam.setDisable(false);
                    cboTrinhDo.setDisable(false);
                    cboGioiTinh.setDisable(false);
                    cboTinh.setDisable(false);
                    cboHuyen.setDisable(false);
                    cboPhuong.setDisable(false);

                    //txtMaCN.setText("1");
                    /*try {
                        txtMaCN.setText("1");
                    } catch (SQLException | ParseException e) {
                        throw new RuntimeException(e);
                    }*/
                } else {
                    themCongNhan();
                }
            }
        });

        btnXoa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checkXoa){
                    checkLuu = false;
                    checkXoa = true;

                    btnThem.setText("Thêm");
                    btnXoa.setText("Xóa");
                    txtMaCN.setText("");
                    btnLamMoi.setDisable(false);
                    btnSua.setDisable(false);

                    txtMaCN.setDisable(true);
                    txtHoTen.setDisable(true);
                    txtSoDienThoai.setDisable(true);
                    txtEmail.setDisable(true);
                    txtDiaChi.setDisable(true);
                    txtNgaySinh.setDisable(true);
                    txtNgayVaoLam.setDisable(true);
                    cboTrinhDo.setDisable(true);
                    cboGioiTinh.setDisable(true);
                    cboTinh.setDisable(true);
                    cboHuyen.setDisable(true);
                    cboPhuong.setDisable(true);
                    return;
                }
                deleteCongNhan();

            }
        });

        btnSua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    suaCongNhan(actionEvent);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnLamMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refeshForm();
            }
        });
        btn_themDanhSach.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Excel Files","*.xlsx","*.xls","*.ods","*.csv");
                fc.getExtensionFilters().add(ef);
                File file = fc.showOpenDialog(null);
                if(file!=null){
                    try {
                        ConnectDB.getInstance().connect();
                        String sql = "INSERT INTO CONGNHAN VALUES(?,?,?,?,?,?,?,?,?)";
                        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);

                        FileInputStream fileIn = new FileInputStream(file);
                        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
                        XSSFSheet sh = wb.getSheetAt(0);
                        Row row;
                        for (int i = 1; i<=sh.getLastRowNum();i++){
                            row = sh.getRow(i);
                            String namSinh = row.getCell(3).getStringCellValue().substring(0,4);
                            String gioiTinh = "1";
                            if(row.getCell(2).getStringCellValue().equalsIgnoreCase("Nữ")){
                                gioiTinh = "0";
                            }

                            int count = tblCongNhan.getItems().size();
                            String ma = "null";
                            if(count==0){
                                ma = "CN"+gioiTinh+namSinh+"0001";
                            }
                            else {
                                for (DTO_CongNhan n : listCongNhan) {
                                    int stt = Integer.parseInt(n.getMaCongNhan().substring(7));
                                    if (count<=stt) {
                                        count = stt+1;
                                        String soThuTu = String.valueOf(count);
                                        if(soThuTu.length()==1){
                                            soThuTu="000"+count;
                                        } else if (soThuTu.length()==2) {
                                            soThuTu="00"+count;
                                        } else if (soThuTu.length()==3) {
                                            soThuTu="0"+count;
                                        } else if (soThuTu.length()==4) {
                                            soThuTu = String.valueOf(count);
                                        }
                                        ma = "CN"+gioiTinh+namSinh+soThuTu;
                                    }
                                }
                            }
                            ppsm.setString(1, ma);
                            ppsm.setString(2, row.getCell(0).getStringCellValue());
                            ppsm.setString(3, row.getCell(1).getStringCellValue());
                            ppsm.setInt(4, Integer.parseInt(gioiTinh));
                            ppsm.setString(5, row.getCell(3).getStringCellValue());
                            ppsm.setString(6, row.getCell(4).getStringCellValue());
                            ppsm.setString(7, row.getCell(5).getStringCellValue());
                            ppsm.setString(8, row.getCell(6).getStringCellValue());
                            ppsm.setString(9, row.getCell(7).getStringCellValue());


                            ppsm.execute();
                            refeshTable();
                        }
                        wb.close();
                        fileIn.close();
                        ppsm.close();
                    } catch (SQLException e) {
                        // throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        //throw new RuntimeException(e);
                    } catch (IOException e) {
                        //throw new RuntimeException(e);
                    }

                }
            }
        });
        btnXuatDanhSach.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ConnectDB.getInstance().connect();
                    String sql = "SELECT * from CongNhan";
                    Statement stm = ConnectDB.getConnection().createStatement();
                    ResultSet rs = stm.executeQuery(sql);

                    XSSFWorkbook wb = new XSSFWorkbook();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
                    Date ngay = new Date();
                    String ngayHienTai = df.format(ngay);
                    XSSFSheet sh = wb.createSheet("DSCongNhan_"+ngayHienTai);
                    XSSFRow header = sh.createRow(0);
                    header.createCell(0).setCellValue("Mã công nhân");
                    header.createCell(1).setCellValue("Tên công nhân");
                    header.createCell(2).setCellValue("Ngày vào làm");
                    header.createCell(3).setCellValue("Giới tính");
                    header.createCell(4).setCellValue("ngày sinh");
                    header.createCell(5).setCellValue("Số điện thoại");
                    header.createCell(6).setCellValue("Email");
                    header.createCell(7).setCellValue("Địa chỉ");
                    header.createCell(8).setCellValue("Trình độ");
                    int index=1;
                    while (rs.next()){
                        XSSFRow row = sh.createRow(index);
                        row.createCell(0).setCellValue(rs.getString("maCongNhan"));
                        row.createCell(1).setCellValue(rs.getString("tenCongNhan"));
                        row.createCell(2).setCellValue(rs.getString("ngayVaoLam"));
                        String gioiTinh = "Nam";
                        if(rs.getString("Phai").equalsIgnoreCase("0")){
                            gioiTinh = "Nữ";
                        }
                        row.createCell(3).setCellValue(gioiTinh);
                        row.createCell(4).setCellValue(rs.getString("ngaySinh"));
                        row.createCell(5).setCellValue(rs.getString("SDT"));
                        row.createCell(6).setCellValue(rs.getString("email"));
                        row.createCell(7).setCellValue(rs.getString("diaChi"));
                        row.createCell(8).setCellValue(rs.getString("trinhDo"));
                        index++;

                    }
                    FileOutputStream fileOut = new FileOutputStream("CongNhan_"+ngayHienTai+".xlsx");
                    wb.write(fileOut);
                    fileOut.close();

                    stm.close();
                    rs.close();
                    wb.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        });
    }

    @FXML
    /***
     * Hàm mở dialog  thêm công nhân
     */
    public void suaCongNhan(ActionEvent event) throws IOException, ParseException {
        int idx = tblCongNhan.getSelectionModel().getSelectedIndex();
        if (idx == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Vui lòng chọn nhân viên cần sửa");
            Optional<ButtonType> clickedButton = alert.showAndWait();
            return;
        }
        DTO_CongNhan cn_Sua = tblCongNhan.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Dialog_SuaCongNhan.fxml"));
        DialogPane addDialog = fxmlLoader.load();
        dialogSua = new Dialog<>();
        dialogSua.setDialogPane(addDialog);
        dialogSua.setTitle("Sửa Công Nhân");
        CTL_UI_SuaCongNhan congNhanController = fxmlLoader.getController();
        congNhanController.setData(cn_Sua, cn_Sua.getDiaChi());
        Optional<ButtonType> clickedButton = dialogSua.showAndWait();

        refeshTable();
        tblCongNhan.refresh();

    }

    public void refeshTable() {
        BUS_CongNhan bus_congNhan = new BUS_CongNhan();
        try {
            ArrayList<DTO_CongNhan> ds = bus_congNhan.getDSCongNhan();
            listCongNhan = FXCollections.observableArrayList(ds);

            tblCongNhan.setItems(listCongNhan);
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCongNhan() {
        int idx = tblCongNhan.getSelectionModel().getSelectedIndex();

        if (idx == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Vui lòng chọn nhân viên cần xóa");
            Optional<ButtonType> clickedButton = alert.showAndWait();
            return;
        }

        String maCN = tblCongNhan.getColumns().get(0).getCellObservableValue(idx).getValue().toString();

            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "Bạn có chắc muốn xóa không?",
                            ButtonType.YES,
                            ButtonType.NO);
            alert.setTitle("Cảnh báo");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.YES) {
                try {
                    new BUS_CongNhan().deleteCongNhan(maCN);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                refeshTable();
            }

    }

    /***
     * Hàm thêm một nhân viên mới
     */
    public void themCongNhan() {
        // lấy thông tin  công nhân
        try {
            if (txtNgayVaoLam.getValue() == null)
                checkFrom = false;
            if (txtNgaySinh.getValue() == null)
                checkFrom = false;
            if (cboGioiTinh.getValue() == null)
                checkFrom = false;
            if (cboTrinhDo.getValue() == null)
                checkFrom = false;
            if (cboTinh.getValue() == null)
                checkFrom = false;
            if (cboHuyen.getValue() == null)
                checkFrom = false;
            if (cboPhuong.getValue() == null)
                checkFrom = false;

            if (!checkFrom) {
                Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
                wn.setContentText("Vui lòng chỉnh sửa thông tin các trường bị đánh dấu");
                Optional<ButtonType> showWN = wn.showAndWait();
                return;
            }

            //String maCN = txtMaCN.getText();

            String tenCN = txtHoTen.getText();
            String ngaySinh = txtNgaySinh.getValue().toString();
            String ngayVaoLam = txtNgayVaoLam.getValue().toString();
            boolean gioiTinh = cboGioiTinh.getValue().equals("Nam");
            String trinhDo = cboTrinhDo.getValue().toString();
            String sdt = txtSoDienThoai.getText().toString();
            String email = txtEmail.getText().toString();
            String diaChi = txtDiaChi.getText().toString() +"," + cboPhuong.getValue()+ ", " +cboHuyen.getValue() + ", "+cboTinh.getValue();
            Date ngaySinhDate = new SimpleDateFormat("YYYY-MM-dd").parse(ngaySinh);
            Date ngayVaoLamDate = new SimpleDateFormat("YYYY-MM-dd").parse(ngayVaoLam);
            String maCN = taoMaCN();
            // Tạo đối tượng
            DTO_CongNhan tmp = new DTO_CongNhan(maCN, tenCN, ngayVaoLamDate, gioiTinh, sdt, ngaySinhDate, email, diaChi, trinhDo);
            BUS_CongNhan bus_congNhan = new BUS_CongNhan();

            bus_congNhan.insertCongNhan(tmp);

            refeshTable();
            // đổi lại nút
            checkLuu = false;
            checkXoa = false;

            btnThem.setText("Thêm");
            btnXoa.setText("Xóa");
            btnLamMoi.setDisable(false);
            btnSua.setDisable(false);
            txtMaCN.setDisable(true);
            txtHoTen.setDisable(true);
            txtSoDienThoai.setDisable(true);
            txtEmail.setDisable(true);
            txtDiaChi.setDisable(true);
            txtNgaySinh.setDisable(true);
            txtNgayVaoLam.setDisable(true);
            cboTrinhDo.setDisable(true);
            cboGioiTinh.setDisable(true);
            cboTinh.setDisable(true);
            cboHuyen.setDisable(true);
            cboPhuong.setDisable(true);
            refeshForm();

        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }


    }

    /***
     * Hàm xóa trắng form thêm nhân viên
     */
    public void refeshForm() {
        txtMaCN.setText("");
        txtHoTen.setText("");
        txtHoTen.setStyle("-fx-border-color: RED;");
        txtSoDienThoai.setText("");
        txtSoDienThoai.setStyle("-fx-border-color: RED;");
        txtEmail.setText("");
        txtEmail.setStyle("-fx-border-color: RED;");
        txtDiaChi.setText("");
        txtDiaChi.setStyle("-fx-border-color: RED;");
        txtNgaySinh.setValue(null);
        txtNgaySinh.setStyle("-fx-border-color: RED;");
        txtNgayVaoLam.setValue(null);
        txtNgayVaoLam.setStyle("-fx-border-color: RED;");
        cboTrinhDo.getSelectionModel().clearSelection();
        cboTrinhDo.setStyle("-fx-border-color: RED;");
        cboGioiTinh.getSelectionModel().clearSelection();
        cboGioiTinh.setStyle("-fx-border-color: RED;");
        cboTinh.getSelectionModel().clearSelection();
        cboTinh.setStyle("-fx-border-color: RED;");
        cboHuyen.getSelectionModel().clearSelection();
        cboHuyen.setStyle("-fx-border-color: RED;");
        cboPhuong.getSelectionModel().clearSelection();
        cboPhuong.setStyle("-fx-border-color: RED;");

    }

    /**
     * Hàm check dữ liệu của form
     *
     * @return true or false
     */
    private boolean check() {
        boolean check = true;
        if (txtHoTen.getText().equals("")) {
            txtHoTen.requestFocus();
            return false;
        }
        Date ngayVaoLam = Date.from(txtNgayVaoLam.getValue().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        if (getDiffDate(ngayVaoLam, new Date()) <= 0) {

        }

        return check;
    }

    /**
     * Hàm trở trả về số ngày cách nhau giưa 2 ngày
     *
     * @param olderDate
     * @param newerDate
     * @return int
     */
    private int getDiffDate(Date olderDate, Date newerDate) {
        int diffInDays = (int) ((newerDate.getTime() - olderDate.getTime())
                / (1000 * 60 * 60 * 24));
        return diffInDays;
    }

    /**
     * Hàm lọc bảng
     */
    private void filerTableCongDoan() {
        FilteredList<DTO_CongNhan> filteredListCongDoan = new FilteredList<>(listCongNhan, b -> true);
        txtTimCongNhan.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListCongDoan.setPredicate(dto_congNhan -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (dto_congNhan.getMaCongNhan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dto_congNhan.getTenCongNhan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(dto_congNhan.getSoDienThoai()).contains(lowerCaseFilter))
                    return true;
                else
                    return false; // Does not match.
            });
        });
        SortedList<DTO_CongNhan> sortedData = new SortedList<>(filteredListCongDoan);
        sortedData.comparatorProperty().bind(tblCongNhan.comparatorProperty());
        tblCongNhan.setItems(sortedData);
    }


    public void closeDialogSua() {
        //refeshTable();
        dialogSua.setResult(ButtonType.CLOSE);
        dialogSua.close();

    }
}
