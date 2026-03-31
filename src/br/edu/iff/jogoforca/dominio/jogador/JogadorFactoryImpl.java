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

    private JogadorRepository getJogadorRepository() {
        return (JogadorRepository) getRepository();
    }

    @Override
    public Jogador getJogador(String nome) {
        // Verifica se jogador já existe no repositório
        Jogador existente = getJogadorRepository().getPorNome(nome);
        if (existente != null) return existente;
        // Cria novo jogador via método estático de fábrica
        return Jogador.criar(getProximoId(), nome);
    }
}
