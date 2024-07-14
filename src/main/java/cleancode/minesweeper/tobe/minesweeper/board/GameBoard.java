package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class GameBoard {

    public final int landMineCount;
    private final Cell[][] board;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel) {
        this.board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
        this.landMineCount = gameLevel.getLandMineCount();
        initializeGameStatus();
    }


    public void initializeGame() {
        initializeGameStatus();
        CellPositions cellDefaultPositions = CellPositions.from(board);


        initializeEmptyCells(cellDefaultPositions);

        List<CellPosition> landMinePositions = cellDefaultPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMinePositions);


        List<CellPosition> numberPositionCandidates = cellDefaultPositions.subtract(landMinePositions);
        initializeNumberCells(numberPositionCandidates);

    }


    public void openAt(CellPosition cellPosition) {
        if (isLandMineCell(cellPosition)) {
            openOneCellAt(cellPosition);
            changeGameStatusToLose();
            return;
        }
        openedSurroundedCells2(cellPosition);
        checkIfGameIsOver();
    }


    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();

        checkIfGameIsOver();
    }


    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }


    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }


    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }


    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowIndexMoreThanOrEqual(getRowSize())
                || cellPosition.isColIndexMoreThenOrEqual(getColSize());
    }


    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public CellSnapShot getSnapShot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapShot();
    }


    private void openOneCellAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }


    private void initializeGameStatus() {
        this.gameStatus = GameStatus.IN_PROGRESS;
    }


    private void initializeEmptyCells(CellPositions cellDefaultPositions) {
        List<CellPosition> allPositions = cellDefaultPositions.getPositions();
        for (CellPosition cellPosition : allPositions) {
            updateCellAt(cellPosition, new EmptyCell());
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMinePositions) {
        for (CellPosition cellPosition : landMinePositions) {
            updateCellAt(cellPosition, new LandMineCell());
        }
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for (CellPosition candidatePosition : numberPositionCandidates) {
            int count = countNearByLandMines(candidatePosition);
            if (count != 0) {
                updateCellAt(candidatePosition, new NumberCell(count));
            }
        }
    }


    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.rowIndex()][position.colIndex()] = cell;
    }


    private int countNearByLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize)
                .stream()
                .filter(this::isLandMineCell)
                .count();

        return (int) count;
    }

    // 셀 유효성 검증
    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUND_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }


    private void openedSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) { // 열려있으면 중지
            return;
        }
        if (isLandMineCell(cellPosition)) { // 지뢰면 중지
            return;
        }

        openOneCellAt(cellPosition);// 안열려있으니 오픈

        if (doesCellHaveLandMineCount(cellPosition)) { // 숫자셀이면 중지
            return;
        }
        // 재귀
        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
        surroundedPositions.forEach(this::openedSurroundedCells);
    }


    private void openedSurroundedCells2(CellPosition cellPosition) {
        Deque<CellPosition> stack = new ArrayDeque<>();
        stack.push(cellPosition);

        while (!stack.isEmpty()) {
            openAndPushCellAt(stack);
        }
    }

    private void openAndPushCellAt(Deque<CellPosition> stack) {
        CellPosition currentCellPosition = stack.pop();

        if (isOpenedCell(currentCellPosition)) { // 열려있으면 중지
            return;
        }
        if (isLandMineCell(currentCellPosition)) { // 지뢰면 중지
            return;
        }

        openOneCellAt(currentCellPosition);// 안열려있으니 오픈

        if (doesCellHaveLandMineCount(currentCellPosition)) { // 숫자셀이면 중지
            return;
        }
        List<CellPosition> surroundedPositions = calculateSurroundedPositions(currentCellPosition, getRowSize(), getColSize());
        for (CellPosition surroundedPosition : surroundedPositions) {
            stack.push(surroundedPosition);
        }
    }


    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }


    private void checkIfGameIsOver() {
        if (isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }


    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }


    private boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }


    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.hasLandMineCountExist();
    }


    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isOpened();
    }


    private boolean isAllCellChecked() {
        return Cells.from(board)
                .isAllCellChecked();

    }


    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.rowIndex()][cellPosition.colIndex()];
    }


}





