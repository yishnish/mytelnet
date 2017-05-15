package network;

import command.TerminalCommandCreator;
import org.apache.commons.net.telnet.TelnetClient;
import locations.Coordinates;
import terminal.TerminalMode;
import terminal.VTerminal;

import java.io.*;

public class MyTelnetNegotiator {

    private VTerminal terminal;
    private TelnetClient telnetClient;
    private TerminalCommandCreator commandCreator;

    public MyTelnetNegotiator(VTerminal terminal, TelnetClient telnetClient) {
        this.terminal = terminal;
        this.telnetClient = telnetClient;
        this.commandCreator = new TerminalCommandCreator();
    }

    public void connect(String host) throws IOException {
        telnetClient.connect(host);
        start();
    }

    private void start() {
        Thread negotiatingThread = new Thread(new Runnable() {
            public void run() {
                terminal.moveCursor(new Coordinates(0, 0));
                Reader reader = new InputStreamReader(telnetClient.getInputStream());
                int character;
                try {
                    while ((character = reader.read()) > -1) {
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
        OutputStream outputStream = telnetClient.getOutputStream();
        outputStream.write(text.getBytes());
        outputStream.write("\n".getBytes());
        outputStream.flush();
    }

    public void send(String text) throws IOException {
        OutputStream outputStream = telnetClient.getOutputStream();
        outputStream.write(text.getBytes());
        outputStream.flush();
    }

    public void send(char character) throws IOException {
        OutputStream outputStream = telnetClient.getOutputStream();
        outputStream.write(character);
    }

    public void setMode(TerminalMode terminalMode) {
        commandCreator.setMode(terminalMode);
    }
}
