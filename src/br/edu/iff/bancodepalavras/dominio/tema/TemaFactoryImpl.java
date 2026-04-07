package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.factory.EntityFactory;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory {

    private static TemaFactoryImpl soleInstance;
    
    private TemaFactoryImpl(TemaRepository repository) {
        super(repository);
    }
    
    public static void createSoleInstance(TemaRepository repository) {
        if (soleInstance == null) {
            soleInstance = new TemaFactoryImpl(repository);
        }
    }

    public static TemaFactoryImpl getSoleInstance() {
        return soleInstance;
    }


    private TemaRepository getTemaRepository() {
        return (TemaRepository) getRepository();
    }

    @Override
    public Tema getTema(String nome) {
        // Verifica se já existe
        Tema[] existentes = getTemaRepository().getPorNome(nome);
        if (existentes != null && existentes.length > 0) {
            return existentes[0];
        }
        return Tema.criar(getProximoId(), nome);
    }
}