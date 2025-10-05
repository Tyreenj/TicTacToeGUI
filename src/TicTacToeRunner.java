import javax.swing.*;

public class TicTacToeRunner {
    public static void main(String[] args) {
        // Create and show GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new TicTacToeFrame();
                frame.setVisible(true);
            }
        });
    }
}