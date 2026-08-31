package br.edu.cafeteria.modelo;

public final class CatalogoProdutos {

    public static final int QUANTIDADE_PRODUTOS = 50;

    private CatalogoProdutos() {
    }

    public static Product[] criarCardapioOrdenado() {
        Product[] produtos = {
            bebida("Affogato Arcano", "P001", 13.90, 20, 120, Tamanho.P),
            bebida("Agua de Atlantis", "P002", 6.00, 40, 0, Tamanho.M),
            comida("Alfajor de Azeroth", "P003", 9.50, 18, 3, false, true),
            comida("Baguete do Bardo", "P004", 16.90, 15, 8, false, true),
            comida("Brownie do Batman", "P005", 11.00, 22, 4, false, true),
            bebida("Bubble Tea Bulbasaur", "P006", 15.50, 16, 45, Tamanho.G),
            bebida("Cafe Cyberpunk", "P007", 10.50, 30, 180, Tamanho.M),
            bebida("Cafe do Programador", "P008", 9.50, 35, 200, Tamanho.P),
            bebida("Cappuccino Cosmico", "P009", 14.00, 24, 110, Tamanho.M),
            bebida("Chai da Chun-Li", "P010", 13.00, 18, 35, Tamanho.M),
            comida("Cheesecake da Zelda", "P011", 15.90, 12, 5, false, true),
            bebida("Chocolate Kryptonita", "P012", 12.50, 20, 5, Tamanho.M),
            comida("Cookie do Cookie Monster", "P013", 8.50, 28, 3, false, true),
            comida("Croissant do Coringa", "P014", 10.90, 17, 6, false, true),
            comida("Donut do Dexter", "P015", 9.90, 25, 3, false, true),
            bebida("Espresso Excalibur", "P016", 8.00, 32, 150, Tamanho.P),
            bebida("Frappe do Flash", "P017", 16.50, 14, 95, Tamanho.G),
            comida("Gelato do Gandalf", "P018", 12.00, 16, 2, true, false),
            comida("Hamburguer Hobbit", "P019", 21.90, 14, 12, false, true),
            bebida("Iced Coffee Iron Man", "P020", 14.90, 19, 175, Tamanho.G),
            bebida("Juice Jedi", "P021", 11.50, 20, 0, Tamanho.M),
            comida("Kibe Kratos", "P022", 8.90, 26, 5, false, true),
            bebida("Latte Jedi", "P023", 13.50, 23, 100, Tamanho.M),
            comida("Lembas Bread", "P024", 8.00, 30, 5, true, false),
            comida("Macaron da Matrix", "P025", 10.00, 24, 4, false, true),
            bebida("Milkshake Mario", "P026", 17.90, 13, 0, Tamanho.G),
            bebida("Mocha Mandaloriano", "P027", 15.00, 18, 130, Tamanho.M),
            comida("Muffin Minecraft", "P028", 10.50, 21, 4, false, true),
            comida("Nachos de Naboo", "P029", 18.90, 15, 9, true, false),
            comida("Omelete de One Piece", "P030", 17.50, 12, 10, false, false),
            comida("Panini Pikachu", "P031", 16.00, 18, 8, false, true),
            comida("Pao de Queijo Portal", "P032", 7.50, 35, 4, false, false),
            bebida("Pocao de Mana", "P033", 12.00, 25, 0, Tamanho.M),
            comida("Pretzel Pac-Man", "P034", 9.00, 20, 4, true, true),
            comida("Quiche Quasar", "P035", 14.90, 15, 7, false, true),
            comida("Ramen de Rivia", "P036", 22.50, 10, 14, false, true),
            comida("Risoto de Rivendell", "P037", 23.90, 11, 15, true, false),
            comida("Sanduiche Stark", "P038", 18.00, 17, 8, false, true),
            bebida("Smoothie Sonic", "P039", 14.50, 16, 0, Tamanho.G),
            bebida("Soda Space Invaders", "P040", 9.00, 28, 0, Tamanho.M),
            comida("Sundae Superman", "P041", 13.90, 17, 3, false, false),
            comida("Tapioca Tatooine", "P042", 12.90, 20, 7, true, false),
            comida("Torta Tardis", "P043", 15.50, 14, 5, false, true),
            comida("Trufa Tron", "P044", 6.50, 32, 2, true, false),
            comida("Udon do Universo", "P045", 21.00, 12, 13, true, true),
            bebida("Vitamina Vulcan", "P046", 13.00, 18, 0, Tamanho.G),
            comida("Waffle Wakanda", "P047", 14.00, 19, 6, false, true),
            comida("X-Burger X-Men", "P048", 22.90, 13, 12, false, true),
            comida("Yakisoba Yoda", "P049", 20.50, 11, 14, true, true),
            comida("Zuppa Zerg", "P050", 18.50, 12, 11, true, false)
        };

        validarCardapio(produtos);
        return produtos;
    }

    private static Bebida bebida(String nome, String codigo, double preco, int estoque,
                                  double cafeina, Tamanho tamanho) {
        return new Bebida(nome, codigo, preco, estoque, cafeina, tamanho);
    }

    private static Comida comida(String nome, String codigo, double preco, int estoque,
                                  int preparo, boolean vegano, boolean gluten) {
        return new Comida(nome, codigo, preco, estoque, preparo, vegano, gluten);
    }

    private static void validarCardapio(Product[] produtos) {
        if (produtos.length != QUANTIDADE_PRODUTOS) {
            throw new IllegalStateException("O cardapio deve possuir exatamente 50 produtos.");
        }

        for (int i = 1; i < produtos.length; i++) {
            String anterior = produtos[i - 1].getNome();
            String atual = produtos[i].getNome();
            if (anterior.compareToIgnoreCase(atual) >= 0) {
                throw new IllegalStateException(
                        "Cardapio fora de ordem alfabetica entre: " + anterior + " e " + atual);
            }
        }
    }
}
