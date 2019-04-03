package chess;


/**********************************************************************
 * This class represents a bishop piece for a chess simulator.
 * Class operation is determining valid "diagonal" moves across chess
 * board.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class Bishop extends ChessPiece {


    /******************************************************************
     * This constructor creates a Bishop object assigned to one player.
     *
     * @param player is an enum representing black or white player.
     *****************************************************************/
    public Bishop(Player player) {
        super(player);
    }

    /******************************************************************
     * This method returns the bishop object's type in a String format.
     *
     * @return a String representing the piece's type.
     *****************************************************************/
    public String type() {
        return "Bishop";
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
        if (Math.abs(move.fromRow - move.toRow) !=
                Math.abs(move.fromColumn - move.toColumn))
            return false;
        int i = 1;

        if ((move.fromRow > move.toRow) &&
                (move.fromColumn > move.toColumn)) {
            while (i < Math.abs(move.fromRow - move.toRow)) {
                if (board[move.fromRow - i][move.fromColumn - i] !=
                        null)
                    return false;
                i++;
            }
        }

        if ((move.fromRow < move.toRow) &&
                (move.fromColumn < move.toColumn)) {
            while (i < Math.abs(move.fromRow - move.toRow)) {
                if (board[move.fromRow + i][move.fromColumn + i] !=
                        null)
                    return false;
                i++;
            }
        }

        if ((move.fromRow > move.toRow) &&
                (move.fromColumn < move.toColumn)) {
            while (i < Math.abs(move.fromRow - move.toRow)) {
                if (board[move.fromRow - i][move.fromColumn + i] !=
                        null)
                    return false;
                i++;
            }
        }

        if ((move.fromRow < move.toRow) &&
                (move.fromColumn > move.toColumn)) {
            while (i < Math.abs(move.fromRow - move.toRow)) {
                if (board[move.fromRow + i][move.fromColumn - i] !=
                        null)
                    return false;
                i++;
            }
        }
        return true;
    }
}