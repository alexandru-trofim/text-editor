package components;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class TextEngine {
    private char[][] text;
    private TextAreaPanel panel;
    private CustomCursor cursor;

    public TextEngine(TextAreaPanel panel) {
        //Prealloc the space for the text
        text = new char[100][100];

        for (char[] line : text) {
            Arrays.fill(line, '\0');
        }

        this.panel = panel;
        this.cursor = panel.getCustomCursor();
    }

    public char[][] getText() {
        return text;
    }

    public void printChar(char c) {
//        text[cursor.i][cursor.j] = c;
        insertCharToArray(text, c, cursor.getJ());
        cursor.moveCursor(panel, CursorDirection.RIGHT, true);
        panel.repaint(cursor.getX(), cursor.getY(), (text[cursor.getI()].length - cursor.getJ()) * EditorConfig.CURSOR_WIDTH, EditorConfig.CURSOR_HEIGHT + 1);
    }

    private void removeCharFromArray(char[] arr, int index) {
//        System.out.println(arr.length);
        for (int i = index; i < arr.length - 1; ++i) {
            arr[i] = arr[i + 1];
        }
    }
    // Method to double the size of a char[] array
    private char[] doubleArraySize(char[] arr) {
        char[] newArray = new char[arr.length * 2];
        System.arraycopy(arr, 0, newArray, 0, arr.length);
        Arrays.fill(newArray, arr.length, newArray.length, '\0');
        return newArray;
    }

    private void insertCharToArray(char[][] text, char character, int index) {
        //If we are at max capacity double the arraySize
        //for now do simple insert
        if (text[cursor.getI()][text[cursor.getI()].length - 1] != '\0') {
            //double the array size
            text[cursor.getI()] = doubleArraySize(text[cursor.getI()]);
        }

        char[] line = text[cursor.getI()];

        for (int i = line.length - 1; i > index; --i) {
            line[i] = line[i - 1];
        }
        line[index] = character;

    }
    public void deleteCharacter() {
        if (cursor.getJ() == 0) return;
        removeCharFromArray(text[cursor.getI()], cursor.getJ() - 1);
        cursor.moveCursor(panel, CursorDirection.LEFT, true);
        // Repaint all the characters on the right that were shifted
        panel.repaint(cursor.getX(), cursor.getY(), (text[cursor.getI()].length - cursor.getJ()) * EditorConfig.CURSOR_WIDTH,
                                                                                EditorConfig.CURSOR_HEIGHT + 1);
    }

    public void paintText(Graphics g) {
        for (int i = 0; i < text.length; ++i) {
            char[] line = text[i];
            if (line[0] == '\0') {
                break;
            }
            g.drawChars(line, 0, line.length, EditorConfig.PADDING_LEFT, (EditorConfig.PADDING_UP + 13) * (i + 1));
        }
    }

}
