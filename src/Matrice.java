import java.util.Random;

public class Matrice {
    private int n;
    private int m;

    int[][] matrice;

    // constructeur aléatoire
    Matrice(int n, int m, int modulo) {
        this.n = n;
        this.m = m;

        int[][] mat = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Random rand = new Random();
                mat[i][j] = rand.nextInt(modulo);
            }
        }
        matrice = mat;
    }


    // Constructeur par valeur
    Matrice( int m, int[] val){


        int indexColonne = 0;
        int indexLigne = 0;

        int n = val.length / m;
        this.n = n;
        this.m = m;
        int[][] mat = new int[n][m];

        // m est le nombre de colonne
        for (int i = 0; i < val.length; i++) {
            mat[indexLigne][indexColonne] = val[i];

            if ( indexColonne++ + 1 == m) {
                indexLigne++;
                indexColonne = 0;
            }
        }
        matrice = mat;
    }



    // methode affichage
    public String toString(){

        // a garder ?
        super.toString();

        String result = "";
        for (int i = 0; i < n; i++) {
            result += "[ ";
            for (int j = 0; j < m; j++) {
                result += matrice[i][j] + " ";
            }
            result += "]\n";
        }
        result += "\n";
        return result;
    }



    public static void main(String[] args){
        Matrice m = new Matrice(3, 2, 10);
        System.out.println(m);

        int[] tab = new int[]{6,7,10,11,8,1};
        Matrice test = new Matrice(3 , tab );
        System.out.println(test);
    }

}
