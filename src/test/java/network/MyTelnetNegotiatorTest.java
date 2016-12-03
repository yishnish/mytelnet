package network;

import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;
import org.mockito.Mockito;
import terminal.Vermont;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MyTelnetNegotiatorTest {

    private TelnetClient telnetClient = mock(TelnetClient.class);

    @Test
    public void testWritesToTerminalWhenStreamReceivesData() throws IOException, InterruptedException {
        Vermont vermont = new Vermont();
        MyInputStream inputStream = new MyInputStream();
        Mockito.when(telnetClient.getInputStream()).thenReturn(inputStream);
        MyTelnetNegotiator telnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient);
        telnetNegotiator.start();
        inputStream.setMyMessage("hi");

        waitToMakeAssertion();

        String screenText = vermont.getScreenText();
        assertThat(screenText, containsString("hi"));
    }

    private void waitToMakeAssertion() throws InterruptedException {
        Thread.sleep(100);
    }
}
