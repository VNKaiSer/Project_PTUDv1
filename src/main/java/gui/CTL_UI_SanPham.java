package gui;

import bus.BUS_SanPham;
import db.ConnectDB;
import dto.DTO_SanPham;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;



public class CTL_UI_SanPham implements Initializable {

    @FXML
    private ComboBox<String> Cbo_ChatLieu;

    @FXML
    private Button btn_DatLai;

    @FXML
    private Button btn_Sua;

    @FXML
    private Button btn_Xoa;

    @FXML
    private Button btn_XuatDanhSach;

    @FXML
    private Button btn_themDanhSach;

    @FXML
    private Button btn_themMoi;

    @FXML
    private TableColumn<DTO_SanPham, String> col_maSP;

    @FXML
    private TableColumn<DTO_SanPham, String> col_soCongDoan;

    @FXML
    private TableColumn<DTO_SanPham, String> col_soLuongSPYeuCau;

    @FXML
    private TableColumn<DTO_SanPham, String> col_tenSP;

    @FXML
    private TableColumn<DTO_SanPham, String> col_ChatLieu;
    @FXML
    private TableView<DTO_SanPham> tbl_SanPham;

    @FXML
    private TextField txt_TimSP;

    @FXML
    private TextField txt_maSP;

    @FXML
    private TextField txt_soCongDoan;

    @FXML
    private TextField txt_soLuongSPYeuCau;

    @FXML
    private TextField txt_tenSP;
    private boolean checkFrom = false;
    ObservableList<String> listChatLieu = FXCollections.observableArrayList("Nỉ", "Kaki","Cotton","Kate","Da","Jean","Demin","PE(Nylon)","Chiffon","Vải Lanh");
    private ObservableList<DTO_SanPham> listSanPham;
    private BUS_SanPham bus_sanPham = new BUS_SanPham();
    @FXML
    private ImageView imgv_anhSp;
    private DTO_SanPham sanPhamClick;
    @FXML
    private Button btnAnh;

    @FXML
    private Label lblAnh;

    private File file;
    private ArrayList listSP= new ArrayList();
    private boolean checkXoa = true;
    private boolean checkSua = true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkInfo();
        btnAnh.setDisable(true);
        try {
            Cbo_ChatLieu.setItems(listChatLieu);

            txt_maSP.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txt_maSP.setStyle("-fx-border-color: GREEN;");
                    checkFrom=true;
                    if (txt_maSP.getText().equals("")){
                        txt_maSP.setStyle("-fx-border-color: RED;");
                        checkFrom=false;
                    }
                }
            });

            txt_tenSP.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txt_tenSP.setStyle("-fx-border-color: GREEN;");
                    checkFrom=true;
                    if (txt_tenSP.getText().equals("")){
                        txt_tenSP.setStyle("-fx-border-color: RED;");
                        checkFrom=false;
                    }
                }
            });

            txt_soCongDoan.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txt_soCongDoan.setStyle("-fx-border-color: GREEN;");
                    checkFrom=true;
                    if (txt_soCongDoan.getText().equals("")){
                        txt_soCongDoan.setStyle("-fx-border-color: RED;");
                        checkFrom=false;
                    }
                }
            });

            txt_soLuongSPYeuCau.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    txt_soLuongSPYeuCau.setStyle("-fx-border-color: GREEN;");
                    checkFrom=true;
                    if (txt_soLuongSPYeuCau.getText().equals("")){
                        txt_soLuongSPYeuCau.setStyle("-fx-border-color: RED;");
                        checkFrom=false;
                    }
                }
            });

            Cbo_ChatLieu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Cbo_ChatLieu.setStyle("-fx-border-color: GREEN;");
                    checkFrom=true;
                    if (Cbo_ChatLieu.getValue() == null){
                        Cbo_ChatLieu.setStyle("-fx-border-color: RED;");
                        checkFrom=false;
                    }
                }
            });

            btnAnh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ChooseImage(event);
                }
            });




        } catch (Exception e) {

        }
        txt_soCongDoan.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txt_soCongDoan.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txt_soCongDoan.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txt_soCongDoan.setText(newValue);
            } else {
                txt_soCongDoan.setText(newValue);
            }
        });
        txt_soLuongSPYeuCau.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txt_soLuongSPYeuCau.setText(newValue.replaceAll("[^\\d,]", ""));
                StringBuilder aus = new StringBuilder();
                aus.append(txt_soLuongSPYeuCau.getText());
                boolean firstPointFound = false;
                newValue = aus.toString();
                txt_soLuongSPYeuCau.setText(newValue);
            } else {
                txt_soLuongSPYeuCau.setText(newValue);
            }
        });


        handleEvent();
        HideInfo();
        loadTableSP();
    }

    public void handleEvent() {
        tbl_SanPham.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            listSP = bus_sanPham.getAllSanPham();
            sanPhamClick = tbl_SanPham.getSelectionModel().getSelectedItem();
            txt_maSP.setText(sanPhamClick.getMaSanPham());
            txt_tenSP.setText(sanPhamClick.getTenSanPham());
            txt_soLuongSPYeuCau.setText(sanPhamClick.getSoLuongYeuCau() + "");
            txt_soCongDoan.setText(sanPhamClick.getSoCongDoan()+"");
            Cbo_ChatLieu.setValue(sanPhamClick.getChatLieu());
            InputStream is = new ByteArrayInputStream(sanPhamClick.getHinhAnh());
            Image image = new Image(is);
            imgv_anhSp.setImage(image);
        });
        btn_themMoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btnAnh.setDisable(false);
                if(btn_themMoi.getText().equalsIgnoreCase("Thêm")){
                    btn_themMoi.setText("Lưu");
                    btn_Xoa.setText("Hủy");
                    btn_Sua.setDisable(true);
                    Clear();
                    txt_maSP.setText(taoMaSP());
                    ShowInfo();
                } else if (btn_themMoi.getText().equalsIgnoreCase("Lưu")) {
                    checkInfo();
                    if(Cbo_ChatLieu.getValue() == null){
                        checkFrom = false;
                        Cbo_ChatLieu.setStyle("-fx-border-color: RED;");
                    }
                    checkHinhAnh();
                    if (!checkFrom) {
                        Alert wn = new Alert(Alert.AlertType.WARNING, "Cảnh báo", ButtonType.APPLY);
                        wn.setContentText("Vui lòng chỉnh sửa thông tin các trường bị đánh dấu hoặc thêm hình ảnh cho sản phẩm");
                        Optional<ButtonType> showWN = wn.showAndWait();
                        return;
                    }
                    else {
                        themSP();
                    }

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
                    btn_Sua.setText("Sửa");
                    btn_Sua.setDisable(false);
                    btn_themMoi.setDisable(false);
                    tbl_SanPham.setDisable(false);
                    HideInfo();
                    btnAnh.setDisable(true);
                } else if (btn_Xoa.getText().equalsIgnoreCase("Xóa")) {
                    xoaSP();
                }

            }
        });
        btn_Sua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (tbl_SanPham.getSelectionModel().getSelectedItem() == null){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dòng cần sửa", ButtonType.CANCEL);
                    a.showAndWait();
                    return;
                }
                try {
                    btnAnh.setDisable(false);
                    btn_themMoi.setDisable(true);
                    btn_Xoa.setText("Hủy");
                    tbl_SanPham.setDisable(true);
                    SuaSP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        btn_DatLai.setOnAction(new EventHandler<ActionEvent>() {
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
                        String sql = "INSERT INTO SanPham VALUES(?,?,?,?,?,?,?)";
                        PreparedStatement ppsm = ConnectDB.getConnection().prepareStatement(sql);

                        FileInputStream fileIn = new FileInputStream(file);
                        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
                        XSSFSheet sh = wb.getSheetAt(0);
                        Row row;

                        for (int i = 1; i<=sh.getLastRowNum();i++){
                            row = sh.getRow(i);
                            int count = tbl_SanPham.getItems().size();
                            String ma = "null";
                            if(count==0){
                                ma = "SP"+"0001";
                            }
                            else {
                                for (DTO_SanPham n : listSanPham) {
                                    int stt = Integer.parseInt(n.getMaSanPham().substring(2));
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
                                        ma = "SP"+soThuTu;
                                    }
                                }
                            }
                            ppsm.setString(1, ma);
                            ppsm.setString(2, row.getCell(0).getStringCellValue());
                            ppsm.setInt(3, (int) row.getCell(1).getNumericCellValue());
                            ppsm.setInt(4, (int) row.getCell(2).getNumericCellValue());
                            ppsm.setString(5, row.getCell(3).getStringCellValue());
                            ppsm.setBoolean(6, false);
                            ppsm.execute();
                            loadTableSP();
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
        btn_XuatDanhSach.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    ConnectDB.getInstance().connect();
                    String sql = "select maSanPham,tenSanPham,soCongDoan,soLuong,chatLieu,trangThai from SanPham";
                    Statement stm = ConnectDB.getConnection().createStatement();
                    ResultSet rs = stm.executeQuery(sql);

                    XSSFWorkbook wb = new XSSFWorkbook();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
                    Date ngay = new Date();
                    String ngayHienTai = df.format(ngay);
                    XSSFSheet sh = wb.createSheet("Product_"+ngayHienTai);
                    XSSFRow header = sh.createRow(0);
                    header.createCell(0).setCellValue("Mã sản phẩm");
                    header.createCell(1).setCellValue("Tên sản phẩm");
                    header.createCell(2).setCellValue("Số công đoạn");
                    header.createCell(3).setCellValue("Số lượng");
                    header.createCell(4).setCellValue("Chất liệu");
                    header.createCell(5).setCellValue("Trạng thấi");
                    int index=1;
                    while (rs.next()){
                        XSSFRow row = sh.createRow(index);
                        row.createCell(0).setCellValue(rs.getString("maSanPham"));
                        row.createCell(1).setCellValue(rs.getString("tenSanPham"));
                        row.createCell(2).setCellValue(rs.getString("soCongDoan"));
                        row.createCell(3).setCellValue(rs.getString("soLuong"));
                        row.createCell(4).setCellValue(rs.getString("chatLieu"));
                        String trangThai = "Chưa hoàn thành";
                        if(rs.getBoolean("trangThai") == true){
                            trangThai = "Đã hoàn thành";
                        }
                        row.createCell(5).setCellValue(trangThai);
                        index++;

                    }
                    FileOutputStream fileOut = new FileOutputStream("Product_"+ngayHienTai+".xlsx");
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

        tbl_SanPham.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("Info_SanPham.fxml"));
//                DialogPane addDialog = null;
//                try {
//                    addDialog = fxmlLoader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                Dialog hienThi = new Dialog<>();
//                hienThi.setDialogPane(addDialog);
//                hienThi.setTitle("Thông Tin Sản Phẩm");
//                CTL_GUI_DialogInforSP dialogInforSP = fxmlLoader.getController();
//                try {
//                    dialogInforSP.setData(tbl_SanPham.getSelectionModel().getSelectedItem());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                Optional<ButtonType> clickedButton = hienThi.showAndWait();
            }
        });
    }

    private void themSP(){
        try {

            String tenSP = txt_tenSP.getText();
            String soCongDoan = txt_soCongDoan.getText();
            String soLuongSPYeuCau = txt_soLuongSPYeuCau.getText();
            String chatLieu = Cbo_ChatLieu.getValue().toString();
            String maSP =  txt_maSP.getText();
            DTO_SanPham sp = new DTO_SanPham(maSP,tenSP,Integer.parseInt(soCongDoan),Integer.parseInt(soLuongSPYeuCau),chatLieu);
            sp.setHinhAnh(imagenToByte(file));
            bus_sanPham.insertSanPham(sp);
            loadTableSP();
            Clear();
            txt_maSP.setText(taoMaSP());
            checkInfo();

            //tbl_SanPham.getSelectionModel().selectLast();
        } catch (Exception e) {
            System.err.println(e);
        }

    }
    private void xoaSP(){
        int idx = tbl_SanPham.getSelectionModel().getSelectedIndex();

        if (idx == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Vui lòng chọn sản phẩm cần xóa");
            Optional<ButtonType> clickedButton = alert.showAndWait();
            return;
        }

        String maSP = tbl_SanPham.getColumns().get(0).getCellObservableValue(idx).getValue().toString();

        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "Bạn có chắc muốn xóa không?",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("Cảnh báo");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            try {
                bus_sanPham.deleteSanPham(maSP);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            loadTableSP();
        }
    }

    /**
     * Hàm cập nhật sản phẩm
     * @throws IOException
     */
    private void SuaSP() throws IOException {
        if(btn_Sua.getText().equalsIgnoreCase("Sửa")){
            btn_Sua.setText("Lưu");
            ShowInfo();
        }
        else { // là nút lưu
            String maSP = txt_maSP.getText();
            String tenSP = txt_tenSP.getText();
            int sl = Integer.parseInt(txt_soLuongSPYeuCau.getText());
            String chatLieu = Cbo_ChatLieu.getSelectionModel().getSelectedItem();
            int soCD = Integer.parseInt(txt_soCongDoan.getText());
            DTO_SanPham sp = new DTO_SanPham(maSP,tenSP,soCD,sl,chatLieu);
            sp.setHinhAnh(sanPhamClick.getHinhAnh());
            bus_sanPham.updateSP(sp);
            HideInfo();
            btn_Sua.setText("Sửa");
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Cập nhật thành công", ButtonType.APPLY);
            a.showAndWait();
            Clear();
            btnAnh.setDisable(true);
            btn_themMoi.setDisable(false);
            tbl_SanPham.setDisable(false);
            loadTableSP();

        }
    }
    private void loadTableSP(){
        tbl_SanPham.getItems().clear();
        try {
            ArrayList<DTO_SanPham> ds = bus_sanPham.getAllSanPham();
            listSanPham = FXCollections.observableArrayList(ds);
            col_maSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
            col_tenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
            col_soCongDoan.setCellValueFactory(new PropertyValueFactory<>("soCongDoan"));
            col_soLuongSPYeuCau.setCellValueFactory(new PropertyValueFactory<>("soLuongYeuCau"));
            col_ChatLieu.setCellValueFactory(new PropertyValueFactory<>("chatLieu"));

            tbl_SanPham.setItems(listSanPham);
        } catch (Exception e) {

        }
    }
    private void HideInfo(){
        txt_maSP.setDisable(true);
        txt_tenSP.setDisable(true);
        txt_soCongDoan.setDisable(true);
        txt_soLuongSPYeuCau.setDisable(true);
        Cbo_ChatLieu.setDisable(true);
    }
    private void ShowInfo(){
        txt_tenSP.setDisable(false);
        txt_soCongDoan.setDisable(false);
        txt_soLuongSPYeuCau.setDisable(false);
        Cbo_ChatLieu.setDisable(false);
    }
    private void Clear(){
        txt_maSP.setText("");
        txt_tenSP.setText("");
        txt_soCongDoan.setText("");
        txt_tenSP.setText("");
        txt_soLuongSPYeuCau.setText("");
        Cbo_ChatLieu.getSelectionModel().clearSelection();
        imgv_anhSp.setImage(null);
        checkInfo();
    }
    private String taoMaSP() {
        int count = tbl_SanPham.getItems().size();
        String ma = "";
        if(count==0){
            ma = "SP"+"0001";
        }
        else {
            for (DTO_SanPham n : listSanPham) {
                int stt = Integer.parseInt(n.getMaSanPham().substring(2));
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
                    ma = "SP"+soThuTu;
                }
            }
        }
        return ma;
    }

    public void ChooseImage (ActionEvent e){
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(imageFilter);
        file = fc.showOpenDialog((Stage)((Node) e.getSource()).getScene().getWindow());
        if (file != null){
            Image image = new Image(file.toURI().toString(),lblAnh.getWidth(), lblAnh.getHeight(), false, true);
            imgv_anhSp.setImage(image);
        }
    }

    private byte[] imagenToByte(File f) throws IOException {
//        Image img = imgv_anhSp.getImage();
        BufferedImage image = ImageIO.read(f);
        ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outStreamObj);
        byte [] byteArray = outStreamObj.toByteArray();


        return byteArray;
    }
    private void checkInfo(){
        if(txt_maSP.getText().equals("")){
            txt_maSP.setStyle("-fx-border-color: RED;");
        }
        else {
            txt_maSP.setStyle("-fx-border-color: GREEN;");
        }
        if(txt_tenSP.getText().equals("")){
            txt_tenSP.setStyle("-fx-border-color: RED;");
        }
        else {
            txt_tenSP.setStyle("-fx-border-color: GREEN;");
        }
        if(txt_soCongDoan.getText().equals("")){
            txt_soCongDoan.setStyle("-fx-border-color: RED;");
        }
        else {
            txt_soCongDoan.setStyle("-fx-border-color: GREEN;");
        }
        if(txt_soLuongSPYeuCau.getText().equals("")){
            txt_soLuongSPYeuCau.setStyle("-fx-border-color: RED;");
        }
        else {
            txt_soLuongSPYeuCau.setStyle("-fx-border-color: GREEN;");
        }
        if(Cbo_ChatLieu.getValue()==null){
            Cbo_ChatLieu.setStyle("-fx-border-color: RED;");
        }
        else {
            Cbo_ChatLieu.setStyle("-fx-border-color: GREEN;");
        }

    }
    private void checkHinhAnh(){
        if(imgv_anhSp.getImage()==null){
            checkFrom=false;
        }
        else {
            checkFrom=true;
        }
    }

}
