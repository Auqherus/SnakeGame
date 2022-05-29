package MainPackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Panel extends JPanel implements ActionListener {
    // Ustawienia panelu dla gry
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    // ustawienia planszy
    final int tabX[] = new int[GAME_UNITS]; // dla węża(głowa, body etc)
    final int tabY[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int eatenApples;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
     super.paintComponent(g);
     draw(g);
    }

    public void draw(Graphics g){
        for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
        }
    }
    public void newApple(){

    }
    public void moves(){

    }
    public void lookForApple(){

    }
    public void checkCollisions(){

    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }
    }
}
