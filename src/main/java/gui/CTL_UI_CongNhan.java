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
import javafx.scene.input.MouseEvent;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    private ArrayList<DTO_CongNhan> listCN;

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
    private BUS_CongNhan bus_congNhan = new BUS_CongNhan();
    @FXML
    private TextField txtTimCongNhan;
    private boolean checkLuu = false;
    private boolean checkXoa = true;

    private DTO_CongNhan CNCLick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refeshTable();
        connectAPI = new BUS_API();

        cboGioiTinh.setItems(listGioiTinh);
        cboTrinhDo.setItems(listTrinhDo);
        connectAPI = new BUS_API();
        cboTinh.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachTinh()));
        CheckRed();
        cboTinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String codeProvince = cboTinh.getValue();
                cboPhuong.setStyle("-fx-border-color: RED;");
                cboPhuong.setStyle("-fx-border-color: RED;");
                cboHuyen.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachHuyen(codeProvince)));
                if(cboTinh.getValue().equals("")){
                    cboTinh.setStyle("-fx-border-color: RED;");
                }
                else {
                    cboTinh.setStyle("-fx-border-color: GREEN;");
                }
            }
        });

        cboHuyen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String codeDis = cboHuyen.getValue();
                cboPhuong.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachDuong(codeDis)));
                if(cboHuyen.getValue().equals("")){
                    cboHuyen.setStyle("-fx-border-color: RED;");
                }
                else {
                    cboHuyen.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        cboPhuong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cboPhuong.getValue().equals("")){
                    cboPhuong.setStyle("-fx-border-color: RED;");
                }
                else {
                    cboPhuong.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txtSoDienThoai.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtSoDienThoai.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txtSoDienThoai.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txtSoDienThoai.setText(newValue);
            } else {
                txtSoDienThoai.setText(newValue);
            }
        });
        txtHoTen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txtHoTen.getText().equals("")){
                    txtHoTen.setStyle("-fx-border-color: RED;");
                }
                else {
                    txtHoTen.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txtEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txtEmail.getText().equals("")|| !txtEmail.getText().toString().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")){
                    txtEmail.setStyle("-fx-border-color: RED;");
                }
                else {
                    txtEmail.setStyle("-fx-border-color: GREEN;");
                }
            }
        });

        txtDiaChi.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txtDiaChi.getText().equals("")){
                    txtDiaChi.setStyle("-fx-border-color: RED;");
                }
                else {
                    txtDiaChi.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txtSoDienThoai.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txtSoDienThoai.getText().equals("")||txtSoDienThoai.getText().length()!=10){
                    txtSoDienThoai.setStyle("-fx-border-color: RED;");
                }
                else {
                    txtSoDienThoai.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txtNgayVaoLam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {
                txtNgayVaoLam.setStyle("-fx-border-color: GREEN;");
                if (txtNgayVaoLam.getValue()==null) {
                    txtNgayVaoLam.setStyle("-fx-border-color: RED;");
                }

                Date ngayVaoLam = Date.from(txtNgayVaoLam.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                if (getDiffDate(ngayVaoLam, new Date()) <= 0) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Ngày vào làm phải bé hơn ngày hiện tại");
                    txtNgayVaoLam.setStyle("-fx-border-color: RED;");
                    Optional<ButtonType> a = er.showAndWait();
                    txtNgayVaoLam.setValue(null);
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
                }

                Date ngaySinh = Date.from(txtNgaySinh.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                if (new Date().getYear() - ngaySinh.getYear() < 18 || new Date().getYear() - ngaySinh.getYear() > 60) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Nhân viên phải từ 18-60 tuổi");
                    txtNgaySinh.setStyle("-fx-border-color: RED;");
                    txtNgaySinh.setValue(null);
                    Optional<ButtonType> a = er.showAndWait();
                }
            }
        });
        cboGioiTinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cboGioiTinh.getSelectionModel().getSelectedItem().equals("")){
                    cboGioiTinh.setStyle("-fx-border-color: RED;");
                }else {
                    cboGioiTinh.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        cboTrinhDo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cboTrinhDo.getSelectionModel().getSelectedItem().equals("")){
                    cboTrinhDo.setStyle("-fx-border-color: RED;");
                }else {
                    cboTrinhDo.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        handleEvent();
        HideInfo();
        refeshTable();
        filerTableCongDoan();
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
        tblCongNhan.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            listCN = bus_congNhan.getDSCongNhan();
            CNCLick = tblCongNhan.getSelectionModel().getSelectedItem();
            txtMaCN.setText(CNCLick.getMaCongNhan());
            txtHoTen.setText(CNCLick.getTenCongNhan());
            int phai = 1;
            if(CNCLick.isPhai()==true){
                phai = 0;
            }
            cboGioiTinh.getSelectionModel().select(phai);
            cboTrinhDo.getSelectionModel().select(CNCLick.getTrinhDo());
            LocalDate ngaySinh = Instant.ofEpochMilli(CNCLick.getNgaySinh().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate ngayVaoLam = Instant.ofEpochMilli(CNCLick.getNgayVaoLam().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            txtNgayVaoLam.setValue(ngayVaoLam);
            txtNgaySinh.setValue(ngaySinh);
            String diaChi [] = CNCLick.getDiaChi().split(",");
            cboTinh.setValue(diaChi[3]);
            cboHuyen.setValue(diaChi[2]);
            cboPhuong.setValue(diaChi[1]);
            txtDiaChi.setText(diaChi[0]);
            txtSoDienThoai.setText(CNCLick.getSoDienThoai());
            txtEmail.setText(CNCLick.getEmail());
            CheckGreen();

        });
        btnThem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(btnThem.getText().equalsIgnoreCase("Thêm")){
                    btnThem.setText("Lưu");
                    btnXoa.setText("Hủy");
                    btnSua.setDisable(true);
                    ShowInfo();
                    Clear();
                    CheckRed();
                } else if (btnThem.getText().equalsIgnoreCase("Lưu")) {
                    themCongNhan();
                }
            }
        });

        btnXoa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (btnXoa.getText().equalsIgnoreCase("Hủy")){
                    Clear();
                    btnThem.setText("Thêm");
                    btnXoa.setText("Xóa");
                    btnSua.setDisable(false);
                    HideInfo();
                }
                else {
                    deleteCongNhan();
                    Clear();
                }


            }
        });

        btnSua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    suaCongNhan(actionEvent);
                    filerTableCongDoan();
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnLamMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Clear();
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
                            filerTableCongDoan();
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
                    FileChooser fc = new FileChooser();
                    FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Excel Files","*.xlsx","*.xls","*.ods","*.csv");
                    fc.getExtensionFilters().add(ef);
                    fc.setInitialFileName("CongNhan_"+ngayHienTai+".xlsx");
                    File file = fc.showSaveDialog(null);
                    FileOutputStream fileOut = new FileOutputStream(file);
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
        ArrayList<DTO_CongNhan> ds = bus_congNhan.getDSCongNhan();
        listCongNhan = FXCollections.observableArrayList(ds);
        maCNCol.setCellValueFactory(new PropertyValueFactory<>("maCongNhan"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("tenCongNhan"));
        gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("phai"));
        ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        ngayVaoLamCol.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
        soDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        trinhDoCol.setCellValueFactory(new PropertyValueFactory<>("trinhDo"));
        tblCongNhan.setItems(listCongNhan);

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
                filerTableCongDoan();
            }

    }

    /***
     * Hàm thêm một nhân viên mới
     */
    public void themCongNhan() {
        // lấy thông tin  công nhân
        try {
            checkInfo();
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
            Clear();
            HideInfo();
            CheckRed();
            filerTableCongDoan();


        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }


    }
    private void HideInfo(){
        txtMaCN.setDisable(true);
        txtHoTen.setDisable(true);
        cboGioiTinh.setDisable(true);
        txtNgayVaoLam.setDisable(true);
        txtNgaySinh.setDisable(true);
        txtSoDienThoai.setDisable(true);
        txtEmail.setDisable(true);
        cboTrinhDo.setDisable(true);
        cboTinh.setDisable(true);
        cboPhuong.setDisable(true);
        cboHuyen.setDisable(true);
        txtDiaChi.setDisable(true);
    }
    private void ShowInfo(){
        txtMaCN.setDisable(false);
        txtHoTen.setDisable(false);
        cboGioiTinh.setDisable(false);
        txtNgayVaoLam.setDisable(false);
        txtNgaySinh.setDisable(false);
        txtSoDienThoai.setDisable(false);
        txtEmail.setDisable(false);
        cboTrinhDo.setDisable(false);
        cboTinh.setDisable(false);
        cboPhuong.setDisable(false);
        cboHuyen.setDisable(false);
        txtDiaChi.setDisable(false);
    }
    private void Clear(){
        txtMaCN.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        cboGioiTinh.getSelectionModel().clearSelection();

        cboTrinhDo.getSelectionModel().clearSelection();
        cboTinh.getSelectionModel().clearSelection();
        cboHuyen.getSelectionModel().clearSelection();
        cboPhuong.getSelectionModel().clearSelection();
        cboTrinhDo.setValue("");
        cboTinh.setValue("");
        cboHuyen.setValue("");
        cboPhuong.setValue("");
        txtDiaChi.setText("");
        txtNgaySinh.setValue(null);
        txtNgayVaoLam.setValue(null);
        CheckRed();
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
                String gioiTinh = "Nam";
                if(dto_congNhan.isPhai() == false){
                    gioiTinh = "Nữ";
                }
                if (dto_congNhan.getMaCongNhan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dto_congNhan.getTenCongNhan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } if(dto_congNhan.getNgaySinh().toString().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(gioiTinh.toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dto_congNhan.getNgayVaoLam().toString().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dto_congNhan.getSoDienThoai().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dto_congNhan.getTrinhDo().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dto_congNhan.getDiaChi().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                else return dto_congNhan.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1;
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
    private void checkInfo(){
        if(txtHoTen.getText().equals("")||
                txtEmail.getText().equals("")|| !txtEmail.getText().toString().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")||
                cboTrinhDo.getValue()==null||txtSoDienThoai.getText().equals("")||txtSoDienThoai.getText().equals("")||txtSoDienThoai.getText().length()!=10||
                txtDiaChi.getText().equals("")||cboGioiTinh.getValue()==null||cboPhuong.getValue()==null||
                cboTinh.getValue()==null||cboHuyen.getValue()==null||txtNgayVaoLam.getValue()==null||
                txtNgaySinh.getValue()==null){
            checkFrom = false;
        }
        else {
            checkFrom = true;
        }

    }
    private void CheckRed(){
        txtHoTen.setStyle("-fx-border-color: RED;");
        txtSoDienThoai.setStyle("-fx-border-color: RED;");
        cboTrinhDo.setStyle("-fx-border-color: RED;");
        txtEmail.setStyle("-fx-border-color: RED;");
        txtDiaChi.setStyle("-fx-border-color: RED;");
        txtNgaySinh.setStyle("-fx-border-color: RED;");
        txtNgayVaoLam.setStyle("-fx-border-color: RED;");
        cboPhuong.setStyle("-fx-border-color: RED;");
        cboTinh.setStyle("-fx-border-color: RED;");
        cboGioiTinh.setStyle("-fx-border-color: RED;");
        cboHuyen.setStyle("-fx-border-color: RED;");
    }
    private void CheckGreen(){
        if(checkFrom){
            txtHoTen.setStyle("-fx-border-color: GREEN;");
            txtSoDienThoai.setStyle("-fx-border-color: GREEN;");
            cboTrinhDo.setStyle("-fx-border-color: GREEN;");
            txtEmail.setStyle("-fx-border-color: GREEN;");
            txtDiaChi.setStyle("-fx-border-color: GREEN;");
            txtNgaySinh.setStyle("-fx-border-color: GREEN;");
            txtNgayVaoLam.setStyle("-fx-border-color: GREEN;");
            cboPhuong.setStyle("-fx-border-color: GREEN;");
            cboTinh.setStyle("-fx-border-color: GREEN;");
            cboGioiTinh.setStyle("-fx-border-color: GREEN;");
            cboHuyen.setStyle("-fx-border-color: GREEN;");
        }

    }
}
