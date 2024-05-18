package org.gui.components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.List;

public class TextEngine {
//    private char[][] text;
    private List<char[]> text;
    private TextAreaPanel panel;
    private CustomCursor cursor;

    private int nrOfLines;

    public TextEngine(TextAreaPanel panel) {
        //Prealloc the space for the text
//        text = new char[100][100];
        text = new ArrayList<>();
        nrOfLines = 1;

        // Adding the first line
        text.add(new char[100]);

        for (char[] line : text) {
            Arrays.fill(line, '\0');
        }

        this.panel = panel;
        this.cursor = panel.getCustomCursor();
    }

    public List<char[]> getText() {
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

    private void insertCharToArray(List<char[]> text, char character, int index) {
        char[] currLine = text.get(cursor.getI());
        int lastChar = currLine.length - 1;

        if (currLine[lastChar] != '\0') {
            //double the array size
            text.set(cursor.getI(), doubleArraySize(currLine));
            currLine = text.get(cursor.getI());
        }

        for (int i = currLine.length - 1; i > index; --i) {
            currLine[i] = currLine[i - 1];
        }
        currLine[index] = character;

    }
    public void deleteCharacter() {
        if (cursor.getJ() == 0) return;
        removeCharFromArray(text.get(cursor.getI()), cursor.getJ() - 1);
        cursor.moveCursor(panel, CursorDirection.LEFT, true);
    }

    private int getYLinePos(int line) {
        int currLinePadding = (EditorConfig.LINE_SPACING + EditorConfig.CURSOR_HEIGHT) * line;
        int textCursorDisplacement = EditorConfig.CURSOR_HEIGHT - EditorConfig.CURSOR_DISPLACEMENT;
        return EditorConfig.PADDING_UP + currLinePadding + textCursorDisplacement;
    }

    public void breakLine(int column, int lineIndex) {
       //We have to split the text on the given line before and after
        char[] currLine = text.get(lineIndex);
        char[] secondPart = Arrays.copyOfRange(currLine, column, currLine.length);
        Arrays.fill(currLine, column, currLine.length, '\0');
        System.out.println(secondPart);
        text.add(lineIndex + 1, secondPart);
    }

    public void paintText(Graphics g) {
        for (int i = 0; i < text.size(); ++i) {
            char[] line = text.get(i);
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
