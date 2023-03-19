package util;

import view.ControlFrame;
import org.jfree.data.xy.XYSeries;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This Class provides the method(s) for parsing the
 * Serial port input stream.
 */
public class FormatDataset {
    public static final XYSeries tempData = new XYSeries("Temperature Sensor Readings");
    public static final XYSeries humidData = new XYSeries("Humidity Sensor Readings");
    public static final XYSeries pressureData = new XYSeries("Pressure Sensor Readings");
    public static final XYSeries lightData = new XYSeries("Light Sensor Readings");
    public static final XYSeries redLightData = new XYSeries("Red Light Sensor Readings");
    public static final XYSeries greenLightData = new XYSeries("Green Light Sensor Readings");
    public static final XYSeries blueLightData = new XYSeries("Blue Light Sensor Readings");
    public static final XYSeries degreesXData = new XYSeries("X-Axis Gyroscope Sensor Readings");
    public static final XYSeries degreesYData = new XYSeries("Y-Axis Gyroscope Sensor Readings");
    public static String[] values;
    public static String data1;
    public static String data2;
    public static String data3;
    public static String data4;
    public static String data5;
    public static String data6;
    public static String data7;
    public static String data8;
    public static String data9;
    /**
     * Parses the port input stream into a CSV string containing
     * values of incoming data. The String is converted into
     * an array oi incoming data that populate the {@link view.ChartFrame}
     * charts.
     */
    public static void parseCSV() {
        int x = 0;
        Scanner scanner = new Scanner(ControlFrame.chosenPort.getInputStream());

        while (scanner.hasNextLine()) {

            try {
                String line = scanner.nextLine();
                values = line.split(",");
                data1 = values[0];
                data2 = values[1];
                data3 = values[2];
                data4 = values[3];
                data5 = values[4];
                data6 = values[5];
                data7 = values[6];
                data8 = values[7];
                data9 = values[8];

                tempData.add(x++, Double.valueOf(data1));
                humidData.add(x++, Double.valueOf(data2));
                pressureData.add(x++, Double.valueOf(data3));
                lightData.add(x++, Double.valueOf(data4));
                redLightData.add(x++, Double.valueOf(data5));
                greenLightData.add(x++, Double.valueOf(data6));
                blueLightData.add(x++, Double.valueOf(data7));
                degreesXData.add(x++, Double.valueOf(data8));
                degreesYData.add(x++, Double.valueOf(data9));

                System.out.println(data1 + ", " + data2
                        + ", " + data3 + ", " + data4
                        + ", " + data5  + ", " + data6
                        + ", " + data7 + ", " + data8
                        + data9);
            } catch (Exception e) {
                System.err.println("Corrupt incoming data. " + e);
                ControlFrame.chosenPort.closePort();
                throw e;
            }
        }
    }
}
