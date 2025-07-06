package uno.jogador;
import uno.baralho.Carta;
import java.util.*;

public class JogadorComputador extends Jogador {
    private Random random = new Random();

    public JogadorComputador(String nome) {
        super(nome);
    }

    @Override
    public Carta jogarCarta(Carta cartaTopo, Scanner scanner) {
        // Implementação feita na lógica do jogo
        return null;
    }
}
