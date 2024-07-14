package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;

public interface CellSignProvidable {

    String provide(CellSnapShot cellSnapShot);

    boolean supports(CellSnapShot cellSnapShot);
}
