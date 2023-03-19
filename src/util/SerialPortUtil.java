package util;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Serial Port Utility class, provides methods
 * to establish and manage the serial connection.
 */
public class SerialPortUtil {

	/**
	 * Lists all available Serial COM ports.
	 */
	public static void getCommPorts() {
		SerialPort[] portList = SerialPort.getCommPorts();
		for (SerialPort port : portList) {
			System.out.println(port);
		}
	}
	/**
	 * Opens and initializes the selected Serial port.
	 *
	 * @param chosenPort The port to open.
	 * @param baud	The selected Baud Rate.
	 * @param dataBits The selected new Data Bits.
	 * @param stopBits The selected new Stop Bits.
	 * @param parity The selected Parity mode..
	 */
	public static void openPort(SerialPort chosenPort, int baud, int dataBits, int stopBits, int parity) {
		chosenPort.setComPortParameters(baud, dataBits, stopBits, parity);
		chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		chosenPort.openPort();
		if (!chosenPort.isOpen()) {
			System.out.println("Port FAILED to open!");
		} else {
			System.out.printf("Port %s is OPEN!", chosenPort);
		}
	}
}
