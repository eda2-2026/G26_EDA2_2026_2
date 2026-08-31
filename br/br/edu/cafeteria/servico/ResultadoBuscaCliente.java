package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Cliente;

public class ResultadoBuscaCliente {
    private final Cliente cliente;
    private final long tempoNanossegundos;
    private final int comparacoes;

    public ResultadoBuscaCliente(Cliente cliente, long tempoNanossegundos, int comparacoes) {
        this.cliente = cliente;
        this.tempoNanossegundos = tempoNanossegundos;
        this.comparacoes = comparacoes;
    }

    public boolean encontrou() {
        return cliente != null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public long getTempoNanossegundos() {
        return tempoNanossegundos;
    }

    public int getComparacoes() {
        return comparacoes;
    }
}
