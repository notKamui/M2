package fr.uge.jee.annotation.messenger;

public class Person {

    public static class Builder {
        private String name;
        private int age;

        public Builder() {
            this.name = "unknown";
            this.age = 0;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            return new Person(name, age);
        }
    }

    private final String name;
    private final int age;

    private Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
