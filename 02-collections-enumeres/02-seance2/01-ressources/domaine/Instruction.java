package domaine;

import util.Util;

import java.time.Duration;

public class Instruction {
    private String description;

    private Duration dureeEnMinutes;

    public Instruction(String description, int duree) {
        Util.checkString(description);
        Util.checkPositiveOrNul(duree);

        this.description = description;
        this.dureeEnMinutes = Duration.ofMinutes(duree);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Util.checkString(description);

        this.description = description;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    public void setDureeEnMinutes(Duration dureeEnMinutes) {
        Util.checkObject(dureeEnMinutes);
        Util.checkPositiveOrNul(dureeEnMinutes.toMinutes());
        if (!dureeEnMinutes.equals(Duration.ofMinutes(dureeEnMinutes.toMinutes()))) {
            throw new IllegalArgumentException();
        }

        this.dureeEnMinutes = dureeEnMinutes;
    }

    @Override
    public String toString() {
        return "(" + String.format("%02d:%02d", dureeEnMinutes.toHours(),
                dureeEnMinutes.toMinutesPart()) + ") " + description;
    }

}
