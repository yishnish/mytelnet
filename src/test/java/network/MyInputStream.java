package network;

import java.io.IOException;
import java.io.InputStream;

class MyInputStream extends InputStream {

    private byte[] myMessage;

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage.getBytes();
    }

    @Override
    public int read() throws IOException {
        if(myMessage != null) {
            if(myMessage.length == 0) {
                return -1;
            }
            char result = (char) myMessage[0];
            if(myMessage.length == 1) {
                myMessage = new byte[0];
            } else {
                leftShiftMyMessage();
            }
            return result;
        }
        return 0;
    }

    private void leftShiftMyMessage() {
        byte[] partialMessage = new byte[myMessage.length - 1];
        System.arraycopy(myMessage, 1, partialMessage, 0, myMessage.length - 1);
        myMessage = partialMessage;
    }
}
