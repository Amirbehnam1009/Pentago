package pentago;

/**
 * Determines a piece.
 */
interface Piece {
    /**
     * Specifies the string presentation of a piece.
     *
     * @return string presentation of a piece
     */
    String toString();

    /**
     * Checks if current piece is equals to input piece. The logic is checking equality of classes of pieces.
     *
     * @param obj input piece to check equality
     * @return {@code true} if pieces are equal, {@code false} otherwise
     */
    boolean equals(Object obj);
}
