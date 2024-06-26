package org.gui.components.cursor;

import java.util.List;

import org.gui.components.TextAreaPanel;

public interface CursorCommand {
	void execute(TextAreaPanel panel, boolean editingText);

	/**
	 * When we move the cursor to a new line we want to move it to the last charater
	 * of the new line if the current line is longer, otherwise we want to leave it
	 * on the same column.
	 * 
	 * @param panel   The panel where the text area is.
	 * @param newLine The index of the new line we want to move to.
	 * @return returns the offset(positive or negative) we have to move our cursor
	 *         when going to a new line.
	 */
	public static int getNewLineColumnPos(TextAreaPanel panel, int newLineIndex) {
		CustomCursor cursor = panel.getCustomCursor();
		int currLinePos = cursor.getJ();
		int newLinePos = 0;
		List<char[]> text = panel.getTextEngine().getText();
		
		char[] newLine = text.get(newLineIndex);

		while (newLinePos++ == newLine.length &&
								newLine[newLinePos] != '\0') {
			newLinePos++;
		}
		// We put here - in front of the return because we want to go to the left
		if (newLinePos >= currLinePos)
			return 0;
		else
			return -(currLinePos - newLinePos);
	}
}
