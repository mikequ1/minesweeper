/**
 * Minesweeper
 * @author Diego Brown
 * @version
 */

//imports
import java.util.Random;
//====================================================================================================================================================

//classes
public class Minesweeper {
    //field variables
    private static int width, height, mines;
    private static int spaceID = 0;
    public Space spaces[];
    private MinesweeperGUI main;
    //====================================================================================================================================================

    //constructors
    public Minesweeper (int width, int height, int mines) {
        resetSpaceID();
        this.width = width;
        this.height = height;
        this.mines = mines;
        spaces = new Space[width * height];
        createGrid();
        assignGrid();
        assignNeighbors();
        main = new MinesweeperGUI(spaces);
    }
    //====================================================================================================================================================

    //private methods
    private void createGrid() {
        System.out.println("==================================================");
        for (int y=1; y<height+1; y++) {
            for (int x=1; x<width+1; x++) {
                spaces[spaceID] = new Space(x, y, spaceID);
                spaceID++;
            }
        }
        System.out.println("==================================================");
    }

    private void assignGrid() {
        Random rand = new Random();        
        do {
            int index = rand.nextInt(width * height);
            if (spaces[index].isMine() == false) {
                spaces[index].makeMine();
                mines--;
            }
        } while (mines > 0);
        System.out.println("==================================================");
    }
    
    private int getNeighbors (Space space) {
        int adjacent = 0;
        int currentIndex = space.getSpaceID();        
        //North
        if (currentIndex - width >= 0 && spaces[currentIndex-width].isMine() == true) {
            adjacent++;
        }
        //South
        if (currentIndex + width <= width * height - 1 && spaces[currentIndex+width].isMine() == true) {
            adjacent++;
        }
        //West
        if (currentIndex - 1 >= 0 && space.getYPos() == spaces[currentIndex-1].getYPos() && 
            spaces[currentIndex-1].isMine() == true) {
            adjacent++;
        }
        //East
        if (currentIndex + 1 <= width * height - 1 && space.getYPos() == spaces[currentIndex+1].getYPos() &&
            spaces[currentIndex+1].isMine() == true) {
            adjacent++;
        }
        //North-West
        if (currentIndex - width - 1 >= 0 && space.getYPos() - 1 == spaces[currentIndex-width-1].getYPos() &&
            spaces[currentIndex-width-1].isMine() == true) {
            adjacent++;
        }
        //North-East
        if (currentIndex - width + 1 >= 0 && currentIndex - width + 1 <= width * height - 1 &&
            space.getYPos() - 1 == spaces[currentIndex-width+1].getYPos() && spaces[currentIndex-width+1].isMine() == true) {
            adjacent++;
        }
        //South-West
        if (currentIndex + width - 1 >= 0 && currentIndex + width - 1 <= width * height - 1 &&
            space.getYPos() + 1 == spaces[currentIndex+width-1].getYPos() && spaces[currentIndex+width-1].isMine() == true) {
            adjacent++;
        }
        //South-East
        if (currentIndex + width + 1 <= width * height - 1 && space.getYPos() + 1 == spaces[currentIndex+width+1].getYPos() &&
            spaces[currentIndex+width+1].isMine() == true) {
            adjacent++;
        }
        return adjacent;
    }
    
    private void assignNeighbors() {
        for (int i=0; i<width*height; i++) {
            spaces[i].setAdjacentMines(getNeighbors(spaces[i]));
        }
        System.out.println("==================================================");
    }
    //====================================================================================================================================================

    //mutator methods
    private void resetSpaceID() {
        spaceID = 0;
    }
    //====================================================================================================================================================
    
    //accessor methods
    public Space getSpaceBySpaceID (int spaceID) {
        return spaces[spaceID];
    }
    
    public static int getWidth() {
        return width;
    }
    
    public static int getHeight() {
        return height;
    }
    //====================================================================================================================================================

    //main method
    public static void main (String[] args) {

    }
    //====================================================================================================================================================
}
//====================================================================================================================================================