package testes;

import br.edu.cafeteria.app.TelaBuscaProdutos;

import javax.swing.SwingUtilities;

public class TelaBuscaProdutosTest {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TelaBuscaProdutos tela = new TelaBuscaProdutos();
            if (!tela.getTitle().contains("Busca Binaria")) {
                throw new AssertionError("Titulo da janela inesperado.");
            }
            if (tela.getContentPane().getComponentCount() != 3) {
                throw new AssertionError("A interface nao possui os tres paineis esperados.");
            }
            tela.dispose();
        });

        System.out.println("Teste de inicializacao da interface Swing passou.");
    }
}
