import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import java.awt.event.*;
import java.io.*;

public class MinesweeperGUI extends JFrame implements ActionListener, MouseListener, Serializable {
    private static GraphicsConfiguration gc;
    private JButton buttons[];
    private Space spaces[];
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private MediaTracker tracker = new MediaTracker(this);
    JFrame frame = new JFrame(gc);
    JFrame win, instructionsframe;
    JPanel winpanel;
    JPanel mainpanel;
    private GameLogic game;
    Minesweeper newgame;
    Image image = toolkit.getImage("bombflagged.gif");
    Color brown = new Color(150,75,0);
    Color navyblue = new Color(0,0,140);
    Color cyan = new Color(0,128,128);
    Color green = new Color(0,100,0);
    int match, flags;
    long startTime;
    //final int TRUETYPE_FONT = Font.TRUETYPE_FONT;
    //File symbolattf = new File("/Library/Sys/BlueJ Projects/Minesweeper/symbola.ttf");
    //FileInputStream inputstream = new FileInputStream("/Library/Sys/BlueJ Projects/Minesweeper/symbola.ttf");
    //Font symbola = new Font("symbola",Font.PLAIN,25);
    //Font.createFont(int TYPE1_FONT, InputStream inputstream);
    JFrame time = new JFrame("Time");

    JMenuBar menubar;
    JMenuItem newgamemenu, difficultymenu, instructions;
    JMenu countermenu, gameoptions;

    public MinesweeperGUI(Space spaces[], GameLogic game) {
        this.spaces = spaces;
        this.game = game;
        flags = game.getMines();
        tracker.addImage(image, 0);
        buttons = new JButton[game.getWidth() * game.getHeight()];

        menubar = new JMenuBar();

        gameoptions = new JMenu("Game");
        countermenu = new JMenu("Mines: " + flags);

        newgamemenu = new JMenuItem("New Game");
        newgamemenu.addActionListener(this);
        newgamemenu.setActionCommand("newgamemenuitem");
        newgamemenu.setMnemonic(KeyEvent.VK_E);

        difficultymenu = new JMenuItem("Change Difficulty");
        difficultymenu.addActionListener(this);
        difficultymenu.setActionCommand("difficultymenuitem");
        difficultymenu.setMnemonic(KeyEvent.VK_E);

        instructions = new JMenuItem("Instructions");
        instructions.addActionListener(this);
        instructions.setActionCommand("instructions");
        instructions.setMnemonic(KeyEvent.VK_E);

        gameoptions.add(newgamemenu);
        gameoptions.add(difficultymenu);
        gameoptions.add(instructions);
        menubar.add(gameoptions);
        menubar.add(countermenu);
        frame.setJMenuBar(menubar);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int k=0; k<game.getWidth() * game.getHeight(); k++) {
            buttons[k] = new JButton();
            buttons[k].setPreferredSize(new Dimension(10,10));
            frame.add(buttons[k]);
            buttons[k].setBorder(border);
            // Set the label's font size to the newly determined size.
            buttons[k].setFont(new Font("Sans-Serif", Font.PLAIN, 25));
            buttons[k].setName(spaces[k].getSpaceIDString());
            buttons[k].setHorizontalAlignment(SwingConstants.CENTER);
            //buttons[k].setMnemonic(KeyEvent.VK_E);
            buttons[k].setActionCommand(spaces[k].getSpaceIDString());
            buttons[k].addActionListener(this);
            buttons[k].setBackground(Color.GRAY);
            buttons[k].addMouseListener(this);
            buttons[k].setOpaque(true);
        }
        frame.setLayout(new GridLayout(game.getHeight(),game.getWidth()));
        frame.setTitle("Minesweeper");
        frame.setIconImage(image);
        frame.setVisible(true);
        frame.setSize(40 * game.getWidth(), 40 * game.getHeight());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void open(JButton button, Space space) {
        int id = space.getSpaceID();
        int width = game.getWidth();
        int height = game.getHeight();
        int total = width * height - 1;
        if (game.hasWon) {

        }
        else if(space.isFlagged()) {

        }
        else if (space.isMine()) {
            win = new JFrame(gc);
            space.uncover();
            for (int i=0; i<buttons.length; i++) {
                if (spaces[i].isMine() && spaces[i].isFlagged()) {
                    buttons[i].setBackground(Color.GREEN);
                }
                else if (spaces[i].isMine() && !spaces[i].isFlagged()) {
                    //buttons[i].setFont(symbola);
                    //buttons[i].setText("\uD83D\uDCA3");
                    buttons[i].setText("*");
                    buttons[i].setBackground(Color.RED);
                }
                else if (!spaces[i].isMine() && spaces[i].isFlagged()) {
                    buttons[i].setText("X");
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
            win.setLocationRelativeTo(null);
            win.setVisible(true);
            game.hasWon = true;
        }
        else if (space.getCovered()) {
            button.setBackground(Color.WHITE);
            space.uncover();
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
        else if (space.getAdjacentMines() == game.getFlaggedNeighbors(space) && space.isUserClick) {
            //North
            if (id - width >= 0) {
                spaces[id-width].isUserClick = false;
                open(buttons[id-width],spaces[id-width]);
                spaces[id-width].isUserClick = true;
            }
            //South  
            if (id + width <= total) {
                spaces[id+width].isUserClick = false;
                open(buttons[id+width],spaces[id+width]);
                spaces[id+width].isUserClick = true;
            }
            //West
            if (id - 1 >= 0 && space.getYPos() == spaces[id-1].getYPos()) {
                spaces[id-1].isUserClick = false;
                open(buttons[id-1],spaces[id-1]);
                spaces[id-1].isUserClick = true;
            }
            //East
            if (id + 1 <= total && space.getYPos() == spaces[id+1].getYPos()) {
                spaces[id+1].isUserClick = false;
                open(buttons[id+1],spaces[id+1]);
                spaces[id+1].isUserClick = true;
            }
            //North-West
            if (id - width - 1 >= 0 && space.getYPos() - 1 == spaces[id-width-1].getYPos()) {
                spaces[id-width-1].isUserClick = false;
                open(buttons[id-width-1],spaces[id-width-1]);
                spaces[id-width-1].isUserClick = true;
            }
            //North-East
            if (id - width + 1 >= 0 && id - width + 1 <= width * height - 1 &&
            space.getYPos() - 1 == spaces[id-width+1].getYPos()) {
                spaces[id-width+1].isUserClick = false;
                open(buttons[id-width+1],spaces[id-width+1]);
                spaces[id-width+1].isUserClick = true;
            }
            //South-West
            if (id + width - 1 >= 0 && id + width - 1 <= width * height - 1 &&
            space.getYPos() + 1 == spaces[id+width-1].getYPos()) {
                spaces[id+width-1].isUserClick = false;
                open(buttons[id+width-1],spaces[id+width-1]);
                spaces[id+width-1].isUserClick = true;
            }
            //South-East
            if (id + width + 1 <= width * height - 1 && space.getYPos() + 1 == spaces[id+width+1].getYPos()) {
                spaces[id+width+1].isUserClick = false;
                open(buttons[id+width+1],spaces[id+width+1]);
                spaces[id+width+1].isUserClick = true;
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
            flags = 0;
            countermenu.setText("Mines: " + flags);
            for (int i=0; i<buttons.length; i++) {
                if (spaces[i].isMine() && spaces[i].isFlagged()) {
                    buttons[i].setBackground(Color.GREEN);
                }
                else if (spaces[i].isMine() && !spaces[i].isFlagged()) {
                    buttons[i].setText("*");
                    buttons[i].setBackground(Color.GREEN);
                }
                // else if (!spaces[i].isMine() && spaces[i].isFlagged()) {
                // buttons[i].setText("X");
                // buttons[i].setBackground(Color.RED);
                // }
            }
            tracker.addImage(image, 0);
            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;
            long elapsedMillis = elapsedTime / 10 % 100;
            long secondsDisplay = elapsedSeconds % 60;
            long elapsedMinutes = elapsedSeconds / 60;
            win.setDefaultLookAndFeelDecorated(true);        
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            win.setIconImage(image);
            JLabel timelabel = new JLabel();
            if ((int)elapsedMillis/10!=0) {
                timelabel.setText("Your time: " + elapsedSeconds + "." + elapsedMillis + " seconds.");
            }
            else {
                timelabel.setText("Your time: " + elapsedSeconds + "." + elapsedMillis + "0 seconds.");
            }
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
            winpanel.add(timelabel);
            winpanel.add(playagain);
            winpanel.add(back);
            win.add(winpanel);
            win.pack();
            win.setLocationRelativeTo(null);
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
        else if ("newgamemenuitem".equals(e.getActionCommand())) {
            game = new GameLogic(game.getWidth(),game.getHeight(),game.getMines());
            frame.setVisible(false);
            frame.dispose();
            //win.setVisible(false);
            //win.dispose();
        }
        else if ("difficultymenuitem".equals(e.getActionCommand())) {
            newgame = new Minesweeper();
            frame.setVisible(false);
            frame.dispose();
            //win.setVisible(false);
            //win.dispose();
        }
        else if ("instructions".equals(e.getActionCommand())) {
            instructionsframe = new JFrame("How to play");
            instructionsframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            instructionsframe.setResizable(false);
            instructionsframe.setSize(675,225);
            instructionsframe.setLocationRelativeTo(null);
            instructionsframe.setIconImage(image);
            //JLabel instructionslabel = new JLabel();
            JTextArea textArea = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(textArea); 
            textArea.setEditable(false);
            textArea.setText("Minesweeper begins with a grid of spaces, some of which are mines.\nLeft-clicking a space will reveal its contents.\nIf the space is a mine: the game will end.\nIf the space is not a mine: it will display a number,\ntelling the player how many of its eight neighbors are mines.\nRight-clicking a space will flag it, allowing the player to keep track of spaces they believe are mines.\nLeft-clicking a space that has already been uncovered will open all eight of its neighbors,\nbut only if the player has already flagged the correct number of spaces around it.\nTo win the game, the player must uncover all non-mine spaces. Good luck!");
            JPanel instructionspanel = new JPanel();
            BoxLayout boxlayout = new BoxLayout(instructionspanel, BoxLayout.Y_AXIS);
            instructionspanel.setLayout(boxlayout);
            instructionspanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
            JButton ok = new JButton("Ok");
            ok.addActionListener(this);
            ok.setActionCommand("ok");
            instructionspanel.add(textArea);
            instructionspanel.add(ok);
            instructionsframe.add(instructionspanel);
            instructionsframe.setVisible(true);
        }
        else if ("ok".equals(e.getActionCommand())) {
            instructionsframe.setVisible(false);
            instructionsframe.dispose();
        }
        else if (game.firstClick) {
            startTime = System.currentTimeMillis();

            game.assignGrid(Integer.parseInt(e.getActionCommand()));
            game.assignNeighbors();
            open(buttons[Integer.parseInt(e.getActionCommand())],spaces[Integer.parseInt(e.getActionCommand())]);
            game.firstClick = false;
        }
        else {
            open(buttons[Integer.parseInt(e.getActionCommand())],spaces[Integer.parseInt(e.getActionCommand())]);
        }
    }

    public void mouseClicked(MouseEvent e) {
        // int id = Integer.parseInt(e.getComponent().getName());
        // if (SwingUtilities.isRightMouseButton(e) && !spaces[id].isFlagged() && spaces[id].getCovered()) {
        // //System.out.println("first one");
        // spaces[id].flag();
        // buttons[id].setText("|>");
        // }
        // else if (SwingUtilities.isRightMouseButton(e) && spaces[id].isFlagged() && spaces[id].getCovered()) {
        // //System.out.println("2nd one");
        // spaces[id].unflag();
        // buttons[id].setText("");
        // }
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        int id = Integer.parseInt(e.getComponent().getName());
        if (SwingUtilities.isRightMouseButton(e) && !spaces[id].isFlagged() && spaces[id].getCovered() && id == match && !game.hasWon) {
            //System.out.println("first one");
            flags--;
            countermenu.setText("Mines: " + flags);
            spaces[id].flag();
            buttons[id].setText("|>");
        }
        else if (SwingUtilities.isRightMouseButton(e) && spaces[id].isFlagged() && spaces[id].getCovered() && id == match && !game.hasWon) {
            //System.out.println("2nd one");
            flags++;
            countermenu.setText("Mines: " + flags);
            spaces[id].unflag();
            buttons[id].setText("");
        }
    }

    public void mousePressed(MouseEvent e) {
        int id = Integer.parseInt(e.getComponent().getName());
        if (SwingUtilities.isRightMouseButton(e) && !spaces[id].isFlagged() && spaces[id].getCovered() && !game.hasWon) {
            //System.out.println("first one");
            match = id;
        }
        else if (SwingUtilities.isRightMouseButton(e) && spaces[id].isFlagged() && spaces[id].getCovered() && !game.hasWon) {
            //System.out.println("2nd one");
            match = id;
        }
    }
}