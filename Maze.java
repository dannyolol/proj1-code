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
                this.mazeStates[i][j] = '#';
            }
        }
    }

    //Getters and Setters
    private boolean setCell(int x, int y, char state){
        if (x >= 0 && x < height && y >= 0 && y < width) {
            mazeStates[x][y] = state;
            return true;
        }
        else {
            System.out.println("Attempted to set cell out of bounds: (" + x + ", " + y + ") with state '" + state + "'.");
        }
        return false; // Return false if out of bounds
    }

    private boolean setStart(int x, int y){
        return setCell(x, y, 'S');
    }

    private boolean setEnd(int x, int y){
        return setCell(x, y, 'E');
    }

    private boolean setEmpty(int x, int y){
        return setCell(x, y, ' ');
    }

    private boolean setWall(int x, int y){
        return setCell(x, y, '#');
    }

    private char getState(int x, int y){
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return mazeStates[x][y];
        }
        return '#'; // Return wall if out of bounds
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
                char state = mazeStates[i][j];
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
}
