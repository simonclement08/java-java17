package java17.ex02;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.junit.Test;

import java17.data.Data;
import java17.data.domain.Customer;
import java17.data.domain.Order;

/**
 * Exercice 02 - Transformation
 */
public class Stream_02_Test {

	@Test
	public void test_map() throws Exception {

		List<Order> orders = new Data().getOrders();

		List<Customer> result = orders.stream().map(order -> order.getCustomer()).toList();

		assertThat(result, hasSize(8));
	}

	@Test
	public void test_map_count() throws Exception {

		List<Order> orders = new Data().getOrders();

		long result = orders.stream().map(order -> order.getCustomer()).collect(Collectors.counting());

		assertThat(result, is(8L));
	}

	@Test
	public void test_map_distinct() throws Exception {

		List<Order> orders = new Data().getOrders();

		List<Customer> result = orders.stream().map(order -> order.getCustomer()).distinct().toList();

		assertThat(result, hasSize(2));
	}

	@Test
	public void test_map_distinct_count() throws Exception {

		List<Order> orders = new Data().getOrders();

		long result = orders.stream().map(order -> order.getCustomer()).distinct().collect(Collectors.counting());

		assertThat(result, is(2L));
	}

	@Test
	public void test_mapToDouble_sum() throws Exception {

		List<Order> orders = new Data().getOrders();

		/*
		 * Calculer le chiffre d'affaires total de la pizzeria (somme des prix des
		 * commandes)
		 */
		double result = orders.stream()
				.mapToDouble(order -> order.getPrice())
				.sum();

		assertThat(result, is(10900.0));
	}

	@Test
	public void test_mapToDouble_avg() throws Exception {

		List<Order> orders = new Data().getOrders();

		/*
		 * Calculer le prix moyen d'une commande
		 */
		OptionalDouble result = orders.stream()
				.mapToDouble(order -> order.getPrice())
				.average();

		assertThat(result.isPresent(), is(true));
		assertThat(result.getAsDouble(), is(1362.5));
	}
}
