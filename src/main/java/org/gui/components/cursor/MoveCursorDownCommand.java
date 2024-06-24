package org.gui.components.cursor;

import java.awt.Rectangle;

import javax.swing.JScrollPane;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;
import org.gui.components.TextEngine;

public class MoveCursorDownCommand implements CursorCommand {
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        TextEngine textEngine = panel.getTextEngine();
        if (textEngine.getText().size() - 1 == cursor.getI()) return;

        int offset = CursorCommand.getNewLineColumnPos(panel,cursor.getI() + 1);

        /* Move the cursor logically */
        cursor.setI(cursor.getI() + 1);
        cursor.setJ(cursor.getJ() + offset);

        /* Move the cursor on screen */
        cursor.updateCursorPhysicalPos();

        /* Scroll Panel if reaching the bottom with the cursor*/
        JScrollPane scrollPane = panel.getScrollPane();
        Rectangle viewRect = scrollPane.getViewport().getViewRect();

	    int currLineYPos = textEngine.getYLinePos(cursor.getI());
        if (currLineYPos + EditorConfig.SCROLL_OFFSET > viewRect.y + viewRect.height) {
            panel.scrollByOneLine(Direction.DOWN, viewRect);
        }

    }
}
