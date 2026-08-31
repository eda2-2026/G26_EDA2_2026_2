package br.edu.cafeteria.modelo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class CatalogoClientes {

    public static final int QUANTIDADE_CLIENTES = 20_000;

    private static final String[] NOMES_IBGE = {
        "Maria", "Jose", "Ana", "Joao", "Antonio", "Francisco", "Pedro", "Carlos",
        "Lucas", "Luiz", "Paulo", "Gabriel", "Marcos", "Davi", "Rafael", "Luis",
        "Daniel", "Miguel", "Gustavo", "Felipe", "Guilherme", "Francisca", "Eduardo",
        "Matheus", "Julia", "Bruno", "Marcelo", "Arthur", "Leonardo", "Rodrigo"
    };

    private static final String[] SOBRENOMES_IBGE = {
        "Silva", "Santos", "Oliveira", "Souza", "Pereira", "Ferreira", "Lima", "Alves",
        "Rodrigues", "Costa", "Sousa", "Gomes", "Nascimento", "Araujo", "Ribeiro",
        "Almeida", "Jesus", "Barbosa", "Soares", "Carvalho", "Martins", "Lopes", "Vieira",
        "Rocha", "Dias", "Goncalves", "Fernandes", "Santana", "Andrade", "Batista"
    };

    private CatalogoClientes() {
    }

    public static Cliente[] criarClientesOrdenados() {
        Cliente[] clientes = new Cliente[QUANTIDADE_CLIENTES];
        int indice = 0;

        for (String primeiroNome : NOMES_IBGE) {
            for (String segundoNome : NOMES_IBGE) {
                if (primeiroNome.equals(segundoNome)) {
                    continue;
                }
                for (String sobrenome : SOBRENOMES_IBGE) {
                    if (indice == clientes.length) {
                        break;
                    }
                    int numero = indice + 1;
                    String nome = primeiroNome + " " + segundoNome + " " + sobrenome;
                    String cpf = criarCpfFicticio(numero);
                    clientes[indice] = numero % 10 == 0 ? vip(nome, cpf) : comum(nome, cpf);
                    indice++;
                }
            }
        }

        if (indice != clientes.length) {
            throw new IllegalStateException("Não foi possível gerar os 20.000 clientes.");
        }

        Arrays.sort(
                clientes,
                Comparator.comparing(Cliente::getNome, String.CASE_INSENSITIVE_ORDER));

        validarCatalogo(clientes);
        return clientes;
    }

    private static Cliente comum(String nome, String cpf) {
        return new ClienteComum(nome, cpf);
    }

    private static Cliente vip(String nome, String cpf) {
        return new ClienteVIP(nome, cpf);
    }

    private static String criarCpfFicticio(int numero) {
        String digitos = String.format(Locale.ROOT, "%011d", 90_000_000_000L + numero);
        return digitos.substring(0, 3) + "."
                + digitos.substring(3, 6) + "."
                + digitos.substring(6, 9) + "-"
                + digitos.substring(9, 11);
    }

    private static void validarCatalogo(Cliente[] clientes) {
        if (clientes.length != QUANTIDADE_CLIENTES) {
            throw new IllegalStateException(
                    "O catálogo deve possuir exatamente " + QUANTIDADE_CLIENTES + " clientes.");
        }

        Set<String> cpfs = new HashSet<>();
        for (int i = 1; i < clientes.length; i++) {
            String anterior = clientes[i - 1].getNome();
            String atual = clientes[i].getNome();
            if (anterior.compareToIgnoreCase(atual) >= 0) {
                throw new IllegalStateException(
                        "Catálogo fora de ordem alfabética entre: " + anterior + " e " + atual);
            }
        }

        for (Cliente cliente : clientes) {
            if (!cpfs.add(cliente.getCpf())) {
                throw new IllegalStateException("CPF duplicado no catálogo: " + cliente.getCpf());
            }
        }
    }
}
