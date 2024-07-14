package cleancode.studycafe.tobe;

import cleancode.studycafe.mytobe.exception.AppException;
import cleancode.studycafe.mytobe.io.InputHandler;
import cleancode.studycafe.mytobe.io.OutputHandler;
import cleancode.studycafe.mytobe.io.StudyCafeFileHandler;
import cleancode.studycafe.mytobe.model.StudyCafeLockerPass;
import cleancode.studycafe.mytobe.model.StudyCafePass;
import cleancode.studycafe.mytobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = getSelectedPass();
            selectLockerPass(selectedPass)
                    .ifPresentOrElse(lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                            () -> outputHandler.showPassOrderSummary(selectedPass));


        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }


    private StudyCafePass getSelectedPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = findPassCandidatesBy(studyCafePassType);

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }


    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType passType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(passType))
                .toList();
    }


    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        if(selectedPass.isEnableLocker()) {
            StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidatesBy(selectedPass);
            if (lockerPassCandidate != null) {
                outputHandler.askLockerPass(lockerPassCandidate);
                boolean isLockerSelected = inputHandler.getLockerSelection();

                if (isLockerSelected) {
                    return Optional.of(lockerPassCandidate);
                }

            }
        }
        return Optional.empty();
    }


    private StudyCafeLockerPass findLockerPassCandidatesBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();
        return allLockerPasses.stream()
                .filter(pass::isSameDurationType)
                .findFirst()
                .orElse(null);
    }
}
