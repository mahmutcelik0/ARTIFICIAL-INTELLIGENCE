package org.example;

import java.util.Random;

public class Main {
    private static final int geneCount = 17;
    private static final int chromosomeCount = 20;


    public static void main(String[] args) {
//        PasswordClass passwordClass = new PasswordClass("ChatGPT and GPT-4");
//
//        Chromosome chromosome = new Chromosome(geneCount);
//        chromosome.printChromosome();
//
//        System.out.println(passwordClass.fitnessFunction(chromosome.getChromosome()));
        Solution solution = new Solution(geneCount,chromosomeCount);
        solution.solve();

    }
}