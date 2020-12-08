package com.codepath.habitwise.features.home;

import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface IHomeRepository {
    void getTaskListByDate(Calendar cal, IHomeEventListner eventListner);
    List<Habit> getHabits(Date date);
}
