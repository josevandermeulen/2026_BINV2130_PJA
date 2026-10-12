package be.vinci.mocks.domaine;

import be.vinci.mocks.source.SourceBibliotheques;
import be.vinci.mocks.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Le service de recherche de bibliothèques : c'est la classe que l'on teste.
 *
 * Il ne sait pas d'où viennent les bibliothèques : il les demande à une SourceBibliotheques reçue
 * dans son constructeur (l'API d'ODWB en production, un faux objet dans les tests).
 *
 * Les résultats sont mis en cache par code postal : une fois les bibliothèques d'un code postal
 * obtenues, le service ne redemande plus jamais ce code postal à la source. Un appel qui échoue
 * n'est pas mis en cache : la recherche suivante réinterroge la source.
 */
public class BibliothequeService {

    private final SourceBibliotheques source;

    private final Map<String, List<Bibliotheque>> cache;

    /**
     * Crée un service qui interroge la source indiquée.
     *
     * @param source la source de bibliothèques
     * @throws IllegalArgumentException si la source est `null`
     */
    public BibliothequeService(SourceBibliotheques source) {
        Util.checkObject(source);

        this.source = source;
        this.cache = new HashMap<>();
    }

    /**
     * Renvoie les bibliothèques d'un code postal, triées par ordre alphabétique de dénomination.
     *
     * La première recherche d'un code postal interroge la source avec le filtre `cp="<code>"` ;
     * les suivantes répondent depuis le cache, sans appel à la source.
     *
     * @param codePostal le code postal (quatre chiffres)
     * @return les bibliothèques de ce code postal, triées ; une liste vide s'il n'y en a aucune
     * @throws IllegalArgumentException    si le code postal est `null` ou n'est pas formé de quatre
     *                                     chiffres ; la source n'est alors pas interrogée
     * @throws SourceIndisponibleException si la source ne peut pas répondre et que ce code
     *                                     postal n'est pas encore en cache
     */
    public List<Bibliotheque> parCodePostal(String codePostal) {
        Util.checkCodePostal(codePostal);

        if (!cache.containsKey(codePostal)) {
            List<Bibliotheque> trouvees = new ArrayList<>(source.rechercher("cp=\"" + codePostal + "\""));
            Collections.sort(trouvees);
            cache.put(codePostal, trouvees);
        }
        return new ArrayList<>(cache.get(codePostal));
    }

    /**
     * Renvoie la bibliothèque d'un code postal la plus proche d'un point.
     *
     * La distance est approchée à vol d'oiseau, en traitant latitude et longitude comme des
     * coordonnées planes : c'est assez précis à l'échelle d'une commune. En cas d'égalité, la
     * première par ordre alphabétique l'emporte.
     *
     * @param codePostal le code postal (quatre chiffres)
     * @param latitude   la latitude du point
     * @param longitude  la longitude du point
     * @return la bibliothèque la plus proche, ou `null` si ce code postal n'a aucune bibliothèque
     * @throws IllegalArgumentException    si le code postal est invalide
     * @throws SourceIndisponibleException si la source ne peut pas répondre et que ce code
     *                                     postal n'est pas encore en cache
     */
    public Bibliotheque plusProche(String codePostal, double latitude, double longitude) {
        Bibliotheque plusProche = null;
        double meilleureDistance = Double.MAX_VALUE;

        for (Bibliotheque bibliotheque : parCodePostal(codePostal)) {
            double distance = distanceAuCarre(bibliotheque, latitude, longitude);
            if (distance < meilleureDistance) {
                plusProche = bibliotheque;
                meilleureDistance = distance;
            }
        }
        return plusProche;
    }

    /**
     * Renvoie les bibliothèques d'un code postal dont les thématiques de recherche ou les fonds
     * spécialisés contiennent un mot-clé, sans tenir compte des majuscules et minuscules.
     *
     * @param codePostal le code postal (quatre chiffres)
     * @param motCle     le mot-clé recherché, par exemple « bande dessinée »
     * @return les bibliothèques trouvées, triées par ordre alphabétique ; une liste vide si aucune
     * @throws IllegalArgumentException    si le code postal est invalide ou si le mot-clé est
     *                                     `null` ou vide
     * @throws SourceIndisponibleException si la source ne peut pas répondre et que ce code
     *                                     postal n'est pas encore en cache
     */
    public List<Bibliotheque> parThematique(String codePostal, String motCle) {
        Util.checkString(motCle);

        String recherche = motCle.toLowerCase();
        List<Bibliotheque> trouvees = new ArrayList<>();
        for (Bibliotheque bibliotheque : parCodePostal(codePostal)) {
            if (bibliotheque.getThematiques().toLowerCase().contains(recherche)
                    || bibliotheque.getFondsSpecialises().toLowerCase().contains(recherche)) {
                trouvees.add(bibliotheque);
            }
        }
        return trouvees;
    }

    private static double distanceAuCarre(Bibliotheque bibliotheque, double latitude, double longitude) {
        double ecartLatitude = bibliotheque.getLatitude() - latitude;
        double ecartLongitude = bibliotheque.getLongitude() - longitude;
        return ecartLatitude * ecartLatitude + ecartLongitude * ecartLongitude;
    }
}
