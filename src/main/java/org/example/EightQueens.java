package org.example;

import java.util.*;

public class EightQueens {
    private static Integer[] board = new Integer[8];
    private static Map<Integer[], Integer> neighborStates = new LinkedHashMap<>();
    private final Random random = new Random();
    private Map<Integer[],Integer> steps = new LinkedHashMap<>();

    public void createAndSolve(){
        randomPlacement();
        printBoard();
        fillNeighbors();
        printOutStates();
        changeState();
    }
    public void solve(){
        randomPlacement();
        while(numOfEating(board) != 0){
            fillNeighbors();
            if(numOfEating(board)== 1 && neighborStates.values().con)
        }
    }

    public void randomPlacement() {
        for (int x = 0; x < board.length; x++) {
            board[x] = random.nextInt(0, 8);
        }
    }

    public void fillNeighbors() {
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