package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.factory.RepositoryFactory;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactory;
import br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;
import br.edu.iff.jogoforca.imagem.ElementoGraficoImagemFactory;
import br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;

public class Aplicacao {
    
    private static final String[] TIPOS_REPOSITORY_FACTORY = {"memoria", "relacional"};
    private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = {"texto", "imagem"};
    private static final String[] TIPOS_RODADA_FACTORY = {"sorteio"};
    
    private static Aplicacao soleInstance;
    
    @SuppressWarnings("unused")
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
        // As injeções das fábricas concretas vão aqui!
        // Quando criar a MemoriaRepositoryFactory, faremos a chamada dela aqui.
    }

    public String[] getTiposRepositoryFactory() {
        return TIPOS_REPOSITORY_FACTORY;
    }

    public void setTipoRepositoryFactory(String tipo) {
        this.tipoRepositoryFactory = tipo;
    }

    public RepositoryFactory getRepositoryFactory() {
        if (tipoRepositoryFactory.equals("memoria")) {
            return br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory.getSoleInstance();
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
        if (tipoElementoGraficoFactory.equals("texto")) {
            return ElementoGraficoTextoFactory.getSoleInstance();
        } else if (tipoElementoGraficoFactory.equals("imagem")) {
            return ElementoGraficoImagemFactory.getSoleInstance();
        }
        return null;
    }

    public BonecoFactory getBonecoFactory() {
        return getElementoGraficoFactory();
    }

    public LetraFactory getLetraFactory() {
        return (LetraFactory) getElementoGraficoFactory();
    }

    public String[] getTiposRodadaFactory() {
        return TIPOS_RODADA_FACTORY;
    }

    public void setTipoRodadaFactory(String tipo) {
        this.tipoRodadaFactory = tipo;
    }

    public RodadaFactory getRodadaFactory() {
        if (tipoRodadaFactory.equals("sorteio")) {
            return RodadaSorteioFactory.getSoleInstance();
        }
        return null;
    }

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