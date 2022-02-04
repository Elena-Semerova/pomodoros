package ru.hse.java;

import java.io.IOException;
import java.util.Scanner;

class CommandLineParser
{
    static PomodoroTimer timer = new PomodoroTimer(25, 5);
    static Scanner scanner = new Scanner(System.in);
    static String category = "unknown";

    public static void main ( String [] arguments )
    {
        System.out.println("Welcome! Ready to start?");
        waitCommand();
    }

    private static void waitCommand() {
        System.out.println("waiting for the command... (set/optimal/start/finish)");
        String inputString = scanner.nextLine().replaceAll("\\s+","");
        switch (inputString) {
            case "set" -> {
                System.out.print("Enter work period duration: ");
                inputString = scanner.nextLine();
                int workDuration = Integer.parseInt(inputString);

                System.out.print("Enter rest period duration: ");
                inputString = scanner.nextLine();
                int restDuration = Integer.parseInt(inputString);

                timer.setTime(workDuration, restDuration);

                System.out.print("Enter category: ");
                inputString = scanner.nextLine();
                category = inputString;

                System.out.println("Timer is ready.");
                waitCommand();
            }
            case "optimal" -> {
                System.out.print("Enter category: ");
                inputString = scanner.nextLine();
                category = inputString;

                int[] optimalTime = {25, 5};
                try {
                    optimalTime = Statistics.getOptimalTime(category);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                timer.setTime(optimalTime[0], optimalTime[1]);

                System.out.print("Optimal time is ");
                System.out.print(timer.getWorkDuration() + "/");
                System.out.println(timer.getRestDuration());
                System.out.println("Timer is ready.");
                waitCommand();
            }
            case "start" -> start();
            case "finish" -> finish();
            default -> {
                System.out.println("Unknown command. Try one of set/optimal/start/finish.");
                waitCommand();
            }
        }
    }

    private static void start() {
        timer.start();
    }

    public static void timeIsUp(String nextActivity) {
        System.out.println("Time is up!");
        if (nextActivity.equals("rest")) {
            String response;
            System.out.print("Have you succeed? (y/n): ");
            response = scanner.nextLine().replaceAll("\\s+","");
            try {
                Statistics.update(
                  category,
                  timer.getWorkDuration(),
                  timer.getRestDuration(),
                  response.equals("y"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Ready for " + nextActivity + "? (y/n): ");
        String response = scanner.nextLine().replaceAll("\\s+","");
        if (response.equals("y")) {
            start();
        } else if (response.equals("n")) {
            waitCommand();
        }
    }
    
    private static void finish() {
        try {
            Statistics.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer.stop();
        System.out.println("Good job, goodbye!");
        System.exit(0);
    }
}
