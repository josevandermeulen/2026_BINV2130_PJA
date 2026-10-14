package domaine;

import util.Util;

import java.util.Objects;

/**
 * Une bibliothèque publique de la Fédération Wallonie-Bruxelles, telle que la décrit le jeu de
 * données « Bibliothèques publiques en FWB + Fonds spécialisés » d'ODWB.
 *
 * C'est une simple donnée : elle ne dépend d'aucun autre objet et ne fait aucun appel extérieur.
 * Deux bibliothèques sont égales si elles ont la même dénomination et le même code postal.
 * L'ordre naturel est l'ordre alphabétique des dénominations.
 */
public class Bibliotheque implements Comparable<Bibliotheque> {

    private final String denomination;

    private final String province;

    private final String codePostal;

    private final String ville;

    private final String categorie;

    private final String thematiques;

    private final String fondsSpecialises;

    private final double latitude;

    private final double longitude;

    /**
     * Crée une bibliothèque.
     *
     * @param denomination     le nom de la bibliothèque
     * @param province         la province où elle se trouve ; `null` ou vide si inconnue
     * @param codePostal       son code postal (quatre chiffres)
     * @param ville            sa ville
     * @param categorie        sa catégorie (« bibliothèque locale », « réseau de bibliothèques », …) ;
     *                         `null` ou vide si inconnue
     * @param thematiques      ses thématiques de recherche, séparées par des virgules ; `null` ou
     *                         vide si elle n'en déclare aucune
     * @param fondsSpecialises ses fonds spécialisés, séparés par des virgules ; `null` ou vide si
     *                         elle n'en déclare aucun
     * @param latitude         sa latitude
     * @param longitude        sa longitude
     * @throws IllegalArgumentException si la dénomination ou la ville est absente ou vide, ou si le
     *                                  code postal est invalide
     */
    public Bibliotheque(String denomination, String province, String codePostal, String ville,
                        String categorie, String thematiques, String fondsSpecialises,
                        double latitude, double longitude) {
        Util.checkString(denomination);
        Util.checkCodePostal(codePostal);
        Util.checkString(ville);

        this.denomination = denomination;
        this.province = province == null ? "" : province;
        this.codePostal = codePostal;
        this.ville = ville;
        this.categorie = categorie == null ? "" : categorie;
        this.thematiques = thematiques == null ? "" : thematiques;
        this.fondsSpecialises = fondsSpecialises == null ? "" : fondsSpecialises;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return le nom de la bibliothèque
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * @return la province où elle se trouve, ou une chaîne vide si elle est inconnue
     */
    public String getProvince() {
        return province;
    }

    /**
     * @return son code postal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @return sa ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @return sa catégorie, ou une chaîne vide si elle est inconnue
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return ses thématiques de recherche, ou une chaîne vide si elle n'en déclare aucune
     */
    public String getThematiques() {
        return thematiques;
    }

    /**
     * @return ses fonds spécialisés, ou une chaîne vide si elle n'en déclare aucun
     */
    public String getFondsSpecialises() {
        return fondsSpecialises;
    }

    /**
     * @return sa latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return sa longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Compare deux bibliothèques par ordre alphabétique de dénomination.
     *
     * @param autre la bibliothèque à comparer
     * @return un entier négatif, nul ou positif selon que cette bibliothèque vient avant, au même
     *         rang ou après l'autre
     */
    @Override
    public int compareTo(Bibliotheque autre) {
        return denomination.compareTo(autre.denomination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bibliotheque that = (Bibliotheque) o;
        return denomination.equals(that.denomination) && codePostal.equals(that.codePostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, codePostal);
    }

    @Override
    public String toString() {
        return denomination + " (" + codePostal + " " + ville + ")";
    }
}
