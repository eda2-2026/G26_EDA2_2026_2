package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Cliente;

public class BuscaBinariaClientes {

    public ResultadoBuscaCliente buscarPorNome(Cliente[] clientes, String nomeProcurado) {
        if (clientes == null) {
            throw new IllegalArgumentException("O vetor de clientes não pode ser nulo.");
        }
        if (nomeProcurado == null || nomeProcurado.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do cliente.");
        }

        String chave = nomeProcurado.trim();
        int inicio = 0;
        int fim = clientes.length - 1;
        int comparacoes = 0;

        long inicioTempo = System.nanoTime();

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;
            Cliente clienteDoMeio = clientes[meio];
            comparacoes++;

            int comparacao = clienteDoMeio.getNome().compareToIgnoreCase(chave);

            if (comparacao == 0) {
                long tempo = System.nanoTime() - inicioTempo;
                return new ResultadoBuscaCliente(clienteDoMeio, tempo, comparacoes);
            }

            if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        long tempo = System.nanoTime() - inicioTempo;
        return new ResultadoBuscaCliente(null, tempo, comparacoes);
    }

}
