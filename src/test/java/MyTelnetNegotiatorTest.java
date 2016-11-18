import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.*;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MyTelnetNegotiatorTest {

    private TelnetClient telnetClient = mock(TelnetClient.class);

    @Test
    public void testWritesToTerminalWhenStreamHasData() throws IOException {
        Vermont vermont = new Vermont();
        MyInputStream inputStream = new MyInputStream();
        inputStream.setMyMessage("hi");
        Mockito.when(telnetClient.getInputStream()).thenReturn(inputStream);
        MyTelnetNegotiator telnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient);
        telnetNegotiator.start();

        String screenText = vermont.getScreenText();
        assertThat(screenText, containsString("hi"));
    }

    private class MyTelnetNegotiator {


        private Vermont vermont;
        private TelnetClient telnetClient;

        public MyTelnetNegotiator(Vermont vermont, TelnetClient telnetClient) {
            this.vermont = vermont;
            this.telnetClient = telnetClient;
        }

        public void start() {
            this.vermont.moveCursor(new CursorPosition(0, 0));
            Reader reader = new InputStreamReader(this.telnetClient.getInputStream());
            int character;
            try {
                while ((character = reader.read()) > -1) {
                    this.vermont.write(String.valueOf((char) character));
                    this.vermont.moveCursor(new CursorPosition(0, this.vermont.getCursorPosition().getCol() + 1));
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
    }

    class MyInputStream extends InputStream {

        private byte[] myMessage;

        public void setMyMessage(String myMessage) {
            this.myMessage = myMessage.getBytes();
        }

        @Override
        public int read() throws IOException {
            if(myMessage.length == 0) {
                return -1;
            }
            char result = (char) myMessage[0];
            if(myMessage.length == 1) {
                myMessage = new byte[0];
            }else{
                leftShiftMyMessage();
            }
            return result;
        }

        private void leftShiftMyMessage() {
            byte[] partialMessage = new byte[myMessage.length - 1];
            System.arraycopy(myMessage, 1, partialMessage, 0, myMessage.length - 1);
            myMessage = partialMessage;
        }
    }
}
