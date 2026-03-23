package br.edu.iff.bancodepalavras.dominio.jogador;

public class Jogador {

    private final long id;
    private String nome;
    private int pontuacao = 0;

    private Jogador(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private Jogador(long id, String nome, int pontuacao) {
        this.id = id;
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public static Jogador criar(long id, String nome) {
        return new Jogador(id, nome);
    }

    public static Jogador reconstituir(long id, String nome, int pontuacao) {
        return new Jogador(id, nome, pontuacao);
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void atualizarPontuacao(int pontos) {
        this.pontuacao += pontos;
    }
}