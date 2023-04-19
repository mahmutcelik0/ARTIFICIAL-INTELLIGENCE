package org.example;

import org.example.Constant.Constans;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* Generation class ı içerisinde genetik algoritmaların çözümü için gerekli yöntemler bulunuyor.
* İçerisinde Chromosome ve Fitness function değerini tutan Integer lardan oluşan Map bulunduruyor
* 1- First generation un oluşturulması
* 2- Elitism yapılması
* 3- Seçilim için roulettwheel yönteminin implementation u
* 4- Seçilim sonrası 2 genin crossover yapılması
* 5- Normal mutasyon
* 6- two opt yöntemiyle mutasyon
* 7- Generation un bastırılması
* Bu class içerisinde sağlanan functionality
* */
public class Generation {
    private Map<Chromosome,Integer> generation = new LinkedHashMap<>();
    private PasswordClass passwordClass = new PasswordClass();
    private final Random random = new Random();

    /*
    * Programın başlangıcında ilk generation un random şekilde oluşturulması gerekiyor.
    * Constant enum içerisinde chromosome sayısı kadar kromozom içerecek şekilde generation oluşturulur
    * */
    public void createFirstGeneration(){
        for(int x = 0; x < Constans.CHROMOSOMECOUNT.getValue() ; x++){
            Chromosome tempChromosom = new Chromosome();
            generation.put(tempChromosom,passwordClass.fitnessFunction(tempChromosom.getGene()));
        }
    }

    //ELITISM PERCENT TAN KUCUK BIR DOUBLE DEGER GELIRSE ELITISM GERCEKLESIR
    /*
    * Önceki generation un en iyi chromosome unun sonraki nesle aktarılması elitism dir
    * Elitism yapılmak istenmesi ve kaç tane chromosome un elitism ile sonraki nesle aktarılacağı enum içerisinden alınır
    * ve değerlere uygun şekilde aktarım gerçekleşir.
    * Olasılık olarak random değer elitism sabit oranından düşük gelirse elitism yapılır
    * En iyi chromosome u bulmak için stream api kullanılarak map value ları kullanılarak sort landı ve list e aktarıldı
    * Kaç tane chromosome aktarılmak isteniyorsa o kadar çalışacak for döngüsü ile sonraki nesle aktarım tamamlanır
    * */
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

    /*
    * Roulett wheel sonucu seçilen 2 chromosome arasındaki cross over yapmayı sağlar
    * İlk chromosome un genleri alınır. Sonrasında ilk chromosome un ikinci yarısına ikinci chromosome un ikinci yarısı yerleştirilir
    * İkinci chromosome un ikinci yarısına ilk chromosome un genlerini tutan temp ten ikinci yarısı getirilir
    * Bu sayede iki chromosome arasında cross over gerçekleşmiş olur
    * */
    public List<Chromosome> makeCrossOver(Chromosome firstChromo, Chromosome secondChromo){
        char[] temp = firstChromo.getGene().clone();
        firstChromo.setSecondHalf(secondChromo.getGene());
        secondChromo.setSecondHalf(temp);
        return List.of(firstChromo, secondChromo);
    }

    /*
    * Mutasyon için tüm chromosome lara two opt kullanmam veya sadece en iyi chromosome a two opt kullanmam çözüme ulaştırmıyordu
    * Hatta belirli tekrar sonrasında çözümün belirli chromosome a doğru evrildiği ve sonuctan cok o chromosome un son haline doğru
    * bir sonuç bulunuyordu. Bunu önlemek için generation daki en başarılı chromosome a two opt mutation geri kalanlara
    * şans değerleri uygun olduğunda normal mutation yaptım
    * Sort lanmış map dolaşılır ve ilk chromosome two opt mutation a yollanır diğer elemanlar normal mutation a yollanır
    * */
    public void mutation(){
        AtomicBoolean firstElement = new AtomicBoolean(true);
        generation.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(e->{
            if(firstElement.get()) {
                try {
                    twoOptMutation(e.getKey());
                    firstElement.set(false);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            }else{
                normalMutation(e.getKey());
            }
        });
    }

    /*
    * Two Opt mutation da chromosome daki gene ler tek tek yer değiştirilerek fitness functionları tekrar hesaplanır.
    * Gelen chromosome için değişmeler sonucu en iyi yeni gen set i set lenir.
    * Önceki değerle karşılaştırılarak devam edilir. Yerdeğiştirmeler sonucunda daha iyi bir fitness function değeri
    * elde ediliyorsa yer değiştirmeye devam edilir.
    * */
    public void twoOptMutation(Chromosome chromosome) throws CloneNotSupportedException {
        Chromosome mutatedChromosome = chromosome;

        Chromosome bestMutated = (Chromosome) mutatedChromosome.clone();

        int beforeTourResult = passwordClass.fitnessFunction(bestMutated.getGene());
        boolean continueLoop = true;

        while(continueLoop){ //passwordClass.fitnessFunction(tempGene)< beforeTourResult -- İyileşme olduğu sürece devam
            char[] tempGene = bestMutated.getGene().clone();

            //Chromsome daki gene ler dolaşılır ve yer değiştirilir
            for(int x = 0 ; x < tempGene.length-1 ; x++){
                for(int y = x+1 ; y < tempGene.length ; y++){
                    char current = tempGene[x];
                    tempGene[x] = tempGene[y];
                    tempGene[y] = current;

                    //Yeni oluşan daha iyiyse bestmutated a setlenir değilse yapılan değişiklik geri alınır
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

    /*
    * Mutasyon oranı Constants enum dan alınır ve metod içerisinde random oluşturulmuş değer daha küçükse chromosome a
    * normal mutasyon gerçekleştirilir. Bu sayede çeşitlilik arttırılmış olundu
    * Normal mutation için random 1 tane gene seçilir ve değeri belirttiğimiz contant içerisinden yani asci table
    * dan random 1 karakter le değiştirilir
    * */
    public void normalMutation(Chromosome chromosome) {
        double randomPercent = random.nextDouble(0,100);
        if(randomPercent < Constans.MUTATIONPERCENT.getValue()){
            int randomGene = random.nextInt(0, Constans.GENECOUNT.getValue());
            char[] temp = chromosome.getGene();
            temp[randomGene] = (char) random.nextInt(Constans.ASCIMIN.getValue(),Constans.ASCIMAX.getValue()+1);
            chromosome.setGene(temp.clone());
        }
    }

    /*
    * Generation un bastırılması için metod -- SİLİNEBİLİR !!!!
    * */
    public void printGeneration(){
        int number = 1;
        for(Map.Entry<Chromosome,Integer> e : generation.entrySet()){
            System.out.println("---------------------------------------------------------");
            e.getKey().printChromosome();
            System.out.println(e.getValue());
        }
    }

    public void printTheBestChromosome(int number) {
        Map.Entry<Chromosome, Integer> minEntry =  Collections.min(generation.entrySet(),Map.Entry.comparingByValue());
        System.out.print(number+". GENERATION'S BEST:\t");
        minEntry.getKey().printChromosome();
        System.out.print("\t"+minEntry.getValue()+"\n");

    }

    //GETTER AND SETTER
    public Map<Chromosome, Integer> getGeneration() {
        return generation;
    }

    public void setGeneration(Map<Chromosome, Integer> generation) {
        this.generation = generation;
    }


}
