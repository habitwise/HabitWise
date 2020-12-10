package com.codepath.habitwise.features.home;

import android.util.Log;

import com.codepath.habitwise.features.loginSignup.ILoginSignupRepository;
import com.codepath.habitwise.features.loginSignup.LoginSignupParseRepository;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.codepath.habitwise.models.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeParseRepository implements IHomeRepository{

    private static final String TAG = "HOME_PARSE_REPOSITORY";
    private static HomeParseRepository homeParseRepository;

    private HomeParseRepository(){}

    public static IHomeRepository getInstance() {
        HomeParseRepository repo = homeParseRepository;
        if (repo == null) {
            homeParseRepository = new HomeParseRepository();
        }
        return homeParseRepository;
    }

    @Override
    public void getTaskListByDate(Calendar cal, final IHomeEventListner eventListner) {
        final Date date = cal.getTime();
        final List<Task> tasks = new ArrayList<>();
        ParseQuery<HabitUserMapping> habitUserQuery = ParseQuery.getQuery(HabitUserMapping.class);
        habitUserQuery.include(HabitUserMapping.key_habit);
        habitUserQuery.include(HabitUserMapping.key_user);

        final ParseQuery<Habit> habitInnerQuery = ParseQuery.getQuery(Habit.class);
        habitInnerQuery.whereEqualTo(Habit.key_days, cal.get(Calendar.DAY_OF_WEEK));

        habitUserQuery.whereMatchesQuery(HabitUserMapping.key_habit, habitInnerQuery);
        habitUserQuery.whereEqualTo(HabitUserMapping.key_user, ParseUser.getCurrentUser());
        habitUserQuery.findInBackground(new FindCallback<HabitUserMapping>() {
            @Override
            public void done(final List<HabitUserMapping> habitUserList, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting HabitUserMapping",e);
                    return;
                }

                ParseQuery<Task> taskQuery = ParseQuery.getQuery(Task.class);
                taskQuery.include(Task.key_habit);
                taskQuery.include(Task.key_user);
                taskQuery.whereEqualTo(Task.key_user, ParseUser.getCurrentUser());
                taskQuery.whereMatchesQuery(Task.key_habit, habitInnerQuery);
                taskQuery.whereEqualTo(Task.key_task_date, date);
                taskQuery.findInBackground(new FindCallback<Task>() {
                    @Override
                    public void done(List<Task> taskList, ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Issue with getting Task Query",e);
                            return;
                        }

                        if (taskList.size() == habitUserList.size()){
                            tasks.addAll(taskList);
                            eventListner.onFecthOfTaskListByDate(tasks);
                            return;
                        }

                        for(HabitUserMapping habitUser : habitUserList){
                            boolean matchFound = false;
                            for(Task task : taskList){
                                if (task.getHabit().getObjectId().equals(habitUser.getHabit().getObjectId())){
                                    tasks.add(task);
                                    matchFound = true;
                                    break;
                                }
                            }
                            if (matchFound) continue;
                            Task task = new Task();
                            task.setCounter(0);
                            task.setHabit(habitUser.getHabit());
                            task.setUser(ParseUser.getCurrentUser());
                            task.setTaskDate(date);
                            tasks.add(task);
                        }
                        Collections.sort(tasks, new Comparator<Task>() {
                            @Override
                            public int compare(Task o1, Task o2) {
                                return (o1.getHabit().getFrequency() - o1.getCounter()) - (o2.getHabit().getFrequency() - o2.getCounter());
                            }
                        });
                        eventListner.onFecthOfTaskListByDate(tasks);
                    }
                });


            }
        });
    }

    @Override
    public List<Habit> getHabits(Date date) {
        // TODO: Complete only if functionality needed
        return null;
    }
}
