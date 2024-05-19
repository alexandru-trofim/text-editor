package org.gui.components.cursor;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomCursor {

    private int x;
    private int y;
    private int i;
    private int j;
    private Color color;
    private int alpha = 255;
    private int increment = EditorConfig.CURSOR_BLINK_STEP;
    private final int cursorWidth = EditorConfig.CURSOR_WIDTH; // Example: cursor width
    private final int  cursorHeight = EditorConfig.CURSOR_HEIGHT; // Example: cursor height

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getCursorWidth() {
        return cursorWidth;
    }

    public int getCursorHeight() {
        return cursorHeight;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public CustomCursor(int x, int y) {
        //Initialize cursor on default position
        this.x = EditorConfig.PADDING_LEFT;
        this.y = EditorConfig.PADDING_UP;
        this.i = 0;
        this.j = 0;
        this.color = new Color(0, 0, 0, alpha);
    }

    //TODO: This one shold be in the text engine
    private int getLastCharPos(TextAreaPanel panel, int lineIndex) {
        int count = j;
        List<char[]> text = panel.getTextEngine().getText();
        while(text.get(lineIndex)[count] != '\0') {
            count++;
        }
        return count;
    }

    /**
     * Finds on what character position on the line should we move
     * When going from a line to another
     * @param panel Current panel we're editing text
     * @param currLine The current line index
     * @param newLine The new line index
     * @return Returns the column position on the new line
     */
    private int getNewLineColumnPos(TextAreaPanel panel, int currLine, int newLine) {
        int currLinePos = j;
        int newLinePos = 0;
        List<char[]> text = panel.getTextEngine().getText();

        while(text.get(newLine)[newLinePos] != '\0') {
            newLinePos++;
        }

        // We put here - in front of the return because we want to go to the left
        if (newLinePos >= currLinePos) return 0;
        else return -(currLinePos - newLinePos);
    }
    public void moveCursor(TextAreaPanel panel, CursorCommand command, boolean editingText) {
        panel.repaint(x, y, cursorWidth + 1, cursorHeight + 1);
        command.execute(panel, editingText);
        panel.repaint(x, y, cursorWidth + 1, cursorHeight + 1);
    }

    public Timer enableCursorBlinking(TextAreaPanel panel) {
        return new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += increment;
                if (alpha >= 200) {
                    alpha = 200;
                    increment = -increment;
                }
                if (alpha <= 0) {
                    alpha = 0;
                    increment = -increment;
                }
                color = new Color(0, 0, 0, alpha);
                // Calculate the rectangle that encompasses the cursor
                // Repaint only the area occupied by the cursor
                panel.repaint(x, y, cursorWidth, cursorHeight);
            }
        });
    }

    public void paintCursor(Graphics g) {
        Color previousColor = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, cursorWidth, cursorHeight);
        g.setColor(previousColor);
    }
}
















