package game;

import java.awt.event.KeyEvent;

public abstract class Obstruction {
    double yvel;
    int delay;
    int height;
    int y;
    private Keyboard keyboard;
    private Square square;
    private double gravity;
    private boolean shake;
    private int deltaShake;
    private int amountOfShakes;

    Obstruction(Square square) {
        this.gravity = 0.5;
        this.keyboard = Keyboard.getInstance();
        this.height = 10;
        this.y = -height;
        this.square = square;
        this.shake = true;
        this.deltaShake = 0;
        this.amountOfShakes = 0;
    }

    abstract Render getRender();

    abstract boolean collides(int x, int y, int width, int height);

    void synchronizeWithOtherPipe(double yvel, int delay) {
        this.yvel = yvel;
        this.delay = delay;
    }

    void update() {
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

    void shake() {
        if (amountOfShakes < 4 * 2 * 6) {
            deltaShake += 1;

            if (deltaShake == 4) {
                deltaShake = 0;
                shake = !shake;
            }

            if (shake)
                y += deltaShake;
            else
                y -= deltaShake;

            amountOfShakes++;
        }
    }
}
