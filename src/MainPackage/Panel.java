package MainPackage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    // Ustawienia panelu dla gry
    static final int SCREEN_WIDTH = 720;
    static final int SCREEN_HEIGHT = 720;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static int DELAY = 65;
    // ustawienia planszy
    final int tabX[] = new int[GAME_UNITS]; // dla węża(głowa, body etc)
    final int tabY[] = new int[GAME_UNITS];
    int bodyParts = 2;
    int eatenApples;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    boolean isGameMenu = true;
    boolean isGameOver = false;
    File appleFile = new File("apple.png");
    /*File bodyFile = new File("body2.png");*/
    boolean pause = false;

    Timer timer;
    Random random;

    Panel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame() {
        newApple();
        newSnakePosition();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void gamePause(Graphics g) {
        timer.stop();
        g.setColor(Color.GREEN);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("PAUSE!", (SCREEN_WIDTH - metrics2.stringWidth("PAUSE!")) / 2, SCREEN_HEIGHT - 450);

        g.setColor(new Color(182, 36, 217, 255));
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score : " + eatenApples, (SCREEN_WIDTH - metrics.stringWidth("Score : " + eatenApples)) / 2, g.getFont().getSize()); // wynik

        g.setColor(new Color(48, 217, 36, 255)); //podpis autora
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        g.drawString("Game made by Auqherus na zaliczenie projektu", (SCREEN_WIDTH - 305), SCREEN_HEIGHT - 10); // for author


        try {
            Image appleFoodImg = ImageIO.read(appleFile);
            g.drawImage(appleFoodImg, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this); // JABLKO JAKO OBRAZEK!
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < bodyParts; i++) { // cialo weza
            if (i == 0) {
                g.setColor(Color.GREEN);
                g.fillRect(tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE); // glowa wyglad

            } else {
                g.setColor(new Color(36, 138, 26));
                g.fillRect(tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE); // cialo wyglad

            }
        }
    }

    public void draw(Graphics g) {

        if (pause && !isGameMenu) {
            gamePause(g);

        } else {
            if (isGameMenu) {

                g.setColor(Color.GREEN); // metoda dla START GAME
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Press Enter to start Game!", (SCREEN_WIDTH - metrics.stringWidth("Press Enter to start Game!")) / 2, SCREEN_HEIGHT - 550);

                g.setColor(Color.GREEN); // Pause
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
                FontMetrics metrics2 = getFontMetrics(g.getFont());
                g.drawString("Press [P] to PAUSE!", (SCREEN_WIDTH - metrics2.stringWidth("Press [P] to PAUSE!")) / 2, SCREEN_HEIGHT - 450);

                g.setColor(Color.GREEN); // arrows
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("Use [<] [^] [>] to MOVE", (SCREEN_WIDTH - metrics3.stringWidth("Use [<] [^] [>] to MOVE")) / 2, SCREEN_HEIGHT - 350);
                /*FontMetrics metrics = getFontMetrics(g.getFont());*/

                g.setColor(new Color(48, 217, 36, 255)); //podpis autora
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
                g.drawString("Game made by Auqherus na zaliczenie projektu", (SCREEN_WIDTH - 305), SCREEN_HEIGHT - 10); // for author
                /*g.drawString("Game made by Auqherus",SCREEN_WIDTH-120,SCREEN_HEIGHT-10);*/
            } else if (running) {
            /*for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); //ustawienie siatki na X
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); //ustawienie siatki na Y
            }*/
                isGameMenu = false;
                /* g.setColor(Color.ORANGE);*/
                /*g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // wygląd jabłka*/
                /* g.fillArc(appleX,appleY,UNIT_SIZE,UNIT_SIZE,15,225);*/ // rogalik zamiast jabłka ^^

                try {
                    Image appleFoodImg = ImageIO.read(appleFile);
                    g.drawImage(appleFoodImg, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this); // JABLKO JAKO OBRAZEK!
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < bodyParts; i++) { // cialo weza
                    if (i == 0) {
                        g.setColor(Color.GREEN);
                        g.fillRect(tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE); // glowa wyglad
                    /*try {

                        Image bodyImage = ImageIO.read(bodyFile);

                        g.drawImage(bodyImage, tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE, this);
                         // Kolorowe cialo!
                        }
                    catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    } else {
                        g.setColor(new Color(36, 138, 26));
                        /* g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255)));*/
                        g.fillRect(tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE); // cialo wyglad
                    /*try {

                        Image bodyImage = ImageIO.read(bodyFile);

                        g.drawImage(bodyImage, tabX[i], tabY[i], UNIT_SIZE, UNIT_SIZE, this);
                        // Kolorowe cialo!
                        }
                    catch (Exception e) {
                        e.printStackTrace();
                        }*/
                    }
                }
                g.setColor(new Color(182, 36, 217, 255));
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score : " + eatenApples, (SCREEN_WIDTH - metrics.stringWidth("Score : " + eatenApples)) / 2, g.getFont().getSize()); // wynik

                g.setColor(new Color(48, 217, 36, 255)); //podpis autora
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
                /*FontMetrics metrics2 = getFontMetrics(g.getFont());*/
                g.drawString("Game made by Auqherus na zaliczenie projektu", (SCREEN_WIDTH - 305), SCREEN_HEIGHT - 10); // for author
                /*g.drawString("Game made by Auqherus",SCREEN_WIDTH-120,SCREEN_HEIGHT-10);*/

            } else {
                gameOver(g);
                isGameOver = true;
            }
        }
    }



    public void newApple() {

        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;   // gdzie ma się pojawić jabłko
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }


    public void newSnakePosition(){
        tabX[0] = UNIT_SIZE*15;
        tabY[0] = UNIT_SIZE*15;
    }
    public void moves(){
        for (int i = bodyParts; i>0; i--) {  // animowanie ciała węża - podążaj za ciałem
            tabX[i] =tabX[i-1];
            tabY[i] =tabY[i-1];
        }
        switch (direction) { // animowanie głowy węża
            case 'U' -> tabY[0] = tabY[0] - UNIT_SIZE;
            case 'D' -> tabY[0] = tabY[0] + UNIT_SIZE;
            case 'R' -> tabX[0] = tabX[0] + UNIT_SIZE;
            case 'L' -> tabX[0] = tabX[0] - UNIT_SIZE;
        }
    }
    public void lookForApple(){
        if(tabX[0] == appleX && tabY[0] == appleY){   // jeżeli głowa węża będzie na pozycji jabłka :
            bodyParts++;                     // zwiększ dlugość węża +1
            eatenApples++;                  // nalicz wynik +1
            newApple();                     // generuj nowe jabłko
        }
    }
    public void checkCollisions(){
        for (int i = bodyParts; i>0; i--) {
            if((tabX[0] == tabX[i] && tabY[0] == tabY[i])){ // koniec gry jak glowa weza uderzy w ogon
                running = false;
            }
            if(tabX[i] == appleX && tabY[i] == appleY) {
                newApple();
            }
            if(tabX[0]< 0){ // kolizja z lewa krawedzia
                running = false;
            }
            if(tabX[0]>= SCREEN_WIDTH){ // kolizja z prawa krawedzia
                running = false;
            }
            if(tabY[0]<0){ // kolizja z gorna krawedzia
                running = false;
            }
            if(tabY[0]>=SCREEN_HEIGHT){ // kolizja z dolna krawedzia
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
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over!",(SCREEN_WIDTH - metrics2.stringWidth("Game Over!"))/2,SCREEN_HEIGHT/2 );

        g.setColor(Color.GREEN); // wciśnij spację, by zagrać jeszcze raz
        g.setFont(new Font("Comic Sans MS",Font.BOLD,22));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press SPACE to Try again!",(SCREEN_WIDTH - metrics3.stringWidth("Press SPACE to Try again!"))/2,SCREEN_HEIGHT-250 );

        g.setColor(Color.GREEN); // wciśnij spację, by zagrać jeszcze raz
        g.setFont(new Font("Comic Sans MS",Font.BOLD,16));
        FontMetrics metrics4 = getFontMetrics(g.getFont());
        g.drawString("Press Esc to back to MENU!",(SCREEN_WIDTH - metrics4.stringWidth("Press Esc to back to MENU!"))/2,SCREEN_HEIGHT-150 );

        g.setColor(new Color(48, 217, 36, 255)); //podpis autora
        g.setFont(new Font("Comic Sans MS",Font.BOLD,13));
        /*FontMetrics metrics4 = getFontMetrics(g.getFont());*/
        g.drawString("Game made by Auqherus na zaliczenie projektu",(SCREEN_WIDTH-305),SCREEN_HEIGHT-10); // for author
    }

    @Override
    public void actionPerformed(ActionEvent e) {  // główne metody metody kolizji, jabłka i ruchu

        if(running){
            moves();
            lookForApple();
            checkCollisions();
        }

        repaint();
    }



    public class MyKeyAdapter extends KeyAdapter{
        @Override

        public void keyPressed(KeyEvent e){// przypisanie ruchów węża do klawiatury

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
                case KeyEvent.VK_ENTER:
                    if(isGameMenu && !running){
                        isGameMenu = false;
                        startGame();
                    }
                    break;

                case KeyEvent.VK_SPACE:
                    if(isGameOver){
                       eatenApples = 0;
                       bodyParts = 2;
                       isGameOver = false;
                       startGame();
                    }
                    break;

                case KeyEvent.VK_ESCAPE:
                    if(isGameOver && !isGameMenu){
                        eatenApples = 0;
                        bodyParts = 2;
                        isGameOver = false;
                        isGameMenu = true;
                        startGame();
                    }
                    break;

                case KeyEvent.VK_P:  // PAUSE
                    if(!pause && running){

                        pause = true;

                    }
                    else if(pause && running){
                        timer.start();
                        pause = false;


                    }
                    break;
            }

        }
    }
}
