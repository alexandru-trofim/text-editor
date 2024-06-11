package org.gui.components.cursor;

import org.gui.components.TextAreaPanel;
import org.gui.components.TextEngine;

public class MoveCursorLeftCommand implements CursorCommand{
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        // This won't be the case anynmore
        CustomCursor cursor = panel.getCustomCursor();
        TextEngine textEngine = panel.getTextEngine();

        if (!editingText && cursor.getJ() == 0) {
            if (cursor.getI() == 0)
                return;
            // The case when we are on the first element of the screen
            // and we want to move the cursor to the last character of the previous line
            cursor.setJ(textEngine.getLastCharPos(cursor.getI() - 1));
            cursor.setI(cursor.getI() - 1);
            //Move cursor on screen
            cursor.updateCursorPhysicalPos();
            return;
        }
        //Move cursor logically
        cursor.setJ(cursor.getJ() - 1);
        //Move cursor on physically on the screen
        cursor.updateCursorPhysicalPos();
    }
}
