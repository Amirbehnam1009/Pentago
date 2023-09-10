package pentago;

/**
 * A pentago player
 */
public abstract class Player {
    /**
     * Player display name
     */
    private String name;
    /**
     * Player piece that can put on board
     */
    private Piece piece;

    /**
     * Constructor of player
     *
     * @param name player name
     */
    Player(String name) {
        this.name = name;
    }

    /**
     * Gets player's piece.
     *
     * @return player's piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets player piece.
     *
     * @param piece piece to set for player
     */
    void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gets next move that player chooses.
     *
     * @return next move of player
     */
    abstract String getNextMove();

    /**
     * Gets next retation that player chooses.
     *
     * @return next rotation of player
     */
    abstract String getNextRotation();

    /**
     * Gets display string of the player.
     *
     * @return display string of the player
     */
    @Override
    public String toString() {
        return name + " (" + piece.toString() + ")";
    }

}
