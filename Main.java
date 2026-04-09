/**
 * Main class used for testing and running Maze object code
 */
public class Main {

    // Main method
    public static void main(String[] args) {

        // Multitest many small mazes to check for consistency
        if (multiTest(5000, 10)) {
            System.out.println("Consistency test passed for maze size " + 10);
        } else {
            System.out.println("Consistency test failed for maze size " + 10);
            return;
        }

        // Multitest some medium mazes to check for consistency
        if (multiTest(10, 50)) {
            System.out.println("Consistency test passed for maze size " + 50);
        } else {
            System.out.println("Consistency test failed for maze size " + 50);
            return;
        }

        // Single test a large maze to make me look cool
        Maze maze = new Maze(300, 100);
        maze.simpleRandomize();
        int steps = maze.solveMaze(false);
        if (steps > 0) {
            System.out.println("Maze solved in " + steps + " steps:\n" + maze);
        } else {
            System.out.println("Maze failed to solve:\n" + maze);
            return;
        }

        // Edge case tests
        // Test maze with no start point
        maze.importMaze("noStartMaze.txt");
        steps = maze.solveMaze(false);
        if (steps == -1) {
            System.out.println("Correctly identified maze with no start point as unsolvable.");
        } else {
            System.out.println("Failed to identify maze with no start point as unsolvable.");
            return;
        }

        // Test maze with no end point
        maze.importMaze("noEndMaze.txt");
        steps = maze.solveMaze(false);
        if (steps == -1) {
            System.out.println("Correctly identified maze with no end point as unsolvable.");
        } else {
            System.out.println("Failed to identify maze with no end point as unsolvable.");
            return;
        }

        // Test maze that is unsolvable
        maze.importMaze("unsolvableMaze.txt");
        steps = maze.solveMaze(false);
        if (steps == -1) {
            System.out.println("Correctly identified unsolvable maze.");
        } else {
            System.out.println("Failed to identify unsolvable maze.");
            return;
        }

    }

    private static boolean multiTest(int numAttempts, int mazeSize) {
        Maze maze = new Maze(mazeSize, mazeSize);
        maze.simpleRandomize();

        for (int i = 0; i < numAttempts; i++) {
            int steps = maze.solveMaze(false);
            if (steps > 0) {
            } else {
                System.out.println("Maze failed to solve:\n" + maze);
                return false; // If any attempt fails, return false
            }
            maze.simpleRandomize(); // Randomize the maze again for the next attempt
        }
        return true; // All attempts succeeded
    }
}