import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.math.RoundingMode;

public class Tile extends JButton {

    public Tile() {
    }

    public Tile(String text) {
        super(text);
        setGraphics();
    }

    public void setGraphics(){
        setFont(new Font("Chalkduster",Font.BOLD,40));
        setBorder(new LineBorder(Color.BLACK));
    }
}
