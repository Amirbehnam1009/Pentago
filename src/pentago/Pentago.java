package pentago;

import pentago.ai.MiniMax;

import java.util.Random;
import java.util.Scanner;

/**
 * The pentago game class that performs the pentago game.
 */
public class Pentago {
    /**
     * The board of the game, all movements and rules will be applied to this board.
     */
    private Board board;
    /**
     * Current turn of the game
     */
    private Player turn;
    /**
     * A two member array that holds the game players, first member is player1 and second member is player2
     */
    private Player[] players = new Player[2];

    /**
     * The main method of the pentago game.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        Pentago pentago = new Pentago();
        pentago.play();

    }

    /**
     * Operates as the playing process of the pentago game is implemented here. It asks for determining the game type by
     * selecting from one player (play with computer) and two player (play with opponent) and handles moves and board
     * state and game rules and regulations and finally determines the winner (or tie state).
     */
    private void play() {
        String game = selectGameMode();
        while (!game.equals("0")) {
            board = new Board();
            switch (game) {
                case "2":
                    playHumanToHuman();
                    break;
                case "1":
                    playHumanToComputer();
                    break;
                default:
                    System.out.println("Invalid game mode, try again");
                    play();
            }
            game = selectGameMode();
        }
    }

    /**
     * Gets game mode from user and returns it.
     *
     * @return game mode
     */
    private String selectGameMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select game mode,");
        System.out.println("0. Exit");
        System.out.println("1. One player (play with computer)");
        System.out.println("2. Two player (play with opponent)");
        return scanner.nextLine().trim();
    }

    /**
     * Performs two player game mode that is playing human to human.
     */
    private void playHumanToHuman() {
        players[0] = new HumanPlayer("Player1");
        players[1] = new HumanPlayer("Player2");
        doPlaying();
    }

    /**
     * Performs two player game mode that is playing human to computer. The player1 will be human player
     * and the player2 will be computer player.
     */
    private void playHumanToComputer() {
        players[0] = new HumanPlayer("Player1");
        MiniMax miniMax = new MiniMax(2, players);
        players[1] = new ComputerPlayer(board, miniMax);
        doPlaying();
    }

    /**
     * The game cycle will be handled here.
     */
    private void doPlaying() {
        randomInitializePlayers();
        boolean end = false;
        while (!end) {
            board.printBoard();
            printTurn();
            String nextMove;
            nextMove = getNextValidMoveString();
            int[] indexes = MoveStringConverter.convertNextMoveToBoardNumber(nextMove);
            while (board.isInvalidMove(indexes[0], indexes[1])) {
                System.out.println(turn.toString() + " can't have \"" + nextMove + "\" move, please choose a valid move.");
                nextMove = getNextValidMoveString();
                indexes = MoveStringConverter.convertNextMoveToBoardNumber(nextMove);
            }
            board.putPiece(turn.getPiece(), indexes[0], indexes[1]);
            board.printBoard();
            if (board.hasWinner()) {
                board.printBoard();
                printGameResult();
                end = true;
            } else {
                printTurnRotation();
                String rotationString;
                rotationString = turn.getNextRotation();
                while (!validateRotationString(rotationString)) {
                    System.out.println("Input format must be like \"B R\", B is a number in range [1-4] and R is a " +
                            "character between 'c' for clockwise and 'a' for anticlockwise or only 's' for skip.");
                    rotationString = turn.getNextRotation();
                }
                boolean skipRotation = rotationString.equals("s") && board.hasSymmetricalBlock();
                while (rotationString.equals("s") && !board.hasSymmetricalBlock()) {
                    System.out.println("Can't skip rotation, There is no symmetrical block!");
                    rotationString = turn.getNextRotation();
                }
                if (!skipRotation) {
                    int blockNumber = MoveStringConverter.getBlockNumberFromRotationString(rotationString);
                    Rotation rotation = MoveStringConverter.getRotationFromRotationString(rotationString);
                    board.rotate(blockNumber, rotation);
                }
                if (board.isGameFinished()) {
                    board.printBoard();
                    printGameResult();
                    end = true;
                }
                changeTurn();
            }
        }
    }

    /**
     * Gets next valid move from turn player and validates syntax of input string and asks for valid move string while
     * input string is invalid. finally returns the valid move string.
     *
     * @return valid move string
     */
    private String getNextValidMoveString() {
        String nextMove;
        nextMove = turn.getNextMove();
        //gets next move from user until input string has valid syntax
        while (isInvalidateMoveString(nextMove)) {
            System.out.println("Input format must be like \"B C\", B is a number in range [1-4] and C is a number in range [1-9].");
            nextMove = turn.getNextMove();
        }
        return nextMove;
    }

    /**
     * Print current player turn before asking for selected rotation of current player.
     */
    private void printTurnRotation() {
        System.out.println(turn.toString() + " rotation/skip:");
    }

    /**
     * Determines next turn of the game and changes {@link Pentago#turn} respectively by simply giving turn
     * to the other player.
     */
    private void changeTurn() {
        //give turn to other user
        if (turn.equals(players[0])) {
            turn = players[1];
        } else {
            turn = players[0];
        }
    }

    /**
     * This method determines the winner or tie state and prints the game results.
     */
    private void printGameResult() {
        Piece winnerPiece = board.getWinnerPiece();
        if (winnerPiece == null) {
            System.out.println("Tie!!");
        } else if (winnerPiece.equals(players[0].getPiece())) {
            System.out.println(players[0].toString() + " Wins");
        } else if (winnerPiece.equals(players[1].getPiece())) {
            System.out.println(players[1].toString() + " Wins");
        } else {
            System.out.println("Tie!!");
        }

    }

    /**
     * Validates syntax of the move string that entered be user. The input string format is "B C" that 'B' part is
     * block number in range 1 to 4 and 'C' part is cell number in range 1 to 9.
     *
     * @param moveString validating move string
     * @return {@code true} if the move string is valid, {@code false} otherwise
     */
    private boolean isInvalidateMoveString(String moveString) {
        char blockNumber;
        char cellNumber;
        if (moveString.length() != 3) {
            return true;
        }
        blockNumber = moveString.charAt(0);
        cellNumber = moveString.charAt(2);
        if (blockNumber != '1' && blockNumber != '2' && blockNumber != '3' && blockNumber != '4') {
            return true;
        }
        if (moveString.charAt(1) != ' ') {
            return true;
        }
        if (cellNumber != '1' && cellNumber != '2' && cellNumber != '3' && cellNumber != '4' && cellNumber != '5' &&
                cellNumber != '6' && cellNumber != '7' && cellNumber != '8' && cellNumber != '9') {
            return true;
        }

        return false;
    }

    /**
     * Validates syntax of the rotation string that entered be user. The input string format is "B R" that 'B' part is
     * block number in range 1 to 4 and 'R ' part is even 'c' for clockwise rotation or 'a' for anti-clockwise rotation.
     *
     * @param rotationString validating rotation string
     * @return {@code true} if the rotation string is valid, {@code false} otherwise
     */
    private boolean validateRotationString(String rotationString) {
        char blockNumber;
        char rotation;
        if (rotationString.length() != 3) {
            return rotationString.equals("s");
        }
        blockNumber = rotationString.charAt(0);
        rotation = rotationString.charAt(2);
        if (blockNumber != '1' && blockNumber != '2' && blockNumber != '3' && blockNumber != '4') {
            return false;
        }
        if (rotationString.charAt(1) != ' ') {
            return false;
        }
        if (rotation != 'c' && rotation != 'a') {
            return false;
        }

        return true;


    }

    /**
     * Print current player turn before asking for selected move of current player.
     */
    private void printTurn() {
        System.out.println(turn.toString() + ":");
    }

    /**
     * Randomly assigns piece color of two players and prints their colors to console.
     */
    private void randomInitializePlayers() {
        Random random = new Random();
        int player1Color = random.nextInt(2) + 1;
        if (player1Color == 1) {
            players[0].setPiece(Block.RED_PIECE);
            players[1].setPiece(Block.BLACK_PIECE);
            turn = players[0];
        } else {
            players[0].setPiece(Block.BLACK_PIECE);
            players[1].setPiece(Block.RED_PIECE);
            turn = players[1];
        }
        System.out.println(players[0].toString() + " - " + players[1].toString());
    }
}
