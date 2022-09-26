package fr.uge.jee.bookstore;

import java.util.Set;

public class Library {

    private final Set<Book> books;

    public Library(Set<Book> books) {
        this.books = Set.copyOf(books);
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }
}
