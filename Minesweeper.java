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
    
    Color green = new Color(0,100,0);
    Color orange = new Color(255,120,0);
    
    Minesweeper minesweeper;
    
    public Minesweeper() {
        drawMenu();
    }
    
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
        beginner.setForeground(green);
        beginner.addActionListener(this);

        //intermediate button
        JButton intermediate = new JButton("Intermediate");
        intermediate.setMnemonic(KeyEvent.VK_E);
        intermediate.setActionCommand("intermediate");
        intermediate.setToolTipText("16x16, 40 mines.");
        intermediate.setOpaque(true);
        intermediate.setForeground(orange);
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
            GameLogic game = new GameLogic(9,9,10);
        }
        else if ("intermediate".equals(e.getActionCommand())) {
            GameLogic game = new GameLogic(16,16,40);
        }
        else if ("expert".equals(e.getActionCommand())) {
            GameLogic game = new GameLogic(30,16,99);
        }
        else if ("submit".equals(e.getActionCommand())) {
            GameLogic game = new GameLogic(Integer.parseInt(widthField.getText()),
                Integer.parseInt(heightField.getText()),Integer.parseInt(mineField.getText()));
        }
        frame.setVisible(false);
        frame.dispose();
    }

    public static void main (String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        //minesweeper.drawMenu();
    }
}