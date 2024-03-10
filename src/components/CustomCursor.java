package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomCursor {
    private int x;
    private int y;
    private int i;
    private int j;
    private Color color;
    private int alpha = 255;
    private int increment = EditorConfig.CURSOR_BLINK_STEP;
    private int cursorWidth = EditorConfig.CURSOR_WIDTH; // Example: cursor width
    private int  cursorHeight = EditorConfig.CURSOR_HEIGHT; // Example: cursor height

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

    private int getLastCharPos(TextAreaPanel panel) {
        int count = j;
        char[][] text = panel.getTextEngine().getText();
        while(text[i][count] != '\0') {
            count++;
        }
        return count;
    }
    public void moveCursor(TextAreaPanel panel, CursorDirection dir, boolean editingText) {
        if (!editingText && j == 0 && dir == CursorDirection.LEFT)
            return;
        if (!editingText && j == getLastCharPos(panel) && dir == CursorDirection.RIGHT)
            return;

        panel.repaint(x, y, cursorWidth + 1, cursorHeight + 1);
        //Changes the coordinates of the square
        if (dir == CursorDirection.RIGHT) {
            x += 10;
            j += 1;
        } else if (dir == CursorDirection.LEFT) {
            x -= 10;
            j -= 1;
        } else if (dir == CursorDirection.NEW_LINE) {
            j = 0;
            i += 1;
            y += EditorConfig.PADDING_UP + 13;
            x = EditorConfig.PADDING_LEFT;
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
        Timer cursorBlinkTimer = new Timer(60, new ActionListener() {
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
        return cursorBlinkTimer;
    }


}
















