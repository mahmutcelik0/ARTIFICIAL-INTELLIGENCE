package org.example;

import java.util.Random;

public class Main {
    static Random random = new Random();
    public static void main(String[] args) throws CloneNotSupportedException {
        Solution solution = new Solution();
        solution.solve();

//        Generation generation = new Generation();
//        Chromosome chromosome = new Chromosome(Constans.GENECOUNT.getValue());
//        chromosome.printChromosome();
//        generation.mutation(chromosome);
//        chromosome.printChromosome();
    }
}