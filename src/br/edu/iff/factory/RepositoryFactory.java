package br.edu.iff.factory;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public interface RepositoryFactory {
    PalavraRepository getPalavraRepository();
    TemaRepository getTemaRepository();
    RodadaRepository getRodadaRepository();
    JogadorRepository getJogadorRepository();
}