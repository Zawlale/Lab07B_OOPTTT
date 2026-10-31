public class TTTBoard {

    private String[][] board = new String[3][3];

    public TTTBoard() {
        clearBoard();
    }

    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }

        return board[row][col].equals(" ");
    }

    public boolean placeMark(int row, int col, String player) {
        if (!isValidMove(row, col)) {
            return false;
        }

        if (!player.equals("X") && !player.equals("O")) {
            return false;
        }

        board[row][col] = player;
        return true;
    }

    public String getMark(int row, int col) {
        return board[row][col];
    }

    public boolean isWin(String player) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(player)
                    && board[row][1].equals(player)
                    && board[row][2].equals(player)) {
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(player)
                    && board[1][col].equals(player)
                    && board[2][col].equals(player)) {
                return true;
            }
        }

        if (board[0][0].equals(player)
                && board[1][1].equals(player)
                && board[2][2].equals(player)) {
            return true;
        }

        return board[0][2].equals(player)
                && board[1][1].equals(player)
                && board[2][0].equals(player);
    }

    public boolean isTie() {
        for (int row = 0; row < 3; row++) {
            if (!isBlockedLine(
                    board[row][0], board[row][1], board[row][2])) {
                return false;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (!isBlockedLine(
                    board[0][col], board[1][col], board[2][col])) {
                return false;
            }
        }

        return isBlockedLine(board[0][0], board[1][1], board[2][2])
                && isBlockedLine(board[0][2], board[1][1], board[2][0]);
    }

    public void clearBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = " ";
            }
        }
    }

    private boolean isBlockedLine(String first, String second, String third) {
        boolean hasX = first.equals("X")
                || second.equals("X") || third.equals("X");

        boolean hasO = first.equals("O")
                || second.equals("O") || third.equals("O");

        return hasX && hasO;
    }
}