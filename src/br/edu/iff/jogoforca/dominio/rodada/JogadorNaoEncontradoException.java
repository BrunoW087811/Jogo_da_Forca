package br.edu.iff.jogoforca.dominio.rodada;

public class JogadorNaoEncontradoException extends Exception {

    @SuppressWarnings("FieldMayBeFinal")
    private String jogador;

    public JogadorNaoEncontradoException(String jogador) {
        super("Jogador não encontrado: " + jogador);
        this.jogador = jogador;
    }

    public String getJogador() {
        return jogador;
    }
}
