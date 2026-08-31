package br.edu.cafeteria.app;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class TelaMenuPrincipal extends JFrame {

    private static final Color MARROM = new Color(67, 45, 35);
    private static final Color AZUL_ESCURO = new Color(25, 45, 75);
    private static final Color CREME = new Color(250, 244, 232);
    private static final Color BEGE_BOTAO = new Color(238, 226, 210);
    private static final Color AZUL_CLARO = new Color(220, 230, 242);

    public TelaMenuPrincipal() {
        super("Geek Byte Brew - Painel Central");
        configurarJanela();
        montarInterface();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o app inteiro ao fechar o menu
        setSize(450, 350);
        setLocationRelativeTo(null); // Centraliza na tela
    }

    private void montarInterface() {
        JPanel painelPrincipal = new JPanel(new GridLayout(3, 1, 15, 15));
        painelPrincipal.setBackground(CREME);
        painelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));
        setContentPane(painelPrincipal);

        JLabel titulo = new JLabel("Geek Byte Brew", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titulo.setForeground(MARROM);
        painelPrincipal.add(titulo);

        JButton btnProdutos = new JButton("1. Buscar Produtos (Cardápio)");
        btnProdutos.setBackground(BEGE_BOTAO);
        btnProdutos.setForeground(MARROM);
        btnProdutos.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnProdutos.setFocusPainted(false);
        btnProdutos.addActionListener(e -> new TelaBuscaProdutos().setVisible(true));
        painelPrincipal.add(btnProdutos);

        JButton btnClientes = new JButton("2. Buscar Clientes (Membros)");
        btnClientes.setBackground(AZUL_CLARO);
        btnClientes.setForeground(AZUL_ESCURO);
        btnClientes.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnClientes.setFocusPainted(false);
        btnClientes.addActionListener(e -> new TelaBuscaClientes().setVisible(true));
        painelPrincipal.add(btnClientes);
    }
}
