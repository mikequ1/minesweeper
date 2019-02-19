//imports
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
//====================================================================================================================================================

//classes
public class MinesweeperGUI extends JFrame {
    //field variables
    private static GraphicsConfiguration gc;
    private JPanel panels[];
    private Toolkit toolkit;
    private MediaTracker tracker;
    //====================================================================================================================================================

    //constructors
    MinesweeperGUI(Space spaces[]) {
        JFrame frame = new JFrame(gc);
        toolkit = Toolkit.getDefaultToolkit();
        tracker = new MediaTracker(this);
        Image image = toolkit.getImage("bombflagged.gif");
        tracker.addImage(image, 0);
        panels = new JPanel[Minesweeper.getWidth() * Minesweeper.getHeight()];
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        for (int k=0; k<Minesweeper.getWidth() * Minesweeper.getHeight(); k++) {
            panels[k] = new JPanel();
            panels[k].setPreferredSize(new Dimension(10,10));
            frame.add(panels[k]);
            panels[k].setBorder(border);
            
            panels[k].add(new JLabel(spaces[k].toString()));
        }
        frame.setLayout(new GridLayout(Minesweeper.getHeight(),Minesweeper.getWidth()));
        frame.setTitle("Minesweeper");
        frame.setIconImage(image);
        frame.setVisible(true);
        frame.setSize(40 * Minesweeper.getWidth(), 40 * Minesweeper.getHeight());
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    //====================================================================================================================================================
}
//====================================================================================================================================================