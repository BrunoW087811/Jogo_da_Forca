import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.factory.RepositoryFactory;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        Aplicacao app = Aplicacao.getSoleInstance();
        app.setTipoRepositoryFactory("memoria"); 
        app.setTipoElementoGraficoFactory("texto");
        app.setTipoRodadaFactory("sorteio"); 

        RepositoryFactory repoFactory = app.getRepositoryFactory();

        TemaFactoryImpl.createSoleInstance(repoFactory.getTemaRepository());
        PalavraFactoryImpl.createSoleInstance(repoFactory.getPalavraRepository());
        JogadorFactoryImpl.createSoleInstance(repoFactory.getJogadorRepository());
        RodadaSorteioFactory.createSoleInstance(
            repoFactory.getRodadaRepository(), 
            repoFactory.getTemaRepository(), 
            repoFactory.getPalavraRepository()
        );

        PalavraAppService.createSoleInstance(
            repoFactory.getPalavraRepository(), 
            app.getPalavraFactory(), 
            repoFactory.getTemaRepository(), 
            app.getTemaFactory()
        );

        RodadaAppService.createSoleInstance(
            repoFactory.getRodadaRepository(), 
            app.getRodadaFactory(), 
            repoFactory.getJogadorRepository(), 
            app.getJogadorFactory()
        );

        PalavraAppService palavraService = PalavraAppService.getSoleInstance();
        RodadaAppService rodadaService = RodadaAppService.getSoleInstance();


        System.out.println(">> Cadastrando palavras no banco...");
        palavraService.novaPalavra("JAVA", "PROGRAMACAO");
        palavraService.novaPalavra("PYTHON", "PROGRAMACAO");
        palavraService.novaPalavra("INTERFACE", "PROGRAMACAO");
        palavraService.novaPalavra("CACHORRO", "ANIMAL");
        palavraService.novaPalavra("GATO", "ANIMAL");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nBem-vindo ao Jogo da Forca! Digite seu nome: ");
        String nomeJogador = scanner.nextLine();

        Rodada rodada = rodadaService.novaRodada(nomeJogador);

        if (rodada == null) {
            System.out.println("Erro ao criar a rodada. Verifique se há palavras cadastradas.");
            return;
        }

        System.out.println("\nO TEMA DA RODADA É: " + rodada.getTema().getNome().toUpperCase());

        while (!rodada.encerrou()) {
            System.out.println("\n=============================================");
            rodada.exibirBoneco(null);
            
            System.out.println("\nPalavras:");
            rodada.exibirItens(null);

            System.out.println("\nErros: " + rodada.getQtdeErros() + "/10");
            System.out.print("Letras erradas: ");
            rodada.exibirLetrasErradas(null);
            System.out.println("\nPontos nesta rodada: " + rodada.calcularPontos());

            System.out.print("\nDigite uma letra (ou digite 'CHUTAR' para arriscar): ");
            // Agora pegamos o input limpo, sem forçar maiúscula ainda
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("CHUTAR")) {
                String[] chutes = new String[rodada.getNumPalavras()];
                for (int i = 0; i < rodada.getNumPalavras(); i++) {
                    System.out.print("Arrisque a palavra " + (i + 1) + ": ");
                    chutes[i] = scanner.nextLine().trim();
                }
                rodada.arriscar(chutes);
            } else if (input.length() > 0) {
                // Envia a letra MINÚSCULA para a lógica da fábrica não quebrar!
                rodada.tentar(Character.toLowerCase(input.charAt(0))); 
            }
        }

        System.out.println("\nFIM DE JOGO");
        rodada.exibirBoneco(null);
        System.out.println("");
        // Agora revelamos os itens no final com a formatação bonitinha também
        rodada.exibirItens(null);
        System.out.println("");

        if (rodada.descobriu()) {
            System.out.println("\nPARABÉNS, " + nomeJogador + "! VOCÊ VENCEU!");
        } else {
            System.out.println("\nVOCÊ FOI ENFORCADO!");
            System.out.println("As palavras corretas eram:");
            for (br.edu.iff.bancodepalavras.dominio.palavra.Palavra p : rodada.getPalavras()) {
                System.out.print("- ");
                for (int i = 0; i < p.getTamanho(); i++) {
                    System.out.print(p.getLetra(i).getCodigo());
                }
                System.out.println();
            }
        }

        System.out.println("Sua pontuação final: " + rodada.calcularPontos());
        
        rodadaService.salvarRodada(rodada);
        scanner.close();
    }
}