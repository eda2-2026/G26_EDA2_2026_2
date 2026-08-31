package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Product;

public class BuscaBinariaProdutos {

    public ResultadoBuscaProduto buscarPorNome(Product[] produtos, String nomeProcurado) {
        if (produtos == null) {
            throw new IllegalArgumentException("O vetor de produtos nao pode ser nulo.");
        }
        if (nomeProcurado == null || nomeProcurado.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do produto.");
        }

        String chave = nomeProcurado.trim();
        int inicio = 0;
        int fim = produtos.length - 1;
        int comparacoes = 0;

        long inicioTempo = System.nanoTime();

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;
            Product produtoDoMeio = produtos[meio];
            comparacoes++;

            int comparacao = produtoDoMeio.getNome().compareToIgnoreCase(chave);

            if (comparacao == 0) {
                long tempo = System.nanoTime() - inicioTempo;
                return new ResultadoBuscaProduto(produtoDoMeio, tempo, comparacoes);
            }

            if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        long tempo = System.nanoTime() - inicioTempo;
        return new ResultadoBuscaProduto(null, tempo, comparacoes);
    }
}
