package org.gui.components.cursor;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

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

    public int getCursorWidth() {
        return cursorWidth;
    }

    public int getCursorHeight() {
        return cursorHeight;
    }

    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
    }

    public void setCursorHeight(int cursorHeight) {
        this.cursorHeight = cursorHeight;
    }

    public CustomCursor(int x, int y) {
        //Initialize cursor on default position
        this.x = EditorConfig.PADDING_LEFT;
        this.y = EditorConfig.PADDING_UP;
        this.i = 0;
        this.j = 0;
        this.color = new Color(0, 0, 0, alpha);
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

    private int getYCursorPos(int line) {
        int currLinePadding = (EditorConfig.LINE_SPACING + EditorConfig.CURSOR_HEIGHT) * line;
        return EditorConfig.PADDING_UP + currLinePadding;
    }
    public void updateCursorPhysicalPos() {
        /* We have to calculate the x and y of the cursor
        * based on the EditorConfig, i, j, and size of characters
        */
        y = getYCursorPos(i);
        x = EditorConfig.PADDING_LEFT + cursorWidth * j;
    }

    public void paintCursor(Graphics g) {
        Color previousColor = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, cursorWidth, cursorHeight);
        g.setColor(previousColor);
    }
}
















