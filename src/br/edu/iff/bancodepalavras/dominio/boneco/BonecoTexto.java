package br.edu.iff.bancodepalavras.dominio.boneco;

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
        switch (partes) {
            case 0:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;

            case 1:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;

            case 2:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;

            case 3:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;

            case 4:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|/");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;

            case 5:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|/  |");
                System.out.println("/    |");
                System.out.println("     |");
                System.out.println("=======");
                break;

            default:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|/  |");
                System.out.println("/ /  |");
                System.out.println("     |");
                System.out.println("=======");
                break;
        }
    }
}