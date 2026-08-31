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
        testes.deveTerExatamenteVinteMilClientesOrdenados();
        testes.deveEncontrarPrimeiroClientePorNome();
        testes.deveEncontrarClienteConhecidoPorNome();
        testes.deveEncontrarClienteDeNumeroVinteMilPorNome();
        testes.deveEncontrarUltimoClientePorNome();
        testes.deveIgnorarMaiusculasEspacosExternosPorNome();
        testes.deveInformarClienteInexistentePorNome();

        System.out.println("Todos os 7 testes de busca binária de clientes passaram!");
    }

    private void deveTerExatamenteVinteMilClientesOrdenados() {
        verificar(clientes.length == 20_000, "O catálogo deve ter 20.000 clientes.");

        for (int i = 1; i < clientes.length; i++) {
            verificar(
                    clientes[i - 1].getNome().compareToIgnoreCase(clientes[i].getNome()) < 0,
                    "O catálogo não está em ordem alfabética no índice " + i);
        }
    }

    private void deveEncontrarPrimeiroClientePorNome() {
        Cliente primeiroCliente = clientes[0];
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, primeiroCliente.getNome());
        verificar(resultado.encontrou(), "O primeiro cliente deveria ser encontrado.");
        verificar(resultado.getCliente() == primeiroCliente, "Primeiro cliente incorreto.");
        validarMetricas(resultado);
    }

    private void deveEncontrarClienteConhecidoPorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Maria Jose Silva");
        verificar(resultado.encontrou(), "O cliente conhecido deveria ser encontrado.");
        verificar("900.000.000-01".equals(resultado.getCliente().getCpf()), "CPF incorreto.");
        validarMetricas(resultado);
    }

    private void deveEncontrarClienteDeNumeroVinteMilPorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Eduardo Rodrigo Carvalho");
        verificar(resultado.encontrou(), "O cliente de número 20.000 deveria ser encontrado.");
        verificar("900.000.200-00".equals(resultado.getCliente().getCpf()), "CPF fictício incorreto.");
        validarMetricas(resultado);
    }

    private void deveEncontrarUltimoClientePorNome() {
        Cliente ultimoCliente = clientes[clientes.length - 1];
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, ultimoCliente.getNome());
        verificar(resultado.encontrou(), "O último cliente deveria ser encontrado.");
        verificar(resultado.getCliente() == ultimoCliente, "Último cliente incorreto.");
        validarMetricas(resultado);
    }

    private void deveIgnorarMaiusculasEspacosExternosPorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "  maria jose silva  ");
        verificar(resultado.encontrou(), "A busca por nome deveria ignorar caixa e espaços externos.");
        verificar("900.000.000-01".equals(resultado.getCliente().getCpf()), "Cliente incorreto.");
    }

    private void deveInformarClienteInexistentePorNome() {
        ResultadoBuscaCliente resultado = busca.buscarPorNome(clientes, "Cliente Inexistente");
        verificar(!resultado.encontrou(), "Um cliente inexistente não pode ser encontrado.");
        validarMetricas(resultado);
    }

    private void validarMetricas(ResultadoBuscaCliente resultado) {
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca inválido.");
        verificar(resultado.getComparacoes() > 0, "A busca deve realizar comparações.");
        verificar(resultado.getComparacoes() <= 15,
                "Com 20.000 clientes, a busca binária não deveria exceder 15 comparações.");
    }

    private void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
