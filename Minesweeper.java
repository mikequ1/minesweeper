<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

public class Minesweeper extends JFrame implements ActionListener {
    private int width = 0;
    private int height = 0;
    private int mines = 0;

    private Toolkit toolkit;
    private MediaTracker tracker;
    private JFrame frame = new JFrame();

    JTextField widthField = new JTextField("Width",2);
    JTextField heightField = new JTextField("Height",2);
    JTextField mineField = new JTextField("Mines",3);
    
    GameLogic game;

    public void drawMenu() {
        //setting the icon
        toolkit = Toolkit.getDefaultToolkit();
        tracker = new MediaTracker(this);
        Image image = toolkit.getImage("bombflagged.gif");
        tracker.addImage(image, 0);

        JFrame.setDefaultLookAndFeelDecorated(true);        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(image);

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        //beginner button
        JButton beginner = new JButton("Beginner");
        beginner.setMnemonic(KeyEvent.VK_E);
        beginner.setActionCommand("beginner");
        beginner.setToolTipText("9x9, 10 mines.");
        beginner.setOpaque(true);
        beginner.setForeground(Color.GREEN);
        beginner.addActionListener(this);

        //intermediate button
        JButton intermediate = new JButton("Intermediate");
        intermediate.setMnemonic(KeyEvent.VK_E);
        intermediate.setActionCommand("intermediate");
        intermediate.setToolTipText("16x16, 40 mines.");
        intermediate.setOpaque(true);
        intermediate.setForeground(Color.ORANGE);
        intermediate.addActionListener(this);

        //expert button
        JButton expert = new JButton("Expert");
        expert.setMnemonic(KeyEvent.VK_E);
        expert.setActionCommand("expert");
        expert.setToolTipText("30x16, 99 mines.");
        expert.setOpaque(true);
        expert.setForeground(Color.RED);
        expert.addActionListener(this);

        //text fields
        widthField.setToolTipText("Enter a width.");
        widthField.setActionCommand("widthSet");
        widthField.addActionListener(this);

        heightField.setToolTipText("Enter a height.");
        heightField.setActionCommand("heightSet");
        heightField.addActionListener(this);

        mineField.setToolTipText("Enter the number of mines.");
        mineField.setActionCommand("minesSet");
        mineField.addActionListener(this);

        //custom button
        JButton custom = new JButton("Submit");
        custom.setMnemonic(KeyEvent.VK_E);
        custom.setActionCommand("submit");
        custom.setToolTipText("Starts a game with custom width, height, and mines.");
        custom.addActionListener(this);

        //Labels
        JLabel presets = new JLabel("Presets");
        JLabel customs = new JLabel("Custom");

        panel.add(presets);
        panel.add(beginner); 
        panel.add(intermediate);
        panel.add(expert);
        panel.add(customs);
        panel.add(widthField);
        panel.add(heightField);
        panel.add(mineField);
        panel.add(custom);
        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if ("beginner".equals(e.getActionCommand())) {
            game = new GameLogic(9,9,10);
        }
        else if ("intermediate".equals(e.getActionCommand())) {
            game = new GameLogic(16,16,40);
        }
        else if ("expert".equals(e.getActionCommand())) {
            game = new GameLogic(30,16,99);
        }
        else if ("submit".equals(e.getActionCommand())) {
            game = new GameLogic(Integer.parseInt(widthField.getText()),
                Integer.parseInt(heightField.getText()),Integer.parseInt(mineField.getText()));
=======
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
>>>>>>> parent of d1546ea (initial clear + open)
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
<<<<<<< HEAD
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.drawMenu();
=======

>>>>>>> parent of d1546ea (initial clear + open)
    }
    //====================================================================================================================================================
}
//====================================================================================================================================================