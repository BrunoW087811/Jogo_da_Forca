package br.edu.iff.bancodepalavras.dominio.jogador;

public interface JogadorFactory {
    Jogador getJogador(String nome);
}