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
                        //this fails as soon as more than 80 characters are written since vt100 codes aren't used yet
                        //to reposition the cursor
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

    public void send(String text) throws IOException {
        outputStream.write(text.getBytes());
        outputStream.write("\n".getBytes());
        outputStream.flush();
    }

    public String getScreenText() {
        return vermont.getScreenText();
    }
}
