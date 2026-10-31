public class TTTGame {

    private TTTBoard board;
    private String currentPlayer;
    private int moveCount;
    private boolean gameOver;

    public TTTGame() {
        board = new TTTBoard();
        resetGame();
    }

    public boolean playMove(int row, int col) {
        if (gameOver) {
            return false;
        }

        if (!board.placeMark(row, col, currentPlayer)) {
            return false;
        }

        moveCount++;

        if (isWin() || isTie()) {
            gameOver = true;
        } else {
            if (currentPlayer.equals("X")) {
                currentPlayer = "O";
            } else {
                currentPlayer = "X";
            }
        }

        return true;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public String getMark(int row, int col) {
        return board.getMark(row, col);
    }

    public boolean isWin() {
        return moveCount >= 5 && board.isWin(currentPlayer);
    }

    public boolean isTie() {
        return moveCount >= 7 && !isWin() && board.isTie();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        board.clearBoard();
        currentPlayer = "X";
        moveCount = 0;
        gameOver = false;
    }
}