package org.example;

import org.example.Constant.Constans;

import java.util.Random;

public class Chromosome implements Cloneable{
    private char[] gene;

    private final Random random = new Random();

    public Chromosome(){
        gene = new char[Constans.GENECOUNT.getValue()];
        fillGeneWithASCIchar();
    }

    public void fillGeneWithASCIchar(){
        for(int x = 0; x < gene.length ; x++){
            gene[x] = (char) random.nextInt(Constans.ASCIMIN.getValue(),Constans.ASCIMAX.getValue()+1);
        }
    }
    public void setSecondHalf(char[] temp){
        for(int x = temp.length/2 ; x < temp.length ; x++){
            this.gene[x] = temp[x];
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Chromosome temp = (Chromosome) super.clone();
        temp.setGene(this.gene.clone());
        return temp;
    }
}
