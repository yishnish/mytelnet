package command;

import terminal.VTerminal;

public class CharacterWriteCommand implements TerminalCommand {

    private char character;

    public CharacterWriteCommand(char character) {
        this.character = character;
    }

    public void call(VTerminal terminal) {
        terminal.write(String.valueOf(character));
    }
}
