package org.example;

import java.util.*;
import java.util.stream.Stream;

public class Generation {
    private Map<Chromosome,Integer> generation = new LinkedHashMap<>();
    private PasswordClass passwordClass = new PasswordClass("ChatGPT and GPT-4");
    private final Random random = new Random();

    public void createFirstGeneration(Integer chromosomeCount, Integer geneCount){
        for(int x = 0 ; x < chromosomeCount ; x++){
            Chromosome tempChromosom = new Chromosome(geneCount);
            generation.put(tempChromosom,passwordClass.fitnessFunction(tempChromosom.getGene()));
        }
    }

    //ELITISM PERCENT TAN KUCUK BIR DOUBLE DEGER GELIRSE ELITISM GERCEKLESIR
    public void makeElitism(double elitismPercent,int count,Generation nextGeneration){
        if (count<=0) return;
        if(random.nextDouble(0,100.1)< elitismPercent){
            List<Map.Entry<Chromosome,Integer>> sorted = generation.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();
            for(int x = 0 ; x < count ; x++){
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

        System.out.println("MULT NUMBER: "+multiplyNumber);
        List<Chromosome> selectedChromosoms = new ArrayList<>();
        List<Map.Entry<Chromosome,Integer>> listOfChromosoms = new ArrayList<>(generation.entrySet().stream().toList());

        //1 i seçilmişlere eklenecek sonrasında o chromosome un seçilebilme olasılığı kaldırılacak
        double maxRandomNumber = 100.00;

        for(int x = 0 ; x < 2 ; x++){

            double spin = random.nextDouble(0,maxRandomNumber);

            for(int y = 0 ; y < listOfChromosoms.size() ; y++){
                if(spin - (((double)1 /listOfChromosoms.get(y).getValue())*multiplyNumber) <= 0){
                    selectedChromosoms.add(listOfChromosoms.get(y).getKey()); //maple turla index alamıyoruz
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
