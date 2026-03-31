package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {

    private static LetraFactory letraFactory;

    @SuppressWarnings("FieldMayBeFinal")
    private Tema tema;
    @SuppressWarnings("FieldMayBeFinal")
    private Letra[] letras;

    private Palavra(long id, String palavra, Tema tema) {
        super(id);
        this.tema = tema;
        this.letras = new Letra[palavra.length()];
        for (int i = 0; i < palavra.length(); i++) {
            char c = Character.toLowerCase(palavra.charAt(i));
            this.letras[i] = getLetraFactory().getLetra(c);
        }
    }

    public static Palavra criar(long id, String palavra, Tema tema) {
        if (letraFactory == null) {
            throw new IllegalStateException("LetraFactory não foi configurada. Chame Palavra.setLetraFactory() antes.");
        }
        return new Palavra(id, palavra, tema);
    }

    public static Palavra reconstituir(long id, String palavra, Tema tema) {
        if (letraFactory == null) {
            throw new IllegalStateException("LetraFactory não foi configurada. Chame Palavra.setLetraFactory() antes.");
        }
        return new Palavra(id, palavra, tema);
    }

    public static LetraFactory getLetraFactory() {
        return letraFactory;
    }

    public static void setLetraFactory(LetraFactory factory) {
        letraFactory = factory;
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

    public Letra[] getLetras() {
        return letras;
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

    public int[] tentar(char codigo) {
        int count = 0;
        for (Letra letra : letras) {
            if (letra.getCodigo() == Character.toLowerCase(codigo)) count++;
        }
        int[] posicoes = new int[count];
        int idx = 0;
        for (int i = 0; i < letras.length; i++) {
            if (letras[i].getCodigo() == Character.toLowerCase(codigo)) {
                posicoes[idx++] = i;
            }
        }
        return posicoes;
    }

    public void exibir(Object contexto, boolean[] posicoesDescobertas) {
        for (int i = 0; i < letras.length; i++) {
            if (posicoesDescobertas != null && i < posicoesDescobertas.length && posicoesDescobertas[i]) {
                letras[i].exibir(contexto);
            } else {
                getLetraFactory().getLetraEncoberta().exibir(contexto);
            }
        }
    }


    public void exibir(Object contexto) {
        for (Letra letra : letras) {
            letra.exibir(contexto);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letra l : letras) sb.append(l.getCodigo());
        return sb.toString();
    }
}
