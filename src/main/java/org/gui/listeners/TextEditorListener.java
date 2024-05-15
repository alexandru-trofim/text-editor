package org.gui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.gui.components.CursorDirection;
import org.gui.components.CustomCursor;
import org.gui.components.TextAreaPanel;

public class TextEditorListener extends KeyAdapter {

    CustomCursor cursor;
    TextAreaPanel panel;
    public TextEditorListener(TextAreaPanel panel) {
        this.panel = panel;
        this.cursor = panel.getCustomCursor();
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        char character = keyEvent.getKeyChar();
        if (Character.isLetterOrDigit(keyCode) || keyCode == ' ') {
            panel.getTextEngine().printChar(character);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            cursor.moveCursor(panel, CursorDirection.RIGHT, false);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            cursor.moveCursor(panel, CursorDirection.LEFT, false);
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            panel.getTextEngine().deleteCharacter();
        } else if (keyCode == KeyEvent.VK_ENTER) {
            cursor.moveCursor(panel, CursorDirection.NEW_LINE, false);
        } else if (keyCode == KeyEvent.VK_UP) {
            cursor.moveCursor(panel, CursorDirection.UP, false);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            cursor.moveCursor(panel, CursorDirection.DOWN, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
