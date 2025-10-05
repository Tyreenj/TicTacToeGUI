import javax.swing.*;
import java.awt.*;

/**
 * Custom JButton subclass for Tic Tac Toe game.
 * Holds the row and column position of the button on the board.
 */
public class TicTacToeButton extends JButton {

    private int row;
    private int col;

    /**
     * Constructor that sets the button's position on the board.
     * @param row The row position (0-2)
     * @param col The column position (0-2)
     */
    public TicTacToeButton(int row, int col) {
        super(" ");
        this.row = row;
        this.col = col;

        // Set default styling
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        setFocusPainted(false);
    }

    /**
     * Gets the row position of this button.
     * @return The row index (0-2)
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position of this button.
     * @return The column index (0-2)
     */
    public int getCol() {
        return col;
    }
}