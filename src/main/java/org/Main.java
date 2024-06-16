package org;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.gui.components.TextAreaPanel;
import org.gui.listeners.ModifyFontListener;
import org.gui.listeners.SaveButtonListener;
import javax.swing.*;
import java.awt.*;

public class Main {

    private static void createAndShowGui() {
        System.out.println("Created gui on EDT? " + SwingUtilities.isEventDispatchThread());
        FlatDarculaLaf.setup();
        JFrame f = new JFrame("Swing paint demo");
        TextAreaPanel panel = new TextAreaPanel();
        JMenuBar menuBar = new JMenuBar();
        JScrollPane scrollPane = new JScrollPane(panel);


        JMenuItem saveItem, openItem;
        JMenu fileMenu = new JMenu("File");

        JMenuItem increaseFontItem, decreaseFontItem;
        JMenu editorMenu = new JMenu("Editor");


        /* Set up the panel*/
        panel.setBorder(null);

        /* Set up the Scroll Pane*/
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setBounds(0, 0, 100, 700);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);


        /* Set up  the File Menu*/
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        /* Action listeners*/
        saveItem.addActionListener(new SaveButtonListener(panel));
        /* Adding menu items to menu */
        fileMenu.add(saveItem);
        fileMenu.add(openItem);

        /* Set up the Editor Menu*/
        increaseFontItem = new JMenuItem("Increase Font");
        decreaseFontItem = new JMenuItem("Decrease Font");
        /* Action listeners*/
        increaseFontItem.addActionListener(new ModifyFontListener(panel, "increase"));
        decreaseFontItem.addActionListener(new ModifyFontListener(panel, "decrease"));
        /* Adding menu items to menu*/
        editorMenu.add(increaseFontItem);
        editorMenu.add(decreaseFontItem);

        /* Adding menus to menu bar*/
        menuBar.add(fileMenu);
        menuBar.add(editorMenu);


        f.setJMenuBar(menuBar);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.add(panel);
        f.add(scrollPane);
        f.pack();
        f.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }
}
