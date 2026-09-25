package domaine;

import util.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

// Question 5 : la classe Livre — le SortedMap règle aussi la Question 21 (ordre de
// service) grâce à l'ordre naturel de l'énuméré Type
/**
 * Un livre de cuisine : les plats, rangés par type puis triés au sein de chaque type.
 *
 * Les types se suivent dans l'ordre de service (Plat.Type), et les plats d'un même type
 * sont classés par difficulté croissante puis par nom. Un plat ne peut y figurer deux fois : deux
 * plats de même type, même difficulté et même nom sont considérés comme le même.
 */
public class Livre {

    private SortedMap<Plat.Type, SortedSet<Plat>> plats = new TreeMap<>();
    // FinQuestion

    // Question 5 : ajouterPlat (le Comparator interne répond aux Questions 6 à 9)
    /**
     * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans.
     * Il faut ajouter correctement le plat en fonction de son type.
     * @param plat le plat à ajouter
     * @return true si le plat a été ajouté, false sinon.
     */
    public boolean ajouterPlat(Plat plat) {
        Util.checkObject(plat);

        SortedSet<Plat> plats = this.plats.get(plat.getType());
        // Si c'est le premier plat de ce type, on crée le SortedSet pour ce type dans la Map.
        if (plats == null) {
            // Comparator trié par difficulté puis nom (corrige le ClassCastException
            // et le faux-doublon détecté quand le tri ne portait que sur la difficulté)
            plats = new TreeSet<>(new Comparator<Plat>() {
                @Override
                public int compare(Plat o1, Plat o2) {
                    int comp = o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
                    if (comp == 0) {
                        return o1.getNom().compareTo(o2.getNom());
                    }
                    return comp;
                }
            });
            this.plats.put(plat.getType(), plats);
        }

        // On ajoute dans le SortedSet
        return plats.add(plat);
    }
    // FinQuestion

    // Question 5 : supprimerPlat
    /**
     * Supprime un plat du livre, s'il est dedans.
     * Si le plat supprimé est le dernier de ce type de plat, il faut supprimer ce type de
     * plat de la Map.
     * @param plat le plat à supprimer
     * @return true si le plat a été supprimé, false sinon.
     */
    public boolean supprimerPlat(Plat plat) {
        Util.checkObject(plat);

        SortedSet<Plat> plats = this.plats.get(plat.getType());
        if (plats == null) {
            return false;
        }

        boolean deleted = plats.remove(plat);
        if (deleted && plats.isEmpty()) {
            this.plats.remove(plat.getType());
        }
        return deleted;
    }
    // FinQuestion

    // Question 10 : opérations avancées du Livre (getPlatsParType, contient)
    /**
     * Renvoie un ensemble contenant tous les plats d'un certain type.
     * L'ensemble n'est pas modifiable.
     * @param type le type de plats souhaité
     * @return l'ensemble des plats
     */
    public SortedSet<Plat> getPlatsParType(Plat.Type type) {
        // L'ensemble renvoyé ne doit pas être modifiable !
        Util.checkObject(type);

        SortedSet<Plat> platsDuType = this.plats.get(type);
        if (platsDuType == null) {
            return null;
        }

        return Collections.unmodifiableSortedSet(platsDuType);
    }

    /**
     * Renvoie true si le livre contient le plat passé en paramètre. False sinon.
     * Pour cette recherche, un plat est identique à un autre si son type, son niveau de
     * difficulté et son nom sont identiques.
     * @param plat le plat à rechercher
     * @return true si le livre contient le plat, false sinon.
     */
    public boolean contient(Plat plat) {
        // Ne pas utiliser 2 fois la méthode get() de la map, et ne pas déclarer de variable locale !
        Util.checkObject(plat);

        if (this.plats.containsKey(plat.getType())) {
            return this.plats.get(plat.getType()).contains(plat);
        }
        return false;
    }

    // Question 10 : tousLesPlats — la version fournie trie déjà (Type, Difficulté, Nom),
    // ce qui répond d'emblée à la Question 20 (au lieu du simple Set non trié demandé ici)
    /**
     * Renvoie un ensemble contenant tous les plats du livre, triés par type, puis par niveau de
     * difficulté, et enfin par nom.
     * @return l'ensemble de tous les plats du livre.
     */
    public Set<Plat> tousLesPlats() {
        Set<Plat> plats = new TreeSet<>(new Comparator<Plat>() {
            @Override
            public int compare(Plat o1, Plat o2) {
                int comp = o1.getType().compareTo(o2.getType());
                if (comp == 0) {
                    comp = o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
                }
                if (comp == 0) {
                    comp = o1.getNom().compareTo(o2.getNom());
                }
                return comp;
            }
        });
        // Ne pas utiliser la méthode keySet() ou entrySet() ici !
        for (Set<Plat> platsDunType : this.plats.values()) {
            plats.addAll(platsDunType);
        }
        return plats;
    }

    // Question 14 (partie optionnelle) : comptage des usages d'un ingrédient dans le livre
    /**
     * Compte, pour chaque ingrédient utilisé dans au moins un plat du livre,
     * dans combien de plats il apparaît.
     * @return une Map associant chaque ingrédient utilisé à son nombre d'usages.
     */
    public Map<Ingredient, Integer> compterUsagesIngredients() {
        Map<Ingredient, Integer> compteur = new HashMap<>();
        for (Plat plat : tousLesPlats()) {
            for (IngredientQuantifie iq : plat.ingredientsQuantifies()) {
                compteur.put(iq.getIngredient(), compteur.getOrDefault(iq.getIngredient(), 0) + 1);
            }
        }
        return compteur;
    }

    // Question 5 : affichage de la table des matières (toString), avec un StringBuilder
    // dès le départ — cf. Question 19 (partie optionnelle)
    /**
     * Renvoie la table des matières du livre : chaque type, souligné, suivi du nom de ses plats.
     *
     * @return la table des matières du livre
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Plat.Type, SortedSet<Plat>> entry : this.plats.entrySet()) {
            str.append(entry.getKey().getNom() + "\n=====\n");
            for (Plat plat : entry.getValue()) {
                str.append(plat.getNom() + "\n");
            }
        }
        return str.toString();
    }

}
