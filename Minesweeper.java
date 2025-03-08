import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private static final int grid_size = 16;
    private static final int num_mines = 50;

    private static final String No_Mine = " ";
    private static final String Covered_Spot = "?";

    private static String[][] msgrid;
    private String[][] mygrid;
    private Random random;

    public Minesweeper() {
        msgrid = new String[grid_size][grid_size];
        mygrid = new String[grid_size][grid_size];
        random = new Random();
        startGrid();
        placeMines();
        initializePlayerGrid();
        starting_pt();
    }

    public void startGrid() {
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                msgrid[i][j] = No_Mine;
            }
        }
    }

    public void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < num_mines) {
            int r = random.nextInt(grid_size);
            int c = random.nextInt(grid_size);

            if (msgrid[r][c].equals(No_Mine)) {
                msgrid[r][c] = "X";
                minesPlaced++;
            }
        }
    }

    public void initializePlayerGrid() {
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                mygrid[i][j] = Covered_Spot;
            }
        }
    }

    public void print_msgrid() {
        // Print top border with column numbers
        System.out.print("   ");
        for (int j = 0; j < grid_size; j++) {
            System.out.printf("%3d ", j);
        }
        System.out.println();
        System.out.print("   +");
        for (int j = 0; j < grid_size; j++) {
            System.out.print("---+");
        }
        System.out.println();

        // Print grid with side borders and row numbers
        for (int i = 0; i < grid_size; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < grid_size; j++) {
                System.out.print(" " + msgrid[i][j] + " |");
            }
            System.out.printf(" %2d\n", i);
            if (i < grid_size - 1) {
                System.out.print("   +");
                for (int j = 0; j < grid_size; j++) {
                    System.out.print("---+");
                }
                System.out.println();
            }
        }

        // Print bottom border
        System.out.print("   +");
        for (int j = 0; j < grid_size; j++) {
            System.out.print("---+");
        }
        System.out.println();
    }

    public void print_mygrid() {
        // Print top border with column numbers
        System.out.print("   ");
        for (int j = 0; j < grid_size; j++) {
            System.out.printf("%3d ", j);
        }
        System.out.println();
        System.out.print("   +");
        for (int j = 0; j < grid_size; j++) {
            System.out.print("---+");
        }
        System.out.println();

        // Print grid with side borders and row numbers
        for (int i = 0; i < grid_size; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < grid_size; j++) {
                System.out.print(" " + mygrid[i][j] + " |");
            }
            System.out.printf(" %2d\n", i);
            System.out.print("   +");
            for (int j = 0; j < grid_size; j++) {
                System.out.print("---+");
            }
            System.out.println();
        }

        // Print bottom border with column numbers
        System.out.print("   ");
        for (int j = 0; j < grid_size; j++) {
            System.out.printf("%3d ", j);
        }
        System.out.println();
    }

    public void starting_pt() {
        int start_spot = 0;
        int max_attempts = 1000; 
        Random random_s = new Random();
        
        while (start_spot < 5 && max_attempts > 0) {
            int r = random_s.nextInt(grid_size);
            int c = random_s.nextInt(grid_size);
            
            if (mygrid[r][c].equals(Covered_Spot) && !msgrid[r][c].equals("X")) {
                int surrounding_mines = get_number(r, c);
                mygrid[r][c] = String.valueOf(surrounding_mines);
                start_spot++;
            }
            max_attempts--; 
        }
        
        if (start_spot < 5) {
            System.out.println("Warning: Found only " + start_spot + " starting spots.");
        }
    }

    public static int get_number(int r, int c) { 
        int m = 0;
    
        // Case 1: if selected spot is in a corner
        if ((r == 0 || r == grid_size - 1) && (c == 0 || c == grid_size - 1)) {
            // Top left corner
            if (r == 0 && c == 0) {
                if (msgrid[1][0].equals("X")) m++;
                if (msgrid[1][1].equals("X")) m++;
                if (msgrid[0][1].equals("X")) m++;
            }
            // Top right corner
            else if (r == 0 && c == grid_size - 1) {
                if (msgrid[0][c - 1].equals("X")) m++;
                if (msgrid[r + 1][c - 1].equals("X")) m++;
                if (msgrid[r + 1][c].equals("X")) m++;
            }
            // Bottom right corner
            else if (r == grid_size - 1 && c == grid_size - 1) {
                if (msgrid[r - 1][c].equals("X")) m++;
                if (msgrid[r - 1][c - 1].equals("X")) m++;
                if (msgrid[r][c - 1].equals("X")) m++;
            }
            // Bottom left corner
            else if (r == grid_size - 1 && c == 0) {
                if (msgrid[r - 1][c].equals("X")) m++;
                if (msgrid[r - 1][c + 1].equals("X")) m++;
                if (msgrid[r][c + 1].equals("X")) m++;
            }
        }
        // Case 2: if selected spot is on column border but not in a corner
        else if (c == 0 || c == grid_size - 1) {
            if (c == 0) {
                if (msgrid[r - 1][c].equals("X")) m++;
                if (msgrid[r - 1][c + 1].equals("X")) m++;
                if (msgrid[r][c + 1].equals("X")) m++;
                if (msgrid[r + 1][c + 1].equals("X")) m++;
                if (msgrid[r + 1][c].equals("X")) m++;
            } else if (c == grid_size - 1) {
                if (msgrid[r - 1][c].equals("X")) m++;
                if (msgrid[r - 1][c - 1].equals("X")) m++;
                if (msgrid[r][c - 1].equals("X")) m++;
                if (msgrid[r + 1][c - 1].equals("X")) m++;
                if (msgrid[r + 1][c].equals("X")) m++;
            }
        }
        // Case 3: if selected spot is on row border but not in a corner
        else if (r == 0 || r == grid_size - 1) {
            if (r == 0) { // Top row
                if (msgrid[r][c - 1].equals("X")) m++;       // Left
                if (msgrid[r][c + 1].equals("X")) m++;       // Right
                if (msgrid[r + 1][c - 1].equals("X")) m++;   // Bottom-left
                if (msgrid[r + 1][c].equals("X")) m++;       // Bottom
                if (msgrid[r + 1][c + 1].equals("X")) m++;   // Bottom-right
            } 
            else { // Bottom row (r == grid_size - 1)
                if (msgrid[r][c - 1].equals("X")) m++;       // Left
                if (msgrid[r][c + 1].equals("X")) m++;       // Right
                if (msgrid[r - 1][c - 1].equals("X")) m++;   // Top-left
                if (msgrid[r - 1][c].equals("X")) m++;       // Top
                if (msgrid[r - 1][c + 1].equals("X")) m++;   // Top-right
            }
        }
        // Case 4: if the selected spot is not on the edge
        else {
            return getnumber(r, c);
        }
    
        return m;
    }
    
    public static int getnumber(int r, int c) {
        int m = 0;
    
        if (msgrid[r - 1][c - 1].equals("X")) m++;  // Top-left
        if (msgrid[r - 1][c].equals("X")) m++;      // Top
        if (msgrid[r - 1][c + 1].equals("X")) m++;  // Top-right
        if (msgrid[r][c - 1].equals("X")) m++;      // Left
        if (msgrid[r][c + 1].equals("X")) m++;      // Right
        if (msgrid[r + 1][c - 1].equals("X")) m++;  // Bottom-left
        if (msgrid[r + 1][c].equals("X")) m++;      // Bottom
        if (msgrid[r + 1][c + 1].equals("X")) m++;  // Bottom-right
    
        return m;
    }

    public boolean checkWin() {
        int uncovered_cells = 0;
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (!mygrid[i][j].equals(Covered_Spot)) {
                    uncovered_cells++;
                }
            }
        }
        return uncovered_cells == (grid_size * grid_size - num_mines);
    }

    public boolean checkLoss(int r, int c) {
        if (msgrid[r][c].equals("X")) {
            return true;
        } else {
            return false;
        }
    }

    public void myGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("The grid is " + grid_size + " by " + grid_size + " and has " + num_mines + " mines.");
        System.out.println("Enter a row and a column to uncover a cell (?), but don't hit a mine(X)!");

        boolean playAgain = true; // Initialize playAgain here

        while (playAgain) {
            boolean gameWon = false;
            boolean gameLost = false;

            startGrid();
            placeMines();
            initializePlayerGrid();
            starting_pt();

            while (!gameWon && !gameLost) {
                print_mygrid();

                int row = -1;
                int col = -1;

                while (true) {
                    try {
                        System.out.print("Enter your row: ");
                        String rowInput = scanner.nextLine().trim();
                        row = Integer.parseInt(rowInput);
                        
                        System.out.print("Enter your column: ");
                        String colInput = scanner.nextLine().trim();
                        col = Integer.parseInt(colInput);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter integers for row and column.");
                    }
                }

                if (row >= 0 && row < grid_size && col >= 0 && col < grid_size) {
                    if (msgrid[row][col].equals("X")) {
                        System.out.println("Oh no! You hit a mine!");
                        gameLost = true;
                        print_msgrid();
                    } else {
                        int surrounding_mines = get_number(row, col);
                        mygrid[row][col] = String.valueOf(surrounding_mines);
                        if (checkWin()) {
                            gameWon = true;
                        }
                    }
                } else {
                    System.out.println("This is an invalid row or column.");
                }
            }

            if (gameLost) {
                System.out.println("Sorry, You lost");
                print_msgrid();
                System.out.println("Do you want to resume this game? (yes/no)");
                String resumeResponse = scanner.nextLine().trim().toLowerCase();

                if (resumeResponse.equals("yes")) {
                    gameLost = false;
                    System.out.println("Game resumed. Be careful!");
                   continue; 

                } else {
                    System.out.println("Do you want to start another game? (yes/no)");
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("yes")) {
                        playAgain = true;
                         startGrid();
                         placeMines();
                       initializePlayerGrid();
                      starting_pt();
                    } else {
                        playAgain = false;
                        System.out.println("Thanks for playing!");
                    }
                }


            } else if (gameWon) {
                print_mygrid();
                System.out.println("Congratulations! You won!");
            }

            System.out.println("Do you want to start another game? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                playAgain = true;
            } else {
                playAgain = false;
                System.out.println("Thanks for playing!");
            }
        }
        scanner.close();
    }

    public void change_board(int r, int c) {
        int find = get_number(r, c);
        mygrid[r][c] = String.valueOf(find);
    }

    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
        game.myGame();
    }
}