package org.gui.components.cursor;

import org.gui.components.TextAreaPanel;

public class MoveCursorDownCommand implements CursorCommand {
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        if (panel.getTextEngine().getText().size() - 1 == cursor.getI()) return;

        int offset = CursorCommand.getNewLineColumnPos(panel,cursor.getI() + 1);

        // Move the cursor logically
        cursor.setI(cursor.getI() + 1);
        cursor.setJ(cursor.getJ() + offset);

        //Move the cursor on screen
//        cursor.setY(cursor.getY() + EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING);
//        cursor.setX(cursor.getX() + offset * EditorConfig.CURSOR_WIDTH);
        cursor.updateCursorPhysicalPos();
    }
}
