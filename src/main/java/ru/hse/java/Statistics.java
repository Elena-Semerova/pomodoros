package ru.hse.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Statistics {
  private static HashMap<String, ArrayList<TimerSettings>> statistics = null;
  static File file = new File("./stats.txt");

  public static void setStatistics(HashMap<String, ArrayList<TimerSettings>> statistics) {
    Statistics.statistics = statistics;
  }
  public static HashMap<String, ArrayList<TimerSettings>> getStatistics() {return statistics;}
  public static void save() throws IOException {
    /**
     * Создается файл, если не существует
     * Записываются в одной строке данные с консоли в виде (workTime, restTime, success)
     */

    if (!file.exists()) {
      file.createNewFile();
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      for (var item : statistics.entrySet()) {
        for (var settings : item.getValue()) {
          String row = String.format("%s %s\n", item.getKey(), settings);
          fileOutputStream.write(row.getBytes());
        }
      }
    }
  }

  public static void read() throws IOException {
    statistics = new HashMap<>();
    if (!file.exists()) {
      return;
    }
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        String category = scanner.next();
        if (!statistics.containsKey(category)) {
          statistics.put(category, new ArrayList<>());
        }

        statistics.get(category).add(
          new TimerSettings(
            scanner.nextInt(), scanner.nextInt(),
            scanner.nextInt(), scanner.nextInt()));
      }
    }
  }

  public static void update(String category, int workTime, int restTime, boolean succeed) throws IOException {
    if (statistics == null) {
      read();
    }
    if (!statistics.containsKey(category)) {
      statistics.put(category, new ArrayList<>());
    }
    var settings = new TimerSettings(workTime, restTime);
    var data = statistics.get(category);
    if (!data.contains(settings)) {
      data.add(settings);
    }
    int idx = data.indexOf(settings);
    if (succeed) {
      data.get(idx).increaseSuccess();
    } else {
      data.get(idx).increaseFails();
    }
  }

  public static int[] getOptimalTime(String category) throws IOException {
    if (statistics == null) {
      read();
    }

    int[] optimalTime = {25, 5};
    var data = statistics.get(category);
    if (data == null || data.size() == 0) {
      return optimalTime;
    }
    var optimalSettings = Collections.max(data);
    optimalTime[0] = optimalSettings.getWorkTime();
    optimalTime[1] = optimalSettings.getRestTime();

    return optimalTime;
  }
}