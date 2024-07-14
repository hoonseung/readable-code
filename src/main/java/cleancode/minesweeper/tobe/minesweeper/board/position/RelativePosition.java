package cleancode.minesweeper.tobe.minesweeper.board.position;

import java.util.List;
import java.util.Objects;

public class RelativePosition {

    public static final List<RelativePosition> SURROUND_POSITIONS = List.of(
            RelativePosition.of(-1, -1),
            RelativePosition.of(-1, 0),
            RelativePosition.of(-1, 1),
            RelativePosition.of(0, -1),
            RelativePosition.of(0, 1),
            RelativePosition.of(1, -1),
            RelativePosition.of(1, 0),
            RelativePosition.of(1, 1)
    );
    private final int deltaRowIndex;
    private final int deltaColIndex;


    public RelativePosition(int deltaRowIndex, int deltaColIndex) {
        this.deltaRowIndex = deltaRowIndex;
        this.deltaColIndex = deltaColIndex;
    }

    public static RelativePosition of(int deltaRow, int deltaCol) {
        return new RelativePosition(deltaRow, deltaCol);
    }

    public int deltaRowIndex() {
        return deltaRowIndex;
    }

    public int deltaColIndex() {
        return deltaColIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelativePosition that = (RelativePosition) o;
        return deltaRowIndex == that.deltaRowIndex && deltaColIndex == that.deltaColIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deltaRowIndex, deltaColIndex);
    }
}
