package be.vinci.mocks.club;

/**
 * La couche métier au-dessus de ClubStock : elle porte les règles d'emprunt.
 *
 * Le stock se contente d'enregistrer ; c'est ce gestionnaire qui refuse d'emprunter un équipement
 * introuvable ou déjà sorti, et qui exige le nom de l'emprunteur.
 */
public class EquipmentManager {
    private final ClubStock stock;

    /**
     * Crée un gestionnaire adossé au stock donné.
     *
     * @param stock le stock d'équipements du club ; ne peut être null
     * @throws IllegalArgumentException si le stock est null
     */
    public EquipmentManager(ClubStock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
        this.stock = stock;
    }

    /**
     * Emprunte un équipement du stock pour un membre du club.
     *
     * L'emprunt n'a lieu que si l'équipement existe et n'est pas déjà emprunté.
     *
     * @param code       le code de l'équipement à emprunter
     * @param memberName le nom du membre emprunteur ; ne peut être `null` ou vide
     * @return `true` si l'équipement a été emprunté, `false` s'il est
     *         introuvable ou déjà emprunté
     * @throws IllegalArgumentException si `memberName` est `null` ou vide
     */
    public boolean addEquipment(String code, String memberName) {
        if (memberName == null || memberName.isBlank()) {
            throw new IllegalArgumentException("memberName cannot be null or empty");
        }
        Equipment equipment = this.stock.findByCode(code);
        if (equipment == null || equipment.isBorrowed()) {
            return false;
        }
        this.stock.markAsBorrowed(equipment, memberName);
        return true;
    }
}
