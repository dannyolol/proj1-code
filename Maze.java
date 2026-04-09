/**
 * Maze object representing a 2d maze of walls and passages
 * Contains logic for maze importing/generation and solution
 */
public class Maze {
    // Instance Variables
    private int height;
    private int width;
    /*
     * 2d array containing info on different states of cells of the maze.
     * '#' indicates a wall
     * ' ' indicates traversable
     * 'S' indicates starting position
     * 'E' indicates ending position
     * '.' indicates a single marked path
     * ':' indicates a double marked path
     * 
     * EXAMPLE MAZE STATES ARRAY:
     * S#E
     * ##### # ###
     * #####
     * ###
     * ### # ### #
     * # # ###
     * # #
     * 
     */

    private char[][] mazeStates;

    // Constructor
    /**
     * Constructor for Maze object. Initializes mazeStates array to be filled with
     * walls by default
     * 
     * @param height height of the maze
     * @param width  width of the maze
     */
    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.mazeStates = new char[height][width];
        // Fill maze with walls by default
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setWall(i, j);
            }
        }
    }

    // Getters and Setters
    /**
     * Helper method to set the state of a cell in the maze. Returns true if
     * successful, false if out of bounds.
     * 
     * @param x
     * @param y
     * @param state
     * @return T if successful, F if out of bounds
     */
    private boolean setState(int x, int y, char state) {
        if (x >= 0 && x < height && y >= 0 && y < width) {
            mazeStates[x][y] = state;
            return true;
        } else {
            System.out
                    .println("Attempted to set cell out of bounds: (" + x + ", " + y + ") with state '" + state + "'.");
        }
        return false; // Return false if out of bounds
    }

    /**
     * Helper methods to set a cell as the start point of the maze. Calls setState
     * with 'S' character.
     * 
     * @param x
     * @param y
     * @return true if successful, false if out of bounds
     */
    private boolean setStart(int x, int y) {
        return setState(x, y, 'S');
    }

    /**
     * Helper methods to set a cell as the end point of the maze. Calls setState
     * with 'E' character.
     * 
     * @param x
     * @param y
     * @return true if successful, false if out of bounds
     */
    private boolean setEnd(int x, int y) {
        return setState(x, y, 'E');
    }

    /**
     * Helper methods to set a cell as empty (traversable). Calls setState with ' '
     * character.
     * 
     * @param x
     * @param y
     * @return true if successful, false if out of bounds
     */
    private boolean setEmpty(int x, int y) {
        return setState(x, y, ' ');
    }

    /**
     * Helper methods to set a cell as a wall. Calls setState with '#' character.
     * 
     * @param x
     * @param y
     * @return true if successful, false if out of bounds
     */
    private boolean setWall(int x, int y) {
        return setState(x, y, '#');
    }

    /**
     * Helper method to get the state of a cell in the maze. Returns the state
     * character if in bounds, '#' if out of bounds.
     * 
     * @param x
     * @param y
     * @return state character if in bounds, '#' if out of bounds
     */
    private char getState(int x, int y) {
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return mazeStates[x][y];
        }
        return '#'; // Return wall if out of bounds
    }

    /**
     * Helper method to get the starting position of the maze.
     * 
     * @return An array containing the coordinates of the starting position, or null
     *         if not found.
     */
    private int[] getStart() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getState(i, j) == 'S') {
                    return new int[] { i, j };
                }
            }
        }
        return null; // Return null if no start point is found
    }

    /**
     * Helper method to get the ending position of the maze.
     * 
     * @return an array containing the coordinates of the ending position, or null
     *         if not found.
     */
    private int[] getEnd() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getState(i, j) == 'E') {
                    return new int[] { i, j };
                }
            }
        }
        return null; // Return null if no end point is found
    }

    // Methods
    /**
     * Tostring prints mazeStates array along with a border of walls to indicate the
     * bounds of the maze.
     * Each cell is separated by a space for readability.
     * 
     * EXAMPLE OUTPUT:
     * ###############
     * #S#E #
     * # ##### # ####
     * # ##### #
     * # ### #
     * #### # ### ##
     * # # # ### #
     * # # #
     * ###############
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int borderWidth = width + 2;
        // Top border
        for (int j = 0; j < borderWidth; j++) {
            sb.append("# ");
        }
        sb.append("\n");
        // Maze rows with side borders
        for (int i = 0; i < height; i++) {
            sb.append("# ");
            for (int j = 0; j < width; j++) {
                char state = getState(i, j);
                sb.append(state).append(" ");
            }
            sb.append("#\n");
        }
        // Bottom border
        for (int j = 0; j < borderWidth; j++) {
            sb.append("# ");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Method to randomize the maze using a randomized version of Prim's algorithm.
     * Starts with a grid of walls, randomly selects a starting point, and carves
     * out passages by randomly selecting walls to remove
     * while ensuring that the maze remains solvable. Finally, it sets the start and
     * end points in the maze.
     */
    public void simpleRandomize() {
        java.util.Random rand = new java.util.Random();
        // Start with a grid of walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setWall(i, j);
            }
        }
        // Randomly choose a starting point and mark it as empty
        int startX = rand.nextInt(height);
        int startY = rand.nextInt(width);
        setEmpty(startX, startY);
        // List of walls to consider for carving
        java.util.List<int[]> walls = new java.util.ArrayList<>();
        // Add initial walls around the starting point
        if (startX > 0)
            walls.add(new int[] { startX - 1, startY }); // Up
        if (startX < height - 1)
            walls.add(new int[] { startX + 1, startY }); // Down
        if (startY > 0)
            walls.add(new int[] { startX, startY - 1 }); // Left
        if (startY < width - 1)
            walls.add(new int[] { startX, startY + 1 }); // Right
        // Carve the maze using Prim's algorithm
        while (!walls.isEmpty()) {
            int[] wall = walls.remove(rand.nextInt(walls.size())); // Randomly select a wall
            int x = wall[0];
            int y = wall[1];
            // Check if the wall can be carved (i.e., it has exactly one adjacent empty
            // cell)
            int emptyCount = 0;
            if (x > 0 && getState(x - 1, y) == ' ')
                emptyCount++; // Up
            if (x < height - 1 && getState(x + 1, y) == ' ')
                emptyCount++; // Down
            if (y > 0 && getState(x, y - 1) == ' ')
                emptyCount++; // Left
            if (y < width - 1 && getState(x, y + 1) == ' ')
                emptyCount++; // Right
            if (emptyCount == 1) { // Carve the wall
                setEmpty(x, y);
                // Add adjacent walls to the list
                if (x > 0 && getState(x - 1, y) == '#')
                    walls.add(new int[] { x - 1, y }); // Up
                if (x < height - 1 && getState(x + 1, y) == '#')
                    walls.add(new int[] { x + 1, y }); // Down
                if (y > 0 && getState(x, y - 1) == '#')
                    walls.add(new int[] { x, y - 1 }); // Left
                if (y < width - 1 && getState(x, y + 1) == '#')
                    walls.add(new int[] { x, y + 1 }); // Right
            }

        }
        // Start in top left corner and end in bottom right corner by default, or the
        // closest empty cell to those corners if they are walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getState(i, j) == ' ') {
                    setStart(i, j);
                    break;
                }
            }
            if (getStart() != null) {
                break;
            }
        }
        for (int i = height - 1; i >= 0; i--) {
            for (int j = width - 1; j >= 0; j--) {
                if (getState(i, j) == ' ') {
                    setEnd(i, j);
                    break;
                }
            }
            if (getEnd() != null) {
                break;
            }
        }
    }

    /**
     * Method to import a maze from a text file.
     * The text file should contain the maze in the same format as the toString
     * output (borderless), with walls represented by '#', empty spaces by ' ', the
     * start point by 'S', and the end point by 'E'.
     * The method reads the file line by line, updates the maze dimensions based on
     * the imported maze, and fills the mazeStates array accordingly.
     * 
     * @param file the path to the text file containing the maze
     */
    public void importMaze(String file) {
        try {
            java.util.Scanner scanner = new java.util.Scanner(new java.io.File(file));
            java.util.List<String> lines = new java.util.ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            // Update maze dimensions based on imported maze
            this.height = lines.size();
            this.width = lines.get(0).length();
            this.mazeStates = new char[height][width];
            // Fill mazeStates array based on imported maze
            for (int i = 0; i < height; i++) {
                String line = lines.get(i);
                for (int j = 0; j < width; j++) {
                    char state = line.charAt(j);
                    setState(i, j, state);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + file);
        }
    }

    /**
     * Increment the mark on a cell in the maze. If the cell is empty, it becomes
     * marked with a single dot ('.'). If it's already marked with a single dot, it
     * becomes marked with a colon (':').
     * If it's already marked with a colon, it becomes marked with a dash ('-'). If
     * the cell is not empty or already marked once, the method prints an error
     * message and returns false.
     * 
     * @param x
     * @param y
     * @return true if the mark was successfully incremented, false if the cell is
     *         not empty or already marked once, or if out of bounds
     */
    private boolean incMark(int x, int y) {
        if (mazeStates[x][y] == ' ') {
            return setState(x, y, '.');
        } else if (mazeStates[x][y] == '.') {
            return setState(x, y, ':');
        } else if (mazeStates[x][y] == ':') {
            return setState(x, y, '-'); // Should never actually happen
        }
        System.out.println("Attempted to increment mark on cell (" + x + ", " + y
                + ") which is not empty or already marked once.");
        return false;
    }

    /**
     * Helper method to move the cursor in a given direction. The direction is
     * represented as an integer (0 for up, 1 for right, 2 for down, 3 for left).
     * 
     * @param cursor
     * @param dir    direction to move the cursor (0 for up, 1 for right, 2 for
     *               down, 3 for left)
     * @return an array containing the new coordinates of the cursor after moving in
     *         the given direction, or the original cursor if the move is invalid
     *         (e.g., out of bounds or into a wall)
     */
    private int[] moveCursor(int[] cursor, int dir) {
        if (cursor[0] == -1 || cursor[1] == -1) {
            System.out.println("Cursor is not set to a valid position. Please set the start point first.");
            return cursor; // Return the original cursor if it's not valid
        }
        int newX = cursor[0];
        int newY = cursor[1];
        switch (dir) {
            case 0:
                newX--;
                break; // Move up
            case 1:
                newY++;
                break; // Move right

            case 2:
                newX++;
                break; // Move down
            case 3:
                newY--;
                break; // Move left
            default:
                System.out
                        .println("Invalid direction: " + dir + ". Use 0 for up, 1 for right, 2 for down, 3 for left.");
                return cursor;
        }
        if (getState(newX, newY) != '#') { // Check if the new position is not a wall
            cursor[0] = newX;
            cursor[1] = newY;
            return new int[] { cursor[0], cursor[1] };
        } else {
            System.out.println("Cannot move to (" + newX + ", " + newY + ") - it's a wall.");
        }
        return cursor; // Return the original cursor if the move is invalid, otherwise update to move
                       // to the new position
    }

    /**
     * Helper method to get the state of the cell in a given direction from the
     * current cursor position. The direction is represented as an integer (0 for
     * up, 1 for right, 2 for down, 3 for left).
     * 
     * @param cursor
     * @param dir    direction to check the state of the cell (0 for up, 1 for
     *               right, 2 for down, 3 for left)
     * @return the state character of the cell in the given direction from the
     *         current cursor position, or '#' if the new position is out of bounds
     */
    private char getStateInDirection(int[] cursor, int dir) {
        int newX = cursor[0];
        int newY = cursor[1];
        switch (dir) {
            case 0:
                newX--;
                break; // Up
            case 1:
                newY++;
                break; // Right
            case 2:
                newX++;
                break; // Down
            case 3:
                newY--;
                break; // Left
            default:
                System.out
                        .println("Invalid direction: " + dir + ". Use 0 for up, 1 for right, 2 for down, 3 for left.");
                return '#';
        }
        return getState(newX, newY);
    }

    /**
     * Method to solve the maze using Tremaux's algorithm. The method starts at the
     * starting position of the maze and explores the maze by prioritizing nearby
     * cells based on their state (empty, marked once, marked twice).
     * If it reaches the end point, it prints a success message and returns true. If
     * it encounters a cell that has been marked twice, it backtracks to the
     * previous cell and continues exploring.
     * If it exhausts all possible paths without reaching the end point, it prints a
     * failure message and returns false.
     * 
     * @return true if the maze is successfully solved, false if the maze is
     *         unsolvable or if there are no valid moves available at any point
     *         during the solution process.
     */
    public int solveMaze(boolean printSteps) {
        // Cursor object is used to track where we are in the maze while solving
        int[] cursor = getStart();
        if (cursor == null) {
            System.out.println("No starting point found in the maze.");
            return -1; // Return -1 to indicate failure due to no starting point
        }
        int solutionSteps = 0;
        // Stack needed as this solution is depth first and need to traverse backwards
        // when stuck
        java.util.Deque<Integer> backtrackStack = new java.util.ArrayDeque<>();
        while (getState(cursor[0], cursor[1]) != 'E') {
            // prioritize nearby cells by given state
            int bestDirection = -1;
            char bestStateValue = '#'; // almost functions as a priority as there are certain cells we want to move
                                       // into
            for (int dir = 0; dir < 4; dir++) {
                char state = getStateInDirection(cursor, dir);
                if (state == 'E') { // Always go to the end if you see it
                    bestDirection = dir;
                    bestStateValue = state;
                    break;
                } else if (state == ' ') { // at a junction prioritize unmarked paths
                    bestStateValue = state;
                    bestDirection = dir;
                } else if (state == '.' && bestStateValue != ' ') { // then go to paths with one mark as they have not
                                                                    // been backtracked over
                    bestStateValue = state;
                    bestDirection = dir;
                } else if (state == 'S' && bestStateValue != ' ') { // Go back over the start to not get stuck
                    bestStateValue = state;
                    bestDirection = dir;
                } else if (state == ':' && bestStateValue != ' ' && bestStateValue != '.') { // only go to double marks
                                                                                             // if there is no other
                                                                                             // option, in which we want
                                                                                             // to backtrack
                    bestStateValue = state;
                    bestDirection = dir;
                }
            }
            if (bestDirection == -1) {
                return -1; // Return -1 to indicate failure due to no valid moves
            } else if (bestStateValue == 'E') { // go to the end if you see it
                cursor = moveCursor(cursor, bestDirection);
                solutionSteps++;
                backtrackStack.push(bestDirection);
                break;
            } else if (bestStateValue == ':') { // backtrack if we are at a cell we've been to twice before, as per
                                                // Tremaux's algorithm
                if (backtrackStack.isEmpty()) {
                    return -1; // Return -1 to indicate failure due to empty backtrack stack
                }
                int backtrackDirection = (backtrackStack.pop() + 2) % 4;
                cursor = moveCursor(cursor, backtrackDirection);
                solutionSteps++;
                continue;
            } else if (bestStateValue == '.') { // explanitory but save the direction you're going to move to and then
                                                // move, make sure to mark the new spot
                cursor = moveCursor(cursor, bestDirection);
                solutionSteps++;
                backtrackStack.push(bestDirection);
                incMark(cursor[0], cursor[1]);
            } else if (bestStateValue == ' ') { // as per above
                cursor = moveCursor(cursor, bestDirection);
                solutionSteps++;
                backtrackStack.push(bestDirection);
                incMark(cursor[0], cursor[1]);
            } else if (bestStateValue == 'S') {
                cursor = moveCursor(cursor, bestDirection);
                solutionSteps++;
                backtrackStack.push(bestDirection);
            }
            if (printSteps) {
                System.out.println("Current maze state:\n" + this);
            }
        }
        return solutionSteps;
    }
}
