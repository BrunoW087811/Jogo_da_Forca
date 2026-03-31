package br.edu.iff.bancodepalavras.dominio.tema.emmemoria;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaTemaRepository implements TemaRepository {

    private static MemoriaTemaRepository soleInstance;
    @SuppressWarnings("FieldMayBeFinal")
    private List<Tema> pool = new ArrayList<>();
    private long idCounter = 1;

    private MemoriaTemaRepository() {}

    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaTemaRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return idCounter++;
    }

    @Override
    public Tema getPorId(long id) {
        for (Tema tema : pool) {
            if (tema.getId() == id) return tema;
        }
        return null;
    }

    @Override
    @SuppressWarnings("CollectionsToArray")
    public Tema[] getPorNome(String nome) {
        List<Tema> encontrados = new ArrayList<>();
        for (Tema tema : pool) {
            if (tema.getNome().equalsIgnoreCase(nome)) {
                encontrados.add(tema);
            }
        }
        return encontrados.toArray(new Tema[0]);
    }

    @Override
    @SuppressWarnings("CollectionsToArray")
    public Tema[] getTodos() {
        return pool.toArray(new Tema[0]);
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        if (pool.contains(tema)) {
            throw new RepositoryException("Tema já existe no repositório.");
        }
        pool.add(tema);
    }

    @Override
    public void atualizar(Tema tema) throws RepositoryException {
        if (!pool.contains(tema)) {
            throw new RepositoryException("Tema não encontrado para atualização.");
        }
    }

    @Override
    public void remover(Tema tema) throws RepositoryException {
        if (!pool.remove(tema)) {
            throw new RepositoryException("Tema não encontrado para remoção.");
        }
    }
}
