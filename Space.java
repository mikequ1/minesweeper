/**
 * Space
 * @author Diego Brown
 * @version
 */

//imports
//====================================================================================================================================================

//classes
public class Space {
    //field variables
    boolean isMine, isFlagged;
    int xpos, ypos, spaceID, adjacentMines;
    //====================================================================================================================================================
    
    //constructors
    public Space (int xpos, int ypos, int spaceID) {
        isMine = false;
        isFlagged = false;
        adjacentMines = 0;
        this.xpos = xpos;
        this.ypos = ypos;
        this.spaceID = spaceID;
        System.out.println("Space " + spaceID + " created at (" + xpos + "," + ypos + ").");
    }
    //====================================================================================================================================================

    //accessor methods
    public boolean isMine() {
        if (isMine == true) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isFlagged() {
        if (this.isFlagged == true) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public int getXPos() {
        return xpos;
    }
    
    public int getYPos() {
        return ypos;
    }
    
    public int getSpaceID() {
        return spaceID;
    }
    
    public int getAdjacentBombs() {
        return adjacentMines;
    }
    //====================================================================================================================================================
    
    //mutator methods
    public void makeMine() {
        isMine = true;
        System.out.println("Space " + spaceID + " converted to mine.");
    }
    
    public void flag() {
        isFlagged = true;
    }
    
    public void setAdjacentMines (int adjacentMines) {
        this.adjacentMines = adjacentMines;
        System.out.println("Space " + spaceID + " assigned " + this.adjacentMines + " adjacent mine(s).");
    }
    
    public String toString() {
        if (isMine == true) {
            return "*";
        }
        else if (adjacentMines != 0) {
            return "" + adjacentMines;
        }
        else {
            return "";
        }
    }
    //====================================================================================================================================================
}
//====================================================================================================================================================