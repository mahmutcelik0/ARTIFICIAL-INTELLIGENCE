package org.example;

public enum Constans {
    GENECOUNT(7),
    CHROMOSOMECOUNT(10),
    ELITISMPERCENT(100),
    ELITCHROMOCOUNT(1),
    ASCIMIN(65),
    ASCIMAX(122),
    MUTATIONPERCENT(100);
    private final int value;
    Constans(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
