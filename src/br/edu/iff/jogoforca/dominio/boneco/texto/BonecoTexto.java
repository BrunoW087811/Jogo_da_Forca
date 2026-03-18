package br.edu.iff.jogoforca.dominio.boneco.texto;

import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {
    
    private static BonecoTexto soleInstance;

    private BonecoTexto() {
    }

    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTexto();
        }
        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        // O diagrama estipula de 1 (só cabeça) até 10 (corpo completo com pernas)
        switch (partes) {
            case 1 -> System.out.println("cabeça");
            case 2 -> System.out.println("cabeça, olho esquerdo");
            case 3 -> System.out.println("cabeça, olho esquerdo, olho direito");
            case 4 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz");
            case 5 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca");
            case 6 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco");
            case 7 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo");
            case 8 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito");
            case 9 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda");
            case 10 -> System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita");
            default -> { 
                if (partes > 10) {
                    System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita (Enforcado!)");
                }
            }
        }
    }
}