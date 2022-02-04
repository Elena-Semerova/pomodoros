package ru.hse.java;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private Timer timer = new Timer();
    private int workDuration = 25;
    private int restDuration =  5;
    private Status status = Status.STOP;
    private TimerTask task = null;

    public PomodoroTimer() {}
    public PomodoroTimer(int workDuration, int restDuration) {
        setTime(workDuration, restDuration);
    }

    public void setTime(int workDuration, int restDuration) {
        if (restDuration <= 0 || workDuration <= 0) {
            throw new IllegalArgumentException();
        }
        this.workDuration = workDuration;
        this.restDuration = restDuration;
    }

    public void start() {
        if (status.equals(Status.REST)) {
            System.out.println("Rest period started");
            task = new TimerTask() {
                @Override
                public void run() {
                    status = Status.WORK;
                    CommandLineParser.timeIsUp("work");
                }
            };
            timer.schedule(task, toMilliseconds(restDuration));
        } else {
            System.out.println("Work period started");
            task = new TimerTask() {
                @Override
                public void run() {
                    status = Status.REST;
                    CommandLineParser.timeIsUp("rest");
                }
            };
            timer.schedule(task, toMilliseconds(workDuration));
        }
    }

    public void stop() {
        if (status != Status.STOP) {
            task.cancel();
            timer.cancel();
        }
    }

    private int toMilliseconds(int minutes) {
//        return minutes * 60 * 1000;
        return minutes * 1000;
    }

    public int getWorkDuration() {
        return workDuration;
    }
    public int getRestDuration() {
        return restDuration;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}