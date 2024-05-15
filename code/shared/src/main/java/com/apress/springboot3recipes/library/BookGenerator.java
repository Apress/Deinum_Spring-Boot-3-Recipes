package com.apress.springboot3recipes.library;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Not really a generator but will read the {@code books.csv} and create {@code Book}
 * instances from the lines in there. The lines in the csv or separated with
 * an {@code |} and the authors field with an {@code ,} as to have the possibility
 * to read multiple authors.
 *
 * @author Marten Deinum
 */
public final class BookGenerator {

	public static List<Book> all() {
		try {
			var books = new ClassPathResource("books.csv").getInputStream();
			try (var lines = new BufferedReader(new InputStreamReader(books)).lines()) {
				return lines
					.skip(1)
					.map((line) -> line.split("\\|"))
					.map((row) -> new Book(row[0], row[1], List.of(row[2].split(","))))
					.toList();
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Error loading 'books.csv'!", ex);
		}
	}

	public static List<Book> random(int size) {
		var books = all();
		if (size >= books.size()) {
			return books;
		}
		return IntStream.range(0, size)
				.mapToObj( (x) -> randomBook(books)).toList();
	}

	public static Book random() {
		return randomBook(all());
	}

	private static Book randomBook(List<Book> books) {
		var idx = ThreadLocalRandom.current().nextInt(books.size());
		return books.get(idx);
	}
}
