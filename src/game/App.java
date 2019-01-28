package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    static int width = 400;
    static int height = 600;
    private static Point initialClick;
    static String nick = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.dispose();
        frame.setUndecorated(true);
        frame.setSize(width, height);
        moveByMouse(frame);

        JTextArea a = createTextArea(width, 28, 0, height / 2 + 10);
        a.setFont(new Font("Arial", Font.PLAIN, 20));
        JButton b = createButton(100, 10, 0, 60, "Play!");
        b.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel label1 = new JLabel("Enter your nick: ");
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        label1.setVisible(true);

        frame.add(label1, BorderLayout.CENTER);
        frame.add(a, BorderLayout.AFTER_LAST_LINE);
        frame.add(b, BorderLayout.AFTER_LAST_LINE);

        frame.setVisible(true);

        GamePanel panel = new GamePanel();


        while (nick.equals("")) {
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nick = a.getText();
                }
            });
        }
        panel.game.nick = nick;
        panel.game.nickSet = true;
        a.setVisible(false);
        b.setVisible(false);
        label1.setVisible(false);
        frame.setVisible(false);

        frame.setVisible(true);

        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);
        frame.add(panel);
    }

    public static JTextArea createTextArea(int width, int height, int xPos, int yPos) {
        JTextArea txt = new JTextArea();
        txt.setVisible(true);
        txt.setBackground(Color.WHITE);
        txt.setBounds(xPos, yPos, width, height);
        return txt;
    }

    public static JButton createButton(int width, int height, int xPos, int yPos, String text) {
        JButton button = new JButton(text);
        button.setBounds(xPos, yPos, width, height);
        button.setVisible(true);
        return button;
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
