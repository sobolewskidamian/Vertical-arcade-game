package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    JTextArea a;
    String nick;
    String aText;

    public ButtonListener() {
        this.nick = "";
        this.aText = "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.nick = this.aText;
    }
}
