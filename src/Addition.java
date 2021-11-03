/**
 * Classe Addition
 * @autors Tania Nunez, Magali Egger
 */
public class Addition extends MatrixOperation{

    /**
     * Effectue l'addition entre deux matrices
     * @param m1 est le premier opérande
     * @param m2 est le second opérande
     * @return une matrice contenant le résultat de l'opération
     */
    @Override
    public Matrix operation(Matrix m1, Matrix m2) {
        Matrix result = new Matrix(m1.getN(), m1.getM(), m1.getModulus());
        for (int i = 0; i < m1.getN(); i++) {
            for (int j = 0; j < m1.getM(); j++) {
                result.getMatrix()[i][j] = Math.floorMod(m1.getMatrix()[i][j] + m2.getMatrix()[i][j], m1.getModulus());
            }
        }
        return result;
    }
}
