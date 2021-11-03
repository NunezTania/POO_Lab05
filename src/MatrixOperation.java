/**
 * Classe MatrixOperation
 * Classe abstraite permettant d'implémenter des opérations entre deux matrices de même taille composante par
 * composante.
 * @autors Tania Nunez, Magali Egger
 */
public abstract class MatrixOperation {

    /**
     * Opération
     * @param m1 premier opérande
     * @param m2 deuxième opérande
     * @return une nouvelle matrice contenant le résultat de l'opération
     */
    public abstract Matrix operation(Matrix m1, Matrix m2);

}
