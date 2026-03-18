package br.edu.iff.dominio;

public abstract class ObjetoDominioImpl implements ObjetoDominio {
    
    @SuppressWarnings("FieldMayBeFinal")
    private long id;

    public ObjetoDominioImpl(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }
}