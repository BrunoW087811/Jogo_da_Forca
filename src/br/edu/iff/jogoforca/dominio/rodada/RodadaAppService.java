package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class RodadaAppService {

    private static RodadaAppService soleInstance;
    private RodadaRepository rodadaRepository;
    private RodadaFactory rodadaFactory;
    private JogadorRepository jogadorRepository;
    private JogadorFactory jogadorFactory;

    private RodadaAppService(RodadaRepository rodadaRepository, RodadaFactory rodadaFactory, JogadorRepository jogadorRepository, JogadorFactory jogadorFactory) {
        this.rodadaRepository = rodadaRepository;
        this.rodadaFactory = rodadaFactory;
        this.jogadorRepository = jogadorRepository;
        this.jogadorFactory = jogadorFactory;
    }

    public static void createSoleInstance(RodadaRepository rodadaRepository, RodadaFactory rodadaFactory, JogadorRepository jogadorRepository, JogadorFactory jogadorFactory) {
        if (soleInstance == null) {
            soleInstance = new RodadaAppService(rodadaRepository, rodadaFactory, jogadorRepository, jogadorFactory);
        }
    }

    public static RodadaAppService getSoleInstance() {
        return soleInstance;
    }

    public Rodada novaRodada(String nomeJogador) throws RepositoryException {
        Jogador jogador = jogadorRepository.getPorNome(nomeJogador);
        
        // Se o jogador não for encontrado no banco, a gente cadastra ele na hora!
        if (jogador == null) {
            jogador = jogadorFactory.getJogador(nomeJogador);
            jogadorRepository.inserir(jogador);
        }

        // Pede para a fábrica criar uma rodada (que já vai fazer aquele sorteio de palavras por debaixo dos panos)
        Rodada rodada = rodadaFactory.getRodada(jogador);
        
        if (rodada != null) {
            rodadaRepository.inserir(rodada);
        }
        
        return rodada;
    }

    public void salvarRodada(Rodada rodada) throws RepositoryException {
        rodadaRepository.atualizar(rodada);
    }
}