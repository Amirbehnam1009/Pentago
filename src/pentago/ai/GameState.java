package pentago.ai;

import pentago.Board;
import pentago.MoveStringConverter;
import pentago.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps game state in Minimax computations.
 */
class GameState {
    /**
     * Board state at this level
     */
    private Board board;
    /**
     * utility score of this game state
     */
    private int utility;
    /**
     * determines max or min player
     */
    private boolean maxPlayer;
    /**
     * current player that can move
     */
    private Player turn;
    /**
     * opponent player
     */
    private Player opponent;
    /**
     * Chosen move for this game state
     */
    private String[] move;

    /**
     * Constructor of game state
     *
     * @param board     game state board
     * @param maxPlayer max player
     * @param turn      current player
     * @param opponent  opponent player
     */
    GameState(Board board, boolean maxPlayer, Player turn, Player opponent) {
        this.board = new Board(board);
        this.maxPlayer = maxPlayer;
        this.turn = turn;
        this.opponent = opponent;
    }

    /**
     * Gets utility score of current game state.
     *
     * @return utility score
     */
    int getUtility() {
        return utility;
    }

    /**
     * Sets utility score of current game state.
     *
     * @param utility utility score
     */
    void setUtility(int utility) {
        this.utility = utility;
    }

    /**
     * Checks for finishing game.
     *
     * @return {@code true} if the game finished, {@code false} otherwise
     */
    boolean isGameOver() {
        return board.isGameFinished();
    }

    /**
     * Gets the max or min player.
     *
     * @return {@code true} if is max player, {@code false} otherwise
     */
    boolean isMaxPlayer() {
        return maxPlayer;
    }

    /**
     * Gets current board state.
     *
     * @return current board state
     */
    Board getBoard() {
        return board;
    }

    /**
     * Gets current player.
     *
     * @return current player
     */
    Player getTurn() {
        return turn;
    }

    /**
     * Apply specified move that contains a move string and a rotation string. If applying move causes finishing the
     * game, ignores the rotation;
     *
     * @param theMove an array containing move and rotation string
     */
    void apply(String[] theMove) {
        move = theMove;
        int[] numbers = MoveStringConverter.convertNextMoveToBoardNumber(theMove[0]);
        board.putPiece(turn.getPiece(), numbers[0], numbers[1]);
        if (!board.hasWinner()) {
            board.rotate(MoveStringConverter.getBlockNumberFromRotationString(theMove[1]), MoveStringConverter.getRotationFromRotationString(theMove[1]));
        }
        Player temp = turn;
        turn = opponent;
        opponent = temp;
    }

    /**
     * Gets all available moves in current state of the game.
     *
     * @return all available moves
     */
    List<String[]> getAvailableMoves() {
        List<String[]> result = new ArrayList<>();
        List<int[]> emptyCells = board.getEmptyCells();
        String[] moveStr;
        String move;
        for (int[] emptyCell : emptyCells) {
            move = MoveStringConverter.convertToMoveString(emptyCell[0], emptyCell[1]);
            for (int i = 0; i < 4; i++) {
                moveStr = new String[2];
                moveStr[0] = move;
                moveStr[1] = MoveStringConverter.convertToRotationString(i + 1, true);
                result.add(moveStr);

                moveStr = new String[2];
                moveStr[0] = move;
                moveStr[1] = MoveStringConverter.convertToRotationString(i + 1, false);
                result.add(moveStr);
            }
        }
        return result;
    }

    /**
     * Gets number of all 5-in-a-rows in game board specified player. 5-in-a-row is a state that 5 pieces with same
     * color was in a sequence in vertical, horizontal or diagonal direction.
     *
     * @param player player to check
     * @return all 5-in-a-row count
     */
    int get5InARows(Player player) {
        return board.get5InARows(player.getPiece());
    }

    /**
     * Gets number of all 4-in-a-rows in game board specified player. 4-in-a-row is a state that 4 pieces with same
     * color was in a sequence in vertical, horizontal or diagonal direction.
     *
     * @param player player to check
     * @return all 4-in-a-row count
     */
    int get4InARows(Player player) {
        return board.get4InARows(player.getPiece());
    }

    /**
     * Gets number of all 3-in-a-rows in game board specified player. 3-in-a-row is a state that 3 pieces with same
     * color was in a sequence in vertical, horizontal or diagonal direction.
     *
     * @param player player to check
     * @return all 3-in-a-row count
     */
    int get3InARows(Player player) {
        return board.get3InARows(player.getPiece());
    }

    /**
     * Gets all piece count of specified player that was not located at edges of the board.
     *
     * @param player player to check
     * @return all piece at center of board
     */
    int getPieceAtCenters(Player player) {
        return board.getPieceAtCenter(player.getPiece());

    }

    /**
     * Gets chosen move for this game state.
     *
     * @return chosen move for this game state
     */
    String[] getMove() {
        return move;
    }

    /**
     * sets chosen move for this game state.
     *
     * @param move chosen move for this game state
     */
    void setMove(String[] move) {
        this.move = move;
    }
}
