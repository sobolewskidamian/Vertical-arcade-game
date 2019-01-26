package game;

import java.awt.image.BufferedImage;

public class Blocker extends Obstruction {
    private Square square;

    public int x;
    public int y;
    public int width = 20;
    public int height = 20;

    private int score;

    public Blocker(Square square) {
        super(square);
        this.x = (int) (Math.random() * (App.width) - this.height);
    }

    public Blocker(Square square, int x) {
        super(square);
        this.x = x - width / 2;
    }

    public boolean collides(int x, int y, int width, int height) {
        if ((y + height >= this.y && y + height <= this.y + this.height) || (y >= this.y && y <= this.y + this.height))
            if ((x + height >= this.x && x + height <= this.x + this.height) || (x >= this.x && x <= this.x + this.height))
                return true;
        return false;
    }

    public void update() {
        super.update();
        y = super.y;
    }

    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        r.image = img;

        return r;
    }

    public void shake() {
        super.shake();
        y = super.y;
    }
}
