package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class Letra {

    @SuppressWarnings("FieldMayBeFinal")
    private char codigo;

    protected Letra(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    public abstract void exibir(Object contexto);

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Letra)) return false;
        Letra outra = (Letra) o;
        return this.codigo == outra.codigo && this.getClass().equals(outra.getClass());
    }

    @Override
    public final int hashCode() {
        return this.codigo + this.getClass().hashCode();
    }

    @Override
    public final String toString() {
        return String.valueOf(codigo);
    }
}
