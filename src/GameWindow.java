import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final int WINDOW_SIZE = 500;

    public GameWindow() {
        setTitle("Snake");
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE));
        add(panel);
        pack();
        setVisible(true);
    }

}
