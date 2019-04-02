package com.manifest.fomo.Overview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;

import com.manifest.fomo.DetailedTypes.DetailedInfo;
import com.manifest.fomo.R;
import com.manifest.fomo.Utils.AppUsageDataUtils;
import com.manifest.fomo.Utils.CallLogDataUtils;
import com.manifest.fomo.Utils.ContactDataUtils;
import com.manifest.fomo.Utils.Utils;

import java.util.ArrayList;

public class OverviewPresenter implements OverviewContract.Presenter {

    private OverviewContract.View mView;
    private Context context;
    private int i = 0;

    public OverviewPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void init() {
        animateCards();
        calculateTotalUsage();
        setUpOverviewAdapter();
    }

    @Override
    public void calculateTotalUsage() {
        // Fetch Total Phone Usage Data //
        long totalDuration = AppUsageDataUtils.getTotalDuration(context);

        //Update View
        mView.setTodaysPhoneUsageScore(Utils.fetchFormattedTime(totalDuration));
        mView.showTodaysPhoneUsage();

        //int todaysScore = (Utils.fetchFOMOScore(totalDuration, CallLogDataUtils.fetchTotalCallDuration(context)));
        int todaysScore = (Utils.fetchFOMOScore(totalDuration));
        mView.showTodaysScore();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (i <= todaysScore) {
                    mView.setTodaysScore(Integer.toString(i));
                    if (i > 100) {
                        mView.setProgressColor(ContextCompat.getColor(context, R.color.RED));
                    }
                    if (i > 150) {
                        mView.setProgressValue(150);
                    } else {
                        mView.setProgressValue(i);
                    }
                    handler.postDelayed(this, 25);
                    i++;
                }
            }
        });
    }

    @Override
    public void animateCards() {
        mView.animateCards();
    }

    @Override
    public void setUpOverviewAdapter() {

        ArrayList<OverviewItem> overviewItems = new ArrayList<>();
        String name = "No name";

        // Fetch Contact Details //
        ArrayList<DetailedInfo> appUsageInfos = ContactDataUtils.fetchContactDetails(context);
        if (appUsageInfos.size() != 0)
            name = appUsageInfos.get(0).getAppName();
        overviewItems.add(new OverviewItem(name, "Most Contacted Person", "e1",
                ContextCompat.getColor(context, R.color.RED), OverviewEnum.MOST_CONTACTED_PERSON.getType()));

        // Fetch Call Logs Details //
        //name = CallLogDataUtils.fetchMaxCalledPerson(context);
        //overviewItems.add(new OverviewItem(name, "Most Called Person", "e1",
        //        ContextCompat.getColor(context, R.color.PURPLE), OverviewEnum.MOST_CALLED_PERSON.getType()));

        // Fetch App Usage Details //
        name = AppUsageDataUtils.fetchMaxUsedAppName(context);
        overviewItems.add(new OverviewItem(name, "Most Used App", "e1",
                ContextCompat.getColor(context, R.color.BLUE), OverviewEnum.MOST_USED_APP.getType()));

        mView.setUpOverviewAdapter(overviewItems);
    }

    @Override
    public void openAppUsageActivity() {
        mView.openAppUsageActivity();
    }

    @Override
    public void bindView(OverviewContract.View view) {
        if (mView == null) {
            mView = view;
        }
    }

    @Override
    public void unBindView() {
        mView = null;
    }
}