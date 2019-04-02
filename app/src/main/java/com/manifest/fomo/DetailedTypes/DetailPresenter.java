package com.manifest.fomo.DetailedTypes;

import android.content.Context;

import com.manifest.fomo.Overview.OverviewEnum;
import com.manifest.fomo.R;
import com.manifest.fomo.Utils.AppUsageDataUtils;
import com.manifest.fomo.Utils.CallLogDataUtils;
import com.manifest.fomo.Utils.ContactDataUtils;
import com.manifest.fomo.Utils.Utils;

public class DetailPresenter implements DetailContract.Presenter {

    private Context context;
    private DetailContract.View mView;
    private int type;

    DetailPresenter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public void init() {

        long totalDuration = 0;
        if (type == OverviewEnum.MOST_USED_APP.getType()) {
            totalDuration = AppUsageDataUtils.getTotalDuration(context);
            mView.setTodaysPhoneUsageScore(Utils.fetchFormattedTime(totalDuration));
        } else if (type == OverviewEnum.MOST_CONTACTED_PERSON.getType()) {
            totalDuration = ContactDataUtils.fetchTotalTimesContacted(context);
            mView.setTodaysPhoneUsageScore(Long.toString(totalDuration));
            mView.setScreenTitle(context.getResources().getString(R.string.total_times_contacted));
        }
        //else if (type == OverviewEnum.MOST_CALLED_PERSON.getType()) {
        //    totalDuration = CallLogDataUtils.fetchTotalCallDuration(context);
        //    mView.setTodaysPhoneUsageScore(Utils.fetchFormattedTime(totalDuration));
        //    mView.setScreenTitle(context.getResources().getString(R.string.total_phone_call_duration));
        //}

        //Update View
        mView.hideProgressBar();
        mView.showTodaysPhoneUsage();
        fetchAppUsageData();
    }

    @Override
    public void fetchAppUsageData() {
        if (type == OverviewEnum.MOST_USED_APP.getType()) {
            mView.setUpRecyclerView(AppUsageDataUtils.getUsageStatistics(context));
        } else if (type == OverviewEnum.MOST_CONTACTED_PERSON.getType()) {
            mView.setUpRecyclerView(ContactDataUtils.fetchContactDetails(context));
        }
        //else if (type == OverviewEnum.MOST_CALLED_PERSON.getType()) {
        //    mView.setUpRecyclerView(CallLogDataUtils.fetchCallLogDetails(context));
        //}
    }

    @Override
    public void bindView(DetailContract.View view) {
        if (mView == null) {
            mView = view;
        }
    }

    @Override
    public void unBindView() {
        mView = null;
    }
}
