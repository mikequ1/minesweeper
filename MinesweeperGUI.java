import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import java.awt.event.*;

public class MinesweeperGUI extends JFrame implements ActionListener {
    private static GraphicsConfiguration gc;
    private JButton buttons[];
    private Space spaces[];
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private MediaTracker tracker = new MediaTracker(this);
    JFrame frame = new JFrame(gc);
    JFrame win;
    JPanel winpanel;
    private GameLogic game;
    Minesweeper newgame;
    Image image = toolkit.getImage("bombflagged.gif");
    Color brown = new Color(150,75,0);
    Color navyblue = new Color(0,0,140);
    Color cyan = new Color(0,128,128);
    Color green = new Color(0,100,0);

    public MinesweeperGUI(Space spaces[], GameLogic game) {
        this.spaces = spaces;
        this.game = game;
        tracker.addImage(image, 0);

        buttons = new JButton[game.getWidth() * game.getHeight()];

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int k=0; k<game.getWidth() * game.getHeight(); k++) {
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
            //buttons[k].addMouseListener(new MouseAdapter());
            buttons[k].setOpaque(true);
        }
        frame.setLayout(new GridLayout(game.getHeight(),game.getWidth()));
        frame.setTitle("Minesweeper");
        frame.setIconImage(image);
        frame.setVisible(true);
        frame.setSize(40 * game.getWidth(), 40 * game.getHeight());
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void open(JButton button, Space space) {
        int id = space.getSpaceID();
        int width = game.getWidth();
        int height = game.getHeight();
        int total = width * height - 1;
        button.setBackground(Color.WHITE);
        space.uncover();

        if (space.isMine() && !space.isFlagged()) {
            win = new JFrame(gc);
            //button.setText("\uD83D\uDCA3");
            for (int i=0; i<buttons.length; i++) {
                if (spaces[i].isMine()) {
                    buttons[i].setText("*");
                    buttons[i].setBackground(Color.RED);
                }
            }
            win.setTitle("You lose!");
            tracker.addImage(image, 0);

            win.setDefaultLookAndFeelDecorated(true);        
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            win.setIconImage(image);

            winpanel = new JPanel();
            BoxLayout boxlayout = new BoxLayout(winpanel, BoxLayout.Y_AXIS);
            winpanel.setLayout(boxlayout);
            winpanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

            JButton playagain = new JButton("Play again?");
            playagain.setMnemonic(KeyEvent.VK_E);
            playagain.setActionCommand("playagain");
            playagain.setToolTipText("Restarts the game");
            playagain.addActionListener(this);

            JButton back = new JButton("Change difficulty");
            back.setMnemonic(KeyEvent.VK_E);
            back.setActionCommand("return");
            back.setToolTipText("Returns to the difficulty screen");
            back.addActionListener(this);

            winpanel.add(playagain);
            winpanel.add(back);
            win.add(winpanel);
            win.pack();
            win.setVisible(true);
            game.hasWon = true;
        }
        else if (space.isMine() && space.isFlagged()) {

        }
        else if (!space.isMine()) {
            if (space.getAdjacentMines() == 0)
                button.setText("");
            else {
                if (Integer.parseInt(space.getAdjacentMinesString()) == 1) {
                    button.setForeground(Color.BLUE);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 2) {
                    button.setForeground(green);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 3) {
                    button.setForeground(Color.RED);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 4) {
                    button.setForeground(navyblue);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 5) {
                    button.setForeground(brown);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 6) {
                    button.setForeground(cyan);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 7) {
                    button.setForeground(Color.BLACK);
                }
                else if (Integer.parseInt(space.getAdjacentMinesString()) == 8) {
                    button.setForeground(Color.GRAY);
                }
                button.setText(space.getAdjacentMinesString());
            }
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
        if (coveredCount == game.getMines() && game.hasWon == false)
        {   
            win = new JFrame(gc);
            win.setTitle("You win!");
            tracker.addImage(image, 0);

            win.setDefaultLookAndFeelDecorated(true);        
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            win.setIconImage(image);

            winpanel = new JPanel();
            BoxLayout boxlayout = new BoxLayout(winpanel, BoxLayout.Y_AXIS);
            winpanel.setLayout(boxlayout);
            winpanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

            JButton playagain = new JButton("Play again?");
            playagain.setMnemonic(KeyEvent.VK_E);
            playagain.setActionCommand("playagain");
            playagain.setToolTipText("Restarts the game");
            playagain.addActionListener(this);

            JButton back = new JButton("Change difficulty");
            back.setMnemonic(KeyEvent.VK_E);
            back.setActionCommand("return");
            back.setToolTipText("Returns to the difficulty screen");
            back.addActionListener(this);

            winpanel.add(playagain);
            winpanel.add(back);
            win.add(winpanel);
            win.pack();
            win.setVisible(true);
            game.hasWon = true;
            //System.out.println("You win");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if ("playagain".equals(e.getActionCommand())) {
            game = new GameLogic(game.getWidth(),game.getHeight(),game.getMines());
            frame.setVisible(false);
            frame.dispose();
            win.setVisible(false);
            win.dispose();
        }
        else if ("return".equals(e.getActionCommand())) {
            newgame = new Minesweeper();
            frame.setVisible(false);
            frame.dispose();
            win.setVisible(false);
            win.dispose();
        }
        else if (game.firstClick) {
            game.assignGrid(Integer.parseInt(e.getActionCommand()));
            game.assignNeighbors();
            open(buttons[Integer.parseInt(e.getActionCommand())],spaces[Integer.parseInt(e.getActionCommand())]);
            game.firstClick = false;
        }
        else {
            open(buttons[Integer.parseInt(e.getActionCommand())],spaces[Integer.parseInt(e.getActionCommand())]);
        }
    }
}