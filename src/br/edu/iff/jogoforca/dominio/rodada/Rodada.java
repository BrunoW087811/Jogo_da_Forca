package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;

public class Rodada extends ObjetoDominioImpl {
    
    private static final int maxPalavras = 3; 
    private static final int maxErros = 10; 
    private static final int pontosQuandoDescobreTodasAsPalavras = 100;
    private static final int pontosPorLetraEncoberta = 15; 

    private Item[] itens;
    private Letra[] erradas;
    private int qtdeErros = 0; 
    private Jogador jogador;

    protected Rodada(long id, Palavra[] palavras, Jogador jogador) {
        super(id);
        this.jogador = jogador;
        this.erradas = new Letra[maxErros];
        
        // Cria os itens baseados nas palavras sorteadas (respeitando o limite)
        int numPalavras = Math.min(palavras.length, maxPalavras);
        this.itens = new Item[numPalavras];
        for (int i = 0; i < numPalavras; i++) {
            this.itens[i] = new Item(palavras[i]);
        }
    }

    protected Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        super(id);
        this.itens = itens;
        this.erradas = erradas;
        this.jogador = jogador;
        
        // Conta quantos erros já vieram carregados do banco
        for (Letra erro : erradas) {
            if (erro != null) qtdeErros++;
        }
    }

    public Tema getTema() {
        if (itens != null && itens.length > 0) {
            return itens[0].getPalavra().getTema();
        }
        return null;
    }

    public Palavra[] getPalavras() {
        Palavra[] palavras = new Palavra[itens.length];
        for (int i = 0; i < itens.length; i++) {
            palavras[i] = itens[i].getPalavra();
        }
        return palavras;
    }

    public int getNumPalavras() {
        return itens.length;
    }

    public void tentar(char codigo) {
        if (encerrou()) return; 

        boolean acertouAlguma = false;
        for (Item item : itens) {
            if (item.tentar(codigo)) {
                acertouAlguma = true;
            }
        }

        // Se a letra não tem em nenhuma palavra e ainda não atingiu o limite de erros
        if (!acertouAlguma && qtdeErros < maxErros) {
            // Nota: Para injetar a Letra correta, você deverá buscar da LetraFactory da sua Aplicação.
            // Para manter a lógica pura, vamos assumir que o sistema consegue gerar a letra.
            // erradas[qtdeErros] = Aplicacao.getSoleInstance().getLetraFactory().getLetra(codigo);
            qtdeErros++;
        }

        if (encerrou()) {
            jogador.setPontuacao(jogador.getPontuacao() + calcularPontos());
        }
    }

    public void arriscar(String[] palavras) {
        if (encerrou()) return; 

        for (int i = 0; i < itens.length; i++) {
            if (i < palavras.length) {
                itens[i].arriscar(palavras[i]);
            }
        }

        if (encerrou()) {
            jogador.setPontuacao(jogador.getPontuacao() + calcularPontos()); 
        }
    }

    public boolean descobriu() {
        // Só descobriu a rodada se todos os itens foram descobertos 
        for (Item item : itens) {
            if (!item.descobriu()) return false;
        }
        return true;
    }

    public boolean arriscou() {
        if (itens.length > 0) {
            return itens[0].arriscou();
        }
        return false;
    }

    public boolean encerrou() {
        // Encerrou = arriscou OU descobriu OU atingiuMaxErros 
        return arriscou() || descobriu() || qtdeErros >= maxErros;
    }

    public int calcularPontos() {
        if (!descobriu()) {
            return 0; // Se não descobriu, 0 pontos 
        }

        int pontos = pontosQuandoDescobreTodasAsPalavras;
        for (Item item : itens) {
            pontos += item.calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
        }
        return pontos;
    }
    public int getQtdeErros() {
        return qtdeErros;
    }

    public int getQtdeTentativasRestantes() {
        return maxErros - qtdeErros;
    }

    public Letra[] getErradas() {
        return erradas;
    }

    public void exibirItens(Object contexto) {
        if (itens != null) {
            for (Item item : itens) {
                item.exibir(contexto);
            }
        }
    }

    public void exibirLetrasErradas(Object contexto) {
        for (int i = 0; i < qtdeErros; i++) {
            if (erradas[i] != null) {
                erradas[i].exibir(contexto);
            }
        }
    }

    public void exibirBoneco(Object contexto) {
        // Como ainda não temos a classe Aplicacao pronta, 
        // deixamos preparado o comentário do que deverá ser feito:
        // Boneco boneco = Aplicacao.getSoleInstance().getBonecoFactory().getBoneco();
        // boneco.exibir(contexto, qtdeErros);
    }
    
    // Método estático para permitir que a fábrica crie a rodada
    public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }
}