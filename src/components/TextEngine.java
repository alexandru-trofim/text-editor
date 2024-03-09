package components;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class TextEngine {
    Queue<CharPrintInfo> characterQueue;

    public char[][] text;
    private TextAreaPanel panel;
    private CustomCursor cursor;

    public TextEngine(TextAreaPanel panel) {
        //Prealloc the space for the text
        text = new char[100][100];
        this.panel = panel;
        this.cursor = panel.getCustomCursor();
        characterQueue = new ArrayDeque<>();

    }

    public void printChar(char c) {
        // Here we just put the logic of the movement and adding the printing of a new
        // character to the queue
        text[cursor.i][cursor.j] = c;
        CharPrintInfo printInfo = new CharPrintInfo(cursor.i, cursor.j, cursor.x, cursor.y);
        characterQueue.offer(printInfo);
        cursor.moveCursor(panel, CursorDirection.RIGHT);
    }

    public void paintChars(Graphics g) {
        // Here we are painting all the characters from the queue\

        while (!characterQueue.isEmpty()) {
            CharPrintInfo printInfo = characterQueue.poll();
            System.out.println("printing character: " + text[printInfo.i][printInfo.j]);
            g.drawChars(text[printInfo.i], printInfo.j, 1, printInfo.x, printInfo.y);
        }
    }

}
