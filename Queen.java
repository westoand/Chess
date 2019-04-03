package chess;


/**********************************************************************
 * This class represents a queen piece for a chess simulator.
 * Class operation is determining valid moves that either move up or
 * down rows and columns, or across the board diagonally.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class Queen extends ChessPiece {


    /******************************************************************
     * This constructor creates a Queen object assigned to one player.
     *
     * @param player is an enum representing black or white player.
     *****************************************************************/
    public Queen(Player player) {
        super(player);
    }


    /******************************************************************
     * This method returns the Queen object's type in a String format.
     *
     * @return a String representing the piece's type.
     *****************************************************************/
    public String type() {
        return "Queen";
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
        Bishop move1 = new Bishop
                (board[move.fromRow][move.fromColumn].player());
        Rook move2 = new Rook
                (board[move.fromRow][move.fromColumn].player());
        return (move1.isValidMove(move, board) ||
                move2.isValidMove(move, board));
    }
}