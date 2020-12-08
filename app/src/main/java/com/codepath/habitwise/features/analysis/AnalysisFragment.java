package com.codepath.habitwise.features.analysis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.features.addUpdateHabit.AddHabitActivity;
import com.codepath.habitwise.features.loginSignup.LoginActivity;
import com.codepath.habitwise.models.Habit;
import com.parse.ParseUser;

public class AnalysisFragment extends Fragment {
    private Habit emptyHabit;
    private Button btnNewHabit;
    private Button btnLogout;

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
        btnLogout = view.findViewById(R.id.btnLogout);

        btnNewHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddHabitActivity.class);
                intent.putExtra("habit", emptyHabit);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Logging out", Toast.LENGTH_LONG).show();
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish(); //remove from stack
            }
        });
    }

}