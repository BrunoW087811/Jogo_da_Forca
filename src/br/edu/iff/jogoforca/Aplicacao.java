package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.factory.RepositoryFactory;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactory;
import br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;
import br.edu.iff.jogoforca.imagem.ElementoGraficoImagemFactory;
import br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;

public class Aplicacao {

    private static final String[] TIPOS_REPOSITORY_FACTORY = {"memoria", "relacional"};
    private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = {"texto", "imagem"};
    private static final String[] TIPOS_RODADA_FACTORY = {"sorteio"};

    private static Aplicacao soleInstance;

    private String tipoRepositoryFactory = TIPOS_REPOSITORY_FACTORY[0];
    private String tipoElementoGraficoFactory = TIPOS_ELEMENTO_GRAFICO_FACTORY[0];
    private String tipoRodadaFactory = TIPOS_RODADA_FACTORY[0];

    private Aplicacao() {
    }

    public static Aplicacao getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new Aplicacao();
        }
        return soleInstance;
    }

    public void configurar() {
        RepositoryFactory repoFactory = getRepositoryFactory();

        // Cria singletons parametrizados 
        TemaFactoryImpl.createSoleInstance(repoFactory.getTemaRepository());
        PalavraFactoryImpl.createSoleInstance(repoFactory.getPalavraRepository());
        JogadorFactoryImpl.createSoleInstance(repoFactory.getJogadorRepository());
        RodadaSorteioFactory.createSoleInstance(
            repoFactory.getRodadaRepository(),
            repoFactory.getTemaRepository(),
            repoFactory.getPalavraRepository()
        );

        // Seta os factories estáticos nas classes de domínio
        Palavra.setLetraFactory(getLetraFactory());
        Rodada.setBonecoFactory(getBonecoFactory());
    }

    // Repository Factory

    public String[] getTiposRepositoryFactory() {
        return TIPOS_REPOSITORY_FACTORY;
    }

    public void setTipoRepositoryFactory(String tipo) {
        this.tipoRepositoryFactory = tipo;
    }

    public RepositoryFactory getRepositoryFactory() {
        if ("memoria".equals(tipoRepositoryFactory)) {
            return br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory.getSoleInstance();
        }
        if ("relacional".equals(tipoRepositoryFactory)) {
            return br.edu.iff.jogoforca.embdr.BDRRepositoryFactory.getSoleInstance();
        }
        return null;
    }

    public String[] getTiposElementoGraficoFactory() {
        return TIPOS_ELEMENTO_GRAFICO_FACTORY;
    }

    public void setTipoElementoGraficoFactory(String tipo) {
        this.tipoElementoGraficoFactory = tipo;
    }

    private ElementoGraficoFactory getElementoGraficoFactory() {
        if ("texto".equals(tipoElementoGraficoFactory)) {
            return ElementoGraficoTextoFactory.getSoleInstance();
        }
        if ("imagem".equals(tipoElementoGraficoFactory)) {
            return ElementoGraficoImagemFactory.getSoleInstance();
        }
        return null;
    }

    public BonecoFactory getBonecoFactory() {
        return getElementoGraficoFactory();
    }

    public LetraFactory getLetraFactory() {
        return getElementoGraficoFactory();
    }

    // Rodada Factory

    public String[] getTiposRodadaFactory() {
        return TIPOS_RODADA_FACTORY;
    }

    public void setTipoRodadaFactory(String tipo) {
        this.tipoRodadaFactory = tipo;
    }

    public RodadaFactory getRodadaFactory() {
        if ("sorteio".equals(tipoRodadaFactory)) {
            return RodadaSorteioFactory.getSoleInstance();
        }
        return null;
    }

    // Entity Factories 

    public TemaFactory getTemaFactory() {
        return TemaFactoryImpl.getSoleInstance();
    }

    public PalavraFactory getPalavraFactory() {
        return PalavraFactoryImpl.getSoleInstance();
    }

    public JogadorFactory getJogadorFactory() {
        return JogadorFactoryImpl.getSoleInstance();
    }
}
