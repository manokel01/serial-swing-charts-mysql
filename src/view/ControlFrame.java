package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import javax.swing.event.PopupMenuListener;

import com.fazecast.jSerialComm.SerialPort;
import util.ComPortListener;
import util.DBUtil;
import util.FormatDataset;
import util.SerialPortUtil;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class ControlFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static SerialPort chosenPort;
	private JTextField textFieldSendData;

	Timer timer = new Timer(1000, null);
	public ControlFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panelSQL = new JPanel();
		panelSQL.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "MySQL Connection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSQL.setBounds(6, 470, 788, 96);
		contentPane.add(panelSQL);
		panelSQL.setLayout(null);

		JButton btnSQLStart = new JButton("Start");
		btnSQLStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO DATASETS (DATA_1, DATA_2, DATA_3, DATA_4, DATA_5, DATA_6) " +
						"VALUES (?, ?, ?, ?, ?, ?)";
				try (Connection conn = DBUtil.getConnection();
					 PreparedStatement p = conn.prepareStatement(sql)) {
					while (true) {

						p.setDouble(1, Double.parseDouble(FormatDataset.data1)); // inserting data to database
						p.setDouble(2, Double.parseDouble(FormatDataset.data2)); // inserting data to database
						p.setDouble(3, Double.parseDouble(FormatDataset.data3)); // inserting data to database
						p.setDouble(4, Double.parseDouble(FormatDataset.data4)); // inserting data to database
						p.setDouble(5, Double.parseDouble(FormatDataset.data5)); // inserting data to database
						p.setDouble(6, Double.parseDouble(FormatDataset.data6)); // inserting data to database
						p.execute();
						timer.setInitialDelay(1000);

					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "SQL Error");
					e1.printStackTrace();
				}
			}
		});
		btnSQLStart.setBounds(180, 39, 117, 29);
		panelSQL.add(btnSQLStart);
		btnSQLStart.setEnabled(true);

		JButton btnSQLLogin = new JButton("Login");
		btnSQLLogin.setBounds(45, 39, 117, 29);
		panelSQL.add(btnSQLLogin);

		JProgressBar progressBarSQL = new JProgressBar();
		progressBarSQL.setValue(100);
		progressBarSQL.setBounds(551, 39, 212, 20);
		panelSQL.add(progressBarSQL);
		progressBarSQL.setValue(0);

		JLabel lblSQLStatus = new JLabel("MySQL connection status");
		lblSQLStatus.setBounds(368, 39, 169, 16);
		panelSQL.add(lblSQLStatus);

		JPanel panelTop = new JPanel();
		panelTop.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Serial Connection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelTop.setBounds(6, 6, 788, 452);
		contentPane.add(panelTop);
		panelTop.setLayout(null);

		JPanel panelSettings = new JPanel();
		panelSettings.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelSettings.setBounds(22, 34, 295, 392);
		panelTop.add(panelSettings);
		panelSettings.setLayout(null);

		JComboBox comboBoxComPorts = new JComboBox();
		comboBoxComPorts.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				comboBoxComPorts.removeAllItems();
				SerialPort[] portList = SerialPort.getCommPorts();
				for (SerialPort port : portList) {
					comboBoxComPorts.addItem(port.getSystemPortName());
				}
			}
		});
		comboBoxComPorts.setBounds(122, 20, 149, 27);
		panelSettings.add(comboBoxComPorts);
		comboBoxComPorts.setEnabled(true);

		JComboBox comboBoxBaudRate = new JComboBox();
		comboBoxBaudRate.setModel(new DefaultComboBoxModel(new String[] {"4800", "9600", "38400", "57600", "115200"}));
		comboBoxBaudRate.setBounds(122, 66, 149, 27);
		panelSettings.add(comboBoxBaudRate);
		comboBoxBaudRate.setSelectedItem("9600");

		JComboBox comboBoxDataBits = new JComboBox();
		comboBoxDataBits.setModel(new DefaultComboBoxModel(new String[] {"6", "7", "8"}));
		comboBoxDataBits.setBounds(122, 113, 149, 27);
		panelSettings.add(comboBoxDataBits);
		comboBoxDataBits.setSelectedItem("8");

		JComboBox comboBoxStopBits = new JComboBox();
		comboBoxStopBits.setModel(new DefaultComboBoxModel(new String[] {"1", "1.5", "2"}));
		comboBoxStopBits.setBounds(121, 164, 149, 27);
		panelSettings.add(comboBoxStopBits);
		comboBoxStopBits.setSelectedItem("1");

		JLabel lblComPorts = new JLabel("COM port");
		lblComPorts.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblComPorts.setBounds(28, 24, 95, 16);
		panelSettings.add(lblComPorts);

		JLabel lblBaudRate = new JLabel("Baud rate");
		lblBaudRate.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblBaudRate.setBounds(28, 70, 95, 16);
		panelSettings.add(lblBaudRate);

		JLabel lblDataBits = new JLabel("Data bits");
		lblDataBits.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDataBits.setBounds(28, 117, 61, 16);
		panelSettings.add(lblDataBits);

		JLabel lblStopBits = new JLabel("Stop Bits");
		lblStopBits.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblStopBits.setBounds(27, 168, 95, 16);
		panelSettings.add(lblStopBits);

		JProgressBar progressBarSerial = new JProgressBar();
		progressBarSerial.setValue(100);
		progressBarSerial.setBounds(121, 262, 137, 20);
		panelSettings.add(progressBarSerial);
		progressBarSerial.setValue(0);

		JLabel lblComStatus = new JLabel("COM Status");
		lblComStatus.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblComStatus.setBounds(27, 266, 82, 16);
		panelSettings.add(lblComStatus);

		JButton btnCharts = new JButton("Charts");
		btnCharts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				GraphFrame.createAndShowGUI();
				ChartFrame.createAndShowGUI();
			}
		});
		btnCharts.setBounds(16, 303, 117, 29);
		panelSettings.add(btnCharts);

		JLabel lblParity = new JLabel("Parity Bits");
		lblParity.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblParity.setBounds(28, 217, 95, 16);
		panelSettings.add(lblParity);

		JComboBox comboBoxParity = new JComboBox();
		comboBoxParity.setModel(new DefaultComboBoxModel(new String[] {"NO_PARITY", "EVEN_PARITY", "ODD-PARITY", "MARK_PARITY", "SPACE_PARITY"}));
		comboBoxParity.setBounds(122, 213, 149, 27);
		panelSettings.add(comboBoxParity);
		comboBoxParity.setSelectedItem("NO_PARITY");

		JPanel panelSend = new JPanel();
		panelSend.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelSend.setBounds(352, 34, 400, 191);
		panelTop.add(panelSend);
		panelSend.setLayout(null);

		JTextArea textAreaSendData = new JTextArea();
		textAreaSendData.setBounds(6, 48, 388, 137);
		panelSend.add(textAreaSendData);

		textFieldSendData = new JTextField();
		textFieldSendData.setBounds(6, 13, 259, 26);
		panelSend.add(textFieldSendData);
		textFieldSendData.setColumns(10);

		JButton btnSendData = new JButton("Send");
		btnSendData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dataToSend = "";
				dataToSend = textFieldSendData.getText();
				try (OutputStream out = chosenPort.getOutputStream()) {
					out.write(dataToSend.getBytes());
				} catch (IOException e5) {
					JOptionPane.showMessageDialog(btnSendData, e);
				}
			}
		});
		btnSendData.setBounds(277, 10, 117, 29);
		panelSend.add(btnSendData);
		btnSendData.setEnabled(false);

		JPanel panelReceive = new JPanel();
		panelReceive.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Received data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelReceive.setBounds(352, 237, 400, 191);
		panelTop.add(panelReceive);
		panelReceive.setLayout(null);

		JTextArea textAreaReceiveData = new JTextArea();
		textAreaReceiveData.setEditable(false);
		textAreaReceiveData.setBounds(6, 21, 388, 164);
		panelReceive.add(textAreaReceiveData);

		JButton btnClose = new JButton("CLOSE");
		btnClose.setEnabled(false);

		JButton btnOpen = new JButton("OPEN");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (btnOpen.getText().equals("OPEN")) {
						SerialPort[] portList = SerialPort.getCommPorts();
						chosenPort = portList[comboBoxComPorts.getSelectedIndex()];
						SerialPortUtil.openPort(chosenPort,
								Integer.parseInt(Objects.requireNonNull(comboBoxBaudRate.getSelectedItem()).toString()),
								Integer.parseInt(Objects.requireNonNull(comboBoxDataBits.getSelectedItem()).toString()),
								Integer.parseInt(Objects.requireNonNull(comboBoxStopBits.getSelectedItem()).toString()),
								comboBoxParity.getSelectedIndex());

						if (chosenPort.isOpen()) {
							//JOptionPane.showMessageDialog(btnOpen, chosenPort.getDescriptivePortName() + " --- Port is OPEN.");
							comboBoxComPorts.setEnabled(false);
							btnOpen.setEnabled(false);
							btnClose.setEnabled(true);
							btnCharts.setEnabled(true);
							btnSendData.setEnabled(true);
							progressBarSerial.setValue(100);
							btnCharts.setEnabled(true);
							ComPortListener listener = new ComPortListener();
							chosenPort.addDataListener(listener);
						}
					} else {
						JOptionPane.showMessageDialog(btnOpen, chosenPort.getDescriptivePortName() + " -- FAILED to open.");
					}
				} catch (IndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(btnOpen, "Please chose COM port.", "ERROR", getDefaultCloseOperation());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(btnOpen, e2, "ERROR", getDefaultCloseOperation());
				}
			}
		});
		btnOpen.setBounds(151, 303, 117, 29);
		panelSettings.add(btnOpen);
		btnOpen.setEnabled(true);

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chosenPort.isOpen()) {
						chosenPort.closePort();
						comboBoxComPorts.setEnabled(true);
						btnClose.setEnabled(false);
						btnOpen.setEnabled(true);
						progressBarSerial.setValue(0);
						System.out.println("Port is CLOSED!");
					}
				} catch (Exception e3) {

				}
			}
		});
		btnClose.setBounds(154, 348, 117, 29);
		panelSettings.add(btnClose);
	}
}
