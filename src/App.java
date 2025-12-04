import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        // int width = 360;
        // int height = 640;

        JFrame jFrame = new JFrame("My Flappy Bird");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        
        FlappyBird flappyBird = new FlappyBird();
        jFrame.add(flappyBird);
        
        jFrame.pack();
        // jFrame.setSize(1000,1000);
        jFrame.setLocationRelativeTo(null);
        flappyBird.requestFocus();
        jFrame.setVisible(true);
        
    }
}
