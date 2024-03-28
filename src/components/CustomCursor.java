package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

    private int getLastCharPos(TextAreaPanel panel, int line) {
        int count = j;
        char[][] text = panel.getTextEngine().getText();
        while(text[line][count] != '\0') {
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
        char[][] text = panel.getTextEngine().getText();

        while(text[newLine][newLinePos] != '\0') {
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
            j = 0;
            i += 1;
            y += EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING;
            x = EditorConfig.PADDING_LEFT;

            panel.getTextEngine().incNrOfLines();
        } else if (dir == CursorDirection.DOWN) {
            int offset = getNewLineColumnPos(panel, i, i + 1);
            y += EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING;
            x += offset * EditorConfig.CURSOR_WIDTH;
            i += 1;
            j += offset;
        } else if (dir == CursorDirection.UP)  {
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
















