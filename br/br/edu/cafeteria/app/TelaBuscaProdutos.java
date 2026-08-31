package br.edu.cafeteria.app;

import br.edu.cafeteria.modelo.Bebida;
import br.edu.cafeteria.modelo.CatalogoProdutos;
import br.edu.cafeteria.modelo.Product;
import br.edu.cafeteria.servico.BuscaBinariaProdutos;
import br.edu.cafeteria.servico.ResultadoBuscaProduto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import java.text.NumberFormat;
import java.util.Locale;

public class TelaBuscaProdutos extends JFrame {

    private static final Color MARROM = new Color(67, 45, 35);
    private static final Color CREME = new Color(250, 244, 232);
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR"));

    private final Product[] produtos = CatalogoProdutos.criarCardapioOrdenado();
    private final BuscaBinariaProdutos buscaBinaria = new BuscaBinariaProdutos();

    private final JTextField campoBusca = new JTextField(28);
    private final JLabel resultado = new JLabel("Digite ou selecione um nome do cardapio.");
    private final JLabel detalhes = new JLabel(" ");
    private final JLabel desempenho = new JLabel("Tempo: - | Comparacoes: -");
    private final JTable tabela = criarTabela();

    public TelaBuscaProdutos() {
        super("Geek Byte Brew - Busca Binaria de Produtos");
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

        tabela.getSelectionModel().addListSelectionListener(evento -> preencherNomeSelecionado());
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    executarBusca();
                }
            }
        });
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setOpaque(false);

        JLabel titulo = new JLabel("Geek Byte Brew", SwingConstants.CENTER);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titulo.setForeground(MARROM);

        JLabel subtitulo = new JLabel(
                "Cardapio com 50 produtos em ordem alfabetica", SwingConstants.CENTER);
        subtitulo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        controles.setOpaque(false);
        JLabel rotulo = new JLabel("Nome exato do produto:");
        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.setBackground(MARROM);
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.addActionListener(evento -> executarBusca());
        campoBusca.addActionListener(evento -> executarBusca());
        getRootPane().setDefaultButton(botaoBuscar);

        controles.add(rotulo);
        controles.add(campoBusca);
        controles.add(botaoBuscar);

        painel.add(textos, BorderLayout.NORTH);
        painel.add(controles, BorderLayout.SOUTH);
        return painel;
    }

    private JTable criarTabela() {
        String[] colunas = {"Nome", "Codigo", "Tipo", "Preco", "Estoque"};
        Object[][] linhas = new Object[produtos.length][colunas.length];

        for (int i = 0; i < produtos.length; i++) {
            Product produto = produtos[i];
            linhas[i][0] = produto.getNome();
            linhas[i][1] = produto.getCodigo();
            linhas[i][2] = tipoDoProduto(produto);
            linhas[i][3] = MOEDA.format(produto.getPreco_Base());
            linhas[i][4] = produto.getQntd_estocada();
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
        novaTabela.getColumnModel().getColumn(0).setPreferredWidth(280);
        novaTabela.getColumnModel().getColumn(1).setPreferredWidth(70);
        return novaTabela;
    }

    private JPanel criarPainelResultado() {
        JPanel painel = new JPanel(new GridLayout(3, 1, 4, 4));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 190, 165)),
                new EmptyBorder(10, 12, 10, 12)));

        resultado.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultado.setForeground(MARROM);
        desempenho.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        painel.add(resultado);
        painel.add(detalhes);
        painel.add(desempenho);
        return painel;
    }

    private void preencherNomeSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            campoBusca.setText(produtos[linha].getNome());
        }
    }

    private void executarBusca() {
        String nome = campoBusca.getText();
        if (nome == null || nome.isBlank()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o nome do produto que deseja procurar.",
                    "Campo obrigatorio",
                    JOptionPane.WARNING_MESSAGE);
            campoBusca.requestFocusInWindow();
            return;
        }

        ResultadoBuscaProduto resposta = buscaBinaria.buscarPorNome(produtos, nome);
        exibirResposta(resposta);
    }

    private void exibirResposta(ResultadoBuscaProduto resposta) {
        if (resposta.encontrou()) {
            Product produto = resposta.getProduto();
            resultado.setText("Produto encontrado: " + produto.getNome());
            detalhes.setText(String.format(
                    "Codigo: %s | Tipo: %s | Preco: %s | Estoque: %d",
                    produto.getCodigo(),
                    tipoDoProduto(produto),
                    MOEDA.format(produto.getPreco_Base()),
                    produto.getQntd_estocada()));
            selecionarProdutoNaTabela(produto);
        } else {
            resultado.setText("Produto nao encontrado no cardapio.");
            detalhes.setText("Confira o nome e tente novamente.");
            tabela.clearSelection();
        }

        double microssegundos = resposta.getTempoNanossegundos() / 1_000.0;
        desempenho.setText(String.format(
                "Tempo: %d ns (%.3f us) | Comparacoes: %d | Complexidade: O(log N)",
                resposta.getTempoNanossegundos(),
                microssegundos,
                resposta.getComparacoes()));
    }

    private void selecionarProdutoNaTabela(Product produto) {
        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i] == produto) {
                tabela.setRowSelectionInterval(i, i);
                tabela.scrollRectToVisible(tabela.getCellRect(i, 0, true));
                return;
            }
        }
    }

    private String tipoDoProduto(Product produto) {
        return produto instanceof Bebida ? "Bebida" : "Comida";
    }
}
