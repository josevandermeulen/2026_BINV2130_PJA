package be.vinci.mocks.club;

import java.util.ArrayList;
import java.util.List;

public class ClubStock {
    private static final int SLEEP_TIME = 500;

    private final List<Equipment> equipments;

    public ClubStock(List<Equipment> equipments) {
        this.equipments = new ArrayList<Equipment>(equipments);
    }

    public Equipment findByCode(String code) {
        // Simule l'accès à une base de données ou une autre source de données
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.equipments.stream().filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

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
