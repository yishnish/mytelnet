package network;

import org.apache.commons.net.telnet.TelnetClient;
import terminal.CursorPosition;
import terminal.Vermont;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MyTelnetNegotiator {

    private Vermont vermont;
    private TelnetClient telnetClient;

    public MyTelnetNegotiator(Vermont vermont, TelnetClient telnetClient) {
        this.vermont = vermont;
        this.telnetClient = telnetClient;
    }

    public void start() {
        Thread negotiatingThread = new Thread(new Runnable() {
            public void run() {
                vermont.moveCursor(new CursorPosition(0, 0));
                InputStream inputStream = telnetClient.getInputStream();
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
}
