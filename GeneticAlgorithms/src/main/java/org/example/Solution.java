package org.example;

import org.example.Constant.Constans;
import java.util.List;
import java.util.Map;

public class Solution {
    //Çözümün ne kadar sürdüğünü kaç generation geçtiğini tutan field lar
    private double startTime = 0;
    private double endTime = 0;
    private int generationCount = 0;

    private final PasswordClass passwordClass = new PasswordClass();

    /*
    * Genetik algoritma çözümü için gerekli adımların sırasıyla yapıldığı metod
    * -Adımlar-
    * 1- Başlangıç için ilk generation oluşturulur ve kontrol sonucu chromosome lar şifreyle eşleşmiyorsa döngü başlar
    * 2- next generation oluşturulur
    * 2.1- next generation a elitism ile old generationdan chromosome gelebilir (olasılıkların ve sabitlerin durumuna göre gelmeyebilir)
    * 2.2- Seçilim yapılması için roulettwheel yöntemi çağırılır ve old generationdan 2 tane chromosome seçilir
    * 2.3- Seçilen 2 chromosome crossover a gider ve crossover gerçekleşir. Oluşan yeni chromosome lar
    * next generation daki chromosome sayısı tamamlanasıya kadar eklenir (elitism tek sayıda ise son crossoverda son chromosome eklenmez)
    * 2.4- next generation için mutation başlar.
    * 2.4.a - En iyi chromosome two opt mutation a girer
    * 2.4.b - Geri kalan kromozomlar olasılıklar sonucunda normal mutation a girebilir
    * 2.5- nextgeneration chromosome larındaki işlemler bitti ve fitness function değerleri hesaplanıp value lara setlenir
    * 2.6- Çözümün generation da bulunup bulunmadığı kontrol edilir
    * 2.7- next generation bir sonraki döngüde old generation olacağı için en sonda old generation a setlenir
    * 2.8- isSolved değeri false gelmişse döngü devam eder
    *
    * */
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

    //Fitness function değerlerinin güncellenmesini sağlayan metod
    //Gelen entry nin chromosome unun genleri passwordClass a yollanır ve sonuç gelen entry nin chromosome unun value suna yazılır
    public void calculateFitnessFunctionValues(Map.Entry<Chromosome,Integer> entry){
        entry.setValue(passwordClass.fitnessFunction(entry.getKey().getGene()));
    }

    //Generation da herhangi bir chromosome un genlerinin şifreyle eşleşip eşleşmediği kontrol edilir ve sonuca göre
    //boolean değer döndürülür
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

    //GETTERS
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
