package org.gui.components.cursor;

import org.gui.components.TextAreaPanel;

public class CursorNewLineCommand implements CursorCommand {
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        panel.getTextEngine().breakLine(cursor.getJ(), cursor.getI());

        //Move the cursor logically
        cursor.setJ(0);
        cursor.setI(cursor.getI() + 1);
        //TODO: Here we should create a formula for the cursor position based
        // on the current (i, j) coordinates something like updateCursorPosOnScreen

        //Move the cursor on screen
//        cursor.setY(cursor.getY() + EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING);
//        cursor.setX(EditorConfig.PADDING_LEFT);
        cursor.updateCursorPhysicalPos();

    }
}
