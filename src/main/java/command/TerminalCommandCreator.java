package command;

public class TerminalCommandCreator {

    private char input;

    public void write(char c) {
        input = c;
    }

    public TerminalCommand createCommand() {
        return new CharacterWriteCommand(input);
    }
}
