package java17.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * Exercice 01 - Les dates avant Java 8
 */
public class DateAndTime_01_Test {

	// Vous vous souvenez de java.util.Date (Java 1) ?
	@Test
	public void test_date() {

		// modifier les paramètres du constructeur pour que le test soit passant
		Date date = new Date(117, Calendar.MARCH, 24, 1, 2, 3);

		assertThat(date.toString(), is("Fri Mar 24 01:02:03 CET 2017"));

		// modifier le jour de la date avec une méthode set pour que le test soit
		// passant
		date.setDate(25);
		assertThat(date.toString(), is("Sat Mar 25 01:02:03 CET 2017"));
	}

	// Vous vous souvenez de java.util.Calendar (Java 1.1) ?

	@Test
	public void test_calendar() throws Exception {
		Calendar calendar = Calendar.getInstance();

		// modifier l'objet calendar pour que le test soit passant
		calendar.set(2017, Calendar.MARCH, 24, 1, 2, 3);

		assertThat(calendar.getTime().toString(), is("Fri Mar 24 01:02:03 CET 2017"));

		// calendar est mutable... :-(
		// modifier l'objet calendar pour que le test soit passant
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		assertThat(calendar.getTime().toString(), is("Sat Mar 25 01:02:03 CET 2017"));
	}

}
