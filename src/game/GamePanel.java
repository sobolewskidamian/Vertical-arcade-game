package game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    Game game;

    public GamePanel() {
        this.game = new Game();
        new Thread(this).start();
    }

    private void update() {
        this.game.update();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        for (Render r : game.getRenders())
            if (r.transform != null) {
                g2D.drawImage(r.image, r.transform, null);
            } else {
                g.drawImage(r.image, r.x, r.y, null);
            }

        if (!game.started) {
            g2D.setFont(new Font("Arial", Font.PLAIN, 20));
            g2D.drawString("Press \"<\" or \">\" to start", 10, 30);
        } else if (!game.gameover || game.square.y <= App.height) {
            g2D.setFont(new Font("Arial", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(game.score), App.width - 60, 30);
        }

        if (game.gameover && game.square.y > App.height + game.square.height) {
            game.deleteAllPipesAndBlockers();
            g2D.setFont(new Font("Arial", Font.PLAIN, 20));
            g2D.drawString("Press Space to restart", 10, 30);
            g2D.drawString("Your score: " + game.score, 10, 200);
            g2D.drawString("High score: " + game.highScore, 10, 240);

            if(game.rankingDidntSet) {
                game.ranking = Ranking.parse(game.nick, game.score);
                game.rankingDidntSet = false;
            }
            if (!game.ranking.equals("")) {
                g2D.setFont(new Font("Arial", Font.BOLD, 13));
                g2D.drawString("Top 10:", 10, 300);
                g2D.setFont(new Font("Arial", Font.PLAIN, 13));
                drawString(g2D, game.ranking, 10, 310);
            }
        }
    }

    void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
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
