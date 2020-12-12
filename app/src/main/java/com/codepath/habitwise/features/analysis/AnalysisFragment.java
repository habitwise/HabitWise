package com.codepath.habitwise.features.analysis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Analysis;
import com.codepath.habitwise.models.Habit;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AnalysisFragment extends Fragment implements IAnalysisEventListner{
    private Habit emptyHabit;
    private TextView tvSharedStreak;
    private TextView tvPersonalStreak;
    private RecyclerView rvSharedStreak;
    private RecyclerView rvPersonalStreak;
    private List<Analysis> personalAnalysis;
    private List<Analysis> sharedAnalysis;
    private PersonalAnalysisAdapter personalAnalysisAdapter;
    private SharedAnalysisAdapter sharedAnalysisAdapter;


    public AnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyHabit = new Habit();

        // Starts here
        tvPersonalStreak = view.findViewById(R.id.tvPersonalStreak);
        tvSharedStreak = view.findViewById(R.id.tvSharedStreak);
        rvPersonalStreak = view.findViewById(R.id.rvPersonalStreak);
        rvSharedStreak = view.findViewById(R.id.rvSharedStreak);

        IAnalysisRepository repo = AnalysisMockRepository.getInstance();
        repo.getPersonalStreak(this);
        repo.getSharedStreak(this);

        personalAnalysis = new ArrayList<>();
        sharedAnalysis = new ArrayList<>();
        personalAnalysisAdapter = new PersonalAnalysisAdapter(getContext(), personalAnalysis);
        sharedAnalysisAdapter = new SharedAnalysisAdapter(getContext(), sharedAnalysis);
        rvPersonalStreak.setAdapter(personalAnalysisAdapter);
        rvSharedStreak.setAdapter(sharedAnalysisAdapter);
        rvPersonalStreak.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSharedStreak.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onFetchOfPersonalStreakList(List<Analysis> personalAnalysisList) {
        personalAnalysis.clear();
        personalAnalysis.addAll(personalAnalysisList);
        personalAnalysisAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchOfSharedStreakList(List<Analysis> sharedAnalysisList) {
        sharedAnalysis.clear();
        sharedAnalysis.addAll(sharedAnalysisList);
        sharedAnalysisAdapter.notifyDataSetChanged();
    }
}