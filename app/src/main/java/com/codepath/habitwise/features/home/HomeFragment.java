package com.codepath.habitwise.features.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.habitwise.R;
import com.codepath.habitwise.features.Utilities;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.Task;
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver;
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter;
import com.michalsvec.singlerowcalendar.utils.DateUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeFragment extends Fragment implements IHomeEventListner{

    public static final String TAG = "HOME_FRAGMENT";
    public List<Habit> habits;
    public List<Task> tasks;
    private Calendar selectedCal;
    protected SwipeRefreshLayout swipeContainer;
    private RecyclerView rvTasks;
    protected TaskAdapter taskAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        habits = new ArrayList<>();
        tasks = new ArrayList<>();
        selectedCal = Utilities.getDateWithoutTimeUsingCalendar(Calendar.getInstance());

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selectedCal = Utilities.getDateWithoutTimeUsingCalendar(date);
                Log.i(TAG, selectedCal.getTime().toString() + "");
                queryTasksByDate(selectedCal);
            }
        });

        queryTasksByDate(selectedCal);

        swipeContainer =  view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data");
                queryTasksByDate(selectedCal);
            }

        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rvTasks = view.findViewById(R.id.rvTasks);
        taskAdapter = new TaskAdapter(getContext(),tasks);
        rvTasks.setAdapter(taskAdapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvTasks.setLayoutManager(manager);
    }

    protected void queryTasksByDate(Calendar date){
        IHomeRepository homeRepository = HomeParseRepository.getInstance();
        homeRepository.getTaskListByDate(date, this);
    }

    @Override
    public void onFecthOfTaskListByDate(List<Task> taskList) {
        tasks.clear();
        tasks.addAll(taskList);
        taskAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onFetchOfHabitList() {
        Log.i(TAG, "Fetch of habit list");
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, "Error occured", e);
    }

}