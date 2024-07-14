package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapShot {

    private final CellSnapShotStatus status;
    private final int nearByLandMineCount;

    public CellSnapShot(CellSnapShotStatus status, int nearByLandMineCount) {
        this.status = status;
        this.nearByLandMineCount = nearByLandMineCount;
    }

    public static CellSnapShot of(CellSnapShotStatus status, int nearByLandMineCount) {
        return new CellSnapShot(status, nearByLandMineCount);
    }

    public static CellSnapShot ofEmpty() {
        return new CellSnapShot(CellSnapShotStatus.EMPTY, 0);
    }

    public static CellSnapShot ofFlag() {
        return new CellSnapShot(CellSnapShotStatus.FLAG, 0);
    }

    public static CellSnapShot ofLandMine() {
        return new CellSnapShot(CellSnapShotStatus.LAND_MINE, 0);
    }

    public static CellSnapShot ofNumber(int nearByLandMineCount) {
        return new CellSnapShot(CellSnapShotStatus.NUMBER, nearByLandMineCount);
    }

    public static CellSnapShot ofUnchecked() {
        return new CellSnapShot(CellSnapShotStatus.UNCHECKED, 0);
    }

    public CellSnapShotStatus getStatus() {
        return status;
    }

    public boolean isSameStatus(CellSnapShotStatus status) {
        return this.status == status;
    }

    public int getNearByLandMineCount() {
        return nearByLandMineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellSnapShot that = (CellSnapShot) o;
        return nearByLandMineCount == that.nearByLandMineCount && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, nearByLandMineCount);
    }
}
