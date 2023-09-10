package pentago;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class to manage board of pentago game. This class manages current state of game board and takes all actions
 * that take place when a move or rotation occurs.
 */
public class Board {
    /**
     * An 2 by 2 array of blocks each contains 9 cells that keeps current board state
     */
    private Block[][] blocks = new Block[2][2];

    /**
     * Constructor of board class
     */
    Board() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                blocks[i][j] = new Block();
            }
        }
    }

    /**
     * Copy constructor of board class
     *
     * @param board board to copy
     */
    public Board(Board board) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                blocks[i][j] = new Block(board.blocks[i][j]);
            }
        }
    }

    /**
     * Prints current state of board to console.
     */
    void printBoard() {
        System.out.println("-----------------------");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|");
                System.out.print(blocks[i][0].getRowString(j));
                System.out.print("|");
                System.out.print(blocks[i][1].getRowString(j));
                System.out.println("|");
            }
            System.out.println("-----------------------");
        }
    }

    /**
     * Checks the validity of putting a piece in block and cell determined by block and cell numbers.
     *
     * @param blockNumber block number to check
     * @param cellNumber  cell number in block to check
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    boolean isInvalidMove(int blockNumber, int cellNumber) {
        int[] blockIndex = getBlockIndex(blockNumber);
        return blocks[blockIndex[0]][blockIndex[1]].isInvalidMove(cellNumber);
    }

    /**
     * Converts block number to cell row and column index.
     *
     * @param blockNumber block number
     * @return an array with two members, 0 index indicates computed row and 1 index indicates computed column
     */
    private int[] getBlockIndex(int blockNumber) {
        int[] result = new int[2];
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                counter++;
                if (blockNumber == counter) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    /**
     * Applying a move by putting specified piece in specified cell of specified block.
     *
     * @param piece       piece to put
     * @param blockNumber block number to put piece
     * @param cellNumber  cell number in block to put piece
     */
    public void putPiece(Piece piece, int blockNumber, int cellNumber) {
        int[] blockIndex = getBlockIndex(blockNumber);
        blocks[blockIndex[0]][blockIndex[1]].putPieceInBlock(piece, cellNumber);
    }

    /**
     * Checks if current state of board has a winner that is a 5-in-a-row. A 5-in-a-row is state that 5 pieces with
     * same color was in a sequence in vertical, horizontal or diagonal direction.
     *
     * @return {@code true} if the game has winner, {@code false} otherwise
     */
    public boolean hasWinner() {
        return isColorWon(Block.RED_PIECE) || isColorWon(Block.BLACK_PIECE);
    }

    /**
     * Gets winner piece of board or {@code null} if there is no winner, even game not finished or tie state.
     *
     * @return piece color of winner or null if winner does not exist
     */
    Piece getWinnerPiece() {
        if (isColorWon(Block.RED_PIECE)) {
            return Block.RED_PIECE;
        }
        if (isColorWon(Block.BLACK_PIECE)) {
            return Block.BLACK_PIECE;
        }
        return null;
    }

    /**
     * Checks if specified piece won that is a 5-in-a-row. A 5-in-a-row is state that 5 pieces with same color was in a
     * sequence in vertical, horizontal or diagonal direction.
     *
     * @param piece piece that win checks
     * @return {@code true} Checks if specified piece won, {@code false} otherwise
     */
    private boolean isColorWon(Piece piece) {
        return checkRowWin(piece) || checkColumnWin(piece) || checkDiagonalWin(piece);
    }

    /**
     * Checks piece win state in diagonal direction.
     *
     * @param piece piece that win checks
     * @return {@code true} Checks if specified piece won in diagonal direction, {@code false} otherwise
     */
    private boolean checkDiagonalWin(Piece piece) {
        if (blocks[0][0].hasMainDiameterFull(piece) && blocks[1][1].has2CellsInMainDiameterWithColorFromBeginning(piece)) {
            return true;
        }
        if (blocks[0][1].hasSecondaryDiameterFull(piece) && blocks[1][0].has2CellsInSecondaryDiameterWithColorFromBeginning(piece)) {
            return true;
        }
        if (blocks[1][0].hasSecondaryDiameterFull(piece) && blocks[0][1].has2CellsInSecondaryDiameterWithColorFromEnd(piece)) {
            return true;
        }
        if (blocks[1][1].hasMainDiameterFull(piece) && blocks[0][0].has2CellsInMainDiameterWithColorFromEnd(piece)) {
            return true;
        }
        if (blocks[0][0].hasUnderMainDiameterFull(piece)
                && blocks[1][0].isCellInBlockExists(piece, 3)
                && blocks[1][1].hasUnderMainDiameterFull(piece)) {
            return true;
        }
        if (blocks[0][0].hasAboveMainDiameterFull(piece)
                && blocks[0][1].isCellInBlockExists(piece, 7)
                && blocks[1][1].hasAboveMainDiameterFull(piece)) {
            return true;
        }
        if (blocks[0][1].hasUnderSecondaryDiameterFull(piece)
                && blocks[1][1].isCellInBlockExists(piece, 1)
                && blocks[1][0].hasAboveSecondaryDiameterFull(piece)) {
            return true;
        }
        if (blocks[0][1].hasAboveSecondaryDiameterFull(piece)
                && blocks[0][0].isCellInBlockExists(piece, 7)
                && blocks[1][0].hasAboveSecondaryDiameterFull(piece)) {
            return true;
        }
        return false;
    }

    /**
     * Checks piece win state in column direction.
     *
     * @param piece piece that win checks
     * @return {@code true} Checks if specified piece won in column direction, {@code false} otherwise
     */
    private boolean checkColumnWin(Piece piece) {
        ArrayList<Integer> indexes;
        indexes = blocks[0][0].getFullColumns(piece);
        for (Integer index : indexes) {
            if (blocks[1][0].has2CellsInColumnWithColorFromBeginning(piece, index)) {
                return true;
            }
        }
        indexes = blocks[0][1].getFullColumns(piece);
        for (Integer index : indexes) {
            if (blocks[1][1].has2CellsInColumnWithColorFromBeginning(piece, index)) {
                return true;
            }
        }
        indexes = blocks[1][0].getFullColumns(piece);
        for (Integer index : indexes) {
            if (blocks[0][0].has2CellsInColumnWithColorFromEnd(piece, index)) {
                return true;
            }
        }
        indexes = blocks[1][1].getFullColumns(piece);
        for (Integer index : indexes) {
            if (blocks[0][1].has2CellsInColumnWithColorFromEnd(piece, index)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks piece win state in row direction.
     *
     * @param piece piece that win checks
     * @return {@code true} Checks if specified piece won in row direction, {@code false} otherwise
     */
    private boolean checkRowWin(Piece piece) {
        ArrayList<Integer> indexes = blocks[0][0].getFullRows(piece);
        for (Integer index : indexes) {
            if (blocks[0][1].has2CellsInRowWithColorFromBeginning(piece, index)) {
                return true;
            }
        }
        indexes = blocks[0][1].getFullRows(piece);
        for (Integer index : indexes) {
            if (blocks[0][0].has2CellsInRowWithColorFromEnd(piece, index)) {
                return true;
            }
        }
        indexes = blocks[1][0].getFullRows(piece);
        for (Integer index : indexes) {
            if (blocks[1][1].has2CellsInRowWithColorFromBeginning(piece, index)) {
                return true;
            }
        }
        indexes = blocks[1][1].getFullRows(piece);
        for (Integer index : indexes) {
            if (blocks[1][0].has2CellsInRowWithColorFromEnd(piece, index)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rotates specified block with specified rotation.
     *
     * @param blockIndex block to rotate
     * @param rotation   rotation
     */
    public void rotate(int blockIndex, Rotation rotation) {
        int[] block = getBlockIndex(blockIndex);
        blocks[block[0]][block[1]].rotate(rotation);
    }

    /**
     * Checks if the game finished, even by winning or tie.
     *
     * @return {@code true} if the finished, {@code false} otherwise
     */
    public boolean isGameFinished() {
        return hasWinner() || isTie();
    }

    /**
     * Checks if current game state is a tie state. Tie state is when all cells are full and no winner exist.
     *
     * @return {@code true} if game state is tie, {@code false} otherwise
     */
    private boolean isTie() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (blocks[i][j].hasEmpty()) {
                    return false;
                }
            }
        }
        return !hasWinner();
    }

    /**
     * Checks if current game state has at least one symmetric block. A symmetric block is a block that if rotates
     * in both clockwise and anti-clockwise directions will not change.
     *
     * @return {@code true} if current game state has at least one symmetric block, {@code false} otherwise
     */
    boolean hasSymmetricalBlock() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (blocks[i][j].isSymmetric()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets block number and cell number of all empty cells in game board.
     *
     * @return list of all empty block and cell numbers
     */
    public List<int[]> getEmptyCells() {
        List<int[]> result = new ArrayList<>();
        int[] emptyCell;
        int counter = 1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                List<Integer> emptyCellsOfBlock = blocks[i][j].getEmptyCells();
                for (int emptyCellNum : emptyCellsOfBlock) {
                    emptyCell = new int[]{counter, emptyCellNum};
                    result.add(emptyCell);
                }
                counter++;
            }
        }
        return result;
    }

    /**
     * Gets number of all 5-in-a-rows in game board. 5-in-a-row is a state that 5 pieces with same color was in a
     * sequence in vertical, horizontal or diagonal direction.
     *
     * @param piece piece color to check
     * @return all 5-in-a-row count
     */
    public int get5InARows(Piece piece) {
        if (isColorWon(piece)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Gets number of all 4-in-a-rows in game board. 4-in-a-row is a state that 4 pieces with same color was in a
     * sequence in vertical, horizontal or diagonal direction.
     *
     * @param piece piece color to check
     * @return all 4-in-a-row count
     */
    public int get4InARows(Piece piece) {
        return get4sInRow(piece) + get4sInColumn(piece) + get4sInDiameter(piece);
    }

    /**
     * Gets number of all 4-in-a-rows in game board in horizontal direction. for more information about 4-in-a-row see
     * {@link Board#get4InARows(Piece)}
     *
     * @param piece piece color to check
     * @return all 4-in-a-row count
     */
    private int get4sInRow(Piece piece) {
        int counter = 0;
        ArrayList<Integer> indexes;
        indexes = blocks[0][0].getFullRows(piece);
        for (Integer i : indexes) {
            if (blocks[0][1].isCellInBlockExists(piece, 3 * i + 1)) {
                counter++;
            }
        }
        indexes = blocks[0][1].getFullRows(piece);
        for (Integer i : indexes) {
            if (blocks[0][0].isCellInBlockExists(piece, 3 * (i + 1))) {
                counter++;
            }
        }
        indexes = blocks[1][0].getFullRows(piece);
        for (Integer i : indexes) {
            if (blocks[1][1].isCellInBlockExists(piece, 3 * i + 1)) {
                counter++;
            }
        }
        indexes = blocks[1][1].getFullRows(piece);
        for (Integer i : indexes) {
            if (blocks[1][0].isCellInBlockExists(piece, 3 * (i + 1))) {
                counter++;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (blocks[0][0].has2CellsInRowWithColorFromEnd(piece, i) && blocks[0][1].has2CellsInRowWithColorFromBeginning(piece, i)) {
                counter++;
            }
            if (blocks[1][0].has2CellsInRowWithColorFromEnd(piece, i) && blocks[1][1].has2CellsInRowWithColorFromBeginning(piece, i)) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Gets number of all 4-in-a-rows in game board in vertical direction. for more information about 4-in-a-row see
     * {@link Board#get4InARows(Piece)}
     *
     * @param piece piece color to check
     * @return all 4-in-a-row count
     */
    private int get4sInColumn(Piece piece) {
        int counter = 0;
        ArrayList<Integer> indexes;
        indexes = blocks[0][0].getFullColumns(piece);
        for (Integer i : indexes) {
            if (blocks[1][0].isCellInBlockExists(piece, i + 1)) {
                counter++;
            }
        }
        indexes = blocks[0][1].getFullColumns(piece);
        for (Integer i : indexes) {
            if (blocks[1][1].isCellInBlockExists(piece, i + 1)) {
                counter++;
            }
        }
        indexes = blocks[1][0].getFullRows(piece);
        for (Integer i : indexes) {
            if (blocks[0][0].isCellInBlockExists(piece, 7 + i)) {
                counter++;
            }
        }
        indexes = blocks[1][1].getFullRows(piece);
        for (Integer i : indexes) {
            if (blocks[0][1].isCellInBlockExists(piece, 7 + i)) {
                counter++;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (blocks[0][0].has2CellsInColumnWithColorFromEnd(piece, i) && blocks[1][0].has2CellsInColumnWithColorFromBeginning(piece, i)) {
                counter++;
            }
            if (blocks[0][1].has2CellsInColumnWithColorFromEnd(piece, i) && blocks[1][1].has2CellsInColumnWithColorFromBeginning(piece, i)) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Gets number of all 4-in-a-rows in game board in diagonal direction. for more information about 4-in-a-row see
     * {@link Board#get4InARows(Piece)}
     *
     * @param piece piece color to check
     * @return all 4-in-a-row count
     */
    private int get4sInDiameter(Piece piece) {
        int counter = 0;
        if (blocks[0][0].hasMainDiameterFull(piece) && blocks[1][1].isCellInBlockExists(piece, 1)) {
            counter++;
        }
        if (blocks[0][0].has2CellsInMainDiameterWithColorFromEnd(piece) && blocks[1][1].has2CellsInMainDiameterWithColorFromBeginning(piece)) {
            counter++;
        }
        if (blocks[0][0].isCellInBlockExists(piece, 9) && blocks[1][1].hasMainDiameterFull(piece)) {
            counter++;
        }
        if (blocks[0][0].hasAboveMainDiameterFull(piece) && blocks[0][1].isCellInBlockExists(piece, 7) && blocks[1][1].isCellInBlockExists(piece, 2)) {
            counter++;
        }
        if (blocks[0][0].isCellInBlockExists(piece, 6) && blocks[0][1].isCellInBlockExists(piece, 7) && blocks[1][1].hasAboveMainDiameterFull(piece)) {
            counter++;
        }
        if (blocks[0][0].hasUnderMainDiameterFull(piece) && blocks[1][0].isCellInBlockExists(piece, 3) && blocks[1][1].isCellInBlockExists(piece, 4)) {
            counter++;
        }
        if (blocks[0][0].isCellInBlockExists(piece, 8) && blocks[1][0].isCellInBlockExists(piece, 3) && blocks[1][1].hasUnderMainDiameterFull(piece)) {
            counter++;
        }
        if (blocks[0][1].hasSecondaryDiameterFull(piece) && blocks[1][0].isCellInBlockExists(piece, 3)) {
            counter++;
        }
        if (blocks[0][1].has2CellsInSecondaryDiameterWithColorFromEnd(piece) && blocks[1][0].has2CellsInSecondaryDiameterWithColorFromBeginning(piece)) {
            counter++;
        }
        if (blocks[0][1].isCellInBlockExists(piece, 7) && blocks[1][0].hasSecondaryDiameterFull(piece)) {
            counter++;
        }
        if (blocks[0][1].hasAboveSecondaryDiameterFull(piece) && blocks[0][0].isCellInBlockExists(piece, 9) && blocks[1][0].isCellInBlockExists(piece, 2)) {
            counter++;
        }
        if (blocks[0][1].isCellInBlockExists(piece, 5) && blocks[0][0].isCellInBlockExists(piece, 9) && blocks[1][0].hasAboveSecondaryDiameterFull(piece)) {
            counter++;
        }
        if (blocks[0][1].hasUnderSecondaryDiameterFull(piece) && blocks[1][1].isCellInBlockExists(piece, 1) && blocks[1][0].isCellInBlockExists(piece, 6)) {
            counter++;
        }
        if (blocks[0][1].isCellInBlockExists(piece, 8) && blocks[1][1].isCellInBlockExists(piece, 1) && blocks[1][0].hasUnderSecondaryDiameterFull(piece)) {
            counter++;
        }
        return counter;
    }

    /**
     * Gets number of all 3-in-a-rows in game board. 3-in-a-row is a state that 3 pieces with same color was in a
     * sequence in vertical, horizontal or diagonal direction.
     *
     * @param piece piece color to check
     * @return all 3-in-a-row count
     */
    public int get3InARows(Piece piece) {
        return get3sInRow(piece) + get3sInColumn(piece) + get3sInDiameter(piece);
    }

    /**
     * Gets number of all 3-in-a-rows in game board in horizontal direction. for more information about 3-in-a-row see
     * {@link Board#get4InARows(Piece)}
     *
     * @param piece piece color to check
     * @return all 3-in-a-row count
     */
    private int get3sInRow(Piece piece) {
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                counter += blocks[i][j].getFullRows(piece).size();
            }
            for (int j = 0; j < 3; j++) {
                if (blocks[i][0].has2CellsInRowWithColorFromEnd(piece, j) && blocks[i][1].isCellInBlockExists(piece, 3 * j + 1)) {
                    counter++;
                }
                if (blocks[i][0].isCellInBlockExists(piece, 3 * (j + 1)) && blocks[i][1].has2CellsInRowWithColorFromBeginning(piece, j)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Gets number of all 3-in-a-rows in game board in vertical direction. for more information about 3-in-a-row see
     * {@link Board#get4InARows(Piece)}
     *
     * @param piece piece color to check
     * @return all 3-in-a-row count
     */
    private int get3sInColumn(Piece piece) {
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                counter += blocks[j][i].getFullColumns(piece).size();
            }
            for (int j = 0; j < 3; j++) {
                if (blocks[0][i].has2CellsInColumnWithColorFromEnd(piece, j) && blocks[1][i].isCellInBlockExists(piece, j + 1)) {
                    counter++;
                }
                if (blocks[0][i].isCellInBlockExists(piece, 7 + j) && blocks[i][1].has2CellsInColumnWithColorFromBeginning(piece, j)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Gets number of all 3-in-a-rows in game board in diagonal direction. for more information about 3-in-a-row see
     * {@link Board#get4InARows(Piece)}
     *
     * @param piece piece color to check
     * @return all 3-in-a-row count
     */
    private int get3sInDiameter(Piece piece) {
        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == j) {
                    if (blocks[i][j].hasMainDiameterFull(piece)) {
                        counter++;
                    }
                    if (blocks[0][1].isCellInBlockExists(piece, 7)) {
                        if (blocks[i][j].hasAboveMainDiameterFull(piece)) {
                            counter++;
                        }
                    }
                    if (blocks[1][0].isCellInBlockExists(piece, 3)) {
                        if (blocks[i][j].hasUnderMainDiameterFull(piece)) {
                            counter++;
                        }
                    }
                } else {
                    if (blocks[i][j].hasSecondaryDiameterFull(piece)) {
                        counter++;
                    }
                    if (blocks[0][0].isCellInBlockExists(piece, 9)) {
                        if (blocks[i][j].hasAboveSecondaryDiameterFull(piece)) {
                            counter++;
                        }
                    }
                    if (blocks[1][1].isCellInBlockExists(piece, 1)) {
                        if (blocks[i][j].hasUnderSecondaryDiameterFull(piece)) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    /**
     * Gets all piece count that was not located at edges of the board.
     *
     * @param piece piece color to check
     * @return all piece at center of board
     */
    public int getPieceAtCenter(Piece piece) {
        int counter = 0;
        int[] nums;
        nums = new int[]{5, 6, 8, 9};
        for (int num : nums) {
            if (blocks[0][0].isCellInBlockExists(piece, num)) {
                counter++;
            }
        }
        nums = new int[]{4, 5, 7, 8};
        for (int num : nums) {
            if (blocks[0][1].isCellInBlockExists(piece, num)) {
                counter++;
            }
        }
        nums = new int[]{2, 3, 5, 6};
        for (int num : nums) {
            if (blocks[1][0].isCellInBlockExists(piece, num)) {
                counter++;
            }
        }
        nums = new int[]{1, 2, 4, 5};
        for (int num : nums) {
            if (blocks[1][1].isCellInBlockExists(piece, num)) {
                counter++;
            }
        }
        return counter;
    }
}