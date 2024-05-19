package org.gui.components.cursor;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

public class MoveCursorRightCommand implements CursorCommand {

    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        //This will still be the case
//        if (!editingText && j == getLastCharPos(panel, i) && dir == CursorDirection.RIGHT)
//            return;
        CustomCursor cursor = panel.getCustomCursor();

        // Move cursor logically
        cursor.setJ(cursor.getJ() + 1);

        // Move cursor on screen
        cursor.setX(cursor.getX() + EditorConfig.CURSOR_WIDTH);
    }
}
