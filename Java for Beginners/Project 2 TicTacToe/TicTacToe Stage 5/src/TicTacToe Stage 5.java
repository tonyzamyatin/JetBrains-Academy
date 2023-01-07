import java.util.Arrays;
import java.util.Scanner;  // Import the Scanner class

public class Main {
    static String checkGameState (char[] gameState) {
        int countX = 0;
        int countO= 0;
        int countEmpty = 0;
        int[] tir = {0, 0};
        // tir = three in a row.
        // tir[0] are the times X has "three in a row" and tir[1] the times O has tir.

        // Check whether there are a lot more X's than O's or vice versa.
        for (char cell: gameState) {
            if (cell == 'X') {
                countX++;
            } else if (cell == 'O') {
                countO++;
            } else if (cell == ' ') {
                countEmpty++;
            }
        }
        if (Math.abs(countX-countO) > 1) {
            return "Impossible";
        }

        // Create two-dimensional array.
        char[][] grid = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j= 0; j < 3; j++) {
                grid[i][j] = gameState[i * 3 + j];
            }
        }

        // Check for "three in a row":
        // Check horizontal.
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                if (grid[i][0] == 'X') {
                    tir[0]++;
                } else if (grid[i][0] == 'O') {
                    tir[1]++;
                }
            }
        }
        // Check vertical.
        for (int i = 0; i < 3; i++) {
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {
                if (grid[0][i] == 'X') {
                    tir[0]++;
                } else if (grid[0][i] == 'O') {
                    tir[1]++;
                }
            }
        }

        // Check for diagonals:
        //First diagonal
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            if (grid[1][1] == 'X') {
                tir[0]++;
            } else if (grid[1][1] == 'O') {
                tir[1]++;
            }
        }
        //Second diagonal
        if (grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2]) {
            if (grid[1][1] == 'X') {
                tir[0]++;
            } else if (grid[1][1] == 'O') {
                tir[1]++;
            }
        }

        // Evaluate who has "tir" to identify winner or impossible state. Generally there is only one "tir".
        if (tir[0] + tir[1] > 1){
            /* Special cas: If at the end of the game one payer has two nearly full rows,
            he can get two "tir"s with one move. */
            if (tir[0] == 2) {
                return "X wins";
            } else if (tir[1] == 2) {
                return "O wins";
            } else {
                return "Impossible";
            }
        } else if (tir[0] == 1) {
            return "X wins";
        } else if (tir[1] == 1) {
            return "O wins";
        }

        // Check whether grid is full. Standard return value in this case is "Draw".
        if (countEmpty == 0) {
            return "Draw";
        }

        // Standard return value.
        return "Game not finished";
    }

    static int[] getMove (char[] gameState) {
        Scanner scanner = new Scanner(System.in);
        int[] move = new int[2];
        boolean legal = true;
        int[] checkedMove = new int[3];
        /* A long try and catch to check whether the input is two numbers.
           If not it jumps to the end and outputs the error message, else it runs the code as usual.
        */
        try{
            move[0] = scanner.nextInt();
            move[1] = scanner.nextInt();

            // Turn into coordinate format matching the one of the grid array.
            move[0]--;
            move[1]--;

            // Check whether coordinates are within the grid.
            if ((move[0] < 0 || move[1] < 0) || (move[0] > 2 || move[1] > 2)) {
                System.out.println("Coordinates should be from 1 to 3!");
                legal = false;
            } else {
                // Create two-dimensional array.
                char[][] grid = new char[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j= 0; j < 3; j++) {
                        grid[i][j] = gameState[i * 3 + j];
                    }
                }

                if(grid[move[0]][move[1]] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                    legal = false;
                }
            }

        } catch (Exception e) {
            System.out.println("You should enter numbers!");
            legal = false;
        }

        checkedMove[0] = legal ? 1 : 0;
        checkedMove[1] = move[0];
        checkedMove[2] = move[1];

        return checkedMove;
    }

    static void printGrid (char[] gameState) {
        System.out.println("---------");
        System.out.println("| " + gameState[0] + " " + gameState[1] + " " + gameState[2] + " |");
        System.out.println("| " + gameState[3] + " " + gameState[4] + " " + gameState[5] + " |");
        System.out.println("| " + gameState[6] + " " + gameState[7] + " " + gameState[8] + " |");
        System.out.println("---------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create array to representing thr current stage of the game and print out empty grid.
        char[] stage = new char[9];
        Arrays.fill(stage, ' ');
        printGrid(stage);

        // Create variables for getting and checking the move of the user.
        int[] move = new int[3];
        boolean legal = true;
        // Create a variable for tracking the turns.
        int turnCount = 1;

        // Keep the game running as long as the game is not finished.
        do {
            // Get move from user, check it and keep asking user for a move until it's a legal move.
            do {
                move = getMove(stage);
                legal = move[0] == 1;
            } while (legal == false);

            // Save move to current stage of the game.
            stage[move[1] * 3 + move[2]] = (turnCount % 2 != 0) ? 'X' : 'O';
            turnCount++;
            printGrid(stage);
        } while (checkGameState(stage).equals("Game not finished"));

        // Print out the result of the game once it's finished.
        System.out.println(checkGameState(stage));

    }
}