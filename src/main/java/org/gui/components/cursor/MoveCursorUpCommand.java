package org.gui.components.cursor;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

public class MoveCursorUpCommand implements CursorCommand{
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        if (cursor.getI() == 0) return;

        int offset = CursorCommand.getNewLineColumnPos(panel, cursor.getI() - 1);
        // Move the cursor logically
        cursor.setI(cursor.getI() - 1);
        cursor.setJ(cursor.getJ() + 1);

        // Move the cursor on screen
        cursor.setY(cursor.getY() - EditorConfig.CURSOR_HEIGHT + EditorConfig.LINE_SPACING);
        cursor.setX(cursor.getX() + offset * EditorConfig.CURSOR_WIDTH);
    }

}
