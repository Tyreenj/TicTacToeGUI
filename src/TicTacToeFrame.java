import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI Frame for the Tic Tac Toe game.
 * Handles user interactions, game state, and display.
 */
public class TicTacToeFrame extends JFrame implements ActionListener {

    private static final int ROW = 3;
    private static final int COL = 3;
    private static String[][] board = new String[ROW][COL];

    private JPanel boardPanel;
    private TicTacToeButton[][] buttons = new TicTacToeButton[ROW][COL];
    private JLabel statusLabel;
    private JButton quitButton;
    private String currentPlayer = "X";
    private int moveCount = 0;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        clearBoard();

        // Board panel with 3x3 grid
        boardPanel = new JPanel(new GridLayout(ROW, COL));

        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                buttons[r][c] = new TicTacToeButton(r, c);
                buttons[r][c].addActionListener(this);
                boardPanel.add(buttons[r][c]);
            }
        }

        // Status bar + control buttons
        JPanel controlPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());

        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitGame());

        buttonPanel.add(resetButton);
        buttonPanel.add(quitButton);

        controlPanel.add(statusLabel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TicTacToeButton clicked = (TicTacToeButton) e.getSource();
        handleMove(clicked.getRow(), clicked.getCol());
    }

    private void handleMove(int row, int col) {
        // Check if space is already occupied (illegal move)
        if (!board[row][col].equals(" ")) {
            JOptionPane.showMessageDialog(this,
                    "That space is already occupied! Please choose another.",
                    "Illegal Move",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Make the move
        board[row][col] = currentPlayer;
        buttons[row][col].setText(currentPlayer);
        moveCount++;

        // Check for win (starting at move 5)
        if (moveCount >= 5 && isWin(currentPlayer)) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableBoard();
            JOptionPane.showMessageDialog(this,
                    "Player " + currentPlayer + " wins!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            promptPlayAgain();
            return;
        }

        // Check for tie (starting at move 7)
        if (moveCount >= 7 && isTie()) {
            statusLabel.setText("It's a tie!");
            disableBoard();

            // Check if board is full
            if (moveCount == 9) {
                JOptionPane.showMessageDialog(this,
                        "The game is a tie! (Board is full)",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "The game is a tie! (No more winning moves possible)",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            promptPlayAgain();
            return;
        }

        // Switch players
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private void promptPlayAgain() {
        int response = JOptionPane.showConfirmDialog(this,
                "Would you like to play another game?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            quitGame();
        }
    }

    private void quitGame() {
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void resetGame() {
        clearBoard();
        moveCount = 0;
        currentPlayer = "X";
        statusLabel.setText("Player X's turn");
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                buttons[r][c].setText(" ");
                buttons[r][c].setEnabled(true);
            }
        }
    }

    private void disableBoard() {
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                buttons[r][c].setEnabled(false);
            }
        }
    }

    private static void clearBoard() {
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                board[r][c] = " ";
            }
        }
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int r = 0; r < ROW; r++) {
            if (board[r][0].equals(player) &&
                    board[r][1].equals(player) &&
                    board[r][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int c = 0; c < COL; c++) {
            if (board[0][c].equals(player) &&
                    board[1][c].equals(player) &&
                    board[2][c].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        if (board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player)) {
            return true;
        }
        if (board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    private static boolean isTie() {
        // Simple tie check: board is full OR no winning moves possible
        // Check if board is full (all spaces occupied)
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                if (board[r][c].equals(" ")) {
                    // Board not full, check if there's still a winning possibility
                    return isNoWinPossible();
                }
            }
        }
        // Board is full and no winner (since we check win before tie)
        return true;
    }

    private static boolean isNoWinPossible() {
        // Check if each row, column, and diagonal has both X and O
        // If they do, no one can win on that line
        boolean xFlag, oFlag;

        // Rows
        for (int r = 0; r < ROW; r++) {
            xFlag = oFlag = false;
            for (int c = 0; c < COL; c++) {
                if (board[r][c].equals("X")) xFlag = true;
                if (board[r][c].equals("O")) oFlag = true;
            }
            if (!(xFlag && oFlag)) return false;
        }

        // Cols
        for (int c = 0; c < COL; c++) {
            xFlag = oFlag = false;
            for (int r = 0; r < ROW; r++) {
                if (board[r][c].equals("X")) xFlag = true;
                if (board[r][c].equals("O")) oFlag = true;
            }
            if (!(xFlag && oFlag)) return false;
        }

        // Diagonal (top-left to bottom-right)
        xFlag = oFlag = false;
        for (int i = 0; i < ROW; i++) {
            if (board[i][i].equals("X")) xFlag = true;
            if (board[i][i].equals("O")) oFlag = true;
        }
        if (!(xFlag && oFlag)) return false;

        // Diagonal (top-right to bottom-left)
        xFlag = oFlag = false;
        for (int i = 0; i < ROW; i++) {
            if (board[i][ROW - 1 - i].equals("X")) xFlag = true;
            if (board[i][ROW - 1 - i].equals("O")) oFlag = true;
        }
        if (!(xFlag && oFlag)) return false;

        return true;
    }
}