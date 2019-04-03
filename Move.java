package chess;


/**********************************************************************
 * This class represents a chess move for the ChessModel
 * chess simulator.  Key attributes include beginning position,
 * ending position, whether a piece was destroyed, and whether the
 * move was a special queening, castling, etc. move.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class Move {

    /** Integer representation of row and column coordinates */
    protected int fromRow, fromColumn, toRow, toColumn;

    /** destroyed piece archives the piece captured in the move */
    protected IChessPiece destroyedPiece;

    /** Boolean representing if the move was for castling */
    protected boolean castlingMove;

    /** Boolean representing if the move was for queening */
    protected boolean queeningMove;

    /** Boolean representing if the move was for en Passant */
    protected boolean enPassantMove;


    /******************************************************************
     * This Move constructor represents beginning and ending move
     * coordinates.
     *
     * @param fromRow is the integer representing the chess board row
     * of the move before the move takes place.
     * @param fromColumn is the integer representing the chess board row
     * of the move before the move takes place.
     * @param toRow is the integer representing the chess board row
     * of the move after the move takes place.
     * @param toColumn is the integer representing the chess board row
     * of the move after the move takes place.
     *****************************************************************/
    public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
        this.destroyedPiece = null;
        this.castlingMove = false;
        this.queeningMove = false;
        this.enPassantMove = false;
    }


    /******************************************************************
     * This Move constructor represents beginning and ending move
     * coordinates.
     *
     * @param fromRow is the integer representing the chess board row
     * of the move before the move takes place.
     * @param fromColumn is the integer representing the chess board row
     * of the move before the move takes place.
     * @param toRow is the integer representing the chess board row
     * of the move after the move takes place.
     * @param toColumn is the integer representing the chess board row
     * of the move after the move takes place.
     * @param destroyedPiece represents the piece formerly at toRow
     * toColumn that was destroyed by the move.
     * @param castlingMove is true when the move involved two moves
     * because it was castling.
     * @param queeningMove is true when the move was "promotion".
     * @param enPassantMove is true when the move was a valid pawn
     * enPassant capture.
     *****************************************************************/
    public Move(int fromRow, int fromColumn, int toRow, int toColumn,
                IChessPiece destroyedPiece, boolean castlingMove,
                boolean queeningMove, boolean enPassantMove) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
        this.destroyedPiece = destroyedPiece;
        this.castlingMove = castlingMove;
        this.queeningMove = queeningMove;
        this.enPassantMove = enPassantMove;
    }


    /******************************************************************
     * This setter method sets the piece destroyed by a move.
     *
     * @param destroyedPiece is the piece destroyed by a move.
     *****************************************************************/
    public void setDestroyedPiece(IChessPiece destroyedPiece) {
        this.destroyedPiece = destroyedPiece;
    }


    /******************************************************************
     * This setter method sets the move attribute that identifies
     * castling moves.
     *
     * @param castlingMove is true if the move was a valid castling.
     *****************************************************************/
    public void setCastlingMove(boolean castlingMove) {
        this.castlingMove = castlingMove;
    }


    /******************************************************************
     * This setter method sets the move attribute that identifies
     * en Passant moves.
     *
     * @param isEnPassantMove is true when the move was a valid
     * en Passant pawn capture.
     *****************************************************************/
    public void setEnPassantMove(boolean isEnPassantMove) {
        this.enPassantMove = isEnPassantMove;
    }


    /******************************************************************
     * This method returns a String representation of "this" Move
     * object.
     *
     * @return is a String representing the move coordinates and
     * attributes.
     *****************************************************************/
    @Override
    public String toString() {
        String s = "Move [fromRow=" + fromRow + ", fromColumn=" +
                fromColumn + ", toRow=" + toRow + ", toColumn=" +
                toColumn
                + "]";
        if (destroyedPiece != null)
            s += " Destroyed Piece = " + destroyedPiece.player() +
                    " " + destroyedPiece.type();

        if (castlingMove)
            s += " castlingMove " + castlingMove;

        if (queeningMove)
            s += " queeningMove " + queeningMove;

        if (enPassantMove)
            s += " enPassantMove " + enPassantMove;
        return s;
    }
}