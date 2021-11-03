
import java.util.Random;
import java.lang.Math;

public class Matrix {
    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getModulus() {
        return modulus;
    }

    public int[][] getMatrice() {
        return matrice;
    }

    private int n;
    private int m;
    private int modulus;

    int[][] matrice;



    // constructeur aléatoire
    public Matrix(int n, int m, int modulo) {
        if (modulo <= 0) {
            throw new RuntimeException("The modulus must be greater than 0.");
        }
        try {
            this.n = n;
            this.m = m;
            this.modulus = modulo;
            int[][] mat = new int[n][m];

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


    // Constructeur par valeur
    public Matrix(int m, int[] val) {
        if (val.length == 0) {
            throw new RuntimeException("You must provide valors for the matrix.");
        }
        try {

            int maxVal = val[0];

            for (int x : val) {
                if (x < 0) throw new RuntimeException("Values for the matrix must be positive.");
                maxVal = Math.max(x, maxVal);
            }

            int n = val.length / m;
            this.n = n;
            this.m = m;
            this.modulus = maxVal + 1;

            matrice = new int[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matrice[i][j] = val[i * m + j];
                }
            }
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public String toString(){
        String result = "";
        for (int i = 0; i < n; i++) {
            result += "| ";
            for (int j = 0; j < m; j++) {
                result += matrice[i][j] + " ";
            }
            result += "|\n";
        }
        result += "\n";
        return result;
    }

    private Matrix getResultMatrix(Matrix other) {
        int n = Math.max(this.n, other.n);
        int m = Math.max(this.m, other.m);
        int[] tab = new int[n * m];
        return new Matrix(m, tab);
    }

    private Matrix operation(Matrix other, MatrixOperation op) {
        if (this.modulus != other.modulus) {
            throw new RuntimeException("Modulus are not compatible.");
        }
        try {
            Matrix m1 = getResultMatrix(other);
            Matrix m2 = getResultMatrix(other);

            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.m; j++) {
                    m1.matrice[i][j] = this.matrice[i][j];
                }
            }
            for (int i = 0; i < other.n; i++) {
                for (int j = 0; j < other.m; j++) {
                    m2.matrice[i][j] = other.matrice[i][j];
                }
            }
            m1.modulus = this.modulus;
            m2.modulus = this.modulus;
            return op.operation(m1, m2);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Matrix addTo(Matrix other) {
        MatrixOperation op = new Addition();
        return this.operation(other, op);
    }

    public Matrix subtractTo(Matrix other) {
        MatrixOperation op = new Subtraction();
        return this.operation(other, op);
    }

    public Matrix multBy(Matrix other) {
        MatrixOperation op = new Multiplication();
        return this.operation(other, op);
    }


    public static void main(String[] args) {

        int n1 = 2, m1 = 5;
        int n2 = 4, m2 = 3;
        int modulus = 5;

        System.out.println("Normal behaviours for operations between two matrixes :");

        System.out.println("one");
        Matrix mat1 = new Matrix(n1, m1, modulus); // Example values for 2nd constructor
                                                   // {1, 3, 1, 1, 3, 2, 4, 2, 1, 0, 1, 0}
        System.out.println(mat1);

        System.out.println("two");
        Matrix mat2 = new Matrix(n2, m2, modulus);  // Example values for 2nd constructor
                                                    // {1, 4, 2, 3, 2, 0, 1, 0, 4, 2, 0, 0, 2, 0, 2}
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
