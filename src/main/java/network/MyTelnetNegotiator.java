package network;

import command.TerminalCommandCreator;
import org.apache.commons.net.telnet.TelnetClient;
import terminal.CursorPosition;
import terminal.VTerminal;

import java.io.*;

public class MyTelnetNegotiator {

    private VTerminal terminal;
    private TelnetClient telnetClient;
    private InputStream inputStream;
    private OutputStream outputStream;
    private TerminalCommandCreator commandCreator;

    public MyTelnetNegotiator(VTerminal terminal, TelnetClient telnetClient, TerminalCommandCreator commandCreator) {
        this.terminal = terminal;
        this.telnetClient = telnetClient;
        this.commandCreator = commandCreator;
    }

    public void connect(String host) throws IOException {
        telnetClient.connect(host);
        start();
    }

    public void start() {
        Thread negotiatingThread = new Thread(new Runnable() {
            public void run() {
                terminal.moveCursor(new CursorPosition(0, 0));
                inputStream = telnetClient.getInputStream();
                outputStream = telnetClient.getOutputStream();
                Reader reader = new InputStreamReader(inputStream);
                int character;
                try {
                    while ((character = reader.read()) > -1) {
//                        System.out.println((char)character);
                        terminal.accept(commandCreator.create((char) character));
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

    public void sendLine(String text) throws IOException {
        outputStream.write(text.getBytes());
        outputStream.write("\n".getBytes());
        outputStream.flush();
    }

    public void send(String text) throws IOException {
        outputStream.write(text.getBytes());
        outputStream.flush();
    }

}
