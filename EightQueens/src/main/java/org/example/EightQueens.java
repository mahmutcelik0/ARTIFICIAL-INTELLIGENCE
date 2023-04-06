package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EightQueens {
    private static Integer[] board = new Integer[8]; // Chess Board -> Elements are columns and Indexes are rows
    private static final Map<Integer[], Integer> neighborStates = new LinkedHashMap<>();
    // Neighbor states of current board -> It holds neighbor state and number of eating each other count
    private final Random random = new Random();

    private static final Object[][] solutionTable = new Object[9][4]; //Keeps our solutions results

    private static int randomRestartCount = 0; //Counts random restart for each solution tour

    private static Replacement replacement = new Replacement(new ArrayList<>(), 0);
    /*
     * Replacement has two fields
     * 1. field is a list that holds replacement counts for each random restart
     * 2. field is an int that holds current replacement count for that board
     * */

    /**
     * It calls solve() nine times and sets values in each loop
     * To calculate time -> System.nanoTime()
     * Finally prints the solutions to the console
     */
    public void solveNineTimes() {

        for (int x = 0; x < 9; x++) {
            double time = 0;
            long startTime = System.nanoTime();

            replacement.setCurrentReplacementCount(0); // Set 0 replacement - beacuse new board will start
            replacement.setReplacementCounts(new ArrayList<>()); //Refresh the replacement list beacyse new tour will start
            randomRestartCount = 0; // Set 0 random restart - beacuse new board will start

            solve();

            long endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            solutionTable[x][0] = replacement.getSumOfReplacements(); //Fill the table
            solutionTable[x][1] = (double) randomRestartCount;
            solutionTable[x][2] = time;
            solutionTable[x][3] = replacement.getReplacementCounts();

        }
        printSolutionTable();
    }

    /**
     * 1 -> Generate random chess table via randomReplacement() method
     * 2 -> While loop continues until a solution is found
     * 2.1 -> If program encountered a local min, method calls itself again
     * (Current state value -> 5 , neighbor states values are 5 or bigger - It means local min)
     * 3 -> If everthing is okey, State will change via changeState()
     * (Current state value -> 5 , neighbor states contain a number that lower than 5)
     */
    public void solve() {
        randomPlacement();
        while (numOfEating(board) != 0) { //RANDOM RESTART YAPTIKTAN SONRA ONCEKI TURDAKI REPLACEMENT LARI DA SAYMALI MIYIZ?
            replacement.increaseFinalReplacement();
            fillNeighbors();
            if (!neighborStates.values().stream().anyMatch(e -> e < numOfEating(board))) {
                replacement.stateChange();
                randomRestartCount++;
                solve();
                break;
            }
            changeState();
        }
    }

    /**
     * Randomly places queens
     */
    public void randomPlacement() {
        for (int x = 0; x < board.length; x++) {
            board[x] = random.nextInt(0, 8);
        }
    }

    /**
     * It has one parameter and it calculates the parameter's eating count
     * 1 - Cross eating control
     * 2 - Horizontal eating control
     */
    public Integer numOfEating(Integer[] val) {
        int count = 0;
        for (int x = 0; x < val.length - 1; x++) {
            for (int y = x + 1; y < val.length; y++) {
                if ((Math.abs(val[y] - val[x])) == (Math.abs(y - x)) || val[x] - val[y] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates 56 neighbor states of current state
     */
    public void fillNeighbors() {
        neighborStates.clear();
        for (int x = 0; x < board.length; x++) {
            Integer[] copyBoard = board.clone();
            for (int y = 0; y < board.length; y++) {
                if (y != board[x]) {
                    copyBoard[x] = y;
                    neighborStates.put(copyBoard.clone(), numOfEating(copyBoard));
                }
            }
        }
    }

    /**
     * It changes the current state to neighbor state that has min number value
     * ! -> If there are more than 1 state that has min number value, program will choose next state randomly
     */
    public void changeState() {
        int minNumber = findMin();
        List<Map.Entry<Integer[], Integer>> arr = neighborStates
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == minNumber)
                .toList();

        board = arr.get(random.nextInt(arr.size())).getKey().clone();
    }

    /**
     * Finds the min number of eating in neighbor states values
     */
    private Integer findMin() {
        return neighborStates
                .values()
                .stream()
                .min(Integer::compare)
                .orElseThrow(NullPointerException::new);
    }

    /**
     * Printing solution table
     */
    public void printSolutionTable() {
        System.out.printf("-------------------------------------------------------------------------------------%n");
        System.out.printf("| %-30s | %-30s | %15s |%n", "Replacement Count", "Random Restart Count", "Time");
        System.out.printf("-------------------------------------------------------------------------------------%n");
        for (int y = 0; y < solutionTable.length; y++) {
            System.out.printf("| %-30s | %-30s | %15s |%n", solutionTable[y][0], solutionTable[y][1], solutionTable[y][2]);
        }
        System.out.printf("-------------------------------------------------------------------------------------%n");
        System.out.println("| Replacement counts per Loop");
        for (int z = 0; z < solutionTable.length; z++) {
            System.out.println("| TOUR " + (z + 1) + ": " + solutionTable[z][3].toString());
        }
        System.out.printf("-------------------------------------------------------------------------------------%n");
    }
}

/**
 * Below class has 2 fields to hold replacement count for table - To provide each tours replacement count
 */
class Replacement {
    List<Integer> replacementCounts;
    Integer currentReplacementCount;

    public Replacement(List<Integer> replacementCounts, Integer currentReplacementCount) {
        this.replacementCounts = replacementCounts;
        this.currentReplacementCount = currentReplacementCount;
    }

    public List<Integer> getReplacementCounts() {
        return replacementCounts;
    }

    public void setReplacementCounts(List<Integer> replacementCounts) {
        this.replacementCounts = replacementCounts;
    }

    public void setCurrentReplacementCount(Integer currentReplacementCount) {
        this.currentReplacementCount = currentReplacementCount;
    }

    //Increases number of replacement of current tour
    public void increaseFinalReplacement() {
        this.currentReplacementCount++;
    }

    //Calculates the final replacement count of tour
    public int getSumOfReplacements() {
        AtomicInteger sum = new AtomicInteger();
        replacementCounts.forEach(sum::addAndGet);
        return sum.get();
    }

    //While state changes, current replacement count will add to the list and set current rep. to 0
    public void stateChange() {
        replacementCounts.add(currentReplacementCount);
        currentReplacementCount = 0;
    }
}