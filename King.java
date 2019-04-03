package chess;


/**********************************************************************
 * This class represents a king piece for a chess simulator.
 * Class operation is determining valid moves that either move one
 * space or "castle".
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class King extends ChessPiece {

    /** Counter that tracks the number of king moves */
    protected int moveCounter;


    /******************************************************************
     * This constructor creates a King object assigned to one player.
     *
     * @param player is an enum representing black or white player.
     *****************************************************************/
    public King(Player player) {
        super(player);
        moveCounter = 0;
    }


    /******************************************************************
     * This method returns the King object's type in a String format.
     *
     * @return a String representing the piece's type.
     *****************************************************************/
    public String type() {
        return "King";
    }


    /******************************************************************
     * This method overrides the ChessPiece isValidMove().
     *
     * @param move is a prospective move of "this" piece.
     * @param board is a 2-D IChessPiece Array representing the game
     * board.
     * @return is true when the param move is a legal chess move for
     * "this" piece.
     *****************************************************************/
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if (!super.isValidMove(move, board))
            return false;
        if (isCastling(move, board)) {
            if ((move.toRow == 0) || (move.toRow == 7)) {
                if (move.toColumn == board[0].length - 2)
                    if (board[move.toRow][move.toColumn + 1]
                            instanceof Rook)
                        return ((Rook)
                                board[move.toRow][move.toColumn + 1]).
                                moveCounter == 0;
                if (move.toColumn == board[0].length - 6)
                    if (board[move.toRow][move.toColumn - 2]
                            instanceof Rook)
                        return ((Rook)
                                board[move.toRow][move.toColumn - 2]).
                                moveCounter == 0;
            }
        }
        //move is invalid if king moves more than one space
        return !((move.toRow < move.fromRow - 1) ||
                (move.toRow > move.fromRow + 1) ||
                (move.toColumn < move.fromColumn - 1) ||
                (move.toColumn > move.fromColumn + 1));
    }


    /******************************************************************
     * This helper method organizes other helper methods to check if
     * a particular move is a valid "castling" move for the King object
     * on the chess board.
     *
     * @param move is a prospective move of "this" piece.
     * @param board is a 2-D IChessPiece Array representing the game
     * board.
     * @return is true if the move is a legal castling move.
     *****************************************************************/
    protected boolean isCastling(Move move, IChessPiece[][] board) {
        boolean castling = false;
        if (((this.moveCounter == 0) &&
                (validEndPosition(move, board)) &&
                (this.isClear(move, board)))) {
            move.castlingMove = true;
            castling = true;
        }
        return castling;
    }


    /******************************************************************
     * This helper method checks if the prospective castling move ends
     * in a valid "castled" position.
     *
     * @param move is a prospective move of "this" piece.
     * @param b is a 2-D IChessPiece Array representing the game
     * board.
     * @return is true if the move ends at a valid "castled" position.
     *****************************************************************/
    private boolean validEndPosition(Move move, IChessPiece[][] b) {
        if ((b[move.fromRow][move.fromColumn].player() ==
                Player.BLACK) &&
                (move.toRow == 0))
            return (move.toColumn == b[0].length - 2) ||
                    (move.toColumn == b[0].length - 6);
        if ((b[move.fromRow][move.fromColumn].player() ==
                Player.WHITE) &&
                (move.toRow == b.length - 1))
            return (move.toColumn == b[0].length - 2) ||
                    (move.toColumn == b[0].length - 6);
        else return false;
    }


    /******************************************************************
     * This helper method checks if the prospective castling move can
     * proceed through only empty spaces.
     *
     * @param move is a prospective move of "this" piece.
     * @param board is a 2-D IChessPiece Array representing the game
     * board.
     * @return is true if the spaces of travel are empty of pieces.
     *****************************************************************/
    private boolean isClear(Move move, IChessPiece[][] board) {
        if (move.fromColumn > move.toColumn)
            return ((board[move.fromRow][move.fromColumn - 1] == null)
                    &&
                    (board[move.fromRow][move.fromColumn - 2] == null)
                    &&
                    (board[move.fromRow][move.fromColumn - 3] == null))
                    ;

        if (move.fromColumn < move.toColumn)
            return ((board[move.fromRow][move.fromColumn + 1] == null)
                    &&
                    (board[move.fromRow][move.fromColumn + 2] == null))
                    ;
        else return false;
    }


    /******************************************************************
     * This method returns a String representation of "this" King
     * object and its attribute.
     *
     * @return is String representation of "this" King piece.
     *****************************************************************/
    @Override
    public String toString() {
        return "King{" +
                "moveCounter=" + moveCounter +
                '}';
    }
}