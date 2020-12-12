package com.codepath.habitwise.features.analysis;

import android.util.Log;

import com.codepath.habitwise.models.Analysis;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.codepath.habitwise.models.Task;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnalysisParseRepository {

    public void getAnalysisItems(ParseUser currentUser){
        ParseQuery<HabitUserMapping> habitUserMappingParseQuery = ParseQuery.getQuery(HabitUserMapping.class);
        habitUserMappingParseQuery.include(HabitUserMapping.key_habit);
        habitUserMappingParseQuery.whereEqualTo(HabitUserMapping.key_user, currentUser);
        habitUserMappingParseQuery.findInBackground(new FindCallback<HabitUserMapping>() {
            @Override
            public void done(List<HabitUserMapping> objects, ParseException e) {

                List<Habit> currentUserHabits = new ArrayList<>();
                for(HabitUserMapping hum : objects) {
                    currentUserHabits.add(hum.getHabit());
                }

                ParseQuery<Task> taskQuery = ParseQuery.getQuery(Task.class);
                taskQuery.include(Task.key_user);
                taskQuery.include(Task.key_habit);
                taskQuery.include(Task.key_habit + '.' + Habit.key_days);
                taskQuery.whereContainedIn(Task.key_habit, currentUserHabits);
                taskQuery.findInBackground(new FindCallback<Task>() {
                    @Override
                    public void done(List<Task> tasks, ParseException e) {
                        Collections.sort(tasks, new Comparator<Task>() {
                            @Override
                            public int compare(Task o1, Task o2) {
                                int habitSort = o1.getHabit().getTitle().compareTo(o2.getHabit().getTitle());
                                if (habitSort != 0) return habitSort;
                                int taskDateSort = o2.getTaskDate().compareTo(o1.getTaskDate());
                                if (taskDateSort != 0) return taskDateSort;
                                return o1.getUser().getUsername().compareTo(o2.getUser().getUsername());
                            }
                        });

                        List<Analysis> lstAnalysis = performTaskAnalysis(tasks);

                    }
                });

            }

        });

    }

    public List<Analysis> performTaskAnalysis(List<Task> sortedTask) {
//        for(Task task : sortedTask) {
//            Log.i("TP", "Habit: " + task.getHabit().getTitle() + " Task Date: " + task.getTaskDate().toString() + "  User: " + task.getUser().getUsername() + " Habit array: " + task.getHabit().getDays());
//        }

        Habit selectedHabit = new Habit();
        Analysis analysis = new Analysis(selectedHabit);
        for(Task task : sortedTask) {
            if (selectedHabit != task.getHabit()) {
                selectedHabit = task.getHabit();
                analysis = new Analysis(selectedHabit);
            }

        }

        return null;
    }

}
