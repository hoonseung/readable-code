package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;

import java.util.Optional;

public class StudyCafePassOrder {

    private final StudyCafeSeatPass seatPass;
    private final StudyCafeLockerPass lockerPass;

    public StudyCafePassOrder(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        this.seatPass = seatPass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafePassOrder of(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass){
        return new StudyCafePassOrder(seatPass, lockerPass);
    }


    public StudyCafeSeatPass getSeatPass() {
        return seatPass;
    }

    public Optional<StudyCafeLockerPass> getLockerPass() {
        return Optional.ofNullable(lockerPass);
    }


    public int getDiscountPrice() {
        return this.seatPass.getDiscountPrice();
    }


    public int getTotalPrice() {
        int seatDiscountPrice = getDiscountPrice();
        int lockerPrice = lockerPass == null ? 0 : lockerPass.getPrice();
        int seatTotalPrice = this.seatPass.getSeatTotalPrice(seatDiscountPrice);

        return seatTotalPrice + lockerPrice;
    }

}
