package fr.uge.jee.bookstore;

public class Book {

    private final String title;
    private long ref;

    public Book(String title, long ref) {
        this.title = title;
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", ref=" + ref +
                '}';
    }
}
