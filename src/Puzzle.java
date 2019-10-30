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

    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<String> finishedGame = new ArrayList<>();
    private JPanel panel = new JPanel();
    private Tile invisibleTile = new Tile();
    private JButton newGameButton = new JButton("New Game");
    private int columns;
    private int rows;

    Puzzle() {
        getUserInput();
        initJFrame();
    }//CONSTRUCTOR

    public void initJFrame() {
        initTiles();
        invisibleTile.setText(String.valueOf(tiles.size()));
        invisibleTile.setVisible(false);
        panel.setLayout(new GridLayout(rows, columns));
        newGameButton.addActionListener(startNewGameListener);
        add(newGameButton, BorderLayout.NORTH);
        add(panel);
        panel.add(invisibleTile);
        setTitle("15 Puzzle");
        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createFinishedGameList();
    }//initJFrame

    public void getUserInput() {

        rows = toInt("How many rows would you like to have?");
        columns = toInt("How many columns would you like to have");
    }//getUserInput

    public int toInt(String message) {

        int inputToInt = 0;
        String input = "";
        while (true) {
            try {
                input = JOptionPane.showInputDialog(null, message);
                if (input == null) {
                    System.exit(0);
                }
                inputToInt = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "You have to type a number.");
                continue;
            }
            if (inputToInt < 3) {
                JOptionPane.showMessageDialog(null,
                        "The number has to be above 2, you entered " + inputToInt);
                continue;
            }
            return inputToInt;
        }
    }//toInt

    public void initTiles() {

        for (int i = 0; i < columns * rows - 1; i++) {
            tiles.add(new Tile(String.valueOf(i + 1)));
            tiles.get(i).addActionListener(clickedTile);
        }
        shuffleTiles();
    }//initTiles

    public void shuffleTiles() {
        Collections.shuffle(tiles);
        addTilesOnThePanel();
        tiles.add(invisibleTile);
    }//shuffleTiles

    private void addTilesOnThePanel() {
        for (int i = 0; i < tiles.size(); i++) {
            panel.add(tiles.get(i));
        }
    }//addTilesOnThePanel

    ActionListener clickedTile = e -> {
        Tile tile = (Tile) e.getSource();

        if (isTileMovable(tile)) {
            moveTile(tile);
            isGameOver();
        }
    };//clickedTile

    public boolean isTileMovable(Tile tile) {

        boolean onTheSameRow = (invisibleTile.getY() == tile.getY()),
                onTheLeft = tile.getX() == invisibleTile.getX() - invisibleTile.getWidth(),
                onTheRight = tile.getX() == invisibleTile.getX() + invisibleTile.getWidth();

        boolean onTheSameColumn = invisibleTile.getX() == tile.getX(),
                isOver = invisibleTile.getY() + invisibleTile.getHeight() == tile.getY(),
                isUnder = invisibleTile.getY() - invisibleTile.getHeight() == tile.getY();

        boolean isToTheRightOrLeft = onTheSameRow && onTheLeft | onTheRight;
        boolean isOverOrUnder = onTheSameColumn && isOver | isUnder;

        return (isToTheRightOrLeft || isOverOrUnder);
    }//isTileMovable

    public void moveTile(Tile tile) {
        Point tempTilePoint = new Point(tile.getLocation());
        tile.setLocation(invisibleTile.getLocation());
        invisibleTile.setLocation(tempTilePoint);

        int indexOfTile = tiles.indexOf(tile);
        int indexOfInvisibleTile = tiles.indexOf(invisibleTile);

        tiles.set(indexOfTile, invisibleTile);
        tiles.set(indexOfInvisibleTile, tile);
    }//moveTile

    public void isGameOver() {

        boolean isGameFinished = finishedGame.stream()
                .allMatch((tileNumber) -> tileNumber
                        .equals(tiles.get(Integer.parseInt(tileNumber) - 1).getText()));
        if (isGameFinished) {
            int x = JOptionPane.showConfirmDialog(null, "You won!\nWould you like to play again?");
            if(x != 0){
                System.exit(0);
            }
            startNewGame();
        }
    }//isGameOver

    ActionListener startNewGameListener = e -> {
        startNewGame();
    };//startNewGameListener

    public void startNewGame(){
        emptyTheTilesList();
        initJFrame();
        invisibleTile.setVisible(true);
        invisibleTile.setVisible(false);
    }//startNewGame



    private void emptyTheTilesList() {

        Iterator<Tile> iterator = tiles.iterator();
        while (iterator.hasNext()) {
            Tile tile = iterator.next();
            iterator.remove();
            panel.remove(tile);
        }
    }//emptyTheTileList

    public void createFinishedGameList() {

        for (int i = 1; i <= tiles.size(); i++) {
            finishedGame.add(String.valueOf(i));
        }
    }//createFinishedGameList

    public static void main(String[] args) {
        new Puzzle();
    }
}