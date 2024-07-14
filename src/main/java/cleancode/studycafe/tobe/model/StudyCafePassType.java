package cleancode.studycafe.tobe.model;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;
    private static final Set<StudyCafePassType> LOCKER_ENABLE_TYPE = Set.of(FIXED);

    StudyCafePassType(String description) {
        this.description = description;
    }


    public boolean isEnableLocker() {
        return LOCKER_ENABLE_TYPE.contains(this);
    }
}
