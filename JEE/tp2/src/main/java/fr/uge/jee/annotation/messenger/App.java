package fr.uge.jee.annotation.messenger;

public class App {
    public static void main(String[] args) {
        var p = getPersonBy();

        p.
    }

    public static Person getPersonBy() {
        return new Person.Builder()
                .setName("John")
                .setAge(42)
                .build();
    }
}
