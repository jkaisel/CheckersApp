package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.CheckersApp.*;

public class Tile extends Rectangle {

    private Piece piece;
    private int x;
    private int y;

    public Tile(boolean backgroundColor, int x, int y) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
        this.x = x;
        this.y = y;

        relocate(x * TILE_SIZE, y * TILE_SIZE);
        setFill(backgroundColor ? Color.CORNSILK : Color.TAN);
        setTileAction();
    }

    private void setTileAction(){
        setOnMouseClicked(e -> {
            tileClicked = (Tile) e.getSource();
            pieceAction();
        });
    }

    boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getTileX() {
        return x;
    }

    public int getTileY() {
        return y;
    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}