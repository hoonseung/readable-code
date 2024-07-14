package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StudyCafePassMachine {


    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = getSelectedPass();
            selectLockerPass(selectedPass)
                    .ifPresentOrElse(lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                            () -> ioHandler.showPassOrderSummary(selectedPass));

        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }


    private StudyCafePass getSelectedPass() {
        StudyCafePassType studyCafePassType = ioHandler.askPassTypeSelecting();

        List<StudyCafePass> passCandidates = findPassCandidatesBy(studyCafePassType);

        return ioHandler.getPassSelecting(passCandidates);
    }


    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType passType) {
        StudyCafePasses allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.findSamePassBy(passType);
    }


    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        StudyCafeLockerPass studyCafeLockerPass = findLockerPassCandidatesBy(selectedPass)
                .filter(lockerPass -> selectedPass.isEnableLocker())
                .filter(ioHandler::askLockerPass)
                .orElse(null);

        return Optional.ofNullable(studyCafeLockerPass);
    }


    private Optional<StudyCafeLockerPass> findLockerPassCandidatesBy(StudyCafePass pass) {
        StudyCafeLockerPasses lockerPasses = studyCafeFileHandler.readLockerPasses();
        return lockerPasses.findLockerPassBy(pass);
    }
}
