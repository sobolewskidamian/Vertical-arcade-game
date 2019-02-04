package game;

import java.awt.image.BufferedImage;

public class Blocker extends Obstruction {
    private int x;
    int y;
    private int width = 20;
    private int height = 20;

    private int score;
    private boolean leftDirection;

    Blocker(Square square, int score) {
        super(square);
        this.x = (int) (Math.random() * (App.width) - this.height);
        this.score = score;
        randomBool();
    }

    Blocker(Square square, int score, int x) {
        super(square);
        this.x = x;
        this.score = score;
        randomBool();
    }

    private void randomBool() {
        double random = Math.random();
        leftDirection = random < 0.5;
    }

    boolean collides(int x, int y, int width, int height) {
        if ((y + height >= this.y && y + height <= this.y + this.height) || (y >= this.y && y <= this.y + this.height) || (this.y >= y && this.y + this.height <= y + height))
            return (x + height >= this.x && x + height <= this.x + this.height) || (x >= this.x && x <= this.x + this.height) || (this.x >= x && this.x + this.width <= x + width);
        return false;
    }

    void update() {
        super.update();
        updateX();
        y = super.y;
    }

    private void updateX(){
        int constant = 0;
        if (score >= 40)
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

    Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;
        r.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        return r;
    }

    void shake() {
        super.shake();
        y = super.y;
    }
}
