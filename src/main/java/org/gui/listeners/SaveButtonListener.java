package org.gui.listeners;

import org.gui.components.TextAreaPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveButtonListener implements ActionListener {

    private TextAreaPanel panel;
    public SaveButtonListener(TextAreaPanel panel) {
        this.panel = panel;
    }


//    private void saveToFileV2(String filePath) {
//
//        List<char[]> text = panel.getTextEngine().getText();
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            //This one has a flaw when having multiple blank lines within the file
//            Arrays.stream(text)
//                    .map(row -> new String(row).replace("\0", "").toCharArray()) // Remove '\0'
//                    .map(String::new) // Convert char[] to String
//                    .takeWhile((line) -> !line.isEmpty())
//                    .forEach(line -> {
//                        try {
//                            writer.write(line);
//                            writer.newLine(); // Add newline after each row
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void saveToFile(String filePath) {
        List<char[]> text = panel.getTextEngine().getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (char[] row : text) {
                if (row[0] == '\0') {
                    writer.newLine();
                    continue;
                }
                for (char c : row) {
                    if (c == '\0') {
                        //Break when finding the \0 character
                        break;
                    }
                    writer.write(c);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        saveToFile("./output.txt");
        System.out.println("The file was saved");
    }
}
