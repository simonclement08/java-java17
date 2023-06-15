package java17.ex01;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java17.data.Data;
import java17.data.Person;

import java.util.ArrayList;
import java.util.List;


/**
 * Exercice 01 - Filter
 */
public class Lambda_01_Test {

    // tag::PersonPredicate[]
    interface PersonPredicate {
        boolean test(Person p);
    }
    // end::PersonPredicate[]

    // tag::filter[]
    private List<Person> filter(List<Person> persons, PersonPredicate predicate) {
        List<Person> filteredPersons = new ArrayList<Person>();
    	for (Person p: persons){
        	if (predicate.test(p)){
        		filteredPersons.add(p);
        	}
        }
        return filteredPersons;
    }
    // end::filter[]


    // tag::test_filter_by_age[]
    @Test
    public void test_filter_by_age() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        List<Person> result = filter(personList, p -> p.getAge() >= 18);

        assert result.size() == 83;

        for (Person person : result) {
            assert person.getAge() > 17;
        }
    }
    // end::test_filter_by_age[]

    // tag::test_filter_by_firstname[]
    @Test
    public void test_filter_by_firstname() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        List<Person> result = filter(personList, p -> "first_10".equals(p.getFirstname()));

        assert result.size() == 1;
        assert result.get(0).getFirstname().equals("first_10");

    }
    // end::test_filter_by_firstname[]

    // tag::test_filter_by_password[]
    @Test
    public void test_filter_by_password() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        String passwordSha512Hex = "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff";

        PersonPredicate personPredicate = p -> p.getAge() > 49 && passwordSha512Hex.matches(DigestUtils.sha512Hex(p.getPassword())) ;
        List<Person> result = filter(personList, personPredicate);

        assert result.size() == 6;
        for (Person person : result) {
            assert person.getPassword().equals("test");
        }
    }
    // end::test_filter_by_password[]
}
