package uno.jogador;

import uno.baralho.Carta;
import java.util.*;

public abstract class Jogador {
    protected String nome;
    protected List<Carta> mao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    public void removerCarta(Carta carta) {
        mao.remove(carta);
    }

    public boolean venceu() {
        return mao.isEmpty();
    }

    public abstract Carta jogarCarta(Carta cartaTopo, Scanner scanner);
}
