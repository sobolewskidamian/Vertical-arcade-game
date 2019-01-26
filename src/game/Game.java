package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    private int restartDelay;

    private Square square;
    private Keyboard keyboard;
    private ArrayList<Pipe> pipes;
    private ArrayList<Pipe> pipesUnderMiddle;
    private ArrayList<Pipe> pipesUnderMiddleMiddle;
    private ArrayList<Blocker> blockers;

    public int score;
    public Boolean gameover;
    public Boolean started;

    public Game() {
        keyboard = Keyboard.getInstance();
        restart();
    }

    public void restart() {
        started = false;
        gameover = false;

        score = 0;
        restartDelay = 0;

        this.square = new Square();
        pipes = new ArrayList<>();
        pipesUnderMiddle = new ArrayList<>();
        pipesUnderMiddleMiddle = new ArrayList<>();
        blockers = new ArrayList<>();
    }

    public void update() {
        watchForStart();

        if (!started)
            return;

        watchForReset();

        this.square.update();

        if (gameover) {
            square.yvel = 0;
            square.xvel = 0;
            return;
        }

        movePipes();
        checkForCollisions();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<>();
        for (Pipe pipe : pipes)
            renders.add(pipe.getRender());
        for (Blocker blocker : blockers)
            renders.add(blocker.getRender());
        renders.add(this.square.getRender());
        return renders;
    }

    private void watchForStart() {
        if (!started && (keyboard.isDown(KeyEvent.VK_RIGHT) || keyboard.isDown(KeyEvent.VK_LEFT))) {
            started = true;
        }
    }

    private void watchForReset() {
        if (restartDelay > 0)
            restartDelay--;

        if (keyboard.isDown(KeyEvent.VK_SPACE) && restartDelay <= 0) {
            restart();
            restartDelay = 10;
            return;
        }
    }

    private void movePipes() {
        boolean inMiddle = false;
        double Synyvel = 0;
        int Syndelay = 0;
        boolean blockerBool = false;

        for (Pipe pipe : pipes) {
            if (pipe.y > App.height) {
                pipes.remove(pipe);
                pipesUnderMiddle.remove(pipe);
                if (pipe.orientation.equals("left"))
                    pipesUnderMiddleMiddle.remove(pipe);
                break;
            } else if (pipe.y >= App.height / 2 && !pipesUnderMiddle.contains(pipe)) {
                inMiddle = true;
                pipesUnderMiddle.add(pipe);
                Synyvel = pipe.yvel;
                Syndelay = pipe.delay;
                if (pipe.orientation.equals("left"))
                    score++;
            }
            if (pipe.y >= App.height / 4 && !pipesUnderMiddleMiddle.contains(pipe)) {
                if (pipe.orientation.equals("left")) {
                    pipesUnderMiddleMiddle.add(pipe);
                    blockerBool = true;
                }
            }
        }

        if ((blockerBool && pipesUnderMiddleMiddle.size() != 0)) {
            Blocker blocker = new Blocker(square);
            blockers.add(blocker);
            if (pipesUnderMiddleMiddle.size() != 0)
                blocker.synchronizeWithOtherPipe(pipesUnderMiddleMiddle.get(0).yvel, pipesUnderMiddleMiddle.get(0).delay);
        }

        if (inMiddle || pipes.size() == 0) {
            Pipe leftPipe;
            Pipe rightPipe;

            Pipe pipe = new Pipe("left", this.square, score);
            pipes.add(pipe);
            leftPipe = pipe;

            pipe = new Pipe("right", this.square, score);
            pipes.add(pipe);
            rightPipe = pipe;

            leftPipe.x = 0;
            leftPipe.width = App.width - rightPipe.width - rightPipe.widthBeetweenTwoPipes;

            leftPipe.synchronizeWithOtherPipe(Synyvel, Syndelay);
            rightPipe.synchronizeWithOtherPipe(Synyvel, Syndelay);
        }

        for (Pipe pipe : pipes) {
            pipe.update();
        }

        for (Blocker blocker : blockers) {
            blocker.update();
        }
    }

    private void checkForCollisions() {
        for (Pipe pipe : pipes) {
            if (pipe.collides(square.x, square.y, square.width, square.height)) {
                gameover = true;
                square.dead = true;
            }
        }

        for (Blocker blocker : blockers) {
            if (blocker.collides(square.x, square.y, square.width, square.height)) {
                gameover = true;
                square.dead = true;
            }
        }

        if (square.y >= App.height - square.height/* || square.x <= 0 || square.x + square.width >= App.width*/) {
            gameover = true;
            square.dead = true;
        }
    }
}
