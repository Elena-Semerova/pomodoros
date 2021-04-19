package ru.hse.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Statistics {
  static int optimalWorkTime = 25;
  static int optimalRestTime = 5;
  static Scanner console = new Scanner(System.in);
  static File file = new File("./stats.txt");

  public static void save(Scanner scanner) throws IOException {
    /**
     * Создается файл, если не существует
     * Записываются в одной строке данные с консоли в виде (workTime, restTime, success)
     */

    if (!file.exists()) {
      file.createNewFile();
    }

    if (scanner == null) {
      scanner = console;
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(file, true)) {
      byte[] buffer;

      String workTime = scanner.next() + ' ';
      buffer = workTime.getBytes();
      fileOutputStream.write(buffer);

      String restTime = scanner.next() + ' ';
      buffer = restTime.getBytes();
      fileOutputStream.write(buffer);

      String success = scanner.next() + '\n';
      buffer = success.getBytes();
      fileOutputStream.write(buffer);

//      System.out.println(workTime);
//      System.out.println(restTime);
//      System.out.println(success);
    }
  }

  public static void read() throws IOException {
    /**
     * Данные в файле хранятся в виде (workTime, restTime, success)
     * Заводим массив с элементами вида (workTime, restTime, counter)
     * counter - считает для уникальных пар {workTime, restTime} количество успехов
     * (если success = 'yes', то +1, если success = 'no', то -1)
     * Пара, для которой counter -> max, становится оптимальной
     */
    StringBuilder sb = new StringBuilder();
    try (Scanner scan = new Scanner(file)) {
      while (scan.hasNextLine()) {
        sb.append(scan.nextLine()).append("\n");
      }
    }

    String[] array = sb.toString().split("\n");

    int[][] data = new int[array.length][3];
    int unique = 0;

    for (int i = 0; i < array.length; i++) {
      data[i][2] = 0;
    }

    for (String line : array) {
      String[] words = line.split(" ");

      int workTime = Integer.parseInt(words[0]);
      int restTime = Integer.parseInt(words[1]);
      String result = words[2];

      boolean flag = true;

      for (int i = 0; i < array.length; i++) {
        if ((data[i][0] == workTime) & (data[i][1] == restTime)) {
          if (result.equals("yes")) {
            data[i][2]++;
          } else if (result.equals("no")) {
            data[i][2]--;
          }
          flag = false;
        }
      }

      if (flag) {
        data[unique][0] = workTime;
        data[unique][1] = restTime;

        if (result.equals("yes")) {
          data[unique][2]++;
        } else if (result.equals("no")) {
          data[unique][2]--;
        }
        unique++;
      }
    }

    int maxCounter = 0;
    int optimalTimeIdx = -1;

    for (int i = 0; i < data.length; i++) {
      if (data[i][2] > maxCounter) {
        maxCounter = data[i][2];
        optimalTimeIdx = i;
      }
    }

    if (optimalTimeIdx == -1) {
      return;
    }

    optimalWorkTime = data[optimalTimeIdx][0];
    optimalRestTime = data[optimalTimeIdx][1];
  }

  public static int[] getOptimalTime() {
    /**
     * Возвращает массив с оптимальным временем
     */
    int[] optimalTime = new int[2];
    optimalTime[0] = optimalWorkTime;
    optimalTime[1] = optimalRestTime;

    return optimalTime;
  }
}