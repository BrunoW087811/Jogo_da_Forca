package br.edu.iff.bancodepalavras.dominio.palavra.embdr;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;

public class BDRPalavraRepository implements PalavraRepository {

    private static BDRPalavraRepository soleInstance;
    private long idCounter = 1;

    private BDRPalavraRepository() {}

    public static BDRPalavraRepository getSoleInstance() {
        if (soleInstance == null) soleInstance = new BDRPalavraRepository();
        return soleInstance;
    }

    @Override public long getProximoId() { return idCounter++; }
    @Override public Palavra getPorId(long id) { return null; }
    @Override public Palavra[] getPorTema(Tema tema) { return new Palavra[0]; }
    @Override public Palavra[] getTodas() { return new Palavra[0]; }
    @Override public Palavra getPalavra(String palavra) { return null; }
    @Override public void inserir(Palavra palavra) throws RepositoryException {}
    @Override public void atualizar(Palavra palavra) throws RepositoryException {}
    @Override public void remover(Palavra palavra) throws RepositoryException {}
}
