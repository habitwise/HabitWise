package com.codepath.habitwise.features.home;

import com.codepath.habitwise.models.Task;

import java.util.List;

public interface IHomeEventListner {
    void onFecthOfTaskListByDate(List<Task> taskList);
    void onFetchOfHabitList();
    void onError(Exception e);
}
