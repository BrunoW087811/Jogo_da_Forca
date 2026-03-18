package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory {
    
    private static JogadorFactoryImpl soleInstance;

    public static void createSoleInstance(JogadorRepository repository) {
        if (soleInstance == null) {
            soleInstance = new JogadorFactoryImpl(repository);
        }
    }

    public static JogadorFactoryImpl getSoleInstance() {
        return soleInstance;
    }

    private JogadorFactoryImpl(JogadorRepository repository) {
        super(repository);
    }

    @SuppressWarnings("unused")
    private JogadorRepository getJogadorRepository() {
        return (JogadorRepository) getRepository();
    }

    @Override
    public Jogador getJogador(String nome) {
        return new Jogador(getProximoId(), nome);
    }
}