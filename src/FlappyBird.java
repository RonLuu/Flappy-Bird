import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlappyBird implements KeyListener{
    
    static int WIDTH = 34;
    static int HEIGHT = 24;
    private int x;
    private int y;
    private int velocityY = 0;
    private int gravity = 1;
    private Image image;

    public FlappyBird(int birdX, int birdY, Image image)
    {
        this.x = birdX;
        this.y = birdY;
        this.image = image;
    }

    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }

    
    public Image getImage()
    {
        return this.image;
    }

    public void move()
    {
        velocityY += gravity;
        this.y += velocityY;
        this.y = Math.max(this.y, 0);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
