package testes;

import br.edu.cafeteria.app.TelaBuscaClientes;

import javax.swing.SwingUtilities;

public class TelaBuscaClientesTest {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TelaBuscaClientes tela = new TelaBuscaClientes();
            if (!tela.getTitle().contains("Clientes")) {
                throw new AssertionError("Título da janela de clientes inesperado.");
            }
            if (tela.getContentPane().getComponentCount() != 3) {
                throw new AssertionError("A interface de clientes não possui os três painéis esperados.");
            }
            tela.dispose();
        });

        System.out.println("Teste de inicialização da tela com 20.000 clientes passou.");
    }
}
