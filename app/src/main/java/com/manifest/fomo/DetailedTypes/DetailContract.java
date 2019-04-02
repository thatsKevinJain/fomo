package com.manifest.fomo.DetailedTypes;

import com.manifest.fomo.BasePresenter;
import com.manifest.fomo.BaseView;

import java.util.ArrayList;

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void showProgressBar();

        void hideProgressBar();

        void setTodaysPhoneUsageScore(String duration);

        void showTodaysPhoneUsage();

        void hideTodaysPhoneUsage();

        void setUpRecyclerView(ArrayList<DetailedInfo> appUsageItems);

        void setScreenTitle(String title);

    }

    interface Presenter extends BasePresenter<View> {
        void init();

        void fetchAppUsageData();
    }
}
