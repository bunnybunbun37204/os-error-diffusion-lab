package org.bunyawat;

import javax.swing.SwingUtilities;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProgramGUI().setVisible(true));
    }
}
