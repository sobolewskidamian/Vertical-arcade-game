package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    JTextArea textArea;
    String nick;
    String aText;

    public ButtonListener(JTextArea textArea) {
        this.nick = "";
        this.aText = "";
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.nick = textArea.getText();
    }
}
