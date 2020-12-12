package com.codepath.habitwise.features.analysis;

import com.codepath.habitwise.features.home.HomeParseRepository;
import com.codepath.habitwise.features.home.IHomeRepository;
import com.codepath.habitwise.models.Analysis;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AnalysisMockRepository implements IAnalysisRepository {

    private static AnalysisMockRepository analysisMockRepository;

    private AnalysisMockRepository(){}

    public static IAnalysisRepository getInstance() {
        AnalysisMockRepository repo = analysisMockRepository;
        if (repo == null) {
            analysisMockRepository = new AnalysisMockRepository();
        }
        return analysisMockRepository;
    }

    @Override
    public void getPersonalStreak(final IAnalysisEventListner eventListner) {
        ParseQuery<HabitUserMapping> habitUserQuery = ParseQuery.getQuery(HabitUserMapping.class);
        habitUserQuery.include(HabitUserMapping.key_user);
        habitUserQuery.include(HabitUserMapping.key_habit);
        habitUserQuery.whereEqualTo(HabitUserMapping.key_user, ParseUser.getCurrentUser());
        habitUserQuery.findInBackground(new FindCallback<HabitUserMapping>() {
            @Override
            public void done(List<HabitUserMapping> objects, ParseException e) {
                List<Habit> habitsList = new ArrayList<>();
                for(HabitUserMapping hum : objects) {
                    habitsList.add(hum.getHabit());
                }
                ParseQuery<Analysis> analysisQuery = ParseQuery.getQuery(Analysis.class);
                analysisQuery.whereContainedIn(Analysis.key_habit, habitsList);
                analysisQuery.whereEqualTo(Analysis.key_isPersonal, true);
                analysisQuery.include(Analysis.key_habit);
                analysisQuery.include(Analysis.key_user1);
                analysisQuery.include(Analysis.key_user2);
                analysisQuery.findInBackground(new FindCallback<Analysis>() {
                    @Override
                    public void done(List<Analysis> objects, ParseException e) {
                        eventListner.onFetchOfPersonalStreakList(objects);
                    }
                });
            }
        });
    }

    @Override
    public void getSharedStreak(final IAnalysisEventListner eventListner) {
        ParseQuery<HabitUserMapping> habitUserQuery = ParseQuery.getQuery(HabitUserMapping.class);
        habitUserQuery.include(HabitUserMapping.key_user);
        habitUserQuery.include(HabitUserMapping.key_habit);
        habitUserQuery.whereEqualTo(HabitUserMapping.key_user, ParseUser.getCurrentUser());
        habitUserQuery.findInBackground(new FindCallback<HabitUserMapping>() {
            @Override
            public void done(List<HabitUserMapping> objects, ParseException e) {
                List<Habit> habitsList = new ArrayList<>();
                for(HabitUserMapping hum : objects) {
                    habitsList.add(hum.getHabit());
                }
                ParseQuery<Analysis> analysisQuery = ParseQuery.getQuery(Analysis.class);
                analysisQuery.whereContainedIn(Analysis.key_habit, habitsList);
                analysisQuery.whereEqualTo(Analysis.key_isPersonal, false);
                analysisQuery.include(Analysis.key_habit);
                analysisQuery.include(Analysis.key_user1);
                analysisQuery.include(Analysis.key_user2);
                analysisQuery.findInBackground(new FindCallback<Analysis>() {
                    @Override
                    public void done(List<Analysis> objects, ParseException e) {
                        eventListner.onFetchOfSharedStreakList(objects);
                    }
                });
            }
        });
    }
}
