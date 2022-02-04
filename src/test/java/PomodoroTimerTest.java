import org.junit.Test;
import ru.hse.java.PomodoroTimer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class PomodoroTimerTest {

    @Test
    public void testGetWorkDuration() throws FileNotFoundException {
        for (int i = 1; i <= 5; i++) {
            System.out.println("testing work duration " + i);

            PomodoroTimer timer = new PomodoroTimer(25, 5);
            File test = new File(String.format("src/test/resources/tests/test%d.txt", i));
            Scanner scanner = new Scanner(test);

            int testWorkDurationTime = 1;
            int testRestDurationTime = 1;

            if (scanner.hasNextInt()) {
                testWorkDurationTime = scanner.nextInt();
            }
            if (scanner.hasNextInt()) {
                testRestDurationTime = scanner.nextInt();
            }

            timer.setTime(testWorkDurationTime, testRestDurationTime);
            int ourWorkDurationTime = timer.getWorkDuration();

            assertEquals(testWorkDurationTime, ourWorkDurationTime);
        }
    }

    @Test
    public void testGetRestDuration() throws FileNotFoundException {
        for (int i = 1; i <= 5; i++) {
            System.out.println("testing rest duration " + i);

            PomodoroTimer timer = new PomodoroTimer(25, 5);
            File test = new File(String.format("src/test/resources/tests/test%d.txt", i));
            Scanner scanner = new Scanner(test);

            int testWorkDurationTime = 1;
            int testRestDurationTime = 1;

            if (scanner.hasNextInt()) {
                testWorkDurationTime = scanner.nextInt();
            }
            if (scanner.hasNextInt()) {
                testRestDurationTime = scanner.nextInt();
            }

            timer.setTime(testWorkDurationTime, testRestDurationTime);
            int ourRestDurationTime = timer.getRestDuration();

            assertEquals(testRestDurationTime, ourRestDurationTime);
        }
    }

    @Test
    public void testStart() {
        System.out.println("testing start");
        var timer = new PomodoroTimer();
        timer.setStatus(ru.hse.java.Status.REST);
        timer.start();

        timer.setStatus(ru.hse.java.Status.WORK);
        timer.start();
    }

    @Test
    public void testStop() {
        System.out.println("testing stop");

        var timer = new PomodoroTimer();
        timer.setStatus(ru.hse.java.Status.WORK);
        timer.start();
        timer.stop();
        timer.stop();
    }
}
