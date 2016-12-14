package network.functional;

import network.MyTelnetNegotiator;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;
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
        Vermont vermont = new Vermont();
        MyTelnetNegotiator myTelnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient);
        myTelnetNegotiator.connect("localhost");
        Thread.sleep(100);
        assertThat(myTelnetNegotiator.getScreenText(), containsString("login:"));
    }

    @Test
    public void testSendingMessagesToLocalServer() throws IOException, InterruptedException {
        TelnetClient telnetClient = new TelnetClient();
        Vermont vermont = new Vermont();
        MyTelnetNegotiator myTelnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient);
        myTelnetNegotiator.connect(LOCALHOST);
        Thread.sleep(100);
        myTelnetNegotiator.send(USERNAME);
        Thread.sleep(100);
        myTelnetNegotiator.send(PASSWORD);
        Thread.sleep(400);
        assertThat(myTelnetNegotiator.getScreenText(), containsString("vagrant@vagrant:"));
    }
}
