import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Game extends Canvas implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// variables
    private Thread thread;
    private boolean isRunning = true;
    private final int WIDTH = 240;
    private final int HEIGHT = 160;
    private final int SCALE = 3;

    private BufferedImage image;

    // windows maker
    public static JFrame frame;

    // imports
    private SpriteSheet sheet;
    private BufferedImage player;

    // constructor method
    public Game(){
        sheet = new SpriteSheet("/spriteSheet.png");
        player = sheet.getSprite(0, 0, 16, 16);

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(160, 120, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.start();
    }

    public void initFrame() {
        frame = new JFrame("Game #01"); // game title
        frame.add(this);
        frame.setResizable(false); // to not resize the window
        frame.pack(); //calcule dimensions  
        frame.setLocationRelativeTo(null); // to start window on center
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close java process on close window
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // geme logics 
    public void tick() {

    }

    // renders graphics 
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null ) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        g.setColor(new Color(19, 19, 19));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        /* rendering game */
        g.drawImage(player, 20, 20, null);
        /* end rendering game */

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bs.show();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        long lastTime = System.nanoTime(); //actual time in nano seconds
        double amountOfTicks = 60.0; // clocks quantities
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        // checking if is running at 60 frames por seconds
        int frames = 0;
        double timer = System.currentTimeMillis();

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;

            // checking if is running at 60 frames por seconds
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                frames ++;
                delta--;
            }

            // checking if is running at 60 frames por seconds
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }

        }

        stop();
    }

}
