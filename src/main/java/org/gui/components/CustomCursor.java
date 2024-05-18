package org.gui.components;

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
    public void moveCursor(TextAreaPanel panel, CursorDirection dir, boolean editingText) {
        if (!editingText && j == 0 && dir == CursorDirection.LEFT)
            return;
        if (!editingText && j == getLastCharPos(panel, i) && dir == CursorDirection.RIGHT)
            return;

        panel.repaint(x, y, cursorWidth + 1, cursorHeight + 1);
        //Changes the coordinates of the square
        if (dir == CursorDirection.RIGHT) {
            x += EditorConfig.CURSOR_WIDTH;
            j += 1;
        } else if (dir == CursorDirection.LEFT) {
            x -= EditorConfig.CURSOR_WIDTH;
            j -= 1;
        } else if (dir == CursorDirection.NEW_LINE) {
            System.out.println("Current j = " + j + "; i = " + i);
            panel.getTextEngine().breakLine(j, i);
            j = 0;
            i += 1;
            //TODO: Here we should create a formula for the cursor position based
            // on the current (i, j) coordinates
            y += EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING;
            x = EditorConfig.PADDING_LEFT;
            // Here we have to split our current line by the position we're at
            // Then move all the lines a line below\
            // Then put the rest of the current line on the next line
            // So when we're pressing enter, we have the current j
            // we shold take all the text from the j to the end of the line
//            breakLine(int column, int line)
            panel.getTextEngine().incNrOfLines();
        } else if (dir == CursorDirection.DOWN) {
            if (panel.getTextEngine().getNrOfLines() - 1 == i) return;

            int offset = getNewLineColumnPos(panel, i, i + 1);
            y += EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING;
            x += offset * EditorConfig.CURSOR_WIDTH;
            i += 1;
            j += offset;
        } else if (dir == CursorDirection.UP)  {
            if (i == 0) return;

            int offset = getNewLineColumnPos(panel, i, i - 1);
            y -= EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING;
            x += offset * EditorConfig.CURSOR_WIDTH;
            i -= 1;
            j += offset;
        }
        //Now paints just the part of the Panel where the square now is
        panel.repaint(x, y, cursorWidth + 1, cursorHeight + 1);
    }
    public void paintCursor(Graphics g) {
        Color previousColor = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, cursorWidth, cursorHeight);
        g.setColor(previousColor);
    }
    public Timer enableCursorBlinking(TextAreaPanel panel) {
        CustomCursor cursor = panel.getCustomCursor();
        return new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor.alpha += cursor.increment;
                if (cursor.alpha >= 200) {
                    cursor.alpha = 200;
                    cursor.increment = -cursor.increment;
                }
                if (cursor.alpha <= 0) {
                    cursor.alpha = 0;
                    cursor.increment = -cursor.increment;
                }
                cursor.color = new Color(0, 0, 0, cursor.alpha);
                // Calculate the rectangle that encompasses the cursor
                // Repaint only the area occupied by the cursor
                panel.repaint(cursor.x, cursor.y, cursor.cursorWidth, cursor.cursorHeight);
            }
        });
    }
}
















