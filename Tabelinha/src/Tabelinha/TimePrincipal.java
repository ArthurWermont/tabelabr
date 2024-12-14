
package Tabelinha;

import javax.swing.*;
import java.util.Comparator;

public class TimePrincipal extends Time implements Competicao {
    private String estado;

    // Construtor
    public TimePrincipal(String nome, String estado, ImageIcon logo) {
        super(nome, logo); // Chama o construtor da classe pai (Time)
        this.estado = estado;

    }

    // Adiciona gols feitos
    public void adicionarGolsFeitos(int gols) {
        setGolsFeitos(getGolsFeitos() + gols);
        calcularSaldoGols();
    }

    // Adiciona gols sofridos
    public void adicionarGolsSofridos(int gols) {
        setGolsSofridos(getGolsSofridos() + gols);
        calcularSaldoGols();
    }

    // Método para comparar times (usado no método de ordenação)
    public static Comparator<Time> compararPorDesempate = new Comparator<Time>() {
        @Override
        public int compare(Time time1, Time time2) {
            // Comparando primeiro pelos pontos
            if (time1.getPontos() != time2.getPontos()) {
                return time2.getPontos() - time1.getPontos(); // Ordem decrescente
            }

            // Se os pontos forem iguais, compara pelo saldo de gols
            if (time1.getSaldoGols() != time2.getSaldoGols()) {
                return time2.getSaldoGols() - time1.getSaldoGols(); // Ordem decrescente
            }

            // Se saldo de gols também for igual, compara pelos gols feitos
            return time2.getGolsFeitos() - time1.getGolsFeitos(); // Ordem decrescente
        }
    };

    // Método para calcular o saldo de gols
    public void calcularSaldoGols() {
        setSaldoGols(getGolsFeitos() - getGolsSofridos());
    }

    // Atualiza as estatísticas do time com base no resultado do jogo
    public void atualizarClassificacao(int golsFeitos, int golsSofridos) {
        if (golsFeitos > golsSofridos) {
            setPontos(getPontos() + 3); // Vitória
        } else if (golsFeitos == golsSofridos) {
            setPontos(getPontos() + 1); // Empate
        }
        // Se perder, não adiciona pontos.
        calcularSaldoGols(); // Atualiza o saldo de gols com base na diferença entre gols feitos e sofridos
    }

    public String getEstado() {
        return estado;
    }
}

