package br.edu.cafeteria.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        configurarAparenciaDoSistema();
        SwingUtilities.invokeLater(() -> new TelaBuscaProdutos().setVisible(true));
    }

    private static void configurarAparenciaDoSistema() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}
