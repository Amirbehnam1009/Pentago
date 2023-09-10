package pentago;

/**
 * Anti-clockwise rotation simulator class.
 */
public class ClockwiseRotation implements Rotation {
    /**
     * Rotates given array of cells as block state clockwise and returns resulting array.
     *
     * @param blockState array of cells as block state
     * @return result of rotation
     */
    @Override
    public Cell[][] rotate(Cell[][] blockState) {
        Cell[][] blockStateTemp = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blockStateTemp[i][j] = blockState[2 - j][i];
            }
        }
        return blockStateTemp;
    }
}
