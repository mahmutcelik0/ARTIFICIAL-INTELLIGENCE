package org.example;

import org.example.Constant.Constans;
import org.example.Constant.PasswordEnum;
import java.util.List;
import java.util.Map;

public class Solution {
    private double startTime = 0;
    private double endTime = 0;
    private int generationCount = 0;

    private final PasswordClass passwordClass = new PasswordClass(PasswordEnum.PASSWORD.getValue());

    public void solve() throws CloneNotSupportedException {
        startTime = System.nanoTime();

        Generation oldGeneration = new Generation();
        oldGeneration.createFirstGeneration();

        boolean isSolved = solvedControl(oldGeneration);
        int number = 1;
        while (!isSolved){
            Generation nextGeneration = new Generation();
            number++;

            oldGeneration.makeElitism(nextGeneration);
            while (nextGeneration.getGeneration().size() < Constans.CHROMOSOMECOUNT.getValue()) {
                List<Chromosome> temp = oldGeneration.roulettWheel();
                oldGeneration.makeCrossOver((Chromosome) temp.get(0).clone(), (Chromosome) temp.get(1).clone()).forEach(e -> {
                    if (nextGeneration.getGeneration().size() != Constans.CHROMOSOMECOUNT.getValue()) {
                        nextGeneration.getGeneration().put(e, passwordClass.fitnessFunction(e.getGene()));
                    }
                });
            }

            nextGeneration.mutation();

            nextGeneration.getGeneration().entrySet().forEach(this::calculateFitnessFunctionValues);

            isSolved = solvedControl(nextGeneration);

            oldGeneration.setGeneration(nextGeneration.getGeneration());

            nextGeneration.printGeneration();
        }
        endTime = System.nanoTime();

        generationCount = number;

        System.out.println("FINAL EXECUTED TOUR: "+ number);
    }

    public void calculateFitnessFunctionValues(Map.Entry<Chromosome,Integer> entry){
        entry.setValue(passwordClass.fitnessFunction(entry.getKey().getGene()));
    }

    public boolean solvedControl(Generation generation){
        return generation.getGeneration().keySet().stream().anyMatch(e -> passwordClass.fitnessFunction(e.getGene()) == 0);
    }

    //WILL DELETE
/*
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
    }*/


    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public int getGenerationCount() {
        return generationCount;
    }
}
