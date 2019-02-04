package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Pipe extends Obstruction {
    int width;
    int widthBeetweenTwoPipes;
    int x;
    int y;
    String orientation;
    private int score;
    private Color color;

    Pipe(String orientation, Square square, int score) {
        super(square);
        this.orientation = orientation;
        this.score = score;
        this.y = -super.height;
        reset();
        setColor();
    }

    private void reset() {
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

    void update() {
        super.update();
        y = super.y;
    }

    boolean collides(int x, int y, int width, int height) {
        if (y + height >= this.y && y <= this.y + this.height)
            return (orientation.equals("left") && x <= this.width) || (orientation.equals("right") && x + width >= this.x);
        return false;
    }

    private void setColor() {
        switch ((score / 5) % 5) {
            case 0:
                color = new Color(255, 14, 24);
                break;
            case 1:
                color = new Color(46, 78, 255);
                break;
            case 2:
                color = new Color(255, 66, 188);
                break;
            case 3:
                color = new Color(135, 255, 90);
                break;
            default:
                color = new Color(255, 126, 42);
                break;
        }
    }

    Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        if (this.width <= 0)
            this.width = 1;

        BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                img.setRGB(i, j, color.getRGB());

        r.image = img;

        return r;
    }

    void shake() {
        super.shake();
        y = super.y;
    }
}
