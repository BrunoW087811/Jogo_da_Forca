package br.edu.iff.bancodepalavras.dominio.jogador;

import br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory {

    private static JogadorFactoryImpl soleInstance;

    private JogadorFactoryImpl(JogadorRepository repository) {
        super(repository);
    }

    public static void createSoleInstance(JogadorRepository repository) {
        if (soleInstance == null) {
            soleInstance = new JogadorFactoryImpl(repository);
        }
    }

    public static JogadorFactoryImpl getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("JogadorFactoryImpl ainda não foi inicializada.");
        }
        return soleInstance;
    }

    @SuppressWarnings("unused")
    private JogadorRepository getJogadorRepository() {
        return (JogadorRepository) getRepository();
    }

    @Override
    public Jogador getJogador(String nome) {
        long id = getProximoId();
        return Jogador.criar(id, nome);
    }
}