package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.texto.LetraTextoFactory; 
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {
    
    @SuppressWarnings("FieldMayBeFinal")
    private Tema tema;
    @SuppressWarnings("FieldMayBeFinal")
    private Letra[] letras; 

    protected Palavra(long id, String palavra, Tema tema) {
        super(id);
        this.tema = tema;
        this.letras = new Letra[palavra.length()];
        
        // O diagrama pede para usar a fábrica da classe Aplicacao para instanciar as letras.
        // Como ainda não criamos a Aplicacao, estamos usando a LetraTextoFactory 
        // diretamente aqui para que seu código compile e você possa testar.
        for (int i = 0; i < palavra.length(); i++) {
            this.letras[i] = LetraTextoFactory.getSoleInstance().getLetra(palavra.charAt(i));
        }
    }

    public Tema getTema() {
        return tema; 
    }

    public int getTamanho() {
        return letras.length; 
    }

    public Letra getLetra(int posicao) {
        return letras[posicao]; 
    }

    public boolean comparar(String palavraArriscada) {
        if (palavraArriscada == null || palavraArriscada.length() != this.getTamanho()) {
            return false;
        }
        for (int i = 0; i < letras.length; i++) {
            if (Character.toLowerCase(letras[i].getCodigo()) != Character.toLowerCase(palavraArriscada.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void exibir(Object contexto, boolean[] posicoesDescobertas) {
        for (int i = 0; i < letras.length; i++) {
            if (posicoesDescobertas != null && i < posicoesDescobertas.length && posicoesDescobertas[i]) {
                letras[i].exibir(contexto); // Exibe a letra real se o jogador já descobriu
            } else {
                // Se não descobriu, exibe o caractere oculto (ex: '*')
                LetraTextoFactory.getSoleInstance().getLetraEncoberta().exibir(contexto);
            }
        }
    }
}