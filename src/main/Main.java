package main;

import view.ControlFrame;

import com.fazecast.jSerialComm.SerialPort;

import java.io.*;

import util.SerialPortUtil;


public class Main {
    public static SerialPort chosenPort = ControlFrame.chosenPort;
    public static Thread thread;
    public static void main(String[] args) throws IOException {

        /* Use an appropriate Look and Feel */
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InstantiationException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        /* Turn off metal's use of bold fonts */
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
        SerialPortUtil.getCommPorts();
        System.out.println();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ControlFrame frame = new ControlFrame();
                frame.setVisible(true);
            }
        });
    }
}