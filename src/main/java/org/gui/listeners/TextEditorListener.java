package org.gui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.gui.components.TextAreaPanel;
import org.gui.components.cursor.CursorCommand;
import org.gui.components.cursor.CursorNewLineCommand;
import org.gui.components.cursor.CustomCursor;
import org.gui.components.cursor.MoveCursorDownCommand;
import org.gui.components.cursor.MoveCursorLeftCommand;
import org.gui.components.cursor.MoveCursorRightCommand;
import org.gui.components.cursor.MoveCursorUpCommand;

public class TextEditorListener extends KeyAdapter {

    CustomCursor cursor;
    TextAreaPanel panel;
    private Map<Integer, CursorCommand> keyMap;

    public TextEditorListener(TextAreaPanel panel) {
        this.panel = panel;
        this.cursor = panel.getCustomCursor();
        keyMap = new HashMap<>();

        keyMap.put(KeyEvent.VK_RIGHT, new MoveCursorRightCommand());
        keyMap.put(KeyEvent.VK_LEFT, new MoveCursorLeftCommand());
        keyMap.put(KeyEvent.VK_DOWN, new MoveCursorDownCommand());
        keyMap.put(KeyEvent.VK_UP, new MoveCursorUpCommand());
        keyMap.put(KeyEvent.VK_ENTER, new CursorNewLineCommand());

    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
			int keyCode = keyEvent.getKeyCode();
			char character = keyEvent.getKeyChar();

			if (keyMap.containsKey(keyCode)) {
				panel.getTextEngine().moveCursor(keyMap.get(keyCode), false);
				keyEvent.consume();
			} else if (Character.isLetterOrDigit(keyCode) || keyCode == ' ') {
				panel.getTextEngine().printChar(character);
			} else if (keyCode == KeyEvent.VK_BACK_SPACE) {
				panel.getTextEngine().deleteCharacter();
		
			}
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
