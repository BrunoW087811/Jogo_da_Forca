package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
public class Item {

    private final int id;
    private final Palavra palavra;
    private final boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    private Item(int id, Palavra palavra) {
        this.id = id;
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
    }

    // Construtor privado para reconstituir item do banco
    private Item(int id, Palavra palavra, int[] posicoesDescobertasIdx, String palavraArriscada) {
        this.id = id;
        this.palavra = palavra;
        this.palavraArriscada = palavraArriscada;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
        if (posicoesDescobertasIdx != null) {
            for (int pos : posicoesDescobertasIdx) {
                if (pos >= 0 && pos < this.posicoesDescobertas.length) {
                    this.posicoesDescobertas[pos] = true;
                }
            }
        }
    }

    // Fábrica para criar novo item (package-private - só Rodada acessa)
    @SuppressWarnings("unused")
    static Item criar(int id, Palavra palavra) {
        return new Item(id, palavra);
    }

    // Fábrica para reconstituir item existente (package-private)
    @SuppressWarnings("unused")
    static Item reconstituir(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        return new Item(id, palavra, posicoesDescobertas, palavraArriscada);
    }

    public int getId() {
        return id;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public String getPalavraArriscada() {
        return palavraArriscada;
    }

    public boolean arriscou() {
        return palavraArriscada != null;
    }

    public boolean acertou() {
        return arriscou() && palavra.comparar(palavraArriscada);
    }

    public boolean descobriu() {
        return acertou() ||
        qtdeLetrasEncobertas() == 0;
    }

    public int qtdeLetrasEncobertas() {
        int count = 0;
        for (boolean descoberta : posicoesDescobertas) {
            if (!descoberta) count++;
        }
        return count;
    }

    public Letra[] getLetrasEncobertas() {
        int qtde = qtdeLetrasEncobertas();
        Letra[] encobertas = new Letra[qtde];
        int idx = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (!posicoesDescobertas[i]) {
                encobertas[idx++] = palavra.getLetra(i);
            }
        }
        return encobertas;
    }

    public Letra[] getLetrasDescobertas() {
        int qtde = 0;
        for (boolean d : posicoesDescobertas) if (d) qtde++;
        Letra[] descobertas = new Letra[qtde];
        int idx = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (posicoesDescobertas[i]) descobertas[idx++] = palavra.getLetra(i);
        }
        return descobertas;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return qtdeLetrasEncobertas() * valorPorLetraEncoberta;
    }

    // Package-private: só Rodada chama
    public void arriscar(String palavraArriscada) {
        // Somente pode arriscar uma única vez
        if (this.palavraArriscada == null) {
            this.palavraArriscada = palavraArriscada;
        }
    }

    public boolean tentar(char codigo) {
        int[] posicoes = palavra.tentar(codigo);
        for (int pos : posicoes) {
            posicoesDescobertas[pos] = true;
        }
        return posicoes.length > 0;
    }

    public void exibir(Object contexto) {
        palavra.exibir(contexto, posicoesDescobertas);
    }
}