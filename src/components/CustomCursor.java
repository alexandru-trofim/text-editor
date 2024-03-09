package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomCursor {
    public int x;
    public int y;
    public int i;
    public int j;
    public Color color;
    public int alpha = 255;
    public int increment = EditorConfig.CURSOR_BLINK_STEP;
    public int cursorWidth = EditorConfig.CURSOR_WIDTH; // Example: cursor width
    public int  cursorHeight = EditorConfig.CURSOR_HEIGHT; // Example: cursor height


    public CustomCursor(int x, int y) {
        //Initialize cursor on default position
        this.x = EditorConfig.PADDING_LEFT;
        this.y = EditorConfig.PADDING_UP;
        this.i = 0;
        this.j = 0;
        this.color = new Color(0, 0, 0, alpha);
        // line in matrix =
    }

    public void moveCursor(JPanel panel, CursorDirection dir) {
        panel.repaint(x, y, cursorWidth + 1, cursorHeight + 1);
        //Changes the coordinates of the square
        if (dir == CursorDirection.RIGHT) {
            x += 10;
            j += 1;
        } else if (dir == CursorDirection.LEFT) {
            x -= 10;
            j -= 1;
        }
        System.out.println("(" + i + ", " + j + ")");
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
















