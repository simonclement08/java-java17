package java17.ex01;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import java17.data.Data;
import java17.data.Person;

/**
 * Exercice 02 - Filter, Map
 */
public class Optional_01_Test {
	
	class NotPresentException extends RuntimeException {
		
	}

    @Test
    public void test_optional_ifPresent() throws Exception {
    	List<Person> persons = Data.buildPersonList(100);

        Optional<Person> optPerson = persons.stream().filter(person -> person.getAge() == 18).findFirst();
        assertThat(optPerson.isPresent(), is(true));
        
        optPerson.ifPresent(person -> System.out.println(person));
    }

    @Test(expected=NotPresentException.class)
    public void test_optional_notPresent() throws Exception {
    	List<Person> persons = Data.buildPersonList(50);

        Optional<Person> optPerson = persons.stream().filter(person -> person.getAge() == 75).findFirst();
        assertThat(optPerson.isPresent(), is(false));
        
        optPerson.orElseThrow(NotPresentException::new);
    }
}
