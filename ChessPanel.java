package chess;

import javafx.geometry.VerticalDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static chess.Player.BLACK;
import static chess.Player.WHITE;

public class ChessPanel extends JPanel {

    private JButton[][] board;
    private ChessModel model;
    private ImageIcon wRook;
    private ImageIcon wBishop;
    private ImageIcon wQueen;
    private ImageIcon wKing;
    private ImageIcon wPawn;
    private ImageIcon wKnight;
    private ImageIcon bRook;
    private ImageIcon bBishop;
    private ImageIcon bQueen;
    private ImageIcon bKing;
    private ImageIcon bPawn;
    private ImageIcon bKnight;
    private boolean firstTurnFlag;
    private int fromRow;
    private int toRow;
    private int fromCol;
    private int toCol;
    private JButton undoButton;
    private JButton AIButton;
    private JLabel turnLabel;
    private boolean AIOn;

    private listener listener;
    private boolean promotionMove;

    public ChessPanel() {
        model = new ChessModel();
        board = new JButton[model.numRows()][model.numColumns()];
        listener = new listener();
        createIcons();

        JPanel boardpanel = new JPanel();
        JPanel buttonpanel = new JPanel();
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));


        boardpanel.setLayout(new GridLayout(model.numRows() + 1,
                model.numColumns(), 1, 1));

        undoButton = new JButton("Undo");
        undoButton.addActionListener(listener);

        AIButton = new JButton("Enable AI");
        AIButton.addActionListener(listener);

        turnLabel = new JLabel();
        turnLabel.setText(model.currentPlayer().toString() + " MOVE");


        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++) {

                if (model.pieceAt(r, c) == null) {
                    board[r][c] = new JButton("", null);
                    board[r][c].addActionListener(listener);
                } else if (model.pieceAt(r, c).player() ==
                        Player.BLACK) {
                    placeBlackPieces(r, c);
                } else if (model.pieceAt(r, c).player() ==
                        Player.WHITE) {
                    placeWhitePieces(r, c);
                }

                setBackGroundColor(r, c);
                buttonpanel.add(board[r][c]);
                boardpanel.add(board[r][c]);
            }
        }

        controlPanel.add(undoButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(AIButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(turnLabel);
        add(controlPanel, BorderLayout.WEST);
        add(boardpanel, BorderLayout.EAST);
        boardpanel.setPreferredSize(new Dimension(600, 600));
        add(buttonpanel, BorderLayout.SOUTH);
        firstTurnFlag = true;
        AIOn = false;
        promotionMove = false;
    }

    private void setBackGroundColor(int r, int c) {
        if ((c % 2 == 1 && r % 2 == 0) || (c % 2 == 0 && r % 2 == 1)) {
            board[r][c].setBackground(Color.LIGHT_GRAY);
        } else if ((c % 2 == 0 && r % 2 == 0) ||
                (c % 2 == 1 && r % 2 == 1)) {
            board[r][c].setBackground(Color.WHITE);
        }
    }

    private void placeWhitePieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, wPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, wRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, wKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, wBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, wQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, wKing);
            board[r][c].addActionListener(listener);
        }
    }

    private void placeBlackPieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, bPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, bRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, bKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, bBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, bQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, bKing);
            board[r][c].addActionListener(listener);
        }
    }

    private void createIcons() {
        // Sets the Image for white player pieces
        wRook = new ImageIcon("wRook.png");
        wBishop = new ImageIcon("wBishop.png");
        wQueen = new ImageIcon("wQueen.png");
        wKing = new ImageIcon("wKing.png");
        wPawn = new ImageIcon("wPawn.png");
        wKnight = new ImageIcon("wKnight.png");

        // Sets the image for the black player pieces
        bRook = new ImageIcon("bRook.png");
        bBishop = new ImageIcon("bBishop.png");
        bQueen = new ImageIcon("bQueen.png");
        bKing = new ImageIcon("bKing.png");
        bPawn = new ImageIcon("bPawn.png");
        bKnight = new ImageIcon("bKnight.png");
    }

    // method that updates the board
    private void displayBoard() {

        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++)
                if (model.pieceAt(r, c) == null)
                    board[r][c].setIcon(null);
                else if (model.pieceAt(r, c).player() ==
                        Player.WHITE) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(wPawn);

                    if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(wRook);

                    if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(wKnight);

                    if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(wBishop);

                    if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(wQueen);

                    if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(wKing);
                } else {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(bPawn);

                    if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(bRook);

                    if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(bKnight);

                    if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(bBishop);

                    if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(bQueen);

                    if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(bKing);
                }
        }
        repaint();
    }

    // inner class that represents action listener for buttons
    private class listener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == undoButton) {
                model.undo();
                model.setNextPlayer();
                turnLabelHelper();
                displayBoard();
            }
            if (event.getSource() == AIButton) {
                if (AIOn) {
                    AIOn = false;
                    AIButton.setText("Enable AI");
                } else {
                    AIOn = true;
                    AIButton.setText("Disable AI");
                    if(model.currentPlayer()==BLACK)
                        enableAI();
                }
                displayBoard();
            }

            for (int r = 0; r < model.numRows(); r++)
                for (int c = 0; c < model.numColumns(); c++) {
                    if (board[r][c] == event.getSource()) {
                        if ((firstTurnFlag == true) &&
                                (model.pieceAt(r, c) != null)) {
                            fromRow = r;
                            fromCol = c;
                            firstTurnFlag = false;
                        } else {
                            toRow = r;
                            toCol = c;
                            firstTurnFlag = true;
                            Move m = new Move
                                    (fromRow, fromCol, toRow, toCol);
                            if ((model.isValidMove(m))) {
                                validMoveHelper(m);
                            }
                        }
                    }
                }

        }

        private void validMoveHelper(Move m) {
            //if the valid move is of a Pawn then check for promotion
            if ((model.pieceAt(m.fromRow, m.fromColumn)
                    instanceof Pawn) &&
                    //check if black reached opponent's side
                    (((model.pieceAt(m.fromRow, m.fromColumn).player()
                            == BLACK) &&
                            (m.toRow == model.board.length - 1)) ||
                            //check if black reached opponent's side
                            (((model.pieceAt(m.fromRow, m.fromColumn).
                                    player() == WHITE) &&
                                    (m.toRow == 0)))))
                promotionMove = true;

            model.move(m);
            //if "queening" move then prompt and reset state
            if (promotionMove) {
                promotionHelper(m);
                promotionMove = false;
            }
            displayBoard();
            model.setNextPlayer();
            turnLabelHelper();
            displayBoard();
            checkGameStatus();
            if (AIOn)
                enableAI();
            displayBoard();
        }

        protected void turnLabelHelper() {
            turnLabel.setText(model.currentPlayer().toString() + " MOVE");
            turnLabel.repaint();
        }


        public void promotionHelper(Move move) {
            String[] promotionOptions =
                    {"Queen", "Knight", "Rook", "Bishop"};
            int promotionChoice = JOptionPane.showOptionDialog
                    (null, "Choose Promotion", "",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, promotionOptions,
                            promotionOptions[0]);
            Player promotedPlayer =
                    model.board[move.toRow][move.toColumn].player();
            if (promotionChoice == 0)
                model.board[move.toRow][move.toColumn] =
                        new Queen(promotedPlayer);
            if (promotionChoice == 1)
                model.board[move.toRow][move.toColumn] =
                        new Knight(promotedPlayer);
            if (promotionChoice == 2)
                model.board[move.toRow][move.toColumn] =
                        new Rook(promotedPlayer);
            if (promotionChoice == 3)
                model.board[move.toRow][move.toColumn] =
                        new Bishop(promotedPlayer);
        }

        private void enableAI() {
                model.AI();
                turnLabelHelper();
                displayBoard();
                checkGameStatus();
        }

        private void checkGameStatus() {
            if (model.isComplete())
                JOptionPane.showMessageDialog(null, "Checkmate!");
            else if (model.inCheck(model.currentPlayer())) {
                if (model.currentPlayer() == WHITE)
                    JOptionPane.showMessageDialog(null,
                            "White is in Check");
                else
                    JOptionPane.showMessageDialog(null,
                            "Black is in Check");
            }
        }
    }
}