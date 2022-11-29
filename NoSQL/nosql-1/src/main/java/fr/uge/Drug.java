package fr.uge;

public final class Drug {

    private final int cip7;
    private final int cis;
    private final String name;

    public Drug(int cip7, int cis, String name) {
        this.cip7 = cip7;
        this.cis = cis;
        this.name = name;
    }

    public int cip7() {
        return cip7;
    }

    public int cis() {
        return cis;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Drug{" +
            "cip7=" + cip7 +
            ", cis='" + cis + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
