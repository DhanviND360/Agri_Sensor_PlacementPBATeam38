import java.util.Scanner;

class SensorPlacement {
    private int gridSize;
    private int[] sensorPositions; // stores column position for each row
    private int solutionsFound;
    
    public SensorPlacement(int n) {
        this.gridSize = n;
        this.sensorPositions = new int[n];
        this.solutionsFound = 0;
    }
    
    // Check if placing a sensor at (row, col) is safe
    private boolean isSafe(int row, int col) {
        // Check all previously placed sensors
        for (int i = 0; i < row; i++) {
            int placedCol = sensorPositions[i];
            
            // Check if sensor is in same column
            if (placedCol == col) {
                return false;
            }
            
            // Check if sensor is in same diagonal
            // Two sensors are on same diagonal if:
            // absolute difference of rows == absolute difference of columns
            if (Math.abs(i - row) == Math.abs(placedCol - col)) {
                return false;
            }
        }
        return true;
    }
    
    // Backtracking function to place sensors
    private boolean placeSensors(int row) {
        // Base case: all sensors placed successfully
        if (row == gridSize) {
            solutionsFound++;
            printSolution();
            return true; // Return true to find first solution
        }
        
        // Try placing sensor in each column of current row
        for (int col = 0; col < gridSize; col++) {
            if (isSafe(row, col)) {
                // Place sensor
                sensorPositions[row] = col;
                
                // Recursively place sensors in next rows
                if (placeSensors(row + 1)) {
                    return true; // Stop after finding first solution
                }
                
                // Backtrack: remove sensor (implicit - will be overwritten)
            }
        }
        
        return false; // No valid placement found
    }
    
    // Print the grid with sensor positions
    private void printSolution() {
        System.out.println("\nSolution #" + solutionsFound + ":");
        System.out.println("Grid with Sensor Placement:");
        System.out.println();
        
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (sensorPositions[row] == col) {
                    System.out.print("S ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        
        System.out.println("\nSensor positions (Row, Column):");
        for (int row = 0; row < gridSize; row++) {
            System.out.println("Sensor " + (row + 1) + ": (" + row + ", " + sensorPositions[row] + ")");
        }
    }
    
    // Find all solutions
    private void findAllSolutions(int row) {
        // Base case: all sensors placed successfully
        if (row == gridSize) {
            solutionsFound++;
            printSolution();
            return;
        }
        
        // Try placing sensor in each column of current row
        for (int col = 0; col < gridSize; col++) {
            if (isSafe(row, col)) {
                // Place sensor
                sensorPositions[row] = col;
                
                // Recursively place sensors in next rows
                findAllSolutions(row + 1);
                
                // Backtrack happens automatically when loop continues
            }
        }
    }
    
    // Main method to start sensor placement
    public void solve(boolean findAll) {
        if (findAll) {
            findAllSolutions(0);
        } else {
            placeSensors(0);
        }
        
        if (solutionsFound == 0) {
            System.out.println("No solution exists for " + gridSize + "x" + gridSize + " grid!");
        } else {
            System.out.println("\n==================================");
            System.out.println("Total solutions found: " + solutionsFound);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Precision Agriculture Sensor Placement ===");
        System.out.println("Using Backtracking Algorithm");
        System.out.println();
        
        System.out.print("Enter grid size (N): ");
        int n = scanner.nextInt();
        
        if (n <= 0) {
            System.out.println("Grid size must be positive!");
            scanner.close();
            return;
        }
        
        if (n == 2 || n == 3) {
            System.out.println("\nNote: No solution exists for " + n + "x" + n + " grid.");
            System.out.println("Try N >= 4");
            scanner.close();
            return;
        }
        
        System.out.print("Find all solutions? (yes/no): ");
        String choice = scanner.next().toLowerCase();
        boolean findAll = choice.equals("yes") || choice.equals("y");
        
        System.out.println("\n==================================");
        System.out.println("Starting sensor placement...");
        System.out.println("Legend: 'S' = Sensor, '.' = Empty");
        System.out.println("==================================");
        
        SensorPlacement sp = new SensorPlacement(n);
        sp.solve(findAll);
        
        scanner.close();
    }
}