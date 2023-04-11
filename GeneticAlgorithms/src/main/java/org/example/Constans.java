package org.example;

public enum Constans {
    GENECOUNT(PasswordEnum.PASSWORD.getValue().length()),
    CHROMOSOMECOUNT(20),
    ELITISMPERCENT(100),
    ELITCHROMOCOUNT(1),
    ASCIMIN(0),
    ASCIMAX(127),
    MUTATIONPERCENT(100);
    private final int value;
    Constans(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
