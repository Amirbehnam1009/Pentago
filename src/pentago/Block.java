package pentago;


import java.util.ArrayList;
import java.util.List;

/**
 * A block in pentago game
 */
class Block {
    /**
     * White piece state of block cell
     */
    final static Piece RED_PIECE = new RedPiece();
    /**
     * Black piece state of block cell
     */
    final static Piece BLACK_PIECE = new BlackPiece();
    /**
     * An 3 by 3 array that keeps current block state
     */
    private Cell[][] blockState = new Cell[3][3];

    /**
     * Constructor of block that initializes block state to all cells be empty.
     */
    Block() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blockState[i][j] = new Cell();
            }
        }
    }

    /**
     * Copy constructor of block
     *
     * @param block block to copy
     */
    Block(Block block) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blockState[i][j] = new Cell(block.blockState[i][j]);
            }
        }
    }

    /**
     * Gets string representation of a row in block.
     *
     * @param row row index
     * @return string representation of the row
     */
    String getRowString(int row) {
        String rowString = "";
        if (row >= 0 && row < 3) {
            for (int i = 0; i < 3; i++) {
                Cell currentItem = blockState[row][i];
                rowString = (rowString + currentItem.toString());
            }
        }
        return rowString;
    }

    /**
     * Checks the validity of putting a piece in cell determined by cell number. The validity checks simply by checking
     * emptiness of the cell.
     *
     * @param cellNumber cell number to check move validity
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    boolean isInvalidMove(int cellNumber) {
        int[] cellIndex = getCellIndex(cellNumber);
        return !blockState[cellIndex[0]][cellIndex[1]].isEmpty();
    }

    /**
     * Converts cell number to cell row and column index.
     *
     * @param cellNumber cell number
     * @return an array with two members, 0 index indicates computed row and 1 index indicates computed column
     */
    private int[] getCellIndex(int cellNumber) {
        int counter = 0;
        int[] result = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counter++;
                if (cellNumber == counter) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    /**
     * Applying a move by putting specified piece in specified cell.
     *
     * @param piece      piece to put
     * @param cellNumber cell number to put piece
     */
    void putPieceInBlock(Piece piece, int cellNumber) {
        int[] cellIndex = getCellIndex(cellNumber);
        blockState[cellIndex[0]][cellIndex[1]].putPiece(piece);
    }

    /**
     * Checks if specified cell filled by specified piece.
     *
     * @param piece      piece to check
     * @param cellNumber cell number to check
     * @return {@code true} if specified cell filled by specified piece, {@code false} otherwise
     */
    boolean isCellInBlockExists(Piece piece, int cellNumber) {
        int[] cellIndex = getCellIndex(cellNumber);
        return blockState[cellIndex[0]][cellIndex[1]].isSameColor(piece);
    }

    /**
     * gets all row indexes that fully has specified piece color.
     *
     * @param piece piece to check
     * @return list of indexes of rows fully has piece
     */
    ArrayList<Integer> getFullRows(Piece piece) {
        ArrayList<Integer> rowList = new ArrayList<>();
        int countPiece = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (blockState[i][j].isSameColor(piece)) {
                    countPiece++;
                }
            }
            if (countPiece == 3) {
                rowList.add(i);
            }
            countPiece = 0;
        }
        return rowList;
    }

    /**
     * gets all column indexes that fully has specified piece color.
     *
     * @param piece piece to check
     * @return list of indexes of columns fully has piece
     */
    ArrayList<Integer> getFullColumns(Piece piece) {
        ArrayList<Integer> columnList = new ArrayList<>();
        int countPiece = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (blockState[j][i].isSameColor(piece)) {
                    countPiece++;
                }
            }
            if (countPiece == 3) {
                columnList.add(i);
            }
            countPiece = 0;
        }
        return columnList;
    }

    /**
     * checks if two first cells of the row that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @param index row index of cell
     * @return {@code true} if two first cells of the row that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInRowWithColorFromBeginning(Piece piece, int index) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            if (blockState[index][i].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if two last cells of the row that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @param index row index of cell
     * @return {@code true} if two last cells of the row that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInRowWithColorFromEnd(Piece piece, int index) {
        int count = 0;
        for (int i = 1; i < 3; i++) {
            if (blockState[index][i].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if two first cells of the column that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @param index column index of cell
     * @return {@code true} if two first cells of the column that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInColumnWithColorFromBeginning(Piece piece, int index) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            if (blockState[i][index].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if two last cells of the column that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @param index column index of cell
     * @return {@code true} if two last cells of the column that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInColumnWithColorFromEnd(Piece piece, int index) {
        int count = 0;
        for (int i = 1; i < 3; i++) {
            if (blockState[i][index].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if the main diameter of block fully has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if the main diameter of block fully has specified piece color, {@code false} otherwise
     */
    boolean hasMainDiameterFull(Piece piece) {
        int countPiece = 0;
        for (int i = 0; i < 3; i++) {
            if (blockState[i][i].isSameColor(piece)) {
                countPiece++;
            }
        }
        return countPiece == 3;
    }

    /**
     * checks if the secondary diameter of block fully has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if the main diameter of block fully has specified piece color, {@code false} otherwise
     */
    boolean hasSecondaryDiameterFull(Piece piece) {
        int countPiece = 0;
        for (int i = 0; i < 3; i++) {
            if (blockState[i][2 - i].isSameColor(piece)) {
                countPiece++;
            }
        }
        return countPiece == 3;
    }

    /**
     * checks if two first cells of the main diameter that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if two first cells of the main diameter that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInMainDiameterWithColorFromBeginning(Piece piece) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            if (blockState[i][i].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if two last cells of the main diameter that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if two last cells of the main diameter that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInMainDiameterWithColorFromEnd(Piece piece) {
        int count = 0;
        for (int i = 1; i < 3; i++) {
            if (blockState[i][i].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if two first cells of the secondary diameter that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if two first cells of the secondary diameter that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInSecondaryDiameterWithColorFromBeginning(Piece piece) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            if (blockState[i][2 - i].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if two last cells of the secondary diameter that determined by index has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if two last cells of the secondary diameter that determined by index has specified piece color,
     * {@code false} otherwise
     */
    boolean has2CellsInSecondaryDiameterWithColorFromEnd(Piece piece) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            if (blockState[i][2 - i].isSameColor(piece)) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * checks if under main diameter of block fully has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if under main diameter of block fully has specified piece color, {@code false} otherwise
     */
    boolean hasUnderMainDiameterFull(Piece piece) {
        return blockState[1][0].isSameColor(piece) && blockState[2][1].isSameColor(piece);
    }

    /**
     * checks if above main diameter of block fully has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if above main diameter of block fully has specified piece color, {@code false} otherwise
     */
    boolean hasAboveMainDiameterFull(Piece piece) {
        return blockState[0][1].isSameColor(piece) && blockState[1][2].isSameColor(piece);
    }

    /**
     * checks if under secondary diameter of block fully has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if under secondary diameter of block fully has specified piece color, {@code false} otherwise
     */
    boolean hasUnderSecondaryDiameterFull(Piece piece) {
        return blockState[1][2].isSameColor(piece) && blockState[2][1].isSameColor(piece);
    }

    /**
     * checks if above secondary diameter of block fully has specified piece color.
     *
     * @param piece piece to check
     * @return {@code true} if above secondary diameter of block fully has specified piece color, {@code false} otherwise
     */
    boolean hasAboveSecondaryDiameterFull(Piece piece) {
        return blockState[0][1].isSameColor(piece) && blockState[1][0].isSameColor(piece);
    }

    /**
     * Rotates block using specified rotation.
     *
     * @param rotation rotation that should be done
     */
    void rotate(Rotation rotation) {
        blockState = rotation.rotate(blockState);
    }

    /**
     * Checks if block has any empty cells.
     *
     * @return {@code true} if cell has any empty cells, {@code false} otherwise
     */
    boolean hasEmpty() {
        boolean empty = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (blockState[i][j].isEmpty()) {
                    empty = true;
                }
            }
        }
        return empty;
    }

    /**
     * Checks if the block is symmetric. A symmetric block is a block that if rotates in both clockwise and anti-clockwise
     * directions will not change.
     *
     * @return {@code true} if the block is symmetric, {@code false} otherwise
     */
    boolean isSymmetric() {
        return blockState[0][0].equals(blockState[0][2]) && blockState[0][2].equals(blockState[2][2]) && blockState[2][2].equals(blockState[2][0])
                && blockState[0][1].equals(blockState[1][2]) && blockState[1][2].equals(blockState[2][1]) && blockState[2][1].equals(blockState[1][0]);
    }

    /**
     * Gets all empty cell numbers in block.
     *
     * @return a list of all empty cell numbers
     */
    List<Integer> getEmptyCells() {
        List<Integer> result = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (blockState[i][j].isEmpty()) {
                    result.add(counter);
                }
                counter++;
            }
        }
        return result;
    }
}
