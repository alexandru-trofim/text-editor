package org;

import org.gui.components.TextAreaPanel;
import org.gui.listeners.SaveButtonListener;

import javax.swing.*;

public class Main {

    private static void createAndShowGui() {
        System.out.println("Created gui on EDT? " + SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing paint demo");
        TextAreaPanel panel = new TextAreaPanel();
        JMenuBar menuBar = new JMenuBar();
        JMenuItem saveItem, openItem;
        JMenu fileMenu = new JMenu("File");

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel);
        f.pack();
        f.setVisible(true);


        // Menu Item (Drop down menus)
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");

        saveItem.addActionListener(new SaveButtonListener(panel));

        // Adding menu items to menu
        fileMenu.add(saveItem);
        fileMenu.add(openItem);

        // adding menu to menu bar
        menuBar.add(fileMenu);

        // setting menubar at top of the window.

        // if you create an object of JFrame in class then code to set JMenuBar to JFrame will be:
        // jframe.setJMenuBar(menuBar);
        // if class is extending JFrame then it will be like this:
        f.setJMenuBar(menuBar);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }
}
