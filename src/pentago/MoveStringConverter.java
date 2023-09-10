package pentago;

/**
 * Utility class for converting move and rotation string to numbers and indexes and vice versa.
 */
public class MoveStringConverter {
    /**
     * Converts move string to block and cell number. The input string format is "B C" that 'B' part is block number
     * in range 1 to 4 and 'C' part is cell number in range 1 to 9. Computed block and cell numbers are same
     * integer value.
     *
     * @param nextMove move string
     * @return an array with two members, 0 index indicates computed block number and 1 index indicates computed
     * cell number
     */
    public static int[] convertNextMoveToBoardNumber(String nextMove) {
        int[] result = new int[2];
        result[0] = Character.getNumericValue(nextMove.charAt(0));
        result[1] = Character.getNumericValue(nextMove.charAt(2));
        return result;
    }

    /**
     * Computes block number from input rotation string. The input string format is "B R" that 'B' part is block number
     * in range 1 to 4 and 'R ' part is even 'c' for clockwise rotation or 'a' for anti-clockwise rotation. Computed
     * block numbers is same integer value.
     *
     * @param nextMove rotation string
     * @return computed block number
     * cell number
     */
    public static int getBlockNumberFromRotationString(String nextMove) {
        int result;
        result = Character.getNumericValue(nextMove.charAt(0));
        return result;
    }

    /**
     * Computes rotation from input rotation string. The input string format is "B R" that 'B' part is block number
     * in range 1 to 4 and 'R ' part is even 'c' for clockwise rotation or 'a' for anti-clockwise rotation. Computed
     * rotation is an object that performs rotation at desired direction.
     *
     * @param nextMove rotation string
     * @return computed rotation object
     * cell number
     */
    public static Rotation getRotationFromRotationString(String nextMove) {
        if (nextMove.charAt(2) == 'c') {
            return new ClockwiseRotation();
        }
        return new AntiClockwiseRotation();
    }

    /**
     * Converts input block and cell number to move string. The result move string format is "B R" that 'B' part is
     * block number in range 1 to 4 and 'R ' part is even 'c' for clockwise rotation or 'a' for anti-clockwise rotation.
     * Computed block numbers is same integer value.
     *
     * @param blockNum block number
     * @param cellNum  cell number
     * @return move string
     */
    public static String convertToMoveString(int blockNum, int cellNum) {
        return blockNum + " " + cellNum;
    }

    /**
     * Converts block number and rotation to rotation string. The result rotation string format is "B R" that 'B' part
     * is block number in range 1 to 4 and 'R ' part is even 'c' for clockwise rotation or 'a' for anti-clockwise
     * rotation. Computed rotation is an object that performs rotation at desired direction.
     *
     * @param blockNum  block number
     * @param clockwise {@code true} if the rotation is clockwise, {@code false} otherwise
     * @return rotation string
     */
    public static String convertToRotationString(int blockNum, boolean clockwise) {
        return blockNum + " " + (clockwise ? "c" : "a");
    }
}
