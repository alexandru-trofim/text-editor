package org.gui.components;

import org.gui.components.cursor.*;

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


    public TextEngine(TextAreaPanel panel) {
        text = new ArrayList<>();

        // Adding the first line
        text.add(new char[100]);
        Arrays.fill(text.getFirst(), '\0');

        this.panel = panel;
        this.cursor = panel.getCustomCursor();
    }

    public List<char[]> getText() {
        return text;
    }

    public void moveCursor(CursorCommand command, boolean editingText) {

        panel.repaint(cursor.getX(),
                      cursor.getY(),
                cursor.getCursorWidth() + 1,
                cursor.getCursorHeight() + 1);
        command.execute(panel, editingText);
        panel.repaint(cursor.getX(),
                cursor.getY(),
                cursor.getCursorWidth() + 1,
                cursor.getCursorHeight() + 1);
    }

    public void printChar(char c) {
        insertCharToArray(text, c, cursor.getJ());
        moveCursor(new MoveCursorRightCommand(), true);
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
        if (cursor.getJ() == 0 && cursor.getI() == 0) {
            return;
        }
        if (cursor.getJ() == 0 && cursor.getI() != 0) {
            // Here we have to concatenate the current string
            // with the previous string and to delete the current string after that
            // Also I have to remember the lastCharPos of the previous line before
            // concatenating it
            int cursorNewJPos = getLastCharPos(cursor.getI() - 1);
            System.out.println("cursorNewJpos: " + cursorNewJPos);
            System.out.println("currentLine: " + cursor.getI());
            System.out.println("newLine: " + (cursor.getI() - 1));
            concatenateWithPrevLine(cursor.getI());
            moveCursor(new MoveCursorToPosCommand(cursor.getI() - 1, cursorNewJPos), true);
            System.out.println("After moving");
            System.out.println("cursor new j " + cursor.getJ());
            System.out.println("cursor new i " + cursor.getI());
            return;
        }
        removeCharFromArray(text.get(cursor.getI()), cursor.getJ() - 1);
        moveCursor(new MoveCursorLeftCommand(), true);
    }

    private int getYLinePos(int line) {
        int currLinePadding = (EditorConfig.LINE_SPACING + EditorConfig.CURSOR_HEIGHT) * line;
        int textCursorDisplacement = EditorConfig.CURSOR_HEIGHT - EditorConfig.CURSOR_DISPLACEMENT;
        return EditorConfig.PADDING_UP + currLinePadding + textCursorDisplacement;
    }
    public int getLastCharPos(int lineIndex) {
        int count = 0;
        while(text.get(lineIndex)[count] != '\0') {
            count++;
        }
        return count;
    }

    public void breakLine(int column, int lineIndex) {
       //We have to split the text on the given line before and after
        char[] currLine = text.get(lineIndex);
        char[] secondPart = Arrays.copyOfRange(currLine, column, currLine.length);
        Arrays.fill(currLine, column, currLine.length, '\0');
        text.add(lineIndex + 1, secondPart);
    }
    public void concatenateWithPrevLine(int lineIndex) {
        // When we're pressing backspace on the first character we
        // have to concatenate the current line with the previous line
        // and delete the current line
        char[] currLine = text.get(lineIndex);
        char[] prevLine = text.get(lineIndex - 1);
        char[] newLine = new char[currLine.length + prevLine.length];
        int currLineLen = getLastCharPos(lineIndex);
        int prevLineLen = getLastCharPos(lineIndex - 1);
        //Concat the two lines
        System.out.println("currLine length: " + currLineLen);
        System.out.println("prevLine length: " + prevLineLen);
        System.arraycopy(prevLine, 0, newLine, 0, prevLineLen);
        System.arraycopy(currLine, 0, newLine, prevLineLen, currLineLen);
        text.set(lineIndex - 1, newLine);
        text.remove(lineIndex);
        System.out.println("new line is " + Arrays.toString(text.get(lineIndex - 1)));

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
