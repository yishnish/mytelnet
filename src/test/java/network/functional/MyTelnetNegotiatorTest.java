package network.functional;

import command.TerminalCommandCreator;
import network.MyTelnetNegotiator;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;
import terminal.BlankDisplay;
import terminal.Vermont;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyTelnetNegotiatorTest {

    private static final String LOCALHOST = "localhost";
    private static final String USERNAME = "vagrant";
    private static final String PASSWORD = "vagrant";

    @Test
    public void testConnectingToLocalServer() throws IOException, InterruptedException {
        TelnetClient telnetClient = new TelnetClient();
        Vermont vermont = new Vermont(new BlankDisplay());
        MyTelnetNegotiator myTelnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient, new TerminalCommandCreator());
        myTelnetNegotiator.connect("localhost");
        Thread.sleep(100);
        assertThat("Test connection to telnet server failed. Do you have a server running to connect to?", vermont.getBufferAsString(), containsString("login:"));
    }

    @Test
    public void testSendingMessagesToLocalServer() throws IOException, InterruptedException {
        TelnetClient telnetClient = new TelnetClient();
        Vermont vermont = new Vermont(new BlankDisplay());
        MyTelnetNegotiator myTelnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient, new TerminalCommandCreator());
        myTelnetNegotiator.connect(LOCALHOST);
        Thread.sleep(100);
        myTelnetNegotiator.sendLine(USERNAME);
        Thread.sleep(100);
        myTelnetNegotiator.sendLine(PASSWORD);
        Thread.sleep(500);
        assertThat("Test connection to telnet server failed. Do you have a server running to connect to?", vermont.getBufferAsString(), containsString("vagrant@vagrant:"));
    }

}
