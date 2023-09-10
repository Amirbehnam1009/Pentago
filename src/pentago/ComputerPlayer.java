package pentago;

import pentago.ai.MiniMax;

/**
 * Determines a semi-intelligent computer player that can choose its next move and rotation based on Minimax decision rule.
 * For more information about Minimax decision rule,
 * see <a href="https://en.wikipedia.org/wiki/Minimax">https://en.wikipedia.org/wiki/Minimax</a>
 */
class ComputerPlayer extends Player {
    /**
     * The pentago game board
     */
    private Board board;
    /**
     * Minimax decision rule
     * For more information about Minimax decision rule,
     * see <a href="https://en.wikipedia.org/wiki/Minimax">https://en.wikipedia.org/wiki/Minimax</a>
     */
    private MiniMax miniMax;
    /**
     * Next chosen rotation
     */
    private String nextRotation;

    /**
     * Constructor that initializes player.
     * For more information about Minimax decision rule,
     * see <a href="https://en.wikipedia.org/wiki/Minimax">https://en.wikipedia.org/wiki/Minimax</a>
     *
     * @param board   The othello game board
     * @param miniMax Minimax decision rule
     */
    ComputerPlayer(Board board, MiniMax miniMax) {
        super("Computer");
        this.board = board;
        this.miniMax = miniMax;
    }

    /**
     * Chooses the best move and rotation from available moves to be used as the current move of computer player in play to computer
     * game mode.<br>
     * It uses Minimax decision rule to perform choosing next move and rotation.
     * For more information about Minimax decision rule,
     * see <a href="https://en.wikipedia.org/wiki/Minimax">https://en.wikipedia.org/wiki/Minimax</a>
     *
     * @return next move string for computer player
     */
    @Override
    String getNextMove() {
        String[] moveStrings = miniMax.run(board, this);
        nextRotation = moveStrings[1];
        System.out.println(moveStrings[0]);
        return moveStrings[0];
    }

    /**
     * Gets chosen next rotation computed using Minimax decision rule.
     *
     * @return next rotation string for computer player
     * @see ComputerPlayer#getNextMove()
     */
    @Override
    String getNextRotation() {
        System.out.println(nextRotation);
        return nextRotation;
    }
}
