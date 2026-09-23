package domaine;

import util.Util;

import java.time.Duration;

// Question 2 : la classe Instruction — attributs et constructeur (Duration.ofMinutes)
/**
 * Une étape de la recette d'un plat : ce qu'il faut faire, et le temps que cela prend.
 *
 * La durée est exprimée en minutes entières ; c'est en additionnant celles de ses instructions
 * qu'un Plat obtient sa durée totale.
 */
public class Instruction {
    private String description;

    private Duration dureeEnMinutes;

    /**
     * Crée une instruction.
     *
     * @param description ce qu'il faut faire
     * @param duree       la durée de l'étape, en minutes ; positive ou nulle
     * @throws IllegalArgumentException si la description est null ou vide, ou si la durée est
     *                                  négative
     */
    public Instruction(String description, int duree) {
        Util.checkString(description);
        Util.checkPositiveOrNul(duree);

        this.description = description;
        this.dureeEnMinutes = Duration.ofMinutes(duree);
    }
    // FinQuestion

    /**
     * Renvoie la description de l'étape.
     *
     * @return la description de l'étape
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifie la description de l'étape.
     *
     * @param description la nouvelle description
     * @throws IllegalArgumentException si la description est null ou vide
     */
    public void setDescription(String description) {
        Util.checkString(description);

        this.description = description;
    }

    /**
     * Renvoie la durée de l'étape.
     *
     * @return la durée de l'étape
     */
    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    // Question 2 : setter avec validation d'une Duration en minutes entières
    /**
     * Modifie la durée de l'étape.
     *
     * @param dureeEnMinutes la nouvelle durée : positive ou nulle, et exprimée en minutes entières
     * @throws IllegalArgumentException si la durée est null, négative, ou comporte des secondes
     */
    public void setDureeEnMinutes(Duration dureeEnMinutes) {
        Util.checkObject(dureeEnMinutes);
        Util.checkPositiveOrNul(dureeEnMinutes.toMinutes());
        if (!dureeEnMinutes.equals(Duration.ofMinutes(dureeEnMinutes.toMinutes()))) {
            throw new IllegalArgumentException();
        }

        this.dureeEnMinutes = dureeEnMinutes;
    }

    // Question 2 : redéfinition de toString (le format HH:mm sur deux chiffres est peaufiné en Question 8)
    /**
     * Renvoie la durée au format `(hh:mm)` suivie de la description.
     *
     * @return la représentation textuelle de l'instruction
     */
    @Override
    public String toString() {
        return "(" + String.format("%02d:%02d", dureeEnMinutes.toHours(),
                dureeEnMinutes.toMinutesPart()) + ") " + description;
    }

}
