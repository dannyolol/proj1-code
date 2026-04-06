/**
 * Main class used for testing and running Maze object code
 */
public class Main{

    //Main method
    public static void main(String[] args){
        Maze maze = new Maze(6, 12);
        System.out.println("INITIAL MAZE:\n" + maze);
        maze.simpleRandomize();
        System.out.println("RANDOMIZED MAZE:\n" + maze);
        maze.importMaze("maze.txt");
        System.out.println("IMPORTED MAZE:\n" + maze);
    }
}