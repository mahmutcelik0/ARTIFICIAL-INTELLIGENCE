package org.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
    private final PasswordClass passwordClass = new PasswordClass(PasswordEnum.PASSWORD.getValue());

    public void solve() throws CloneNotSupportedException {
        Generation oldGeneration = new Generation();
        oldGeneration.createFirstGeneration();
//        System.out.println("FIRST GENERATION");
//        oldGeneration.printGeneration();


        boolean firstTour = true;
        boolean isSolved = solvedControl(oldGeneration);
        int number = 1;
        while (!isSolved){
            Generation nextGeneration = new Generation();
            System.out.println(number + "TOUR");
            number++;

            if(oldGeneration ==nextGeneration) System.out.println("yALANN");
            nextGeneration = new Generation();

            oldGeneration.makeElitism(nextGeneration);
            while (nextGeneration.getGeneration().size() < Constans.CHROMOSOMECOUNT.getValue()) {
                List<Chromosome> temp = oldGeneration.roulettWheel();
                Generation finalNextGeneration = nextGeneration;
                oldGeneration.makeCrossOver((Chromosome) temp.get(0).clone(), (Chromosome) temp.get(1).clone()).forEach(e -> {
                    if (finalNextGeneration.getGeneration().size() != Constans.CHROMOSOMECOUNT.getValue()) {
                        finalNextGeneration.getGeneration().put(e, passwordClass.fitnessFunction(e.getGene()));
                    }
                });
            }


            //MUTATION ===????
            Map.Entry<Chromosome,Integer> minChromo = Collections.min(nextGeneration.getGeneration().entrySet(),Map.Entry.comparingByValue());
            nextGeneration.mutation();

            nextGeneration.getGeneration().entrySet().forEach(this::calculateFitnessFunctionValues);

            firstTour = false;

            isSolved = solvedControl(nextGeneration);

            if(!firstTour){
                oldGeneration.setGeneration(nextGeneration.getGeneration());
            }

            nextGeneration.printGeneration();
            //printTheMin(nextGeneration);
        }
        System.out.println("FINAL EXECUTED TOUR: "+ number);
    }

    public void calculateFitnessFunctionValues(Map.Entry<Chromosome,Integer> entry){
        entry.setValue(passwordClass.fitnessFunction(entry.getKey().getGene()));
    }

    public boolean solvedControl(Generation generation){
        return generation.getGeneration().keySet().stream().anyMatch(e -> passwordClass.fitnessFunction(e.getGene()) == 0);
    }

    public void printTheMin(Generation generation){
        AtomicInteger inte = new AtomicInteger();
        inte.set(100);
        generation.getGeneration().keySet().stream().forEach(e -> {
            if(passwordClass.fitnessFunction(e.getGene()) < inte.get()){
                inte.set(passwordClass.fitnessFunction(e.getGene()));
            }
        });
//        generation.getGeneration().entrySet().stream().filter(e -> e.getValue() == inte.get()).forEach(System.out::println);
        System.out.println("MIN VALUE: " +inte.get());
    }
}
