package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.factory.EntityFactory;

public abstract class RodadaFactoryImpl extends EntityFactory implements RodadaFactory {
    
    @SuppressWarnings("FieldMayBeFinal")
    private TemaRepository temaRepository;
    private PalavraRepository palavraRepository;

    protected RodadaFactoryImpl(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) { 
        super(repository);
        this.temaRepository = temaRepository;
        this.palavraRepository = palavraRepository;
    }

    protected RodadaRepository getRodadaRepository() {
        return (RodadaRepository) getRepository();
    }

    protected TemaRepository getTemaRepository() {
        return temaRepository; 
    }

    protected PalavraRepository getPalavraRepository() {
        return palavraRepository; 
    }

    public void setPalavraRepository(PalavraRepository palavraRepository) {
        this.palavraRepository = palavraRepository;
    }
}