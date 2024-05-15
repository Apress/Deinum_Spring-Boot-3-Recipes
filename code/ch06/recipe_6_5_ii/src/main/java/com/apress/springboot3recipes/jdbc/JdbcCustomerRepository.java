package com.apress.springboot3recipes.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
class JdbcCustomerRepository implements CustomerRepository {

	private static final String ALL_QUERY = "SELECT id, name, email FROM customer";
	private static final String BY_ID_QUERY = "SELECT id, name, email FROM customer WHERE id=?";
	private static final String INSERT_QUERY =
		"INSERT INTO customer (name, email) VALUES (?,?)";
	private final JdbcTemplate jdbc;

	JdbcCustomerRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<Customer> findAll() {
		return jdbc.query(ALL_QUERY, (rs, rowNum) -> toCustomer(rs));
	}

	@Override
	public Optional<Customer> findById(long id) {
		try {
			var customer = jdbc.queryForObject(
							BY_ID_QUERY,
							(rs, rowNum) -> toCustomer(rs), id);
			return Optional.of(customer);
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public Customer save(Customer customer) {
		var keyHolder = new GeneratedKeyHolder();
		jdbc.update( (con) -> {
			var ps = con.prepareStatement(INSERT_QUERY, new String[] { "id"});
			ps.setString(1, customer.name());
			ps.setString(2, customer.email());
			return ps;
		}, keyHolder);
		var id = keyHolder.getKey().longValue();
		return new Customer(id, customer.name(), customer.email());

	}

	private Customer toCustomer(ResultSet rs) throws SQLException {
    var id = rs.getLong(1);
    var name = rs.getString(2);
    var email = rs.getString(3);
    return new Customer(id, name, email);
	}
}
