import java.util.Random;
public class Matrice {
    int n;
    int m;
    //int matrice[][];
    Matrice(int n, int m) {
        this.n = n;
        this.m = m;
        //matrice = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Random rand = new Random(n);
                //matrice[i][j] = java.util.Random.Random(n);
            }
        }
    }
}
