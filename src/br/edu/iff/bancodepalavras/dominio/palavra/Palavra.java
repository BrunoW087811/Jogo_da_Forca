package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.Aplicacao; // Importando o Cérebro do Jogo!

public class Palavra extends ObjetoDominioImpl {
    
    @SuppressWarnings("FieldMayBeFinal")
    private Tema tema;
    @SuppressWarnings("FieldMayBeFinal")
    private Letra[] letras; 

    protected Palavra(long id, String palavra, Tema tema) {
        super(id);
        this.tema = tema;
        this.letras = new Letra[palavra.length()];
        
        for (int i = 0; i < palavra.length(); i++) {
            // 1. Transformamos a letra em minúscula para a fábrica não bugar na conta matemática
            char charMinusculo = Character.toLowerCase(palavra.charAt(i));
            
            // 2. Puxamos a letra da fábrica correta que a Aplicação escolheu!
            this.letras[i] = Aplicacao.getSoleInstance().getLetraFactory().getLetra(charMinusculo);
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
                letras[i].exibir(contexto); 
            } else {
                // Puxamos o asterisco da fábrica correta também
                Aplicacao.getSoleInstance().getLetraFactory().getLetraEncoberta().exibir(contexto);
            }
        }
    }
}