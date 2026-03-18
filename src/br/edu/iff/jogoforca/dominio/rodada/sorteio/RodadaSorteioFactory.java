package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class RodadaSorteioFactory extends RodadaFactoryImpl {
    
    private static RodadaSorteioFactory soleInstance;

    public static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        if (soleInstance == null) {
            soleInstance = new RodadaSorteioFactory(repository, temaRepository, palavraRepository);
        }
    }

    public static RodadaSorteioFactory getSoleInstance() {
        return soleInstance;
    }

    private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(repository, temaRepository, palavraRepository);
    }

    @Override
    public Rodada getRodada(Jogador jogador) {
        // 1. Pega todos os temas salvos no banco
        Tema[] todosOsTemas = getTemaRepository().getTodas();
        if (todosOsTemas == null || todosOsTemas.length == 0) return null; // Prevenção de erro

        // 2. Sorteia um índice aleatório para escolher o tema
        int indexTema = (int) (Math.random() * todosOsTemas.length);
        Tema temaSorteado = todosOsTemas[indexTema];

        // 3. Pega todas as palavras associadas ao tema sorteado
        Palavra[] palavrasDoTema = getPalavraRepository().getPorTema(temaSorteado);
        if (palavrasDoTema == null || palavrasDoTema.length == 0) return null;

        // 4. Sorteia no máximo 3 palavras (limite da rodada) sem repetições
        int qtdSorteada = Math.min(3, palavrasDoTema.length); 
        Palavra[] sorteadas = new Palavra[qtdSorteada];
        
        // Usamos as bibliotecas do Java apenas para embaralhar a lista de palavras
        java.util.List<Palavra> listaParaEmbaralhar = new java.util.ArrayList<>(java.util.Arrays.asList(palavrasDoTema));
        java.util.Collections.shuffle(listaParaEmbaralhar);
        
        for (int i = 0; i < qtdSorteada; i++) {
            sorteadas[i] = listaParaEmbaralhar.get(i);
        }

        // 5. Retorna a rodada criada chamando aquele método estático que fizemos no Passo 1
        return Rodada.criar(getProximoId(), sorteadas, jogador);
    }
}