package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    private int restartDelay;
    private Keyboard keyboard;
    private ArrayList<Pipe> pipes;
    private ArrayList<Pipe> pipesUnderMiddle;
    private ArrayList<Pipe> pipesUnderMiddleMiddle;
    private ArrayList<Blocker> blockers;
    Square square;
    int score;
    int highScore;
    Boolean gameover;
    Boolean started;
    static String ranking;
    Boolean rankingDidntSet;
    Boolean nickSet;
    String nick;


    Game() {
        keyboard = Keyboard.getInstance();
        nickSet = false;
        nick = "";
        restart();
    }

    private void restart() {
        started = false;
        gameover = false;
        score = 0;
        restartDelay = 0;
        square = new Square();
        pipes = new ArrayList<>();
        pipesUnderMiddle = new ArrayList<>();
        pipesUnderMiddleMiddle = new ArrayList<>();
        blockers = new ArrayList<>();
        ranking = "Loading...";
        rankingDidntSet = true;
    }

    void update() {
        watchForStart();

        if (!started)
            return;

        watchForReset();

        this.square.update();

        if (gameover) {
            square.rotation = true;
            shake();
            return;
        }

        movePipes();
        checkForCollisions();
        setHighScore();
    }

    ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<>();
        for (Pipe pipe : pipes)
            renders.add(pipe.getRender());
        for (Blocker blocker : blockers)
            renders.add(blocker.getRender());
        renders.add(this.square.getRender());
        return renders;
    }

    private void watchForStart() {
        if (!started && nickSet && (keyboard.isDown(KeyEvent.VK_RIGHT) || keyboard.isDown(KeyEvent.VK_LEFT))) {
            started = true;
        }
    }

    private void watchForReset() {
        restartDelay++;
        if (keyboard.isDown(KeyEvent.VK_SPACE) && restartDelay > 40) {
            restart();
            restartDelay = 0;
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

        for (Blocker blocker : blockers) {
            if (blocker.y > App.height) {
                blockers.remove(blocker);
                break;
            }
        }

        if (blockerBool && pipesUnderMiddleMiddle.size() != 0) {
            addBolcker();
        }

        if (inMiddle || pipes.size() == 0) {
            addTwoPipes(Synyvel, Syndelay);
        }

        for (Pipe pipe : pipes) {
            pipe.update();
        }

        for (Blocker blocker : blockers) {
            blocker.update();
        }
    }

    private void addBolcker() {
        Blocker blocker;
        if (pipesUnderMiddleMiddle.size() != 0) {
            int index = pipesUnderMiddleMiddle.size() - 1;
            blocker = new Blocker(square, score, pipesUnderMiddleMiddle.get(index).width + pipesUnderMiddleMiddle.get(index).widthBeetweenTwoPipes / 2);
            blocker.synchronizeWithOtherPipe(pipesUnderMiddleMiddle.get(index).yvel, pipesUnderMiddleMiddle.get(index).delay);
        } else blocker = new Blocker(square, score);
        blockers.add(blocker);
    }

    private void addTwoPipes(double Synyvel, int Syndelay) {
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
        leftPipe.widthBeetweenTwoPipes = rightPipe.widthBeetweenTwoPipes;

        leftPipe.synchronizeWithOtherPipe(Synyvel, Syndelay);
        rightPipe.synchronizeWithOtherPipe(Synyvel, Syndelay);
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

    private void shake() {
        for (Pipe pipe : pipes) {
            pipe.shake();
        }

        for (Blocker blocker : blockers) {
            blocker.shake();
        }
    }

    void deleteAllPipesAndBlockers() {
        ArrayList<Pipe> toDeletePipes = new ArrayList<>(pipes);
        ArrayList<Blocker> toDeleteBlockers = new ArrayList<>(blockers);
        for (Pipe act : toDeletePipes)
            pipes.remove(act);
        for (Blocker act : toDeleteBlockers)
            blockers.remove(act);
    }

    private void setHighScore() {
        if (score > highScore)
            highScore = score;
    }
}
