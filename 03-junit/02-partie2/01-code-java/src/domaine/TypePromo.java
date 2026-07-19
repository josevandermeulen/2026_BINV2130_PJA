package domaine;

public enum TypePromo {
    PUB("Remise publicitaire"), SOLDE("Remise pour solde"),
    DESTOCKAGE("Remise pour déstockage");

    private String typePromo;

    private TypePromo(String typePromo) {
        this.typePromo = typePromo;
    }

    public String getTypePromo() {
        return typePromo;
    }


}
