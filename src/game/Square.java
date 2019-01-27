package game;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Square {
    int x;
    int y;
    int width;
    int height;
    boolean dead;
    boolean rotation;
    private double yvel;
    private double xvel;
    private double xvalue;
    private double gravity;
    private int rotationInt;
    private int jumpDelay;
    private Keyboard keyboard;

    public Square() {
        yvel = 0;
        xvel = 0;
        xvalue = 3;
        width = 42;
        height = 42;
        x = App.width / 2 - width / 2;
        y = App.height / 2 - height / 2;
        gravity = 0.5;
        jumpDelay = 0;
        dead = false;
        rotation = false;
        rotationInt = 0;
        keyboard = Keyboard.getInstance();
    }

    void update() {
        yvel += gravity;

        if (jumpDelay > 0)
            jumpDelay--;

        if (!dead && jumpDelay <= 0 && (keyboard.isDown(KeyEvent.VK_LEFT) || keyboard.isDown(KeyEvent.VK_RIGHT))) {
            yvel = -10;
            jumpDelay = 10;
            if (keyboard.isDown(KeyEvent.VK_LEFT))
                xvel = -this.xvalue;
            else if (keyboard.isDown(KeyEvent.VK_RIGHT))
                xvel = this.xvalue;
        }

        if (yvel > 0 || y > App.height / 2 - height / 2)
            y += (int) yvel;

        x += (int) xvel;

        if (x >= App.width)
            x = -width + 1;
        else if (x <= -width)
            x = App.width - 1;
    }

    Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;
        r.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        r.transform = new AffineTransform();
        r.transform.translate(x + width / 2, y + height / 2);
        r.transform.translate(-width / 2, -height / 2);
        if (rotation) {
            rotationInt++;
            r.transform.rotate(rotationInt / 50.0);
        }
        return r;
    }
}
