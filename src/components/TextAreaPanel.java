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
    public TextEngine textEngine;
    public Graphics graphics;

    public CustomCursor getCustomCursor() {
        return cursor;
    }
    public TextAreaPanel() {
        // pos - cursorHeight
        //We don't need the coordinates for now
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


    private void setCustomFont() {
        try {
            InputStream stream = getClass()
                    .getResourceAsStream("/resources/SpaceMono-Regular.ttf");
            currentFont = Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(stream)).deriveFont(16f);
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
    public void paintChars(Graphics g) {
        // Here we are painting all the characters from the queue\
        Queue<CharPrintInfo> characterQueue = textEngine.characterQueue;
        char[][] text = textEngine.text;

        while (!characterQueue.isEmpty()) {
            CharPrintInfo printInfo = characterQueue.poll();
            System.out.println("printing character: " + text[printInfo.i][printInfo.j]);
            g.drawChars(text[printInfo.i], printInfo.j, 1, printInfo.x, printInfo.y);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        graphics = g;
        super.paintComponent(g);
        //Draw components
        //Text reference point is top left , Cursor reference point bottom left
//        g.drawString("Xhis is my custom Panel!", EditorConfig.PADDING_LEFT,
//                                EditorConfig.PADDING_UP + EditorConfig.CURSOR_HEIGHT);

//        paintChars(g);

        Queue<CharPrintInfo> characterQueue = textEngine.characterQueue;
        char[][] text = textEngine.text;
//        for (int i = 0; i < text[0].length; ++i) {
//            g.drawChars(text[0], i, 1, EditorConfig.PADDING_LEFT + 10 * i, EditorConfig.PADDING_UP);
//            repaint(EditorConfig.PADDING_LEFT + 10 * i, EditorConfig.PADDING_UP, EditorConfig.CURSOR_WIDTH, EditorConfig.CURSOR_HEIGHT);
//        }
        //13 Is a magic Number it is because the
        g.drawChars(text[0], 0, text.length, EditorConfig.PADDING_LEFT,  EditorConfig.PADDING_UP + 13);
//        repaint(EditorConfig.PADDING_LEFT, EditorConfig.PADDING_UP - 25, EditorConfig.CURSOR_WIDTH, EditorConfig.CURSOR_HEIGHT + 5);
        cursor.paintCursor(g);
//        while (!characterQueue.isEmpty()) {
//            CharPrintInfo printInfo = characterQueue.poll();
//            System.out.println("printing character: " + text[printInfo.i][printInfo.j] +
//                    " at pos (" + printInfo.x + ", " + printInfo.y + ")");
//            char[] c = new char[]{text[printInfo.i][printInfo.j]};
//            g.drawChars(c, 0, 1, printInfo.x,EditorConfig.PADDING_UP + 13);
////            repaint(printInfo.x, printInfo.y - 20, EditorConfig.CURSOR_WIDTH + 20, EditorConfig.CURSOR_HEIGHT + 20);
//            g.setColor(new Color(0, 0, 0));
//        }
//        g.drawChars(new char[]{'a', 'b', 'c', 'd'}, 0, 4, 50, 50);
//        repaint();
    }
}













