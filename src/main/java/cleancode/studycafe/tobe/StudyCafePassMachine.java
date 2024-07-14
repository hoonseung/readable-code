package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPasses;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.type.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {


    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = getSelectedPass();
            selectLockerPass(selectedPass)
                    .ifPresentOrElse(lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                            () -> ioHandler.showPassOrderSummary(selectedPass));

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
        StudyCafeSeatPasses allPasses = studyCafeFileHandler.readStudyCafePasses();
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
        StudyCafeLockerPasses lockerPasses = studyCafeFileHandler.readLockerPasses();
        return lockerPasses.findLockerPassBy(selectedPass);
    }
}
