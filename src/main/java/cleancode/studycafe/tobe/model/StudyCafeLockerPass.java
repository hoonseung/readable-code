package cleancode.studycafe.tobe.model;

import cleancode.studycafe.mytobe.model.StudyCafePassType;

public class StudyCafeLockerPass {

    private final cleancode.studycafe.mytobe.model.StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerPass(cleancode.studycafe.mytobe.model.StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(cleancode.studycafe.mytobe.model.StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public cleancode.studycafe.mytobe.model.StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public String display() {
        if (passType == cleancode.studycafe.mytobe.model.StudyCafePassType.HOURLY) {
            return String.format("%s시간권 - %d원", duration, price);
        }
        if (passType == cleancode.studycafe.mytobe.model.StudyCafePassType.WEEKLY) {
            return String.format("%s주권 - %d원", duration, price);
        }
        if (passType == StudyCafePassType.FIXED) {
            return String.format("%s주권 - %d원", duration, price);
        }
        return "";
    }

}
