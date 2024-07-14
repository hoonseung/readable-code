package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;


public interface OutPutHandler {


    void showBoard(GameBoard board);

    void showGameStartComments();

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    void showExceptionMessage(Exception e);

    void showSimpleMessage(String message);
}
