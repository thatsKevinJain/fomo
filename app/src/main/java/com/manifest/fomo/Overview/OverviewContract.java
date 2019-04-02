package com.manifest.fomo.Overview;

import com.manifest.fomo.BasePresenter;
import com.manifest.fomo.BaseView;

import java.util.ArrayList;

public interface OverviewContract {

    interface View extends BaseView<Presenter> {

        void showTodaysPhoneUsage();

        void hideTodaysPhoneUsage();

        void showTodaysScore();

        void hideTodaysScore();

        void animateCards();

        void setUpOverviewAdapter(ArrayList<OverviewItem> overviewItems);

        void setTodaysPhoneUsageScore(String text);

        void setTodaysScore(String score);

        void openAppUsageActivity();

        void setProgressColor(int color);

        void setProgressValue(float number);
    }

    interface Presenter extends BasePresenter<View> {
        void init();

        void calculateTotalUsage();

        void animateCards();

        void setUpOverviewAdapter();

        void openAppUsageActivity();
    }
}
