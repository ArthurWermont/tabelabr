package Tabelinha;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabelaCampeonato implements Usuario {
    private ArrayList<TimePrincipal> times;
    private ArrayList<ArrayList<Jogo>> rodadas = new ArrayList<>(); // Inicializa a lista de rodadas

    public TabelaCampeonato() {
        this.times = new ArrayList<>();
        this.rodadas = new ArrayList<>();

        int resposta = JOptionPane.showConfirmDialog(null, "Deseja gerar os times automaticamente?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            inicializarTimes(); // Inicializa os times predefinidos
        } else {
            addPeloUsuario(); // Permite que o usuário adicione os times manualmente
        }
        try {
            gerarRodadas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar rodadas: " + e.getMessage());
        }
    }



    public void addPeloUsuario() {
        // times.clear();
        for (int time = 0; time < 20; time++) {
            //JOptionPane.showInputDialog("Digite o nome do time #" + (i + 1) + ": ");
            String nomeTime = JOptionPane.showInputDialog("Digite o nome do time #" + (time + 1) + ": ");
            times.add(new TimePrincipal(nomeTime, "Estado não definido"));
        }
    }

    public void inicializarTimes() {
        times.add(new TimePrincipal("Atlético Mineiro", "Minas Gerais"));
        times.add(new TimePrincipal("Atlético Paranaense (Athletico-PR)", "Paraná"));
        times.add(new TimePrincipal("Bahia", "Bahia"));
        times.add(new TimePrincipal("Botafogo", "Rio de Janeiro"));
        times.add(new TimePrincipal("Corinthians", "São Paulo"));
        times.add(new TimePrincipal("Coritiba", "Paraná"));
        times.add(new TimePrincipal("Cruzeiro", "Minas Gerais"));
        times.add(new TimePrincipal("Cuiabá", "Mato Grosso"));
        times.add(new TimePrincipal("Flamengo", "Rio de Janeiro"));
        times.add(new TimePrincipal("Fluminense", "Rio de Janeiro"));
        times.add(new TimePrincipal("Fortaleza", "Ceará"));
        times.add(new TimePrincipal("Goiás", "Goiás"));
        times.add(new TimePrincipal("Grêmio", "Rio Grande do Sul"));
        times.add(new TimePrincipal("Internacional", "Rio Grande do Sul"));
        times.add(new TimePrincipal("Palmeiras", "São Paulo"));
        times.add(new TimePrincipal("Red Bull Bragantino", "São Paulo"));
        times.add(new TimePrincipal("Santos", "São Paulo"));
        times.add(new TimePrincipal("São Paulo", "São Paulo"));
        times.add(new TimePrincipal("Vasco da Gama", "Rio de Janeiro"));
        times.add(new TimePrincipal("Ceará", "Ceará"));
    }


    public void gerarRodadas() {
        if (times.isEmpty()) {
            throw new IllegalStateException("Nenhum time disponível para gerar as rodadas.");
        }

        int numTimes = times.size();
        if (numTimes < 2) {
            throw new IllegalStateException("Número insuficiente de times para gerar rodadas.");
        }

        if (numTimes % 2 != 0) {
            throw new IllegalArgumentException("Número ímpar de times, não é possível gerar rodadas.");
        }

        if (!rodadas.isEmpty()) {
            throw new IllegalStateException("As rodadas já foram geradas.");
        }

        ArrayList<ArrayList<Jogo>> rodadasIda = criarRodadasIda();
        ArrayList<ArrayList<Jogo>> rodadasVolta = criarRodadasVolta(rodadasIda);
        rodadas.addAll(rodadasIda);
        rodadas.addAll(rodadasVolta);
    }

    private ArrayList<ArrayList<Jogo>> criarRodadasIda() {
        ArrayList<ArrayList<Jogo>> rodadasIda = new ArrayList<>();
        int numTimes = times.size();
        for (int i = 0; i < numTimes - 1; i++) {
            ArrayList<Jogo> jogosDaRodada = new ArrayList<>();
            for (int j = 0; j < numTimes / 2; j++) {
                int time1Idx = (i + j) % (numTimes - 1);
                int time2Idx = (numTimes - 1 - j + i) % (numTimes - 1);
                if (j == 0) time2Idx = numTimes - 1;

                jogosDaRodada.add(new Jogo(times.get(time1Idx), times.get(time2Idx)));
            }
            rodadasIda.add(jogosDaRodada);
        }
        return rodadasIda;
    }

    private ArrayList<ArrayList<Jogo>> criarRodadasVolta(ArrayList<ArrayList<Jogo>> rodadasIda) {
        ArrayList<ArrayList<Jogo>> rodadasVolta = new ArrayList<>();
        for (ArrayList<Jogo> rodadaIda : rodadasIda) {
            ArrayList<Jogo> jogosDaRodadaVolta = new ArrayList<>();
            for (Jogo jogoIda : rodadaIda) {
                jogosDaRodadaVolta.add(new Jogo(jogoIda.getTime2(), jogoIda.getTime1()));
            }
            rodadasVolta.add(jogosDaRodadaVolta);
        }
        return rodadasVolta;
    }

    // Exibir a tabela de classificação em uma interface gráfica com JTable
    public void exibirTabelaClassificacaoComJTable() {
        // Ordenar os times pela pontuação e saldo de gols
        times.sort(Comparator.comparingInt(TimePrincipal::getPontos)
                .thenComparingInt(TimePrincipal::getSaldoGols).reversed());

        // Configurar o modelo de dados da tabela
        String[] colunas = {"Posição", "Time", "Pontos", "Saldo de Gols"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        // Preencher a tabela com os dados dos times
        for (int i = 0; i < times.size(); i++) {
            TimePrincipal time = times.get(i);
            Object[] linha = {i + 1, time.getNome(), time.getPontos(), time.getSaldoGols()};
            modeloTabela.addRow(linha);
        }

        // Criar a JTable com o modelo de dados
        JTable tabelaClassificacao = new JTable(modeloTabela);
        tabelaClassificacao.setFillsViewportHeight(true);

        // Configurar o painel de exibição
        JFrame frame = new JFrame("Tabela de Classificação do Campeonato");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        // Adicionar a JTable ao painel de rolagem
        JScrollPane scrollPane = new JScrollPane(tabelaClassificacao);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Tornar a interface visível
        frame.setVisible(true);
    }



    public ArrayList<ArrayList<Jogo>> getRodadas() {
        return rodadas;
    }

    public ArrayList<TimePrincipal> getTimes() {
        return times;
    }
}