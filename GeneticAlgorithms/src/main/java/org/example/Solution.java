package org.example;

public class Solution {
    private final int geneCount;
    private final int chromosomeCount;

    public Solution(int geneCount, int chromosomeCount) {
        this.geneCount = geneCount;
        this.chromosomeCount = chromosomeCount;
    }

    public void solve(){
        Generation firstGeneration = new Generation();
        firstGeneration.createFirstGeneration(Constans.CHROMOSOMECOUNT.getValue(),Constans.GENECOUNT.getValue());
        System.out.println("FIRST GENERATION");
        firstGeneration.printGeneration();

        Generation secondGeneration = new Generation();
        firstGeneration.makeElitism(Constans.ELITISMPERCENT.getValue(),Constans.ELITCHROMOCOUNT.getValue(),secondGeneration);
        System.out.println("SECOND GENERATION");
        secondGeneration.printGeneration();

        System.out.println("-----------------------");
        firstGeneration.getGeneration().values().forEach(System.out::println);
        firstGeneration.roulettWheel();
    }


}
