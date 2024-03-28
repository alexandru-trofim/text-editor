package components;

import gui.listeners.TextEditorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class TextAreaPanel extends JPanel {

    private Font currentFont;
    private CustomCursor cursor;
    private Timer cursorBlinkTimer;
    private TextEngine textEngine;

    public CustomCursor getCustomCursor() {
        return cursor;
    }
    public TextAreaPanel() {
        cursor = new CustomCursor(0, 0);
        textEngine = new TextEngine(this);
        cursorBlinkTimer = cursor.enableCursorBlinking(this);

        setBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY));
        setFocusable(true);
        requestFocusInWindow();

        cursorBlinkTimer.start();
        setCustomFont();

        addKeyListener(new TextEditorListener(this));
    }

    public TextEngine getTextEngine() {
        return textEngine;
    }

    private void setCustomFont() {
        try {
            InputStream stream = getClass()
                    .getResourceAsStream("/resources/SpaceMono-Regular.ttf");

            currentFont = Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(stream)).deriveFont(EditorConfig.FONT_SIZE);
            setFont(currentFont);

        } catch (IOException | FontFormatException | NullPointerException e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading font: " + e.getMessage(),
                                                                "Font Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        textEngine.paintText(g);
        cursor.paintCursor(g);
    }
}













