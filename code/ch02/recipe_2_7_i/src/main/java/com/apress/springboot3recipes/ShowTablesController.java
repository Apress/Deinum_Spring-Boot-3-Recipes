package com.apress.springboot3recipes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowTablesController {

	private final JdbcTemplate jdbc;

	public ShowTablesController(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@GetMapping("/show-tables")
	public List<String> showTables() {
		var sql = "select tablename from pg_catalog.pg_tables";
		return jdbc.queryForList(sql, String.class);
	}
}
