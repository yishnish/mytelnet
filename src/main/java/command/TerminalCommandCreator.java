package command;

public class TerminalCommandCreator {

    public TerminalCommand write(char c) {
        return new CharacterWriteCommand(c);
    }
}
