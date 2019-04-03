package chess;


/**********************************************************************
 * This class represents a rook piece for a chess simulator.
 * Class operation is determining valid moves that either move up or
 * down rows and columns.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class Rook extends ChessPiece {

    /** Counter that tracks the number of rook moves */
    int moveCounter;


    /******************************************************************
     * This constructor creates a Rook object assigned to one player.
     *
     * @param player is an enum representing black or white player.
     *****************************************************************/
    public Rook(Player player) {
        super(player);
        moveCounter = 0;
    }


    /******************************************************************
     * This method returns the Rook object's type in a String format.
     *
     * @return a String representing the piece's type.
     *****************************************************************/
    public String type() {
        return "Rook";
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
        if (isUpDownLeftRightMove(move, board))
            return true;
        else return false;
    }


    /******************************************************************
     * This helper method checks if the prospective moves up and down
     * the row columns.  It also checks that the path of travel is
     * clear.
     *
     * @param move is a prospective move of "this" piece.
     * @param board is a 2-D IChessPiece Array representing the game
     * board.
     * @return is true if the move is a valid up/down/left/right move.
     *****************************************************************/
    public boolean
    isUpDownLeftRightMove(Move move, IChessPiece[][] board) {
        //if row and column both change the move is illegal
        if ((move.fromRow != move.toRow) &&
                (move.fromColumn != move.toColumn))
            return false;

        else if (checkClearPath(move, board))
            return true;

        else return false;
    }


    /******************************************************************
     * This helper method checks that the path of travel for the move
     * is clear.
     *
     * @param move is a prospective move of "this" piece.
     * @param board is a 2-D IChessPiece Array representing the game
     * board.
     * @return is true if the move path is clear of other pieces.
     *****************************************************************/
    public boolean checkClearPath(Move move, IChessPiece[][] board) {
        if (move.fromRow < move.toRow) {
            int checkRow = move.fromRow + 1;
            while (checkRow < move.toRow) {
                if (board[checkRow][move.toColumn] != null)
                    return false;
                checkRow++;
            }
            return true;
        }
        if (move.fromRow > move.toRow) {
            int checkRow = move.fromRow - 1;
            while (checkRow > move.toRow) {
                if (board[checkRow][move.toColumn] != null)
                    return false;
                checkRow--;
            }
            return true;
        }
        if (move.fromColumn < move.toColumn) {
            int checkColumn = move.fromColumn + 1;
            while (checkColumn < move.toColumn) {
                if (board[move.toRow][checkColumn] != null)
                    return false;
                checkColumn++;
            }
            return true;
        }
        if (move.fromColumn > move.toColumn) {
            int checkColumn = move.fromColumn - 1;
            while (checkColumn > move.toColumn) {
                if (board[move.toRow][checkColumn] != null)
                    return false;
                checkColumn--;
            }
            return true;
        } else return false;
    }


    /******************************************************************
     * This method returns a String representation of "this" Rook
     * object and its attribute.
     *
     * @return is String representation of "this" Rook piece.
     *****************************************************************/
    @Override
    public String toString() {
        return "Rook{" +
                "moveCounter=" + moveCounter +
                '}';
    }
}
