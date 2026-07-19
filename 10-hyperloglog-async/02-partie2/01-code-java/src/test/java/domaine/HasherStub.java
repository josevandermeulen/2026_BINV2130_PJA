package domaine;

public class HasherStub implements Hasher {

    private final int hashFixe;

    public HasherStub(int hashFixe) {
        this.hashFixe = hashFixe;
    }

    @Override
    public int hash(String valeur) {
        return hashFixe;
    }

}
