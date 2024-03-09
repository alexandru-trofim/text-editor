package gui.listeners;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import components.CustomCursor;
import components.TextAreaPanel;

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
            panel.textEngine.printChar(character);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            cursor.moveCursor(panel, components.CursorDirection.RIGHT);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            cursor.moveCursor(panel, components.CursorDirection.LEFT);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
