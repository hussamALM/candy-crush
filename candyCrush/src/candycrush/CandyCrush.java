package candycrush;

import java.util.Arrays;
import java.util.Scanner;

public class CandyCrush {

    /*
    you can modify the game settings in here 
    NOTE: the sizes should NOT be less than 3
          the sizes should be the same for both X and Y
     */
    final static private String[] COLORS = {"pink","red", "blue", "purple", "green", "yellow"};
    static Orb[][] board = generateBoard();
    final static int COLUMN_SIZE = 5;
    final static int ROW_SIZE = 5;

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            printBoard();
            String move;
            int row;
            int col;
            try {
                System.out.println("what is the orb you want to move ?\nrow:");
                row = scan.nextInt() - 1;
                System.out.println("column:");
                col = scan.nextInt() - 1;
                System.out.println("in what direction you want the orb to move?");
                scan.nextLine();
                move = scan.nextLine();
                move(row, col, move);
            } catch (Exception e) {
                System.out.println("just enter a normal inputs, can't you ?");
                // scanning the extra line so that it will not throw an err each time 
                scan.nextLine();
            }
        }
    }

    public static boolean isThereAMatch(Orb[][] tempBoard) {
        boolean match = false;
        //System.arraycopy(board, 0, tempBoard, 0, board.length);
        // checking for the row side if there is any matching
        for (int i = 0; i < tempBoard.length; i++) {
            int counter = 1;
            int startIndex = -1;
            int endIndex = -1;
            for (int j = 0; j < tempBoard[0].length - 1; j++) {
                if (tempBoard[i][j].getColor() == tempBoard[i][j + 1].getColor()) {
                    if (counter == 1) {
                        startIndex = j;
                    }
                    if (counter > 1) {
                        endIndex = j + 1;
                    }
                    counter++;

                } else {
                    if (!(counter >= 3)) {
                        // if there is no three adjacent elements reset
                        counter = 1;
                        startIndex = -1;
                        endIndex = -1;
                    }
                }
            }
            if (counter >= 3) {
                match = true;
                System.out.println("Found a " + counter + " adjacent elements with the same color in row " + i);
                System.out.println("they are in the interval (" + startIndex + "," + endIndex + ")");
                removeMatchRow(startIndex, endIndex, i, tempBoard);

            }
        }

        // checking for the column side if there is any matching
        for (int i = 0; i < tempBoard[0].length; i++) {
            int counter = 1;
            int startIndex = -1;
            int endIndex = -1;
            for (int j = 0; j < tempBoard.length - 1; j++) {
                if (tempBoard[j][i].getColor() == tempBoard[j + 1][i].getColor()) {
                    if (counter == 1) {
                        startIndex = j;
                    }
                    if (counter > 1) {
                        endIndex = j + 1;
                    }
                    counter++;
                } else {
                    if (!(counter >= 3)) {
                        counter = 1;
                        startIndex = -1;
                        endIndex = -1;
                    }
                }
            }
            if (counter >= 3) {
                match = true;
                System.out.println("Found a " + counter + " adjacent elements with the same color in column " + i);
                System.out.println("they are in the interval (" + startIndex + "," + endIndex + ")");
                removeMatchCol(startIndex, endIndex, i, tempBoard);

            }
        }

        return match;
    }

    public static void removeMatchRow(int startIndex, int endIndex, int row, Orb[][] tempBoard) {
        for (int i = startIndex; i <= endIndex; i++) {
            tempBoard[row][i].setColor();
        }
        generateNewOrbs(tempBoard);
    }

    public static void removeMatchCol(int startIndex, int endIndex, int col, Orb[][] tempBoard) {
        for (int i = startIndex; i <= endIndex; i++) {
            tempBoard[i][col].setColor();
        }
        generateNewOrbs(tempBoard);

    }

    public static void generateNewOrbs(Orb[][] tempBoard) {
         for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[0].length; j++) {
                System.out.print(" " + tempBoard[i][j] + " ");

            }
            System.out.println();}
        

        do {
            for (int i = 0; i < tempBoard[0].length - 1; i++) {
                for (int j = 0; j < tempBoard[0].length; j++) {
                    if (tempBoard[i][j].getColor() == "null") {
                        Orb temp = tempBoard[i][j];
                        tempBoard[i][j] = tempBoard[i + 1][j];
                        tempBoard[i + 1][j] = temp;

                    }
                }
            }
            int lastRow = tempBoard.length - 1;
            for (int i = 0; i < tempBoard[0].length; i++) {
                if (tempBoard[lastRow][i].getColor() == "null") {
                    Orb orb = new Orb(COLORS[(int) (Math.random() * COLORS.length)]);
                    tempBoard[lastRow][i] = orb;
                }
            }
        } while (isThereNull(tempBoard));
        isThereAMatch(tempBoard);
    }

    public static boolean isThereNull(Orb[][] tempBoard) {
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[0].length; j++) {
                if (tempBoard[i][j].getColor() == "null") {
                    return true;
                }
            }
        }
        return false;
    }

    //moving the orbs depending on the entered values
    //moving outside the board is NOT allowed
    public static void move(int row, int col, String direction) {
        String temp = direction.toLowerCase();
        Orb[][] tempBoard = copyBoard();
        switch (temp) {
            case "up":
                if (row - 1 < 0) {
                    System.out.println("don't move to this place it's called (the void)");
                    break;
                }
                Orb tempp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row - 1][col];
                tempBoard[row - 1][col] = tempp;
                if (isThereAMatch(tempBoard)) {
                    System.arraycopy(tempBoard, 0, board, 0, board.length);
                } else {
                    System.out.println("this move is not usefull, go do something else");
                }
                break;
            case "down":
                if (row + 1 >= board.length) {
                    System.out.println("don't move to this place it's called (the void)");
                    break;
                }
                tempp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row + 1][col];
                tempBoard[row + 1][col] = tempp;
                if (isThereAMatch(tempBoard)) {
                    System.arraycopy(tempBoard, 0, board, 0, board.length);
                } else {
                    System.out.println("this move is not usefull, go do something else");
                }
                break;
            case "right":
                if (col + 1 > board[0].length) {
                    System.out.println("don't move to this place it's called (the void)");
                    break;
                }
                tempp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row][col + 1];
                tempBoard[row][col + 1] = tempp;
                if (isThereAMatch(tempBoard)) {
                    System.arraycopy(tempBoard, 0, board, 0, board.length);
                } else {
                    System.out.println("this move is not usefull, go do something else");
                }
                break;
            case "left":
                if (col - 1 < 0) {
                    System.out.println("don't move to this place it's called (the void)");
                    break;
                }
                tempp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row][col - 1];
                tempBoard[row][col - 1] = tempp;
                if (isThereAMatch(tempBoard)) {
                    System.arraycopy(tempBoard, 0, board, 0, board.length);
                } else {
                    System.out.println("this move is not usefull, go do something else");
                }
                break;
            default:
                System.out.println("we live in a 2D world, we only can move to 4 directions");
                break;
        }
    }

    //generating the board dynamically and randomly
    public static Orb[][] generateBoard() {
        Orb[][] newBoard = new Orb[ROW_SIZE][COLUMN_SIZE];
        do {
            for (int i = 0; i < newBoard.length; i++) {
                for (int j = 0; j < newBoard[0].length; j++) {
                    //choosing a random color from the COLORS and then assign it to the cell
                    int randomNumber = (int) (Math.random() * COLORS.length);
                    Orb orb = new Orb(COLORS[randomNumber]);
                    newBoard[i][j] = orb;
                }
            }
        } while (!isThereAMatch(newBoard) && willItMove(newBoard));
        return newBoard;
    }

    // i mean it's for printing the borad "expected something else?"
    public static void printBoard() {
        System.out.println("----------------------------------------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");

    }

    public static Orb[][] copyBoard() {
        Orb[][] tempBoard = new Orb[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        return tempBoard;
    }

    public static Orb[][] copyHilpper(Orb[][] arr) {
        Orb[][] tempBoard = new Orb[arr.length][arr[0].length];
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[0].length; j++) {
                tempBoard[i][j] = arr[i][j];
            }
        }
        return tempBoard;
    }

    public static boolean willItMove(Orb[][] arr) {
        Orb[][] tempBoard = copyHilpper(arr);
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[0].length; j++) {
                if (willItMoveHelper(i, j, "up", tempBoard)
                        || willItMoveHelper(i, j, "down", tempBoard)
                        || willItMoveHelper(i, j, "right", tempBoard)
                        || willItMoveHelper(i, j, "left", tempBoard)) {
                    return true;
                }
            }
        }
        return false;
    }

    // the code below is for checking if the move wil be okay 
    public static boolean willItMoveHelper(int row, int col, String direction, Orb[][] tempBoard) {
        String cond = direction.toLowerCase();
        switch (cond) {
            case "up":
                if (row <= 0) {
                    return false;
                }
                System.out.println(row + "" + col);
                Orb temp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row - 1][col];
                tempBoard[row - 1][col] = temp;
                return isThereAMatachHelper(tempBoard);
            case "down":
                if (row >= tempBoard.length) {
                    return false;
                }
                temp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row + 1][col];
                tempBoard[row + 1][col] = temp;
                return isThereAMatachHelper(tempBoard);
            case "right":
                if (col >= tempBoard[0].length - 1) {
                    return false;
                }
                temp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row][col + 1];
                tempBoard[row][col + 1] = temp;
                return isThereAMatachHelper(tempBoard);
            case "left":
                if (col <= 0) {
                    return false;
                }
                temp = tempBoard[row][col];
                tempBoard[row][col] = tempBoard[row][col - 1];
                tempBoard[row][col - 1] = temp;
                return isThereAMatachHelper(tempBoard);

        }
        return false;

    }

    public static boolean isThereAMatachHelper(Orb[][] tempBoard) {
        for (int i = 0; i < tempBoard.length; i++) {
            int counter = 1;
            for (int j = 0; j < tempBoard[0].length - 1; j++) {
                if (tempBoard[i][j].getColor() == tempBoard[i][j + 1].getColor()) {
                    counter++;
                } else {
                    if (!(counter >= 3)) {
                        counter = 1;
                    }
                }
            }
            if (counter >= 3) {
                return true;

            }
        }
        for (int i = 0; i < tempBoard[0].length; i++) {
            int counter = 1;
            for (int j = 0; j < tempBoard.length - 1; j++) {
                if (tempBoard[j][i].getColor() == tempBoard[j + 1][i].getColor()) {
                    counter++;
                } else {
                    if (!(counter >= 3)) {
                        counter = 1;
                    }
                }
            }
            if (counter >= 3) {
                return true;
            }
        }
        return false;
    }

}
