import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.factory.RepositoryFactory;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.JogadorNaoEncontradoException;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;
import java.util.Scanner;

public class App {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) throws Exception {

        // 1. Configura a aplicação (seta factories e cria singletons)
        Aplicacao app = Aplicacao.getSoleInstance();
        app.setTipoRepositoryFactory("memoria");
        app.setTipoElementoGraficoFactory("texto");
        app.setTipoRodadaFactory("sorteio");
        app.configurar();

        RepositoryFactory repoFactory = app.getRepositoryFactory();

        // 2. Cria os serviços de aplicação
        PalavraAppService.createSoleInstance(
            repoFactory.getTemaRepository(),
            repoFactory.getPalavraRepository(),
            app.getPalavraFactory()
        );

        RodadaAppService.createSoleInstance(
            app.getRodadaFactory(),
            repoFactory.getRodadaRepository(),
            repoFactory.getJogadorRepository()
        );

        PalavraAppService palavraService = PalavraAppService.getSoleInstance();
        RodadaAppService rodadaService = RodadaAppService.getSoleInstance();

        // 3. Cria temas e insere no repositório antes de cadastrar palavras
        TemaRepository temaRepo = repoFactory.getTemaRepository();
        JogadorRepository jogadorRepo = repoFactory.getJogadorRepository();
        JogadorFactory jogadorFactory = app.getJogadorFactory();

        Tema temaProg = app.getTemaFactory().getTema("PROGRAMACAO");
        try { temaRepo.inserir(temaProg); } catch (RepositoryException e) {}

        Tema temaAnimal = app.getTemaFactory().getTema("ANIMAL");
        try { temaRepo.inserir(temaAnimal); } catch (RepositoryException e) {}

        System.out.println(">> Cadastrando palavras no banco...");
        palavraService.novaPalavra("JAVA",      temaProg.getId());
        palavraService.novaPalavra("PYTHON",    temaProg.getId());
        palavraService.novaPalavra("INTERFACE", temaProg.getId());
        palavraService.novaPalavra("CACHORRO",  temaAnimal.getId());
        palavraService.novaPalavra("GATO",      temaAnimal.getId());
        palavraService.novaPalavra("ELEFANTE",  temaAnimal.getId());

        // 4. Lê nome e cadastra o jogador antes de criar a rodada
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nBem-vindo ao Jogo da Forca! Digite seu nome: ");
        String nomeJogador = scanner.nextLine().trim();

        Jogador jogador = jogadorRepo.getPorNome(nomeJogador);
        if (jogador == null) {
            jogador = jogadorFactory.getJogador(nomeJogador);
            try { jogadorRepo.inserir(jogador); } catch (RepositoryException e) {}
        }

        // 5. Cria a rodada
        Rodada rodada;
        try {
            rodada = rodadaService.novaRodada(nomeJogador);
        } catch (JogadorNaoEncontradoException e) {
            System.out.println("Erro: jogador não encontrado.");
            scanner.close();
            return;
        }

        if (rodada == null) {
            System.out.println("Erro ao criar a rodada. Verifique se há palavras cadastradas.");
            scanner.close();
            return;
        }

        System.out.println("\nO TEMA DA RODADA É: " + rodada.getTema().getNome());

        // 6. Loop principal
        while (!rodada.encerrou()) {
            System.out.println();
            rodada.exibirBoneco(null);

            System.out.println("\nPalavras:");
            rodada.exibirItens(null);

            System.out.println("Erros: " + rodada.getQtdeErros() + "/" + Rodada.getMaxErros());
            System.out.print("Letras erradas: ");
            rodada.exibirLetrasErradas(null);
            System.out.println("\nPontos parciais: " + rodada.calcularPontos());

            System.out.print("\nDigite uma letra (ou 'CHUTAR' para arriscar): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("CHUTAR")) {
                String[] chutes = new String[rodada.getNumPalavras()];
                for (int i = 0; i < rodada.getNumPalavras(); i++) {
                    System.out.print("Palavra " + (i + 1) + ": ");
                    chutes[i] = scanner.nextLine().trim();
                }
                rodada.arriscar(chutes);
            } else if (!input.isEmpty()) {
                rodada.tentar(Character.toLowerCase(input.charAt(0)));
            }
        }

        // 7. Resultado final
        System.out.println();
        System.out.println("FIM DE JOGO");
        rodada.exibirBoneco(null);
        System.out.println("\nPalavras:");
        rodada.exibirPalavras(null);

        if (rodada.descobriu()) {
            System.out.println("\nPARABÉNS, " + nomeJogador + "! VOCÊ VENCEU!");
        } else {
            System.out.println("\nVOCÊ FOI ENFORCADO!");
        }

        System.out.println("Pontuação desta rodada : " + rodada.calcularPontos());
        System.out.println("Pontuação total        : " + rodada.getJogador().getPontuacao());

        rodadaService.salvarRodada(rodada);
        scanner.close();
    }
}
