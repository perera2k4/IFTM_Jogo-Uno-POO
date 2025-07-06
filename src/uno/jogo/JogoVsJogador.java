package uno.jogo;
import java.util.Scanner;
import uno.baralho.Carta;
import uno.jogador.Jogador;

public class JogoVsJogador extends Jogo {
    @Override
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome do Jogador 1: ");
        String nome1 = scanner.nextLine();
        System.out.print("Nome do Jogador 2: ");
        String nome2 = scanner.nextLine();
        jogadores.add(new uno.jogador.JogadorHumano(nome1));
        jogadores.add(new uno.jogador.JogadorHumano(nome2));

        for (Jogador jogador : jogadores) {
            for (int i = 0; i < 7; i++) {
                jogador.receberCarta(baralho.comprar());
            }
        }

        cartaTopo = baralho.comprar();
        System.out.println("Carta inicial: " + cartaTopo);

        int cartasParaComprar = 0;
        boolean pularVez = false;
        boolean jogoAtivo = true;
        while (jogoAtivo) {
            Jogador jogador = jogadores.get(jogadorAtual);
            System.out.println("\nVez de " + jogador.getNome());
            boolean corEscolhida = false;
            String novaCor = null;

            if (cartasParaComprar > 0) {
                for (int i = 0; i < cartasParaComprar; i++) {
                    Carta c = baralho.comprar();
                    if (c != null) jogador.receberCarta(c);
                }
                System.out.println(jogador.getNome() + " comprou " + cartasParaComprar + " cartas!");
                cartasParaComprar = 0;
                pularVez = false;
                jogadorAtual = (jogadorAtual + direcao + jogadores.size()) % jogadores.size();
                continue;
            }
            if (pularVez) {
                System.out.println(jogador.getNome() + " perdeu a vez!");
                pularVez = false;
                jogadorAtual = (jogadorAtual + direcao + jogadores.size()) % jogadores.size();
                continue;
            }

            System.out.println("Sua mão: ");
            for (int i = 0; i < jogador.getMao().size(); i++) {
                System.out.println((i+1) + ": " + jogador.getMao().get(i));
            }
            System.out.println("Carta no topo: " + cartaTopo);

            boolean jogou = false;
            while (!jogou) {
                System.out.print("Escolha a carta para jogar (número) ou 0 para comprar: ");
                int escolha = -1;
                try {
                    escolha = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida.");
                    continue;
                }
                if (escolha == 0) {
                    Carta nova = baralho.comprar();
                    if (nova == null) {
                        System.out.println("Baralho acabou!");
                        continue;
                    }
                    jogador.receberCarta(nova);
                    System.out.println("Você comprou: " + nova);
                    jogou = true;
                } else if (escolha > 0 && escolha <= jogador.getMao().size()) {
                    Carta escolhida = jogador.getMao().get(escolha-1);
                    if (podeJogar(escolhida, cartaTopo)) {
                        jogador.removerCarta(escolhida);

                        if (escolhida.getCor().equals("Preto")) {
                            String[] cores = {"Vermelho", "Verde", "Azul", "Amarelo"};
                            int corOpcao = -1;
                            while (corOpcao < 1 || corOpcao > 4) {
                                System.out.println("Escolha a cor para o próximo turno:");
                                for (int i = 0; i < cores.length; i++) {
                                    System.out.println((i+1) + " - " + cores[i]);
                                }
                                System.out.print("Opção: ");
                                try {
                                    corOpcao = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Entrada inválida.");
                                    continue;
                                }
                                if (corOpcao < 1 || corOpcao > 4) {
                                    System.out.println("Opção inválida!");
                                }
                            }
                            novaCor = cores[corOpcao-1];
                            cartaTopo = new uno.baralho.Carta(novaCor, escolhida.getValor());
                            corEscolhida = true;
                            System.out.println(jogador.getNome() + " jogou: " + escolhida + " e escolheu a cor " + novaCor);
                            jogou = true;

                            if (escolhida.getValor().equals("+4")) {
                                cartasParaComprar = 4;
                                pularVez = true;
                            }
                        } else if (escolhida.getValor().equals("+2")) {
                            cartaTopo = escolhida;
                            System.out.println(jogador.getNome() + " jogou: " + escolhida);
                            jogou = true;
                            cartasParaComprar = 2;
                            pularVez = true;
                        } else if (escolhida.getValor().equals("Pular") || escolhida.getValor().equals("Bloqueio")) {
                            cartaTopo = escolhida;
                            System.out.println(jogador.getNome() + " jogou: " + escolhida);
                            jogou = true;
                            pularVez = true;
                        } else {
                            cartaTopo = escolhida;
                            System.out.println(jogador.getNome() + " jogou: " + escolhida);
                            jogou = true;
                        }
                    } else {
                        System.out.println("Não pode jogar essa carta!");
                    }
                } else {
                    System.out.println("Escolha inválida.");
                }
            }

            if (jogador.venceu()) {
                System.out.println("\nParabéns, " + jogador.getNome() + " venceu!");
                jogoAtivo = false;
            } else {
                jogadorAtual = (jogadorAtual + direcao + jogadores.size()) % jogadores.size();
            }
        }
    }

    private boolean podeJogar(uno.baralho.Carta carta, uno.baralho.Carta topo) {
        return carta.getCor().equals(topo.getCor()) || carta.getValor().equals(topo.getValor()) || carta.getCor().equals("Preto");
    }
}
