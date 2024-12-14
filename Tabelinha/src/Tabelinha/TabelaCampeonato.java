package Tabelinha;

import java.util.ArrayList;
import javax.swing.*;

public class TabelaCampeonato implements Usuario {
    //esta no construtor de tabelacampeonato
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



    public void addPeloUsuario() {

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
                JOptionPane.showMessageDialog(null, "Você precisa inserir um nome para o time.", "Cuidado!",
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
        int numTimes = times.size();
        if(numTimes != 20){
            JOptionPane.showMessageDialog(null,"Número insuficiente de times para gerar rodadas.","Erro!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (times.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Número insuficiente de times para gerar rodadas.","Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }

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

    public ArrayList<ArrayList<Jogo>> getRodadas() {
        return rodadas;
    }

    public ArrayList<TimePrincipal> getTimes() {
        return times;
    }

}