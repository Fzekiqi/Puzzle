import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Puzzle extends JFrame {


    JButton[] buttons = new JButton[15];
    JPanel panel = new JPanel();
    JButton unvis = new JButton();

    Puzzle() {
        iniButtons();
        panel.setLayout(new GridLayout(4, 4));

        add(panel);

        // unvis.setVisible(false);
        panel.add(unvis);
        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void iniButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("" + (i + 1));
            panel.add(buttons[i]);
            buttons[i].addActionListener(l);
        }
    }

    ActionListener l = e -> {

        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {

                if (
                        ((
                                unvis.getY() == buttons[i].getY()) &&
                                buttons[i].getX() == unvis.getX() - unvis.getWidth() |
                                        buttons[i].getX() == unvis.getX() + unvis.getWidth())
                ) {
                    Point temp = new Point(buttons[i].getLocation());
                    buttons[i].setLocation(unvis.getLocation());
                    unvis.setLocation(temp);

                } else if (
                        unvis.getX() == buttons[i].getX() &&
                                unvis.getY() + unvis.getHeight() == buttons[i].getY() |
                                        unvis.getY() - unvis.getHeight() == buttons[i].getY()) {


                    Point temp = new Point(buttons[i].getLocation());
                    buttons[i].setLocation(unvis.getLocation());
                    unvis.setLocation(temp);
                }
            }
        }
    };

    public static void main(String[] args) {
        new Puzzle();
    }

}