package cleancode.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {

    boolean isLandMine();

    boolean hasLandMineCountExist();

    CellSnapShot getSnapShot();

    void open();

    boolean isOpened();

    void flag();

    boolean isChecked();


}
