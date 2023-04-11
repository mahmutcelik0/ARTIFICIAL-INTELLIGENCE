package org.example.Constant;

/*
* Proje genelinde kullanılan sabit değerlerin tek bir yerden erişilebilmesi
* ve kolaylıkla tutarlı bir şekilde değiştirilebilmesi için değerler enum içerisinde saklandı
* */
public enum Constans {
    GENECOUNT(PasswordEnum.PASSWORD.getValue().length()),
    CHROMOSOMECOUNT(20),
    ELITISMPERCENT(100),
    ELITCHROMOCOUNT(1),
    ASCIMIN(0),
    ASCIMAX(127),
    MUTATIONPERCENT(100),
    SOLUTIONCOUNT(3);
    private final int value;
    Constans(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
