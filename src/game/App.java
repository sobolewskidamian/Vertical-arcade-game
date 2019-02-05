package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    static int width = 400;
    static int height = 600;
    private static Point initialClick;

    private static JFrame frame;
    private static JTextArea textArea;
    private static JLabel label;
    private static JButton button;

    public static void main(String[] args) {
        Keyboard keyboard = Keyboard.getInstance();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = createJFrame(new Point(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2));
        frame.setLayout(null);
        createNickSite();
        frame.setVisible(true);

        ButtonListener listener = new ButtonListener(textArea);
        button.addActionListener(listener);
        textArea.addKeyListener(keyboard);

        String nickFromListener;
        while ((nickFromListener = removeLineSeparator(listener.nick)).equals("")) {
            if (nickFromListener.equals("") && keyboard.isDown(KeyEvent.VK_ENTER))
                listener.nick = textArea.getText();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        frame.setVisible(false);

        frame = createJFrame(frame.getLocation());
        frame.addKeyListener(Keyboard.getInstance());

        GamePanel panel = new GamePanel();
        panel.game.nick = nickFromListener;
        panel.game.nickSet = true;
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void createNickSite() {
        textArea = createTextArea(width - 40, 28, 20, height / 2 + 20);
        label = createLabel(width - 40, 40, 20, height / 2 - 20, "Enter your nick: ");
        button = createButton(80, 35, width / 2 - 40, height / 2 + 60, "Play!");

        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(label);
        frame.add(textArea);
        frame.add(button);
    }

    private static JFrame createJFrame(Point point) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.dispose();
        frame.setUndecorated(true);
        frame.setSize(width, height);
        frame.setLocation(point);
        moveByMouse(frame);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        return frame;
    }

    private static JTextArea createTextArea(int width, int height, int xPos, int yPos) {
        JTextArea txt = new JTextArea();
        txt.setBackground(Color.WHITE);
        txt.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.LIGHT_GRAY));
        txt.setBounds(xPos, yPos, width, height);
        return txt;
    }

    private static JButton createButton(int width, int height, int xPos, int yPos, String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
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

    private static String removeLineSeparator(String str) {
        return str.replaceAll("\r", "").replaceAll("\n", "");
    }
}
