// CafeApplication.java
package main;

import view.OpeningForm;

import javax.swing.*;

public class CafeApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel to system default
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Start with opening form
                OpeningForm openingForm = new OpeningForm();
                openingForm.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}