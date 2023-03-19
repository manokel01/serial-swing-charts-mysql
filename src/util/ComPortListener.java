package util;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * This Class implements the SerialPortDataListener Interface
 * and provides methods for listening to the Serial port.
 */
public class ComPortListener implements SerialPortDataListener {
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (getListeningEvents() == 1) {
            FormatDataset.parseCSV();
        }
    }
}
