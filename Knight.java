package chess;


/**********************************************************************
 * This class represents a knight piece for a chess simulator.
 * Class operation is determining valid "L-shaped" moves on chess
 * board.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class Knight extends ChessPiece {


    /******************************************************************
     * This constructor creates a Knight object assigned to one player.
     *
     * @param player is an enum representing black or white player.
     *****************************************************************/
    public Knight(Player player) {
        super(player);
    }


    /******************************************************************
     * This method returns the Knight object's type in a String format.
     *
     * @return a String representing the piece's type.
     *****************************************************************/
    public String type() {
        return "Knight";
    }


    /******************************************************************
     * This method overrides the ChessPiece isValidMove().
     *
     * @param move is a prospective move of "this" piece.
     * @param board is a 2-D IChessPiece Array representing the game
     * board.
     * @boolean is true when the param move is a legal chess move for
     * "this" piece.
     *****************************************************************/
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if (!super.isValidMove(move, board))
            return false;

        if ((Math.abs(move.fromRow - move.toRow) == 2) &&
                (Math.abs(move.fromColumn - move.toColumn) == 1))
            return true;

        if ((Math.abs(move.fromColumn - move.toColumn) == 2) &&
                (Math.abs(move.fromRow - move.toRow) == 1))
            return true;

        return false;
    }
}