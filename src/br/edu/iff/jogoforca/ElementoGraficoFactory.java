package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public interface ElementoGraficoFactory {
    Boneco getBoneco();
    Letra getLetra(char codigo);
    Letra getLetraEncoberta();
}