package network.functional;

import network.MyTelnetNegotiator;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;
import terminal.Vermont;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyTelnetNegotiatorTest {

    @Test
    public void testConnectingToLocalServer() throws IOException, InterruptedException {
        TelnetClient telnetClient = new TelnetClient();
        Vermont vermont = new Vermont();
        MyTelnetNegotiator myTelnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient);
        myTelnetNegotiator.connect("localhost");
        Thread.sleep(100);
        assertThat(myTelnetNegotiator.getScreenText(), containsString("login:"));
    }
}
