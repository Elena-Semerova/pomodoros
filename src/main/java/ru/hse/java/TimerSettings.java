package ru.hse.java;

public class TimerSettings implements Comparable<TimerSettings> {
  private int workTime = 25;
  private int restTime = 5;
  private int success = 0;
  private int fails = 0;

  public TimerSettings(int workTime, int restTime, int success, int fails) {
    if (workTime <= 0 || restTime <= 0 || success < 0 || fails < 0) {
      throw new IllegalArgumentException();
    }
    this.workTime = workTime;
    this.restTime = restTime;
    this.success = success;
    this.fails = fails;
  }
  public TimerSettings(int workTime, int restTime) {
    if (workTime <= 0 || restTime <= 0) {
      throw new IllegalArgumentException();
    }
    this.workTime = workTime;
    this.restTime = restTime;
  }
  public int getWorkTime() {
    return workTime;
  }

  public int getRestTime() {
    return restTime;
  }

  public int score() {
    return success - fails;
  }
  @Override
  public int compareTo(TimerSettings o) {
    return score() - o.score();
  }
  @Override
  public String toString() {
    return String.format("%d %d %d %d", workTime, restTime, success, fails);
  }
  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof TimerSettings))
      return false;
    TimerSettings other = (TimerSettings)o;
    return (other.restTime == restTime) && (other.workTime == workTime);
  }

  public void increaseSuccess() {
    success += 1;
  }

  public void increaseFails() {
    fails += 1;
  }
}
