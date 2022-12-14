package gui;

import bus.BUS_API;
import bus.BUS_NhanVien;
import db.ConnectDB;
import dto.DTO_CongNhan;
import dto.DTO_NhanVien;
import dto.DTO_SanPham;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class CTRL_UI_NhanVien implements Initializable{

    @FXML
    private Button btn_Sua;

    @FXML
    private Button btn_Xoa;

    @FXML
    private Button btn_XuatDS;

    @FXML
    private Button btn_lamMoi;

    @FXML
    private Button btn_themDS;

    @FXML
    private Button btn_themMoi;

    @FXML
    private ComboBox<String> cboGioiTinh;

    @FXML
    private ComboBox<String> cbo_huyen;

    @FXML
    private ComboBox<String> cbo_phuong;


    @FXML
    private ComboBox<String> cbo_tinh;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_DiaChi;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_SDT;
    @FXML
    private TableColumn<DTO_NhanVien, String> col_email;
    @FXML
    private TableColumn<DTO_NhanVien, String> col_gioiTinh;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_luongCoBan;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_maNV;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_ngaySinh;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_ngayVaoLam;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_tenNV;
    @FXML
    private DatePicker dp_ngaySinh;

    @FXML
    private DatePicker dp_ngayVaoLam;

    @FXML
    private TableView<DTO_NhanVien> tbl_NhanVien;
    @FXML
    private TextField txtLuongCoBan;
    @FXML
    private TextField txt_Email;

    @FXML
    private TextField txt_duong;

    @FXML
    private TextField txt_maNhanVIen;
    @FXML
    private TextField txt_timNV;
    @FXML
    private TextField txt_soDienThoai;

    @FXML
    private TextField txt_tenNhanVien;
    static BUS_API connectAPI;
    private ObservableList<DTO_NhanVien> listNhanVien;
    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
    private BUS_NhanVien bus_nhanVien = new BUS_NhanVien();
    private  ArrayList<DTO_NhanVien> listNV = new ArrayList();
    private DTO_NhanVien NVClick;
    public static Dialog<ButtonType> dialogSua;
    private boolean checkFrom = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboGioiTinh.setItems(listGioiTinh);
        connectAPI = new BUS_API();
        cbo_tinh.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachTinh()));
        CheckRed();
        cbo_tinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String codeProvince = cbo_tinh.getValue();
                cbo_phuong.setStyle("-fx-border-color: RED;");
                cbo_huyen.setStyle("-fx-border-color: RED;");
                cbo_huyen.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachHuyen(codeProvince)));
                if(cbo_tinh.getValue().equals("")){
                    cbo_tinh.setStyle("-fx-border-color: RED;");
                }
                else {
                    cbo_tinh.setStyle("-fx-border-color: GREEN;");
                }
            }
        });

        cbo_huyen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String codeDis = cbo_huyen.getValue();
                cbo_phuong.setItems(FXCollections.observableArrayList(connectAPI.getDanhSachDuong(codeDis)));
                if(cbo_huyen.getValue().equals("")){
                    cbo_huyen.setStyle("-fx-border-color: RED;");
                }
                else {
                    cbo_huyen.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        cbo_phuong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cbo_phuong.getValue().equals("")){
                    cbo_phuong.setStyle("-fx-border-color: RED;");
                }
                else {
                    cbo_phuong.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txt_soDienThoai.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txt_soDienThoai.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txt_soDienThoai.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txt_soDienThoai.setText(newValue);
            } else {
                txt_soDienThoai.setText(newValue);
            }
        });
        txtLuongCoBan.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtLuongCoBan.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txtLuongCoBan.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txtLuongCoBan.setText(newValue);
            } else {
                txtLuongCoBan.setText(newValue);
            }
        });
        txt_tenNhanVien.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txt_tenNhanVien.getText().equals("")){
                    txt_tenNhanVien.setStyle("-fx-border-color: RED;");
                }
                else {
                    txt_tenNhanVien.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txt_Email.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txt_Email.getText().equals("")|| !txt_Email.getText().toString().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")){
                    txt_Email.setStyle("-fx-border-color: RED;");
                }
                else {
                    txt_Email.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txtLuongCoBan.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txtLuongCoBan.getText().equals("")){
                    txtLuongCoBan.setStyle("-fx-border-color: RED;");
                }
                else {
                    txtLuongCoBan.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txt_duong.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txt_duong.getText().equals("")){
                    txt_duong.setStyle("-fx-border-color: RED;");
                }
                else {
                    txt_duong.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        txt_soDienThoai.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(txt_soDienThoai.getText().equals("")||txt_soDienThoai.getText().length()!=10){
                    txt_soDienThoai.setStyle("-fx-border-color: RED;");
                }
                else {
                    txt_soDienThoai.setStyle("-fx-border-color: GREEN;");
                }
            }
        });
        dp_ngayVaoLam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {
                dp_ngayVaoLam.setStyle("-fx-border-color: GREEN;");
                if (dp_ngayVaoLam.getValue()==null) {
                    dp_ngayVaoLam.setStyle("-fx-border-color: RED;");
                }

                Date ngayVaoLam = Date.from(dp_ngayVaoLam.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                if (getDiffDate(ngayVaoLam, new Date()) <= 0) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Ngày vào làm phải bé hơn ngày hiện tại");
                    dp_ngayVaoLam.setStyle("-fx-border-color: RED;");
                    Optional<ButtonType> a = er.showAndWait();
                    dp_ngayVaoLam.setValue(null);
                }
            }
        });

        dp_ngaySinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {
                dp_ngaySinh.setStyle("-fx-border-color: GREEN;");
                checkFrom = true;
                if (dp_ngaySinh.getValue()==null) {
                    dp_ngaySinh.setStyle("-fx-border-color: RED;");
                }

                Date ngaySinh = Date.from(dp_ngaySinh.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                if (new Date().getYear() - ngaySinh.getYear() < 18 || new Date().getYear() - ngaySinh.getYear() > 60) {
                    Alert er = new Alert(Alert.AlertType.ERROR, "Cảnh báo", ButtonType.OK);
                    er.setContentText("Nhân viên phải từ 18-60 tuổi");
                    dp_ngaySinh.setStyle("-fx-border-color: RED;");
                    dp_ngaySinh.setValue(null);
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
        handleEvent();
        HideInfo();
        loadTableNV();
        filerTableNhanVien();
    }
    public void handleEvent(){
        tbl_NhanVien.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            listNV = bus_nhanVien.getAllDSNhanVien();
            NVClick = tbl_NhanVien.getSelectionModel().getSelectedItem();
            txt_maNhanVIen.setText(NVClick.getMaNhanVien());
            txt_tenNhanVien.setText(NVClick.getTenNhanVien());
            int phai = 1;
            if(NVClick.isPhai()==true){
                phai = 0;
            }
            cboGioiTinh.getSelectionModel().select(phai);
            txtLuongCoBan.setText(String.format("%1$.0fVND", NVClick.getLuongCoBan()));
            LocalDate ngaySinh = Instant.ofEpochMilli(NVClick.getNgaySinh().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate ngayVaoLam = Instant.ofEpochMilli(NVClick.getNgayVaoLam().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            dp_ngayVaoLam.setValue(ngayVaoLam);
            dp_ngaySinh.setValue(ngaySinh);
            String diaChi [] = NVClick.getDiaChi().split(",");
            cbo_tinh.setValue(diaChi[3]);
            cbo_huyen.setValue(diaChi[2]);
            cbo_phuong.setValue(diaChi[1]);
            txt_duong.setText(diaChi[0]);
            txt_soDienThoai.setText(NVClick.getSoDienThoai());
            txt_Email.setText(NVClick.getEmail());
            CheckGreen();

        });
        btn_themMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(btn_themMoi.getText().equalsIgnoreCase("Thêm")){
                    btn_themMoi.setText("Lưu");
                    btn_Xoa.setText("Hủy");
                    btn_Sua.setDisable(true);
                    ShowInfo();
                    Clear();
                    CheckRed();
                } else if (btn_themMoi.getText().equalsIgnoreCase("Lưu")) {
                    themNV();
                }
            }
        });
        btn_Xoa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (btn_Xoa.getText().equalsIgnoreCase("Hủy")){
                    Clear();
                    btn_themMoi.setText("Thêm");
                    btn_Xoa.setText("Xóa");
                    btn_Sua.setDisable(false);
                    HideInfo();
                } else if (btn_Xoa.getText().equalsIgnoreCase("Xóa")) {
                    xoaNV();
                    Clear();
                }

            }
        });
        btn_lamMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Clear();
            }
        });
        btn_Sua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    suaNhanVien(actionEvent);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btn_themDS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Excel Files","*.xlsx","*.xls","*.ods","*.csv");
                fc.getExtensionFilters().add(ef);
                File file = fc.showOpenDialog(null);
                if(file!=null){
                    try {
                        ConnectDB.getInstance().connect();
                        String sql = "INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?,?,?)";
                        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);

                        FileInputStream fileIn = new FileInputStream(file);
                        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
                        XSSFSheet sh = wb.getSheetAt(0);
                        Row row;
                        for (int i = 1; i<sh.getLastRowNum();i++){
                            row = sh.getRow(i);
                            String namSinh = row.getCell(3).getStringCellValue().substring(0,4);
                            String gioiTinh = "1";
                            if(row.getCell(2).getStringCellValue().equalsIgnoreCase("Nữ")){
                                gioiTinh = "0";
                            }

                            int count = tbl_NhanVien.getItems().size();
                            String ma = "null";
                            if(count==0){
                                ma = "NV"+gioiTinh+namSinh+"0001";
                            }
                            else {
                                for (DTO_NhanVien n : listNhanVien) {
                                    int stt = Integer.parseInt(n.getMaNhanVien().substring(7));
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
                                        ma = "NV"+gioiTinh+namSinh+soThuTu;
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
                            ppsm.setDouble(9, row.getCell(7).getNumericCellValue());

                            ppsm.execute();
                            loadTableNV();
                            filerTableNhanVien();
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
        btn_XuatDS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ConnectDB.getInstance().connect();
                    String sql = "SELECT * from NhanVien";
                    Statement stm = ConnectDB.getConnection().createStatement();
                    ResultSet rs = stm.executeQuery(sql);

                    XSSFWorkbook wb = new XSSFWorkbook();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
                    Date ngay = new Date();
                    String ngayHienTai = df.format(ngay);
                    XSSFSheet sh = wb.createSheet("DSNhanVien"+ngayHienTai);
                    XSSFRow header = sh.createRow(0);
                    header.createCell(0).setCellValue("Mã nhân viên");
                    header.createCell(1).setCellValue("Tên nhân viên");
                    header.createCell(2).setCellValue("Ngày vào làm");
                    header.createCell(3).setCellValue("Giới tính");
                    header.createCell(4).setCellValue("ngày sinh");
                    header.createCell(5).setCellValue("Số điện thoại");
                    header.createCell(6).setCellValue("Email");
                    header.createCell(7).setCellValue("Địa chỉ");
                    header.createCell(8).setCellValue("Lương cơ bản");
                    int index=1;
                    while (rs.next()){
                        XSSFRow row = sh.createRow(index);
                        row.createCell(0).setCellValue(rs.getString("maNhanVien"));
                        row.createCell(1).setCellValue(rs.getString("tenNhanVien"));
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
                        row.createCell(8).setCellValue(rs.getString("luongCoBan"));
                        index++;

                    }
                    FileChooser fc = new FileChooser();
                    FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Excel Files","*.xlsx","*.xls","*.ods","*.csv");
                    fc.getExtensionFilters().add(ef);
                    fc.setInitialFileName("NhanVien_"+ngayHienTai+".xlsx");
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
    private void themNV(){
        try {
            checkInfo();
            if(!checkFrom){
                Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
                wn.setContentText("Vui lòng chỉnh sửa thông tin các trường bị đánh dấu");
                Optional<ButtonType> showWN = wn.showAndWait();
                return;
            }else {
                String tenNV = txt_tenNhanVien.getText();
                String gioiTinh = cboGioiTinh.getValue();
                Boolean phai = true;
                if(gioiTinh.equalsIgnoreCase("Nữ")){
                    phai = false;
                }
                String sdt = txt_soDienThoai.getText();
                Date ngayVaoLam = Date.from(dp_ngayVaoLam.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                Date ngaySinh = Date.from(dp_ngaySinh.getValue().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                //String ngay
                String email = txt_Email.getText();
                Double luongCoBan = Double.valueOf(txtLuongCoBan.getText());
                String diaChi = txt_duong.getText() +"," + cbo_phuong.getValue()+ ", " +cbo_huyen.getValue() + ", "+cbo_tinh.getValue();
                String maNV = taoMaNV();
                DTO_NhanVien nv = new DTO_NhanVien(maNV,tenNV,ngayVaoLam,phai,ngaySinh,sdt,email,diaChi,luongCoBan);
                bus_nhanVien.insertNhanVien(nv);
                loadTableNV();
                Clear();
                HideInfo();
                CheckRed();
                filerTableNhanVien();
            }

            //tbl_SanPham.getSelectionModel().selectLast();
        } catch (Exception e) {

        }

    }
    private int getDiffDate(Date olderDate, Date newerDate) {
        int diffInDays = (int) ((newerDate.getTime() - olderDate.getTime())
                / (1000 * 60 * 60 * 24));
        return diffInDays;
    }
    private void xoaNV(){
        int idx = tbl_NhanVien.getSelectionModel().getSelectedIndex();

        if (idx == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Vui lòng chọn sản phẩm cần xóa");
            Optional<ButtonType> clickedButton = alert.showAndWait();
            return;
        }

        String maNV = tbl_NhanVien.getColumns().get(0).getCellObservableValue(idx).getValue().toString();

        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "Bạn có chắc muốn xóa không?",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("Cảnh báo");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            try {
                bus_nhanVien.deleteNhanVien( maNV);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            loadTableNV();
            filerTableNhanVien();

        }
    }
    private void loadTableNV(){

        try {
            ArrayList<DTO_NhanVien> ds = bus_nhanVien.getAllDSNhanVien();
            listNhanVien = FXCollections.observableArrayList(ds);
            col_maNV.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
            col_tenNV.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
            col_gioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
            col_ngaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
            col_ngayVaoLam.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
            col_SDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
            col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            col_luongCoBan.setCellValueFactory(new PropertyValueFactory<>("luongCoBan"));

            /*col_soLuongSPYeuCaetCellValueFactory(new PropertyValueFactory<>("soLuongYeuCau"));
            col_ChatLieu.setCellValueFactory(new PropertyValueFactory<>("chatLieu"));*/

            tbl_NhanVien.setItems(listNhanVien);
        } catch (Exception e) {

        }
    }
    private void HideInfo(){
        txt_maNhanVIen.setDisable(true);
        txt_tenNhanVien.setDisable(true);
        cboGioiTinh.setDisable(true);
        dp_ngayVaoLam.setDisable(true);
        dp_ngaySinh.setDisable(true);
        txt_soDienThoai.setDisable(true);
        txt_Email.setDisable(true);
        txtLuongCoBan.setDisable(true);
        cbo_tinh.setDisable(true);
        cbo_phuong.setDisable(true);
        cbo_huyen.setDisable(true);
        txt_duong.setDisable(true);
    }
    private void ShowInfo(){
        txt_tenNhanVien.setDisable(false);
        cboGioiTinh.setDisable(false);
        dp_ngayVaoLam.setDisable(false);
        dp_ngaySinh.setDisable(false);
        txt_soDienThoai.setDisable(false);
        txt_Email.setDisable(false);
        txtLuongCoBan.setDisable(false);
        cbo_tinh.setDisable(false);
        cbo_phuong.setDisable(false);
        cbo_huyen.setDisable(false);
        txt_duong.setDisable(false);
    }
    private void Clear(){
        txt_maNhanVIen.setText("");
        txt_tenNhanVien.setText("");
        txt_soDienThoai.setText("");
        txt_Email.setText("");
        cboGioiTinh.getSelectionModel().clearSelection();
        txtLuongCoBan.setText("");

        cbo_tinh.getSelectionModel().clearSelection();
        cbo_huyen.getSelectionModel().clearSelection();
        cbo_phuong.getSelectionModel().clearSelection();
        cbo_tinh.setValue("");
        cbo_huyen.setValue("");
        cbo_phuong.setValue("");
        txt_duong.setText("");
        dp_ngaySinh.setValue(null);
        dp_ngayVaoLam.setValue(null);
        CheckRed();
    }
    private String taoMaNV() throws SQLException, ParseException {
        int count = tbl_NhanVien.getItems().size();
        Date ngaySinh = Date.from(dp_ngaySinh.getValue().atStartOfDay()
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
            ma = "NV"+gioiTinh+namSinh+"0001";
        }
        else {
            for (DTO_NhanVien n : listNhanVien) {
                int stt = Integer.parseInt(n.getMaNhanVien().substring(7));
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
                    ma = "NV"+gioiTinh+namSinh+soThuTu;
                }
            }
        }
        return ma;
    }
    private void filerTableNhanVien() {
        FilteredList<DTO_NhanVien> filteredListNV = new FilteredList<>(listNhanVien, b -> true);
        txt_timNV.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListNV.setPredicate(dtoNhanVien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String gioiTinh = "Nam";
                if(dtoNhanVien.isPhai() == false){
                    gioiTinh = "Nữ";
                }
                if (dtoNhanVien.getMaNhanVien().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                if (dtoNhanVien.getTenNhanVien().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dtoNhanVien.getNgaySinh().toString().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(gioiTinh.toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dtoNhanVien.getNgayVaoLam().toString().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dtoNhanVien.getSoDienThoai().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                if(dtoNhanVien.getDiaChi().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                else return dtoNhanVien.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1;

            });
        });
        SortedList<DTO_NhanVien> sortedData = new SortedList<>(filteredListNV);
        sortedData.comparatorProperty().bind(tbl_NhanVien.comparatorProperty());
        tbl_NhanVien.setItems(sortedData);
    }
    public void suaNhanVien(ActionEvent event) throws IOException, ParseException {
        int idx = tbl_NhanVien.getSelectionModel().getSelectedIndex();
        if (idx == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Vui lòng chọn nhân viên cần sửa");
            Optional<ButtonType> clickedButton = alert.showAndWait();
            return;
        }
        DTO_NhanVien cn_Sua = tbl_NhanVien.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Dialog_SuaNhanVien.fxml"));
        DialogPane addDialog = fxmlLoader.load();
        dialogSua = new Dialog<>();
        dialogSua.setDialogPane(addDialog);
        dialogSua.setTitle("Sửa Nhân viên");
        CTL_UI_SuaNhanVien NVController = fxmlLoader.getController();
        NVController.setData(cn_Sua, cn_Sua.getDiaChi());
        Optional<ButtonType> clickedButton = dialogSua.showAndWait();

        loadTableNV();
        tbl_NhanVien.refresh();
        filerTableNhanVien();

    }
    public void closeDialogSua() {
        //refeshTable();
        dialogSua.setResult(ButtonType.CLOSE);
        dialogSua.close();

    }
    private void checkInfo(){
        if(txt_tenNhanVien.getText().equals("")||
                txt_Email.getText().equals("")||txt_Email.getText().equals("")|| !txt_Email.getText().toString().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")||
                txtLuongCoBan.getText().equals("")||txt_soDienThoai.getText().equals("")||txt_soDienThoai.getText().length()!=10||
                txt_duong.getText().equals("")||cboGioiTinh.getValue()==null||cbo_phuong.getValue()==null||
                cbo_tinh.getValue()==null||cbo_huyen.getValue()==null||dp_ngayVaoLam.getValue()==null||
                dp_ngaySinh.getValue()==null){
            checkFrom = false;
        }
        else {
            checkFrom = true;
        }

    }
    private void CheckRed(){
        txt_tenNhanVien.setStyle("-fx-border-color: RED;");
        txt_soDienThoai.setStyle("-fx-border-color: RED;");
        txtLuongCoBan.setStyle("-fx-border-color: RED;");
        txt_Email.setStyle("-fx-border-color: RED;");
        txt_duong.setStyle("-fx-border-color: RED;");
        dp_ngaySinh.setStyle("-fx-border-color: RED;");
        dp_ngayVaoLam.setStyle("-fx-border-color: RED;");
        cbo_phuong.setStyle("-fx-border-color: RED;");
        cbo_tinh.setStyle("-fx-border-color: RED;");
        cboGioiTinh.setStyle("-fx-border-color: RED;");
        cbo_huyen.setStyle("-fx-border-color: RED;");
    }
    private void CheckGreen(){
        if(checkFrom){
            txt_tenNhanVien.setStyle("-fx-border-color: GREEN;");
            txt_soDienThoai.setStyle("-fx-border-color: GREEN;");
            txtLuongCoBan.setStyle("-fx-border-color: GREEN;");
            txt_Email.setStyle("-fx-border-color: GREEN;");
            txt_duong.setStyle("-fx-border-color: GREEN;");
            dp_ngaySinh.setStyle("-fx-border-color: GREEN;");
            dp_ngayVaoLam.setStyle("-fx-border-color: GREEN;");
            cbo_phuong.setStyle("-fx-border-color: GREEN;");
            cbo_tinh.setStyle("-fx-border-color: GREEN;");
            cboGioiTinh.setStyle("-fx-border-color: GREEN;");
            cbo_huyen.setStyle("-fx-border-color: GREEN;");
        }

    }
}
