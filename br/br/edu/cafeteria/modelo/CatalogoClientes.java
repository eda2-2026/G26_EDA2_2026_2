package br.edu.cafeteria.modelo;

public final class CatalogoClientes {

    public static final int QUANTIDADE_CLIENTES = 70;

    private CatalogoClientes() {
    }

    public static Cliente[] criarClientesOrdenados() {
        Cliente[] clientes = {
            comum("Ada Lovelace", "001.000.000-01"),
            vip("Alan Turing", "002.000.000-02"),
            comum("Albert Einstein", "003.000.000-03"),
            comum("Alfred Hitchcock", "004.000.000-04"),
            vip("Anakin Skywalker", "005.000.000-05"),
            comum("Arthur Dent", "006.000.000-06"),
            comum("Arya Stark", "007.000.000-07"),
            vip("Barack Obama", "008.000.000-08"),
            comum("Bilbo Baggins", "009.000.000-09"),
            vip("Bruce Wayne", "010.000.000-10"),
            comum("Carl Sagan", "011.000.000-11"),
            comum("Charles Darwin", "012.000.000-12"),
            vip("Clark Kent", "013.000.000-13"),
            comum("Daenerys Targaryen", "014.000.000-14"),
            vip("Darth Vader", "016.000.000-16"),
            comum("Diana Prince", "015.000.000-15"),
            comum("Elena Ferrante", "017.000.000-17"),
            comum("Elliot Alderson", "018.000.000-18"),
            vip("Elon Musk", "019.000.000-19"),
            comum("Frodo Baggins", "020.000.000-20"),
            vip("Geralt de Rivia", "021.000.000-21"),
            comum("Grace Hopper", "022.000.000-22"),
            comum("Harry Potter", "023.000.000-23"),
            vip("Hermione Granger", "024.000.000-24"),
            comum("Indiana Jones", "026.000.000-26"),
            vip("Iron Man", "027.000.000-27"),
            comum("Isaac Newton", "025.000.000-25"),
            comum("Jack Sparrow", "028.000.000-28"),
            comum("James Bond", "029.000.000-29"),
            comum("Jane Austen", "030.000.000-30"),
            vip("Jon Snow", "031.000.000-31"),
            comum("Katniss Everdeen", "032.000.000-32"),
            comum("Kiki Delivery", "033.000.000-33"),
            vip("Lara Croft", "034.000.000-34"),
            comum("Leia Organa", "035.000.000-35"),
            comum("Linus Torvalds", "036.000.000-36"),
            vip("Luke Skywalker", "037.000.000-37"),
            comum("Margaret Hamilton", "038.000.000-38"),
            comum("Marie Curie", "039.000.000-39"),
            vip("Marty McFly", "040.000.000-40"),
            comum("Michael Scott", "041.000.000-41"),
            comum("Mulan Hua", "042.000.000-42"),
            vip("Neo Anderson", "043.000.000-43"),
            comum("Nikola Tesla", "044.000.000-44"),
            comum("Norman Bates", "045.000.000-45"),
            vip("Oprah Winfrey", "046.000.000-46"),
            comum("Paul Atreides", "047.000.000-47"),
            comum("Peter Parker", "048.000.000-48"),
            vip("Princess Peach", "049.000.000-49"),
            comum("Ragnar Lothbrok", "050.000.000-50"),
            comum("Rick Sanchez", "051.000.000-51"),
            vip("Ron Weasley", "052.000.000-52"),
            comum("Samwise Gamgee", "053.000.000-53"),
            comum("San Mononoke", "054.000.000-54"),
            vip("Sarah Connor", "055.000.000-55"),
            comum("Sherlock Holmes", "056.000.000-56"),
            comum("Spock Vulcan", "057.000.000-57"),
            vip("Stephen Hawking", "058.000.000-58"),
            comum("Steve Rogers", "059.000.000-59"),
            comum("Tifa Lockhart", "060.000.000-60"),
            vip("Tony Stark", "061.000.000-61"),
            comum("Tracer Lena", "062.000.000-62"),
            comum("Usagi Tsukino", "063.000.000-63"),
            vip("Victor Frankenstein", "064.000.000-64"),
            comum("Vito Corleone", "065.000.000-65"),
            comum("Wanda Maximoff", "066.000.000-66"),
            vip("Wednesday Addams", "067.000.000-67"),
            comum("Willy Wonka", "068.000.000-68"),
            comum("Yoda Master", "069.000.000-69"),
            vip("Zelda Hyrule", "070.000.000-70")
        };

        validarCatalogo(clientes);
        return clientes;
    }

    private static Cliente comum(String nome, String cpf) {
        return new ClienteComum(nome, cpf);
    }

    private static Cliente vip(String nome, String cpf) {
        return new ClienteVIP(nome, cpf);
    }

    private static void validarCatalogo(Cliente[] clientes) {
        if (clientes.length != QUANTIDADE_CLIENTES) {
            throw new IllegalStateException("O catálogo deve possuir exatamente 70 clientes.");
        }

        for (int i = 1; i < clientes.length; i++) {
            String anterior = clientes[i - 1].getNome();
            String atual = clientes[i].getNome();
            if (anterior.compareToIgnoreCase(atual) >= 0) {
                throw new IllegalStateException(
                        "Catálogo fora de ordem alfabética entre: " + anterior + " e " + atual);
            }
        }
    }
}
