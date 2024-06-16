package org.gui.listeners;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ModifyFontListener implements ActionListener {

    private final String action;
    private final TextAreaPanel panel;

    public ModifyFontListener(TextAreaPanel panel, String action) {
        this.action = action;
        this.panel = panel;
    }

    private int getNewCursorWidth() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        /*We need the current font*/
        g.setFont(panel.getFont());

        // Obtain FontMetrics
        FontMetrics metrics = g.getFontMetrics();

        // Calculate total height and individual character width
        return metrics.charWidth('H'); // Example character
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (action.equals("decrease")) {
            EditorConfig.FONT_SIZE = EditorConfig.FONT_SIZE == 12.0f ?
                                    EditorConfig.FONT_SIZE :
                                    EditorConfig.FONT_SIZE - 2;

        } else if (action.equals("increase")) {
            EditorConfig.FONT_SIZE = EditorConfig.FONT_SIZE == 26 ?
                                     EditorConfig.FONT_SIZE :
                                     EditorConfig.FONT_SIZE + 2;
        }

        panel.setCustomFont();
        EditorConfig.CURSOR_WIDTH = getNewCursorWidth();
        EditorConfig.CURSOR_HEIGHT = (int) Math.floor(18 * (EditorConfig.FONT_SIZE / 16.0f));

        panel.getCustomCursor().setCursorWidth(EditorConfig.CURSOR_WIDTH);
        panel.getCustomCursor().setCursorHeight(EditorConfig.CURSOR_HEIGHT);

        /* When we modify the size we have to remove the displacement*/
        panel.getCustomCursor().updateCursorPhysicalPos();
    }
}












