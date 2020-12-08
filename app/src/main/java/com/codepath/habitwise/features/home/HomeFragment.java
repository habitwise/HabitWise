package com.codepath.habitwise.features.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private RecyclerView rvTasks;
    protected TaskAdapter adapter_task;
    protected List<Task> taskList;
    protected SwipeRefreshLayout swipeContainer;

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
                queryTasks();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rvTasks = view.findViewById(R.id.rvTasks);
        taskList = new ArrayList<>();
        adapter_task = new TaskAdapter(getContext(),taskList);
        rvTasks.setAdapter(adapter_task);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvTasks.setLayoutManager(manager);
        queryTasks();
    }

    protected void queryTasks() {
        ParseQuery<Task> taskQuery = ParseQuery.getQuery(Task.class);
        taskQuery.setLimit(20);
        taskQuery.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(List<Task> tasks, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting tasks",e);
                    return;
                }
                for (Task task : tasks) {
                    Log.d("post", "retrieved a related post");
                }
                adapter_task.clear();
                taskList.addAll(tasks);
//                swipeContainer.setRefreshing(false);
                adapter_task.notifyDataSetChanged();

            }
        });
    }
}
