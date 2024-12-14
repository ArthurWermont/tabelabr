package br.unifor.tabelinha.tabelinha;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;


public class TabelaCampeonato implements Usuario {
    Scanner teclado = new Scanner(System.in);
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
            JOptionPane.showMessageDialog(null, "Erro ao gerar rodadas: " + e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // public void salvarRodadasExcel() {
    //     // definindo o nome do arquivo
    //     String nomeArquivo = "campeonato_brasileiro.xlsx";

    //     // criação de um objeto workbook do tipo XXSFWorkbook
    //     // representa o arquivo Excel
    //     try (Workbook workbook = new XSSFWorkbook()) {
    //         // Criar uma planilha para cada rodada
    //         // percorre todas as rodadas do campeonato (armazenadas em rodadas, uma lista de listas de jogos)
    //         // Para cada rodada, ele obtém a lista de jogos da rodada atual (jogosDaRodada)
    //         for (int i = 0; i < rodadas.size(); i++) {
    //             ArrayList<Jogo> jogosDaRodada = rodadas.get(i);

    //             // Para cada rodada, uma nova planilha é criada dentro do workbook
    //             // Nome da planilha: "Rodada 1", "Rodada 2", ...
    //             Sheet sheet = workbook.createSheet("Rodada " + (i + 1));

    //             // Criar o cabeçalho da rodada
    //             Row headerRow = sheet.createRow(0);
    //             headerRow.createCell(0).setCellValue("Time Casa");
    //             headerRow.createCell(1).setCellValue("Placar");
    //             headerRow.createCell(2).setCellValue("Time Visitante");

    //             // Preencher os dados dos jogos
    //             // Para cada jogo na rodada, o método cria uma nova linha abaixo do cabeçalho.
    //             for (int j = 0; j < jogosDaRodada.size(); j++) {
    //                 Jogo jogo = jogosDaRodada.get(j);
    //                 Row row = sheet.createRow(j + 1); // Linha começa na segunda linha

    //                 // Preenchendo as células
    //                 // Cada linha contém: O nome do time da casa (jogo.getTime1().getNome()).
    //                 // O placar do jogo, que é uma combinação dos gols do time da casa e do time visitante (jogo.getGolsTime1() + " - " + jogo.getGolsTime2()).
    //                 // O nome do time visitante (jogo.getTime2().getNome()).
    //                 row.createCell(0).setCellValue(jogo.getTime1().getNome());
    //                 row.createCell(1).setCellValue(jogo.getGolsTime1() + " - " + jogo.getGolsTime2());
    //                 row.createCell(2).setCellValue(jogo.getTime2().getNome());
    //             }

    //             // Ajustar largura das colunas para melhor exibição
    //             for (int col = 0; col < 3; col++) {
    //                 sheet.autoSizeColumn(col);
    //             }
    //         }

    //         // Salvar o arquivo Excel
    //         // O FileOutputStream é usado para escrever o conteúdo da planilha 
    //         // no arquivo definido anteriormente (nomeArquivo). 
    //         // Caso a operação seja bem-sucedida, uma mensagem de sucesso é exibida para o usuário.
    //         try (FileOutputStream fileOut = new FileOutputStream(nomeArquivo)) {
    //             workbook.write(fileOut);
    //             JOptionPane.showMessageDialog(null, "Arquivo Excel salvo com sucesso: " + nomeArquivo, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    //         }
    //         // caso ocorra algum erro durante a execução,
    //         // será exibida uma mensagem de erro e sua descrição
    //     } catch (IOException e) {
    //         JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo Excel: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    //     }
    // }


    public void addPeloUsuario() {
        // times.clear();
        for (int time = 0; time < 20; time++) {
            //JOptionPane.showInputDialog("Digite o nome do time #" + (i + 1) + ": ");
            String nomeTime = JOptionPane.showInputDialog( null,"Digite o nome do time #" + (time + 1) + ": "
                    ,"Times Personalizados",JOptionPane.INFORMATION_MESSAGE);
            // Verifica se o usuário clicou em "Cancelar" ou não digitou nada
            if (nomeTime == null) {
                JOptionPane.showMessageDialog(null, "Operação cancelada. O programa será encerrado.", "Cancelado",
                        JOptionPane.ERROR_MESSAGE);
                return; // Encerra o programa
            } else if (nomeTime.trim().isEmpty()) {
                // Verifica se o campo está vazio
                JOptionPane.showMessageDialog(null, "Você precisa inserir um nome para o time.", "Alerta!",
                        JOptionPane.WARNING_MESSAGE);
                time--;
            } else {
                times.add(new TimePrincipal(nomeTime, "Estado não definido",null));
            }
        }
    }

    public void inicializarTimes() {
        times.add(new TimePrincipal("Atlético Mineiro", "Minas Gerais", new ImageIcon("C:\\Users\\55859\\IdeaProjects\\Tabelinha\\Logos_Clubes\\Galo_Logo.png")));
        times.add(new TimePrincipal("Atlético Paranaense (Athletico-PR)", "Paraná", new ImageIcon("C:\\Users\\55859\\IdeaProjects\\Tabelinha\\Logos_Clubes\\CAP_Logo.png")));
        times.add(new TimePrincipal("Bahia", "Bahia" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\Tabelinha\\Logos_Clubes\\Bahia_Logo.png")));
        times.add(new TimePrincipal("Botafogo", "Rio de Janeiro" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\Tabelinha\\Logos_Clubes\\Fogo_Logo.png")));
        times.add(new TimePrincipal("Corinthians", "São Paulo" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\Tabelinha\\Logos_Clubes\\Curintia_Logo.png")));
        times.add(new TimePrincipal("Coritiba", "Paraná" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\Tabelinha\\Logos_Clubes\\Coxa_Logo.png")));
        times.add(new TimePrincipal("Cruzeiro", "Minas Gerais" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Cruzeiro_Logo.png")));
        times.add(new TimePrincipal("Cuiabá", "Mato Grosso" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Cuiaba_Logo.png")));
        times.add(new TimePrincipal("Flamengo", "Rio de Janeiro" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Flamengo_Logo.png")));
        times.add(new TimePrincipal("Fluminense", "Rio de Janeiro" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Fluminense_Logo.png")));
        times.add(new TimePrincipal("Fortaleza", "Ceará" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Laion_Logo.png")));
        times.add(new TimePrincipal("Goiás", "Goiás" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Goias_Logo.png")));
        times.add(new TimePrincipal("Grêmio", "Rio Grande do Sul" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Gremio_Logo.png")));
        times.add(new TimePrincipal("Internacional", "Rio Grande do Sul" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Inter_Logo.png")));
        times.add(new TimePrincipal("Palmeiras", "São Paulo" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Palmeiras_Logo.png")));
        times.add(new TimePrincipal("Red Bull Bragantino", "São Paulo" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\RBB_Logo.png")));
        times.add(new TimePrincipal("Santos", "São Paulo" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Santos_Logo.png")));
        times.add(new TimePrincipal("São Paulo", "São Paulo" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Sampa_Logo.png")));
        times.add(new TimePrincipal("Vasco da Gama", "Rio de Janeiro" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Vasco_Logo.png")));
        times.add(new TimePrincipal("Ceará", "Ceará" , new ImageIcon("C:\\Users\\55859\\IdeaProjects\\tabelinha\\Logos_Clubes\\Ceara_Logo.png")));
    }


    public void gerarRodadas() {
        @SuppressWarnings("unused")
        int numTimes = times.size();
        if (times.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Número insuficiente de times para gerar rodadas.","Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }

        /*if (numTimes < 2) {
            JOptionPane.showMessageDialog(null, "Número insuficiente de times para gerar rodadas.", "Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (numTimes % 2 != 0) {
            JOptionPane.showMessageDialog(null,"Número ímpar de times, não é possível gerar rodadas.","Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }*/

        if (!rodadas.isEmpty()) {
            JOptionPane.showMessageDialog(null,"As rodadas já foram geradas.","Erro!",
                    JOptionPane.ERROR_MESSAGE);
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
    /* public void exibirTabelaClassificacaoComJTable() {
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

    }*/

    public ArrayList<ArrayList<Jogo>> getRodadas() {
        return rodadas;
    }

    public ArrayList<TimePrincipal> getTimes() {
        return times;
    }


}