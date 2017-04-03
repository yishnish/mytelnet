Hi!

Use a MyTelnetNegotiator along with a TelnetConnection and a VTerminal to send and display messages to and from
a telnet server.

Sometimes a telnet server seems to switch coordinate modes from 1-based to 0-based. If that happens you can tell
the MyTelnetNegotiator to setMode to TerminalMode.ONES_BASED (and vice-versa if that's something you're into).

VTerminal stores the current state of the telnet screen in a buffer array. You can see the screen by calling
getScreenText() on your terminal. You can access the screen buffer if you want to do that.

A couple of the tests, for better or worse, expect there to be a running telnet server on localhost. You made need
to install and start a telnet server if those fail.


For example, connecting to a local telnet server and playing some nethack:

import command.TerminalCommandCreator;
import network.MyTelnetNegotiator;
import org.apache.commons.net.telnet.TelnetClient;
import terminal.Ascii;
import terminal.PrintStreamDisplay;
import terminal.TerminalMode;
import terminal.Vermont;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        TelnetClient telnetClient = new TelnetClient();
        PrintStreamDisplay display = new PrintStreamDisplay(System.out);
        Vermont vermont = new Vermont(display);

        TerminalCommandCreator commandCreator = new TerminalCommandCreator();
        commandCreator.setMode(TerminalMode.ONES_BASED);

        MyTelnetNegotiator myTelnetNegotiator = new MyTelnetNegotiator(vermont, telnetClient, commandCreator);

        myTelnetNegotiator.connect(somewhere);
        Thread.sleep(100);
        myTelnetNegotiator.sendLine(user);
        Thread.sleep(100);
        myTelnetNegotiator.sendLine(password);
        Thread.sleep(100);
        myTelnetNegotiator.sendLine("nethack");
        Thread.sleep(100);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String text = reader.readLine();
            myTelnetNegotiator.send(text);
        }
    }
}
