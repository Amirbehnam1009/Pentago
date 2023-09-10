package pentago;

import java.util.Scanner;

/**
 * Determines a simple human player
 */
class HumanPlayer extends Player {
    /**
     * Constructor of player
     *
     * @param name player name
     */
    HumanPlayer(String name) {
        super(name);
    }

    /**
     * Gets next move as a string from console and returns that input as player's next move.
     *
     * @return input move string
     */
    String getNextMove() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return line.trim();
    }

    /**
     * Gets next rotation as a string from console and returns that input as player's next rotation.
     *
     * @return input rotation string
     */
    @Override
    String getNextRotation() {
        Scanner scanner = new Scanner(System.in);
        String move = scanner.nextLine().trim();
        return move.trim();
    }
}
