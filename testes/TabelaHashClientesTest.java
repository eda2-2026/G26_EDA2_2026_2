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
        testes.deveInformarCpfInexistente();

        System.out.println("Todos os testes da Tabela Hash passaram com sucesso!");
    }

    private void deveEncontrarPrimeiroClientePorCpf() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("001.000.000-01");
        verificar(resultado.encontrou(), "O cliente com CPF 001.000.000-01 deveria ser encontrado.");
        verificar("Ada Lovelace".equals(resultado.getCliente().getNome()), "Nome do cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void deveEncontrarClienteQualquerPorCpf() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("042.000.000-42");
        verificar(resultado.encontrou(), "O cliente com CPF 042.000.000-42 deveria ser encontrado.");
        verificar("Mulan Hua".equals(resultado.getCliente().getNome()), "Nome do cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void deveEncontrarUltimoClientePorCpf() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("070.000.000-70");
        verificar(resultado.encontrou(), "O cliente com CPF 070.000.000-70 deveria ser encontrado.");
        verificar("Zelda Hyrule".equals(resultado.getCliente().getNome()), "Nome do cliente incorreto.");
        validarMetricasHash(resultado);
    }

    private void deveInformarCpfInexistente() {
        ResultadoBuscaCliente resultado = tabelaHash.buscarPorCpf("999.999.999-99");
        verificar(!resultado.encontrou(), "Um CPF inexistente não pode ser encontrado.");
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca inválido.");
    }

    private void validarMetricasHash(ResultadoBuscaCliente resultado) {
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca inválido.");
        verificar(resultado.getComparacoes() > 0, "A busca deve realizar pelo menos 1 comparação.");
        // Na Tabela Hash com encadeamento separado, para 70 itens e tamanho 97,
        // a quantidade de comparações em buscas bem-sucedidas é praticamente O(1) (geralmente 1 a 3 comparações).
        verificar(resultado.getComparacoes() <= 3,
                "Na Tabela Hash, a busca não deveria exceder 3 comparações no cenário ideal.");
    }

    private void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
