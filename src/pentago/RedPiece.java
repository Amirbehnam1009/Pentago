package pentago;

/**
 * Determines a red piece.
 */
public class RedPiece implements Piece {
    /**
     * Specifies the string presentation of a red piece.
     *
     * @return string presentation of a red piece
     */
    @Override
    public String toString() {
        final String RED_COLOR = "\033[0;31m";
        final String RESET = "\033[0m";
        return RED_COLOR + "●" + RESET;
    }

    /**
     * Checks if current piece is equals to input piece. The logic is checking equality of classes of pieces.
     *
     * @param obj input piece to check equality
     * @return {@code true} if pieces are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass().equals(obj.getClass());
    }
}
