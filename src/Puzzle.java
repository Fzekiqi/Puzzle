import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Iterator;

public class Puzzle extends JFrame {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<String> finishedGame = new ArrayList<>();
    private JPanel panel = new JPanel();
    private JButton invisibleButton = new JButton();
    private JButton newGame = new JButton("New Game");
    private int columns;
    private int rows;

    Puzzle() {
        getUserInput();
        initJFrame();
    }

    public void initJFrame() {
        initButtons();
        invisibleButton.setText(String.valueOf(buttons.size()));
        invisibleButton.setVisible(false);
        panel.setLayout(new GridLayout(rows, columns));

        newGame.addActionListener(startNewGame);
        add(newGame, BorderLayout.NORTH);
        add(panel);
        panel.add(invisibleButton);
        setTitle("15 Puzzle");
        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createList();
    }

    public void getUserInput() {
        try {
            columns = Integer.parseInt(JOptionPane.showInputDialog(null, "Hur många kolumner vill du ha?"));
            rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Hur många rader vill du ha?"));
        } catch (Exception e) {
            e.printStackTrace();
            //FIXME wfakwa
        }
    }

    public void initButtons() {
        for (int i = 0; i < columns * rows - 1; i++) {
            buttons.add(new JButton(String.valueOf(i + 1)));
            buttons.get(i).addActionListener(l);
        }
        shuffleButtons();
    }

    public void shuffleButtons() {
        Collections.shuffle(buttons);
        for (int i = 0; i < buttons.size(); i++) {
            panel.add(buttons.get(i));
        }
        buttons.add(invisibleButton);
    }

    ActionListener l = e -> {
        for (int i = 0; i < buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)) {
                JButton button = buttons.get(i);

                if (isButtonMovable(button)) {
                    moveButtons(button);
                    isGameOver();
                }
                break;
            }
        }
    };

    public boolean isButtonMovable(JButton button) {
        boolean isToTheRightOrLeft = (invisibleButton.getY() == button.getY()) &&
                button.getX() == invisibleButton.getX() - invisibleButton.getWidth() |
                        button.getX() == invisibleButton.getX() + invisibleButton.getWidth();

        boolean isOverOrUnder = (invisibleButton.getX() == button.getX() &&
                invisibleButton.getY() + invisibleButton.getHeight() == button.getY() |
                        invisibleButton.getY() - invisibleButton.getHeight() == button.getY());

        return (isToTheRightOrLeft || isOverOrUnder);
    }

    public void moveButtons(JButton button) {
        Point temp = new Point(button.getLocation());
        button.setLocation(invisibleButton.getLocation());
        invisibleButton.setLocation(temp);

        int indexOfButton = buttons.indexOf(button);
        int indexOfInvisibleButton = buttons.indexOf(invisibleButton);

        buttons.set(indexOfButton, invisibleButton);
        buttons.set(indexOfInvisibleButton, button);
    }

    public void isGameOver() {
        boolean isGameFinished = finishedGame.stream()
                .allMatch((buttonNumber) -> buttonNumber.equals(buttons.get(Integer.parseInt(buttonNumber) - 1).getText()));
        if (isGameFinished) {
            JOptionPane.showMessageDialog(null, "Du har vunnit!");
        }
    }

    ActionListener startNewGame = e -> {
        Iterator<JButton> iterator = buttons.iterator();

        while (iterator.hasNext()) {
            JButton button = iterator.next();
            iterator.remove();
            panel.remove(button);
        }

        initJFrame();
        invisibleButton.setVisible(true);
        invisibleButton.setVisible(false);
    };

    public void createList() {
        for (int i = 1; i <= buttons.size(); i++) {
            finishedGame.add(String.valueOf(i));
        }
    }

    public static void main(String[] args) {
        new Puzzle();
    }
}