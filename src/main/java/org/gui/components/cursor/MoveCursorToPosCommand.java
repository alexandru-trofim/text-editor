package org.gui.components.cursor;

import org.gui.components.TextAreaPanel;

public class MoveCursorToPosCommand implements CursorCommand {

    private final int i;
    private final int j;
    public MoveCursorToPosCommand(int i, int j) {
        this.i = i;
        this.j = j;
    }
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        // This won't be the case anynmore
        CustomCursor cursor = panel.getCustomCursor();
        if (i < 0 || j < 0) {
            System.out.println("Trying to move cursor to an invalid position");
            return;
        }

        //Move cursor logically
        cursor.setI(i);
        cursor.setJ(j);
        //Move cursor on physically on the screen
        cursor.updateCursorPhysicalPos();

    }
}
