package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class EmptyCell implements Cell {

    private final CellState cellState = CellState.initialize();

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCountExist() {
        return false;
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


    public CellSnapShot getSnapShot() {
        if (cellState.isOpened()) {
            return CellSnapShot.ofEmpty();
        }
        if (cellState.isFlagged()) {
            return CellSnapShot.ofFlag();
        }
        return CellSnapShot.ofUnchecked();
    }
}
