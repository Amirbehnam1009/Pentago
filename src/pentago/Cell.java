package pentago;

/**
 * Determines a cell in the othello game board
 */
class Cell {
    /**
     * Empty state of the cell
     */
    private boolean empty = true;
    /**
     * The piece that fills the cell, if specified
     */
    private Piece piece;

    /**
     * Default construvtor of cell class
     */
    Cell() {
    }

    /**
     * Copy constructor of cell class
     *
     * @param cell cell to copy
     */
    Cell(Cell cell) {
        this.empty = cell.empty;
        this.piece = cell.piece;
    }

    /**
     * Gets the empty state of the cell
     *
     * @return {@code true} if cell is empty, {@code false} otherwise
     */
    boolean isEmpty() {
        return empty;
    }

    /**
     * Puts specified piece in the cell and makes it not empty.
     *
     * @param piece piece that must put in the cell
     */
    void putPiece(Piece piece) {
        this.piece = piece;
        empty = false;
    }

    /**
     * String representation of the cell, even empty or piece representation in the cell.
     *
     * @return string representation of the cell
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return " ○ ";
        } else {
            return " " + piece.toString() + " ";
        }
    }

    /**
     * Checks if the piece in the cell has the same color of input piece.
     *
     * @param piece input piece to check
     * @return {@code true} if the piece in the cell has the same color of input piece, {@code false} otherwise
     */
    boolean isSameColor(Piece piece) {
        return !isEmpty() && this.piece.equals(piece);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return (empty && cell.empty) || (!cell.empty && isSameColor(cell.piece));
    }
}
