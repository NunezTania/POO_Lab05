/**
 * Classe Matrix
 * @autor Tania Nunez
 * @autor Magali Egger
 */

import java.util.Random;
import java.lang.Math;

public class Matrix {

    private int n;
    private int m;
    private int modulus;
    int[][] matrix;

    public int getN() { return n; }
    public int getM() { return m; }
    public int getModulus() { return modulus; }
    public int[][] getMatrix() { return matrix; }


    /**
     * Constructeur par valeur aléatoire
     * @param n est le nombre de ligne
     * @param m est le nombre de colonne
     * @param modulo les éléments de la matrice ont des valeurs entre 0 et modulus - 1
     * @throws RuntimeException indique si le modulus est inférieur à zéro
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
            matrix = mat;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Constructeur de matrice avec valeurs fournies
     * @param n est le nombre de lignes
     * @param m est le nombre de colonne
     * @param val est le tableau contenant les valeurs des éléments voulues dans la matrice
     * @throws RuntimeException indique si le tableau est vide
     * @throws RuntimeException indique si le modulo est inférieur à 0
     * @implNote Si la taille fournie est plus grande que celle du tableau de valeurs, les valeurs manquantes seront
     *           initialisées à 0. Dans le cas contraitre, les valeurs en trop ne sont pas prises en compte.
     */
    public Matrix(int n, int m, int[] val) {
        if (val.length == 0) {
            throw new RuntimeException("You must provide values for the matrix.");
        }

        if (n <= 0 || m <= 0) {
            throw new RuntimeException("Invalid matrix size");
        }
        try {

            int maxVal = val[0];

            for (int x : val) {
                if (x < 0) throw new RuntimeException("Values for the matrix must be positive.");
                maxVal = Math.max(x, maxVal);
            }

            this.n = n;
            this.m = m;
            this.modulus = maxVal + 1;

            matrix = new int[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m && i * m + j < val.length; j++) {
                    matrix[i][j] = val[i * m + j];
                }
            }
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Fonction d'affichage
     * @return la matrice sous la forme d'un String
     */
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append("| ");
            for (int j = 0; j < m; j++) {
                result.append(matrix[i][j]).append(" ");
            }
            result.append("|\n");
        }
        result.append("\n");
        return result.toString();
    }

    /**
     * Crée une matrice de taille max(N1, N2) x max(M1, M2) afin de pouvoir effectuer une opération entre this et
     * other
     * @param other est le deuxième opérande
     * @return une matrice remplie de 0 avec la taille de la matrice résultante d'une opération entre les matrices
     *         this et other.
     */
    private Matrix getResultSizedMatrix(Matrix other) {
        int n = Math.max(this.n, other.n);
        int m = Math.max(this.m, other.m);
        int[] tab = new int[n * m];
        Matrix ret = new Matrix(n, m, tab);
        ret.modulus = this.modulus;
        return ret;
    }

    /**
     * Effectue l'opération fournie en paramètre sur les matrices this et other
     * @param other est le deuxième opérande
     * @param op est l'opération souhaitée
     * @return la matrice résultante de l'opération si il n'y a pas eu d'erreur, null dans le cas échéant.
     * @throws RuntimeException indique que les modulos ne sont pas compatibles
     */
    private Matrix operation(Matrix other, MatrixOperation op) {
        if (this.modulus != other.modulus) {
            throw new RuntimeException("Modulus are not compatible.");
        }
        try {
            Matrix m1 = getResultSizedMatrix(other);
            Matrix m2 = getResultSizedMatrix(other);

            for (int i = 0; i < this.n; i++) {
                if (this.m >= 0) System.arraycopy(this.matrix[i], 0, m1.matrix[i], 0, this.m);
            }
            for (int i = 0; i < other.n; i++) {
                if (other.m >= 0) System.arraycopy(other.matrix[i], 0, m2.matrix[i], 0, other.m);
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
     * Addition
     * @param other est le deuxième opérande
     * @return la matrice qui contient le résultat de l'opération
     */
    public Matrix addTo(Matrix other) {
        MatrixOperation op = new Addition();
        return this.operation(other, op);
    }

    /**
     * Soustraction
     * @param other est le deuxième opérande
     * @return la matrice qui contient le résultat de l'opération
     */
    public Matrix subtractTo(Matrix other) {
        MatrixOperation op = new Subtraction();
        return this.operation(other, op);
    }

    /**
     * Multiplication
     * @param other est l'opérande 2
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

        System.out.println("Normal behaviour for operations between two matrixes :\n");

        System.out.println("one\n");
        Matrix mat1 = new Matrix(n1, m1, modulus);
        System.out.println(mat1);

        System.out.println("two\n");
        Matrix mat2 = new Matrix(n2, m2, modulus);  // Example values for 2nd constructor
                                                    // {1, 4, 2, 3, 2, 0, 1, 0, 4, 2, 0, 0, 2, 0, 2}
        System.out.println(mat2);

        System.out.println("one + two\n");
        System.out.println(mat1.addTo(mat2));

        System.out.println("one - two\n");
        System.out.println(mat1.subtractTo(mat2));

        System.out.println("one x two\n");
        System.out.println(mat1.multBy(mat2));

        int n3 = 2, m3 = 5;
        int n4 = 4, m4 = 3;
        int modulus1 = 5, modulus2 = 4;

        System.out.println("Operations between two uncompatible matrixes :\n");

        System.out.println("one\n");
        Matrix mat3 = new Matrix(n3, m3, modulus1);
        System.out.println(mat3);

        System.out.println("two\n");
        Matrix mat4 = new Matrix(n4, m4, modulus2);
        System.out.println(mat4);

        System.out.println("one + two\n");
        System.out.println(mat3.addTo(mat4));

        System.out.println("one - two\n");
        System.out.println(mat3.subtractTo(mat4));

        System.out.println("one x two\n");
        System.out.println(mat3.multBy(mat4));
    }
}
