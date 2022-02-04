import org.apache.commons.io.FileUtils;
import org.junit.Test;
import ru.hse.java.Statistics;
import ru.hse.java.TimerSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class StatisticsTest {


  @Test
  public void testSave() throws IOException {
    System.out.println("testing save");

    File stats = new File("stats.txt");
    //test 1
    stats.delete();
    Statistics.setStatistics(new HashMap<>());
    Statistics.save();
    assertTrue(stats.exists());
    byte[] expected = "".getBytes();
    assertArrayEquals(expected, FileUtils.readFileToByteArray(stats));

    //test 2
    stats.delete();

    HashMap<String, ArrayList<TimerSettings>> test = new HashMap<>();

    ArrayList<TimerSettings> category1 = new ArrayList<>();
    category1.add(new TimerSettings(1, 1, 1, 2));
    category1.add(new TimerSettings(1, 2, 1, 0));

    ArrayList<TimerSettings> category2 = new ArrayList<>();
    category2.add(new TimerSettings(3, 4, 5, 6));
    category2.add(new TimerSettings(7, 8, 9, 10));

    test.put("category1", category1);
    test.put("category2", category2);

    Statistics.setStatistics(test);
    Statistics.save();
    assertTrue(stats.exists());

    expected = String.join(
      "\n",
      "category2 3 4 5 6",
      "category2 7 8 9 10",
      "category1 1 1 1 2",
      "category1 1 2 1 0",
      "").getBytes();

    assertArrayEquals(expected, FileUtils.readFileToByteArray(stats));
  }

  @Test
  public void testGetOptimalTime() throws IOException {
    System.out.println("testing getOptimalTime");
    File stats = new File("stats.txt");
    //test 1
    stats.delete();
    Statistics.setStatistics(new HashMap<>());
    assertArrayEquals(new int[]{25, 5}, Statistics.getOptimalTime("unknown"));

    //test 2
    stats.delete();

    HashMap<String, ArrayList<TimerSettings>> test = new HashMap<>();

    ArrayList<TimerSettings> category1 = new ArrayList<>();
    category1.add(new TimerSettings(1, 1, 1, 2));
    category1.add(new TimerSettings(1, 2, 1, 0));

    ArrayList<TimerSettings> category2 = new ArrayList<>();
    category2.add(new TimerSettings(3, 4, 5, 1));
    category2.add(new TimerSettings(7, 8, 9, 6));

    test.put("category1", category1);
    test.put("category2", category2);

    Statistics.setStatistics(test);
    Statistics.save();

    assertArrayEquals(new int[]{1, 2}, Statistics.getOptimalTime("category1"));
    assertArrayEquals(new int[]{3, 4}, Statistics.getOptimalTime("category2"));

    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, false);
    assertArrayEquals(new int[]{1, 1}, Statistics.getOptimalTime("category1"));
  }
  @Test
  public void testUpdate() throws IOException {
    System.out.println("testing update");

    File stats = new File("stats.txt");
    //test 1
    stats.delete();
    Statistics.setStatistics(new HashMap<>());
    Statistics.update("category1", 1, 2, true);
    Statistics.save();
    assertTrue(stats.exists());
    byte[] expected = "category1 1 2 1 0\n".getBytes();
    assertArrayEquals(expected, FileUtils.readFileToByteArray(stats));

    //test 2
    stats.delete();

    HashMap<String, ArrayList<TimerSettings>> test = new HashMap<>();

    ArrayList<TimerSettings> category1 = new ArrayList<>();
    category1.add(new TimerSettings(1, 1, 1, 2));
    category1.add(new TimerSettings(1, 2, 1, 0));

    ArrayList<TimerSettings> category2 = new ArrayList<>();
    category2.add(new TimerSettings(3, 4, 5, 6));
    category2.add(new TimerSettings(7, 8, 9, 10));

    test.put("category1", category1);
    test.put("category2", category2);

    Statistics.setStatistics(test);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, false);
    Statistics.save();
    assertTrue(stats.exists());

    expected = String.join(
      "\n",
      "category2 3 4 5 6",
      "category2 7 8 9 10",
      "category1 1 1 5 3",
      "category1 1 2 1 0",
      "").getBytes();

    assertArrayEquals(expected, FileUtils.readFileToByteArray(stats));
  }
  @Test
  public void testRead() throws IOException {
    System.out.println("testing read");

    File stats = new File("stats.txt");
    //test 1
    stats.delete();
    Statistics.setStatistics(new HashMap<>());
    Statistics.update("category1", 1, 2, true);
    Statistics.save();
    var expected = Statistics.getStatistics();
    Statistics.setStatistics(null);
    Statistics.read();
    assertTrue(expected.equals(Statistics.getStatistics()));

    //test 2
    stats.delete();

    HashMap<String, ArrayList<TimerSettings>> test = new HashMap<>();

    ArrayList<TimerSettings> category1 = new ArrayList<>();
    category1.add(new TimerSettings(1, 1, 1, 2));
    category1.add(new TimerSettings(1, 2, 1, 0));

    ArrayList<TimerSettings> category2 = new ArrayList<>();
    category2.add(new TimerSettings(3, 4, 5, 6));
    category2.add(new TimerSettings(7, 8, 9, 10));

    test.put("category1", category1);
    test.put("category2", category2);

    Statistics.setStatistics(test);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, true);
    Statistics.update("category1", 1, 1, false);
    Statistics.save();
    expected = Statistics.getStatistics();
    Statistics.setStatistics(null);
    Statistics.read();
    assertTrue(expected.equals(Statistics.getStatistics()));
  }
}
