package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShotStatus;

public class NumberCellSignProvider implements CellSignProvidable {


    @Override
    public String provide(CellSnapShot cellSnapShot) {
        return String.valueOf(cellSnapShot.getNearByLandMineCount());
    }

    @Override
    public boolean supports(CellSnapShot cellSnapShot) {
        return cellSnapShot.isSameStatus(CellSnapShotStatus.NUMBER);
    }
}
