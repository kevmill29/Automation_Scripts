import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class LateOrderAutomation {

    // Adjustable settings
    private static final int START_DELAY_MS = 5000;  // time to focus window
    private static final int KEY_DELAY_MS = 200;     // delay between inputs

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        // Ask user for number of rows
        System.out.print("Enter number of rows to process: ");
        int rows = scanner.nextInt();

        Robot robot = new Robot();

        System.out.println("Click into the FIRST 'Opt' field now...");
        System.out.println("Automation starts in 5 seconds...");
        Thread.sleep(START_DELAY_MS);

        // 🚫 IMPORTANT: No TAB, no DOWN
        // Start EXACTLY where your cursor is

        for (int i = 0; i < rows; i++) {

            typeText(robot, "14");
            Thread.sleep(KEY_DELAY_MS);

            // 🚫 DO NOT manually move down
            // AS400 auto-advances to next row
        }

        // Submit all entries
        pressKey(robot, KeyEvent.VK_ENTER);

        System.out.println("Completed processing " + rows + " rows.");
        scanner.close();
    }

    private static void pressKey(Robot robot, int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    private static void typeText(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }
}