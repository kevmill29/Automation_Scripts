import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class SpoolFileCleanupAutomation {

    private static final int START_DELAY_MS = 5000;
    private static final int STEP_DELAY_MS = 500;
    private static final int TYPE_DELAY_MS = 100;

    public static void main(String[] args) {

        Robot robot = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // ✅ Prompt for username
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();

            robot = new Robot();

            System.out.println("Focus the AS400 window...");
            System.out.println("Starting in 5 seconds...");
            Thread.sleep(START_DELAY_MS);

            runProcess(robot, username);

            System.out.println("Process completed.");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            cleanup();
            scanner.close();
        }
    }

    private static void runProcess(Robot robot, String username) throws Exception {

        // 1. Press ESC
        pressKey(robot, KeyEvent.VK_ESCAPE);
        Thread.sleep(STEP_DELAY_MS);

        // 2. Press 8 and Enter
        typeText(robot, "8");
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);

        // 3. Press Enter again
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);

        // 4. Shift + Tab
        pressShiftKey(robot, KeyEvent.VK_TAB);
        Thread.sleep(STEP_DELAY_MS);

        // 5. Type WRKSPLF USERNAME
        String workCmd = "WRKSPLF " + username;
        typeText(robot, workCmd);
        Thread.sleep(STEP_DELAY_MS);

        // Press Enter
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);

        pressShiftKey(robot, KeyEvent.VK_TAB);
        Thread.sleep(STEP_DELAY_MS);


        // 6. Type DLTSPLF command
        String deleteCmd = "DLTSPLF FILE(*SELECT) SELECT(" + username + ")";
        typeText(robot, deleteCmd);
        Thread.sleep(STEP_DELAY_MS);

        // Press Enter to run
        pressKey(robot, KeyEvent.VK_ENTER);
        Thread.sleep(STEP_DELAY_MS);
    }

    // ✅ Key helpers

    private static void pressKey(Robot robot, int key) {
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    private static void pressShiftKey(Robot robot, int key) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    private static void typeText(Robot robot, String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            typeChar(robot, c);
            Thread.sleep(TYPE_DELAY_MS);
        }
    }

    private static void typeChar(Robot robot, char c) {

    switch (c) {

        case ' ':
            pressKey(robot, KeyEvent.VK_SPACE);
            return;

        case '(':
            pressShiftKey(robot, KeyEvent.VK_9);
            return;

        case ')':
            pressShiftKey(robot, KeyEvent.VK_0);
            return;

        case '*':
            pressShiftKey(robot, KeyEvent.VK_8);
            return;

        default:
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (keyCode == KeyEvent.VK_UNDEFINED) {
                throw new IllegalArgumentException("Unsupported character: " + c);
            }

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
}

    private static void cleanup() {
        try {
            Thread.currentThread().interrupt();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
