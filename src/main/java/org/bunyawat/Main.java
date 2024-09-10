package org.bunyawat;

import javax.swing.*;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConverterGUI().setVisible(true);
        });
    }
}
