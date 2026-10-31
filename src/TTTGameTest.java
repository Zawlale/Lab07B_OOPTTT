import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TTTGameTest {

    private TTTGame game;

    @BeforeEach
    void setUp() {
        game = new TTTGame();
    }

    @Test
    void testConstructor() {
        assertEquals("X", game.getCurrentPlayer());
        assertEquals(0, game.getMoveCount());
        assertFalse(game.isGameOver());
        assertFalse(game.isWin());
        assertFalse(game.isTie());
    }

    @Test
    void testValidMove() {
        assertTrue(game.playMove(0, 0));

        assertEquals("X", game.getMark(0, 0));
        assertEquals("O", game.getCurrentPlayer());
        assertEquals(1, game.getMoveCount());
    }

    @Test
    void testAlternatingTurns() {
        game.playMove(0, 0);
        game.playMove(1, 1);

        assertEquals("X", game.getMark(0, 0));
        assertEquals("O", game.getMark(1, 1));
        assertEquals("X", game.getCurrentPlayer());
        assertEquals(2, game.getMoveCount());
    }

    @Test
    void testOccupiedSquare() {
        game.playMove(0, 0);

        assertFalse(game.playMove(0, 0));
        assertEquals("X", game.getMark(0, 0));
        assertEquals("O", game.getCurrentPlayer());
        assertEquals(1, game.getMoveCount());
    }

    @Test
    void testInvalidPosition() {
        assertFalse(game.playMove(-1, 0));
        assertFalse(game.playMove(3, 0));
        assertFalse(game.playMove(0, -1));
        assertFalse(game.playMove(0, 3));

        assertEquals("X", game.getCurrentPlayer());
        assertEquals(0, game.getMoveCount());
    }

    @Test
    void testXWin() {
        game.playMove(0, 0);
        game.playMove(1, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(0, 2);

        assertTrue(game.isWin());
        assertTrue(game.isGameOver());
        assertFalse(game.isTie());
        assertEquals("X", game.getCurrentPlayer());
        assertEquals(5, game.getMoveCount());
    }

    @Test
    void testOWin() {
        game.playMove(0, 0);
        game.playMove(1, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(2, 2);
        game.playMove(1, 2);

        assertTrue(game.isWin());
        assertTrue(game.isGameOver());
        assertFalse(game.isTie());
        assertEquals("O", game.getCurrentPlayer());
        assertEquals(6, game.getMoveCount());
    }

    @Test
    void testEarlyTie() {
        game.playMove(0, 0);
        game.playMove(0, 1);
        game.playMove(0, 2);
        game.playMove(1, 1);
        game.playMove(1, 0);
        game.playMove(1, 2);
        game.playMove(2, 1);

        assertFalse(game.isGameOver());

        game.playMove(2, 0);

        assertTrue(game.isTie());
        assertTrue(game.isGameOver());
        assertFalse(game.isWin());
        assertEquals(8, game.getMoveCount());
        assertEquals(" ", game.getMark(2, 2));

        assertFalse(game.playMove(2, 2));
        assertEquals(8, game.getMoveCount());
    }

    @Test
    void testFullBoardTie() {
        game.playMove(0, 0);
        game.playMove(0, 1);
        game.playMove(0, 2);
        game.playMove(1, 0);
        game.playMove(1, 1);
        game.playMove(2, 0);
        game.playMove(1, 2);
        game.playMove(2, 2);

        assertFalse(game.isGameOver());

        game.playMove(2, 1);

        assertTrue(game.isTie());
        assertTrue(game.isGameOver());
        assertFalse(game.isWin());
        assertEquals(9, game.getMoveCount());
    }

    @Test
    void testMoveAfterWin() {
        game.playMove(0, 0);
        game.playMove(1, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(0, 2);

        assertFalse(game.playMove(2, 2));
        assertEquals(" ", game.getMark(2, 2));
        assertEquals(5, game.getMoveCount());
        assertEquals("X", game.getCurrentPlayer());
    }

    @Test
    void testResetGame() {
        game.playMove(0, 0);
        game.playMove(1, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(0, 2);

        game.resetGame();

        assertEquals("X", game.getCurrentPlayer());
        assertEquals(0, game.getMoveCount());
        assertFalse(game.isGameOver());
        assertFalse(game.isWin());
        assertFalse(game.isTie());

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals(" ", game.getMark(row, col));
            }
        }

        assertTrue(game.playMove(1, 1));
        assertEquals("X", game.getMark(1, 1));
        assertEquals("O", game.getCurrentPlayer());
    }
}