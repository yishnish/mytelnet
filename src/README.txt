Hi!

Use a MyTelnetNegotiator along with a TelnetConnection and a VTerminal to send and display messages to and from
a telnet server.

Sometimes a telnet server seems to switch coordinate modes from 1-based to 0-based. If that happens you can tell
the MyTelnetNegotiator to setMode to TerminalMode.ONES_BASED (and vice-versa if that's something you're into).

VTerminal stores the current state of the telnet screen in a buffer array. You can see the screen by calling
getScreenText() on your terminal. You can access the screen buffer if you want to do that.