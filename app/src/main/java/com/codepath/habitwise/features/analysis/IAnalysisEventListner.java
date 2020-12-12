package com.codepath.habitwise.features.analysis;

import com.codepath.habitwise.models.Analysis;

import java.util.List;

public interface IAnalysisEventListner {
    void onFetchOfPersonalStreakList(List<Analysis> personalAnalysisList);
    void onFetchOfSharedStreakList(List<Analysis> sharedAnalysisList);
}
