package network;

import command.TerminalCommandCreator;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;
import org.mockito.Mockito;
import terminal.Ascii;
import terminal.BlankDisplay;
import terminal.Vermont;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MyTelnetNegotiatorTest {

    private TelnetClient telnetClient = mock(TelnetClient.class);

    @Test
    public void testWritesToTerminalWhenStreamReceivesData() throws IOException, InterruptedException {
        Vermont vermont = new Vermont(new BlankDisplay());
        MyInputStream inputStream = new MyInputStream();
        Mockito.when(telnetClient.getInputStream()).thenReturn(inputStream);
        MyTelnetNegotiator telnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient, new TerminalCommandCreator());
        telnetNegotiator.connect("a URL");
        char[] message = new char[]{Ascii.ESC, '[', ';', 'H', 'h', Ascii.ESC, '[', '0', ';', '1', 'H', 'i'};
        inputStream.setMyMessage(new String(message));

        waitToMakeAssertion();

        String screenText = vermont.getBufferAsString();
        assertThat(screenText, containsString("hi"));
    }

    @Test
    public void testSendingMessageWithoutNewlineAtEnd() throws IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Vermont vt = new Vermont(new BlankDisplay());
        Mockito.when(telnetClient.getOutputStream()).thenReturn(outputStream);
        MyInputStream inputStream = new MyInputStream();
        Mockito.when(telnetClient.getInputStream()).thenReturn(inputStream);
        MyTelnetNegotiator telnetNegotiator = new MyTelnetNegotiator(vt, telnetClient, new TerminalCommandCreator());
        telnetNegotiator.connect("a URL");

        waitToMakeAssertion();

        telnetNegotiator.send("hi");

        assertThat(outputStream.toString(), containsString("hi"));
    }

    @Test
    public void testSendingMessageWithNewlineAtEnd() throws IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Vermont vt = new Vermont(new BlankDisplay());
        Mockito.when(telnetClient.getOutputStream()).thenReturn(outputStream);
        MyInputStream inputStream = new MyInputStream();
        Mockito.when(telnetClient.getInputStream()).thenReturn(inputStream);
        MyTelnetNegotiator telnetNegotiator = new MyTelnetNegotiator(vt, telnetClient, new TerminalCommandCreator());
        telnetNegotiator.connect("a URL");

        waitToMakeAssertion();

        telnetNegotiator.sendLine("hi");

        assertThat(outputStream.toString(), containsString("hi\n"));
    }

    private void waitToMakeAssertion() throws InterruptedException {
        Thread.sleep(100);
    }
}
