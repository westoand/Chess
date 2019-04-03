package chess;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



/**********************************************************************
 * This class is the JUnit test for a the model class of a chess game
 * simulator.  The tested class represents chess game logic that uses
 * classes for each type of piece.  The model's operations include
 * moving pieces, checking whether moves are legal, and undoing moves.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class ChessModelTest {

    @Test
    public void testWhiteBishopMovement() {

        ChessModel game = new ChessModel();

        //move bishop into middle for testing
        Move move = new Move(7, 2, 4, 4);
        game.move(move);

        //Test Mundane movements
        move = new Move(4, 4, 2, 2);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 2, 6);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 5, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 2, 2);
        assertTrue(game.isValidMove(move));

        //setup four pawns in each direction of bishop
        move = new Move(1, 0, 3, 3);
        game.move(move);
        move = new Move(1, 1, 3, 5);
        game.move(move);
        move = new Move(1, 2, 5, 5);
        game.move(move);
        move = new Move(1, 3, 5, 3);
        game.move(move);

        //Test bishop can't jump
        move = new Move(4, 4, 2, 2);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 2, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 2);
        assertFalse(game.isValidMove(move));

        //Test bishop can capture
        move = new Move(4, 4, 3, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 3, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 5, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 5, 3);
        assertTrue(game.isValidMove(move));
    }


    @Test
    public void testBlackBishopMovement() {
        ChessModel game = new ChessModel();
        game.setNextPlayer();

        //move bishop into middle for testing

        Move move = new Move(0, 5, 4, 4);
        game.move(move);

        //Test Mundane movements
        move = new Move(4, 4, 2, 2);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 2, 6);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 5, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 2, 2);
        assertTrue(game.isValidMove(move));

        //setup four pawns in each direction of bishop
        move = new Move(6, 0, 3, 3);
        game.move(move);
        move = new Move(6, 1, 3, 5);
        game.move(move);
        move = new Move(6, 2, 5, 5);
        game.move(move);
        move = new Move(6, 3, 5, 3);
        game.move(move);

        //Test bishop can't jump
        move = new Move(4, 4, 2, 2);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 2, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 2);
        assertFalse(game.isValidMove(move));

        //Test bishop can capture
        move = new Move(4, 4, 3, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 3, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 5, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 5, 3);
        assertTrue(game.isValidMove(move));
    }

    @Test
    public void testWhiteKingMovement() {
        ChessModel game = new ChessModel();

        //move king into middle for testing
        Move move = new Move(7, 4, 4, 4);
        game.move(move);

        //Test Mundane movements
        move = new Move(4, 4, 3, 4);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 4, 3);
        assertTrue(game.isValidMove(move));


        //setup pawns around king
        move = new Move(1, 0, 3, 4);
        game.move(move);
        move = new Move(1, 1, 4, 3);
        game.move(move);

        //Test king can't jump
        move = new Move(4, 4, 2, 2);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 2, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 2);
        assertFalse(game.isValidMove(move));

        //Test king can capture
        move = new Move(4, 4, 3, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 3, 5);
        assertTrue(game.isValidMove(move));
    }

    @Test
    public void testBlackKingMovement() {
        ChessModel game = new ChessModel();
        game.setNextPlayer();

        //move king into middle for testing

        Move move = new Move(0, 4, 4, 4);
        game.move(move);

        //Test Mundane movements
        move = new Move(4, 4, 3, 4);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 4, 3);
        assertTrue(game.isValidMove(move));


        //setup pawns around king
        move = new Move(6, 0, 3, 4);
        game.move(move);
        move = new Move(6, 1, 4, 3);
        game.move(move);

        //Test king can't jump
        move = new Move(4, 4, 2, 2);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 2, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 6);
        assertFalse(game.isValidMove(move));
        move = new Move(4, 4, 6, 2);
        assertFalse(game.isValidMove(move));

        //Test king can capture
        move = new Move(4, 4, 3, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 4, 3, 5);
        assertTrue(game.isValidMove(move));
    }

    @Test
    public void testWhiteKnight() {
        ChessModel game = new ChessModel();
        //Test normal valid move with jump, up and left
        Move move = new Move(7, 1, 5, 0);
        assertTrue(game.isValidMove(move));
        game.move(move);

        //Test up and right
        move = new Move(5, 0, 3, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);

        //test right and up /w a capture
        move = new Move(3, 1, 1, 2);
        assertTrue(game.isValidMove(move));

        //test right and down
        move = new Move(3, 1, 5, 2);
        assertTrue(game.isValidMove(move));

        //test left and down
        move = new Move(3, 1, 5, 0);
        assertTrue(game.isValidMove(move));

        //test left and up /w capture
        move = new Move(3, 1, 1, 0);
        game.isValidMove(move);

        //move to test capturing own piece
        move = new Move(3, 1, 5, 2);
        game.move(move);

        move = new Move(5, 2, 6, 0);
        assertFalse(game.isValidMove(move));

        //test invalid move
        move = new Move(5, 2, 3, 2);
        assertFalse(game.isValidMove(move));
    }

    @Test
    public void testBlackKnight() {
        ChessModel game = new ChessModel();
        game.setNextPlayer();

        //Test normal valid move with jump, up and left
        Move move = new Move(0, 1, 2, 0);
        assertTrue(game.isValidMove(move));
        game.move(move);

        //Test down and left
        move = new Move(2, 0, 4, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);

        //test down and right with a capture
        move = new Move(4, 1, 6, 2);
        assertTrue(game.isValidMove(move));

        //test invalid move
        move = new Move(6, 2, 6, 1);
        assertFalse(game.isValidMove(move));
    }


    @Test
    public void testWhitePawnMovement() {
        ChessModel game = new ChessModel();
        //pawn first double move
        Move move = new Move(6, 1, 4, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);
        //pawn can't move backwards
        move = new Move(4, 1, 5, 1);
        assertFalse(game.isValidMove(move));
        //pawn can't move sideways
        move = new Move(4, 1, 4, 2);
        assertFalse(game.isValidMove(move));
        //can't move sideways other direction
        move = new Move(4, 1, 4, 1);
        assertFalse(game.isValidMove(move));
        //can't move twice after first move
        move = new Move(4, 1, 2, 1);
        assertFalse(game.isValidMove(move));
        //move a black piece directly in front of pawn
        game.setNextPlayer();
        move = new Move(1, 1, 3, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);
        //check that white pawn can't move vertically to capture black pawn
        game.setNextPlayer();
        move = new Move(4, 1, 3, 1);
        assertFalse(game.isValidMove(move));
        //move black piece diagonally to pawn
        game.setNextPlayer();
        move = new Move(1, 2, 3, 2);
        assertTrue(game.isValidMove(move));
        game.move(move);
        //test pawn can capture diagonally
        game.setNextPlayer();
        move = new Move(4, 1, 3, 2);
        assertTrue(game.isValidMove(move));
    }

    @Test
    public void testBlackPawnMovement() {
        ChessModel game = new ChessModel();
        game.setNextPlayer();
        //Test black can double move on first turn
        Move move = new Move(1, 6, 3, 6);
        assertTrue(game.isValidMove(move));
        game.move(move);
        //Test that black can't double move on second turn
        move = new Move(3, 5, 5, 6);
        assertFalse(game.isValidMove(move));
        //Move a white pawn directly in front of black pawn
        move = new Move(6, 1, 4, 6);
        game.move(move);
        //Test pawn can't capture vertically
        move = new Move(3, 6, 4, 6);
        assertFalse(game.isValidMove(move));
        //move white pawn into valid capture position
        move = new Move(4, 6, 4, 7);
        game.move(move);
        //Test black pawn can capture
        move = new Move(3, 6, 4, 7);
        assertTrue(game.isValidMove(move));
        //move pawn to other valid capture position
        move = new Move(4, 7, 4, 5);
        game.move(move);
        //Test black pawn can capture other direction
        move = new Move(3, 6, 4, 5);
        assertTrue(game.isValidMove(move));
        //Test black pawn can't move backwards
        move = new Move(3, 6, 2, 6);
        assertFalse(game.isValidMove(move));
    }

    @Test
    public void whiteQueenMovement() {
        ChessModel game = new ChessModel();
        //put queen in center for testing
        Move move = new Move(7, 3, 4, 3);
        game.move(move);

        //Test bishop like movements
        move = new Move(4, 3, 2, 1);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 2, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 5, 4);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 5, 2);
        assertTrue(game.isValidMove(move));

        //Test rook like movements
        move = new Move(4, 3, 2, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 5, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 4, 7);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 4, 0);
        assertTrue(game.isValidMove(move));

        //Test invalid move
        move = new Move(4, 3, 3, 1);
        assertFalse(game.isValidMove(move));
    }

    @Test
    public void blackQueenMovement() {
        ChessModel game = new ChessModel();
        game.setNextPlayer();
        //put queen in center for testing
        Move move = new Move(0, 3, 4, 3);
        game.move(move);

        //Test bishop like movements
        move = new Move(4, 3, 2, 1);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 2, 5);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 5, 4);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 5, 2);
        assertTrue(game.isValidMove(move));

        //Test rook like movements
        move = new Move(4, 3, 2, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 5, 3);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 4, 7);
        assertTrue(game.isValidMove(move));
        move = new Move(4, 3, 4, 0);
        assertTrue(game.isValidMove(move));

        //Test invalid move
        move = new Move(4, 3, 3, 1);
        assertFalse(game.isValidMove(move));
    }

    @Test
    public void testWhiteRookMovement() {
        ChessModel game = new ChessModel();
        //move a rook out of the back line
        Move move = new Move(7, 0, 5, 0);
        game.move(move);

        //Test mundane rook movements
        for (int i = 4; i > 0; i--) {
            move = new Move(5, 0, i, 0);
            assertTrue(game.isValidMove(move));
        }

        for (int i = 1; i < 8; i++) {
            move = new Move(5, 0, 5, i);
            assertTrue(game.isValidMove(move));
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 8; j++) {
                move = new Move(5, 0, i, j);
                assertFalse(game.isValidMove(move));
            }
        }

        //setup - rook with 4 pawns surrounding it
        move = new Move(5, 0, 4, 3);
        game.move(move);
        move = new Move(1, 0, 3, 3);
        game.move(move);
        move = new Move(1, 1, 4, 4);
        game.move(move);
        move = new Move(1, 2, 5, 3);
        game.move(move);
        move = new Move(1, 3, 4, 2);
        game.move(move);

        //Test the rook can capture in all 4 directions
        move = new Move(4, 3, 3, 3);
        assertTrue(game.isValidMove(move));

        move = new Move(4, 3, 4, 4);
        assertTrue(game.isValidMove(move));

        move = new Move(4, 3, 5, 3);
        assertTrue(game.isValidMove(move));

        move = new Move(4, 3, 4, 2);
        assertTrue(game.isValidMove(move));

        //Test that the rook can't jump any of the pawns
        move = new Move(4, 3, 2, 3);
        assertFalse(game.isValidMove(move));

        move = new Move(4, 3, 4, 5);
        assertFalse(game.isValidMove(move));

        move = new Move(4, 3, 6, 3);
        assertFalse(game.isValidMove(move));

        move = new Move(4, 3, 4, 1);
        assertFalse(game.isValidMove(move));
    }

    @Test
    public void testBlackRookMovement() {
        ChessModel game = new ChessModel();
        game.setNextPlayer();
        //move a rook out of the back line
        Move move = new Move(0, 0, 5, 0);
        game.move(move);

        //Test mundane rook movements
        for (int i = 4; i > 1; i--) {
            move = new Move(5, 0, i, 0);
            assertTrue(game.isValidMove(move));
        }

        for (int i = 1; i < 8; i++) {
            move = new Move(5, 0, 5, i);
            assertTrue(game.isValidMove(move));
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 8; j++) {
                move = new Move(5, 0, i, j);
                assertFalse(game.isValidMove(move));
            }
        }

        //setup - rook with 4 pawns surrounding it
        move = new Move(5, 0, 4, 3);
        game.move(move);
        move = new Move(6, 0, 3, 3);
        game.move(move);
        move = new Move(6, 1, 4, 4);
        game.move(move);
        move = new Move(6, 2, 5, 3);
        game.move(move);
        move = new Move(6, 3, 4, 2);
        game.move(move);

        //Test the rook can capture in all 4 directions
        move = new Move(4, 3, 3, 3);
        assertTrue(game.isValidMove(move));

        move = new Move(4, 3, 4, 4);
        assertTrue(game.isValidMove(move));

        move = new Move(4, 3, 5, 3);
        assertTrue(game.isValidMove(move));

        move = new Move(4, 3, 4, 2);
        assertTrue(game.isValidMove(move));

        //Test that the rook can't jump any of the pawns
        move = new Move(4, 3, 2, 3);
        assertFalse(game.isValidMove(move));

        move = new Move(4, 3, 4, 5);
        assertFalse(game.isValidMove(move));

        move = new Move(4, 3, 6, 3);
        assertFalse(game.isValidMove(move));

        move = new Move(4, 3, 4, 1);
        assertFalse(game.isValidMove(move));
    }


    @Test
    public void testPawnCantJump() {
        ChessModel game = new ChessModel();
        //Move a piece in front of the pawn at 6, 1 and check that it
        //can't jump the piece on it's first move
        Move move = new Move(1, 0, 5, 0);
        game.move(move);

        move = new Move(6, 0, 4, 0);
        assertFalse(game.isValidMove(move));

        //repeat with pawn at 1, 7
        game.setNextPlayer();
        move = new Move(6, 1, 2, 7);
        game.move(move);

        move = new Move(1, 7, 2, 7);
        game.move(move);
        assertFalse(game.isValidMove(move));
    }

    @Test
    public void testEnPassantCaptureWhite() {
        ChessModel game = new ChessModel();
        //move a white pawn into position to test enpassant
        Move move = new Move(6, 0, 3, 0);
        game.move(move);
        game.setNextPlayer();

        //move black piece two from starting position
        move = new Move(1, 1, 3, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);
        game.setNextPlayer();

        //move the pawn diagonally one tile above black pawn
        move = new Move(3, 0, 2, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertNull(game.pieceAt(3, 0));
    }

    @Test
    public void testBlackEnPassantCapture() {
        ChessModel game = new ChessModel();
        //pass turn to black player for test
        game.setNextPlayer();
        //move a black pawn into position to test enpassant
        Move move = new Move(1, 6, 4, 6);
        game.move(move);
        game.setNextPlayer();

        //move white piece two from starting position
        move = new Move(6, 5, 4, 5);
        assertTrue(game.isValidMove(move));
        game.move(move);
        game.setNextPlayer();

        //move the pawn diagonally one tile above black pawn
        move = new Move(4, 6, 5, 5);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertNull(game.pieceAt(4, 5));
    }


    @Test
    public void testWhiteEnPassantCapture() {
        ChessModel game = new ChessModel();
        //move a white pawn into position to test enpassant
        Move move = new Move(6, 0, 3, 0);
        game.move(move);
        game.setNextPlayer();

        //move black piece two from starting position
        move = new Move(1, 1, 3, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);
        game.setNextPlayer();

        //move the pawn diagonally one tile above black pawn
        move = new Move(3, 0, 2, 1);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertTrue(game.pieceAt(3, 0) == null);
    }

    @Test
    public void testCastlingWhiteQueenSide() {
        ChessModel game = new ChessModel();


        //clear knight
        Move move = new Move(7, 1, 5, 0);
        game.move(move);
        //clear pawn blocking bishop
        move = new Move(6, 3, 4, 3);
        game.move(move);
        //clear bishop
        move = new Move(7, 2, 5, 4);
        game.move(move);
        //clear queen
        move = new Move(7, 3, 5, 3);
        game.move(move);


        //Test casting queen side, black
        move = new Move(7, 4, 7, 2);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertTrue(game.pieceAt(7, 2) instanceof King);
        assertTrue(game.pieceAt(7, 3) instanceof Rook);
    }

    @Test
    public void testCastlingWhiteKingSide() {
        ChessModel game = new ChessModel();


        //clear knight
        Move move = new Move(7, 6, 5, 7);
        game.move(move);
        //clear pawn blocking bishop
        move = new Move(6, 4, 4, 4);
        game.move(move);
        //clear bishop
        move = new Move(7, 5, 5, 3);
        game.move(move);

        //Test casting king side, white
        move = new Move(7, 4, 7, 6);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertTrue(game.pieceAt(7, 6) instanceof King);
        assertTrue(game.pieceAt(7, 5) instanceof Rook);
    }

    @Test
    public void testCastlingBlackQueenSide() {
        ChessModel game = new ChessModel();

        game.setNextPlayer();

        //clear knight
        Move move = new Move(0, 1, 2, 0);
        game.move(move);
        //clear pawn blocking bishop
        move = new Move(1, 3, 3, 3);
        game.move(move);
        //clear bishop
        move = new Move(0, 2, 3, 5);
        game.move(move);
        //clear queen
        move = new Move(0, 3, 2, 3);
        game.move(move);


        //Test casting queen side, black
        move = new Move(0, 4, 0, 2);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertTrue(game.pieceAt(0, 2) instanceof King);
        assertTrue(game.pieceAt(0, 3) instanceof Rook);
    }

    @Test
    public void testCastlingBlackKingSide() {
        ChessModel game = new ChessModel();

        game.setNextPlayer();

        //clear knight
        Move move = new Move(0, 6, 2, 7);
        game.move(move);
        //clear pawn blocking bishop
        move = new Move(1, 4, 3, 4);
        game.move(move);
        //clear bishop
        move = new Move(0, 5, 3, 2);
        game.move(move);

        //Test casting king side, black
        move = new Move(0, 4, 0, 6);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertTrue(game.pieceAt(0, 6) instanceof King);
        assertTrue(game.pieceAt(0, 5) instanceof Rook);
    }

    @Test
    public void testNumRows() {
        ChessModel game = new ChessModel();
        assertEquals(8, game.numRows());
        assertEquals(8, game.numColumns());
    }

    @Test
    public void testIsCheckBroken() {
        ChessModel game = new ChessModel();

        assertFalse(game.isComplete());

        //setup check
        //clear white pawn
        Move move = new Move(6, 4, 4, 4);
        game.move(move);

        //clear black pawn
        move = new Move(1, 3, 3, 3);
        game.move(move);

        //check black king with white bishop
        move = new Move(7, 5, 3, 1);
        game.move(move);

        assertTrue(game.inCheck(Player.BLACK));
        assertFalse(game.isComplete());
        game.setNextPlayer();

        //block check with black pawn
        move = new Move(1, 2, 2, 2);
        assertTrue(game.isValidMove(move));
        game.move(move);

        assertFalse(game.inCheck(Player.BLACK));
        assertFalse(game.isComplete());
    }
}