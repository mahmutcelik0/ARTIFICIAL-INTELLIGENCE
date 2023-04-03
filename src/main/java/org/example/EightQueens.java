package org.example;

import java.util.*;

public class EightQueens {
    private static Integer[] board = new Integer[8];
    private static final Map<Integer[], Integer> neighborStates = new LinkedHashMap<>();
    private final Random random = new Random();

    private static final Double[][] solutionTable = new Double[10][3];

    private static int replacementCount = 0;
    private static int randomRestartCount = 0;

    public void solveNineTimes() {
        for (int x = 0; x < 9; x++) {
            System.out.println(x + ". STEP:");
            double time = 0;
            long startTime = System.nanoTime();
            replacementCount = 0;
            randomRestartCount = 0;
            solve();
            long endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            solutionTable[x][0] = (double) replacementCount;
            solutionTable[x][1] = (double) randomRestartCount;
            solutionTable[x][2] = time;
        }
        printSolutionTable();
    }

    public void solve() {
        randomPlacement();
        printBoard();
        int number = 1;
        while (numOfEating(board) != 0) {
            replacementCount++;
            System.out.println("NUMBER:" + number++);
            fillNeighbors();
            System.out.println("CONTAINS VAL:" + neighborStates.containsValue(1));
            System.out.println("EATING IS 1: " + (numOfEating(board) == 1));
            if (!neighborStates.values().stream().anyMatch(e -> e < numOfEating(board))) {
                randomRestartCount++;
                System.out.println("-----RANDOM RESTART IS STARTING -----");
                printOutStates();
                solve();
                break;
            }
            changeState();
        }
    }


    public void randomPlacement() {
        for (int x = 0; x < board.length; x++) {
            board[x] = random.nextInt(0, 8);
        }
    }

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

    private Integer findMin() {
        return neighborStates
                .values()
                .stream()
                .min(Integer::compare)
                .orElseThrow(NullPointerException::new);
    }

    public void changeState() {
        int minNumber = findMin();
        List<Map.Entry<Integer[], Integer>> arr = neighborStates
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == minNumber)
                .toList();

        board = arr.get(random.nextInt(arr.size())).getKey().clone();
        System.out.println("------------------------------");
        arr.forEach(e -> System.out.println(Arrays.toString(e.getKey()) + " " + e.getValue()));
        System.out.println("------------------------------");
        System.out.println(Arrays.toString(board));

    }

    public void printBoard() {
        System.out.println(Arrays.toString(board));
        System.out.println(numOfEating(board));
    }

    public void printSolutionTable() {
        System.out.printf("-------------------------------------------------------------------------------------%n");
        System.out.printf("| %-30s | %-30s | %15s |%n", "Replacement Count", "Random Restart Count", "Time");
        System.out.printf("-------------------------------------------------------------------------------------%n");
        for (int y = 0; y < 9; y++) {
            System.out.printf("| %-30s | %-30s | %15s |%n", solutionTable[y][0],  solutionTable[y][1], solutionTable[y][2]);
        }
        System.out.printf("-------------------------------------------------------------------------------------%n");
    }

    public void printOutStates() {
        neighborStates
                .keySet()
                .forEach(e -> System.out.println(Arrays.toString(e) + " " + neighborStates.get(e)));
    }

    public Integer numOfEating(Integer[] val) {
        int count = 0;
        for (int x = 0; x < val.length - 1; x++) {
            for (int y = x + 1; y < val.length; y++) {
                if ((Math.abs(val[y] - val[x])) == (Math.abs(y - x)) || val[x] - val[y] == 0) {
//                    System.out.println("BOARD[y]: " +val[y] );
//                    System.out.println("BOARD[x]: " +val[x] );
//                    System.out.println("X: "+x);
//                    System.out.println("Y: "+y);
                    count++;
                }
            }
        }
        return count;
    }
}