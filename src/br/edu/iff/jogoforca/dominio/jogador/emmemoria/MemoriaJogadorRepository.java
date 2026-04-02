package br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaJogadorRepository implements JogadorRepository {

    private static MemoriaJogadorRepository soleInstance;
    private final List<Jogador> pool = new ArrayList<>();
    private long idCounter = 1;

    private MemoriaJogadorRepository() {}

    public static MemoriaJogadorRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaJogadorRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return idCounter++;
    }

    @Override
    public Jogador getPorId(long id) {
        for (Jogador jogador : pool) {
            if (jogador.getId() == id) return jogador;
        }
        return null;
    }

    @Override
    public Jogador getPorNome(String nome) {
        for (Jogador jogador : pool) {
            if (jogador.getNome().equalsIgnoreCase(nome)) {
                return jogador;
            }
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("CollectionsToArray")
    public Jogador[] getTodos() {
        return pool.toArray(new Jogador[0]);
    }

    @Override
    public void inserir(Jogador jogador) throws RepositoryException {
        if (pool.contains(jogador)) {
            throw new RepositoryException("Jogador já existe no repositório.");
        }
        pool.add(jogador);
    }

    @Override
    public void atualizar(Jogador jogador) throws RepositoryException {
        if (!pool.contains(jogador)) {
            throw new RepositoryException("Jogador não encontrado para atualização.");
        }
    }

    @Override
    public void remover(Jogador jogador) throws RepositoryException {
        if (!pool.remove(jogador)) {
            throw new RepositoryException("Jogador não encontrado para remoção.");
        }
    }
}