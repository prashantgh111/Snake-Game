
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener ,KeyListener {

        private int[] snakeXlength=new int[750];
        private int[] snakeYlength=new int[750];
        private int lengthofsnake=3;   // initial stage length is 3

        private int[]xPos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
        private int[]yPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600};

        private Random random=new Random();
        private int enemyX,enemyY;

        // at initial stage we set the direction of snake head is right other 3 directions we make it closed .
        private boolean left=false;
        private boolean right= true;
        private boolean up= false;
        private boolean down=false;
        private int moves=0;
        private int score=0;
        private boolean gameOver=false;
        // create ImageIcon for all images
        private ImageIcon snaketitle=new ImageIcon(getClass().getResource("snaketitle.jpg"));
        private ImageIcon leftmouth=new ImageIcon(getClass().getResource("leftmouth.png"));
        private ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.png"));
        private ImageIcon upmouth=new ImageIcon(getClass().getResource("upmouth.png"));
        private ImageIcon downmouth=new ImageIcon(getClass().getResource("downmouth.png"));
        private ImageIcon snakeimage=new ImageIcon(getClass().getResource("snakeimage.png"));
        private ImageIcon enemy=new ImageIcon(getClass().getResource("enemy.png"));


        // moves snake by every 100 ml seconds so for that i draw gamepanel every time & every time i change the position of snake
        //har 100 ml sec ke bad mai is game panel ko  draw/paint karunga  . so i need 2 variable
        private Timer timer;
        private int delay=100; // kitne ml sec ki delay karni hai aaapko; and implents Action Listener

        // create constructor of GamePanel class
        GamePanel(){
            addKeyListener(this);
           // i need to call two methods so keyLe]istner work on jpanel
            setFocusable(true);
            setFocusTraversalKeysEnabled(true);

        timer=new Timer(delay,this);
        timer.start();
        newEnemy();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            // draw border so first set color of graphic & then draw rectangle
            g.setColor(Color.white);
            g.drawRect(24, 15, 851, 55);
            // draw border for snake game part area
            g.drawRect(24, 75, 851, 551);

            // draw snake title into the border
           snaketitle.paintIcon(this,g,25,16);

           // snake game part area color
            g.setColor(Color.BLACK);
            g.fillRect(25,76,850,550); // 0ne value extra than drwarect so its fit in the border

            // to check initial stage or not
            if(moves==0){
                // for x position left side se
                snakeXlength[0]=100;  // 100 pixel
                snakeXlength[1]=75;
                snakeXlength[2]=50;

                // for y position from top
                snakeYlength[0]=100;  // 100 pixel for all .bcoz from top all are on same distance .
                snakeYlength[1]=100;
                snakeYlength[2]=100;
                // assign values;

            }
            // here we draw snake head direction wise
            if(left){
                leftmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
            }
            if(right){
                rightmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
            }
            if(up){
                upmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
            }
            if(down){
                downmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
            }

            // draw snake body part start i from 1 .bcoz at the first position i have already draw head means oth position
            for(int i=1;i<lengthofsnake;i++){
                // here we draw snake body which circle;
                snakeimage.paintIcon(this,g,snakeXlength[i],snakeYlength[i]);  // by getting  snake of size 3
            }
            enemy.paintIcon(this,g,enemyX,enemyY);

            // for game over
           if(gameOver==true){
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD,50));
                g.drawString("Game Over",300,300);

                g.setFont(new Font("Arial",Font.PLAIN,20));
                g.drawString("press SPACE to Restart",320,350);
           }

           g.setColor(Color.WHITE);
           g.setFont(new Font("Arial",Font.PLAIN,14));
           g.drawString("Score: "+score,750,30);
            g.drawString("length : "+lengthofsnake,750,50);

            // ab tak humne jitne bhi image add kiye wo sab dispose ho jayege
           g.dispose();
        }


    @Override
    public void actionPerformed(ActionEvent e) {
            // reverse loop
        for(int i=lengthofsnake-1;i>0;i--){
            // current wali position pe usse paheli wali position ki value add kar rahe hai
            snakeXlength[i]=snakeXlength[i-1];
            snakeYlength[i]=snakeYlength[i-1];
        }  // if i run this for loop then whole snake is properly move head with body .
        if(left){
            snakeXlength[0]=snakeXlength[0]-25;
        }
        if(right){
            snakeXlength[0]=snakeXlength[0]+25;
        }
        if(up){
            snakeYlength[0]=snakeYlength[0]-25;
        }
        if(down){
            snakeYlength[0]=snakeYlength[0]+25;
        }
     /*here we set condition to for snake to dont't cross the border ..jo hamne border banaya tha snake game part area ke liye uske andar hi wo
         rahena chahiye .. if snake goes right then its comes from left and if it goes left its come from right same for up ans down.
        */
        if(snakeXlength[0]>850) snakeXlength[0]=25;  // left se niklege
        if(snakeXlength[0]<25) snakeXlength[0]=850;  // right se niklaega
        if(snakeYlength[0]>600) snakeYlength[0]=75;  // up se niklege  // snake border part = width=851 , hight=551 600 badi leni hogi
        if(snakeYlength[0]<75) snakeYlength[0]=600;  // down se niklega

        // ab arrow ki help se snake ki direction change karne ke liye KeyListener Implements karna padega .line no 11 and implements all methods.

        collidesWithEnemy();  //chek collision with enemy
        collidesWithBody();   //check collision with body
       repaint(); // after i run  this just head is moving not the body part of snake. so muje head ke pahle jo circle hai usko head ka position
        //batana padge & uske piche wale ko current ka . so hum reverse loop lagayenge
    }


    @Override
    public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                restart();
        }
            if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){  // goes only left direction if u pressed right key not work
                left=true;
                right=false;
                up=false;
                down=false;
                moves++;     // jab main  run karunga to initial stage pe ye ruka rahega . and when i pressed any key then snake is move;
            }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){  // goes only right direction if u pressed left key not work
            left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){  //goes only up direction if u pressed down key not work
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){  // goes only down direction if u pressed up key not work
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        } // if run this it not run bcoz first i need to add keyListner into my constructor GamePanel(){} and i need to call
        // two methods so keyLe]istner work on jpanel. and after i run this key is work. but snake agar right me ja raha hai to left me nhi jana
        // chahiye agar aap run karoge to right key press goes right and press left key goes left . so ek sath left ya right me nhi ja sakte.
        // so opposite direction me nhi jana chahiye. to condition lagayenge.so restriction lagani hogi.

    }

    @Override
    public void keyReleased(KeyEvent e){

    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    private void newEnemy() {
        enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(22)];

        // if enemy lie on snake body part regain set the random position of enemy ;
        for(int i=lengthofsnake-1;i>=0;i--){
            if(snakeXlength[i]==enemyX && snakeYlength[i]==enemyY){
                newEnemy();
            }

        }
    }
    private void collidesWithEnemy() {
        if(snakeXlength[0]==enemyX && snakeYlength[0]==enemyY){
            //newEnemy();
            lengthofsnake++;
            newEnemy();
            score++;
        }
    }
   private void collidesWithBody() {
            for(int i=lengthofsnake-1;i>0;i--){  // i>=0 nhi lena hai timer stop hoga dry karke dekho.
                if(snakeXlength[i]==snakeXlength[0] && snakeYlength[i]==snakeYlength[0]){
                    timer.stop();
                    gameOver=true;
                }
            }
    }
    private void restart(){
            gameOver=false;
            moves=0;
            score=0;
            lengthofsnake=3;
            left=false;
            right=true;
            up=false;
            down=false;

            timer.start();
            repaint();
    }
}




