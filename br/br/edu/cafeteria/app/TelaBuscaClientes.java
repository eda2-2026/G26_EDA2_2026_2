package br.edu.cafeteria.app;

import br.edu.cafeteria.modelo.CatalogoClientes;
import br.edu.cafeteria.modelo.Cliente;
import br.edu.cafeteria.modelo.ClienteComum;
import br.edu.cafeteria.modelo.ClienteVIP;
import br.edu.cafeteria.servico.BuscaBinariaClientes;
import br.edu.cafeteria.servico.ResultadoBuscaCliente;
import br.edu.cafeteria.servico.TabelaHashClientes;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaBuscaClientes extends JFrame {

    private static final Color AZUL_ESCURO = new Color(25, 45, 75);
    private static final Color CREME = new Color(250, 244, 232);

    private final Cliente[] clientes = CatalogoClientes.criarClientesOrdenados();
    private final BuscaBinariaClientes buscaBinaria = new BuscaBinariaClientes();
    private final TabelaHashClientes tabelaHash = new TabelaHashClientes(clientes);


    private final JComboBox<String> comboFiltro = new JComboBox<>(new String[]{"Nome", "CPF"});
    private final JTextField campoBusca = new JTextField(22);
    private final JLabel resultado = new JLabel("Digite ou selecione um cliente na tabela.");
    private final JLabel detalhes = new JLabel(" ");
    private final JLabel desempenho = new JLabel("Tempo: - | Comparacoes: -");
    private final JTable tabela = criarTabela();

    public TelaBuscaClientes() {
        super("Geek Byte Brew - Busca Binária de Clientes");
        configurarJanela();
        montarInterface();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 620));
        setSize(950, 680);
        setLocationRelativeTo(null);
    }

    private void montarInterface() {
        JPanel conteudo = new JPanel(new BorderLayout(12, 12));
        conteudo.setBackground(CREME);
        conteudo.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(conteudo);

        conteudo.add(criarCabecalho(), BorderLayout.NORTH);
        conteudo.add(new JScrollPane(tabela), BorderLayout.CENTER);
        conteudo.add(criarPainelResultado(), BorderLayout.SOUTH);

        // Preenche o campo de texto ao clicar na tabela, respeitando o filtro escolhido
        tabela.getSelectionModel().addListSelectionListener(evento -> preencherCampoSelecionado());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    executarBusca();
                }
            }
        });

        comboFiltro.addActionListener(evento -> preencherCampoSelecionado());
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setOpaque(false);

        JLabel titulo = new JLabel("Membros Geek Byte Brew", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titulo.setForeground(AZUL_ESCURO);

        JLabel subtitulo = new JLabel(
                "Base com 20.000 clientes ordenados", SwingConstants.CENTER);
        subtitulo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        controles.setOpaque(false);
        JLabel rotulo = new JLabel("Buscar por:");

        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.setBackground(AZUL_ESCURO);
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.addActionListener(evento -> executarBusca());
        campoBusca.addActionListener(evento -> executarBusca());
        getRootPane().setDefaultButton(botaoBuscar);

        controles.add(rotulo);
        controles.add(comboFiltro);
        controles.add(campoBusca);
        controles.add(botaoBuscar);

        painel.add(textos, BorderLayout.NORTH);
        painel.add(controles, BorderLayout.SOUTH);
        return painel;
    }

    private JTable criarTabela() {
        String[] colunas = {"Nome", "CPF", "Categoria", "Saldo XP"};
        Object[][] linhas = new Object[clientes.length][colunas.length];

        for (int i = 0; i < clientes.length; i++) {
            Cliente cliente = clientes[i];
            linhas[i][0] = cliente.getNome();
            linhas[i][1] = cliente.getCpf();
            linhas[i][2] = tipoDoCliente(cliente);
            linhas[i][3] = cliente.getSaldoXP() + " pts";
        }

        DefaultTableModel modelo = new DefaultTableModel(linhas, colunas) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        JTable novaTabela = new JTable(modelo);
        novaTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        novaTabela.setRowHeight(25);
        novaTabela.getTableHeader().setReorderingAllowed(false);
        novaTabela.getColumnModel().getColumn(0).setPreferredWidth(250);
        novaTabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        return novaTabela;
    }

    private JPanel criarPainelResultado() {
        JPanel painel = new JPanel(new GridLayout(3, 1, 4, 4));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 190, 165)),
                new EmptyBorder(10, 12, 10, 12)));

        resultado.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultado.setForeground(AZUL_ESCURO);
        desempenho.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        painel.add(resultado);
        painel.add(detalhes);
        painel.add(desempenho);
        return painel;
    }

    private void preencherCampoSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            if ("Nome".equals(comboFiltro.getSelectedItem())) {
                campoBusca.setText(clientes[linha].getNome());
            } else {
                campoBusca.setText(clientes[linha].getCpf());
            }
        }
    }

    private void executarBusca() {
        String termo = campoBusca.getText();
        if (termo == null || termo.isBlank()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o termo que deseja procurar.",
                    "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            campoBusca.requestFocusInWindow();
            return;
        }

        String filtro = (String) comboFiltro.getSelectedItem();
        ResultadoBuscaCliente resposta;

        if ("CPF".equals(filtro)) {
            resposta = tabelaHash.buscarPorCpf(termo);
        } else {
            resposta = buscaBinaria.buscarPorNome(clientes, termo);
        }

        exibirResposta(resposta);
    }

    private void exibirResposta(ResultadoBuscaCliente resposta) {
        if (resposta.encontrou()) {
            Cliente cliente = resposta.getCliente();
            resultado.setText("Cliente encontrado: " + cliente.getNome());
            detalhes.setText(String.format(
                    "CPF: %s | Categoria: %s | XP Acumulado: %d pts",
                    cliente.getCpf(),
                    tipoDoCliente(cliente),
                    cliente.getSaldoXP()));
            selecionarClienteNaTabela(cliente);
        } else {
            resultado.setText("Cliente não encontrado no catálogo.");
            detalhes.setText("Confira a ortografia ou o filtro selecionado e tente novamente.");
            tabela.clearSelection();
        }

        double microssegundos = resposta.getTempoNanossegundos() / 1_000.0;
        String complexidade = "Nome".equals(comboFiltro.getSelectedItem()) ? "O(log N)" : "O(1)";

        desempenho.setText(String.format(
                "Tempo: %d ns (%.3f µs) | Comparações: %d | Complexidade: %s",
                resposta.getTempoNanossegundos(),
                microssegundos,
                resposta.getComparacoes(),
                complexidade));
    }

    private void selecionarClienteNaTabela(Cliente cliente) {
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] == cliente) {
                tabela.setRowSelectionInterval(i, i);
                tabela.scrollRectToVisible(tabela.getCellRect(i, 0, true));
                return;
            }
        }
    }

    private String tipoDoCliente(Cliente cliente) {
        if (cliente instanceof ClienteVIP) return "VIP";
        if (cliente instanceof ClienteComum) return "Comum";
        return "Desconhecido";
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaBuscaClientes().setVisible(true);
        });
    }
}
