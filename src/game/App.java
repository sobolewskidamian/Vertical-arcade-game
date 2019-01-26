package game;

import javax.swing.*;

public class App {
    static int width = 400;
    static int height = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width + 16, height + 39);


        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);

        GamePanel panel = new GamePanel();
        frame.add(panel);
    }
}
