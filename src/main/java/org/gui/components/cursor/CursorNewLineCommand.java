package org.gui.components.cursor;

import java.awt.Rectangle;

import javax.swing.JScrollPane;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;
import org.gui.components.TextEngine;

public class CursorNewLineCommand implements CursorCommand {
    @Override
    public void execute(TextAreaPanel panel, boolean editingText) {
        CustomCursor cursor = panel.getCustomCursor();
        TextEngine textEngine = panel.getTextEngine();
        textEngine.breakLine(cursor.getJ(), cursor.getI());

        /* Set new preferred size 
         * Get the last line position */
        int lastLineYPos = textEngine.getYLinePos(textEngine.getText().size() - 1);
        /* TODO: Implement and for width */
        panel.updateContentSize(600, lastLineYPos + EditorConfig.PADDING_BOTTOM);

        /* Move the cursor logically */
        cursor.setJ(0);
        cursor.setI(cursor.getI() + 1);
        /* Move the cursor on screen */
        cursor.updateCursorPhysicalPos();

        /* Scroll Panel if reaching the bottom with the cursor*/
        JScrollPane scrollPane = panel.getScrollPane();
        Rectangle viewRect = scrollPane.getViewport().getViewRect();

	    int currLineYPos = textEngine.getYLinePos(cursor.getI());
        if (currLineYPos + EditorConfig.SCROLL_OFFSET > viewRect.y + viewRect.height) {
            System.out.println("Am intrat la scroll New Line");
            System.out.println("currlineY + scrollOffset " + (currLineYPos + EditorConfig.SCROLL_OFFSET) + " viewRectY + height " + (viewRect.y + viewRect.height));
            panel.scrollByOneLine(Direction.NEW_LINE, viewRect);
        }

    }
}































