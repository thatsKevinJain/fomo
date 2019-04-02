package com.manifest.fomo.Utils;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.manifest.fomo.DetailedTypes.DetailedInfo;
import com.manifest.fomo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUsageDataUtils {

    public static ArrayList<DetailedInfo> getUsageStatistics(Context context) {

        UsageEvents.Event currentEvent;
        List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, DetailedInfo> map = new HashMap<>();

        Calendar b = Calendar.getInstance();
        b.set(Calendar.HOUR_OF_DAY, 0);
        b.set(Calendar.MINUTE, 0);
        b.set(Calendar.SECOND, 0);

        // Begin Time = Today's Date at 00:00:00//
        long beginTime = b.getTimeInMillis();

        // End Time = Current Time //
        long endTime = System.currentTimeMillis();

        UsageStatsManager mUsageStatsManager = (UsageStatsManager)
                context.getSystemService(Context.USAGE_STATS_SERVICE);

        assert mUsageStatsManager != null;
        UsageEvents usageEvents = mUsageStatsManager.queryEvents(beginTime, endTime);

        final PackageManager pm = context.getPackageManager();

        //capturing all events in a array to compare with next element

        while (usageEvents.hasNextEvent()) {
            currentEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);
            if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                    currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                allEvents.add(currentEvent);
                String key = currentEvent.getPackageName();

                // taking it into a collection to access by package name
                if (map.get(key) == null) {
                    map.put(key, new DetailedInfo(key));

                    ApplicationInfo ai;
                    try {
                        ai = pm.getApplicationInfo(key, 0);
                        Drawable icon = pm.getApplicationIcon(key);
                        map.get(key).setAppIcon(icon);
                    } catch (final PackageManager.NameNotFoundException e) {
                        map.get(key).setAppIcon(context.getDrawable(R.drawable.ic_apps));
                        ai = null;
                    }
                    map.get(key).setAppName((String) (ai != null ? pm.getApplicationLabel(ai) : key));
                }
            }
        }

        //iterating through the array list
        for (int i = 0; i < allEvents.size() - 1; i++) {
            UsageEvents.Event E0 = allEvents.get(i);
            UsageEvents.Event E1 = allEvents.get(i + 1);

            //for launchCount of apps in time range
            if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType() == 1) {
                // if true, E1 (launch event of an app) app launched
                map.get(E1.getPackageName()).launchCount++;
            }

            //for UsageTime of apps in time range
            if (E0.getEventType() == 1 && E1.getEventType() == 2
                    && E0.getClassName().equals(E1.getClassName())) {
                long diff = (E1.getTimeStamp() - E0.getTimeStamp()) / (1000 * 60);
                map.get(E0.getPackageName()).timeInForeground += diff;
            }
        }

        ArrayList<DetailedInfo> appUsageInfos = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, DetailedInfo> entry : map.entrySet()) {
            long duration = entry.getValue().getTimeInForeground();
            if (duration > 0) {
                appUsageInfos.add(new DetailedInfo(entry.getValue().getAppName(),
                        (int) duration));
                appUsageInfos.get(i).setAppIcon(entry.getValue().getAppIcon());
                appUsageInfos.get(i).setLaunchCount(entry.getValue().getLaunchCount());
                appUsageInfos.get(i).setPackageName(entry.getValue().getPackageName());
                i++;
            }
        }

        //transferred final data into modal class object
        Collections.sort(appUsageInfos, (lhs, rhs) -> Long.compare(rhs.getTimeInForeground(), lhs.getTimeInForeground()));
        return appUsageInfos;
    }

    public static long getTotalDuration(Context context) {
        ArrayList<DetailedInfo> appUsageInfos = getUsageStatistics(context);
        long phoneUsageDuration = 0;
        for (DetailedInfo info : appUsageInfos) {
            phoneUsageDuration += info.getTimeInForeground();
        }
        return phoneUsageDuration;
    }

    public static String fetchMaxUsedAppName(Context context) {
        ArrayList<DetailedInfo> appUsageInfos = getUsageStatistics(context);
        if (appUsageInfos.size() != 0)
            return appUsageInfos.get(0).getAppName();
        else
            return "No name";
    }
}