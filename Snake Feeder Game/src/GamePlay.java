import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;


// fruit khilane pr tail ka ek block beech mein randomly appear hokr gayab ho rha .. kyu aa rha vo tail vhan 100 mili seconds (which is the delay time) ke liye
public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private ImageIcon titleImage;
    private ImageIcon headRight;
    private ImageIcon headLeft;
    private ImageIcon headUp;
    private ImageIcon headDown;
    private ImageIcon tail;
    private ImageIcon fruitImage;

    private Timer timer;
    private int delay = 100;


    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];


    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    private int lengthOfSnake = 3;

    private int[] fruitXpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600,
            625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] fruitYpos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    private Random random = new Random();
    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);


    private int moves = 0;
    private int scores = 0;


    public GamePlay(){

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer (delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // Display title
        titleImage = new ImageIcon("title.png");
        titleImage.paintIcon(this, g, 24, 5);

        // Display gameplay Border
        g.setColor(Color.DARK_GRAY);
        g.drawRect(24, 74, 851, 577 );

        // Display gameplay Background
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);

        //Display score and length
        g.setColor(Color.WHITE);
        g.setFont(new Font("aereal", Font.PLAIN, 15));
        g.drawString("Scores: " + scores, 700, 35);

        g.drawString("Length: " + lengthOfSnake, 700, 55);


        if(moves == 0){
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
        }


        //Initial position of head image
        headRight = new ImageIcon("headRight.png");
        headRight.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);


        for(int i = 0; i<lengthOfSnake; i++){

            if(i==0 && right){
                headRight.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
            }
            if(i==0 && left){
                headLeft = new ImageIcon("headLeft.png");
                headLeft.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
            }
            if(i==0 && up){
                headUp = new ImageIcon("headUP.png");
                headUp.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
            }
            if(i==0 && down){
                headDown = new ImageIcon("headDown.png");
                headDown.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
            }

            if(i!=0){
                tail = new ImageIcon("tail.png");
                tail.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }

            fruitImage = new ImageIcon("fruit.png");

            if( fruitXpos[xpos] == snakeXlength[0] && fruitYpos[ypos] == snakeYlength[0]){

                scores += 5;
                lengthOfSnake++;

                xpos = random.nextInt(34);
                ypos = random.nextInt(23);

            }
            fruitImage.paintIcon(this, g, fruitXpos[xpos], fruitYpos[ypos]);// if this line is being executed for each value of i,
            // toh fir toh bhaut saare fruits ho jayenge na .. matlab purane vale fruit ko gayab krne ke liye toh humne kuchh likha hi nhi na
            // pr aeisa isliye nhi ho rha kyuki xpos aur ypos toh same hi hain na hrr i ke liye jabtk ki fruit aur head ki same position vali if statement ture nhi ho jati

        }


        for(int i = 1; i<lengthOfSnake; i++){ // yhan pr notice kro - i ki value 1 se start kri hai.. jis se tail aur head ki position same hone pr game over ho..
                                              // nhi toh head aur head ki position same hone se hi game over ho jata.. hehe

            if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]){

                right = false;
                left = false;
                up = false;
                down = false;

                g.setColor(Color.RED);
                g.setFont(new Font("aereal", Font.BOLD, 40));
                g.drawString("Game Over! Scores: " + scores, 250, 300 );

                g.setColor(Color.WHITE);
                g.setFont(new Font("aereal", Font.BOLD, 20));
                g.drawString("Press Enter to restart", 350, 340);

            }


        }

        g.dispose(); // what does g.dispose() do.. kyuki isse comment out krne se koi frk hi nhi padd rha

    }

    public void keyPressed(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            moves = 0;
            scores = 0;
            lengthOfSnake = 3;
            repaint(); // again agr yhan pr bhi repaint nhi krenge toh vhi hoga, game restart ho gya hoga pr dikhega nhi..
            // jab arrow keys se snake move krogi toh direct game chalta hua dikhega

        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moves++;
            if(!left){
                right = true;
            }else{
                right  = false;
               // left = true;
            }

            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moves++;
            if(!right){
                left = true;
            }else{
                left = false;
                //right = true;
            }

            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            moves++;
            if(!down){
                up = true;
            }else{
                up = false;
                //down = true;
            }

            right = false;
            left = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            moves++;
            if(!up){
                down = true;
            }else{
                down = false;
               // up = true;
            }

            right = false;
            left = false;
        }

    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
    public void actionPerformed(ActionEvent e){


        timer.restart();

        if(right){
            for(int n = lengthOfSnake-1; n>=0; n--){
                snakeYlength[n+1] = snakeYlength[n]; // try by modifying this line
            }
            for(int n = lengthOfSnake-1; n>=0; n--){
                if(n==0){
                    snakeXlength[n] = snakeXlength[n] + 25;
                }else{
                    snakeXlength[n] = snakeXlength[n-1];
                }

                if(snakeXlength[n] > 850){
                    snakeXlength[n] = 25;
                }
            }

            repaint();// agr repaint hta denge toh saanp chalega toh pr chalta hua dikhega nhi
        }

        if(left){
            for(int n = lengthOfSnake - 1; n >=0; n--){
                snakeYlength[n+1] = snakeYlength[n];
            }
            for(int n = lengthOfSnake-1; n>=0; n--){
                if(n==0){
                    snakeXlength[n] = snakeXlength[n] -25;
                }else{
                    snakeXlength[n] = snakeXlength[n-1];
                }

                if(snakeXlength[n] <25){
                    snakeXlength[n] = 850;
                }
            }

            repaint();
        }

        if(up){
            for(int n = lengthOfSnake-1; n>=0; n--){
                snakeXlength[n+1] = snakeXlength[n];
            }
            for(int n = lengthOfSnake-1; n>=0; n--){
                if(n==0){
                    snakeYlength[n] = snakeYlength[n] - 25;
                }else{
                    snakeYlength[n] = snakeYlength[n-1];
                }

                if(snakeYlength[n] < 75){
                    snakeYlength[n] = 625;
                }
            }

            repaint();
        }

        if(down){
            for(int n= lengthOfSnake-1; n>=0; n--){
                snakeXlength[n+1] = snakeXlength[n];
            }
            for(int n= lengthOfSnake-1; n>=0; n--){
                if(n==0){
                    snakeYlength[n] = snakeYlength[n] + 25;
                }else{
                    snakeYlength[n] = snakeYlength[n-1];
                }

                if(snakeYlength[n] > 625){
                    snakeYlength[n] = 75;
                }
            }
            repaint();
        }

    }
}
