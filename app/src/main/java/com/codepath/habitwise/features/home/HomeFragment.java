package com.codepath.habitwise.features.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Habit;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private RecyclerView rvHabits;
    protected HabitAdapter adapter;
    protected List<Habit> HabitList;
    protected SwipeRefreshLayout swipeContainer;
//    EndlessRecyclerViewScrollListener scrollListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer =  view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data");
                queryHabits();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rvHabits = view.findViewById(R.id.rvHabits);
        HabitList = new ArrayList<>();
        adapter = new HabitAdapter(getContext(),HabitList);
        rvHabits.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHabits.setLayoutManager(layoutManager);
        queryHabits();
    }



    protected void queryHabits() {
        ParseQuery<Habit> query = ParseQuery.getQuery(Habit.class);
        query.include(Habit.key_title);
        query.setLimit(20);
        query.findInBackground(new FindCallback<Habit>() {
            @Override
            public void done(List<Habit> habits, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting habits",e);
                    return;
                }
                for (Habit post : habits) {
                    Log.i(TAG, "Habit:" + post.getTitle() );
                }
                adapter.clear();
                HabitList.addAll(habits);
//                swipeContainer.setRefreshing(false);
                adapter.notifyDataSetChanged();

            }
        });
    }
}
