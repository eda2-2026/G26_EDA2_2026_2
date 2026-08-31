package br.edu.cafeteria.modelo;

public class ClienteComum extends Cliente {

    public ClienteComum(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public void acumularXP(double valorGasto) {
        if (valorGasto > 0) {
            int pontos = (int) (valorGasto * PONTOS_POR_REAL);
            adicionarXP(pontos);
        }
    }
}
