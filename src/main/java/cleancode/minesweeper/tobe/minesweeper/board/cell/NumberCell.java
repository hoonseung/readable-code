package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class NumberCell implements Cell {

    private final CellState cellState = CellState.initialize();
    private int nearByLandMineCount;


    public NumberCell(int nearByLandMineCount) {
        super();
        this.nearByLandMineCount = nearByLandMineCount;
    }


    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCountExist() {
        return true;
    }


    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public boolean isChecked() {
        return cellState.isOpened();
    }

    @Override
    public CellSnapShot getSnapShot() {
        if (cellState.isOpened()) {
            return CellSnapShot.ofNumber(nearByLandMineCount);
        }
        if (cellState.isFlagged()) {
            return CellSnapShot.ofFlag();
        }
        return CellSnapShot.ofUnchecked();
    }
}
