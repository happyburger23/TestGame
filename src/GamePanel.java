import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 pixel tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale; //48 pixel resolution

    //ratio is 4:3
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol; //48 * 16 = 768 pixels
    final int screenHeight = tileSize * maxScreenRow; //48 * 12 = 576 pixels

    //THREAD STUFF
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

    }
}
