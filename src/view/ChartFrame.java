package view;

import util.FormatDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

/**
 * This Class creates the window were the incoming Serial data will be
 * illustrated on six X-Y charts. In this implementation the incoming data
 * are:
 * - temperature
 * - humidity
 * - barometric pressure
 * - light intensity
 * - R,G,B light intensity values
 * - tilt position of the sensor in two dimensions
 */
public class ChartFrame extends JFrame {

    public ChartFrame(String name) {
        super("Serial Incoming Data");
        this.setResizable(true);
    }

    public void addComponentsToPane(Container pane) {
        JPanel graphsPanel = new JPanel();
        graphsPanel.setLayout(new GridLayout(0, 2));

        XYSeriesCollection dataset1 = new XYSeriesCollection(FormatDataset.tempData);
        XYSeriesCollection dataset2 = new XYSeriesCollection(FormatDataset.humidData);
        XYSeriesCollection dataset3 = new XYSeriesCollection(FormatDataset.pressureData);
        XYSeriesCollection dataset4 = new XYSeriesCollection(FormatDataset.lightData);
        XYSeriesCollection dataset5 = new XYSeriesCollection();
            dataset5.addSeries(FormatDataset.redLightData);
            dataset5.addSeries(FormatDataset.greenLightData);
            dataset5.addSeries(FormatDataset.blueLightData);
        XYSeriesCollection dataset6 = new XYSeriesCollection();
            dataset6.addSeries(FormatDataset.degreesXData);
            dataset6.addSeries(FormatDataset.degreesYData);

        JFreeChart chart1 = ChartFactory
                .createXYLineChart("Temperature", "Time (seconds)", "degrees Celsius", dataset1);
        JFreeChart chart2 = ChartFactory
                .createXYLineChart("Humidity", "Time (seconds)", "%RH", dataset2);
        JFreeChart chart3 = ChartFactory
                .createXYLineChart("Barometric Pressure", "Time (seconds)", "kPa", dataset3);
        JFreeChart chart4 = ChartFactory
                .createXYLineChart("Light Intensity", "Time (seconds)", "integer (0-4097)", dataset4);
        JFreeChart chart5 = ChartFactory
                .createXYLineChart("RGB Light Intensity", "Time (seconds)", "integer (0-4097)", dataset5);
        JFreeChart chart6 = ChartFactory
                .createXYLineChart("Gyroscope Tilt", "Time (seconds)", "degrees", dataset6);

        graphsPanel.add(new ChartPanel(chart1), -1);
        graphsPanel.add(new ChartPanel(chart2), -1);
        graphsPanel.add(new ChartPanel(chart3), -1);
        graphsPanel.add(new ChartPanel(chart4), -1);
        graphsPanel.add(new ChartPanel(chart5), -1);
        graphsPanel.add(new ChartPanel(chart6), -1);
        pane.add(graphsPanel, "South");
    }

    public static void createAndShowGUI() {
        ChartFrame frame = new ChartFrame("GridLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addComponentsToPane(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }
}

