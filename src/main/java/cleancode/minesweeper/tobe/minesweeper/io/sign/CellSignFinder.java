package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;

import java.util.List;

// 내부 인스턴스에 따라 다른 표시를 반환해야 하는데 다른게 추가 될 때 마다 게속 구현체를 넣어줘야한다.
// 이것을 열거타입으로 변경하여 하나의 열거타입에서 상수별로 인터페이스를 각각 구현한다. -> CellSingProvider 가 책임 수행
public class CellSignFinder {


    private static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
            new EmptyCellSignProvider(),
            new FlagCellSignProvider(),
            new LandMineCellSignProvider(),
            new NumberCellSignProvider(),
            new UncheckedCellSignProvider()
    );

    public String findCellSignFrom(CellSnapShot snapShot) {
        return CELL_SIGN_PROVIDERS.stream()
                .filter(provider -> provider.supports(snapShot))
                .findFirst()
                .map(provider -> provider.provide(snapShot))
                .orElseThrow(() -> new IllegalStateException("확인할 수 없는 셀입니다."));
    }
}
