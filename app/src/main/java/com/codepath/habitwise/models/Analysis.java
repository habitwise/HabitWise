package com.codepath.habitwise.models;

import com.parse.ParseUser;

public class Analysis {
    public ParseUser user1;
    public ParseUser user2;
    public int currentStreak;
    public int highestStreak;
    public Habit habit;

    public Analysis (Habit habit) {
        this.habit = habit;
    }

    public ParseUser getUser1() {
        return user1;
    }

    public void setUser1(ParseUser user1) {
        this.user1 = user1;
    }

    public ParseUser getUser2() {
        return user2;
    }

    public void setUser2(ParseUser user2) {
        this.user2 = user2;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getHighestStreak() {
        return highestStreak;
    }

    public void setHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}
