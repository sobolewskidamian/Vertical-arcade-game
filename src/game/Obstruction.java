package game;

import java.awt.event.KeyEvent;

public abstract class Obstruction {
    protected double yvel;
    protected int delay;
    private double gravity;
    private Keyboard keyboard;
    protected int height;
    private Square square;
    protected int y;

    public Obstruction(Square square) {
        this.gravity = 0.5;
        this.keyboard = Keyboard.getInstance();
        this.height = 10;
        this.y = -height;
        this.square = square;
    }

    public abstract Render getRender();

    public abstract boolean collides(int x, int y, int width, int height);

    public void synchronizeWithOtherPipe(double yvel, int delay) {
        this.yvel = yvel;
        this.delay = delay;
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
}
