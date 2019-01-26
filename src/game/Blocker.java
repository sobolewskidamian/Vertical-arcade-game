package game;

import java.awt.image.BufferedImage;

public class Blocker extends Obstruction {
    private Square square;

    public int x;
    public int y;
    public int width = 20;
    public int height = 20;

    private int score;
    private boolean leftDirection;

    public Blocker(Square square, int score) {
        super(square);
        this.x = (int) (Math.random() * (App.width) - this.height);
        this.score = score;
        randomBool();
    }

    public Blocker(Square square, int score, int x) {
        super(square);
        this.x = x;
        this.score = score;
        randomBool();
    }

    private void randomBool() {
        double random = Math.random();
        if (random < 0.5)
            leftDirection = true;
        else
            leftDirection = false;
    }

    public boolean collides(int x, int y, int width, int height) {
        if ((y + height >= this.y && y + height <= this.y + this.height) || (y >= this.y && y <= this.y + this.height) || (this.y >= y && this.y + this.height <= y + height))
            if ((x + height >= this.x && x + height <= this.x + this.height) || (x >= this.x && x <= this.x + this.height) || (this.x >= x && this.x + this.width <= x + width))
                return true;
        return false;
    }

    public void update() {
        super.update();
        y = super.y;

        int constant = 0;
        if (score >= 0)
            constant = 3;
        else if (score >= 20)
            constant = 2;
        else if (score >= 10)
            constant = 1;

        if (leftDirection)
            constant *= -1;

        x += constant;
        if (x <= 0 || x >= App.width - width)
            leftDirection = !leftDirection;
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
