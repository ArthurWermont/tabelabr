package Tabelinha;

import java.awt.Color;

public class Cores {

    /*O modificador final em Java é utilizado para indicar que o valor de uma variável,
     método ou classe não pode ser alterado após sua inicialização. Quando aplicado a uma variável,
     o final significa que essa variável será uma CONSTANTE, ou seja,
     seu valor não pode ser modificado depois de atribuído.*/

    // Cores para texto
    public static final Color TEXTO_LABEL = new Color(0, 0, 255);       // Azul
    public static final Color TEXTO_BOTAO = new Color(255, 255, 255);   // Branco
    public static final Color TEXTO_TABELA = new Color(0, 0, 0);        // Preto

    // Cores para o fundo
    public static final Color FUNDO_BOTAO = new Color(0, 128, 0);       // Verde
    public static final Color FUNDO_TABELA = new Color(192, 192, 192);  // Cinza claro
    public static final Color FUNDO_SCROLL = new Color(192, 192, 192);  // Fundo para JScrollPane
}