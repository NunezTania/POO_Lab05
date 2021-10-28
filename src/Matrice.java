
import java.util.Random;
import java.lang.Math;

public class Matrice {
    private int n;
    private int m;
    private int modulo;

    int[][] matrice;



    // constructeur aléatoire
    Matrice(int n, int m, int modulo) {
        this.n = n;
        this.m = m;
        this.modulo = modulo;
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
        int n = val.length / m;
        this.n = n;
        this.m = m;
        /*
        TODO initialiser le modulo a la valeur max du tableau
        */
        if (val.length == 0) {
            throw new RuntimeException("You must provide valors for the matrix.");
        }
        try {
            int maxVal = val[0];
            for (int x : val) {
                if (x > maxVal) maxVal = x;
            }
            this.modulo = maxVal + 1;

            int indexColonne = 0;
            int indexLigne = 0;

            int[][] mat = new int[n][m];

            // m est le nombre de colonne
            for (int i = 0; i < val.length; i++) {
                mat[indexLigne][indexColonne] = val[i];
                if (indexColonne++ + 1 == m) {
                    indexLigne++;
                    indexColonne = 0;
                }
            }
            matrice = mat;
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }



    // methode affichage
    public String toString(){
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



    // Addition de deux matrices
    public static Matrice operation( Matrice matrice1, Matrice matrice2, Matrice matrice3, int choix) {
        if( matrice1.modulo != matrice2.modulo ) {
            throw new RuntimeException("Modulus are not compatible.");
        }
        // TODO mettre un switch ?
        // TODO corrigé pour avoir meme reponse enonce
        for (int i = 0; i < matrice1.n; i++){
            for(int j = 0; j < matrice1.m; j++){
                if( choix == 0) matrice1.matrice[i][j] = matrice2.matrice[i][j] + matrice3.matrice[i][j] % matrice1.modulo;
                if( choix == 1) matrice1.matrice[i][j] = matrice2.matrice[i][j] - matrice3.matrice[i][j] % matrice1.modulo;
                if( choix == 2) {
                    if( i < matrice2.m  && j < matrice2.n && i < matrice3.m && j < matrice3.n) {
                        matrice1.matrice[i][j] = matrice2.matrice[i][j] * matrice3.matrice[j][i] % matrice1.modulo;
                    } else {
                        matrice1.matrice[i][j] = 0;
                    }
                }
            }
        }
        return matrice1;
    }


    // TODO factoriser les fonction add, soustraire, multi
    public static Matrice addition(Matrice matrice1, Matrice matrice2) {
        Matrice matrice0 = new Matrice( Math.max(matrice2.n, matrice1.n), Math.max(matrice2.m, matrice1.m), matrice2.modulo);
        return operation(matrice0, matrice1, matrice2, 0);
    }


    public static Matrice soustraction(Matrice matrice1, Matrice matrice2) {
        Matrice matrice0 = new Matrice( Math.max(matrice1.n, matrice2.n), Math.max(matrice1.m, matrice2.m), matrice2.modulo);
        return operation(matrice0, matrice1, matrice2, 1);
    }


    public static Matrice multiplication(Matrice matrice1, Matrice matrice2) {
        Matrice matrice0 = new Matrice( Math.max(matrice1.n,matrice2.n), Math.max(matrice1.m,matrice2.m), 5);
        return operation(matrice0, matrice1, matrice2, 2);
    }

    public Matrice addTo(Matrice other) {
        Matrice result = new Matrice(this.n, this.m, this.modulo);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                result.matrice[i][j] = Math.floorMod(this.matrice[i][j] + other.matrice[i][j], this.modulo);
            }

        }
        return result;
    }

    public Matrice subtractTo(Matrice other) {
        Matrice result = new Matrice(this.n, this.m, this.modulo);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                result.matrice[i][j] = Math.floorMod(this.matrice[i][j] - other.matrice[i][j], this.modulo);
            }

        }
        return result;
    }

    public Matrice multBy(Matrice other) {
        Matrice result = new Matrice(this.n, this.m, this.modulo);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                result.matrice[i][j] = Math.floorMod(this.matrice[i][j] * other.matrice[i][j], this.modulo);
            }

        }
        return result;
    }

    public Matrice getResultMatrix(Matrice other) {
        int n = Math.max(this.n, other.n);
        int m = Math.max(this.m, other.m);
        int[] tab = new int[n * m];
        return new Matrice(m, tab);
    }

    public Matrice operation(Matrice other, int op) {
        if (this.modulo != other.modulo) {
            throw new RuntimeException("Modulus are not compatible.");
        }
        try {

            Matrice operand1 = getResultMatrix(other);
            Matrice operand2 = getResultMatrix(other);
            operand1.modulo = this.modulo;
            operand2.modulo = this.modulo;
            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.m; j++) {
                    operand1.matrice[i][j] = this.matrice[i][j];
                }
            }
            for (int i = 0; i < other.n; i++) {
                for (int j = 0; j < other.m; j++) {
                    operand2.matrice[i][j] = other.matrice[i][j];
                }
            }

            switch(op) {
                case 0:
                    return operand1.addTo(operand2);
                case 1:
                    return operand1.subtractTo(operand2);
                case 2:
                    return operand1.multBy(operand2);
            }
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        /*
       // test constructeur sans para
        System.out.println("constructeur");
        Matrice m = new Matrice(2, 3, 10);
        System.out.println(m);

        // test constructeur avec para
        System.out.println("---------------------");
        System.out.println("constructeur");
        int[] tab = new int[]{6,7,10,1,8,1};
        Matrice test = new Matrice(3 , tab );
        System.out.println(test);

        // test addition
        System.out.println("---------------------");
        System.out.println("addition");
        int[] tab2 = new int[]{5,2,10,1,3,1};
        Matrice test2 = new Matrice(3 , tab2 );
        System.out.println(test);
        System.out.println(test2);
        m = addition(test, test2);
        System.out.println(m);

        // test soustraction
        System.out.println("---------------------");
        System.out.println("soustraction");
        Matrice w;
        Matrice test3 = new Matrice(3 , tab2 );
        System.out.println(test);
        System.out.println(test3);
        w = soustraction(test, test3);
        System.out.println(w);

        // test multiplication
        System.out.println("---------------------");
        System.out.println("multiplication");
        int[] arr1 = new int[]{1,3,1,1,3,2,4,2,1,0,1,0};
        Matrice w1 = new Matrice(4 , arr1);
        int[] arr2 = new int[]{1,4,2,3,2,0,1,0,4,2,0,0,2,0,2};
        Matrice w2 = new Matrice(5 , arr2);
        Matrice w3 = multiplication(w2,w1);
        System.out.println(w1);
        System.out.println(w2);
        System.out.println(w3);*/
        System.out.println("one");
        Matrice m1 = new Matrice(4, new int[]{1,3,1,1,3,2,4,2,1,0,1,0});
        System.out.println(m1);

        System.out.println("two");
        Matrice m2 = new Matrice(5, new int[]{1,4,2,3,2,0,1,0,4,2,0,0,2,0,2});
        System.out.println(m2);

        System.out.println("one + two");
        System.out.println(m1.operation(m2, 0));

        System.out.println("one - two");
        System.out.println(m1.operation(m2, 1));

        System.out.println("one x two");
        System.out.println(m1.operation(m2, 2));
    }
}
