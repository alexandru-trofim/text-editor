package org.gui.components.cursor;

import java.awt.Rectangle;

import javax.swing.JScrollPane;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;
import org.gui.components.TextEngine;

public class MoveCursorUpCommand implements CursorCommand{
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        TextEngine textEngine = panel.getTextEngine();
        if (cursor.getI() == 0) return;

        int offset = CursorCommand.getNewLineColumnPos(panel, cursor.getI() - 1);
        /* Move the cursor logically */
        cursor.setI(cursor.getI() - 1);
        cursor.setJ(cursor.getJ() + offset);

        /* Move the cursor on screen */
        cursor.updateCursorPhysicalPos();

        /* Scroll Panel if reaching the bottom with the cursor*/
        JScrollPane scrollPane = panel.getScrollPane();
        Rectangle viewRect = scrollPane.getViewport().getViewRect();
	    int currLineYPos = textEngine.getYLinePos(cursor.getI());

        if (currLineYPos < viewRect.y + EditorConfig.SCROLL_OFFSET) {
            panel.scrollByOneLine(Direction.UP, viewRect);
        }
    }

}
