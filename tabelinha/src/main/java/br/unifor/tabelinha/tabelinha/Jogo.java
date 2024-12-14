package br.unifor.tabelinha.tabelinha;

import java.util.Random;

public class Jogo  {
    private TimePrincipal time1;
    private TimePrincipal time2;
    private int golsTime1;
    private int golsTime2;

    public Jogo(TimePrincipal time1, TimePrincipal time2) {
        this.time1 = time1;
        this.time2 = time2;
        this.golsTime1 = 0; // Inicializa os gols como 0
        this.golsTime2 = 0; // Inicializa os gols como 0
    }

    /* public void definirResultado(int golsTime1, int golsTime2) {
        this.golsTime1 = golsTime1;
        this.golsTime2 = golsTime2;

        // Adiciona os gols feitos e sofridos para os dois times
        time1.adicionarGolsFeitos(golsTime1);
        time1.adicionarGolsSofridos(golsTime2);
        time2.adicionarGolsFeitos(golsTime2);
        time2.adicionarGolsSofridos(golsTime1);
    }*/

    public void jogar() {
        Random aleatorio = new Random();
        // Gera um placar aleatório para o jogo, por exemplo, de 0 a 5 gols para cada time
        this.golsTime1 = aleatorio.nextInt(6);
        this.golsTime2 = aleatorio.nextInt(6);

        // Atualiza os gols feitos pelos times
        time1.adicionarGolsFeitos(golsTime1);
        time1.adicionarGolsSofridos(golsTime2);
        time2.adicionarGolsFeitos(golsTime2);
        time2.adicionarGolsSofridos(golsTime1);
    }

    // Métodos para obter os times e os resultados
    public TimePrincipal getTime1() {
        return time1;
    }

    public TimePrincipal getTime2() {
        return time2;
    }

    public int getGolsTime1() {
        return golsTime1;
    }

    public int getGolsTime2() {
        return golsTime2;
    }
}