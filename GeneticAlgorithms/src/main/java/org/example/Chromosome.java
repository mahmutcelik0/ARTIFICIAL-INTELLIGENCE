package org.example;

import org.example.Constant.Constans;

import java.util.Random;
/*
* Generation larda kullanılacak Chromosome class ı.
* İçerisinde genleri bulundurur. Genlerin uzunlukları şifrenin boyutu kadar olur ve değerleri
* constructor içerisinde otomatik olarak dolduran metod çağırılır
* */
public class Chromosome implements Cloneable{
    private char[] gene;

    private final Random random = new Random();

    /*
    * Yeni bir Chromosome oluşturulduğunda gene array i otomatik olarak asci tablosundaki değerler arasından
    * random gelen karakterlerle doldurulur
    * */
    public Chromosome(){
        gene = new char[Constans.GENECOUNT.getValue()];
        fillGeneWithASCIchar();
    }

    /*
    * Constant enum içerisinden min ve max aralığında int primitive değeri üretilir ve bu sayede gene deki değerler
    * asci tablosundaki karakterlerle random şekilde doldurulmuş olunur. Constants enum içerisinde aralık değiştirilebilir
    * */
    public void fillGeneWithASCIchar(){
        for(int x = 0; x < gene.length ; x++){
            gene[x] = (char) random.nextInt(Constans.ASCIMIN.getValue(),Constans.ASCIMAX.getValue()+1);
        }
    }

    /*
    * Çaprazlamada kullanılan metod. İçerisine gelen array deki değerler şuanki Chromosome instance ındaki genin 2. kısmına kopyalar
    * Chromosome 1 - Chromosome 2
    * chromosome1.setSecondHalf(chromosome2.getGene()) dediğimiz zaman chromosome1 in gene array ine chromosome 2 nin 2.
    * kısmı set lenir
    * İki taraf için de çağırılma olacağından dolayı sonuç olarak cross over işlemi yapılmış olunur
    * */
    public void setSecondHalf(char[] temp){
        for(int x = temp.length/2 ; x < temp.length ; x++){
            this.gene[x] = temp[x];
        }
    }

    /*
    * Chromosome daki değerlerin düzgün bir şekilde bastırılması için metod
    * */
    public void printChromosome(){
        StringBuilder str = new StringBuilder();
        for(char c: gene){
            str.append(c);
        }
        System.out.println(str);
    }

    //Getter Setter and Clone methods
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
