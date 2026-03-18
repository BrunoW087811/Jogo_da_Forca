package br.edu.iff.bancodepalavras.dominio.letra;

import java.util.Objects;

public abstract class Letra {
    
    @SuppressWarnings("FieldMayBeFinal")
    private char codigo;

    protected Letra(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    // O parâmetro 'contexto' permite que a letra seja exibida em diferentes 
    // interfaces (Texto, Web, etc.) [cite: 4, 5]
    public abstract void exibir(Object contexto);

    // No diagrama, Letra possui equals e hashCode para funcionar no Flyweight [cite: 137, 207, 211]
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Letra)) return false;
        Letra outra = (Letra) obj;
        return this.codigo == outra.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}