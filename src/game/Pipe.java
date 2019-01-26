package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Pipe extends Obstruction {
    private Square square;

    public int width;
    public int widthBeetweenTwoPipes;

    public int x;
    public int y = -height;

    public String orientation;

    private Keyboard keyboard;
    private int score;

    public Pipe(String orientation, Square square, int score) {
        super(square);
        this.orientation = orientation;
        this.keyboard = Keyboard.getInstance();
        this.square = square;
        this.score = score;
        reset();
    }

    public void reset() {
        y = -height;
        if (orientation.equals("right")) {
            int range;
            if (score <= 10) {
                range = 70;
            } else if (score <= 20)
                range = 35;
            else
                range = 0;
            widthBeetweenTwoPipes = (int) (Math.random() * 49) + (100 + range);
            x = (int) (Math.random() * (App.width - widthBeetweenTwoPipes)) + widthBeetweenTwoPipes;
            if (x >= App.width)
                x = App.width - 1;
            width = App.width - x;
        }
    }

    public void update() {
        super.update();
        y = super.y;
    }

    public boolean collides(int x, int y, int width, int height) {
        if (y + height >= this.y && y <= this.y + this.height) {
            if ((orientation.equals("left") && x <= this.width) || (orientation.equals("right") && x + width >= this.x))
                return true;
        }
        return false;
    }

    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        if (this.width <= 0)
            this.width = 1;

        BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        r.image = img;

        r.color = Color.CYAN;

        return r;
    }

    public void shake() {
        super.shake();
        y = super.y;
    }
}
