package java8.ex08;

import java8.data.Data;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 5 - Files
 */
public class Stream_08_Test {

    // Chemin vers un fichier de données des naissances
    private static final String NAISSANCES_DEPUIS_1900_CSV = "naissances_depuis_1900.csv";

    private static final String DATA_DIR = "./pizza-data";


    // Structure modélisant les informations d'une ligne du fichier
    class Naissance {
        String annee;
        String jour;
        Integer nombre;

        public Naissance(String annee, String jour, Integer nombre) {
            this.annee = annee;
            this.jour = jour;
            this.nombre = nombre;
        }

        public String getAnnee() {
            return annee;
        }

        public void setAnnee(String annee) {
            this.annee = annee;
        }

        public String getJour() {
            return jour;
        }

        public void setJour(String jour) {
            this.jour = jour;
        }

        public Integer getNombre() {
            return nombre;
        }

        public void setNombre(Integer nombre) {
            this.nombre = nombre;
        }
    }


    @Test
    public void test_group() throws IOException {

        // utiliser la méthode java.nio.file.Files.lines pour créer un stream de lignes du fichier naissances_depuis_1900.csv
        // Le bloc try(...) permet de fermer (close()) le stream après utilisation
        try (Stream<String> lines = Files.lines(Paths.get(getResource()))) {

            // construire une MAP (clé = année de naissance, valeur = somme des nombres de naissance de l'année)
            Map<String, Integer> result = lines
                    .skip(1) // Skip the header line
                    .map(line -> line.split(";"))
                    .map(arr -> new Naissance(arr[1], arr[2], Integer.parseInt(arr[3])))
                    .collect(Collectors.groupingBy(Naissance::getAnnee, Collectors.summingInt(Naissance::getNombre)));

            assertThat(result.get("2015"), is(8097));
            assertThat(result.get("1900"), is(5130));
        }
    }

    @Test
    public void test_max() throws IOException {

        // utiliser la méthode java.nio.file.Files.lines pour créer un stream de lignes du fichier naissances_depuis_1900.csv
        // Le bloc try(...) permet de fermer (close()) le stream après utilisation
        try (Stream<String> lines = Files.lines(Paths.get(getResource()))) {

            // trouver l'année où il va eu le plus de nombre de naissance
            Optional<Naissance> result = lines
                    .skip(1) // Skip the header line
                    .map(line -> line.split(";"))
                    .map(arr -> new Naissance(arr[1], arr[2], Integer.parseInt(arr[3])))
                    .max(Comparator.comparing(Naissance::getNombre));

            assertThat(result.get().getNombre(), is(48));
            assertThat(result.get().getJour(), is("19640228"));
            assertThat(result.get().getAnnee(), is("1964"));
        }
    }

    @Test
    public void test_collectingAndThen() throws IOException {
        // utiliser la méthode java.nio.file.Files.lines pour créer un stream de lignes du fichier naissances_depuis_1900.csv
        // Le bloc try(...) permet de fermer (close()) le stream après utilisation
        try (Stream<String> lines = Files.lines(Paths.get(getResource()))) {

            // construire une MAP (clé = année de naissance, valeur = maximum de nombre de naissances)
            // utiliser la méthode "collectingAndThen" à la suite d'un "grouping"
            Map<String, Naissance> result = lines
                    .skip(1) // Skip the header line
                    .map(line -> line.split(";"))
                    .map(arr -> new Naissance(arr[1], arr[2], Integer.parseInt(arr[3])))
                    .collect(Collectors.toMap(Naissance::getAnnee, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Naissance::getNombre))));;

            assertThat(result.get("2015").getNombre(), is(38));
            assertThat(result.get("2015").getJour(), is("20150909"));
            assertThat(result.get("2015").getAnnee(), is("2015"));

            assertThat(result.get("1900").getNombre(), is(31));
            assertThat(result.get("1900").getJour(), is("19000123"));
            assertThat(result.get("1900").getAnnee(), is("1900"));
        }
    }

    // Des données figurent dans le répertoire pizza-data
    // explorer les fichiers pour voir leur forme
    // compléter le test

    //@Test
    public void test_pizzaData() throws IOException {
        // utiliser la méthode java.nio.file.Files.list pour parcourir un répertoire

        // trouver la pizza la moins chère
        String pizzaNamePriceMin = null;

        assertThat(pizzaNamePriceMin, is("L'indienne"));

    }

    // TODO Optionel
    // TODO Créer un test qui exporte des données new Data().getPizzas() dans des fichiers
    // TODO 1 fichier par pizza
    // TODO le nom du fichier est de la forme ID.txt (ex. 1.txt, 2.txt)


    public static URI getResource(){
        try {
            return ClassLoader.getSystemResource(NAISSANCES_DEPUIS_1900_CSV).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Fichier " + NAISSANCES_DEPUIS_1900_CSV + " non trouvé.");
        }
    }

}