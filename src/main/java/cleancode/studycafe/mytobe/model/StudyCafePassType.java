package cleancode.studycafe.mytobe.model;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;
    private static final Set<StudyCafePassType> LOCKER_ENABLE_TYPES = Set.of(StudyCafePassType.FIXED);

    StudyCafePassType(String description) {
        this.description = description;
    }


    public  boolean isLockerType() {
        return LOCKER_ENABLE_TYPES.contains(this);
    }

}
