import java.awt.*;
public class Pipe {
    private int x;
    private int y;
    private int velocityX = 5;
    public static int WIDTH = 64;
    public static int HEIGHT = 512;
    private Image image;
    private boolean passed = false;

    public Pipe(int pipeX, int pipeY, Image image) {
        this.x = pipeX;
        this.y = pipeY;
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

    public boolean move(FlappyBird bird)
    {
        this.x -= velocityX;

        if (!this.passed && bird.getX() > this.x + WIDTH) {
            this.passed = true;
            return true;
        }

        return false;
    }
}
