package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 pixel tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48 pixel resolution

    //ratio is 4:3
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //48 * 16 = 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //48 * 12 = 576 pixels

    //WORLD PARAMS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    //TILEMANAGER INIT
    TileManager tileM = new TileManager(this);

    //KEYHANDLER AND THREAD INIT
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);

    //PLAYER INIT
    public Player player = new Player(this, keyH);

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
            //System.out.println("currentTime = " + currentTime);

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

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2); //draw Tile Manager draw() method. MUST BE DRAWN BEFORE PLAYER
        player.draw(g2); //draw player draw() method
        g2.dispose();
    }
}
