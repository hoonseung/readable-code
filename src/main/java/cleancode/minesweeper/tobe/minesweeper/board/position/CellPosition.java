package cleancode.minesweeper.tobe.minesweeper.board.position;

import java.util.Objects;

public class CellPosition {

    private final int rowIndex;
    private final int colIndex;

    public CellPosition(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
        }
    }


    public static CellPosition of(int rowIndex, int colIndex) {
        return new CellPosition(rowIndex, colIndex);
    }


    public int rowIndex() {
        return rowIndex;
    }

    public int colIndex() {
        return colIndex;
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return this.rowIndex() + relativePosition.deltaRowIndex() >= 0
                && this.colIndex() + relativePosition.deltaColIndex() >= 0;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (this.canCalculatePositionBy(relativePosition)) {
            return new CellPosition(
                    this.rowIndex + relativePosition.deltaRowIndex(),
                    this.colIndex + relativePosition.deltaColIndex()
            );
        }
        throw new IllegalArgumentException("움직일 수 있는 좌표가 아닙니다.");
    }


    public boolean isRowIndexMoreThanOrEqual(int rowSize) {
        return this.rowIndex >= rowSize;
    }

    public boolean isColIndexMoreThenOrEqual(int colSize) {
        return this.colIndex >= colSize;
    }

    public Boolean isRowIndexLessThan(int rowSize) {
        return this.rowIndex < rowSize;
    }

    public boolean isColIndexLessThan(int colSize) {
        return this.colIndex < colSize;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }
}
