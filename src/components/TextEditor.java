package components;

import javax.swing.*;

public class TextEditor {

    private static void createAndShowGui() {
        System.out.println("Created gui on EDT? " + SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing paint demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new TextAreaPanel());
        f.pack();
        f.setVisible(true);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::createAndShowGui);
    }
}
