package be.vinci.mocks.club;

public class EquipmentManager {
    private final ClubStock stock;

    public EquipmentManager(ClubStock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
        this.stock = stock;
    }

    /**
     * Emprunte un équipement du stock pour un membre du club.
     * <p>
     * L'emprunt n'a lieu que si l'équipement existe et n'est pas déjà emprunté.
     * </p>
     *
     * @param code       le code de l'équipement à emprunter
     * @param memberName le nom du membre emprunteur ; ne peut être {@code null} ou vide
     * @return {@code true} si l'équipement a été emprunté, {@code false} s'il est
     *         introuvable ou déjà emprunté
     * @throws IllegalArgumentException si {@code memberName} est {@code null} ou vide
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
