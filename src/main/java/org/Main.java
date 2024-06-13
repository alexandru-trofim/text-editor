package org;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.gui.components.TextAreaPanel;
import org.gui.listeners.SaveButtonListener;
import javax.swing.*;

public class Main {

    private static void createAndShowGui() {
        System.out.println("Created gui on EDT? " + SwingUtilities.isEventDispatchThread());
        FlatDarculaLaf.setup();
        JFrame f = new JFrame("Swing paint demo");
        TextAreaPanel panel = new TextAreaPanel();
        JMenuBar menuBar = new JMenuBar();

        JMenuItem saveItem, openItem;
        JMenu fileMenu = new JMenu("File");

        JMenuItem increaseFontItem, decreaseFontItem;
        JMenu editorMenu = new JMenu("Editor");

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel);
        f.pack();
        f.setVisible(true);

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
        /*Here*/
        /* Adding menu items to menu*/
        editorMenu.add(increaseFontItem);
        editorMenu.add(decreaseFontItem);

        /* Adding menus to menu bar*/
        menuBar.add(fileMenu);
        menuBar.add(editorMenu);

        f.setJMenuBar(menuBar);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }
}
