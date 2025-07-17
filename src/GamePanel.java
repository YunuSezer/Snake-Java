import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {


    private final int GRID_SIZE_X = 20;
    private final int GRID_SIZE_Y = 20;
    private final int DELAY = 150;

    private int score = 0;

    private Point appleLoc;
    private LinkedList<Point> snake = new LinkedList<>();

    private Timer timer;

    private int DirectionX = 1;
    private int DirectionY = 0;


    public GamePanel() {
        setBackground(Color.black);
        setFocusable(true);
        requestFocusInWindow();

        timer = new Timer(DELAY,this);
        timer.start();

        createApple();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        if(DirectionY!=1){DirectionX=0;DirectionY=-1;}
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        if(DirectionY!=-1){DirectionX=0;DirectionY=1;}
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        if(DirectionX!=1){DirectionX=-1;DirectionY=0;}
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        if(DirectionX!=-1){DirectionX=1;DirectionY=0;}
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tileHeight = getHeight() / GRID_SIZE_Y;
        int tileWidth = getWidth() / GRID_SIZE_X;


        g.setColor(Color.darkGray);
        for (int row = 0; row < GRID_SIZE_Y; row++) {
            for (int col = 0; col < GRID_SIZE_X; col++) {
                g.drawRect(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
            }
        }

        g.setColor(Color.GREEN);
        for (Point p : snake) {
            int x = p.x * tileWidth;
            int y = p.y * tileHeight;
            g.fillRect(x, y, tileWidth, tileHeight);
        }

        g.setColor(Color.red);
        int x = appleLoc.x * tileWidth;
        int y = appleLoc.y * tileHeight;
        g.fillRect(x,y, tileWidth, tileHeight);
    }

    private void snakeLogic() {
        Point snakeHead = snake.getFirst();
        Point nextHead = new Point(snakeHead.x+DirectionX,snakeHead.y+DirectionY);


        if(nextHead.x < 0 || nextHead.x >= GRID_SIZE_X) gameOver();
        if(nextHead.y < 0 || nextHead.y >= GRID_SIZE_Y) gameOver();
        if(nextHead.equals(appleLoc)) createApple();

        if(snake.contains(nextHead)) gameOver();

        snake.addFirst(nextHead);
        snake.removeLast();
    }

    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Skor: "+(score-1), "Oyun Bitti!",JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void createApple(){
        updateScore();
        if(snake.size() < GRID_SIZE_X * GRID_SIZE_Y){
            do {
                appleLoc = MyRandom.nextPoint(GRID_SIZE_X, GRID_SIZE_Y);
            }while (snake.contains(appleLoc));
        }
        snake.add(appleLoc);
    }

    private void updateScore() {
        changeWindowTitle("Snake - Score: "+score);
        score++;
        if((score-1) % 10 == 0 && (score-1) != 0) Toolkit.getDefaultToolkit().beep();

    }

    public void actionPerformed(ActionEvent e) {

        snakeLogic();
        repaint();
    }

    public void changeWindowTitle(String newTitle) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if(frame != null){
            frame.setTitle(newTitle);
        }
    }

}
