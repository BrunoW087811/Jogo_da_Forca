package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class LetraFactoryImpl implements LetraFactory {
    
    @SuppressWarnings("FieldMayBeFinal")
    private Letra[] pool; 
    private Letra encoberta; 

    protected LetraFactoryImpl() {
        this.pool = new Letra[26]; // Para as 26 letras do alfabeto 
        this.encoberta = null;
    }

    @Override
    public final Letra getLetra(char codigo) { 
        int indice = codigo - 'a';
        if (indice < 0 || indice >= 26) return null;

        if (pool[indice] == null) {
            pool[indice] = criarLetra(codigo); 
        }
        return pool[indice];
    }

    @Override
    public final Letra getLetraEncoberta() { 
        if (encoberta == null) {
            encoberta = criarLetra('*'); // Ou o caractere definido para esconder 
        }
        return encoberta;
    }

    // Método que as subclasses (Texto/Imagem) devem implementar 
    protected abstract Letra criarLetra(char codigo); 
}