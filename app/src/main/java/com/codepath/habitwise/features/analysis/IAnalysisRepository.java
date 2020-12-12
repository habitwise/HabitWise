package com.codepath.habitwise.features.analysis;

import com.codepath.habitwise.models.Analysis;

import java.util.List;

public interface IAnalysisRepository {
    void getPersonalStreak(IAnalysisEventListner eventListner);
    void getSharedStreak(IAnalysisEventListner eventListner);
}
