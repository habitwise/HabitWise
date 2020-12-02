package com.codepath.habitwise.features.analysis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.habitwise.R;
import com.codepath.habitwise.features.addUpdateHabit.AddHabitActivity;
import com.codepath.habitwise.models.Habit;

public class AnalysisFragment extends Fragment {
    private static final int RESULT_OK = 1;
    private Habit emptyHabit;
    private Button btnNewHabit;
    private final int REQUEST_CODE = 20;

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
        btnNewHabit = view.findViewById(R.id.btnNewHabit);

        btnNewHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddHabitActivity.class);
                intent.putExtra("habit", emptyHabit);
                startActivity(intent);
            }
        });
    }

}