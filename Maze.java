/**
 * Maze object representing a 2d maze of walls and passages
 * Contains logic for maze importing/generation and solution
 */
public class Maze {
    //Instance Variables
    private int height;
    private int width;
    /* 2d array containing info on different states of cells of the maze. 
        '#' indicates a wall
        ' ' indicates traversable
        'S' indicates starting position
        'E' indicates ending position
        '.' indicates a single marked path
        ':' indicates a double marked path

        EXAMPLE MAZE STATES ARRAY:
        S#E          
         #####  # ###
         #####       
               ###   
        ### #  ###  #
          # #  ###   
          #          #
        
     */ 

    private char[][] mazeStates;

    //Constructor
    /**
     * Constructor for Maze object. Initializes mazeStates array to be filled with walls by default
     * @param height height of the maze
     * @param width width of the maze
     */
    public Maze(int height, int width){
        this.height = height;
        this.width = width;
        this.mazeStates = new char[height][width];
        //Fill maze with walls by default
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                setWall(i, j);
            }
        }
    }

    //Getters and Setters
    /**
     * Helper method to set the state of a cell in the maze. Returns true if successful, false if out of bounds.
     * @param x
     * @param y
     * @param state
     * @return T if successful, F if out of bounds
     */
    private boolean setState(int x, int y, char state){
        if (x >= 0 && x < height && y >= 0 && y < width) {
            mazeStates[x][y] = state;
            return true;
        }
        else {
            System.out.println("Attempted to set cell out of bounds: (" + x + ", " + y + ") with state '" + state + "'.");
        }
        return false; // Return false if out of bounds
    }
    /**
     * Helper methods to set specific states of cells in the maze. Each method calls setState with the appropriate state character.
     * @param x
     * @param y
     * @return
     */
    private boolean setStart(int x, int y){
        return setState(x, y, 'S');
    }
    /**
     * Helper methods to set specific states of cells in the maze. Each method calls setCell with the appropriate state character.
     * @param x
     * @param y
     * @return
     */
    private boolean setEnd(int x, int y){
        return setState(x, y, 'E');
    }
    /**
     * Helper methods to set specific states of cells in the maze. Each method calls setCell with the appropriate state character.
     * @param x
     * @param y
     * @return
     */
    private boolean setEmpty(int x, int y){
        return setState(x, y, ' ');
    }
    /**
     * Helper methods to set specific states of cells in the maze. Each method calls setCell with the appropriate state character.
     * @param x
     * @param y
     * @return
     */
    private boolean setWall(int x, int y){
        return setState(x, y, '#');
    }
    /**
     * Helper method to get the state of a cell in the maze. Returns the state character if in bounds, '#' if out of bounds.
     * @param x
     * @param y
     * @return
     */
    private char getState(int x, int y){
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return mazeStates[x][y];
        }
        return '#'; // Return wall if out of bounds
    }
    /**
     * Helper method to get the starting position of the maze.
     * @return An array containing the coordinates of the starting position, or null if not found.
     */
    private int[] getStart() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getState(i, j) == 'S') {
                    return new int[]{i, j};
                }
            }
        }
        return null; // Return null if no start point is found
    }
    /**
     * Helper method to get the ending position of the maze.
     * @return an array containing the coordinates of the ending position, or null if not found.
     */
    private int[] getEnd() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getState(i, j) == 'E') {
                    return new int[]{i, j};
                }
            }
        }
        return null; // Return null if no end point is found
    }


    //Methods
    /**
     * Tostring prints mazeStates array along with a border of walls to indicate the bounds of the maze.
     *  Each cell is separated by a space for readability. 
     * 
     * EXAMPLE OUTPUT:
     * ###############
     * #S#E          #
     * # #####  # ####
     * # #####       #
     * #       ###   #
     * #### #  ###  ##
     * #  # #  ###   #
     * #  #          #
     * ###############
     */
    public String toString(){
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
     * Starts with a grid of walls, randomly selects a starting point, and carves out passages by randomly selecting walls to remove 
     * while ensuring that the maze remains solvable. Finally, it sets the start and end points in the maze.
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
        if (startX > 0) walls.add(new int[]{startX - 1, startY}); // Up
        if (startX < height - 1) walls.add(new int[]{startX + 1, startY}); // Down
        if (startY > 0) walls.add(new int[]{startX, startY - 1}); // Left
        if (startY < width - 1) walls.add(new int[]{startX, startY + 1}); // Right
        // Carve the maze using Prim's algorithm
        while (!walls.isEmpty()) {
            int[] wall = walls.remove(rand.nextInt(walls.size())); // Randomly select a wall
            int x = wall[0];
            int y = wall[1];
            // Check if the wall can be carved (i.e., it has exactly one adjacent empty cell)
            int emptyCount = 0;
            if (x > 0 && getState(x - 1, y) == ' ') emptyCount++; // Up
            if (x < height - 1 && getState(x + 1, y) == ' ') emptyCount++; // Down
            if (y > 0 && getState(x, y - 1) == ' ') emptyCount++; // Left
            if (y < width - 1 && getState(x, y + 1) == ' ') emptyCount++; // Right
            if (emptyCount == 1) { // Carve the wall
                setEmpty(x, y);
                // Add adjacent walls to the list
                if (x > 0 && getState(x - 1, y) == '#') walls.add(new int[]{x - 1, y}); // Up
                if (x < height - 1 && getState(x + 1, y) == '#') walls.add(new int[]{x + 1, y}); // Down
                if (y > 0 && getState(x, y - 1) == '#') walls.add(new int[]{x, y - 1}); // Left
                if (y < width - 1 && getState(x, y + 1) == '#') walls.add(new int[]{x, y + 1}); // Right
            }

        
        }
        //Start in top left corner and end in bottom right corner by default, or the closest empty cell to those corners if they are walls
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

    public void importMaze(String mazeString) {
        String[] lines = mazeString.split("\n");
        this.height = lines.length;
        this.width = lines[0].length();
        this.mazeStates = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char state = lines[i].charAt(j);
                setState(i, j, state);
            }
        }
    }
}
