import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TTTBoardTest {

    private TTTBoard board;

    @BeforeEach
    void setUp() {
        board = new TTTBoard();
    }

    @Test
    void testConstructor() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals(" ", board.getMark(row, col));
            }
        }

        assertFalse(board.isWin("X"));
        assertFalse(board.isWin("O"));
        assertFalse(board.isTie());
    }

    @Test
    void testPlaceMark() {
        assertTrue(board.placeMark(1, 1, "X"));
        assertEquals("X", board.getMark(1, 1));
        assertFalse(board.isValidMove(1, 1));
    }

    @Test
    void testOccupiedSquare() {
        board.placeMark(0, 0, "X");

        assertFalse(board.placeMark(0, 0, "O"));
        assertEquals("X", board.getMark(0, 0));
    }

    @Test
    void testInvalidPosition() {
        assertFalse(board.isValidMove(-1, 0));
        assertFalse(board.isValidMove(3, 0));
        assertFalse(board.isValidMove(0, -1));
        assertFalse(board.isValidMove(0, 3));
        assertFalse(board.placeMark(3, 0, "X"));
    }

    @Test
    void testInvalidPlayer() {
        assertFalse(board.placeMark(0, 0, "A"));
        assertEquals(" ", board.getMark(0, 0));
    }

    @Test
    void testRowWins() {
        for (int row = 0; row < 3; row++) {
            board.clearBoard();

            board.placeMark(row, 0, "X");
            board.placeMark(row, 1, "X");
            assertFalse(board.isWin("X"));

            board.placeMark(row, 2, "X");
            assertTrue(board.isWin("X"));
            assertFalse(board.isWin("O"));
            assertFalse(board.isTie());
        }
    }

    @Test
    void testColumnWins() {
        for (int col = 0; col < 3; col++) {
            board.clearBoard();

            board.placeMark(0, col, "O");
            board.placeMark(1, col, "O");
            assertFalse(board.isWin("O"));

            board.placeMark(2, col, "O");
            assertTrue(board.isWin("O"));
            assertFalse(board.isWin("X"));
            assertFalse(board.isTie());
        }
    }

    @Test
    void testDiagonalWins() {
        board.placeMark(0, 0, "X");
        board.placeMark(1, 1, "X");
        board.placeMark(2, 2, "X");

        assertTrue(board.isWin("X"));

        board.clearBoard();

        board.placeMark(0, 2, "O");
        board.placeMark(1, 1, "O");
        board.placeMark(2, 0, "O");

        assertTrue(board.isWin("O"));
    }

    @Test
    void testEarlyTie() {
        board.placeMark(0, 0, "X");
        board.placeMark(0, 1, "O");
        board.placeMark(0, 2, "X");
        board.placeMark(1, 1, "O");
        board.placeMark(1, 0, "X");
        board.placeMark(1, 2, "O");
        board.placeMark(2, 1, "X");

        assertFalse(board.isTie());

        board.placeMark(2, 0, "O");

        assertTrue(board.isTie());
        assertEquals(" ", board.getMark(2, 2));
        assertFalse(board.isWin("X"));
        assertFalse(board.isWin("O"));
    }

    @Test
    void testFullBoardTie() {
        board.placeMark(0, 0, "X");
        board.placeMark(0, 1, "O");
        board.placeMark(0, 2, "X");
        board.placeMark(1, 0, "O");
        board.placeMark(1, 1, "X");
        board.placeMark(2, 0, "O");
        board.placeMark(1, 2, "X");
        board.placeMark(2, 2, "O");

        assertFalse(board.isTie());

        board.placeMark(2, 1, "X");

        assertTrue(board.isTie());
        assertFalse(board.isWin("X"));
        assertFalse(board.isWin("O"));
    }

    @Test
    void testClearBoard() {
        board.placeMark(0, 0, "X");
        board.placeMark(1, 1, "O");
        board.clearBoard();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals(" ", board.getMark(row, col));
                assertTrue(board.isValidMove(row, col));
            }
        }

        assertFalse(board.isWin("X"));
        assertFalse(board.isWin("O"));
        assertFalse(board.isTie());
    }
}