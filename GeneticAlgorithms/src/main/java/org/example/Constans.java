package org.example;

public enum Constans {
    GENECOUNT(17),
    CHROMOSOMECOUNT(20),
    ELITISMPERCENT(100),
    ELITCHROMOCOUNT(1),
    ASCISIZE(127);
    private final int value;
    Constans(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
