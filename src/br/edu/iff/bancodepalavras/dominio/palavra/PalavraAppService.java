package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class PalavraAppService {

    private static PalavraAppService soleInstance;
    private final PalavraRepository palavraRepository;
    private final PalavraFactory palavraFactory;
    private final TemaRepository temaRepository;
    private final TemaFactory temaFactory;

    private PalavraAppService(PalavraRepository palavraRepository, PalavraFactory palavraFactory, TemaRepository temaRepository, TemaFactory temaFactory) {
        this.palavraRepository = palavraRepository;
        this.palavraFactory = palavraFactory;
        this.temaRepository = temaRepository;
        this.temaFactory = temaFactory;
    }

    public static void createSoleInstance(PalavraRepository palavraRepository, PalavraFactory palavraFactory, TemaRepository temaRepository, TemaFactory temaFactory) {
        if (soleInstance == null) {
            soleInstance = new PalavraAppService(palavraRepository, palavraFactory, temaRepository, temaFactory);
        }
    }

    public static PalavraAppService getSoleInstance() {
        return soleInstance;
    }

    public boolean novaPalavra(String stringPalavra, String nomeTema) throws RepositoryException {
        // Se a palavra já existe no repositório, não recriamos
        if (palavraRepository.getPalavra(stringPalavra) != null) {
            return false;
        }

        Tema tema;
        Tema[] temasEncontrados = temaRepository.getPorNome(nomeTema);
        
        // Se o tema não existe, a gente cria e já salva ele no banco
        if (temasEncontrados == null || temasEncontrados.length == 0) {
            tema = temaFactory.getTema(nomeTema);
            temaRepository.inserir(tema);
        } else {
            // Se já existe, pegamos o primeiro da lista
            tema = temasEncontrados[0];
        }

        // Finalmente, cria a palavra e insere no repositório
        Palavra palavra = palavraFactory.getPalavra(stringPalavra, tema);
        palavraRepository.inserir(palavra);
        
        return true;
    }
}