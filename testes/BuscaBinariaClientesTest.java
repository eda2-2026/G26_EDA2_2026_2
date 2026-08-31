package testes;

import br.edu.cafeteria.modelo.CatalogoClientes;
import br.edu.cafeteria.modelo.Cliente;
import br.edu.cafeteria.servico.BuscaBinariaClientes;
import br.edu.cafeteria.servico.ResultadoBuscaCliente;

public class BuscaBinariaClientesTest {

    private final Cliente[] clientes = CatalogoClientes.criarClientesOrdenados();
    private final BuscaBinariaClientes busca = new BuscaBinariaClientes();

    public static void main(String[] args) {
        BuscaBinariaClientesTest testes = new BuscaBinariaClientesTest();
        testes.deveTerExatamenteSetentaClientesOrdenados();
        testes.deveEncontrarPrimeiroClientePorNome();
        testes.deveEncontrarClienteDoMeioPorNome();
        testes.deveEncontrarUltimoClientePorNome();
        testes.deveIgnorarMaiusculasEspacosExternosPorNome();
        testes.deveInformarClienteInexistentePorNome();
        testes.deveEncontrarClientePorCpf();
        testes.deveInformarCpfInexistente();

        System.out.println("Todos os 8 testes de Clientes passaram com sucesso!");
    }

    private void deveTerExatamenteSetentaClientesOrdenados() {
        verificar(clientes.length == 70, "O catálogo deve ter 70 clientes.");

        for (int i = 1; i < clientes.length; i++) {
            verificar(
                    clientes[i - 1].getNome().compareToIgnoreCase(clientes[i].getNome()) < 0,
                    "O catálogo não está em ordem alfabética no índice " + i);
        }
    }

    private void deveEncontrarPrimeiroClientePorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Ada Lovelace");
        verificar(resultado.encontrou(), "O primeiro cliente deveria ser encontrado.");
        verificar("001.000.000-01".equals(resultado.getCliente().getCpf()), "CPF incorreto.");
        validarMetricas(resultado);
    }

    private void deveEncontrarClienteDoMeioPorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Linus Torvalds");
        verificar(resultado.encontrou(), "O cliente do meio deveria ser encontrado.");
        validarMetricas(resultado);
    }

    private void deveEncontrarUltimoClientePorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Zelda Hyrule");
        verificar(resultado.encontrou(), "O último cliente deveria ser encontrado.");
        verificar("070.000.000-70".equals(resultado.getCliente().getCpf()), "CPF incorreto.");
        validarMetricas(resultado);
    }

    private void deveIgnorarMaiusculasEspacosExternosPorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "  grace hopper  ");
        verificar(resultado.encontrou(), "A busca por nome deveria ignorar caixa e espaços externos.");
        verificar("022.000.000-22".equals(resultado.getCliente().getCpf()), "Cliente incorreto.");
    }

    private void deveInformarClienteInexistentePorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Cliente Inexistente");
        verificar(!resultado.encontrou(), "Um cliente inexistente não pode ser encontrado.");
        validarMetricas(resultado);
    }

    private void deveEncontrarClientePorCpf() {
        ResultadoBuscaCliente resultado = busca.buscarPorCpf(clientes, "042.000.000-42");
        verificar(resultado.encontrou(), "O cliente deveria ser encontrado pelo CPF.");
        verificar("Mulan Hua".equals(resultado.getCliente().getNome()), "Nome do cliente incorreto.");
        validarMetricas(resultado);
    }

    private void deveInformarCpfInexistente() {
        ResultadoBuscaCliente resultado = busca.buscarPorCpf(clientes, "999.999.999-99");
        verificar(!resultado.encontrou(), "Um CPF inexistente não pode ser encontrado.");
        validarMetricas(resultado);
    }

    private void validarMetricas(ResultadoBuscaCliente resultado) {
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca inválido.");
        verificar(resultado.getComparacoes() > 0, "A busca deve realizar comparações.");
        verificar(resultado.getComparacoes() <= 7,
                "Com 70 clientes, a busca binária não deveria exceder 7 comparações.");
    }

    private void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
