import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
   static final int SCREEN_WIDTH=600;
   static final int SCREEN_HEIGHT=600;
   static final int UNIT_SIZE=25;
   static final int GAME_UNIT=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
   static int DELAY=200;
   int[] x=new int[GAME_UNIT];
   int[] y=new int[GAME_UNIT];
   int bodyParts=3;
   int applesEaten;
   int appleX;
   int appleY;
   char direction='R';
   boolean running=false;
   Timer timer;
   Random random;
   JButton button,easy,medium,hard,square,oval,red,yellow,green,blue,cyan,rainbow,white,go;
   JPanel jPanel;
   static String shape,colour;

    private java.awt.event.ActionListener ActionListener;


    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        button=new JButton("Restart");
        button.setForeground(Color.white );
        button.setBackground(Color.black);
        button.setLocation(SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
        jPanel=new JPanel();
        jPanel.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        jPanel.setBackground(Color.black);
        jPanel.setForeground(Color.white);
        jPanel.setLayout(null);
        easy=new JButton("Easy");
        easy.setBounds(100,330,100,60);
        medium=new JButton("Medium");
        medium.setBounds(250,330,100,60);
        hard=new JButton("Hard");
        hard.setBounds(400,330,100,60);
        easy.setVisible(true);
        medium.setVisible(true);
        hard.setVisible(true);
        easy.setBackground(Color.LIGHT_GRAY);
        medium.setBackground(Color.cyan);
        hard.setBackground(Color.red);
        easy.setForeground(Color.white);
        medium.setForeground(Color.white);
        hard.setForeground(Color.white);
        JLabel jLabel=new JLabel("Select the Shape of the snake");
        jLabel.setForeground(Color.yellow);
        jLabel.setVisible(true);
        jLabel.setBounds(140,0, 400,200);
        jLabel.setFont(new Font("Verdana", Font.BOLD,20));
        square=new JButton("SQUARE");
        oval=new JButton("OVAL");
        square.setBounds(100,140,100,60);
        oval.setBounds(400,140,100,60);
        square.setBackground(Color.ORANGE);
        oval.setBackground(Color.ORANGE);
        square.setForeground(Color.white);
        oval.setForeground(Color.white);
        JLabel jLabel2=new JLabel("Select the Speed");
        jLabel2.setForeground(Color.white);
        jLabel2.setVisible(true);
        jLabel2.setBounds(200,180, 400,200);
        jLabel2.setFont(new Font("Verdana", Font.BOLD,20));
        go=new JButton("GO");
        go.setBounds(220,450,150,60);
        JLabel pic = new JLabel(new ImageIcon("snake1.png"));
        pic.setBounds(0,0,610,610);
        pic.setVisible(true);
        jPanel.add(easy);
        jPanel.add(medium);
        jPanel.add(hard);
        jPanel.add(jLabel);
        jPanel.add(square);
        jPanel.add(oval);
        jPanel.add(jLabel2);
        jPanel.add(go);
        jPanel.add(pic);
        jPanel.setVisible(true);
        this.add(jPanel);
        this.add(button);
        square.addActionListener(this);
        oval.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        go.addActionListener(this);
    }

    public void startGame(){

        button.setVisible(false);
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
            g.setColor(Color.white);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    if(shape.equals("square")){
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    else if(shape.equals("oval")) {
                        g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                } else {
                   // g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    if(shape.equals("square")){
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    else if(shape.equals("oval")) {
                        g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }
            //Score
            g.setColor(Color.yellow);
            g.setFont(new Font("Ink Free",Font.BOLD,50));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX=random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U': {
                y[0] = y[0] - UNIT_SIZE;
                break;
            }
            case 'D':{
                y[0]=y[0] + UNIT_SIZE;
                break;
            }
            case 'L':{
                x[0]=x[0] - UNIT_SIZE;
                break;
            }
            case 'R':{
                x[0]=x[0] + UNIT_SIZE;
                break;
            }

        }
    }
    public void checkApple(){
        if((x[0]== appleX) && (y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //checks if head collides with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        //check if head touches left border
        if(x[0]<0){
            running=false;
        }
        //check if head touches right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //check if head touches top border
        if(y[0]<0){
            running=false;
        }
        //check if head touches bottom border
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,100));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        //Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
        button.setVisible(true);
        button.addActionListener(this);
    }

    public void set(){
        random=new Random();
        startGame();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==go){
            go.setBackground(Color.green);
            jPanel.setVisible(false);
            set();
        }
        if(e.getSource()==square){
            shape ="square";
            square.setBackground(Color.green);
            oval.setBackground(Color.ORANGE);
        }
        if(e.getSource()==oval){
            shape="oval";
            oval.setBackground(Color.green);
            square.setBackground(Color.ORANGE);
        }
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
        if(e.getSource()==button){
            this.setVisible(false);
            new GameFrame();
        }
        if(e.getSource()==easy){
            easy.setBackground(Color.green);
            DELAY=200;
            medium.setBackground(Color.cyan);
            hard.setBackground(Color.red);
        }
        else if(e.getSource()==medium){
            medium.setBackground(Color.green);
            DELAY=100;
            easy.setBackground(Color.LIGHT_GRAY);
            hard.setBackground(Color.red);
        }
        else if(e.getSource()==hard){
            hard.setBackground(Color.green);
            DELAY=40;
            easy.setBackground(Color.LIGHT_GRAY);
            medium.setBackground(Color.cyan);
        }

    }
    public  class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:{
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT:{
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                }
                case KeyEvent.VK_UP:{
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                }
                case KeyEvent.VK_DOWN:{
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
                }
            }
        }
    }
}
