package com.manifest.fomo.DetailedTypes;

import android.graphics.drawable.Drawable;

public class DetailedInfo {
    Drawable appIcon;
    String appName, packageName;
    public long timeInForeground;
    public int launchCount;

    public DetailedInfo(String appName, long timeInForeground) {
        this.appName = appName;
        this.timeInForeground = timeInForeground;
    }

    public DetailedInfo(String pName) {

        this.packageName = pName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getTimeInForeground() {
        return timeInForeground;
    }

    public void setTimeInForeground(long timeInForeground) {
        this.timeInForeground = timeInForeground;
    }

    public int getLaunchCount() {
        return launchCount;
    }

    public void setLaunchCount(int launchCount) {
        this.launchCount = launchCount;
    }
}
