import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Puzzle extends JFrame {


    JButton[] buttons = new JButton[15];
    JButton[] finishedGame = new JButton[15];
    JPanel panel = new JPanel();
    JButton invisibleButton = new JButton();

    Puzzle() {
        initJFrame();
    }

    public void initJFrame() {
        initButtons(buttons);
        initButtons(finishedGame);
        invisibleButton.setVisible(false);
        panel.setLayout(new GridLayout(4, 4));
        add(panel);
        panel.add(invisibleButton);
        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void initButtons(JButton[] jButtons) {

        for (int i = 0; i < jButtons.length; i++) {
            jButtons[i] = new JButton("" + (i + 1));
            jButtons[i].addActionListener(l);
        }
        shuffleButtons();
    }

    public void shuffleButtons() {
        Collections.shuffle(Arrays.asList(buttons));
        for (int i = 0; i < buttons.length; i++) {
            panel.add(buttons[i]);
        }
    }

    ActionListener l = e -> {

        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                JButton button = buttons[i];

                if (
                    ((
                        invisibleButton.getY() == button.getY()) &&
                        button.getX() == invisibleButton.getX() - invisibleButton.getWidth() |
                            button.getX() == invisibleButton.getX() + invisibleButton.getWidth())
                ) {

                    Point temp = new Point(button.getLocation());
                    button.setLocation(invisibleButton.getLocation());
                    invisibleButton.setLocation(temp);

                } else if (
                    invisibleButton.getX() == button.getX() &&
                        invisibleButton.getY() + invisibleButton.getHeight() == button.getY() |
                            invisibleButton.getY() - invisibleButton.getHeight() == button.getY()) {

                    Point temp = new Point(button.getLocation());
                    button.setLocation(invisibleButton.getLocation());
                    invisibleButton.setLocation(temp);
                }

            }
        }

        isGameOver();
    };

    public void isGameOver() {


    }

    public static void main(String[] args) {
        new Puzzle();
    }

}