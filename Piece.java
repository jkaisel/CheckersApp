package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static sample.CheckersApp.*;

public class Piece extends StackPane {

    private final double ELLIPSE_RADIUS_X = 0.3;
    private final double ELLIPSE_RADIUS_Y = 0.25;
    private PieceType type;
    private int x;
    private int y;

    public Piece(PieceType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        Ellipse ellipse = createEllipse();
        Ellipse background = createBackround();

        getChildren().addAll(background, ellipse);

        pieceActionOnMouseClicked();
    }

    static void movePiece(int oldX, int oldY, int newX, int newY) {
        pieceClicked.relocate(TILE_SIZE * newX, TILE_SIZE * newY);
        pieceClicked.setPieceY(newY);
        pieceClicked.setPieceX(newX);
        board[oldY][oldX].setPiece(null);
        tileClicked.setPiece(pieceClicked);
        previousPieceClickedType = pieceClicked.getType();
        pieceClicked.setOpacity(1.0);
    }

    static void attackingPieceMove(int oldX, int oldY, int newX, int newY) {
        int xPieceAttacked = oldX + (newX - oldX) / 2;
        int yPieceAttacked = oldY + (newY - oldY) / 2;
        if (board[yPieceAttacked][xPieceAttacked].hasPiece()) {
            Piece pieceAttacked = board[yPieceAttacked][xPieceAttacked].getPiece();
            if (pieceAttacked.getType() != pieceClicked.getType()) {
                board[yPieceAttacked][xPieceAttacked].setPiece(null);
                pieceGroup.getChildren().remove(pieceAttacked);
                movePiece(oldX, oldY, newX, newY);
            }
        }
    }

    static void setPiecePosition(int x, int y) {

        board[y][x] = new Tile(((x + y) % 2 == 0), x, y);
        tileGroup.getChildren().add(board[y][x]);

        Piece piece = null;
        if (y <= 2 && (x + y) % 2 != 0) {
            piece = new Piece(PieceType.RED, x, y);
        }
        if (y >= 5 && (x + y) % 2 != 0) {
            piece = new Piece(PieceType.WHITE, x, y);
        }

        if (piece != null) {
            board[y][x].setPiece(piece);
            pieceGroup.getChildren().add(piece);
        }
    }

    private Ellipse createEllipse(){
        Ellipse ellipse = new Ellipse(TILE_SIZE * ELLIPSE_RADIUS_X, TILE_SIZE * ELLIPSE_RADIUS_Y);
        ellipse.setFill(type == PieceType.RED ? Color.BROWN : Color.LINEN);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * ELLIPSE_RADIUS_X * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * ELLIPSE_RADIUS_Y * 2) / 2);
        return ellipse;
    }

    private Ellipse createBackround(){
        Ellipse ellipse = new Ellipse(TILE_SIZE * ELLIPSE_RADIUS_X, TILE_SIZE * ELLIPSE_RADIUS_Y);
        ellipse.setFill(Color.BLACK);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * ELLIPSE_RADIUS_X * 2) / 2);
        ellipse.setTranslateY(((TILE_SIZE - TILE_SIZE * ELLIPSE_RADIUS_Y * 2) / 2) + TILE_SIZE * 0.07);
        return ellipse;
    }

    private void pieceActionOnMouseClicked() {
        this.setOnMouseClicked(e -> {
            Piece tempPiece = (Piece) e.getSource();
            if (tempPiece.getType() != previousPieceClickedType && tempPiece != previousPieceClicked) {
                pieceClicked = tempPiece;
                isPieceClicked = true;
                pieceClicked.setOpacity(0.5);
                if (previousPieceClicked != null) {
                    pieceClicked.setOpacity(0.5);
                    previousPieceClicked.setOpacity(1.0);
                }
                previousPieceClicked = pieceClicked;
            }
        });
    }

    public int getPieceX() {
        return x;
    }

    public int getPieceY() {
        return y;
    }

    public void setPieceX(int x) {
        this.x = x;
    }

    public void setPieceY(int y) {
        this.y = y;
    }

    public PieceType getType() {
        return type;
    }
}
