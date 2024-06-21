package org.gui.components.cursor;

import java.awt.Rectangle;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

public class MoveCursorDownCommand implements CursorCommand {
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        if (panel.getTextEngine().getText().size() - 1 == cursor.getI()) return;

        int offset = CursorCommand.getNewLineColumnPos(panel,cursor.getI() + 1);

        /* Move the cursor logically */
        cursor.setI(cursor.getI() + 1);
        cursor.setJ(cursor.getJ() + offset);

        /* Move the cursor on screen */
        cursor.updateCursorPhysicalPos();

        /* Scroll Panel if reaching the bottom with the cursor*/
        JScrollPane scrollPane = panel.getScrollPane();
        Rectangle viewRect = scrollPane.getViewport().getViewRect();

        /* TODO: Here we will simplify like this:
         * if (newLinePosY is outside of viewRect) then scroll */
        if (cursor.getY() + EditorConfig.CURSOR_HEIGHT > viewRect.y + viewRect.height - EditorConfig.CURSOR_HEIGHT) {
            panel.scrollByOneLine(Direction.DOWN, viewRect);
        }
    }
}
