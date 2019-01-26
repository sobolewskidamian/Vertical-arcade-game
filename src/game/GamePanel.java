package game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private Game game;

    public GamePanel() {
        this.game = new Game();
        new Thread(this).start();
    }

    public void update() {
        this.game.update();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        for (Render r : game.getRenders())
            if (r.transform != null) {
                g2D.drawImage(r.image, r.transform, null);
            }
            else {
                g.drawImage(r.image, r.x, r.y, null);
            }

        if (!game.started) {
            g2D.setFont(new Font("Arial", Font.PLAIN, 20));
            g2D.drawString("Press \"<\" or \">\" to start", 10, 30);
        } else {
            g2D.setFont(new Font("Arial", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(game.score), App.width - 60, 30);
        }

        if (game.gameover) {
            g2D.setFont(new Font("Arial", Font.PLAIN, 20));
            g2D.drawString("Press Space to restart", 10, 30);
        }
    }

    public void run() {
        try {
            while (true) {
                update();
                Thread.sleep(15);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
