package uno.baralho;

import java.util.*;

public class Baralho {
    private List<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();
        inicializarBaralho();
        embaralhar();
    }

    private void inicializarBaralho() {
        String[] cores = {"Vermelho", "Verde", "Azul", "Amarelo"};
        String[] valores = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Bloqueio", "Inverter", "+2"};
        for (String cor : cores) {
            for (String valor : valores) {
                cartas.add(new Carta(cor, valor));
                if (!valor.equals("0")) cartas.add(new Carta(cor, valor));
            }
        }
        for (int i = 0; i < 4; i++) {
            cartas.add(new Carta("Preto", "+4"));
            cartas.add(new Carta("Preto", "Coringa"));
        }
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta comprar() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }

    public int tamanho() {
        return cartas.size();
    }
}
