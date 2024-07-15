package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPasses;
import cleancode.studycafe.tobe.model.pass.type.StudyCafePassType;
import cleancode.studycafe.tobe.provider.LockerPassProvider;
import cleancode.studycafe.tobe.provider.SeatPassProvider;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final SeatPassProvider seatPassProvider;
    private final LockerPassProvider lockerPassProvider;
    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();


    public StudyCafePassMachine(SeatPassProvider seatPassProvider, LockerPassProvider lockerPassProvider) {
        this.seatPassProvider = seatPassProvider;
        this.lockerPassProvider = lockerPassProvider;
    }

    // 헥사고날 아키텍처 - 포트(인터페이스)와 어댑터 (인터페이스 구현체)
    // 규격이나 스펙만 맞으면 사용가능

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = getSelectedPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            StudyCafePassOrder passOrder = StudyCafePassOrder.of(selectedPass, optionalLockerPass.orElse(null));

            ioHandler.showPassOrderSummary(passOrder);


        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }


    private StudyCafeSeatPass getSelectedPass() {
        StudyCafePassType studyCafePassType = ioHandler.askPassTypeSelecting();

        List<StudyCafeSeatPass> passCandidates = findPassCandidatesBy(studyCafePassType);

        return ioHandler.getPassSelecting(passCandidates);
    }


    private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType passType) {
        StudyCafeSeatPasses allPasses = seatPassProvider.getSeatPasses();
        return allPasses.findSamePassBy(passType);
    }


    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        StudyCafeLockerPass studyCafeLockerPass = findLockerPassCandidatesBy(selectedPass)
                .filter(lockerPass -> selectedPass.isEnableLocker())
                .filter(ioHandler::askLockerPass)
                .orElse(null);

        return Optional.ofNullable(studyCafeLockerPass);
    }


    private Optional<StudyCafeLockerPass> findLockerPassCandidatesBy(StudyCafeSeatPass selectedPass) {
        StudyCafeLockerPasses lockerPasses = lockerPassProvider.getLockerPasses();
        return lockerPasses.findLockerPassBy(selectedPass);
    }
}
