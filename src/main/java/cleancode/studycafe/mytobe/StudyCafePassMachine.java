package cleancode.studycafe.mytobe;

import cleancode.studycafe.mytobe.exception.AppException;
import cleancode.studycafe.mytobe.io.InputHandler;
import cleancode.studycafe.mytobe.io.OutputHandler;
import cleancode.studycafe.mytobe.io.StudyCafeFileHandler;
import cleancode.studycafe.mytobe.model.StudyCafeLockerPass;
import cleancode.studycafe.mytobe.model.StudyCafePass;
import cleancode.studycafe.mytobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private boolean lockerSelection;

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            StudyCafeLockerPass lockerPass = selectLockerPass(selectedPass);
            showPassOrderTotalSummary(selectedPass, lockerPass);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = getPassCandidates(studyCafePassType);

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private  List<StudyCafePass> getPassCandidates(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }


    private void showPassOrderTotalSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        if (isLockerSelected(lockerPass)) {
            outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } else {
            outputHandler.showPassOrderSummary(selectedPass, null);
        }
    }

    private boolean isLockerSelected(StudyCafeLockerPass lockerPass) {
        return lockerSelection && lockerPass != null;
    }


    private StudyCafeLockerPass selectLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPassCandidate = lockerPasses.stream()
                .filter(locker ->
                        locker.getPassType() == selectedPass.getPassType()
                                && locker.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);

        if (isLockerPassExist(lockerPassCandidate)) {
            outputHandler.askLockerPass(lockerPassCandidate);
            lockerDecide();
        }

        return lockerPassCandidate;
    }


    private void lockerDecide() {
        lockerSelection = inputHandler.getLockerSelection();
    }

    private static boolean isLockerPassExist(StudyCafeLockerPass lockerPassCandidate) {
        return lockerPassCandidate != null;
    }

}
