package Tabelinha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
public class CampeonatoApp extends JFrame {
    private JTable tabelaJogos;
    private DefaultTableModel modeloTabela;
    private JButton botaoProximaRodada;
    private JButton botaoResultadosAnteriores;
    private JButton botaoTabelaClassificacao;
    private JLabel labelRodadaAtual;
    private ArrayList<ArrayList<Jogo>> rodadas;
    private int rodadaAtual;
    private TabelaCampeonato tabelaCampeonato;


    public CampeonatoApp(TabelaCampeonato tabelaCampeonato) {
        this.tabelaCampeonato = tabelaCampeonato;
        this.rodadas = tabelaCampeonato.getRodadas();
        this.rodadaAtual = 0;

        setTitle("Tabela do Campeonato Brasileiro");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);//tela fica no meio quando o programa é inicializado
        setResizable(false);//nao diminui nem almenta o tamanho da tela

        modeloTabela = new DefaultTableModel(new Object[]{"Time Casa", "Placar", "Time Visitante"}, 0);
        tabelaJogos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaJogos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelControle = new JPanel();
        labelRodadaAtual = new JLabel("Rodada: 1");
        botaoProximaRodada = new JButton("Próxima Rodada");
        botaoResultadosAnteriores = new JButton("Ver Resultados Anteriores");
        botaoTabelaClassificacao = new JButton("Ver Tabela de Classificação");

        painelControle.add(labelRodadaAtual);
        painelControle.add(botaoProximaRodada);
        painelControle.add(botaoResultadosAnteriores);
        painelControle.add(botaoTabelaClassificacao);

        add(painelControle, BorderLayout.SOUTH);

        // Ação para o botão "Próxima Rodada"
        botaoProximaRodada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rodadaAtual < rodadas.size() - 1) {
                    rodadaAtual++;
                    executarRodadaAtual();
                    atualizarTabela();
                    labelRodadaAtual.setText("Rodada: " + (rodadaAtual + 1));
                } else {
                    JOptionPane.showMessageDialog(null, "Você já está na última rodada.");
                }
            }
        });

        // Ação para o botão "Resultados Anteriores"
        botaoResultadosAnteriores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rodadaAtual > 0) {
                    rodadaAtual--;
                    atualizarTabela();
                    labelRodadaAtual.setText("Rodada: " + (rodadaAtual + 1));
                } else {
                    JOptionPane.showMessageDialog(null, "Esta é a primeira rodada.");
                }
            }
        });

        // Ação para o botão "Tabela de Classificação"
        botaoTabelaClassificacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTabelaClassificacao();
            }
        });

        executarRodadaAtual();
        atualizarTabela();
    }

    private void executarRodadaAtual() {
        if (rodadas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "As rodadas ainda não foram geradas.");
            return; // Retorna sem tentar acessar a lista vazia
        }

        ArrayList<Jogo> jogosDaRodada = rodadas.get(rodadaAtual);
        for (Jogo jogo : jogosDaRodada) {
            jogo.jogar();
            jogo.getTime1().atualizarClassificacao(jogo.getGolsTime1(), jogo.getGolsTime2());
            jogo.getTime2().atualizarClassificacao(jogo.getGolsTime2(), jogo.getGolsTime1());
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        if (rodadas.size() > 0) {
            ArrayList<Jogo> jogosDaRodada = rodadas.get(rodadaAtual);
            for (Jogo jogo : jogosDaRodada) {
                modeloTabela.addRow(new Object[]{
                        jogo.getTime1().getNome(),
                        jogo.getGolsTime1() + " - " + jogo.getGolsTime2(),
                        jogo.getTime2().getNome()
                });
            }
        }
    }

    private void exibirTabelaClassificacao() {
        JFrame tabelaFrame = new JFrame("Tabela de Classificação");
        tabelaFrame.setSize(500, 400);
        tabelaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel modeloClassificacao = new DefaultTableModel(new Object[]{
                "Posição", "Time", "Pontos", "Gols Feitos", "Gols Sofridos", "Saldo de Gols"}, 0);
        JTable tabelaClassificacao = new JTable(modeloClassificacao);
        JScrollPane scrollPane = new JScrollPane(tabelaClassificacao);
        tabelaFrame.add(scrollPane, BorderLayout.CENTER);

        ArrayList<TimePrincipal> times = tabelaCampeonato.getTimes();
        // Ordena a lista de times utilizando o seu Comparator
        Collections.sort(times, TimePrincipal.compararPorDesempate);

        // Preencher a tabela com os dados dos times
        for (int i = 0; i < times.size(); i++) {
            TimePrincipal time = times.get(i);
            modeloClassificacao.addRow(new Object[]{
                    i + 1,
                    time.getNome(),
                    time.getPontos(),
                    time.getGolsFeitos(),
                    time.getGolsSofridos(),
                    time.getSaldoGols()
            });
        }

        tabelaFrame.setVisible(true);
    }

    public static void main(String[] args) {
        TabelaCampeonato tabelaCampeonato = new TabelaCampeonato();
        CampeonatoApp app = new CampeonatoApp(tabelaCampeonato);
        app.setVisible(true);
    }
}
