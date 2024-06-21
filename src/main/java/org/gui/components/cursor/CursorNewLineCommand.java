package org.gui.components.cursor;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;
import org.gui.components.TextEngine;

public class CursorNewLineCommand implements CursorCommand {
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        TextEngine textEngine = panel.getTextEngine();
        textEngine.breakLine(cursor.getJ(), cursor.getI());

        /* Move the cursor logically */
        cursor.setJ(0);
        cursor.setI(cursor.getI() + 1);
        /* Move the cursor on screen */
        cursor.updateCursorPhysicalPos();
        /* Set new preferred size 
         * Get the last line position */
        int lastLineYPos = textEngine.getYLinePos(textEngine.getText().size() - 1);
        /* TODO: Implement and for width */
        panel.updateContentSize(600, lastLineYPos + EditorConfig.PADDING_BOTTOM);

    }
}































