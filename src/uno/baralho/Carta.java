package uno.baralho;

public class Carta {
    private String cor;
    private String valor;

    public Carta(String cor, String valor) {
        this.cor = cor;
        this.valor = valor;
    }

    public String getCor() {
        return cor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return cor + " " + valor;
    }
}
