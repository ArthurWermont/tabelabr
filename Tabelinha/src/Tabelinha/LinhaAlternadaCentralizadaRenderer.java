package Tabelinha;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class LinhaAlternadaCentralizadaRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Centralizar o conteúdo
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // Alternar a cor de fundo das linhas
        if (!isSelected) {
            if (row % 2 == 0) {
                setBackground(new Color(240, 240, 240)); // Cor para linhas pares
            } else {
                setBackground(Color.WHITE); // Cor para linhas ímpares
            }
        }

        // Verificar se o valor é um JLabel
        if (value instanceof JLabel) {
            JLabel label = (JLabel) value;

            // Centralizar o conteúdo do JLabel
            setText(label.getText()); // Define o texto
            setIcon(label.getIcon()); // Define o ícone (logo do time)
            setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto
        } else {
            // Para valores normais (não JLabel), trata como texto ou imagem
            setIcon(null);  // Limpa o ícone caso não seja um JLabel
            setText(value != null ? value.toString() : ""); // Exibe o valor como texto
        }

        return this;
    }
}
