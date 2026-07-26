package domaine;

/**
 * Un Hasher de test qui renvoie toujours le même hash, quelle que soit la valeur reçue.
 *
 * C'est ce qui rend les tests de HyperLogLog déterministes : en fixant le hash, on choisit
 * exactement le registre visé et le nombre de zéros attendu, au lieu de subir un vrai hachage.
 */
public class HasherStub implements Hasher {

    // TODO Question 5 : un attribut (le hash fixe) à assigner dans le constructeur,
    //  et hash(String) qui renvoie toujours cette valeur.

    /**
     * Crée le stub avec le hash qu'il devra toujours renvoyer.
     *
     * @param hashFixe la valeur que hash renverra pour n'importe quelle entrée
     */
    public HasherStub(int hashFixe) {
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Renvoie le hash fixe du stub, sans regarder la valeur reçue.
     *
     * @param valeur ignorée
     * @return le hash fixé à la construction
     */
    @Override
    public int hash(String valeur) {
        throw new UnsupportedOperationException("À implémenter");
    }

}
