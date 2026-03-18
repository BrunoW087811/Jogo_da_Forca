package br.edu.iff.jogoforca.texto;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.texto.LetraTextoFactory;
import br.edu.iff.jogoforca.ElementoGraficoFactory;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.texto.BonecoTextoFactory;

public class ElementoGraficoTextoFactory implements ElementoGraficoFactory {
    
    private static ElementoGraficoTextoFactory soleInstance;
    
    // Agregações das fábricas específicas
    @SuppressWarnings("FieldMayBeFinal")
    private BonecoTextoFactory bonecoFactory;
    @SuppressWarnings("FieldMayBeFinal")
    private LetraTextoFactory letraFactory;

    private ElementoGraficoTextoFactory() {
        // Acessando os singletons e setando as agregações conforme a nota do diagrama
        this.bonecoFactory = BonecoTextoFactory.getSoleInstance();
        this.letraFactory = LetraTextoFactory.getSoleInstance();
    }

    public static ElementoGraficoTextoFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new ElementoGraficoTextoFactory();
        }
        return soleInstance;
    }

    @Override
    public Boneco getBoneco() {
        return bonecoFactory.getBoneco();
    }

    @Override
    public Letra getLetra(char codigo) {
        return letraFactory.getLetra(codigo);
    }

    @Override
    public Letra getLetraEncoberta() {
        return letraFactory.getLetraEncoberta();
    }
}