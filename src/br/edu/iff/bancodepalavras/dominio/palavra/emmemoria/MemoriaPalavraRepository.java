package br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaPalavraRepository implements PalavraRepository {

    private static MemoriaPalavraRepository soleInstance;
    private List<Palavra> pool = new ArrayList<>();
    private long idCounter = 1;

    private MemoriaPalavraRepository() {}

    // É EXATAMENTE ESSE MÉTODO AQUI QUE O JAVA ESTAVA SENTINDO FALTA!
    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaPalavraRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return idCounter++;
    }

    @Override
    public Palavra getPorId(long id) {
        for (Palavra palavra : pool) {
            if (palavra.getId() == id) return palavra;
        }
        return null;
    }

    @Override
    public Palavra[] getPorTema(Tema tema) {
        List<Palavra> encontradas = new ArrayList<>();
        for (Palavra palavra : pool) {
            if (palavra.getTema().equals(tema)) {
                encontradas.add(palavra);
            }
        }
        return encontradas.toArray(new Palavra[encontradas.size()]);
    }

    @Override
    public Palavra[] getTodas() {
        return pool.toArray(new Palavra[pool.size()]);
    }

    @Override
    public Palavra getPalavra(String stringPalavra) {
        for (Palavra palavra : pool) {
            if (palavra.comparar(stringPalavra)) {
                return palavra;
            }
        }
        return null;
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        if (pool.contains(palavra)) {
            throw new RepositoryException("Palavra já existe no repositório.");
        }
        pool.add(palavra);
    }

    @Override
    public void atualizar(Palavra palavra) throws RepositoryException {
        if (!pool.contains(palavra)) {
            throw new RepositoryException("Palavra não encontrada para atualização.");
        }
    }

    @Override
    public void remover(Palavra palavra) throws RepositoryException {
        if (!pool.remove(palavra)) {
            throw new RepositoryException("Palavra não encontrada para remoção.");
        }
    }
}