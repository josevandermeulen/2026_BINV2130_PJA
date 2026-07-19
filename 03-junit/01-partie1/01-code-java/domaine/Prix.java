package domaine;

import exceptions.QuantiteNonAutoriseeException;
import util.Util;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Représente le prix appliqué à un produit selon la quantité achetée, avec une promotion optionnelle.
 *
 * Les prix unitaires sont définis par palier de quantité minimale (voir definirPrix) : la quantité
 * achetée détermine, parmi les paliers définis, le prix unitaire à appliquer (getPrix).
 *
 * Une promotion optionnelle (TypePromo) peut s'ajouter à ce prix de base. La signification de
 * valeurPromo dépend du type de promo choisi :
 * - PUB : montant fixe (en euros) soustrait du prix
 * - SOLDE : pourcentage de réduction appliqué au prix
 * - DESTOCKAGE : pourcentage de réduction, avec un prix plancher de 1 euro
 *
 * getPrix renvoie le prix brut, sans promo ; getPrixPromo renvoie le prix après application
 * de la promotion en cours (ou le prix inchangé s'il n'y a pas de promo).
 */
public class Prix {

    private final TypePromo typePromo;
    private final double valeurPromo;
    private SortedMap<Integer, Double> prixParQuantite = new TreeMap<Integer, Double>();

    public Prix() {
        typePromo = null;
        valeurPromo = 0;
    }

    /**
     * @param typePromo   le type de promotion appliquée
     * @param valeurPromo la valeur de la promo : montant fixe en euros pour PUB,
     *                    pourcentage pour SOLDE et DESTOCKAGE
     * @throws IllegalArgumentException si typePromo est null ou si valeurPromo n'est pas strictement positive
     */
    public Prix(TypePromo typePromo, double valeurPromo) {
        Util.checkObject(typePromo);
        Util.checkStrictlyPositive(valeurPromo);
        this.typePromo = typePromo;
        this.valeurPromo = valeurPromo;
    }

    public TypePromo getTypePromo() {
        return typePromo;
    }

    public double getValeurPromo() {
        return valeurPromo;
    }

    /**
     * Cette méthode permet de définir le prix unitaire correspondant à une quantité minimale.
     * S'il existe déjà un prix correspondant à la quantité, il sera remplacé.
     *
     * @param quantiteMin
     * @param prixUnitaire le prix unitaire à partir de cette quantité
     * @throws IllegalArgumentException en cas de quantité négative ou nulle ou en cas de valeur négative ou nulle
     */
    public void definirPrix(int quantiteMin, double prixUnitaire) {
        Util.checkStrictlyPositive(quantiteMin);
        Util.checkStrictlyPositive(prixUnitaire);
        prixParQuantite.put(quantiteMin, prixUnitaire);
    }

    /**
     * Cette méthode renvoie le prix à appliquer selon la quantité achetée
     *
     * @param quantite la quantité achetée
     * @return le prix unitaire pour cette quantité
     * @throws QuantiteNonAutoriseeException si la quantité est inférieure à la quantité minimale autorisée
     * @throws IllegalArgumentException      en cas de quantité négative ou nulle
     */
    public double getPrix(int quantite) throws QuantiteNonAutoriseeException {
        Util.checkStrictlyPositive(quantite);
        SortedMap<Integer, Double> quantiteInferieure = prixParQuantite.headMap(quantite+1);
        if (quantiteInferieure.isEmpty()) throw new QuantiteNonAutoriseeException();
        return quantiteInferieure.get(quantiteInferieure.lastKey());
    }

    /**
     * Cette méthode renvoie le prix à appliquer selon la quantité achetée, après application de la promotion en cours.
     * Le mode de calcul dépend du type de promo :
     * - PUB : valeurPromo est soustraite du prix (jamais en-dessous de 0)
     * - SOLDE : valeurPromo est un pourcentage de réduction (jamais en-dessous de 0)
     * - DESTOCKAGE : valeurPromo est un pourcentage de réduction, mais le prix ne descend jamais sous 1 euro
     *
     * S'il n'y a pas de promo, le prix normal (voir getPrix) est renvoyé tel quel.
     *
     * @param quantite la quantité achetée
     * @return le prix unitaire après promo pour cette quantité
     * @throws QuantiteNonAutoriseeException si la quantité est inférieure à la quantité minimale autorisée
     * @throws IllegalArgumentException      en cas de quantité négative ou nulle
     */
    public double getPrixPromo(int quantite) throws QuantiteNonAutoriseeException {
        double prix = getPrix(quantite);
        if (typePromo == null) return prix;
        switch (typePromo) {
            case PUB:
                return Math.max(0, prix - valeurPromo);
            case SOLDE:
                return Math.max(0, prix * (1 - valeurPromo / 100));
            case DESTOCKAGE:
                return Math.max(1, prix * (1 - valeurPromo / 100));
            default:
                return prix;
        }
    }

    @Override
    public String toString() {
        String detail = "";
        if (typePromo != null) detail += "Promo : " + typePromo + " - " + valeurPromo + "\n";
        for (Entry<Integer, Double> entry : prixParQuantite.entrySet()
             ) {
            detail += entry.getValue() + " euros à partir de " + entry.getKey() + " unités" + "\n";
        }
        return detail;
    }
}
