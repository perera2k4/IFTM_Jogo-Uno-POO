package uno.jogo;
import uno.baralho.*;
import uno.jogador.*;
import java.util.*;

public abstract class Jogo {
    protected Baralho baralho;
    protected List<Jogador> jogadores;
    protected Carta cartaTopo;
    protected int direcao = 1;
    protected int jogadorAtual = 0;

    public Jogo() {
        baralho = new Baralho();
        jogadores = new ArrayList<>();
    }

    public abstract void iniciar();
}
