package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.factory.EntityFactory;

public class PalavraFactoryImpl extends EntityFactory implements PalavraFactory {
    
    private static PalavraFactoryImpl soleInstance;

    public static void createSoleInstance(PalavraRepository repository) {
        if (soleInstance == null) {
            soleInstance = new PalavraFactoryImpl(repository);
        }
    }

    public static PalavraFactoryImpl getSoleInstance() {
        return soleInstance;
    }

    private PalavraFactoryImpl(PalavraRepository repository) {
        super(repository);
    }

    @SuppressWarnings("unused")
    private PalavraRepository getPalavraRepository() {
        return (PalavraRepository) getRepository();
    }

    @Override
    public Palavra getPalavra(String palavra, Tema tema) {
        return new Palavra(getProximoId(), palavra, tema);
    }
}