package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.dominio.ObjetoDominioImpl;

public class Jogador extends ObjetoDominioImpl {
    
    private String nome;
    private int pontuacao = 0;

    // Construtor para um jogador novo (pontuação começa zerada)
    protected Jogador(long id, String nome) {
        super(id);
        this.nome = nome;
        this.pontuacao = 0;
    }

    // Construtor para um jogador já existente (com pontuação recuperada)
    protected Jogador(long id, String nome, int pontuacao) {
        super(id);
        this.nome = nome;
        this.pontuacao = pontuacao;
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

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}