import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TTTFrame extends JFrame {

    private TTTGame game;
    private TicTacToeTile[][] tiles;
    private JLabel statusLabel;

    public TTTFrame() {
        game = new TTTGame();
        tiles = new TicTacToeTile[3][3];

        setTitle("Tic Tac Toe");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        ActionListener tileListener = e -> {
            TicTacToeTile tile = (TicTacToeTile) e.getSource();
            handleMove(tile);
        };

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                tiles[row][col] = new TicTacToeTile(row, col);
                tiles[row][col].setFont(
                        new Font("SansSerif", Font.BOLD, 60));
                tiles[row][col].addActionListener(tileListener);
                boardPanel.add(tiles[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitGame());
        add(quitButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitGame();
            }
        });

        updateDisplay();
        setLocationRelativeTo(null);
    }

    private void handleMove(TicTacToeTile tile) {
        if (game.isGameOver()) {
            return;
        }

        if (!game.playMove(tile.getRow(), tile.getCol())) {
            JOptionPane.showMessageDialog(
                    this,
                    "That square is taken. Pick an empty square.",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateDisplay();

        if (game.isWin()) {
            endGame("Player " + game.getCurrentPlayer() + " wins!");
        } else if (game.isTie()) {
            if (game.getMoveCount() == 9) {
                endGame("It's a tie! The board is full.");
            } else {
                endGame("It's a tie! No winning moves remain.");
            }
        }
    }

    private void updateDisplay() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                tiles[row][col].setText(game.getMark(row, col));
            }
        }

        statusLabel.setText(
                "Player " + game.getCurrentPlayer() + "'s turn");
    }

    private void endGame(String message) {
        statusLabel.setText(message);
        JOptionPane.showMessageDialog(this, message);

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Would you like to play again?",
                "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            game.resetGame();
            updateDisplay();
        } else {
            dispose();
        }
    }

    private void quitGame() {
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}