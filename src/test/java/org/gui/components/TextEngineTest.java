package org.gui.components;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TextEngineTest {

	@Test
	void testBreakLine() {
		TextAreaPanel textAreaPanel = new TextAreaPanel();
		TextEngine  textEngine = new TextEngine(textAreaPanel);
		List<char[]> text = new ArrayList<>();
		ArrayList<char[]> expectedText = new ArrayList<>();

		/* Test 1 */
		text.add("Array".toCharArray());
		
		textEngine.setText(text);
		
		expectedText.add("Array".toCharArray());
		expectedText.add("".toCharArray());
		
		textEngine.breakLine(5, 0);
		text = textEngine.getText();
        // Assert that the two lists are the same
        assertEquals(text.size(), expectedText.size(), "Lists differ in size");

        for (int i = 0; i < text.size(); i++) {
            assertArrayEquals(text.get(i), expectedText.get(i), 
                              "Array mismatch at index " + i);
        }

        /* Test 2 */
		text = new ArrayList<>();
		expectedText = new ArrayList<>();

		text.add("".toCharArray());
		
		textEngine.setText(text);
		
		expectedText.add("".toCharArray());
		expectedText.add("".toCharArray());
		
		textEngine.breakLine(0, 0);
		text = textEngine.getText();
        // Assert that the two lists are the same
        assertEquals(text.size(), expectedText.size(), "Lists differ in size");

        for (int i = 0; i < text.size(); i++) {
        	System.out.println(text.get(i));
        	System.out.println(expectedText.get(i));
            assertArrayEquals(text.get(i), expectedText.get(i), 
                              "Array mismatch at index " + i);
        }

	}

}
