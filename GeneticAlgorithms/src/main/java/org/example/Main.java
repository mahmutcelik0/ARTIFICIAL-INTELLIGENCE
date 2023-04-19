package org.example;

import org.example.Constant.Constans;

public class Main {

    /**
     * Constant değerler içerisinden kaç tur çözüleceğini alıp ve o kadar tekrarda çözdürür. Sonuçları Solution array inde saklar
     * Sonrasında bastırılması gerçekleşir
     * */
    public static void main(String[] args) throws CloneNotSupportedException {
        Solution[] solutions = new Solution[Constans.SOLUTIONCOUNT.getValue()];
        for(int x = 0 ; x < Constans.SOLUTIONCOUNT.getValue(); x++){
            Solution solution = new Solution();
            solution.solve();
            solutions[x] = solution;
        }
        printFinalTable(solutions);
    }

    /**
     * Sonuçların dokumantasyondaki isteklere göre düzenlenip bastırılmasını gerçekleştirir
     * */
    public static void printFinalTable(Solution[] solutions){
        System.out.println(" ");
        int avgGenCount = 0;

        System.out.printf("---------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-20s | %-20s | %-20s | %-20s |%n", "START TIME ", "END TIME", "EXECUTION TIME", "GENERATION COUNT");
        System.out.printf("---------------------------------------------------------------------------------------------%n");

        for (Solution solution : solutions) {
            double executionTime = (solution.getEndTime()-solution.getStartTime())/1000000.0;
            avgGenCount += solution.getGenerationCount();
            System.out.printf("| %-20s | %-20s | %-20s | %-20s |%n", solution.getStartTime(),solution.getEndTime(),executionTime+" ms",solution.getGenerationCount());
        }

        System.out.printf("---------------------------------------------------------------------------------------------%n");
        avgGenCount/=Constans.SOLUTIONCOUNT.getValue();
        System.out.println("AVERAGE GENERATION COUNT: "+ avgGenCount);
        System.out.printf("---------------------------------------------------------------------------------------------%n");

    }
}