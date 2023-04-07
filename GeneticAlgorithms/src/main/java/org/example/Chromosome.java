package org.example;

import java.util.Random;

public class Chromosome {
    private char[] gene;

    private final Random random = new Random();

    public Chromosome(Integer geneCount){
        gene = new char[geneCount];
        fillGeneWithASCIchar();
    }

    public void fillGeneWithASCIchar(){
        for(int x = 0; x < gene.length ; x++){
            gene[x] = (char) random.nextInt(0,Constans.ASCISIZE.getValue()+1);
        }
    }
    public void setSecondHalf(char[] temp){
        int y = 0;
        for(int x = temp.length/2 ; x < temp.length ; y++){
            this.gene[x] = temp[y];
            x++;
        }
    }

    public void printChromosome(){
        StringBuilder str = new StringBuilder();
        for(char c: gene){
            str.append(c);
        }
        System.out.println(str);
    }

    public char[] getGene() {
        return gene;
    }

    public void setGene(char[] gene) {
        this.gene = gene;
    }
}
