package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Pipe {
    private Square square;

    public int x;
    public int y;
    public int width;
    public int height = 10;
    public int widthBeetweenTwoPipes;

    public double yvel = 0;
    public double gravity = 0.5;
    public int delay;

    public String orientation;

    private Image image;
    private Keyboard keyboard;
    private int score;

    public Pipe(String orientation, Square square, int score) {
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
        yvel += gravity;

        if (delay > 0)
            delay--;

        if (delay <= 0 && (keyboard.isDown(KeyEvent.VK_LEFT) || keyboard.isDown(KeyEvent.VK_RIGHT))) {
            yvel = -10;
            delay = 10;
        }

        if (yvel < 0 && this.square.y <= App.height / 2 - height / 2)
            y -= (int) yvel;
    }

    public void synchronizeWithOtherPipe(double yvel, int delay) {
        this.yvel = yvel;
        this.delay = delay;
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

        return r;
    }
}
