import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.factory.RepositoryFactory;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.JogadorNaoEncontradoException;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;
import java.util.Scanner;

public class App {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) throws Exception {

        // Configura o funcionamento interno do jogo para usar memória e textos no console
        Aplicacao app = Aplicacao.getSoleInstance();
        app.setTipoRepositoryFactory("memoria");
        app.setTipoElementoGraficoFactory("texto");
        app.setTipoRodadaFactory("sorteio");
        app.configurar();

        RepositoryFactory fabricaDeRepositorios = app.getRepositoryFactory();

        PalavraAppService.createSoleInstance(
            fabricaDeRepositorios.getTemaRepository(),
            fabricaDeRepositorios.getPalavraRepository(),
            app.getPalavraFactory()
        );
        RodadaAppService.createSoleInstance(
            app.getRodadaFactory(),
            fabricaDeRepositorios.getRodadaRepository(),
            fabricaDeRepositorios.getJogadorRepository()
        );

        PalavraAppService gerenteDePalavras = PalavraAppService.getSoleInstance();
        RodadaAppService gerenteDeRodadas = RodadaAppService.getSoleInstance();
        TemaRepository repositorioDeTemas = fabricaDeRepositorios.getTemaRepository();
        JogadorRepository repositorioDeJogadores = fabricaDeRepositorios.getJogadorRepository();

        // Cadastra os temas e palavras iniciais para o jogo não começar vazio
        Tema temaProg = app.getTemaFactory().getTema("PROGRAMACAO");
        Tema temaAnimal = app.getTemaFactory().getTema("ANIMAL");

        try { repositorioDeTemas.inserir(temaProg); } catch (RepositoryException e) {}
        try { repositorioDeTemas.inserir(temaAnimal); } catch (RepositoryException e) {}

        System.out.println("Preparando o dicionário de palavras...");
        gerenteDePalavras.novaPalavra("JAVA",      temaProg.getId());
        gerenteDePalavras.novaPalavra("PYTHON",    temaProg.getId());
        gerenteDePalavras.novaPalavra("INTERFACE", temaProg.getId());
        gerenteDePalavras.novaPalavra("CACHORRO",  temaAnimal.getId());
        gerenteDePalavras.novaPalavra("GATO",      temaAnimal.getId());
        gerenteDePalavras.novaPalavra("ELEFANTE",  temaAnimal.getId());

        Scanner teclado = new Scanner(System.in);
        System.out.print("\nOi! Bem-vindo ao Jogo da Forca. Qual é o seu nome? ");
        String nomeDoJogador = teclado.nextLine().trim();

        // Busca o jogador ou cria um novo caso seja a primeira vez jogando
        Jogador jogadorAtual = repositorioDeJogadores.getPorNome(nomeDoJogador);
        
        if (jogadorAtual == null) {
            jogadorAtual = app.getJogadorFactory().getJogador(nomeDoJogador);
            try { repositorioDeJogadores.inserir(jogadorAtual); } catch (RepositoryException e) {}
        }

        boolean querJogarDeNovo = true;

        while (querJogarDeNovo) {
            Rodada rodadaAtual;

            try {
                rodadaAtual = gerenteDeRodadas.novaRodada(nomeDoJogador);
            } catch (JogadorNaoEncontradoException e) {
                System.out.println("Não consegui encontrar o seu cadastro. Encerrando o jogo.");
                teclado.close();
                return;
            }

            if (rodadaAtual == null) {
                System.out.println("Ocorreu um erro ao criar a rodada. Verifique se existem palavras cadastradas.");
                teclado.close();
                return;
            }

            System.out.println("\nVamos começar! O tema sorteado para você é: " + rodadaAtual.getTema().getNome());

            // Mantém a rodada rodando enquanto o jogador não ganhar ou for enforcado
            while (!rodadaAtual.encerrou()) {
                System.out.println();
                rodadaAtual.exibirBoneco(null);

                System.out.println("\nPalavra(s):");
                rodadaAtual.exibirItens(null);

                System.out.println("Cuidado, você já errou " + rodadaAtual.getQtdeErros() + " de " + Rodada.getMaxErros() + " tentativas.");
                System.out.print("Letras erradas até agora: ");
                rodadaAtual.exibirLetrasErradas(null);
                
                System.out.println("\nPontuação parcial: " + rodadaAtual.calcularPontos() + " pts");
                
                System.out.print("\nDigite uma letra (ou escreva 'CHUTAR' se quiser adivinhar a palavra inteira): ");
                String acaoDoJogador = teclado.nextLine().trim();

                if (acaoDoJogador.equalsIgnoreCase("CHUTAR")) {
                    String[] palpites = new String[rodadaAtual.getNumPalavras()];
                    for (int i = 0; i < rodadaAtual.getNumPalavras(); i++) {
                        System.out.print("Qual é a sua aposta para a palavra " + (i + 1) + "? ");
                        palpites[i] = teclado.nextLine().trim();
                    }
                    rodadaAtual.arriscar(palpites);

                } else if (!acaoDoJogador.isEmpty()) {
                    char letraTentada = Character.toLowerCase(acaoDoJogador.charAt(0));
                    rodadaAtual.tentar(letraTentada);
                }
            }

            System.out.println("\nA rodada acabou!");
            rodadaAtual.exibirBoneco(null);
            
            System.out.println("\nAs palavras corretas eram:");
            rodadaAtual.exibirPalavras(null);

            if (rodadaAtual.descobriu()) {
                System.out.println("\nParabéns, " + nomeDoJogador + "! Você acertou tudo e venceu a rodada.");
            } else {
                System.out.println("\nVocê foi enforcado.");
            }

            System.out.println("Pontos ganhos nesta rodada: " + rodadaAtual.calcularPontos());
            System.out.println("Sua pontuação total acumulada: " + rodadaAtual.getJogador().getPontuacao());

            gerenteDeRodadas.salvarRodada(rodadaAtual);

            // Coleta e exibe o ranking ordenado de todos os jogadores
            System.out.println("\nVeja como está o placar do jogo até agora:");
            
            Jogador[] rankingDeJogadores = fabricaDeRepositorios.getJogadorRepository().getTodos();
            
            if (rankingDeJogadores != null && rankingDeJogadores.length > 0) {
                java.util.Arrays.sort(rankingDeJogadores, (jogadorA, jogadorB) -> jogadorB.getPontuacao() - jogadorA.getPontuacao());
                
                int posicaoNoRanking = 1;
                for (Jogador j : rankingDeJogadores) {
                    System.out.printf("%dº lugar: %-20s | %d pts:%n", posicaoNoRanking++, j.getNome(), j.getPontuacao());
                }
            } else {
                System.out.println("Nenhum ponto registrado ainda.");
            }

            System.out.print("\nQuer jogar mais uma partida? (S/N): ");
            String resposta = teclado.nextLine().trim();
            querJogarDeNovo = resposta.equalsIgnoreCase("S");
        }

        System.out.println("\nObrigado por jogar! Até a próxima.");
        teclado.close();
    }
}