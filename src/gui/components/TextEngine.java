package gui.components;

import java.awt.*;
import java.util.Arrays;

public class TextEngine {
    private char[][] text;
    private TextAreaPanel panel;
    private CustomCursor cursor;

    private int nrOfLines;

    public TextEngine(TextAreaPanel panel) {
        //Prealloc the space for the text
        text = new char[100][100];
        nrOfLines = 1;

        for (char[] line : text) {
            Arrays.fill(line, '\0');
        }

        this.panel = panel;
        this.cursor = panel.getCustomCursor();
    }

    public char[][] getText() {
        return text;
    }
    public int getNrOfLines() {
        return nrOfLines;
    }

    public void incNrOfLines() {
        nrOfLines++;
    }

    public void decNrOfLines() {
        nrOfLines--;
    }

    public void printChar(char c) {
        insertCharToArray(text, c, cursor.getJ());
        cursor.moveCursor(panel, CursorDirection.RIGHT, true);
    }

    private void removeCharFromArray(char[] arr, int index) {
        for (int i = index; i < arr.length - 1; ++i) {
            arr[i] = arr[i + 1];
        }
    }

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
    }

    private int getYLinePos(int line) {
        int currLinePadding = (EditorConfig.LINE_SPACING + EditorConfig.CURSOR_HEIGHT) * line;
        int textCursorDisplacement = EditorConfig.CURSOR_HEIGHT - EditorConfig.CURSOR_DISPLACEMENT;
        return EditorConfig.PADDING_UP + currLinePadding + textCursorDisplacement;
    }
    //TODO: Here we should pring the "NrOfLines" lines
    public void paintText(Graphics g) {
        for (int i = 0; i < nrOfLines; ++i) {
            char[] line = text[i];
            if (line[0] == '\0') {
                continue;
            }

            int yLinePos = getYLinePos(i);
            g.drawChars(line, 0, line.length, EditorConfig.PADDING_LEFT, yLinePos);
            panel.repaint(EditorConfig.PADDING_LEFT, yLinePos, line.length * EditorConfig.CURSOR_WIDTH,
                                                                                        EditorConfig.CURSOR_HEIGHT);
        }
    }

}
