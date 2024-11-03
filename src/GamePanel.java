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

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; //1 billion divided by FPS (60) = 0.1666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            long currentTime = System.nanoTime(); //1 billion nanoseconds = 1 second
            System.out.println("currentTime = " + currentTime);

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); //returns time remaining until next drawTIme
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime+= drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //SECOND METHOD USING DELTA - DOES NOT BEHAVE RIGHT?
//    @Override
//    public void run() {
//        double drawInterval = 1000000000 / FPS;
//        double delta = 0;
//        long lastTime = System.nanoTime();
//        long currentTime;
//
//        //checking FPS
//        long timer = 0;
//        long drawCount = 0;
//
//        while (gameThread != null) {
//            currentTime = System.nanoTime();
//
//            delta += (currentTime - lastTime) / drawInterval;
//
//            timer += (currentTime = lastTime);
//
//            lastTime = currentTime;
//
//            if (delta >= 1) {
//                update();
//                repaint();
//
//                delta--;
//                drawCount++;
//            }
//
//            if (timer >= 1000000000) {
//                System.out.println("FPS:" + drawCount);
//                drawCount = 0;
//                timer = 0;
//            }
//        }
//    }

    public void update() {
        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed == true) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed == true) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
