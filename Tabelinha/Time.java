package Tabelinha;

public abstract class Time {
    private String nome;
    private int pontos;
    private int saldoGols;
    private int golsFeitos;
    private int golsSofridos;

    // Construtor
    public Time(String nome) {
        this.nome = nome;
        this.pontos = 0;
        this.saldoGols = 0;
        this.golsFeitos = 0;
        this.golsSofridos = 0;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getSaldoGols() {
        return saldoGols;
    }

    public void setSaldoGols(int saldoGols) {
        this.saldoGols = saldoGols;
    }

    public int getGolsFeitos() {
        return golsFeitos;
    }

    public void setGolsFeitos(int golsFeitos) {
        this.golsFeitos = golsFeitos;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public void setGolsSofridos(int golsSofridos) {
        this.golsSofridos = golsSofridos;
    }
}
