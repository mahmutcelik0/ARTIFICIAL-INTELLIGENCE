package org.example;
public class Main {
    //Çözüm yolu olarak Map kullanmayı tercih ettik. Alternatif olarak temporary array de kullanabilirdik.
    //Aybars hocaya sorduğumuzda Map şeklinde kalabileceğini ve size de iletmemizi söyledi
    public static void main(String[] args) {
        EightQueens queens = new EightQueens();
        queens.solveNineTimes();
    }
}