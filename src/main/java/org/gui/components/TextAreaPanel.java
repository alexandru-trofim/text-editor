package org.gui.components;

import org.gui.components.cursor.CustomCursor;
import org.gui.listeners.TextEditorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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
                    .getResourceAsStream("/SpaceMono-Regular.ttf");
            currentFont = Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(stream)).deriveFont(EditorConfig.FONT_SIZE);
            setFont(currentFont);
            //This if for scale
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            g.setFont(currentFont);

            // Obtain FontMetrics
            FontMetrics metrics = g.getFontMetrics();

            // Calculate total height and individual character width
            int textHeight = metrics.getAscent() + metrics.getDescent(); // Omit leading unless designing for text paragraphs
            int charWidth = metrics.charWidth('H'); // Example character

            // Output metrics
            System.out.println("Text Height: " + textHeight + " pixels");
            System.out.println("Width of 'H': " + charWidth + " pixels");
            System.out.println("Width of cursor: " + EditorConfig.CURSOR_WIDTH);
            System.out.println("Height of cursor: " + EditorConfig.CURSOR_HEIGHT);

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
        cursor.paintCursor(g);
        textEngine.paintText(g);
    }
}













