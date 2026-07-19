package be.vinci.main;

import be.vinci.calculatrice.CalculatriceTest;
import be.vinci.minijunit.RapportExecution;
import be.vinci.minijunit.TestRunner;

public class Main {

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        RapportExecution rapport = runner.executerTests(CalculatriceTest.class);
        System.out.println(rapport);
    }

}
