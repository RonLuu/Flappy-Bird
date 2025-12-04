import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBirdPanel extends JPanel implements ActionListener{
    private int width = 360;
    private int height = 640;
    private Image bottomPipeImage;
    private Image topPipeImage;
    private Image flappyFlappyBirdImage;
    private Image flappyFlappyBirdBgImage;
    private double score = 0;

    private FlappyBird bird;

    private ArrayList<Pipe> pipes;
    private Timer gameLoop;
    private Timer placePipeLoop;
    private boolean gameOver = false;

    public FlappyBirdPanel()
    {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setBackground(Color.BLACK);
        bottomPipeImage = new ImageIcon(getClass().getResource("resource\\bottompipe.png")).getImage();   
        topPipeImage = new ImageIcon(getClass().getResource("resource\\toppipe.png")).getImage();   
        flappyFlappyBirdImage = new ImageIcon(getClass().getResource("resource\\flappybird.png")).getImage();   
        flappyFlappyBirdBgImage = new ImageIcon(getClass().getResource("resource\\flappybirdbg.png")).getImage(); 
        
        bird = new FlappyBird(width/8, height/2, flappyFlappyBirdImage);
        addKeyListener(bird);

        pipes = new ArrayList<>();
        placePipeLoop = new Timer(1750, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                placePipe();
            }
        });
        placePipeLoop.start();
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void placePipe()
    {
        int randomPipeY = (int) (0 - Pipe.HEIGHT/4 - Math.random()*(Pipe.HEIGHT/2));
        int openingSpace = height/6;
        
        int topPipeY = randomPipeY;
        Pipe topPipe = new Pipe(width, topPipeY, topPipeImage);
        pipes.add(topPipe);
        
        int bottomPipeY = topPipeY + Pipe.HEIGHT + openingSpace;
        Pipe bottomPipe = new Pipe(width, bottomPipeY, bottomPipeImage);
        
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        // System.out.println(counter);
        g.drawImage(flappyFlappyBirdBgImage, 0, 0, width, height, null);
        // Should I make a draw method for flappy bird
        g.drawImage(bird.getImage(), bird.getX(), bird.getY(), FlappyBird.WIDTH, FlappyBird.HEIGHT, null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getX(), pipe.getY(), Pipe.WIDTH, Pipe.HEIGHT, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) 
        {
            g.drawString("Game Over: "+ String.valueOf((int)score), 10, 35);
        }
        else
        {
            g.drawString(String.valueOf((int)score), 10, 35);
        }
    }
    
    public boolean collision(FlappyBird b, Pipe p) 
    {
        return  b.getX() < p.getX() + Pipe.WIDTH && 
                b.getX() + FlappyBird.WIDTH > p.getX() &&
                b.getY() < p.getY() + Pipe.HEIGHT &&
                b.getY() + FlappyBird.HEIGHT > p.getY();
    }

    public void move()
    {
        this.bird.move();
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.move(bird))
            {
                score += 0.5;
            }

            if (collision(bird, pipe))
            {
                gameOver = true;
            }

        }

        if (bird.getY() > height) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver)
        {
            gameLoop.stop();
            placePipeLoop.stop();
        }
    }
}
