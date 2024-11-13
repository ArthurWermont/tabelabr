package Tabelinha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TabelaCampeonatoFrame extends JFrame {

    public TabelaCampeonatoFrame(ArrayList<TimePrincipal> times) {
        setTitle("Tabela do Campeonato Brasileiro");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar o modelo de dados para a tabela
        String[] colunas = {"Posição", "Time", "Pontos", "Saldo de Gols"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        // Preencher a tabela com os dados dos times
        for (int i = 0; i < times.size(); i++) {
            TimePrincipal time = times.get(i);
            Object[] linha = {i + 1, time.getNome(), time.getPontos(), time.getSaldoGols()};
            modeloTabela.addRow(linha);
        }

        // Criar a tabela com o modelo de dados
        JTable tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // Tornar a janela visível
        setVisible(true);
    }
}
