package be.vinci.mocks.club;

import java.util.ArrayList;
import java.util.List;

/**
 * Le stock d'équipements du club, tenant lieu de source de données.
 *
 * Chaque méthode attend délibérément avant de répondre, pour imiter une base lente : c'est cette
 * lenteur qui justifie de la remplacer par un mock dans les tests plutôt que de s'en servir.
 */
public class ClubStock {
    private static final int SLEEP_TIME = 500;

    private final List<Equipment> equipments;

    /**
     * Crée un stock à partir des équipements donnés, recopiés dans une liste interne.
     *
     * @param equipments les équipements que contient le stock
     */
    public ClubStock(List<Equipment> equipments) {
        this.equipments = new ArrayList<Equipment>(equipments);
    }

    /**
     * Recherche un équipement par son code.
     *
     * @param code le code de l'équipement recherché
     * @return l'équipement correspondant, ou null si aucun ne porte ce code
     */
    public Equipment findByCode(String code) {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.equipments.stream().filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

    /**
     * Enregistre l'emprunt d'un équipement par un membre.
     *
     * @param equipment  l'équipement emprunté
     * @param memberName le nom du membre emprunteur
     */
    public void markAsBorrowed(Equipment equipment, String memberName) {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        equipment.setBorrowed(true);
    }
}
