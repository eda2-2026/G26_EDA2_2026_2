package testes;

import br.edu.cafeteria.modelo.CatalogoProdutos;
import br.edu.cafeteria.modelo.Product;
import br.edu.cafeteria.servico.BuscaBinariaProdutos;
import br.edu.cafeteria.servico.ResultadoBuscaProduto;

public class BuscaBinariaProdutosTest {

    private final Product[] produtos = CatalogoProdutos.criarCardapioOrdenado();
    private final BuscaBinariaProdutos busca = new BuscaBinariaProdutos();

    public static void main(String[] args) {
        BuscaBinariaProdutosTest testes = new BuscaBinariaProdutosTest();
        testes.deveTerExatamenteCinquentaProdutosOrdenados();
        testes.deveEncontrarPrimeiroProduto();
        testes.deveEncontrarProdutoDoMeio();
        testes.deveEncontrarUltimoProduto();
        testes.deveIgnorarMaiusculasEspacosExternos();
        testes.deveInformarProdutoInexistente();
        System.out.println("Todos os 6 testes passaram.");
    }

    private void deveTerExatamenteCinquentaProdutosOrdenados() {
        verificar(produtos.length == 50, "O cardapio deve ter 50 produtos.");

        for (int i = 1; i < produtos.length; i++) {
            verificar(
                    produtos[i - 1].getNome().compareToIgnoreCase(produtos[i].getNome()) < 0,
                    "O cardapio nao esta em ordem alfabetica no indice " + i);
        }
    }

    private void deveEncontrarPrimeiroProduto() {
        ResultadoBuscaProduto resultado = busca.buscarPorNome(produtos, "Affogato Arcano");
        verificar(resultado.encontrou(), "O primeiro produto deveria ser encontrado.");
        verificar("P001".equals(resultado.getProduto().getCodigo()), "Codigo incorreto.");
        validarMetricas(resultado);
    }

    private void deveEncontrarProdutoDoMeio() {
        ResultadoBuscaProduto resultado = busca.buscarPorNome(produtos, "Macaron da Matrix");
        verificar(resultado.encontrou(), "O produto do meio deveria ser encontrado.");
        validarMetricas(resultado);
    }

    private void deveEncontrarUltimoProduto() {
        ResultadoBuscaProduto resultado = busca.buscarPorNome(produtos, "Zuppa Zerg");
        verificar(resultado.encontrou(), "O ultimo produto deveria ser encontrado.");
        verificar("P050".equals(resultado.getProduto().getCodigo()), "Codigo incorreto.");
        validarMetricas(resultado);
    }

    private void deveIgnorarMaiusculasEspacosExternos() {
        ResultadoBuscaProduto resultado = busca.buscarPorNome(produtos, "  cafe do programador  ");
        verificar(resultado.encontrou(), "A busca deveria ignorar caixa e espacos externos.");
        verificar("P008".equals(resultado.getProduto().getCodigo()), "Produto incorreto.");
    }

    private void deveInformarProdutoInexistente() {
        ResultadoBuscaProduto resultado = busca.buscarPorNome(produtos, "Produto Inexistente");
        verificar(!resultado.encontrou(), "Um produto inexistente nao pode ser encontrado.");
        validarMetricas(resultado);
    }

    private void validarMetricas(ResultadoBuscaProduto resultado) {
        verificar(resultado.getTempoNanossegundos() >= 0, "Tempo de busca invalido.");
        verificar(resultado.getComparacoes() > 0, "A busca deve realizar comparacoes.");
        verificar(resultado.getComparacoes() <= 6,
                "Com 50 produtos, a busca binaria nao deveria exceder 6 comparacoes.");
    }

    private void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
