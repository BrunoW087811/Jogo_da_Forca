package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
public class Rodada extends ObjetoDominioImpl {

    // Campos de configuração estáticos (setados pela Aplicacao.configurar())
    private static BonecoFactory bonecoFactory;
    private static int pontosPorLetraEncoberta = 15;
    private static int pontosQuandoDescobreTodasAsPalavras = 100;
    private static int maxErros = 10;
    private static int maxPalavras = 3;

    // Campos de instância
    private final Item[] itens;
    private final Letra[] erradas;
    private int qtdeErros = 0;
    private final Jogador jogador;
    // Construtor para nova rodada a partir de palavras sorteadas
    private Rodada(long id, Palavra[] palavras, Jogador jogador) {
        super(id);
        this.jogador = jogador;
        this.erradas = new Letra[maxErros];

        if (bonecoFactory == null) {
            throw new IllegalStateException("BonecoFactory não foi configurada. Chame Rodada.setBonecoFactory() antes.");
        }

        int numPalavras = Math.min(palavras.length, maxPalavras);
        this.itens = new Item[numPalavras];
        for (int i = 0; i < numPalavras; i++) {
            this.itens[i] = Item.criar(i, palavras[i]);
        }
    }

    private Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        super(id);
        this.itens = itens;
        this.erradas = erradas;
        this.jogador = jogador;
        for (Letra erro : erradas) {
            if (erro != null) qtdeErros++;
        }
    }

    //Métodos estáticos de fábrica

    public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }

    public static Rodada reconstituir(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        return new Rodada(id, itens, erradas, jogador);
    }


    public static BonecoFactory getBonecoFactory() {
        return bonecoFactory;
    }

    public static void setBonecoFactory(BonecoFactory factory) {
        bonecoFactory = factory;
    }

    public static int getPontosPorLetraEncoberta() {
        return pontosPorLetraEncoberta;
    }

    public static void setPontosPorLetraEncoberta(int pontos) {
        pontosPorLetraEncoberta = pontos;
    }

    public static int getPontosQuandoDescobreTodasAsPalavras() {
        return pontosQuandoDescobreTodasAsPalavras;
    }

    public static void setPontosQuandoDescobreTodasAsPalavras(int pontos) {
        pontosQuandoDescobreTodasAsPalavras = pontos;
    }

    public static int getMaxErros() {
        return maxErros;
    }

    public static void setMaxErros(int max) {
        maxErros = max;
    }

    public static int getMaxPalavras() {
        return maxPalavras;
    }

    public static void setMaxPalavras(int max) {
        maxPalavras = max;
    }


    public Jogador getJogador() {
        return jogador;
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

    public int getQtdeErros() {
        return qtdeErros;
    }

    public int getQtdeTentativasRestantes() {
        return maxErros - qtdeErros;
    }

    public int getQtdeTentativas() {
        return getCertas().length + qtdeErros;
    }

    public int getQtdeAcertos() {
        return getCertas().length;
    }

    public Letra[] getErradas() {
        int count = 0;
        for (Letra l : erradas) if (l != null) count++;
        Letra[] resultado = new Letra[count];
        int idx = 0;
        for (Letra l : erradas) if (l != null) resultado[idx++] = l;
        return resultado;
    }

    @SuppressWarnings({"CollectionsToArray", "ManualArrayToCollectionCopy"})
    public Letra[] getCertas() {
        java.util.Set<Letra> certas = new java.util.LinkedHashSet<>();
        for (Item item : itens) {
            for (Letra l : item.getLetrasDescobertas()) {
                certas.add(l);
            }
        }
        return certas.toArray(new Letra[0]);
    }

    public Letra[] getTentativas() {
        // tentativas = certas + erradas
        Letra[] certas = getCertas();
        Letra[] erradasArr = getErradas();
        Letra[] todas = new Letra[certas.length + erradasArr.length];
        System.arraycopy(certas, 0, todas, 0, certas.length);
        System.arraycopy(erradasArr, 0, todas, certas.length, erradasArr.length);
        return todas;
    }


    public void tentar(char codigo) {
        if (encerrou()) return;
        boolean acertouAlguma = false;
        for (Item item : itens) {
            if (item.tentar(codigo)) {
                acertouAlguma = true;
            }
        }

        if (!acertouAlguma && qtdeErros < maxErros) {
            erradas[qtdeErros] = Palavra.getLetraFactory().getLetra(Character.toLowerCase(codigo));
            qtdeErros++;
        }

        if (encerrou()) {
            jogador.atualizarPontuacao(calcularPontos());
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
            jogador.atualizarPontuacao(calcularPontos());
        }
    }

    public boolean arriscou() {
        for (Item item : itens) {
            if (item.arriscou()) return true;
        }
        return false;
    }

    public boolean descobriu() {
        for (Item item : itens) {
            if (!item.descobriu()) return false;
        }
        return true;
    }

    public boolean encerrou() {
        return arriscou() || descobriu() ||
        qtdeErros >= maxErros;
    }

    public int calcularPontos() {
        if (!descobriu()) return 0;
        int pontos = pontosQuandoDescobreTodasAsPalavras;
        for (Item item : itens) {
            pontos += item.calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
        }
        return pontos;
    }


    public void exibirBoneco(Object contexto) {
        Boneco boneco = bonecoFactory.getBoneco();
        boneco.exibir(contexto, qtdeErros);
    }

    public void exibirItens(Object contexto) {
        for (Item item : itens) {
            item.exibir(contexto);
            System.out.println();
        }
    }

    public void exibirPalavras(Object contexto) {
        for (Item item : itens) {
            item.getPalavra().exibir(contexto);
            System.out.println();
        }
    }

    public void exibirLetrasErradas(Object contexto) {
        for (int i = 0; i < qtdeErros; i++) {
            if (erradas[i] != null) {
                erradas[i].exibir(contexto);
                System.out.print(" ");
            }
        }
    }
}