package org.example;

import org.example.Constant.Constans;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Solution[] solutions = new Solution[Constans.SOLUTIONCOUNT.getValue()];
        for(int x = 0 ; x < Constans.SOLUTIONCOUNT.getValue(); x++){
            Solution solution = new Solution();
            solution.solve();
            solutions[x] = solution;
        }
        printFinalTable(solutions);
    }

    public static void printFinalTable(Solution[] solutions){
        System.out.println(" ");
        for (Solution solution : solutions) {
            System.out.println("START TIME: "+ solution.getStartTime());
            System.out.println("END TIME: "+ solution.getEndTime());
            double executionTime = (solution.getEndTime()-solution.getStartTime())/1000000.0;
            System.out.println("EXECUTION TIME: "+ executionTime+" ms");
            System.out.println("GENERATION COUNT: "+ solution.getGenerationCount());
        }
    }
}