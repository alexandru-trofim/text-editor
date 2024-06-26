package org.gui.listeners;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import org.gui.components.TextAreaPanel;

public class SaveButtonListener implements ActionListener {


    private TextAreaPanel panel;
    public SaveButtonListener(TextAreaPanel panel) {
        this.panel = panel;
    }

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
