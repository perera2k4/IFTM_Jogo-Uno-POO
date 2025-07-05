package uno.jogo;
import java.util.List;
import java.util.Scanner;
import uno.baralho.Carta;
import uno.jogador.Jogador;

public class JogoVsComputador extends Jogo {
    @Override
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome do Jogador: ");
        String nome1 = scanner.nextLine();
        jogadores.add(new uno.jogador.JogadorHumano(nome1));
        jogadores.add(new uno.jogador.JogadorComputador("Computador"));

        // Cada jogador compra 7 cartas
        for (Jogador jogador : jogadores) {
            for (int i = 0; i < 7; i++) {
                jogador.receberCarta(baralho.comprar());
            }
        }

        cartaTopo = baralho.comprar();
        System.out.println("Carta inicial: " + cartaTopo);

        boolean jogoAtivo = true;
        int cartasParaComprar = 0;
        boolean pularVez = false;
        while (jogoAtivo) {
            Jogador jogador = jogadores.get(jogadorAtual);
            System.out.println("\nVez de " + jogador.getNome());
            boolean corEscolhida = false;
            String novaCor = null;

            // Se deve comprar cartas por +2 ou +4
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

            if (jogador instanceof uno.jogador.JogadorHumano) {
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
                            // Se for coringa ou +4, escolher cor
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
                                // Se for +4, próximo compra 4 cartas e perde a vez
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
            } else { // Computador
                System.out.println("Mão do computador: " + jogador.getMao().size() + " cartas.");
                Carta jogada = escolherJogadaComputador(jogador.getMao(), cartaTopo);
                if (jogada != null) {
                    jogador.removerCarta(jogada);
                    // Se for coringa ou +4, computador escolhe cor
                    if (jogada.getCor().equals("Preto")) {
                        String[] cores = {"Vermelho", "Verde", "Azul", "Amarelo"};
                        // Escolhe a cor que mais tem na mão
                        java.util.Map<String, Integer> contagem = new java.util.HashMap<>();
                        for (String cor : cores) contagem.put(cor, 0);
                        for (Carta c : jogador.getMao()) {
                            if (contagem.containsKey(c.getCor()))
                                contagem.put(c.getCor(), contagem.get(c.getCor()) + 1);
                        }
                        String corEscolhidaPc = cores[0];
                        int max = 0;
                        for (String cor : cores) {
                            if (contagem.get(cor) > max) {
                                max = contagem.get(cor);
                                corEscolhidaPc = cor;
                            }
                        }
                        cartaTopo = new uno.baralho.Carta(corEscolhidaPc, jogada.getValor());
                        System.out.println("Computador jogou: " + jogada + " e escolheu a cor " + corEscolhidaPc);
                        // Se for +4, próximo compra 4 cartas e perde a vez
                        if (jogada.getValor().equals("+4")) {
                            cartasParaComprar = 4;
                            pularVez = true;
                        }
                    } else if (jogada.getValor().equals("+2")) {
                        cartaTopo = jogada;
                        System.out.println("Computador jogou: " + jogada);
                        cartasParaComprar = 2;
                        pularVez = true;
                    } else if (jogada.getValor().equals("Pular") || jogada.getValor().equals("Bloqueio")) {
                        cartaTopo = jogada;
                        System.out.println("Computador jogou: " + jogada);
                        pularVez = true;
                    } else {
                        cartaTopo = jogada;
                        System.out.println("Computador jogou: " + jogada);
                    }
                } else {
                    Carta nova = baralho.comprar();
                    if (nova != null) {
                        jogador.receberCarta(nova);
                        System.out.println("Computador comprou uma carta.");
                    } else {
                        System.out.println("Baralho acabou!");
                    }
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

    // Prioridade: +4 > especiais mesma cor > cor igual > número igual > coringa
    private Carta escolherJogadaComputador(List<Carta> mao, Carta cartaTopo) {
        // 1. +4
        for (Carta c : mao) {
            if (c.getValor().equals("+4") && podeJogar(c, cartaTopo)) return c;
        }
        // 2. Especiais mesma cor (Pular, Inverter, +2, Bloqueio)
        for (Carta c : mao) {
            if ((c.getValor().equals("Pular") || c.getValor().equals("Inverter") || c.getValor().equals("+2") || c.getValor().equals("Bloqueio"))
                && c.getCor().equals(cartaTopo.getCor()) && podeJogar(c, cartaTopo)) return c;
        }
        // 3. Cor igual
        for (Carta c : mao) {
            if (c.getCor().equals(cartaTopo.getCor()) && podeJogar(c, cartaTopo)) return c;
        }
        // 4. Número igual
        for (Carta c : mao) {
            if (c.getValor().equals(cartaTopo.getValor()) && podeJogar(c, cartaTopo)) return c;
        }
        // 5. Coringa
        for (Carta c : mao) {
            if (c.getValor().equals("Coringa") && podeJogar(c, cartaTopo)) return c;
        }
        return null;
    }

    private boolean podeJogar(uno.baralho.Carta carta, uno.baralho.Carta topo) {
        return carta.getCor().equals(topo.getCor()) || carta.getValor().equals(topo.getValor()) || carta.getCor().equals("Preto");
    }
}
