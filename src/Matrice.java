import java.util.Random;
import java.util.Vector;

public class Matrice {
    private int n;
    private int m;

    int matrice[][];

    // constructeur aléatoire
    Matrice(int n, int m, int modulo) {
        this.n = n;
        this.m = m;

        int[][] mat = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Random rand = new Random(modulo);
                mat[i][j] = 0;
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
            for (int j = 0; j < m; j++) {
                result += matrice[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args){
        Matrice m = new Matrice(3, 2, 10);
        System.out.println(m);
    }
}
