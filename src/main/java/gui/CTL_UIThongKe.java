package gui;

import bus.BUS_ThongKe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class CTL_UIThongKe implements Initializable {
    @FXML
    private ComboBox<String> cboLoaiTK;

    @FXML
    private ComboBox<String> cboThoiGian;

    @FXML
    private DatePicker dateCuoi;

    @FXML
    private DatePicker dateDau;

    @FXML
    private Label lblCongNhanDiLam;

    @FXML
    private Label lblCongNhanPhep;

    @FXML
    private Label lblCongNhanVang;

    @FXML
    private Label lblSoLuongSanPham;

    @FXML
    private AnchorPane chart;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String date = formatter.format(LocalDate.now());

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Hide();
        cboThoiGian.setItems(FXCollections.observableArrayList("Ngày", "Tuần", "Tháng", "Khoảng thời gian"));
        cboLoaiTK.setItems(FXCollections.observableArrayList("Thống kê top sản phẩm", "Thông kê lượng sản phẩm làm theo ca", "Thông kê nhân top nhân viên năng xuất", "Thông kê lương công ty"));
        handleEvent();
    }

    private void  thongKeTheoNgayTuan(){
        cboLoaiTK.setItems(FXCollections.observableArrayList("Thống kê top sản phẩm", "Thông kê lượng sản phẩm làm theo ca", "Thông kê nhân top công nhân năng xuất"));
    }




    /**
     * Hiển thị các nút khi chọn vào khoảng chấm ngày
     */
    private void Show() {
        dateDau.setDisable(false);
        dateCuoi.setDisable(false);
    }

    /**
     * Ẩn các nút khi chọn vào khoảng chấm ngày
     */
    private void Hide() {
        dateDau.setDisable(true);
        dateCuoi.setDisable(true);
    }

    private void handleEvent() {
        cboThoiGian.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int index = cboThoiGian.getSelectionModel().getSelectedIndex();
                    if (index == 0) {
                        if (cboLoaiTK.getSelectionModel().getSelectedIndex() == 0)
                            ThongKeTopNhanVienTheoNgay(date);
                        thongKeTheoNgayTuan();
                        Hide();
                        thongKeDiLam(0);
                    } else if (index == 1) {
                        if (cboLoaiTK.getSelectionModel().getSelectedIndex() == 0)
                            ThongKeTopNhanVienTheoTuan(date);
                        thongKeTheoNgayTuan();
                        Hide();
                        thongKeDiLam(1);
                    } else if (index == 2) {
                        if (cboLoaiTK.getSelectionModel().getSelectedIndex() == 0)
                            ThongKeTopNhanVienTheoThang(date);
                        Hide();
                        thongKeDiLam(2);
                    } else if (index == 3) {
//                        if (cboLoaiTK.getSelectionModel().getSelectedIndex() == 0)
//                            ThongKeTopNhanVienTheoKhoang(da);
                        Show();
                        thongKeDiLam(3);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        dateCuoi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                HashMap<Integer, Integer> dicThongKe = null;
                try {
                    dicThongKe = new BUS_ThongKe().getThongKeDiLamTheoKhoangTG(formatter.format(dateDau.getValue()), formatter.format(dateCuoi.getValue()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                int c = dicThongKe.get(1) == null ? 0 : dicThongKe.get(1);
                int v = dicThongKe.get(0) == null ? 0 : dicThongKe.get(2);
                int p = dicThongKe.get(2) == null ? 0 : dicThongKe.get(3);

                lblCongNhanDiLam.setText(c + "");
                lblCongNhanPhep.setText(p + "");
                lblCongNhanVang.setText(v + "");
                lblSoLuongSanPham.setText(c + v + p + "");

                if (cboLoaiTK.getSelectionModel().getSelectedIndex() == 0){
                    try {
                        ThongKeTopNhanVienTheoKhoang(formatter.format(dateDau.getValue()), formatter.format(dateCuoi.getValue()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cboLoaiTK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cboLoaiTK.getSelectionModel().getSelectedIndex() == 0 && cboThoiGian.getSelectionModel().getSelectedIndex() == 0){
                    try {
                        ThongKeTopNhanVienTheoNgay("2022-12-12");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    /**
     * Even thống kê khi nhấn vào combobox
     *
     * @param choice
     * @throws SQLException
     */
    private void thongKeDiLam(int choice) throws SQLException {
        HashMap<Integer, Integer> dicThongKe = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = formatter.format(LocalDate.now());

        if (choice == 0) { // theo ngày
            Clear();
            dicThongKe = new BUS_ThongKe().getThongKeDiLamTheoNgay(date);
        } else if (choice == 1) { // theo tuần
            Clear();
            dicThongKe = new BUS_ThongKe().getThongKeDiLamTheoTuan(date);
        } else if (choice == 2) { // theo tháng
            Clear();
            dicThongKe = new BUS_ThongKe().getThongKeDiLamTheoThang(date);
        } else if (choice == 3) { // theo khoảng thời gian
            return;
//            dicThongKe = new BUS_ThongKe().getThongKeDiLamTheoKhoangTG(formatter.format(dateCuoi.getValue()), formatter.format(dateCuoi.getValue()));
        }
        int c = dicThongKe.get(1) == null ? 0 : dicThongKe.get(1);
        int v = dicThongKe.get(0) == null ? 0 : dicThongKe.get(2);
        int p = dicThongKe.get(2) == null ? 0 : dicThongKe.get(3);

        lblCongNhanDiLam.setText(c + "");
        lblCongNhanPhep.setText(p + "");
        lblCongNhanVang.setText(v + "");
        lblSoLuongSanPham.setText(c + v + p + "");
    }

    private void Clear() {
        lblCongNhanDiLam.setText("0");
        lblCongNhanPhep.setText("0");
        lblCongNhanVang.setText("0");
        lblSoLuongSanPham.setText("0");
    }

    private void ThongKeTopNhanVienTheoNgay(String date) throws SQLException {
        HashMap<String, Integer> data = new BUS_ThongKe().getTopCongNhan(date);
        chart.getChildren().add(createLineChart(data));
    }

    private void ThongKeTopNhanVienTheoTuan(String date) throws SQLException {
        HashMap<String, Integer> data = new BUS_ThongKe().getTopCongNhanTuan(date);
        chart.getChildren().add(createLineChart(data));
    }

    private void ThongKeTopNhanVienTheoThang(String date) throws SQLException {
        HashMap<String, Integer> data = new BUS_ThongKe().getTopCongNhanThang(date);
        chart.getChildren().add(createLineChart(data));
    }

    private void ThongKeTopNhanVienTheoKhoang(String date, String date2) throws SQLException {
        HashMap<String, Integer> data = new BUS_ThongKe().getTopCongNhanKhoang(date,date2);
        chart.getChildren().add(createLineChart(data));
    }




    public BarChart createLineChart(HashMap<String, Integer> hashMap) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tên nhân viên");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Lương sản phẩm");

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Java");
        Set<String> keySet = hashMap.keySet();
        for (String key : keySet) {
            dataSeries.getData().add(new XYChart.Data(key, hashMap.get(key)));
        }

        BarChart chart = new BarChart(xAxis, yAxis);
        chart.getData().addAll(dataSeries);
        chart.setTitle(" BIỂU ĐỒ TOP NHÂN VIÊN NĂNG XUẤT");
        return chart;
    }

    public BarChart createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Region");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Area (km²)");

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Region");
        dataSeries.getData().add(new XYChart.Data("Africa", 30370000));
        dataSeries.getData().add(new XYChart.Data("Antarctica", 14000000));
        dataSeries.getData().add(new XYChart.Data("Asia", 44579000));
        dataSeries.getData().add(new XYChart.Data("Europe", 10180000));
        dataSeries.getData().add(new XYChart.Data("North America", 24709000));
        dataSeries.getData().add(new XYChart.Data("Australia", 8600000));
        dataSeries.getData().add(new XYChart.Data("South America", 17840000));

        BarChart chart = new BarChart(xAxis, yAxis);
        chart.getData().add(dataSeries);
        chart.setTitle("The chart summarizes the area of each continent.");
        return chart;
    }

    public PieChart createChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.addAll(new PieChart.Data("Africa", 30370000),
                new PieChart.Data("Antarctica", 14000000),
                new PieChart.Data("Asia", 44579000),
                new PieChart.Data("Europe", 10180000),
                new PieChart.Data("North America", 24709000),
                new PieChart.Data("Australia", 8600000),
                new PieChart.Data("South America", 17840000));

        PieChart chart = new PieChart();
        chart.setData(data);
        chart.setTitle("The chart summarizes the area of each continent.");
        chart.setClockwise(true);
        chart.setLabelLineLength(30);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(true);
        chart.setStartAngle(50);
        chart.setLegendSide(Side.RIGHT);

        return chart;
    }

    public PieChart createPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.addAll(new PieChart.Data("Africa", 30370000),
                new PieChart.Data("Antarctica", 14000000),
                new PieChart.Data("Asia", 44579000),
                new PieChart.Data("Europe", 10180000),
                new PieChart.Data("North America", 24709000),
                new PieChart.Data("Australia", 8600000),
                new PieChart.Data("South America", 17840000));

        PieChart chart = new PieChart();
        chart.setData(data);
        chart.setTitle("The chart summarizes the area of each continent.");
        chart.setClockwise(true);
        chart.setLabelLineLength(30);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(true);
        chart.setStartAngle(50);
        chart.setLegendSide(Side.RIGHT);

        return chart;
    }


}
