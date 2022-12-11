package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CTL_UIThongKe implements Initializable {
    @FXML
    private AnchorPane lineChart;
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
        lineChart.getChildren().add(createPieChart());
    }

    public LineChart createLineChart(ArrayList<Integer> listTime, ArrayList<Double> listTien) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Thời gian");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Số tiền");

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Java");
        int i  = 0;
        for (Integer it:
             listTime) {
            dataSeries.getData().add(new XYChart.Data(it, listTien.get(i++)));
        }
        LineChart chart = new LineChart(xAxis, yAxis);
        chart.getData().addAll(dataSeries);
        chart.setTitle("Top Programming Languages");
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
