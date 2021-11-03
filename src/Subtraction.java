
public class Subtraction extends MatrixOperation{

    /**
     * Effectue la soustraction entre deux matrices
     * @param m1 est le premier opérande
     * @param m2 est le second opérande
     * @return une matrice contenant le résultat de l'opération
     */
    @Override
    public Matrix operation(Matrix m1, Matrix m2) {
        Matrix result = new Matrix(m1.getN(), m1.getM(), m1.getModulus());
        for (int i = 0; i < m1.getN(); i++) {
            for (int j = 0; j < m1.getM(); j++) {
                result.getMatrice()[i][j] = Math.floorMod(m1.getMatrice()[i][j] - m2.getMatrice()[i][j], m1.getModulus());
            }
        }
        return result;
    }
}
