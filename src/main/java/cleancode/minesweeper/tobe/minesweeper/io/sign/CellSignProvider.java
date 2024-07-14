package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShotStatus;

import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable {


    EMPTY(CellSnapShotStatus.EMPTY) {
        @Override
        public String provide(CellSnapShot cellSnapShot) {
            return EMPTY_SIGN;
        }
    },
    FLAG(CellSnapShotStatus.FLAG) {
        @Override
        public String provide(CellSnapShot cellSnapShot) {
            return FLAG_SIGN;
        }
    },
    LAND_MINE(CellSnapShotStatus.LAND_MINE) {
        @Override
        public String provide(CellSnapShot cellSnapShot) {
            return LAND_MINE_SIGN;
        }
    },
    NUMBER(CellSnapShotStatus.NUMBER) {
        @Override
        public String provide(CellSnapShot cellSnapShot) {
            return String.valueOf(cellSnapShot.getNearByLandMineCount());
        }
    },
    UNCHECKED(CellSnapShotStatus.UNCHECKED) {
        @Override
        public String provide(CellSnapShot cellSnapShot) {
            return UNCHECKED_SIGN;
        }
    },
    ;

    private static final String EMPTY_SIGN = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private final CellSnapShotStatus status;

    CellSignProvider(CellSnapShotStatus status) {
        this.status = status;
    }

    public static String findCellSignFrom(CellSnapShot snapShot) {
        CellSignProvider cellSignProvider = findBy(snapShot);
        return cellSignProvider.provide(snapShot);
    }

    private static CellSignProvider findBy(CellSnapShot snapShot) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(snapShot))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("확인할 수 없는 셀입니다"));
    }

    @Override
    public boolean supports(CellSnapShot cellSnapShot) {
        return cellSnapShot.isSameStatus(status);
    }


}
