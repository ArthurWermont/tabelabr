package Tabelinha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;//operar em coleções (como listas, conjuntos, filas, etc.)


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


    // Construtor da classe
    public CampeonatoApp(TabelaCampeonato tabelaCampeonato) {
        this.tabelaCampeonato = tabelaCampeonato;
        this.rodadas = tabelaCampeonato.getRodadas();
        this.rodadaAtual = 0;

        setTitle("Tabela do Campeonato Brasileiro");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo da tabela para exibição dos jogos
        modeloTabela = new DefaultTableModel(new Object[]{"Time Casa", "Placar", "Time Visitante"}, 0);
        tabelaJogos = new JTable(modeloTabela);

        // Alterando a fonte e altura das linhas
        tabelaJogos.setFont(new Font("Arial", Font.PLAIN, 20));
        tabelaJogos.setRowHeight(65);
        tabelaJogos.setRowMargin(5);


        JScrollPane scrollPane = new JScrollPane(tabelaJogos);
        add(scrollPane, BorderLayout.CENTER);

        // Cores para a tabela de jogos
        tabelaJogos.setBackground(Cores.FUNDO_TABELA);
        tabelaJogos.setForeground(Cores.TEXTO_TABELA);
        scrollPane.getViewport().setBackground(Cores.FUNDO_SCROLL);

        // Renderer de linhas alternadas e centralizadas
        LinhaAlternadaCentralizadaRenderer rendererCentralizadoAlternado = new LinhaAlternadaCentralizadaRenderer();
        for (int i = 0; i < tabelaJogos.getColumnCount(); i++) {
            tabelaJogos.getColumnModel().getColumn(i).setCellRenderer(rendererCentralizadoAlternado);
        }


        // Painel de controle na parte inferior
        JPanel painelControle = new JPanel(new FlowLayout());
        labelRodadaAtual = new JLabel("Rodada: 1");
        labelRodadaAtual.setForeground(Cores.TEXTO_LABEL);

        botaoProximaRodada = new JButton("Próxima Rodada");
        botaoProximaRodada.setForeground(Cores.TEXTO_BOTAO);
        botaoProximaRodada.setBackground(Cores.FUNDO_BOTAO);

        botaoResultadosAnteriores = new JButton("Ver Resultados Anteriores");
        botaoResultadosAnteriores.setForeground(Cores.TEXTO_BOTAO);
        botaoResultadosAnteriores.setBackground(Cores.FUNDO_BOTAO);

        botaoTabelaClassificacao = new JButton("Ver Tabela de Classificação");
        botaoTabelaClassificacao.setForeground(Cores.TEXTO_BOTAO);
        botaoTabelaClassificacao.setBackground(Cores.FUNDO_BOTAO);

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

                    if (rodadaAtual == 37) {  // Rodada 38 (índice começa em 0, então 37 é a rodada 38)
                        salvarTabelaCSV();  // Salva a tabela final
                        salvarRodadasCSV();  // Salva os resultados de todas as rodadas
                    }
                    // Salva os resultados da rodada no arquivo CSV

                    // Verifica a rodada atual e ajusta o estado do botão
                    if (rodadaAtual == 37) {
                        // Desativa o botão na rodada 38 (índice 37)
                        botaoProximaRodada.setEnabled(false);
                    }
                    if(rodadaAtual >=1){
                        botaoResultadosAnteriores.setEnabled(true);
                    }
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
                }
                if (rodadaAtual == 0){
                    botaoResultadosAnteriores.setEnabled(false);
                }
                if(rodadaAtual<=36){
                    botaoProximaRodada.setEnabled(true);
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

    public void salvarRodadasCSV() {
            String nomeArquivo = "campeonatoBrasileiro.csv";
            try (FileWriter file = new FileWriter(nomeArquivo, true); // 'true' para adicionar ao arquivo existente
                 PrintWriter writer = new PrintWriter(file)) {


                // Itera por todas as rodadas
                for (int i = 0; i < rodadas.size(); i++) {
                    ArrayList<Jogo> jogosDaRodada = rodadas.get(i);

                    // Escrever título para a rodada (Rodada 1, Rodada 2, ...)
                    writer.println("Rodada " + (i + 1));  // "Rodada 1", "Rodada 2", etc.
                    //writer.println(); // Linha em branco para separar as rodadas
                    writer.println("Time Casa,Placar,Time Visitante"); // Cabeçalho para os jogos da rodada

                    // Adicionar os jogos da rodada
                    for (Jogo jogo : jogosDaRodada) {
                        String linha = jogo.getTime1().getNome() + "," +
                                jogo.getGolsTime1() + " X " + jogo.getGolsTime2() + "," +
                                jogo.getTime2().getNome();
                        writer.println(linha);
                    }

                    writer.println(); // Linha em branco para separar as rodadas
                }

            } catch (IOException e) {
                //JOptionPane.showMessageDialog(null, "Erro ao salvar as rodadas no arquivo CSV: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }

    public void salvarTabelaCSV() {

            // Nome do arquivo CSV
            String nomeArquivo = "campeonatoBrasileiro.csv";

            try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
                // Cabeçalho do CSV
                writer.println("Posição,Time,Pontos,Gols Feitos,Gols Sofridos,Saldo de Gols");

                // Obter lista de times ordenados
                ArrayList<TimePrincipal> times = tabelaCampeonato.getTimes();
                Collections.sort(times, TimePrincipal.compararPorDesempate);

                // Adicionar dados dos times no CSV
                for (int i = 0; i < times.size(); i++) {
                    TimePrincipal time = times.get(i);

                    // Formatar a linha do CSV
                    String linha = i + 1 + "," +
                            time.getNome() + "," +
                            time.getPontos() + "," +
                            time.getGolsFeitos() + "," +
                            time.getGolsSofridos() + "," +
                            time.getSaldoGols();

                    // Escrever a linha no arquivo
                    writer.println(linha);
                    writer.println();
                }

                // Mensagem de sucesso
                JOptionPane.showMessageDialog(null, "Campeonato salvo com sucesso em " + nomeArquivo, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                // Caso haja erro ao salvar o arquivo
                JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo CSV: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }

    void executarRodadaAtual() {
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
                ImageIcon logoTimeCasa = jogo.getTime1().getLogo(); // Logo do time da casa
                ImageIcon logoTimeVisitante = jogo.getTime2().getLogo(); // Logo do time visitante
                String placar = jogo.getGolsTime1() + "  X  " + jogo.getGolsTime2();

                // Criar os valores para as células
                JLabel labelTimeCasa = new JLabel(jogo.getTime1().getNome());
                labelTimeCasa.setIcon(logoTimeCasa);
                labelTimeCasa.setHorizontalTextPosition(JLabel.RIGHT); // Nome à direita da imagem

                //TROCAR O LUGAR DA LOGO
                JLabel labelTimeVisitante = new JLabel(jogo.getTime2().getNome());
                labelTimeVisitante.setIcon(logoTimeVisitante);
                labelTimeVisitante.setHorizontalTextPosition(JLabel.LEFT); // Nome à direita da imagem

                // Adiciona a linha na tabela com as células formatadas corretamente
                modeloTabela.addRow(new Object[]{
                        labelTimeCasa, // JLabel com o nome e logo do time da casa
                        placar,
                        labelTimeVisitante // JLabel com o nome e logo do time visitante
                });
            }
        }
    }

    public void exibirTabelaClassificacao() {
        JFrame tabelaFrame = new JFrame("Tabela de Classificação");
        tabelaFrame.setSize(1000, 500);
        tabelaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DefaultTableModel modeloClassificacao = new DefaultTableModel(new Object[]{
                "Posição", "Time", "Pontos", "Gols Feitos", "Gols Sofridos", "Saldo de Gols"}, 0);
        JTable tabelaClassificacao = new JTable(modeloClassificacao);
        JScrollPane scrollPane = new JScrollPane(tabelaClassificacao);
        tabelaFrame.add(scrollPane, BorderLayout.CENTER);
        tabelaClassificacao.setFont(new Font("Arial", Font.BOLD, 20));
        tabelaClassificacao.setRowHeight(65);
        tabelaClassificacao.setRowMargin(5);


        // Aplicar o renderizador para centralizar e alternar as cores das linhas
        LinhaAlternadaCentralizadaRenderer rendererCentralizadoAlternado2 = new LinhaAlternadaCentralizadaRenderer();
        for (int i = 0; i < tabelaClassificacao.getColumnCount(); i++) {
            tabelaClassificacao.getColumnModel().getColumn(i).setCellRenderer(rendererCentralizadoAlternado2);
        }

        ArrayList<TimePrincipal> times = tabelaCampeonato.getTimes();
        Collections.sort(times, TimePrincipal.compararPorDesempate);

        for (int i = 0; i < times.size(); i++) {
            TimePrincipal time = times.get(i);

            ImageIcon logoTime = time.getLogo(); // Supondo que o método getLogo retorna uma ImageIcon
            JLabel labelTime = new JLabel(time.getNome());
            labelTime.setIcon(logoTime);
            labelTime.setHorizontalTextPosition(JLabel.RIGHT); // Nome à direita da logo
            labelTime.setVerticalAlignment(SwingConstants.CENTER); // Centraliza verticalmente

            // Adicionar a linha na tabela com o nome e logo do time
            modeloClassificacao.addRow(new Object[]{
                    i + 1,                 // Posição
                    labelTime,             // JLabel com a logo e nome do time
                    time.getPontos(),      // Pontos
                    time.getGolsFeitos(),  // Gols Feitos
                    time.getGolsSofridos(),// Gols Sofridos
                    time.getSaldoGols()    // Saldo de Gols
            });
        }

        tabelaFrame.setVisible(true);
    }
}
