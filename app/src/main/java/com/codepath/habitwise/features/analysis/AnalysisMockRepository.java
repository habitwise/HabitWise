package com.codepath.habitwise.features.analysis;

import com.codepath.habitwise.features.home.HomeParseRepository;
import com.codepath.habitwise.features.home.IHomeRepository;
import com.codepath.habitwise.models.Analysis;
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
                List<Analysis> analysisList = new ArrayList<>();
                for(HabitUserMapping hum : objects) {
                    Analysis analysis = new Analysis(hum.getHabit());
                    analysis.setUser1(hum.getUser());
                    analysis.setCurrentStreak(100);
                    analysisList.add(analysis);
                }
                eventListner.onFetchOfPersonalStreakList(analysisList);
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
                List<Analysis> analysisList = new ArrayList<>();
                for(HabitUserMapping hum : objects) {
                    Analysis analysis = new Analysis(hum.getHabit());
                    analysis.setUser1(hum.getUser());
                    analysis.setUser2(hum.getUser());
                    analysis.setCurrentStreak(100);
                    analysisList.add(analysis);
                }
                eventListner.onFetchOfSharedStreakList(analysisList);
            }
        });
    }
}
