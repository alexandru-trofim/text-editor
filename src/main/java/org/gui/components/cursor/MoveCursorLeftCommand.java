package org.gui.components.cursor;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

public class MoveCursorLeftCommand implements CursorCommand{
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        // This won't be the case anynmore
//        if (!editingText && j == 0 && dir == CursorDirection.LEFT)
//            return;
        CustomCursor cursor = panel.getCustomCursor();

        //Move cursor logically
        cursor.setJ(cursor.getJ() - 1);
        //Move cursor on screen
        cursor.setX(cursor.getX() - EditorConfig.CURSOR_WIDTH);
    }
}
