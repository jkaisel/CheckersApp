package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static sample.Piece.attackingPieceMove;
import static sample.Piece.movePiece;

public class CheckersApp extends Application {

    static final int TILE_SIZE = 100;
    private static final int COLUMNS = 8;
    private static final int ROWS = 8;
    static PieceType previousPieceClickedType = PieceType.RED;
    static Piece previousPieceClicked = null;
    static boolean isPieceClicked = false;
    static Piece pieceClicked;
    static Tile tileClicked;

    static Tile[][] board = new Tile[COLUMNS][ROWS];
    static Group tileGroup = new Group();
    static Group pieceGroup = new Group();

    private Parent createBoard() {
        Pane root = new Pane();
        root.setPrefSize(COLUMNS * TILE_SIZE,ROWS * TILE_SIZE);
        root.getChildren().addAll(tileGroup,pieceGroup);

        for (int y = 0; y < COLUMNS; y++) {
            for (int x = 0; x < ROWS; x++) {
                Piece.setPiecePosition(x, y);
            }
        }
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createBoard());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void pieceAction() {
        if (isPieceClicked && pieceClicked.getType() != previousPieceClickedType) {
            int oldX = pieceClicked.getPieceX();
            int oldY = pieceClicked.getPieceY();
            int newX = tileClicked.getTileX();
            int newY = tileClicked.getTileY();

            // MOVE or ATTACK
            if (Math.abs(newX - oldX) == 1 && (newY - oldY) == pieceClicked.getType().MOVEDIR && !tileClicked.hasPiece()) {
                movePiece(oldX, oldY, newX, newY);

            } else if (Math.abs(newX - oldX) == 2 && (newY - oldY) == 2 * pieceClicked.getType().MOVEDIR && !tileClicked.hasPiece()) {
                attackingPieceMove(oldX, oldY, newX, newY);
            }
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
