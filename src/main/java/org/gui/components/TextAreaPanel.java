package org.gui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import org.gui.components.cursor.CustomCursor;
import org.gui.components.cursor.Direction;
import org.gui.listeners.TextEditorListener;

public class TextAreaPanel extends JPanel {
	private Font currentFont;
	private CustomCursor cursor;
	private Timer cursorBlinkTimer;
	private TextEngine textEngine;
	private int preferredWidth = 600; // Initial preferred width
	private int preferredHeight = 600; // Initial preferred h
	private JScrollPane scrollPane;


	public TextAreaPanel() {
		cursor = new CustomCursor(0, 0);
		textEngine = new TextEngine(this);
		cursorBlinkTimer = cursor.enableCursorBlinking(this);
		scrollPane = null;

		setFocusable(true);
		requestFocusInWindow();

		cursorBlinkTimer.start();
		setCustomFont();
		addKeyListener(new TextEditorListener(this));
	}
	
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public CustomCursor getCustomCursor() {
		return cursor;
	}

	public void updatePanelSize() {
		preferredHeight = getHeight();
		preferredWidth = getWidth();
	}

	public TextEngine getTextEngine() {
		return textEngine;
	}

	public void setCustomFont() { 
		try {
			InputStream stream = getClass().getResourceAsStream("/UbuntuMono-R.ttf");
			currentFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(stream))
					.deriveFont(EditorConfig.FONT_SIZE);

			setFont(currentFont);
			// This if for scale
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.setFont(currentFont);

			// Obtain FontMetrics
			FontMetrics metrics = g.getFontMetrics();

			// Calculate total height and individual character width
			int charWidth = metrics.charWidth('a'); // Example character
			cursor.setCursorWidth(charWidth);
			EditorConfig.CURSOR_WIDTH = charWidth;

		} catch (IOException | FontFormatException | NullPointerException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading font: " + e.getMessage(), "Font Loading Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updateContentSize(int newWidth, int newHeight) {
		boolean sizeChanged = false;

		if (newWidth > preferredWidth) {
			preferredWidth = newWidth;
			sizeChanged = true;
		}
		if (newHeight > preferredHeight) {
			preferredHeight = newHeight;
			sizeChanged = true;
			//Maybe it's ok, maybe not
			scrollPane.getVerticalScrollBar().setMaximum(preferredHeight);
			System.out.println("height: " + scrollPane.getVerticalScrollBar().getMaximum());
			
		}

		if (sizeChanged) {
			revalidate(); // Notify the JScrollPane of the change
//            repaint();    // Optionally repaint to reflect changes immediately
		}
	}

	public void scrollByOneLine(Direction direction, Rectangle viewRect) {
	    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	    int offset = 0; 
	    int lineHeight = EditorConfig.CURSOR_WIDTH + EditorConfig.LINE_SPACING; 

	    int currLineYPos = textEngine.getYLinePos(cursor.getI());

	    if (direction == Direction.UP) {
	    	offset = Math.abs((viewRect.y + EditorConfig.SCROLL_OFFSET) - currLineYPos);
	    	offset += EditorConfig.CURSOR_HEIGHT;
	    } else if (direction == Direction.DOWN) {
	    	offset = Math.abs((viewRect.y + viewRect.height) - currLineYPos);
	    	offset += EditorConfig.SCROLL_OFFSET;
	    } else if (direction == Direction.NEW_LINE) {
	    	System.out.println("Viewport end y " + (viewRect.y + viewRect.height));
	    	offset = Math.abs((viewRect.y + viewRect.height) - currLineYPos);
	    	System.out.println("offset 1: " + offset);
	    	offset += EditorConfig.PADDING_BOTTOM;
	    	System.out.println("offset 2: " + offset);
	    }


	    offset = direction == Direction.UP ? - offset : offset;
		System.out.println("offset 3: " + offset);
		System.out.println("scrollBar value1: " + verticalScrollBar.getValue());
	     System.out.println("Updated Scroll Maximum 1: " + scrollPane.getVerticalScrollBar().getMaximum());
	    verticalScrollBar.setValue(verticalScrollBar.getValue() + offset); // Move the scrollbar down by one line
	      System.out.println("Updated Scroll Maximum 2: " + scrollPane.getVerticalScrollBar().getMaximum());
	      System.out.println("get preffered height " + getPreferredSize().height);

		System.out.println("scrollBar value2 " + verticalScrollBar.getValue());

        JScrollPane scrollPane = getScrollPane();
        Rectangle viewRect2 = scrollPane.getViewport().getViewRect();
	    	System.out.println("Viewport end y " + (viewRect2.y + viewRect2.height));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(preferredWidth, preferredHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		cursor.paintCursor(g);
		textEngine.paintText(g);
	}
}

































