/**
 * Représenter une matrice
 * @autor Tania Nunez
 * @autor Magali Egger
 */


import java.util.Random;
import java.lang.Math;

public class Matrix {

    private int n;
    private int m;
    private int modulus;
    int[][] matrice;

    public int getN() { return n; }
    public int getM() { return m; }
    public int getModulus() { return modulus; }
    public int[][] getMatrice() { return matrice; }


    /**
     * Constructeur par valeur aléatoire
     * @param n est le nombre de ligne
     * @param m est le nombre de colonne
     * @param modulo les éléments de la matrice ont des valeurs entre 0 et modulo-1
     * @throws RuntimeException indique que le modulo doit être supérieur à zéro
     */
    public Matrix(int n, int m, int modulo) {
        // vérification du modulo
        if (modulo <= 0) {
            throw new RuntimeException("The modulus must be greater than 0.");
        }
        // vérification des tailles m et n
        if(n <= 0 || m <= 0){
            throw new RuntimeException("The sizes of the matrix must be greater than 0");
        }

        try {
            this.n = n;
            this.m = m;
            this.modulus = modulo;
            int[][] mat = new int[n][m];

            // chaque élément de la matrice prend une valeur random entre 0 et modulo-1
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    Random rand = new Random();
                    mat[i][j] = rand.nextInt(modulo);
                }
            }
            matrice = mat;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Constructeur de matrice par valeurs fournies
     * @param m est le nombre de colonne
     * @param val est le tableau contenant les valeurs des éléments de la matrice
     * @throws RuntimeException indique que le tableau ne doit par être vide
     * @throws RuntimeException indique que le modulo doit être supérieur à zéro
     */
    public Matrix(int m, int[] val) {
        if (val.length == 0) {
            throw new RuntimeException("You must provide values for the matrix.");
        }

        try {

            int maxVal = val[0];

            for (int x : val) {
                if (x < 0) throw new RuntimeException("Values for the matrix must be positive.");
                maxVal = Math.max(x, maxVal);
            }

            this.n = val.length / m;
            this.m = m;
            this.modulus = maxVal + 1;
            if(n <= 0 || m <= 0){
                throw new RuntimeException("The sizes of the matrix must be greater than 0");
            }

            matrice = new int[n][m];

            for (int i = 0; i < n; i++) {
                System.arraycopy(val, i * m, matrice[i], 0, m);
            }
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Représente la matrice sous forme de string afin de permettre son affichage
     * @return les éléments de la matrice sous forme d'un String
     */
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append("| ");
            for (int j = 0; j < m; j++) {
                result.append(matrice[i][j]).append(" ");
            }
            result.append("|\n");
        }
        result.append("\n");
        return result.toString();
    }

    /**
     * Permet de créer la matrice de résultat de la bonne taille
     * @param other est la matrice servant de second opérande à l'opération
     * @return une matrice qui contiendra le résultat d'une opération
     */
    private Matrix getResultMatrix(Matrix other) {
        int n = Math.max(this.n, other.n);
        int m = Math.max(this.m, other.m);
        int[] tab = new int[n * m];
        return new Matrix(m, tab);
    }

    /**
     * Effectuer des opérations sur la matrice
     * @param other est la seconde matrice avec laquelle se fera l'opération
     * @param op est l'opération choisie
     * @return stock le résultat de l'opération sous forme d'une nouvelle matrice
     * @throws RuntimeException indique que les modulos ne sont pas compatibles
     */
    private Matrix operation(Matrix other, MatrixOperation op) {
        if (this.modulus != other.modulus) {
            throw new RuntimeException("Modulus are not compatible.");
        }
        try {
            Matrix m1 = getResultMatrix(other);
            Matrix m2 = getResultMatrix(other);

            for (int i = 0; i < this.n; i++) {
                if (this.m >= 0) System.arraycopy(this.matrice[i], 0, m1.matrice[i], 0, this.m);
            }
            for (int i = 0; i < other.n; i++) {
                if (other.m >= 0) System.arraycopy(other.matrice[i], 0, m2.matrice[i], 0, other.m);
            }


            m1.modulus = this.modulus;
            m2.modulus = this.modulus;
            return op.operation(m1, m2);

        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Opération d'addition
     * @param other est la matrice servant de second opérande à l'opération
     * @return la matrice qui contient le résultat de l'opération
     */
    public Matrix addTo(Matrix other) {
        MatrixOperation op = new Addition();
        return this.operation(other, op);
    }

    /**
     * Opréation de soustraction
     * @param other est la matrice servant de second opérande à l'opération
     * @return la matrice qui contient le résultat de l'opération
     */
    public Matrix subtractTo(Matrix other) {
        MatrixOperation op = new Subtraction();
        return this.operation(other, op);
    }

    /**
     * Opréation de multiplication
     * @param other est la matrice servant de second opérande à l'opération
     * @return la matrice qui contient le résultat de l'opération
     */
    public Matrix multBy(Matrix other) {
        MatrixOperation op = new Multiplication();
        return this.operation(other, op);
    }


    public static void main(String[] args) {

        int n1 = 3, m1 = 4;
        int n2 = 4, m2 = 5;
        int modulus = 5;

        System.out.println("Normal behaviours for operations between two matrixes :");

        System.out.println("one");
        //Matrix mat1 = new Matrix(n1, m1, modulus); // Example values for 2nd constructor
        //Matrix mat1 = new Matrix(m1, new int[]{1, 3, 1, 1, 3, 2, 4, 2, 1, 0, 1} );
        Matrix mat1 = new Matrix(m1, new int[10]);

        System.out.println(mat1);

        System.out.println("two");
        //Matrix mat2 = new Matrix(n2, m2, modulus);  // Example values for 2nd constructor
        //Matrix mat2 = new Matrix(m2, new int[]{1, 4, 2, 3, 2, 0, 1, 0, 4, 2, 0, 0, 2, 0, 2});
        Matrix mat2 = new Matrix(m2, new int[10]);


        System.out.println(mat2);

        System.out.println("one + two");
        System.out.println(mat1.addTo(mat2));

        System.out.println("one - two");
        System.out.println(mat1.subtractTo(mat2));

        System.out.println("one x two");
        System.out.println(mat1.multBy(mat2));

        // Abnormal behaviour


        int n3 = 2, m3 = 5;
        int n4 = 4, m4 = 3;
        int modulus1 = 5;
        int modulus2 = 4;

        System.out.println("Operations between two uncompatible matrixes :");
        System.out.println("one");
        Matrix mat3 = new Matrix(n3, m3, modulus1);
        System.out.println(mat3);

        System.out.println("two");
        Matrix mat4 = new Matrix(n4, m4, modulus2);
        System.out.println(mat4);

        System.out.println("one + two");
        System.out.println(mat3.addTo(mat4));

        System.out.println("one - two");
        System.out.println(mat3.subtractTo(mat4));

        System.out.println("one x two");
        System.out.println(mat3.multBy(mat4));
    }
}
