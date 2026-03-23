package br.edu.iff.jogoforca.dominio.rodada.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaRodadaRepository implements RodadaRepository {

    private static MemoriaRodadaRepository soleInstance;
    private List<Rodada> pool = new ArrayList<>();
    private long idCounter = 1;

    private MemoriaRodadaRepository() {}

    public static MemoriaRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRodadaRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return idCounter++;
    }

    @Override
    public Rodada getPorId(long id) {
        for (Rodada rodada : pool) {
            if (rodada.getId() == id) return rodada;
        }
        return null;
    }

    @Override
    public Rodada[] getPorJogador(Jogador jogador) {
        List<Rodada> encontradas = new ArrayList<>();
        // Como o diagrama não exigiu um getJogador() na Rodada, 
        // em um caso real você compararia o jogador interno da rodada.
        // Vamos apenas retornar vazio por segurança por enquanto.
        return encontradas.toArray(new Rodada[encontradas.size()]);
    }

    @Override
    public void inserir(Rodada rodada) throws RepositoryException {
        if (pool.contains(rodada)) {
            throw new RepositoryException("Rodada já existe no repositório.");
        }
        pool.add(rodada);
    }

    @Override
    public void atualizar(Rodada rodada) throws RepositoryException {
        if (!pool.contains(rodada)) {
            throw new RepositoryException("Rodada não encontrada para atualização.");
        }
    }

    @Override
    public void remover(Rodada rodada) throws RepositoryException {
        if (!pool.remove(rodada)) {
            throw new RepositoryException("Rodada não encontrada para remoção.");
        }
    }
}