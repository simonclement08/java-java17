package java8.ex07;

import java8.data.Data;
import java8.data.domain.Order;
import java8.data.domain.Pizza;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Exercice 08 - Stream Parallel - Collections
 */
public class Stream_07_Test {

    // Voici une méthode qui exécute 10 fois un traitement et retourne le meilleur temps (le plus court)
    private long monitor(Runnable fn) {

        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            fn.run();
            long end = System.nanoTime();
            long duration = (end - start) / 1_000_000;
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    // créer une fonction List<Pizza> -> List<Pizza>
    // seules les pizzas ayant un prix >= 1000 sont conservées
    Function<List<Pizza>, List<Pizza>> filterPizza = pizzas -> pizzas.stream()
            .filter(pizza -> pizza.getPrice() >= 1000)
            .collect(Collectors.toList());;

    // créer une fonction List<Pizza> -> List<Pizza>
    // seules les pizzas ayant un prix >= 1000 sont conservées
    // .parallel()
    Function<List<Pizza>, List<Pizza>> parallelFilterPizza = pizzas -> pizzas.parallelStream()
            .filter(pizza -> pizza.getPrice() >= 1000)
            .collect(Collectors.toList());;

    // exécuter le test pour visualiser le temps d'exécution
    @Test
    public void test_arraylist_vs_linkedlist() throws Exception {
        arraylist_vs_linkedlist(filterPizza);
    }

    // Que constatez-vous ?
    // De mon côté :
    // INFO: arrayList=21 ms
    // INFO: linkedList=21 ms


    // exécuter le test pour visualiser le temps d'exécution
    @Test
    public void test_parallel_arraylist_vs_linkedlist() throws Exception {
        arraylist_vs_linkedlist(parallelFilterPizza);
    }

    // Que constatez-vous ?
    // INFO: arrayList=15 ms
    // INFO: linkedList=83 ms

    public void arraylist_vs_linkedlist(Function<List<Pizza>, List<Pizza>> fn) throws Exception {

        int nbPizzas = 1000000;

        List<Pizza> pizzaArrayList = new ArrayList<>(new Data().getPizzas(nbPizzas));
        List<Pizza> pizzaLinkedList = new LinkedList<>(new Data().getPizzas(nbPizzas));

        long arrayList = monitor(() -> fn.apply(pizzaArrayList));
        long linkedList = monitor(() -> fn.apply(pizzaLinkedList));

        Logger.getGlobal().info("arrayList=" + arrayList + " ms");
        Logger.getGlobal().info("linkedList=" + linkedList + " ms");

    }

}
