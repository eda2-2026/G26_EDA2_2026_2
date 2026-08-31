package testes;

import br.edu.cafeteria.modelo.CatalogoClientes;
import br.edu.cafeteria.modelo.Cliente;
import br.edu.cafeteria.servico.ResultadoBuscaCliente;
import br.edu.cafeteria.servico.TabelaHashClientes;

public class TabelaHashClientesTest {

    private final Cliente[] clientes = CatalogoClientes.criarClientesOrdenados();
    private final TabelaHashClientes tabelaHash = new TabelaHashClientes(clientes);

    public static void main(String[] args) {
        TabelaHashClientesTest testes = new TabelaHashClientesTest();
        testes.deveEncontrarPrimeiroClientePorCpf();
        testes.deveEncontrarClienteQualquerPorCpf();
        testes.deveEncontrarUltimoClientePorCpf();
        testes.deveEncontrarClienteDeNumeroVinteMilPorCpf();
        testes.deveInformarCpfInexistente();

        System.out.println("Todos os testes da Tabela Hash passaram com sucesso!");
    }

    private void deveEncontrarPrimeiroClientePorCpf() {
        Cliente primeiroCliente = clientes[0];
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf(primeiroCliente.getCpf());
        verificar(resultado.encontrou(), "O primeiro cliente deveria ser encontrado pelo CPF.");
        verificar(resultado.getCliente() == primeiroCliente, "Primeiro cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void deveEncontrarClienteQualquerPorCpf() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("900.000.000-42");
        verificar(resultado.encontrou(), "O cliente com CPF 900.000.000-42 deveria ser encontrado.");
        verificar("Maria Ana Gomes".equals(resultado.getCliente().getNome()),
                "Nome do cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void deveEncontrarUltimoClientePorCpf() {
        Cliente ultimoCliente = clientes[clientes.length - 1];
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf(ultimoCliente.getCpf());
        verificar(resultado.encontrou(), "O último cliente deveria ser encontrado pelo CPF.");
        verificar(resultado.getCliente() == ultimoCliente, "Último cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void deveInformarCpfInexistente() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("999.999.999-99");
        verificar(!resultado.encontrou(), "Um CPF inexistente não pode ser encontrado.");
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca inválido.");
    }

    private void deveEncontrarClienteDeNumeroVinteMilPorCpf() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("90000020000");
        verificar(resultado.encontrou(), "O cliente de número 20.000 deveria ser encontrado pelo CPF.");
        verificar("Eduardo Rodrigo Carvalho".equals(resultado.getCliente().getNome()),
                "Nome do cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void validarMetricasHash(ResultadoBuscaCliente resultado) {
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca inválido.");
        verificar(resultado.getComparacoes() > 0, "A busca deve realizar pelo menos 1 comparação.");
        verificar(resultado.getComparacoes() <= 5,
                "Na tabela hash dimensionada, a busca não deveria exceder 5 comparações.");
    }

    private void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
