package java17.ex05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import java17.data.Data;
import java17.data.Person;

/**
 * Exercice 5 - java.util.function.Consumer
 */
public class Function_05_Test {

    //tag::functions[]
    Consumer<Person> changePasswordToSecret = p -> p.setPassword("secret");;

    Consumer<Person> verifyAge = p -> assertTrue(p.getAge() > 4);;

    Consumer<Person> verifyPassword = p -> assertEquals("secret", p.getPassword());;
    //end::functions[]


    @Test
    public void test_consumer() throws Exception {
        List<Person> personList = Data.buildPersonList();

        personList.forEach(changePasswordToSecret);
        personList.forEach(verifyAge.andThen(verifyPassword));
    }
}
