package cleancode.studycafe.tobe.model.pass.type;

import java.util.Set;

public enum StudyCafePassType implements PassTypeFormatter{

    HOURLY("시간 단위 이용권"){
        @Override
        public String format(int duration, int price) {
            return String.format("%s시간권 - %d원", duration, price);
        }
    },
    WEEKLY("주 단위 이용권"){
        @Override
        public String format(int duration, int price) {
            return String.format("%s주권 - %d원", duration, price);
        }
    },
    FIXED("1인 고정석"){
        @Override
        public String format(int duration, int price) {
            return  String.format("%s주권 - %d원", duration, price);
        }
    };


    private final String description;
    private static final Set<StudyCafePassType> LOCKER_ENABLE_TYPE = Set.of(FIXED);

    StudyCafePassType(String description) {
        this.description = description;
    }

    public boolean isEnableLocker() {
        return LOCKER_ENABLE_TYPE.contains(this);
    }


}
