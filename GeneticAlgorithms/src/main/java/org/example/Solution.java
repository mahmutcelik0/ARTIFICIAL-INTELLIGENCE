package org.example;

import java.util.List;

public class Solution {
    private final PasswordClass passwordClass = new PasswordClass("ChatGPT and GPT-4");

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
//        firstGeneration.getGeneration().values().forEach(System.out::println);
//        firstGeneration.roulettWheel().stream().forEach(e-> System.out.println(e.getGene()));

        while (secondGeneration.getGeneration().size() < Constans.CHROMOSOMECOUNT.getValue()){
            System.out.println("LOOP:  "+ secondGeneration.getGeneration().size());
            List<Chromosome> temp = firstGeneration.roulettWheel();
            firstGeneration.makeCrossOver(temp.get(0),temp.get(1)).forEach(e ->{
                if(secondGeneration.getGeneration().size() != Constans.CHROMOSOMECOUNT.getValue()){
                    System.out.println(secondGeneration.getGeneration().containsKey(e)); //NASIL AYNI KROMOZOM DENK GELIYOR
                    secondGeneration.getGeneration().put(e, passwordClass.fitnessFunction(e.getGene()));
                }
            });
        }
        System.out.println("SECOND GEN");
        secondGeneration.printGeneration();
    }
}
