package com.codepath.habitwise.features.habitDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.codepath.habitwise.models.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

//import android.widget.CalendarView;

public class detailsActivity extends AppCompatActivity {
    public static final String TAG = "detailsActivity";
    private TextView tvTitle;
    private TextView tvInfo;
    private Switch toggleSwitch;

    private RelativeLayout counterBox;
    private Button incrementButton;
    private TextView textViewCounter;
    private Button decrementButton;

    private RelativeLayout FriendsBox;
    private TextView youTextBox;
    private TextView youCompleteBox;
    private TextView textStreak;
    private TextView friendsTextBox;
    private TextView friendsCompleteBox;

    private RelativeLayout statisticsBox;
    private TextView bestStreakBox;
    private TextView currentStreakBox;
    private TextView allTimeBox;
    private TextView todayBox;
    private TextView completionsBox;
    private TextView dayCompletions;

    private CalendarView mCalendarView;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnComplete;

    private int frequency;
    private int recurrence;
    private String description="";
    private String userTaskCompletion = "";
    private String friendTaskCompletion = "";
    private String userStringCount = "";
    private String friendStringCount = "";
    private  int userCounter = 0;
    private int friendCounter = 0;

    private int userTasksCompleted = 0;
    private int friendTasksCompleted = 0;
    private int userDaysCompleted = 0;
    private int friendDaysCompleted = 0;
    private int totalTasksCount = 0;
    private int totalDaysCount = 0;
    private int bestStreak = 0;

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;

    private List<EventDay> mEventDaysDone = new ArrayList<>();
    private List<EventDay> mEventDaysIncomplete = new ArrayList<>();
    private List<EventDay> mEventDaysHalfComplete = new ArrayList<>();



     List<Task> userTaskList = new ArrayList<>();
     List<Task> friendTaskList = new ArrayList<>();
    List<Date> datesList = new ArrayList<>();

    ParseUser friendObject;
    ParseUser userObject;
    Habit habitObject;
    Task userTodayTaskObject;
    Task friendTodayTask;
    Date dateObject;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        habitObject = getIntent().getParcelableExtra("habit");
        userTodayTaskObject = getIntent().getParcelableExtra("task");
        dateObject = userTodayTaskObject.getTaskDate();
        userObject = ParseUser.getCurrentUser();
        queryTasks();


        init();



    }

    public void init() {
        tvTitle = findViewById(R.id.tvTitle);
        tvInfo = findViewById(R.id.tvInfo);
        toggleSwitch = findViewById(R.id.toggleSwitch);

        counterBox = findViewById(R.id.counterBox);
        incrementButton = findViewById(R.id.incrementButton);
        textViewCounter = findViewById(R.id.textViewCounter);
        decrementButton = findViewById(R.id.decrementButton);

        FriendsBox = findViewById(R.id.FriendsBox);
        youTextBox = findViewById(R.id.youTextBox);
        youCompleteBox = findViewById(R.id.youCompleteBox);
        textStreak = findViewById(R.id.textStreak);
        friendsTextBox = findViewById(R.id.friendsTextBox);
        friendsCompleteBox = findViewById(R.id.friendsCompleteBox);

        statisticsBox = findViewById(R.id.statisticsBox);
        bestStreakBox = findViewById(R.id.bestStreakBox);
      //  currentStreakBox = findViewById(R.id.currentStreakBox);
        allTimeBox = findViewById(R.id.allTimeBox);
     //   todayBox = findViewById(R.id.todayBox);
        completionsBox = findViewById(R.id.completionsBox);
       // dayCompletions = findViewById(R.id.dayCompletions);

        mCalendarView =  findViewById(R.id.calendarViewDetails);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnComplete = findViewById(R.id.btnComplete);


        populate_habitDetails();
        populateBanner();
        populateStatistics();

    }


    public void populate_habitDetails () {
        userCounter = userTodayTaskObject.getCounter();
        userStringCount = Integer.toString(userCounter);
        description = "";
        Log.d(TAG, "title value..." + habitObject.getTitle() );
        tvTitle.setText(habitObject.getTitle());
        frequency = habitObject.getFrequency();
        recurrence = habitObject.getRecurrence();
        if (frequency == 1){
            counterBox.setVisibility(View.GONE);
            if (userCounter == 1)
                toggleSwitch.setChecked(true);
            else
                toggleSwitch.setChecked(false);
            description += "Once a day";
        }
        else {
            description += frequency + " times a day";
            textViewCounter.setText(userStringCount);
            toggleSwitch.setVisibility(View.GONE);
            decrementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Log.d(TAG, "Decreasing value...");
                    userCounter--;
                    userStringCount = Integer.toString(userCounter);
                    textViewCounter.setText(userStringCount);
                }
            });
            incrementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Log.d(TAG, "Increasing value...");
                    userCounter++;
                    userStringCount = Integer.toString(userCounter);
                    textViewCounter.setText(userStringCount);
                }
            });
        }
        if (recurrence == 0){
            description += ", everyday!";
        }
        else{
            description += ", weekly!";
//                Log.i(TAG,"fetching days:" + habit.getDays().getClass().getName());
//                Log.i(TAG,"fetching days:" + habit.getDays());
        }
        tvInfo.setText(description);
            }

    public void populateBanner(){
        userTaskCompletion = "";
        userTaskCompletion +=   userCounter+ "/" +frequency+ " completed";
        youCompleteBox.setText(userTaskCompletion);
        if (habitObject.getShared()) {
            friendCounter = friendTodayTask.getCounter();
            friendStringCount = Integer.toString(friendCounter);
            friendTaskCompletion = "";
            friendTaskCompletion += friendCounter + "/" + frequency + " completed";
            friendsCompleteBox.setText(friendTaskCompletion);
            friendsTextBox.setText(friendObject.getUsername());
        }


    }

    protected void queryTasks(){
        queryUserTasks(  );
        if (habitObject.getShared()){
            friendsTextBox.setVisibility(View.VISIBLE);
            friendsCompleteBox.setVisibility(View.VISIBLE);
            friendObject = queryFriend();
            queryFriendTasks();
        }

    }

    protected ParseUser queryFriend() {
        ParseQuery<HabitUserMapping> friendTasksQuery = ParseQuery.getQuery(HabitUserMapping.class);
        friendTasksQuery.include(HabitUserMapping.key_habit);
        friendTasksQuery.include(HabitUserMapping.key_user);
        friendTasksQuery.whereEqualTo(HabitUserMapping.key_habit, habitObject);
        friendTasksQuery.whereNotEqualTo(HabitUserMapping.key_user,ParseUser.getCurrentUser() );
//        friendTasksQuery.find()
        friendTasksQuery.findInBackground(new FindCallback<HabitUserMapping>() {
            @Override
            public void done(List<HabitUserMapping> friends, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting friends",e);
                    return;
                }
                for (HabitUserMapping friend : friends) {
                    friendObject = friend.getUser();
                    Log.d("task", "retrieved  friends" + friends.size());
                }
            }
        });
        return friendObject;
    }

    protected void queryUserTasks( ) {
        ParseQuery<Task> taskQuery = ParseQuery.getQuery(Task.class);
        taskQuery.include(Task.key_habit);
        taskQuery.include(Task.key_user);
        taskQuery.whereEqualTo(Task.key_user, userObject );
        taskQuery.whereEqualTo(Task.key_habit, habitObject);
        taskQuery.whereGreaterThanOrEqualTo("counter" ,1 );
        taskQuery.whereLessThanOrEqualTo(Task.key_task_date, dateObject);
        try {
            userTaskList = taskQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "in query user tasks");
//        taskQuery.findInBackground(new FindCallback<Task>() {
//            @Override
//            public void done(List<Task> tasks, ParseException e) {
//                if (e != null){
//                    Log.e(TAG, "Issue with getting tasks",e);
//                    return;
//                }
//                for (Task task : tasks) {
//                    Log.d(TAG, "retrieved a related user's task" + tasks.size() + task.getTaskDate());
//                }
//                userTaskList.addAll(tasks);
//            }
//        });
    }

    protected void queryFriendTasks() {
        ParseQuery<Task> taskQuery = ParseQuery.getQuery(Task.class);
        taskQuery.include(Task.key_habit);
        taskQuery.include(Task.key_user);
        taskQuery.whereEqualTo(Task.key_user, friendObject );
        taskQuery.whereEqualTo(Task.key_habit, habitObject);
        taskQuery.whereGreaterThanOrEqualTo("counter" ,1 );
        taskQuery.whereLessThanOrEqualTo(Task.key_task_date, dateObject);
        try {
            friendTaskList = taskQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        taskQuery.findInBackground(new FindCallback<Task>() {
//            @Override
//            public void done(List<Task> tasks, ParseException e) {
//                if (e != null){
//                    Log.e(TAG, "Issue with getting tasks",e);
//                    return;
//                }
//                for (Task task : tasks) {
//                    Log.d(TAG, "retrieved a related user's task" + tasks.size() + task.getTaskDate());
//                }
//                friendTaskList.addAll(tasks);
//            }
//        });

        ParseQuery<Task>  query = ParseQuery.getQuery(Task.class);
        query.include(Task.key_habit);
        query.include(Task.key_user);
        query.whereEqualTo(Task.key_user, friendObject );
        query.whereEqualTo(Task.key_habit, habitObject);
        query.whereEqualTo(Task.key_task_date, dateObject);
        query.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(List<Task> tasks, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting friend's today task",e);
                    return;
                }
                for (Task task : tasks) {
                    friendTodayTask = task;
                    Log.d(TAG, "retrieved a related friend's today task" + tasks.size() + task.getTaskDate());
                }
            }
        });
    }



    protected void populateStatistics( ) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
//        cal1.setTime(habitObject.getCreatedAt());
//        cal2.setTime(dateObject);

        cal1.set(habitObject.getCreatedAt().getYear(), habitObject.getCreatedAt().getMonth(),habitObject.getCreatedAt().getDate());
        cal2.set(dateObject.getYear(),dateObject.getMonth(),dateObject.getDate());

        Log.i(TAG, "cal1" + habitObject.getCreatedAt() + "cal2" + dateObject);
        int totalDays = (int)((cal2.getTime().getTime() - cal1.getTime().getTime())  / (1000 * 60 * 60 * 24) + 1) ;

        Log.i(TAG, "total days first" + totalDays);
        if (totalDays > 7){
        int totalWeeks = totalDays /7;
            Log.i(TAG, "total totalWeeks" + totalWeeks);
        totalDaysCount = totalWeeks * habitObject.getDays().size();
            Log.i(TAG, "total totalDaysCount" + totalDaysCount);
        totalDays = totalDays - totalWeeks*7;
            Log.i(TAG, "total days" + totalDays);}
        int dayOfWeek2 = cal2.get(Calendar.DAY_OF_WEEK);
        int dayOfWeek1 = dayOfWeek2 - totalDays + 1 ;
        Log.i(TAG, "total days" + totalDays);
        for ( int i = dayOfWeek1 ; i <= dayOfWeek2 ; i++){
            if( habitObject.getDays().contains(i)){
                  totalDaysCount += 1;
            }
            }
        Log.i(TAG, "total days" + totalDays);
        totalTasksCount = frequency * totalDaysCount;
        Log.i(TAG, "total days" + totalDays);
        for (Task task : userTaskList) {
            userTasksCompleted += task.getCounter();
            if (task.getCounter() >= task.getHabit().getFrequency()) {
                userDaysCompleted += 1;
            }
        }
        Log.i(TAG, "userTasksCompleted" + userTasksCompleted + "userDaysCompleted" + userDaysCompleted);

        for (Task friendTask : friendTaskList) {
            friendTasksCompleted += friendTask.getCounter();
            if (friendTask.getCounter() >= friendTask.getHabit().getFrequency()) {
                friendDaysCompleted += 1;
            }
        }
        Log.i(TAG, "total days" + totalDays);
        int total = userTasksCompleted + friendTasksCompleted;
        Log.i(TAG, "total days" + totalDays);
        completionsBox.setText(Integer.toString(total) + "/" + totalTasksCount);
        float percent = (total / totalTasksCount) * 100;
        allTimeBox.setText(Float.toString(percent)+" %");

        for (Task task : userTaskList) {
            datesList.add(task.getTaskDate());
                    }
        bestStreak = longestConsecutive(datesList);
        bestStreakBox.setText(Integer.toString(bestStreak));


//        Calendar calendar = Calendar.getInstance();
        for (Task eachTask : userTaskList){
            Date date = eachTask.getTaskDate();
            Calendar temp_cal = new GregorianCalendar();
            temp_cal.setTime(date);
            mEventDaysDone.add(new EventDay(temp_cal));
        }
        Calendar a = new GregorianCalendar();
        Calendar b = new GregorianCalendar();
        a.set(2020,12,7);
        b.set(2020,12,16);
        mEventDaysDone.add(new EventDay(a));
        mEventDaysDone.add(new EventDay(b));
        mCalendarView.setEvents(mEventDaysDone);





       }

    public int longestConsecutive(List<Date> dates) {
        HashSet<Date> set = new HashSet<>();
        for(Date date: dates) set.add(date);
        int result = 0;
        for(Date date: dates){
            Calendar temp_cal = new GregorianCalendar();
            int count = 1;
            temp_cal.setTime(date);
            temp_cal.add(Calendar.DATE, -1);
            Date temp_date_down = temp_cal.getTime();
            while(set.contains(temp_date_down)){
                set.remove(temp_date_down);
                Calendar temp_cal2 = new GregorianCalendar();
                temp_cal2.setTime(temp_date_down);
                temp_cal2.add(Calendar.DATE, -1);
                temp_date_down = temp_cal2.getTime();
                count++;
            }
            Calendar temp_cal3 = new GregorianCalendar();
            temp_cal3.setTime(date);
            temp_cal3.add(Calendar.DATE, 1);
            Date temp_date_up = temp_cal3.getTime();

            while(set.contains(temp_date_up)){
                set.remove(temp_date_up);
                Calendar temp_cal4 = new GregorianCalendar();
                temp_cal4.setTime(temp_date_down);
                temp_cal4.add(Calendar.DATE, 1);
                temp_date_up = temp_cal4.getTime();
                count++;
            }
            result = Math.max(result, count);
        }
        return result;
    }
    }