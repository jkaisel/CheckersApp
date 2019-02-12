package sample;

public enum PieceType {
    RED(1), WHITE(-1);

    final int MOVEDIR;

    PieceType(int MOVEDIR){
        this.MOVEDIR = MOVEDIR;
    }

}
