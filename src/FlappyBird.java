import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    private int width = 360;
    private int height = 640;
    private Image bottomPipeImage;
    private Image topPipeImage;
    private Image flappyBirdImage;
    private Image flappyBirdBgImage;

    private int pipeX = width;
    private int pipeY = 0;
    private int pipeWidth = 64;
    private int pipeHeight = 512;

    private double score = 0;

    class Pipe {
        private int x = pipeX;
        private int y = pipeY;
        private int width = pipeWidth;
        private int height = pipeHeight;
        private Image image;
        private boolean passed = false;

        public Pipe(Image image)
        {
            this.image = image;
        }

    }

    private int birdX = width/8;
    private int birdY = height/2;
    private int birdWidth = 34;
    private int birdHeight = 24;
    
    class Bird {
        
        private int x = birdX;
        private int y = birdY;
        private int width = birdWidth;
        private int height = birdHeight;
        private Image image;
        
        public Bird(Image image)
        {
            this.image = image;
        }

    }

    private Bird bird;
    private int velocityY = 0;
    private int velocityX = 5;
    private int gravity = 1;
    private ArrayList<Pipe> pipes;
    private Timer gameLoop;
    private Timer placePipeLoop;
    private boolean gameOver = false;

    public FlappyBird()
    {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.BLACK);
        bottomPipeImage = new ImageIcon(getClass().getResource("resource\\bottompipe.png")).getImage();   
        topPipeImage = new ImageIcon(getClass().getResource("resource\\toppipe.png")).getImage();   
        flappyBirdImage = new ImageIcon(getClass().getResource("resource\\flappybird.png")).getImage();   
        flappyBirdBgImage = new ImageIcon(getClass().getResource("resource\\flappybirdbg.png")).getImage(); 
        
        bird = new Bird(flappyBirdImage);
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
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = height/6;
        
        Pipe topPipe = new Pipe(topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
        

        Pipe bottomPipe = new Pipe(bottomPipeImage);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
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
        g.drawImage(flappyBirdBgImage, 0, 0, width, height, null);
        g.drawImage(bird.image, bird.x, bird.y, bird.width, bird.height, null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipeHeight, null);
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
    
    public boolean collision(Bird b, Pipe p) 
    {
        return b.x < p.x + p.width && 
                b.x + b.width > p.x &&
                b.y < p.y + p.height &&
                b.y + b.height > p.y;
    }

    public void move()
    {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
        // bird.y = Math.min(bird.y, 550);
        
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x -= velocityX;
            
            if (!pipe.passed && bird.x > pipe.x + pipe.width)
            {
                pipe.passed = true;
                score += 0.5;
            }

            if (collision(bird, pipe))
            {
                gameOver = true;
            }

        }

        if (bird.y > height) {
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

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) 
        {
            velocityY = -9;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
