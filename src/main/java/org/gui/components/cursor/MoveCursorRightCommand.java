package org.gui.components.cursor;

import org.gui.components.TextAreaPanel;
import org.gui.components.TextEngine;

public class MoveCursorRightCommand implements CursorCommand {

    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        TextEngine textEngine = panel.getTextEngine();
        CustomCursor cursor = panel.getCustomCursor();

        if (!editingText && cursor.getJ() == textEngine.getLastCharPos(cursor.getI())) {
            return;
        }
        // Move cursor logically
        cursor.setJ(cursor.getJ() + 1);

        // Move cursor on screen
        cursor.updateCursorPhysicalPos();
    }
}
