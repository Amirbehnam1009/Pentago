package pentago;

/**
 * Rotation class that simulates a rotation.
 */
public interface Rotation {
    /**
     * Rotates given array of cells as block state and returns resulting array.
     *
     * @param blockState array of cells as block state
     * @return result of rotation
     */
    Cell[][] rotate(Cell[][] blockState);
}
