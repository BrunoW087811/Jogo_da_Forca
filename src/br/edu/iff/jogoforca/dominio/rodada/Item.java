package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;

public class Item {
    
    private final Palavra palavra;
    private final boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    Item(Palavra palavra) {
        this.palavra = palavra;
        // Inicializa o array com o tamanho exato da palavra. 
        // Em Java, boolean[] já nasce com todos os valores como 'false'.
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
    }

    @SuppressWarnings("unused")
    Item(Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        this.palavra = palavra;
        this.palavraArriscada = palavraArriscada;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
        
        // Converte as posições numéricas salvas no banco para o array booleano
        if (posicoesDescobertas != null) {
            for (int pos : posicoesDescobertas) {
                if (pos >= 0 && pos < this.posicoesDescobertas.length) {
                    this.posicoesDescobertas[pos] = true;
                }
            }
        }
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public int getQtdeLetrasEncobertas() {
        int count = 0;
        for (boolean descoberta : posicoesDescobertas) {
            if (!descoberta) {
                count++;
            }
        }
        return count;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return getQtdeLetrasEncobertas() * valorPorLetraEncoberta;
    }

    public boolean descobriu() {
        // A regra do diagrama: descobriu = acertou ou qtdeLetrasEncobertas igual a 0
        return acertou() || getQtdeLetrasEncobertas() == 0;
    }

    public void exibir(Object contexto) {
        // Delega a exibição para a Palavra, passando as posições que já foram abertas
        palavra.exibir(contexto, posicoesDescobertas);
    }

    boolean tentar(char codigo) {
        boolean achou = false;
        // Verifica se a letra tentada existe na palavra e marca a posição como true
        for (int i = 0; i < palavra.getTamanho(); i++) {
            if (palavra.getLetra(i).getCodigo() == codigo) {
                posicoesDescobertas[i] = true;
                achou = true;
            }
        }
        return achou;
    }

    void arriscar(String palavraArriscada) {
        this.palavraArriscada = palavraArriscada;
    }

    public String getPalavraArriscada() {
        return palavraArriscada;
    }

    public boolean arriscou() {
        return palavraArriscada != null;
    }

    public boolean acertou() {
        // Retorna true se arriscou algo e esse algo é igual à palavra real
        return arriscou() && palavra.comparar(palavraArriscada);
    }
    public Letra[] getLetrasDescobertas() {
        // Conta quantas foram descobertas
        int qtde = 0;
        for (boolean descoberta : posicoesDescobertas) {
            if (descoberta) qtde++;
        }
        
        // Cria o array e preenche
        Letra[] descobertas = new Letra[qtde];
        int index = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (posicoesDescobertas[i]) {
                descobertas[index++] = palavra.getLetra(i);
            }
        }
        return descobertas;
    }

    public Letra[] getLetrasEncobertas() {
        int qtde = getQtdeLetrasEncobertas();
        Letra[] encobertas = new Letra[qtde];
        int index = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (!posicoesDescobertas[i]) {
                encobertas[index++] = palavra.getLetra(i);
            }
        }
        return encobertas;
    }
}