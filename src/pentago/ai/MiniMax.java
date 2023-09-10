package pentago.ai;

import pentago.Board;
import pentago.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements the Minimax decision rule.
 * For more information about Minimax decision rule,
 * see <a href="https://en.wikipedia.org/wiki/Minimax">https://en.wikipedia.org/wiki/Minimax</a>
 */
public class MiniMax {
    /**
     * Maximum level of Minimax checking
     */
    private int maxPly;
    /**
     * Game players
     */
    private Player[] players;
    /**
     * Current turn player
     */
    private Player turn;

    /**
     * Constructor of Minimax
     *
     * @param maxPly  Maximum level of Minimax checking
     * @param players Game players
     */
    public MiniMax(int maxPly, Player[] players) {
        this.maxPly = maxPly;
        this.players = players;
    }

    /**
     * Runs Minimax decision rule to choose a move that maximizes turn player score in current state of the game.
     *
     * @param board game state
     * @param turn  current player
     * @return best move for player
     */
    public String[] run(Board board, Player turn) {
        GameState gameState = new GameState(board, true, turn, getOpponent(turn));
        this.turn = turn;
        return miniMax(gameState, 0).getMove();
    }

    /**
     * Minimax execution in specified layer for specified game state.
     *
     * @param gameState current game state
     * @param ply       layer of execution
     * @return game state with best score
     */
    private GameState miniMax(GameState gameState, int ply) {
        if (ply++ == maxPly || gameState.isGameOver()) {
            setScore(gameState);
            return gameState;
        }
        if (gameState.isMaxPlayer()) {
            getMax(gameState, ply);
        } else {
            getMin(gameState, ply);
        }
        return gameState;
    }

    /**
     * Computes and sets utility score for specified game state. It uses following heuristics based on
     * <a href="https://www.ke.tu-darmstadt.de/lehre/arbeiten/bachelor/2011/Buescher_Niklas.pdf">
     * https://www.ke.tu-darmstadt.de/lehre/arbeiten/bachelor/2011/Buescher_Niklas.pdf
     * </a>:
     * <br>
     * 5-in-a-row 100,000<br>
     * 4-in-a-row 1,000<br>
     * 3-in-a-row 100<br>
     * piece in center 5<br>
     * piece at board 0<br>
     *
     * @param gameState game state to compute utility score
     */
    private void setScore(GameState gameState) {
        int fiveInARowCount = gameState.get5InARows(turn) - gameState.get5InARows(getOpponent(turn));
        int fourInARowCount = gameState.get4InARows(turn) - gameState.get4InARows(getOpponent(turn));
        int threeInARowCount = gameState.get3InARows(turn) - gameState.get3InARows(getOpponent(turn));
        int pieceAtCenterCount = gameState.getPieceAtCenters(turn) - gameState.getPieceAtCenters(getOpponent(turn));

        int score = fiveInARowCount * 100000 + fourInARowCount * 1000 + threeInARowCount * 100 + pieceAtCenterCount * 5;
        gameState.setUtility(score);
    }

    /**
     * Performs max part of Minimax decision rule for turn player to maximize turn player score.
     *
     * @param gameState current game state
     * @param ply       level of computation
     */
    private void getMax(GameState gameState, int ply) {
        int bestScore = Integer.MIN_VALUE;
        List<String[]> bestMoves = new ArrayList<>();

        Player opponent = getOpponent(gameState.getTurn());
        List<String[]> availableMoves = gameState.getAvailableMoves();
        for (String[] theMove : availableMoves) {
            GameState child = new GameState(gameState.getBoard(), !gameState.isMaxPlayer(), gameState.getTurn(), opponent);
            child.apply(theMove);

            miniMax(child, ply);

            if (child.getUtility() > bestScore) {
                bestScore = child.getUtility();
                bestMoves = new ArrayList<>();
                bestMoves.add(theMove);
            } else if (child.getUtility() == bestScore) {
                bestMoves.add(theMove);
            }
        }
        gameState.setMove(selectBestMove(bestMoves));
        gameState.setUtility(bestScore);
    }

    /**
     * Performs pin part of Minimax decision rule for turn player to minimize turn player score that equals to
     * maximizing opponent player score.
     *
     * @param gameState current game state
     * @param ply       level of computation
     */
    private void getMin(GameState gameState, int ply) {
        int bestScore = Integer.MAX_VALUE;
        List<String[]> bestMoves = new ArrayList<>();

        Player opponent = getOpponent(gameState.getTurn());
        List<String[]> availableMoves = gameState.getAvailableMoves();
        for (String[] theMove : availableMoves) {
            GameState child = new GameState(gameState.getBoard(), !gameState.isMaxPlayer(), gameState.getTurn(), opponent);
            child.apply(theMove);

            miniMax(child, ply);

            if (child.getUtility() < bestScore) {
                bestScore = child.getUtility();
                bestMoves = new ArrayList<>();
                bestMoves.add(theMove);
            } else if (child.getUtility() == bestScore) {
                bestMoves.add(theMove);
            }
        }
        gameState.setMove(selectBestMove(bestMoves));
        gameState.setUtility(bestScore);
    }

    /**
     * Selects best move from equal score moves. It first filters moves that result to corner and then randomly chooses
     * from remain moves.
     *
     * @param bestMoves all moves to choose one
     * @return chosen move
     */
    private String[] selectBestMove(List<String[]> bestMoves) {
        if (bestMoves.isEmpty()) {
            return null;
        }
        if (bestMoves.size() == 1) {
            return bestMoves.get(0);
        }
        List<String[]> filtered = filterCorners(bestMoves);
        if (!filtered.isEmpty()) {
            if (filtered.size() == 1) {
                return filtered.get(0);
            }
            Random random = new Random();
            int index = random.nextInt(filtered.size());
            return filtered.get(index);
        } else {
            Random random = new Random();
            int index = random.nextInt(bestMoves.size());
            return bestMoves.get(index);
        }
    }

    /**
     * Filters moves that result to corner.
     *
     * @param bestMoves all moves to filter
     * @return filtered moves
     */
    private List<String[]> filterCorners(List<String[]> bestMoves) {
        List<String[]> result = new ArrayList<>();
        for (String[] move : bestMoves) {
            if (!moveResultsToCorner(move)) {
                result.add(move);
            }
        }
        return result;
    }

    /**
     * Checks if specified move result to corner.
     *
     * @param move move to check
     * @return {@code true} if move results to corder, {@code false} otherwise
     */
    private boolean moveResultsToCorner(String[] move) {
        if (move[1].charAt(0) != move[0].charAt(0)) {
            switch (move[0].charAt(0)) {
                case '1':
                    return move[0].charAt(2) == '1';
                case '2':
                    return move[0].charAt(2) == '3';
                case '3':
                    return move[0].charAt(2) == '7';
                case '4':
                    return move[0].charAt(2) == '9';
            }
        } else {
            switch (move[1].charAt(2)) {
                case 'c':
                    switch (move[0].charAt(0)) {
                        case '1':
                            return move[0].charAt(2) == '7';
                        case '2':
                            return move[0].charAt(2) == '1';
                        case '3':
                            return move[0].charAt(2) == '9';
                        case '4':
                            return move[0].charAt(2) == '3';
                    }
                case 'a':
                    switch (move[0].charAt(0)) {
                        case '1':
                            return move[0].charAt(2) == '3';
                        case '2':
                            return move[0].charAt(2) == '9';
                        case '3':
                            return move[0].charAt(2) == '1';
                        case '4':
                            return move[0].charAt(2) == '7';
                    }
            }
        }
        return false;
    }

    /**
     * Gets opponent player.
     *
     * @param player specified player
     * @return opponet player
     */
    private Player getOpponent(Player player) {
        if (player.equals(players[0])) {
            return players[1];
        } else {
            return players[0];
        }
    }
}
