package gui;

import bus.BUS_API;
import bus.BUS_NhanVien;
import db.ConnectDB;
import dto.DTO_CongNhan;
import dto.DTO_NhanVien;
import dto.DTO_SanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ComboBox<String> cbo_ChucVu;

    @FXML
    private ComboBox<String> cbo_huyen;

    @FXML
    private ComboBox<String> cbo_luongCoBan;

    @FXML
    private ComboBox<String> cbo_phuong;

    @FXML
    private ComboBox<String> cbo_tienPhuCap;

    @FXML
    private ComboBox<String> cbo_tienTrachNhiem;

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
    private TableColumn<DTO_NhanVien, String> col_tienPhuCap;

    @FXML
    private TableColumn<DTO_NhanVien, String> col_tienTrachNhiem;

    @FXML
    private DatePicker dp_ngaySinh;

    @FXML
    private DatePicker dp_ngayVaoLam;

    @FXML
    private TableView<DTO_NhanVien> tbl_NhanVien;

    @FXML
    private TextField txt_Email;

    @FXML
    private TextField txt_duong;

    @FXML
    private TextField txt_maNhanVIen;

    @FXML
    private TextField txt_soDienThoai;

    @FXML
    private TextField txt_tenNhanVien;
    static BUS_API connectAPI;
    private ObservableList<DTO_NhanVien> listNhanVien;
    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
    ObservableList<String> listChucVu = FXCollections.observableArrayList("Nhân viên văn phòng", "Trường phòng");
    private BUS_NhanVien bus_nhanVien = new BUS_NhanVien();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboGioiTinh.setItems(listGioiTinh);
        cbo_ChucVu.setItems(listChucVu);
        connectAPI = new BUS_API();
        cbo_tinh.setItems(connectAPI.getDanhSachTinh());
        cbo_tinh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tinh = cbo_tinh.getValue();
                cbo_huyen.setDisable(false);
                String codeProvince = cbo_tinh.getValue();
                cbo_huyen.setItems(connectAPI.getDanhSachHuyen(codeProvince));
            }
        });

        cbo_huyen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String huyen = cbo_tinh.getValue();
                cbo_phuong.setDisable(false);
                String codeDis = cbo_huyen.getValue();
                cbo_phuong.setItems(connectAPI.getDanhSachDuong(codeDis));
            }
        });

        handleEvent();
        HideInfo();
        loadTableNV();
    }
    public void handleEvent(){
        cbo_ChucVu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cbo_ChucVu.getValue().toString().equalsIgnoreCase("Nhân viên văn phòng")){
                    cbo_luongCoBan.setValue("5000000");
                    cbo_tienPhuCap.setValue("1000000");
                    cbo_tienTrachNhiem.setValue("1500000");
                }
                else {
                    cbo_luongCoBan.setValue("15000000");
                    cbo_tienPhuCap.setValue("10000000");
                    cbo_tienTrachNhiem.setValue("15000000");
                }
            }
        });
        btn_themMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(btn_themMoi.getText().equalsIgnoreCase("Thêm")){
                    btn_themMoi.setText("Lưu");
                    btn_Xoa.setText("Hủy");
                    btn_Sua.setDisable(true);
                    ShowInfo();

                } else if (btn_themMoi.getText().equalsIgnoreCase("Lưu")) {
                    themNV();
                    Clear();
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
                } else if (btn_Xoa.getText().equalsIgnoreCase("Xóa")) {
                    xoaNV();
                }

            }
        });
        btn_lamMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Clear();
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
                        String sql = "INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?,?,?,?,?)";
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
                            ppsm.setDouble(10, row.getCell(8).getNumericCellValue());
                            ppsm.setDouble(11, row.getCell(9).getNumericCellValue());

                            ppsm.execute();
                            loadTableNV();
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
                    header.createCell(9).setCellValue("Tiền phụ cấp");
                    header.createCell(10).setCellValue("Tiền trách nhiệm");
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
                        row.createCell(9).setCellValue(rs.getString("tienPhuCap"));
                        row.createCell(10).setCellValue(rs.getString("tienTrachNhiem"));
                        index++;

                    }
                    FileOutputStream fileOut = new FileOutputStream("NhanVien_"+ngayHienTai+".xlsx");
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
            String tenNV = txt_tenNhanVien.getText();
            String gioiTinh = cboGioiTinh.getValue();
            Boolean phai = true;
            if(gioiTinh.equalsIgnoreCase("Nữ")){
                phai = false;
            }
            String sdt = txt_soDienThoai.getText();
            String chucVu = cbo_ChucVu.getValue();
            Date ngayVaoLam = Date.from(dp_ngayVaoLam.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            Date ngaySinh = Date.from(dp_ngaySinh.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            //String ngay
            String email = txt_Email.getText();
            Double luongCoBan = Double.valueOf(cbo_luongCoBan.getValue());
            Double tienPhuCap = Double.valueOf(cbo_tienPhuCap.getValue());
            Double tienTrachNhiem = Double.valueOf(cbo_tienTrachNhiem.getValue());
            String diaChi = txt_duong.getText() +"," + cbo_phuong.getValue()+ ", " +cbo_huyen.getValue() + ", "+cbo_tinh.getValue();
            String maNV = taoMaNV();
            DTO_NhanVien nv = new DTO_NhanVien(maNV,tenNV,ngayVaoLam,phai,ngaySinh,sdt,email,diaChi,luongCoBan,tienPhuCap,tienTrachNhiem);
            bus_nhanVien.insertNhanVien(nv);
            loadTableNV();
            //tbl_SanPham.getSelectionModel().selectLast();
        } catch (Exception e) {

        }

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
            col_tienPhuCap.setCellValueFactory(new PropertyValueFactory<>("tienPhuCap"));
            col_tienTrachNhiem.setCellValueFactory(new PropertyValueFactory<>("tienTrachNhiem"));

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
        cbo_ChucVu.setDisable(true);
        dp_ngayVaoLam.setDisable(true);
        dp_ngaySinh.setDisable(true);
        txt_soDienThoai.setDisable(true);
        txt_Email.setDisable(true);
        cbo_luongCoBan.setDisable(true);
        cbo_tienPhuCap.setDisable(true);
        cbo_tienTrachNhiem.setDisable(true);
    }
    private void ShowInfo(){
        txt_tenNhanVien.setDisable(false);
        cboGioiTinh.setDisable(false);
        cbo_ChucVu.setDisable(false);
        dp_ngayVaoLam.setDisable(false);
        dp_ngaySinh.setDisable(false);
        txt_soDienThoai.setDisable(false);
        txt_Email.setDisable(false);
        cbo_luongCoBan.setDisable(false);
        cbo_tienPhuCap.setDisable(false);
        cbo_tienTrachNhiem.setDisable(false);
    }
    private void Clear(){
        txt_tenNhanVien.setText("");
        txt_soDienThoai.setText("");
        txt_Email.setText("");
        cboGioiTinh.getSelectionModel().clearSelection();
        cbo_ChucVu.getSelectionModel().clearSelection();
        cbo_luongCoBan.getSelectionModel().clearSelection();
        cbo_tienPhuCap.getSelectionModel().clearSelection();
        cbo_tienTrachNhiem.getSelectionModel().clearSelection();
        cbo_tinh.getSelectionModel().clearSelection();
        cbo_huyen.getSelectionModel().clearSelection();
        cbo_phuong.getSelectionModel().clearSelection();
        txt_duong.setText("");
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
}
