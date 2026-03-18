package br.edu.iff.jogoforca.dominio.boneco.imagem;

import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoImagem implements Boneco {
    
    private static BonecoImagem soleInstance;

    private BonecoImagem() {
    }

    public static BonecoImagem getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoImagem();
        }
        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        // Deixado vazio conforme as instruções do projeto.
    }
}