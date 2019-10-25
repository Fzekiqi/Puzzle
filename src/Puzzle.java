import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Puzzle extends JFrame {


    //    JButton[] buttons = new JButton[15];
    ArrayList<JButton> buttons = new ArrayList<>();
    ArrayList<JButton> finishedGame = new ArrayList<>();
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
        buttons.add(invisibleButton);
        finishedGame.add(invisibleButton);
    }


    public void initButtons(ArrayList<JButton> jbuttons) {

        for (int i = 0; i < 15; i++) {
            jbuttons.add(new JButton("" + (i + 1)));
            jbuttons.get(i).addActionListener(l);
        }
        shuffleButtons();
    }

    public void shuffleButtons() {
//        Collections.shuffle(buttons);
        for (int i = 0; i < buttons.size(); i++) {
            panel.add(buttons.get(i));
        }
    }

    ActionListener l = e -> {


        System.out.println("Before Changing");

        for(JButton button1 : buttons){
            System.out.print(button1.getText() + " ");
        }
        System.out.println();

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

                    Point temp = new Point(button.getLocation());
                    button.setLocation(invisibleButton.getLocation());
                    invisibleButton.setLocation(temp);


                    System.out.println("Change Place in list");

                    int indexOfButton = buttons.indexOf(button);
                    int indexOfInvisibleButton = buttons.indexOf(invisibleButton);

                    buttons.set(indexOfButton,invisibleButton);
                    buttons.set(indexOfInvisibleButton, button);

                    for(JButton button1 : buttons){
                        System.out.print(button1.getText() + " ");
                    }
                    System.out.println();
                    }

                break;
                }
            }
    };

    public static void main(String[] args) {
        new Puzzle();
    }
}