package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Cliente;

public class TabelaHashClientes {

    private static class NoHash {
        String chaveCpf;
        Cliente cliente;
        NoHash proximo;

        NoHash(String chaveCpf, Cliente cliente) {
            this.chaveCpf = chaveCpf;
            this.cliente = cliente;
        }
    }

    private final NoHash[] tabela;
    private static final int CAPACIDADE_MINIMA = 101;

    public TabelaHashClientes() {
        this(CAPACIDADE_MINIMA);
    }

    public TabelaHashClientes(Cliente[] clientes) {
        this(calcularCapacidade(clientes));
        if (clientes == null) {
            System.out.println("[DEBUG HASH] O array de clientes recebido é NULO!");
            return;
        }

        System.out.println("[DEBUG HASH] Iniciando inserção de " + clientes.length + " clientes na tabela hash...");
        int inseridos = 0;
        for (Cliente cliente : clientes) {
            if (cliente != null) {
                inserir(cliente);
                inseridos++;
            }
        }
        System.out.println("[DEBUG HASH] Total de clientes inseridos com sucesso: " + inseridos);
    }

    private TabelaHashClientes(int capacidade) {
        tabela = new NoHash[capacidade];
    }

    private static int calcularCapacidade(Cliente[] clientes) {
        if (clientes == null || clientes.length == 0) {
            return CAPACIDADE_MINIMA;
        }
        return proximoPrimo(clientes.length * 2 + 1);
    }

    private static int proximoPrimo(int candidato) {
        int numero = Math.max(CAPACIDADE_MINIMA, candidato);
        while (!ehPrimo(numero)) {
            numero++;
        }
        return numero;
    }

    private static boolean ehPrimo(int numero) {
        if (numero < 2) {
            return false;
        }
        if (numero % 2 == 0) {
            return numero == 2;
        }
        for (int divisor = 3; divisor <= numero / divisor; divisor += 2) {
            if (numero % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    private String normalizarCpf(String cpf) {
        if (cpf == null) return "";
        return cpf.replaceAll("\\D", "");
    }

    private int calcularIndice(String cpf) {
        int hashcode = cpf.hashCode();
        return (hashcode & 0x7FFFFFFF) % tabela.length;
    }

    public void inserir(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null) return;

        String cpfNormalizado = normalizarCpf(cliente.getCpf());
        if (cpfNormalizado.isEmpty()) return;

        int indice = calcularIndice(cpfNormalizado);
        NoHash novoNo = new NoHash(cpfNormalizado, cliente);

        if (tabela[indice] == null) {
            tabela[indice] = novoNo;
        } else {
            NoHash atual = tabela[indice];
            while (true) {
                if (atual.chaveCpf.equals(cpfNormalizado)) {
                    atual.cliente = cliente;
                    return;
                }
                if (atual.proximo == null) {
                    break;
                }
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
    }

    public ResultadoBuscaCliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return new ResultadoBuscaCliente(null, 0, 0);
        }

        long tempoInicio = System.nanoTime();
        int comparacoes = 0;

        String cpfNormalizado = normalizarCpf(cpf);
        int indice = calcularIndice(cpfNormalizado);

        System.out.println("[DEBUG HASH] Buscando CPF normalizado: '" + cpfNormalizado + "' no índice da tabela: " + indice);

        NoHash atual = tabela[indice];
        while (atual != null) {
            comparacoes++;
            if (atual.chaveCpf.equals(cpfNormalizado)) {
                long tempoDecorrido = System.nanoTime() - tempoInicio;
                return new ResultadoBuscaCliente(atual.cliente, tempoDecorrido, comparacoes);
            }
            atual = atual.proximo;
        }

        long tempoDecorrido = System.nanoTime() - tempoInicio;
        return new ResultadoBuscaCliente(null, tempoDecorrido, comparacoes);
    }

}
