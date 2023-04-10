package org.example;

import java.util.*;
import java.util.stream.Stream;

public class Generation {
    private Map<Chromosome,Integer> generation = new LinkedHashMap<>();
    private PasswordClass passwordClass = new PasswordClass("ChatGPT");
    private final Random random = new Random();

    public void createFirstGeneration(){
        for(int x = 0 ; x < Constans.CHROMOSOMECOUNT.getValue() ; x++){
            Chromosome tempChromosom = new Chromosome();
            generation.put(tempChromosom,passwordClass.fitnessFunction(tempChromosom.getGene()));
        }
    }

    //ELITISM PERCENT TAN KUCUK BIR DOUBLE DEGER GELIRSE ELITISM GERCEKLESIR
    public void makeElitism(Generation nextGeneration){
        if(Constans.ELITCHROMOCOUNT.getValue() <= 0) return;
        if(random.nextDouble(0,100.1)< Constans.ELITISMPERCENT.getValue()){
            List<Map.Entry<Chromosome,Integer>> sorted = generation.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();
            for(int x = 0 ; x < Constans.ELITCHROMOCOUNT.getValue() ; x++){
                nextGeneration.getGeneration().put(sorted.get(x).getKey(),sorted.get(x).getValue());
            }
        }
    }

    public List<Chromosome> roulettWheel(){
        // (15/17  + 1/16) * x = 100
        double sumWithDivided = 0;
        for (Integer num : generation.values()){
            sumWithDivided += ((double) 1 /num);
        }
        double multiplyNumber = 100/ sumWithDivided;

        List<Chromosome> selectedChromosoms = new ArrayList<>();
        List<Map.Entry<Chromosome,Integer>> listOfChromosoms = new ArrayList<>(generation.entrySet().stream().toList());

        //1 i seçilmişlere eklenecek sonrasında o chromosome un seçilebilme olasılığı kaldırılacak
        double maxRandomNumber = 100.00;

        for(int x = 0 ; x < 2 ; x++){

            double spin = random.nextDouble(0,maxRandomNumber);

            for(int y = 0 ; y < listOfChromosoms.size() ; y++){
                if(spin - (((double)1 /listOfChromosoms.get(y).getValue())*multiplyNumber) <= 0){
                    selectedChromosoms.add(listOfChromosoms.get(y).getKey());
                    maxRandomNumber-= ((double)1/listOfChromosoms.get(y).getValue()*multiplyNumber);
                    listOfChromosoms.remove(y);
                    break;
                }else {
                    spin-= (double) 1 /listOfChromosoms.get(y).getValue()*multiplyNumber;
                }
            }
        }
        return selectedChromosoms;
    }

    public List<Chromosome> makeCrossOver(Chromosome firstChromo, Chromosome secondChromo){
        char[] temp = firstChromo.getGene().clone();
        firstChromo.setSecondHalf(secondChromo.getGene());
        secondChromo.setSecondHalf(temp);
        return List.of(firstChromo, secondChromo);
    }

    public void mutation(Chromosome chromosome) throws CloneNotSupportedException {
        double randomNumber = random.nextDouble(0,100);
        if(Constans.MUTATIONPERCENT.getValue() > randomNumber){
            startMutationOnRandomChromosome(chromosome);
        }
    }

    public void startMutationOnRandomChromosome(Chromosome chromosome) throws CloneNotSupportedException {
        Chromosome mutatedChromosome = chromosome; //generation.keySet().stream().toList().get(random.nextInt(0,Constans.CHROMOSOMECOUNT.getValue()));

        Chromosome bestMutated = (Chromosome) mutatedChromosome.clone();

        int beforeTourResult = passwordClass.fitnessFunction(bestMutated.getGene());
        boolean continueLoop = true;

        while(continueLoop){ //passwordClass.fitnessFunction(tempGene)< beforeTourResult
            char[] tempGene = bestMutated.getGene().clone();

            for(int x = 0 ; x < tempGene.length-1 ; x++){
                for(int y = x+1 ; y < tempGene.length ; y++){
                    char current = tempGene[x];
                    tempGene[x] = tempGene[y];
                    tempGene[y] = current;

                    if(passwordClass.fitnessFunction(tempGene) <passwordClass.fitnessFunction(bestMutated.getGene())){
                        bestMutated.setGene(tempGene.clone());
                    }else{
                        current = tempGene[y];
                        tempGene[y] = tempGene[x];
                        tempGene[x] = current;
                    }
                }
            }
            if(!(passwordClass.fitnessFunction(tempGene) <beforeTourResult)){
                continueLoop = false;
            }else{
                beforeTourResult = passwordClass.fitnessFunction(tempGene);
            }
        }
        mutatedChromosome.setGene(bestMutated.getGene().clone());


    }

    public void printGeneration(){
        int number = 1;
        for(Map.Entry<Chromosome,Integer> e : generation.entrySet()){
            System.out.println("---------------------------------------------------------");
            System.out.println((number++)+". CHROMOSOME: ");
            e.getKey().printChromosome();
            System.out.println("---------------------------------------------------------");
            System.out.println(e.getValue());
        }
    }

    public Map<Chromosome, Integer> getGeneration() {
        return generation;
    }

    public void setGeneration(Map<Chromosome, Integer> generation) {
        this.generation = generation;
    }
}
