package network;

import org.apache.commons.net.telnet.TelnetClient;
import terminal.CursorPosition;
import terminal.VTerminal;

import java.io.*;

public class MyTelnetNegotiator {

    private VTerminal vermont;
    private TelnetClient telnetClient;
    private InputStream inputStream;
    private OutputStream outputStream;

    public MyTelnetNegotiator(VTerminal terminal, TelnetClient telnetClient) {
        this.vermont = terminal;
        this.telnetClient = telnetClient;
    }

    public void connect(String host) throws IOException {
        telnetClient.connect(host);
        start();
    }

    public void start() {
        Thread negotiatingThread = new Thread(new Runnable() {
            public void run() {
                vermont.moveCursor(new CursorPosition(0, 0));
                inputStream = telnetClient.getInputStream();
                outputStream = telnetClient.getOutputStream();
                Reader reader = new InputStreamReader(inputStream);
                int character;
                try {
                    while ((character = reader.read()) > -1) {
                        vermont.write(String.valueOf((char) character));
                        vermont.moveCursor(new CursorPosition(0, vermont.getCursorPosition().getCol() + 1));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        telnetClient.disconnect();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        negotiatingThread.start();
    }
//
//    public void send(String text) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        Integer c;
//        while (reader.ready()) {
//            c = reader.read();
//            if(c != null) {
//                System.out.println("reader = " + (char) Character.getNumericValue(c));
//            }
//        }
//
//        outputStream.write(text.getBytes());
//        outputStream.write("\n".getBytes());
//        outputStream.flush();
//    }

    public String getScreenText() {
        return vermont.getScreenText();
    }
}
