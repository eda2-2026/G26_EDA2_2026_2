package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Product;

public class ResultadoBuscaProduto {

    private final Product produto;
    private final long tempoNanossegundos;
    private final int comparacoes;

    public ResultadoBuscaProduto(Product produto, long tempoNanossegundos, int comparacoes) {
        this.produto = produto;
        this.tempoNanossegundos = tempoNanossegundos;
        this.comparacoes = comparacoes;
    }

    public boolean encontrou() {
        return produto != null;
    }

    public Product getProduto() {
        return produto;
    }

    public long getTempoNanossegundos() {
        return tempoNanossegundos;
    }

    public int getComparacoes() {
        return comparacoes;
    }
}
