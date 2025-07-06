<div align="center">
  <img src="./if-logo.png" alt="Logo da Instituição" width="450"/>
  <h3>Instituto Federal de Educação, Ciência e Tecnologia do Triângulo Mineiro - Campus Ituiutaba</h3>
  <p><em>Graduação em Tecnologia em <u>Análise e Desenvolvimento de Sistemas</u></em></p>
  <p>Programação Orientada a Objetos</p>
</div>


# 🎮 Jogo UNO - Programação Orientada a Objetos

## 📋 Sobre o Projeto

Este projeto implementa o clássico jogo de cartas UNO, permitindo partidas entre dois jogadores humanos ou entre um jogador humano e o computador. O jogo foi desenvolvido seguindo os princípios da POO, com uma arquitetura estruturada.

## 🎯 Funcionalidades

### Modos de Jogo
- **Jogador vs Jogador**: Dois jogadores humanos competem
- **Jogador vs Computador**: Jogue contra uma Computador

### Cartas Implementadas
- **Cartas Numéricas**: 0-9 em quatro cores (Vermelho, Verde, Azul, Amarelo)
- **Cartas de Ação**:
  - **Bloqueio**: Bloqueia o próximo jogador
  - **Inverter**: Inverte a direção do jogo
  - **+2**: Próximo jogador compra 2 cartas e perde a vez
  - **+4**: Próximo jogador compra 4 cartas e perde a vez (pode escolher cor)
  - **Coringa**: Permite escolher a cor para o próximo turno

## 🧠 Estratégia de jogar contra a máquina
No computador foi implementado uma estratégia inteligente:

1. **Cartas +4**: Máxima punição ao oponente
2. **Especiais da mesma cor**: Controle do jogo mantendo cor
3. **Combinação por cor**: Facilita próximas jogadas
4. **Combinação por valor**: Opção secundária
5. **Coringas normais**: Último recurso


## 🏗️ Arquitetura do Projeto

### Estrutura de Pacotes
```
src/uno/
├── Main.java                  # Classe principal
├── baralho/
│   ├── Carta.java             # Representa uma carta
│   └── Baralho.java           # Gerencia o baralho
├── jogador/
│   ├── Jogador.java           # Classe abstrata base
│   ├── JogadorHumano.java     # Implementação para jogar 1x1
│   └── JogadorComputador.java # Implementação para jogar Computador
├── jogo/
│   ├── Jogo.java              # Classe abstrata base do jogo
│   ├── JogoVsJogador.java     # Modo PvP
│   └── JogoVsComputador.java  # Modo vs Computador
└── excecao/
    └── UnoException.java      # Exceções personalizadas
```

## 🚀 Como Executar

### Pré-requisitos
- Java 8 ou superior
- Terminal/Prompt de comando

### Compilação
```bash
# Navegar para o diretório do projeto
cd src

# Compilar todos os arquivos Java
javac uno/*.java uno/baralho/*.java uno/jogador/*.java uno/jogo/*.java uno/excecao/*.java

# Ou usar o comando compilado (se disponível)
java -cp . uno.Main
```

### Execução
```bash
# Executar o jogo
java uno.Main
```

## 🎮 Como Jogar

### Início do Jogo
1. Execute o programa
2. Escolha o modo de jogo (1 - PvP, 2 - vs Computador, 0 - Sair)
3. Digite o(s) nome(s) do(s) jogador(es)

### Durante o Jogo
- **Visualização**: Veja suas cartas numeradas e a carta do topo
- **Jogada**: Digite o número da carta que deseja jogar
- **Compra**: Digite 0 para comprar uma carta do baralho
- **Cartas Especiais**: Escolha cores quando jogar coringas

### Regras de Jogada
- Combine **cor** ou **número/símbolo** com a carta do topo
- Cartas **pretas** (coringas e +4) podem ser jogadas a qualquer momento
- **Objetivo**: Seja o primeiro a ficar sem cartas!
