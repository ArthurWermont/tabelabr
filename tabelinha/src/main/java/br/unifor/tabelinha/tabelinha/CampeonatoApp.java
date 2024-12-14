package br.unifor.tabelinha.tabelinha;
//instalamos o maven e configuramos para ele ser uma variavel do sistema onde dentro do
// maven ja tem todas as consifiguraçoes de varios springboots que nos teriamos que usar, 
//se nao fosse pelo maven teria que fazer dowload de varias versoes diferentes de springboots 
//onde facilmentes teriamos que baixar 15 arquivos que demoraria muuito mais no computador da unifor.
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public int getRodadaAtual() {
        return rodadaAtual;
    }

    public void setRodadaAtual(int rodadaAtual) {
        this.rodadaAtual = rodadaAtual;
    }

    @SuppressWarnings("unused")
    private int teste;
    private TabelaCampeonato tabelaCampeonato;
   // private JButton botaoSalvarCSV ;

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


        /*botaoSalvarCSV = new JButton("Salvar Tabela em CSV");
        botaoSalvarCSV.setForeground(Cores.TEXTO_BOTAO);
        botaoSalvarCSV.setBackground(Cores.FUNDO_BOTAO);

        painelControle.add(botaoSalvarCSV);*/

        botaoProximaRodada = new JButton("Próxima Rodada");
        botaoProximaRodada.setForeground(Cores.TEXTO_BOTAO);
        botaoProximaRodada.setBackground(Cores.FUNDO_BOTAO);

        botaoResultadosAnteriores = new JButton("Ver Resultados Anteriores");
        botaoResultadosAnteriores.setForeground(Cores.TEXTO_BOTAO);
        botaoResultadosAnteriores.setBackground(Cores.FUNDO_BOTAO);

        botaoTabelaClassificacao = new JButton("Ver Tabela de Classificação");
        botaoTabelaClassificacao.setForeground(Cores.TEXTO_BOTAO);
        botaoTabelaClassificacao.setBackground(Cores.FUNDO_BOTAO);

        // Botão para salvar Excel
        /*JButton salvarExcelButton = new JButton("Salvar Excel");
        salvarExcelButton.setForeground(Cores.TEXTO_BOTAO);
        salvarExcelButton.setBackground(Cores.FUNDO_BOTAO);
        salvarExcelButton.addActionListener(e -> tabelaCampeonato.salvarRodadasExcel());*/

        painelControle.add(labelRodadaAtual);
        painelControle.add(botaoProximaRodada);
        painelControle.add(botaoResultadosAnteriores);
        painelControle.add(botaoTabelaClassificacao);
        //painelControle.add(salvarExcelButton);

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
                        salvarRodadasETabelaExcel();
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
                    writer.println("Time Casa,Placar,Time Visitante"); // Cabeçalho para os jogos da rodada

                    // Adicionar os jogos da rodada
                    for (Jogo jogo : jogosDaRodada) {
                        String linha = jogo.getTime1().getNome() + "," +
                                jogo.getGolsTime1() + " - " + jogo.getGolsTime2() + "," +
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
        /* if (rodadas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "As rodadas ainda não foram geradas.","Alerta!",
                    JOptionPane.WARNING_MESSAGE);
        }*/

        ArrayList<Jogo> jogosDaRodada = rodadas.get(rodadaAtual);
        for (Jogo jogo : jogosDaRodada) {
            jogo.jogar();
            jogo.getTime1().atualizarClassificacao(jogo.getGolsTime1(), jogo.getGolsTime2());
            jogo.getTime2().atualizarClassificacao(jogo.getGolsTime2(), jogo.getGolsTime1());
        }
    }

    public void salvarRodadasETabelaExcel() {
        // Define o nome do arquivo Excel
        String nomeArquivo = "campeonato_brasileiro.xlsx";
    
        try (Workbook workbook = new XSSFWorkbook()) {  // Cria um novo arquivo Excel (XSSFWorkbook para Excel 2007+)
            
            // Criar uma planilha para cada rodada
            for (int i = 0; i < rodadas.size(); i++) {  // Loop por cada rodada
                ArrayList<Jogo> jogosDaRodada = rodadas.get(i);  // Obtém os jogos da rodada atual
    
                // Cria uma nova planilha para a rodada (nome "Rodada 1", "Rodada 2", ...)
                Sheet rodadaSheet = workbook.createSheet("Rodada " + (i + 1));
    
                // Criar o cabeçalho da planilha da rodada
                Row headerRow = rodadaSheet.createRow(0);  // Cria a primeira linha para o cabeçalho
                headerRow.createCell(0).setCellValue("Time Casa");  // Coluna 1: Time Casa
                headerRow.createCell(1).setCellValue("Placar");  // Coluna 2: Placar
                headerRow.createCell(2).setCellValue("Time Visitante");  // Coluna 3: Time Visitante
    
                // Preencher os dados dos jogos na planilha da rodada
                for (int j = 0; j < jogosDaRodada.size(); j++) {  // Loop pelos jogos da rodada
                    Jogo jogo = jogosDaRodada.get(j);  // Obtém o jogo atual
                    Row row = rodadaSheet.createRow(j + 1);  // Cria uma nova linha para o jogo
    
                    // Preenche os dados do jogo nas colunas
                    row.createCell(0).setCellValue(jogo.getTime1().getNome());  // Time Casa
                    row.createCell(1).setCellValue(jogo.getGolsTime1() + " - " + jogo.getGolsTime2());  // Placar
                    row.createCell(2).setCellValue(jogo.getTime2().getNome());  // Time Visitante
                }
    
                // Ajusta a largura das colunas para que o conteúdo fique visível
                for (int col = 0; col < 3; col++) {
                    rodadaSheet.autoSizeColumn(col);  // Ajusta a largura das colunas 0, 1 e 2
                }
            }
    
            // Criar uma nova planilha para a tabela do campeonato
            Sheet tabelaSheet = workbook.createSheet("Tabela Campeonato");
    
            // Cabeçalho da planilha da tabela
            Row tabelaHeaderRow = tabelaSheet.createRow(0);  // Cria a primeira linha para o cabeçalho
            tabelaHeaderRow.createCell(0).setCellValue("Posição");  // Coluna 1: Posição
            tabelaHeaderRow.createCell(1).setCellValue("Time");  // Coluna 2: Time
            tabelaHeaderRow.createCell(2).setCellValue("Pontos");  // Coluna 3: Pontos
            tabelaHeaderRow.createCell(3).setCellValue("Gols Feitos");  // Coluna 4: Gols Feitos
            tabelaHeaderRow.createCell(4).setCellValue("Gols Sofridos");  // Coluna 5: Gols Sofridos
            tabelaHeaderRow.createCell(5).setCellValue("Saldo de Gols");  // Coluna 6: Saldo de Gols
    
            // Obtém a lista de times ordenados pela classificação
            ArrayList<TimePrincipal> times = tabelaCampeonato.getTimes();
            Collections.sort(times, TimePrincipal.compararPorDesempate);  // Ordena os times pelo critério de desempate
    
            // Preencher os dados da tabela
            for (int i = 0; i < times.size(); i++) {  // Loop por todos os times
                TimePrincipal time = times.get(i);  // Obtém o time atual
                Row row = tabelaSheet.createRow(i + 1);  // Cria uma nova linha para o time
    
                // Preenche os dados do time nas colunas
                row.createCell(0).setCellValue(i + 1);  // Posição
                row.createCell(1).setCellValue(time.getNome());  // Nome do time
                row.createCell(2).setCellValue(time.getPontos());  // Pontos
                row.createCell(3).setCellValue(time.getGolsFeitos());  // Gols Feitos
                row.createCell(4).setCellValue(time.getGolsSofridos());  // Gols Sofridos
                row.createCell(5).setCellValue(time.getSaldoGols());  // Saldo de Gols
            }
    
           // Ajusta a largura das colunas de 0 a 5 (todas as colunas da tabela)
                for (int col = 0; col < 6; col++) {
                tabelaSheet.autoSizeColumn(col);  // Ajusta a largura da coluna de acordo com o maior conteúdo em cada uma
            }

    
            // Salvar o arquivo Excel
            try (FileOutputStream fileOut = new FileOutputStream(nomeArquivo)) {
                //escreve os dados no arquivo
                workbook.write(fileOut);  // Escreve o conteúdo no arquivo
                JOptionPane.showMessageDialog(null, "Arquivo Excel salvo com sucesso: " + nomeArquivo, "Sucesso", JOptionPane.INFORMATION_MESSAGE);  // Exibe uma mensagem de sucesso
            }
    
        } catch (IOException e) {
            // Se ocorrer um erro durante o processo, exibe uma mensagem de erro
            JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo Excel: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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