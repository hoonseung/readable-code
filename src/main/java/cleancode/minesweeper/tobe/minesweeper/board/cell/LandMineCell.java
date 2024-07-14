package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class LandMineCell implements Cell {


    private final CellState cellState = CellState.initialize();


    @Override
    public boolean isLandMine() {
        return true;
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
        return cellState.isFlagged();
    }

    @Override
    public CellSnapShot getSnapShot() {
        if (cellState.isOpened()) {
            return CellSnapShot.ofLandMine();
        }
        if (cellState.isFlagged()) {
            return CellSnapShot.ofFlag();
        }
        return CellSnapShot.ofUnchecked();
    }

}
