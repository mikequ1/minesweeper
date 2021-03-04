import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.event.*;

public class MinesweeperGUI extends JFrame implements ActionListener {
    private static GraphicsConfiguration gc;
    private JButton buttons[];
    private Space spaces[];
    private Toolkit toolkit;
    private MediaTracker tracker;
    JFrame frame = new JFrame(gc);
    private GameLogic game;

    public MinesweeperGUI(Space spaces[], GameLogic game) {
        this.spaces = spaces;
        this.game = game;

        toolkit = Toolkit.getDefaultToolkit();
        tracker = new MediaTracker(this);
        Image image = toolkit.getImage("bombflagged.gif");
        tracker.addImage(image, 0);

        buttons = new JButton[GameLogic.getWidth() * GameLogic.getHeight()];

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int k=0; k<GameLogic.getWidth() * GameLogic.getHeight(); k++) {
            buttons[k] = new JButton();
            buttons[k].setPreferredSize(new Dimension(10,10));
            frame.add(buttons[k]);
            buttons[k].setBorder(border);
            // Set the label's font size to the newly determined size.
            buttons[k].setFont(new Font("Sans-Serif", Font.PLAIN, 25));
            buttons[k].setHorizontalAlignment(SwingConstants.CENTER);
            //buttons[k].setMnemonic(KeyEvent.VK_E);
            buttons[k].setActionCommand(spaces[k].getSpaceIDString());
            buttons[k].addActionListener(this);
            buttons[k].setBackground(Color.GRAY);
            buttons[k].setOpaque(true);
        }
        frame.setLayout(new GridLayout(GameLogic.getHeight(),GameLogic.getWidth()));
        frame.setTitle("Minesweeper");
        frame.setIconImage(image);
        frame.setVisible(true);
        frame.setSize(40 * GameLogic.getWidth(), 40 * GameLogic.getHeight());
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void open(JButton button, Space space) {
        int id = space.getSpaceID();
        int width = GameLogic.getWidth();
        int height = GameLogic.getHeight();
        int total = width * height - 1;
        button.setBackground(Color.WHITE);
        space.uncover();
        
        if (space.isMine() && !space.isFlagged()) {
            System.exit(0);
        }
        else if (space.isMine() && space.isFlagged()) {

        }
        else if (!space.isMine()) {
            if (space.getAdjacentMines() == 0)
                button.setText("");
            else
                button.setText(space.getAdjacentMinesString());
            //North
            if (id - width >= 0 && spaces[id-width].isMine() == false && spaces[id-width].getCovered() == true && space.getAdjacentMines() == 0){
                open(buttons[id-width],spaces[id-width]);
            }
            //South  
            if (id + width <= total && spaces[id+width].isMine() == false && spaces[id+width].getCovered() == true && space.getAdjacentMines() == 0)
              {
                open(buttons[id+width],spaces[id+width]);
            }
            //West
            if (id - 1 >= 0 && space.getYPos() == spaces[id-1].getYPos() && spaces[id-1].isMine() == false
            && spaces[id-1].getCovered() == true && space.getAdjacentMines() == 0) {
                open(buttons[id-1],spaces[id-1]);
            }
            //East
            if (id + 1 <= total && space.getYPos() == spaces[id+1].getYPos() &&
            spaces[id+1].isMine() == false && spaces[id+1].getCovered() == true && space.getAdjacentMines() == 0) {
                open(buttons[id+1],spaces[id+1]);
            }
            //North-West
            if (id - width - 1 >= 0 && space.getYPos() - 1 == spaces[id-width-1].getYPos() &&
            spaces[id-width-1].isMine() == false && spaces[id-width-1].getCovered() == true &&
             space.getAdjacentMines() == 0) {
                open(buttons[id-width-1],spaces[id-width-1]);
            }
            //North-East
            if (id - width + 1 >= 0 && id - width + 1 <= width * height - 1 &&
            space.getYPos() - 1 == spaces[id-width+1].getYPos() && spaces[id-width+1].isMine() == false &&
            spaces[id-width+1].getCovered() == true &&  space.getAdjacentMines() == 0) {
                open(buttons[id-width+1],spaces[id-width+1]);
            }
            //South-West
            if (id + width - 1 >= 0 && id + width - 1 <= width * height - 1 &&
            space.getYPos() + 1 == spaces[id+width-1].getYPos() && spaces[id+width-1].isMine() == false
            && spaces[id+width-1].getCovered() == true &&  space.getAdjacentMines() == 0) {
                open(buttons[id+width-1],spaces[id+width-1]);
            }
            //South-East
            if (id + width + 1 <= width * height - 1 && space.getYPos() + 1 == spaces[id+width+1].getYPos() &&
            spaces[id+width+1].isMine() == false && spaces[id+width+1].getCovered() == true &&  space.getAdjacentMines() == 0) {
                open(buttons[id+width+1],spaces[id+width+1]);
            }
        }
        winGame();
        
    }
    
    
    public void winGame()
    {   
        int coveredCount = 0;
        for (int i = 0; i < spaces.length; i++)
        {
            if (spaces[i].getCovered() == true)
                coveredCount++;   
        }
        if (coveredCount == game.getMines())
        {   
            frame.setVisible(false);
            frame.dispose();
            JFrame win = new JFrame(gc);
            System.out.println("you win");
        }
        System.out.println("run once");
        System.out.println(coveredCount);
        System.out.println(game.getMines());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        int id = Integer.parseInt(e.getActionCommand());
        open(buttons[id],spaces[id]);
    }
}