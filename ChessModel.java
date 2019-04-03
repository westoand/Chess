package chess;


import java.util.ArrayList;

import static chess.Player.BLACK;
import static chess.Player.WHITE;


/**********************************************************************
 * This class is the "model" class of a Model-View-Controller program.
 * This ChessModel class represents chess game logic that uses other
 * classes for each type of piece.  The model's operations include
 * moving pieces, checking whether moves are legal, and undoing moves.
 *
 * @suthor Andrew Weston, Owen Ganzer, Dalton Claybaugh
 * @version Winter 2019
 *********************************************************************/
public class ChessModel implements IChessModel {

    /** Player enum representing player's turn */
    protected Player player;

    /** 2-D Array of IChessPieces representing the chess board */
    protected IChessPiece[][] board;

    /** Archive of previous moves */
    ArrayList<Move> moveList;


    /******************************************************************
     * This ChessModel constructor represents chess game logic that uses
     * other classes for each type of piece.  The model's operations
     * include moving pieces, checking whether moves are legal, and
     * undoing moves.
     *****************************************************************/
    public ChessModel() {
        board = new IChessPiece[8][8];
        player = WHITE;

        board[7][0] = new Rook(WHITE);
        board[7][1] = new Knight(WHITE);
        board[7][2] = new Bishop(WHITE);
        board[7][3] = new Queen(WHITE);
        board[7][4] = new King(WHITE);
        board[7][5] = new Bishop(WHITE);
        board[7][6] = new Knight(WHITE);
        board[7][7] = new Rook(WHITE);

        for (int i = 0; i < board[0].length; i++) {
            board[board.length - 2][i] = new Pawn(WHITE);
        }

        board[0][0] = new Rook(BLACK);
        board[0][1] = new Knight(BLACK);
        board[0][2] = new Bishop(BLACK);
        board[0][3] = new Queen(BLACK);
        board[0][4] = new King(BLACK);
        board[0][5] = new Bishop(BLACK);
        board[0][6] = new Knight(BLACK);
        board[0][7] = new Rook(BLACK);

        for (int i = 0; i < board[0].length; i++) {
            board[1][i] = new Pawn(BLACK);
        }

        moveList = new ArrayList<>();
    }


    /******************************************************************
     * This method checks whether a prospective move is legal for
     * the player whose turn it is.
     *
     * @param move is a prospective move of "this" the specified piece.
     * @return is true when the param move is a legal chess move for
     * the specific piece, move, and board state.
     *****************************************************************/
    public boolean isValidMove(Move move) {
        return isValidMove(move, this.currentPlayer());
    }


    /******************************************************************
     * This method checks whether a prospective move is legal for
     * the player whose turn it is.
     *
     * @param move is a prospective move of "this" the specified piece.
     * @param movingPlayer is the player making the prospective move.
     * @return is true when the param move is a legal chess move for
     * the specific piece, move, and board state.
     *****************************************************************/
    public boolean isValidMove(Move move, Player movingPlayer) {
        boolean valid = false;
        IChessPiece fromPosn = board[move.fromRow][move.fromColumn];

        if ((fromPosn != null) &&
                (fromPosn.isValidMove(move, board)) &&
                (fromPosn.player() == movingPlayer)) {
            if (inCheck(movingPlayer)) {
                valid = isCheckBroken(move);
            } else if (isSelfCheck(move))
                valid = false;
            else valid = true;
            if (valid) {
                valid = checkEnPassantCapture(move, valid, fromPosn);
                valid = checkCastling(move, valid, fromPosn);
            }
            return valid;
        }
        return valid;
    }


    /******************************************************************
     * This helper method checks if a prospective move is legal
     * castling.
     *
     * @param move is a prospective move of "this" the specified piece.
     * @param valid is true when the prospective castling move has not
     * yet been established as illegal.
     * @param fromPosn is the beginning board position (row and col).
     * @return is true when the castling move is legal.
     *****************************************************************/
    private boolean checkCastling(Move move, boolean valid,
                                  IChessPiece fromPosn) {
        if ((fromPosn instanceof King) &&
                (((King) fromPosn).isCastling(move, board))) {
            if (inCheck(fromPosn.player()))
                valid = false;
            if (castlingThruCheck(move))
                valid = false;
        }
        return valid;
    }


    /******************************************************************
     * This helper method checks if a prospective move is legal
     * en Passant capturing.
     *
     * @param move is a prospective move of "this" the specified piece.
     * @param valid is true when the prospective capture has not
     * yet been established as illegal.
     * @param fromPosn is the beginning board position (row and col).
     * @return is true when the en passant capture move is legal.
     *****************************************************************/
    private boolean checkEnPassantCapture(Move move, boolean valid,
                                          IChessPiece fromPosn) {
        Move lastMove;
        if ((fromPosn instanceof Pawn) &&
                (((Pawn) fromPosn).isEnPassantCapturing
                        (move, board))) {
            //check last move was enemy pawn 2-space move
            lastMove = moveList.get
                    (moveList.size() - 1);
            if ((Math.abs(lastMove.toRow - lastMove.fromRow) ==
                    2) && (lastMove.toColumn == move.toColumn))
                valid = true;
            else valid = false;
        }
        return valid;
    }


    /******************************************************************
     * This  method checks whether the parameter player is currently
     * in check.
     *
     * @param p is the player under consideration of check status.
     * @return is true Player p is in check.
     *****************************************************************/
    public boolean inCheck(Player p) {
        int kingRow = -1;
        int kingCol = -1;
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c] != null) &&
                        (board[r][c] instanceof King) &&
                        (board[r][c].player() == p)) {
                    kingRow = r;
                    kingCol = c;
                    //break out of nested loop search
                    r = board.length;
                    c = board[0].length;
                }
            }
        //is the other player putting you in check
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c] != null) &&
                        (board[r][c].player() != p) &&
                        (board[r][c].isValidMove
                                (new Move(r, c, kingRow, kingCol),
                                        board))) {
                    return true;
                }
            }
        return false;
    }


    /******************************************************************
     * This helper method checks whether a player currently in check
     * has broken out of check with a prospective move.
     *
     * @param move is a prospective move to break check.
     * @return is true when the checked player would no longer be
     * in check as a result of the prospective move.
     *****************************************************************/
    private boolean isCheckBroken(Move move) {
        boolean broken = false;
        this.move(move);
        if (!inCheck(player))
            broken = true;
        this.undo();
        return broken;
    }


    /******************************************************************
     * This helper method checks whether the player's move would check
     * themselves.
     *
     * @param move is a prospective move to break check.
     * @return is true when the checked player would be putting
     * themselves in check as a result of the prospective move.
     *****************************************************************/
    private boolean isSelfCheck(Move move) {
        boolean selfCheck = false;
        this.move(move);
        if (inCheck(player))
            selfCheck = true;
        this.undo();
        player.next();
        return selfCheck;
    }


    /******************************************************************
     * This helper method checks if a prospective castling move is
     * illegal because the king would have to move through a check.
     *
     * @param move is a prospective castling move.
     * @return is true when the castling move is illegal.
     *****************************************************************/
    public boolean castlingThruCheck(Move move) {
        boolean thruCheck = false;
        int travelRow = move.fromRow;
        int travelCol = move.fromColumn;

        if (move.fromColumn > move.toColumn) {
            travelCol--;
            thruCheck = isThruCheck(thruCheck, travelRow, travelCol,
                    board[move.fromRow][move.fromColumn]);

            //check 2 squares are clear for queen's side castle
            if (!thruCheck) {
                travelCol--;
                thruCheck = isThruCheck(thruCheck, travelRow,
                        travelCol,
                        board[move.fromRow][move.fromColumn]);
            }
        }
        if (move.fromColumn < move.toColumn) {
            travelCol++;
            thruCheck = isThruCheck(thruCheck, travelRow, travelCol,
                    board[move.fromRow][move.fromColumn]);
        }
        return thruCheck;
    }


    /******************************************************************
     * This helper method checks if a prospective castling move is
     * illegal because the king would have to move through a check.
     *
     * @param thruCheck is true when the move traverses a checked
     * position.
     * @param travelRow is an integer representation of the row the
     * king travels while castling.
     * @param travelCol is an integer representation of the column
     * the king travels while castling.
     * @return is true when the castling move is illegal.
     *****************************************************************/
    private boolean isThruCheck(boolean thruCheck, int travelRow,
                                int travelCol,
                                IChessPiece iChessPiece) {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c] != null) &&
                        (board[r][c].player() !=
                                iChessPiece.player()) &&
                        (board[r][c].isValidMove(new Move(r, c,
                                travelRow, travelCol), board)))
                    thruCheck = true;
            }
        return thruCheck;
    }


    /******************************************************************
     * This method executes a specific move.  Key operations include
     * archiving the move, moving the piece, and checking for special
     * move features (queening, castling, etc.)
     *
     * @param move is a prospective move of "this" the specified piece.
     *****************************************************************/
    public void move(Move move) {
        archiveMove(move);

        //check if castlingMove before moving anything
        if (board[move.fromRow][move.fromColumn] instanceof King) {
            if (move.castlingMove) {
                castlingRookHelper(move);
            }
        }

        //move the piece
        board[move.toRow][move.toColumn] =
                board[move.fromRow][move.fromColumn];
        board[move.fromRow][move.fromColumn] = null;

        //check piece specific actions
        if (board[move.toRow][move.toColumn] instanceof Rook)
            ((Rook) board[move.toRow][move.toColumn]).moveCounter++;

        if (board[move.toRow][move.toColumn] instanceof King)
            ((King) board[move.toRow][move.toColumn]).moveCounter++;

        if (board[move.toRow][move.toColumn] instanceof Pawn) {
            ((Pawn) board[move.toRow][move.toColumn]).moveCounter++;

            //check for "queening"
            if ((board[move.toRow][move.toColumn].player() == BLACK) &&
                    (move.toRow == board.length - 1)) {
                board[move.toRow][move.toColumn] = new Queen(BLACK);
                moveList.get(moveList.size() - 1).
                        queeningMove = true;
            }

            if ((board[move.toRow][move.toColumn].player() == WHITE) &&
                    (move.toRow == 0)) {
                board[move.toRow][move.toColumn] = new Queen(WHITE);
                moveList.get(moveList.size() - 1).
                        queeningMove = true;
            }
        }
    }


    /******************************************************************
     * This helper method records the key attributes of a move:
     * position, whether a piece was destroyed, etc.
     *
     * @param move is a prospective move of "this" the specified piece.
     *****************************************************************/
    private void archiveMove(Move move) {
        //if you are destroying a piece then archive it
        if (board[move.toRow][move.toColumn] != null) {
            moveList.add(new Move(move.fromRow,
                    move.fromColumn, move.toRow, move.toColumn));
            moveList.get(moveList.size() - 1).
                    setDestroyedPiece
                            (board[move.toRow][move.toColumn]);
        } else
            moveList.add(move);

        //update move arraylist for destroyed enPassant pieces
        if ((board[move.fromRow][move.fromColumn] instanceof Pawn) &&
                (((Pawn) board[move.fromRow][move.fromColumn]).
                        isEnPassantCapturing(move, board))) {
            if (board[move.fromRow][move.fromColumn].player()
                    == WHITE) {
                moveList.get(moveList.size() - 1).
                        setDestroyedPiece
                                (board[move.toRow + 1][move.toColumn]);
                board[move.toRow + 1][move.toColumn] = null;
            }
            if (board[move.fromRow][move.fromColumn].player()
                    == BLACK) {
                moveList.get(moveList.size() - 1).
                        setDestroyedPiece
                                (board[move.toRow - 1][move.toColumn]);
                board[move.toRow - 1][move.toColumn] = null;
            }
        }
    }


    /******************************************************************
     * This helper method executes the rook portion of a castling
     * move.
     *
     * @param kingMove is the king's move that cues castling.
     *****************************************************************/
    private void castlingRookHelper(Move kingMove) {
        int rookColStart = -1;
        int rookColEnd = -1;

        if (kingMove.toRow == 0) {
            if (kingMove.toColumn == board[0].length - 2) {
                rookColStart = board[0].length - 1;
                rookColEnd = board[0].length - 3;
            }
            if (kingMove.toColumn == board[0].length - 6) {
                rookColStart = 0;
                rookColEnd = board[0].length - 5;
            }
        }
        if (kingMove.toRow == 7) {
            if (kingMove.toColumn == board[0].length - 2) {
                rookColStart = board[0].length - 1;
                rookColEnd = board[0].length - 3;
            }
            if (kingMove.toColumn == board[0].length - 6) {
                rookColStart = 0;
                rookColEnd = board[0].length - 5;
            }
        }
        Move rookCastlingMove = new Move(kingMove.toRow, rookColStart,
                kingMove.toRow, rookColEnd);
        rookCastlingMove.setCastlingMove(true);
        move(rookCastlingMove);
    }


    /******************************************************************
     * This method reverses the previous move to the prior board state.
     * The turn passes back to the player whose turn was undone.
     *****************************************************************/
    protected void undo() {
        try {
            Move lastMove = moveList.get(moveList.size() - 1);

            //undo a simple move
            if ((!lastMove.queeningMove) && (!lastMove.castlingMove) &&
                    (!lastMove.enPassantMove)) {
                mundaneUndo(lastMove);
            } else {
                if (lastMove.castlingMove) {
                    //requires 2 undo moves for castling; reverse rook
                    mundaneUndo(moveList.get(moveList.size() - 1));
                    //reverse King
                    mundaneUndo(moveList.get(moveList.size() - 1));
                } else if (lastMove.enPassantMove) {
                    if (board[lastMove.toRow][lastMove.toColumn].
                            player() == WHITE) {
                        mundaneUndo(lastMove);
                        board[lastMove.toRow + 1][lastMove.toColumn] =
                                board[lastMove.toRow]
                                        [lastMove.toColumn];
                        board[lastMove.toRow][lastMove.toColumn] = null;
                    } else if
                    (board[lastMove.toRow][lastMove.toColumn].player()
                                    == BLACK) {
                        mundaneUndo(lastMove);
                        board[lastMove.toRow - 1][lastMove.toColumn] =
                                board[lastMove.toRow]
                                        [lastMove.toColumn];
                        board[lastMove.toRow][lastMove.toColumn] = null;
                    }
                }
                //queening
                else {
                    mundaneUndo(lastMove);
                    Player queenedPlayer = board[lastMove.fromRow]
                            [lastMove.fromColumn].player();
                    board[lastMove.fromRow][lastMove.fromColumn] =
                            new Pawn(queenedPlayer);
                }
            }
            //if we undo all of the moves we reset the player to black
            //the controller immediately passes the turn to white
        } catch (ArrayIndexOutOfBoundsException e) {
            player = BLACK;
        }
    }


    /******************************************************************
     * This helper method reverses the previous move to the prior board
     * state for simple moves (not castling, queening, etc.)
     *
     *@param lastMove is the most recent move that is to be reversed.
     *****************************************************************/
    private void mundaneUndo(Move lastMove) {
        //reverse the last move
        move(new Move(lastMove.toRow, lastMove.toColumn,
                lastMove.fromRow, lastMove.fromColumn));

        //remove the fake reversal "move" from the archive
        moveList.remove(moveList.size() - 1);
        //remove the original move from the archive
        moveList.remove(moveList.size() - 1);

        //if you reverse the move then also decrement the moveCounter
        // (once for the reversed move and once for the reversal "move")
        if (board[lastMove.fromRow][lastMove.fromColumn]
                instanceof Pawn)
            ((Pawn) board[lastMove.fromRow][lastMove.fromColumn]).
                    moveCounter -= 2;
        if (board[lastMove.fromRow][lastMove.fromColumn]
                instanceof Rook)
            ((Rook) board[lastMove.fromRow][lastMove.fromColumn]).
                    moveCounter -= 2;
        if (board[lastMove.fromRow][lastMove.fromColumn]
                instanceof King)
            ((King) board[lastMove.fromRow][lastMove.fromColumn]).
                    moveCounter -= 2;
        if (lastMove.destroyedPiece != null)
            board[lastMove.toRow][lastMove.toColumn] =
                    lastMove.destroyedPiece;
        else board[lastMove.toRow][lastMove.toColumn] = null;
    }


    /******************************************************************
     * This method checks if one player has attained checkmate and
     * won the game.
     *****************************************************************/
    public boolean isComplete() {
        boolean complete = true;
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c] != null) &&
                        (board[r][c].player() == player)) {
                    for (int r2 = 0; r2 < board.length; r2++)
                        for (int c2 = 0; c2 < board[0].length; c2++) {
                            if (isValidMove(new Move(r, c, r2, c2))) {
                                complete = false;
                                r = board.length;
                                c = board[0].length;
                                r2 = board.length;
                                c2 = board[0].length;
                            }
                        }
                }
            }
        return complete;
    }

    /******************************************************************
     * This getter method returns the player whose turn it is.
     *****************************************************************/
    public Player currentPlayer() {
        return player;
    }


    /******************************************************************
     * This getter method returns the number of rows on the board.
     *****************************************************************/
    public int numRows() {
        return board.length;
    }


    /******************************************************************
     * This getter method returns the number of columns on the board.
     *****************************************************************/
    public int numColumns() {
        return board[0].length;
    }


    /******************************************************************
     * This getter method returns the piece at the specified board
     * location.
     *
     * @param row is the integer representation of the row of the chess
     * board from 0 - 7.
     * @param column  is the integer representation of the row of the
     * chess board
     *****************************************************************/
    public IChessPiece pieceAt(int row, int column) {
        return board[row][column];
    }


    /******************************************************************
     * This setter method passes the turn to the other player.
     *****************************************************************/
    public void setNextPlayer() {
        player = player.next();
    }


    /******************************************************************
     * This method represents a simple, artificial intelligence
     * capability for the black chess pieces.  If black is in check
     * the AI attempts to break check, checkmate, check, destroy
     * an enemy piece, or advance a pawn.
     *****************************************************************/
    public void AI() {
        Move AIMove = null;
        if (inCheck(BLACK)) {
            for (int fr = 0; fr < board.length; fr++)
                for (int fc = 0; fc < board[0].length; fc++) {
                    if ((board[fr][fc] != null) &&
                            (board[fr][fc].player() == BLACK))
                        for (int r = 0; r < board.length; r++)
                            for (int c = 0; c < board[0].length; c++) {
                                Move test = new Move(fr, fc, r, c);
                                if (isValidMove(test, BLACK)) {
                                    AIMove = test;
                                    fr = board.length;
                                    fc = board[0].length;
                                    r = board.length;
                                    c = board[0].length;
                                }
                            }
                }

        } //attempt to checkmate
        if (AIMove == null) {
            for (int fr = 0; fr < board.length; fr++)
                for (int fc = 0; fc < board[0].length; fc++)
                    if (board[fr][fc] != null) {
                        if (board[fr][fc].player() == BLACK)
                            for (int tr = 0; tr < board.length; tr++)
                                for
                                (int c = 0; c < board[0].length; c++) {
                                    Move test = new Move(fr, fc, tr, c);
                                    if (isValidMove(test, BLACK)) {
                                        move(test);
                                        if (isComplete()) {
                                            AIMove = test;
                                            fr = board.length;
                                            fc = board[0].length;
                                            tr = board.length;
                                            c = board[0].length;
                                        }
                                        undo();
                                    }
                                }
                    }
        }

        //attempt to check
        if (AIMove == null) {
            for (int fr = 0; fr < board.length; fr++)
                for (int fc = 0; fc < board[0].length; fc++)
                    if ((board[fr][fc] != null) &&
                            (board[fr][fc].player() == BLACK)) {
                        for (int r = 0; r < board.length; r++)
                            for (int c = 0; c < board[0].length; c++) {
                                Move test = new Move(fr, fc, r, c);
                                if (isValidMove(test, BLACK)) {
                                    move(test);
                                    if (inCheck(WHITE)) {
                                        AIMove = test;
                                        fr = board.length;
                                        fc = board[0].length;
                                        r = board.length;
                                        c = board[0].length;
                                    }
                                    undo();
                                }
                            }
                    }
        }
        if (AIMove == null) {
            //Try to destroy
            for (int fr = 0; fr < board.length; fr++)
                for (int fc = 0; fc < board[0].length; fc++)
                    if (board[fr][fc] != null) {
                        if (board[fr][fc].player() == BLACK) {
                            for (int tr = 0; tr < board.length; tr++)
                                for
                                (int tc = 0; tc < board[0].length; tc++)
                                    if ((board[tr][tc] != null) &&
                                            (board[tr][tc].player() ==
                                                    (WHITE))) {
                                        Move test = new Move
                                                (fr, fc, tr, tc);
                                        if (isValidMove(test)) {
                                            AIMove = test;
                                            fr = board.length;
                                            fc = board[0].length;
                                            tr = board.length;
                                            tc = board[0].length;
                                        }
                                    }
                        }
                    }
        }
        if (AIMove == null) {
            //move pawns
            int pawnCol = (int) (Math.random() * 8);
            if (board[1][pawnCol] != null) {
                Move testedMove = new Move(1, pawnCol,
                        3, pawnCol);
                if (board[1][pawnCol].isValidMove(testedMove, board))
                    AIMove = testedMove;
            }
        }

        if (AIMove == null) {
            boolean valid = false;

            while (!valid) {
                int fr = (int) (Math.random() * 8);
                int fc = (int) (Math.random() * 8);
                if ((board[fr][fc]) != null &&
                        (board[fr][fc].player() == BLACK)) {
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            Move testedMove = new Move(fr, fc, r, c);
                            if (isValidMove(testedMove)) {
                                AIMove = testedMove;
                                valid = true;
                            }
                        }
                    }
                }
            }
        }
        move(AIMove);
        setNextPlayer();
    }
}