package br.unifor.tabelinha.tabelinha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // Cria a instância de TabelaCampeonato
        TabelaCampeonato tabelaCampeonato = new TabelaCampeonato();

        // Cria a instância da interface gráfica
        CampeonatoApp app = new CampeonatoApp(tabelaCampeonato);

        // Torna a interface visível
        app.setVisible(true);
		SpringApplication.run(Main.class, args);

    }
}
