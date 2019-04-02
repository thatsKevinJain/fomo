package com.manifest.fomo.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.manifest.fomo.DetailedTypes.DetailedInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CallLogDataUtils {

    public static ArrayList<DetailedInfo> fetchCallLogDetails(Context context) {
        String[] strFields = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
        };

        // Defines a string to contain the selection clause
        String mSelectionClause = CallLog.Calls.DATE + " >= ? AND " + CallLog.Calls.DURATION + " > 0";

        // Defines Sort Order //
        String sortOrder = CallLog.Calls.DURATION + " DESC";

        Calendar b = Calendar.getInstance();
        b.set(Calendar.HOUR_OF_DAY, 0);
        b.set(Calendar.MINUTE, 0);
        b.set(Calendar.SECOND, 0);

        // Begin Time = Today's Date at 00:00:00//
        long beginTime = b.getTimeInMillis();

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = {Long.toString(beginTime)};

        Cursor mCallCursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                strFields,
                mSelectionClause,
                mSelectionArgs,
                sortOrder);

        ArrayList<DetailedInfo> detailedInfos = new ArrayList<>();
        HashMap<String, Long> map = new HashMap<>();

        int nameID = mCallCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int durationID = mCallCursor.getColumnIndex(CallLog.Calls.DURATION);

        while (mCallCursor.moveToNext()) {
            String name = mCallCursor.getString(nameID);
            if (name == null) {
                name = mCallCursor.getString(mCallCursor.getColumnIndex(CallLog.Calls.NUMBER));
            }
            long duration = Long.parseLong(mCallCursor.getString(durationID)) / 60;
            if (duration > 0) {
                if (map.containsKey(name)) {
                    map.put(name, map.get(name) + duration);
                } else
                    map.put(name, duration);
            }
        }
        mCallCursor.close();

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            detailedInfos.add(new DetailedInfo(entry.getKey(), entry.getValue()));
        }

        Collections.sort(detailedInfos, (lhs, rhs) -> Long.compare(rhs.getTimeInForeground(), lhs.getTimeInForeground()));

        return detailedInfos;
    }

    public static long fetchTotalCallDuration(Context context) {
        ArrayList<DetailedInfo> detailedInfos;
        detailedInfos = fetchCallLogDetails(context);
        long totalDuration = 0;
        for (DetailedInfo detailedInfo : detailedInfos) {
            totalDuration += detailedInfo.getTimeInForeground();
        }
        return totalDuration;
    }

    public static String fetchMaxCalledPerson(Context context) {
        ArrayList<DetailedInfo> detailedInfos;
        detailedInfos = fetchCallLogDetails(context);
        if (detailedInfos.size() != 0)
            return detailedInfos.get(0).getAppName();
        else
            return "No name";
    }
}
