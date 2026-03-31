package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class PalavraAppService {

    private static PalavraAppService soleInstance;

    private final PalavraRepository palavraRepository;
    private final PalavraFactory palavraFactory;
    private final TemaRepository temaRepository;

    private PalavraAppService(TemaRepository temaRepository,
                               PalavraRepository palavraRepository,
                               PalavraFactory palavraFactory) {
        this.temaRepository = temaRepository;
        this.palavraRepository = palavraRepository;
        this.palavraFactory = palavraFactory;
    }

    public static void createSoleInstance(TemaRepository temaRepository,
                                           PalavraRepository palavraRepository,
                                           PalavraFactory palavraFactory) {
        if (soleInstance == null) {
            soleInstance = new PalavraAppService(temaRepository, palavraRepository, palavraFactory);
        }
    }

    public static PalavraAppService getSoleInstance() {
        return soleInstance;
    }


    public boolean novaPalavra(String palavra, long idTema) {
        if (palavraRepository.getPalavra(palavra) != null) {
            return true;
        }

        Tema tema = temaRepository.getPorId(idTema);
        if (tema == null) {
            return false;
        }

        try {
            Palavra novaPalavra = palavraFactory.getPalavra(palavra, tema);
            palavraRepository.inserir(novaPalavra);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}
