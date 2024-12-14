package Tabelinha;

public class Main {
    public static void main(String[] args) {
        // Cria a instância de TabelaCampeonato
        TabelaCampeonato tabelaCampeonato = new TabelaCampeonato();

        // Cria a instância da interface
        CampeonatoApp app = new CampeonatoApp(tabelaCampeonato);

        // Torna a interface visível
        app.setVisible(true);

    }
}
