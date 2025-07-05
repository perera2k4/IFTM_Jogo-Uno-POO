package uno;
import java.util.Scanner;
import uno.jogo.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao UNO!");
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nEscolha o modo de jogo:");
            System.out.println("1 - Jogador vs Jogador");
            System.out.println("2 - Jogador vs Computador");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }
            switch (opcao) {
                case 1:
                    JogoVsJogador jogoPvP = new JogoVsJogador();
                    jogoPvP.iniciar();
                    break;
                case 2:
                    JogoVsComputador jogoPc = new JogoVsComputador();
                    jogoPc.iniciar();
                    break;
                case 0:
                    System.out.println("Saindo do jogo. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}
