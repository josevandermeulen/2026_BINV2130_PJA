package code_theorie;

import domaine.Employe;
import domaine.Genre;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CodeTest {

    boolean isHomme(Employe e){
        return e.getGenre() == Genre.HOMME;
    }

    public static void main(String[] args) {
        List<Employe> employes = new ArrayList<>();



        double tailleMoyenneDesHommes = employes
                .stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .mapToInt(e -> e.getTaille())
                .average()
                .orElse(0);

        List<Employe> listDesHommes = employes
                .stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .collect(toList ());






        List<Employe> listDesHommesFor = new ArrayList<>();
        for (Employe employe : employes) {
            if (employe.getGenre() == Genre.HOMME) {
                listDesHommesFor.add(employe);
            }
        }

        employes.forEach(System.out::println);
        employes.forEach(e -> System.out.println(e));

        List<Integer> list = Arrays.asList(6, 2, 9, 1, 7);
        list.stream().sorted().forEach(System.out::println);

        Integer max = Stream.of(1, 2, 3, 4, 5)
                .reduce(0, Integer::max);
        System.out.println(max);

        var employeTries =
                employes.stream()
                        .filter(e -> e.getGenre() == Genre.HOMME)
                        .sorted(Comparator.comparingInt(Employe::getTaille)
                        .reversed());

        Stream<String> listeNom = employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .sorted(Comparator.comparingInt(Employe::getTaille)
                        .reversed())
                .map(Employe::getNom);

        List<Integer> numbers = Arrays.asList(3,4,5,1,2,-5);
        int sumF = numbers.stream().reduce(0, (a, b) -> a + b);
        Integer acc = 0;
        for (Integer number : numbers) {
            acc = acc + number;
        }
        int sumLoop = acc;

        int sum = 0 ;
        for(int i : numbers){
            sum+=i ;
        }
        Integer max2 = Stream.of(1, 2, 3, 4, 5)
                .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println(max);

        // Optional
        var vide = numbers.stream()
                .filter(n -> n>1000)
                .collect(Collectors.averagingInt(Integer::intValue));


        Optional<Employe> geantEnOption = employes.stream()
                .filter(e -> e.getTaille() > 210)
                .findAny();

        if (geantEnOption.isPresent()){
            System.out.println("Le géant: " + geantEnOption.get());
        }

        System.out.println(geantEnOption.get());

        Employe geante = employes.stream()
                .filter(e -> e.getTaille() > 210)
                .findAny()
                .orElse(new Employe(Genre.FEMME, 220, "La géante"));

    }

}


