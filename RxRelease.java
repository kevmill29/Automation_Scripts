import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RxRelease {

    private static final int START_DELAY_MS = 5000;
    private static final int STEP_DELAY_MS = 500;
    private static final int TYPE_DELAY_MS = 100;

    public static void main(String[] args) {

        Robot robot = null;

        try {
            robot = new Robot();

            System.out.println("Click into the correct starting field...");
            System.out.println("Automation starts in 5 seconds...");
            Thread.sleep(START_DELAY_MS);

            runProcess(robot);

            System.out.println("Process completed successfully.");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();

        } finally {
            cleanupThreads();
            System.out.println("Cleanup complete. Exiting program.");
        }
    }

    private static void runProcess(Robot robot) throws Exception {

        // Step 1: Select Option 1
        typeText(robot, "1");
        Thread.sleep(TYPE_DELAY_MS);
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);

        // Step 2: Select Option 2
        typeText(robot, "2");
        Thread.sleep(TYPE_DELAY_MS);
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);

        // ✅ NEW STEP: Extra Enter
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);

        
  // ✅ NEW STEP: Page Down
        pressKey(robot, KeyEvent.VK_PAGE_DOWN);
        Thread.sleep(STEP_DELAY_MS);
        
  // ✅ NEW STEP: Move down 9 times
        for (int i = 0; i < 9; i++) {
            pressKey(robot, KeyEvent.VK_DOWN);
            Thread.sleep(150);
        }

        Thread.sleep(STEP_DELAY_MS);




        // Step 3: Enter A
        typeText(robot, "A");
        Thread.sleep(TYPE_DELAY_MS);

        // Step 4: Shift + F4
        pressShiftFunction(robot, KeyEvent.VK_F4);
        Thread.sleep(STEP_DELAY_MS);

        // Step 5: Type command with UTC time
        String command = "All KE " + getLocalMilitaryTime();
        typeText(robot, command);
        Thread.sleep(STEP_DELAY_MS);

        
// ✅ NEW STEP: Press numpad +
        pressKey(robot, KeyEvent.VK_ADD);
        Thread.sleep(STEP_DELAY_MS);


        // Step 6: Shift + F4 again
        pressShiftFunction(robot, KeyEvent.VK_F4);
        Thread.sleep(STEP_DELAY_MS);
    }

    // UTC time in HHmm format
  
private static String getLocalMilitaryTime() {
    ZonedDateTime nowLocal = ZonedDateTime.now(); // uses system timezone
    return nowLocal.format(DateTimeFormatter.ofPattern("HHmm"));
}


    private static void pressKey(Robot robot, int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    private static void pressShiftFunction(Robot robot, int functionKey) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(functionKey);
        robot.keyRelease(functionKey);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    private static void typeText(Robot robot, String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            typeChar(robot, c);
            Thread.sleep(TYPE_DELAY_MS);
        }
    }

    private static void typeChar(Robot robot, char c) {
        if (c == ' ') {
            pressKey(robot, KeyEvent.VK_SPACE);
            return;
        }

        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

        if (Character.isUpperCase(c)) {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }

    private static void cleanupThreads() {
        try {
            Thread.currentThread().interrupt();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}