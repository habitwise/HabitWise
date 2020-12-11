package com.codepath.habitwise.features.habitDetails;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.codepath.habitwise.R;
import com.codepath.habitwise.features.addUpdateHabit.AddHabitActivity;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.codepath.habitwise.models.Task;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;


public class detailsActivity extends AppCompatActivity {
    public static final String TAG = "detailsActivity";
    private TextView tvTitle;
    private TextView tvInfo;
    private Switch toggleSwitch;

    private RelativeLayout counterBox;
    private Button incrementButton;
    private TextView textViewCounter;
    private Button decrementButton;

    private TextView youCompleteBox;
    private TextView currentStreakBox;
    private TextView friendsTextBox;
    private TextView friendsCompleteBox;

    private TextView bestStreakBox;
    private TextView allTimeBox;
    private TextView completionsBox;

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
    private int currentStreak = 0;

    private List<EventDay> mEventDaysDone = new ArrayList<>();
    private List<EventDay> mEventDaysIncomplete = new ArrayList<>();
    private List<EventDay> mEventDaysHalfComplete = new ArrayList<>();



     List<Task> userTaskList = new ArrayList<>();
     List<Task> friendTaskList = new ArrayList<>();
    List<Date> userDatesList = new ArrayList<>();
    List<Date> friendDatesList = new ArrayList<>();
    List<Date> datesList = new ArrayList<>();
    List<HabitUserMapping> friends = new ArrayList<>();

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

        youCompleteBox = findViewById(R.id.youCompleteBox);
        currentStreakBox = findViewById(R.id.currentStreakBox);
        friendsTextBox = findViewById(R.id.friendsTextBox);
        friendsCompleteBox = findViewById(R.id.friendsCompleteBox);

        bestStreakBox = findViewById(R.id.bestStreakBox);
        allTimeBox = findViewById(R.id.allTimeBox);
        completionsBox = findViewById(R.id.completionsBox);
        mCalendarView =  findViewById(R.id.calendarViewDetails);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnComplete = findViewById(R.id.btnComplete);
        populate_habitDetails();
        populateBanner();
        populateStatistics();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitObject.deleteInBackground();
            }
        });

    }


    public void populate_habitDetails () {
        userCounter = userTodayTaskObject.getCounter();
        userStringCount = Integer.toString(userCounter);
        description = "";
        tvTitle.setText(habitObject.getTitle());
        frequency = habitObject.getFrequency();
        recurrence = habitObject.getRecurrence();
        if (frequency == 1){
            toggleSwitch.setVisibility(View.VISIBLE);
            counterBox.setVisibility(View.GONE);
            if (userCounter == 1)
                toggleSwitch.setChecked(true);
            else
                toggleSwitch.setChecked(false);
            description += "Once";

            toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) userTodayTaskObject.setCounter(1);
                    else userTodayTaskObject.setCounter(0);
                    userTodayTaskObject.saveInBackground();
                }
            });
        }
        else {
            description += frequency + " times";
            textViewCounter.setText(userStringCount);
            toggleSwitch.setVisibility(View.GONE);
            counterBox.setVisibility(View.VISIBLE);
            decrementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Decreasing value...");
                    if (userCounter - 1 < 0) return;
                    userCounter--;
                    userStringCount = Integer.toString(userCounter);
                    textViewCounter.setText(userStringCount);
                    userTodayTaskObject.setCounter(userCounter);
                    userTodayTaskObject.saveInBackground();
                }
            });
            incrementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Increasing value...");
                    userCounter++;
                    userStringCount = Integer.toString(userCounter);
                    textViewCounter.setText(userStringCount);
                    userTodayTaskObject.setCounter(userCounter);
                    userTodayTaskObject.saveInBackground();
                }
            });
        }
        if (recurrence == 0){
            description += " everyday!";
        }
        else{
            description += ", weekly!";
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
            friendsTextBox.setVisibility(View.VISIBLE);
            friendsCompleteBox.setVisibility(View.VISIBLE);
            friendsTextBox.setText(friendObject.getUsername());
        }
    }

    protected void queryTasks(){
        queryUserTasks(  );
        if (habitObject.getShared()){
            friendObject = queryFriend();
            queryFriendTasks(friendObject);
        }

    }

    protected ParseUser queryFriend() {
        ParseQuery<HabitUserMapping> friendTasksQuery = ParseQuery.getQuery(HabitUserMapping.class);
        friendTasksQuery.include(HabitUserMapping.key_habit);
        friendTasksQuery.include(HabitUserMapping.key_user);
        friendTasksQuery.whereEqualTo(HabitUserMapping.key_habit, habitObject);
        friendTasksQuery.whereNotEqualTo(HabitUserMapping.key_user,ParseUser.getCurrentUser() );
        try {
            friends = friendTasksQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for ( HabitUserMapping friend : friends){
            friendObject = friend.getUser();
        }
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

    }

    protected void queryFriendTasks(ParseUser friendUser) {
        ParseQuery<Task> taskQuery = ParseQuery.getQuery(Task.class);
        Log.i(TAG, "friendTask--------" + habitObject.getTitle() + friendUser.getObjectId());
        taskQuery.include(Task.key_habit);
        taskQuery.include(Task.key_user);
        taskQuery.whereEqualTo(Task.key_user, friendUser );
        taskQuery.whereEqualTo(Task.key_habit, habitObject);
//        taskQuery.whereGreaterThanOrEqualTo("counter" ,1 );
        taskQuery.whereLessThanOrEqualTo(Task.key_task_date, dateObject);
        try {
            friendTaskList = taskQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean saveTask = true;


        for(Task friendTask : friendTaskList){
            Log.i(TAG, "friendTask" + friendTask.getHabit().getTitle());
            if(friendTask.getTaskDate().equals(dateObject)){
                friendTodayTask = friendTask;
                saveTask = false;

            }
            if(friendTask.getCounter() == 0){
                friendTaskList.remove(friendTask);

            }
        }
        if(saveTask){
            Task newTask = new Task();
            newTask.setCounter(0);
            newTask.setHabit(habitObject);
            newTask.setUser(friendObject);
            newTask.setTaskDate(dateObject);
            newTask.saveInBackground();
            friendTaskList.add(newTask);
            friendTodayTask = newTask;
        }


    }



    protected void populateStatistics( ) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.set(habitObject.getCreatedAt().getYear(), habitObject.getCreatedAt().getMonth(),habitObject.getCreatedAt().getDate());
        cal2.set(dateObject.getYear(),dateObject.getMonth(),dateObject.getDate());

        int totalDays = (int)((cal2.getTime().getTime() - cal1.getTime().getTime())  / (1000 * 60 * 60 * 24) + 1) ;

        if (totalDays > 7){
        int totalWeeks = totalDays /7;
        totalDaysCount = totalWeeks * habitObject.getDays().size();
        totalDays = totalDays - totalWeeks*7;
        }
        int dayOfWeek2 = cal2.get(Calendar.DAY_OF_WEEK);
        int dayOfWeek1 = dayOfWeek2 - totalDays + 1 ;
        for ( int i = dayOfWeek1 ; i <= dayOfWeek2 ; i++){
            if( habitObject.getDays().contains(i)){
                  totalDaysCount += 1;
            }
            }
        totalTasksCount = frequency * totalDaysCount;
        for (Task task : userTaskList) {
            userTasksCompleted += task.getCounter();
            if (task.getCounter() >= task.getHabit().getFrequency()) {
                Date date = task.getTaskDate();
                Calendar temp_cal = new GregorianCalendar();
                temp_cal.setTime(date);
                temp_cal.clear(Calendar.HOUR_OF_DAY);
                temp_cal.clear(Calendar.MINUTE);
                temp_cal.clear(Calendar.SECOND);
                temp_cal.clear(Calendar.MILLISECOND);
                userDatesList.add(temp_cal.getTime());
            }
        }

        for (Task friendTask : friendTaskList) {
            friendTasksCompleted += friendTask.getCounter();
            if (friendTask.getCounter() >= friendTask.getHabit().getFrequency()) {
                Date date = friendTask.getTaskDate();
                Calendar temp_cal = new GregorianCalendar();
                temp_cal.setTime(date);
                temp_cal.clear(Calendar.HOUR_OF_DAY);
                temp_cal.clear(Calendar.MINUTE);
                temp_cal.clear(Calendar.SECOND);
                temp_cal.clear(Calendar.MILLISECOND);
                friendDatesList.add(temp_cal.getTime());
            }
        }
        int total = userTasksCompleted + friendTasksCompleted;
        completionsBox.setText(Integer.toString(total) + "/" + totalTasksCount);
        float percent = (total / totalTasksCount) * 100;
        allTimeBox.setText(Float.toString(percent)+" %");

        if(habitObject.getShared()){
        userDatesList.retainAll(friendDatesList);


        }
        userDatesList.sort((d1,d2) -> d1.compareTo(d2));

        bestStreak = longestConsecutive(userDatesList);
        bestStreakBox.setText(Integer.toString(bestStreak));
        currentStreakBox.setText(Integer.toString(currentStreak));

        List<Calendar> calendars = new ArrayList<>();
        for (Date eachDate : userDatesList){
            Calendar temp_cal = new GregorianCalendar();
            temp_cal.setTime(eachDate);
            calendars.add(temp_cal);
            mEventDaysDone.add(new EventDay(temp_cal,R.drawable.sample_drawable, Color.parseColor("#228B22")));
        }

        mCalendarView.setHighlightedDays(calendars);
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
            currentStreak = count;
        }

        return result;
    }
    }