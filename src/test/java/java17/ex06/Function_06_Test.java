package java17.ex06;


import java.util.function.Supplier;

import org.junit.Test;

import java17.data.Person;

/**
 * Exercice 06 - java.util.function.Supplier
 */
public class Function_06_Test {


    // tag::formatAge[]
    String formatAge(Supplier<Person> supplier) {
        return "[age=" + supplier.get().getAge() + "]";
    }
    // end::formatAge[]


    @Test
    public void test_supplier_formatAge() throws Exception {
    	Supplier<Person> supplier = () -> {
    		Person person = new Person();
    		person.setAge(35);
    		return person;
    	};
        String result = formatAge(supplier);

        assert result.equals("[age=35]");
    }

}
