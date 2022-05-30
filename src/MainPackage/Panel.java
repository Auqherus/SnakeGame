package MainPackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Panel extends JPanel implements ActionListener {
    // Ustawienia panelu dla gry
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 62;
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

    public void draw(Graphics g) {
        if (running) {
            /*for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); //ustawienie siatki na X
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); //ustawienie siatki na Y
            }*/
                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // ustawienie jabka
                for (int i = 0; i < bodyParts; i++) { // cialo weza
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(36, 138, 26));
                   /* g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255)));*/
                    g.fillRect(tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(182, 36, 217, 255));
            g.setFont(new Font("Comic Sans MS",Font.BOLD,20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score : "+eatenApples,(SCREEN_WIDTH - metrics.stringWidth("Score : "+eatenApples))/2,g.getFont().getSize()); // for score

            g.setColor(new Color(48, 217, 36, 255)); //podpis autora
            g.setFont(new Font("Comic Sans MS",Font.BOLD,13));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Game made by Auqherus na zaliczenie projektu",(SCREEN_WIDTH+125-metrics2.stringWidth("Game made by Auqherus"))/2,SCREEN_HEIGHT-10); // for author
            /*g.drawString("Game made by Auqherus",SCREEN_WIDTH-120,SCREEN_HEIGHT-10);*/
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void moves(){
        for (int i = bodyParts; i>0; i--) {
            tabX[i] =tabX[i-1];
            tabY[i] =tabY[i-1];
        }
        switch(direction){
            case 'U':
                tabY[0] = tabY[0] - UNIT_SIZE;
                break;
            case 'D':
                tabY[0] = tabY[0] + UNIT_SIZE;
                break;
            case 'R':
                tabX[0] = tabX[0] + UNIT_SIZE;
                break;
            case 'L':
                tabX[0] = tabX[0] - UNIT_SIZE;
                break;
        }
    }
    public void lookForApple(){
        if(tabX[0] == appleX && tabY[0] == appleY){
            bodyParts++;
            eatenApples++;
            newApple();
        }
    }
    public void checkCollisions(){
        for (int i = bodyParts; i>0; i--) {
            if((tabX[0] == tabX[i] && tabY[0] == tabY[i])){ // koniec gry jak glowa weza uderzy w ogon
                running = false;
            }
            if(tabX[0]< 0){ // kolizja z lewa krawedzia
                running = false;
            }
            if(tabX[0]> SCREEN_WIDTH){ // kolizja z prawa krawedzia
                running = false;
            }
            if(tabY[0]<0){ // kolizja z gorna krawedzia
                running = false;
            }
            if(tabY[0]>SCREEN_HEIGHT){ // kolizja z dolna krawedzia
                running = false;
            }
            if(!running){
                timer.stop();
            }
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED); // wynik koncowy
        g.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : "+eatenApples,(SCREEN_WIDTH - metrics1.stringWidth("Score : "+eatenApples))/2,g.getFont().getSize());

        g.setColor(Color.RED); // metoda dla koniec gry
        g.setFont(new Font("Comic Sans MS",Font.BOLD,82));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!",(SCREEN_WIDTH - metrics.stringWidth("Game Over!"))/2,SCREEN_HEIGHT/2 );

        g.setColor(new Color(48, 217, 36, 255)); //podpis autora
        g.setFont(new Font("Comic Sans MS",Font.BOLD,13));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game made by Auqherus na zaliczenie projektu",(SCREEN_WIDTH+125-metrics2.stringWidth("Game made by Auqherus"))/2,SCREEN_HEIGHT-10); // for author
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            moves();
            lookForApple();
            checkCollisions();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
