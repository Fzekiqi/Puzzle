import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.stream.Stream;


public class Puzzle extends JFrame {

    ArrayList<JButton> buttons = new ArrayList<>();
    JPanel panel = new JPanel();
    JButton invisibleButton = new JButton();
    JButton newGame=new JButton("New Game");

    Puzzle() {
        initJFrame();
    }

    public void initJFrame() {
        setTitle("15 Puzzle");
        initButtons();
        invisibleButton.setText("16");
        invisibleButton.setVisible(false);
        panel.setLayout(new GridLayout(4, 4));

        newGame.setVisible(false);
        newGame.addActionListener(startNewGame);
        add(newGame,BorderLayout.NORTH);
        add(panel);
        panel.add(invisibleButton);
        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttons.add(invisibleButton);
    }


    public void initButtons() {

        for (int i = 0; i < 15; i++) {

            buttons.add(new JButton("" + (i + 1)));
            buttons.get(i).addActionListener(l);

        }
        shuffleButtons();
    }

    public void shuffleButtons() {
    //  Collections.shuffle(buttons);
        for (int i = 0; i < buttons.size(); i++) {
            panel.add(buttons.get(i));
        }
        buttons.add(invisibleButton);
    }

    ActionListener l = e -> {

        for (int i = 0; i < buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)) {
                JButton button = buttons.get(i);

                if (
                        ((
                                (invisibleButton.getY() == button.getY()) &&
                                        button.getX() == invisibleButton.getX() - invisibleButton.getWidth() |
                                                button.getX() == invisibleButton.getX() + invisibleButton.getWidth())
                                ||
                                (invisibleButton.getX() == button.getX() &&
                                        invisibleButton.getY() + invisibleButton.getHeight() == button.getY() |
                                                invisibleButton.getY() - invisibleButton.getHeight() == button.getY())
                        )

                ) {

                    moveTiles(button);
                    isGameOver();
                }
                break;
            }
        }
    };

    public void moveTiles(JButton button) {
        Point temp = new Point(button.getLocation());
        button.setLocation(invisibleButton.getLocation());
        invisibleButton.setLocation(temp);

        int indexOfButton = buttons.indexOf(button);
        int indexOfInvisibleButton = buttons.indexOf(invisibleButton);

        buttons.set(indexOfButton, invisibleButton);
        buttons.set(indexOfInvisibleButton, button);
    }

    public void isGameOver() {
        if (
                Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16")
                        .allMatch((buttonNumber) -> buttonNumber.equals(buttons.get(Integer.parseInt(buttonNumber) - 1).getText()))
        ) {

            JOptionPane.showMessageDialog(null, "Du har vunnit Fazli!");
            newGame.setVisible(true);
        }
    }


    ActionListener startNewGame= e -> {
        for (JButton button : buttons ){
            buttons.remove(button);
            panel.remove(button);
        }

        getContentPane().remove(panel);
        getContentPane().remove(newGame);
        getContentPane().repaint();


        initJFrame();
    };

    public static void main(String[] args) {
        new Puzzle();
    }

}