package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    static int width = 400;
    static int height = 600;
    private static Point initialClick;

    static JFrame frame;
    static JTextArea a;
    static JLabel label1;
    static JButton b;

    public static void main(String[] args) {
        frame = createJFrame();
        frame.setLayout(null);
        createNickSite();
        frame.setVisible(true);

        ButtonListener listener = new ButtonListener();
        b.addActionListener(listener);

        String nickFromListener = listener.nick;
        while (nickFromListener.equals("")) {
            listener.aText = a.getText();
            nickFromListener = listener.nick;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        frame = createJFrame();

        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);

        GamePanel panel = new GamePanel();
        panel.game.nick = nickFromListener;
        panel.game.nickSet = true;
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void createNickSite() {
        a = createTextArea(width - 40, 28, 20, height / 2 + 20);
        label1 = createLabel(width - 40, 40, 20, height / 2 - 20, "Enter your nick: ");
        b = createButton(80, 35, width / 2 - 40, height / 2 + 60, "Play!");

        a.setFont(new Font("Arial", Font.PLAIN, 20));
        b.setFont(new Font("Arial", Font.PLAIN, 20));
        label1.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(label1);
        frame.add(a);
        frame.add(b);
    }

    private static JFrame createJFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.dispose();
        frame.setUndecorated(true);
        frame.setSize(width, height);
        moveByMouse(frame);
        return frame;
    }

    private static JTextArea createTextArea(int width, int height, int xPos, int yPos) {
        JTextArea txt = new JTextArea();
        txt.setBackground(Color.WHITE);
        txt.setBounds(xPos, yPos, width, height);
        return txt;
    }

    private static JButton createButton(int width, int height, int xPos, int yPos, String text) {
        JButton button = new JButton(text);
        button.setBounds(xPos, yPos, width, height);
        return button;
    }

    private static JLabel createLabel(int width, int height, int xPos, int yPos, String text) {
        JLabel label = new JLabel(text);
        label.setBounds(xPos, yPos, width, height);
        return label;
    }

    private static void moveByMouse(JFrame frame) {
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                frame.getComponentAt(initialClick);
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });
    }
}
