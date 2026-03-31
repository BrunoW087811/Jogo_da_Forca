package br.edu.iff.jogoforca.embdr;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.factory.RepositoryFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.jogoforca.dominio.rodada.embdr.BDRRodadaRepository;

public class BDRRepositoryFactory implements RepositoryFactory {

    private static BDRRepositoryFactory soleInstance;

    private BDRRepositoryFactory() {
    }

    public static BDRRepositoryFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRRepositoryFactory();
        }
        return soleInstance;
    }

    @Override
    public TemaRepository getTemaRepository() {
        return br.edu.iff.bancodepalavras.dominio.tema.embdr.BDRTemaRepository.getSoleInstance(); 
    }

    @Override
    public PalavraRepository getPalavraRepository() {
        return br.edu.iff.bancodepalavras.dominio.palavra.embdr.BDRPalavraRepository.getSoleInstance();
    }

    @Override
    public RodadaRepository getRodadaRepository() {
        return BDRRodadaRepository.getSoleInstance(); 
    }

    @Override
    public JogadorRepository getJogadorRepository() {
        return br.edu.iff.jogoforca.dominio.jogador.embdr.BDRJogadorRepository.getSoleInstance();
    }
}