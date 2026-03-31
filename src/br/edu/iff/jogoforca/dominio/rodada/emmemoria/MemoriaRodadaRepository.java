package br.edu.iff.jogoforca.dominio.rodada.emmemoria;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaRodadaRepository implements RodadaRepository {

    private static MemoriaRodadaRepository soleInstance;
    @SuppressWarnings("FieldMayBeFinal")
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
    @SuppressWarnings("CollectionsToArray")
public Rodada[] getPorJogador(Jogador jogador) {
    List<Rodada> encontradas = new ArrayList<>();
    for (Rodada rodada : pool) {
        if (rodada.getJogador().getId() == jogador.getId()) {
            encontradas.add(rodada);
        }
    }
    return encontradas.toArray(new Rodada[0]);
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