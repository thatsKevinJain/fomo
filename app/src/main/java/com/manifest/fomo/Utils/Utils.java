package com.manifest.fomo.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.manifest.fomo.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class Utils {

    public static String fetchFormattedTime(long time) {
        if (time == 0) {
            return "0 min";
        } else if (time == 1) {
            return "1 min";
        } else if (time < 60) {
            return time + " mins";
        } else if (time < 120) {
            return (time / 60) + " hour " + (time % 60) + " mins";
        } else
            return (time / 60) + " hours " + (time % 60) + " mins";
    }

    public static int fetchFOMOScore(long appUsageDuration) {
        //return (int) ((appUsageDuration * 0.66) + (callUsageDuration * 0.33));
        return (int) ((appUsageDuration * 0.99));
    }

    // Returns the URL for the Facebook Page of Team Manifest //
    public static String getFacebookPageURL(PackageManager packageManager) {
        String FACEBOOK_URL = "https://www.facebook.com/teamanifest/";
        String FACEBOOK_PAGE_ID = "442475519480804";
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                //newer versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            } else {
                //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    // This will create an About Us Page Dynamically //
    public static View createAboutUs(Context context) {
        String version = "1.0";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Element versionElement = new Element();
        versionElement.setTitle(String.format(context.getResources().getString(R.string.version), version));
        return new AboutPage(context)
                .isRTL(false)
                .setDescription(context.getResources().getString(R.string.about_us_description))
                .setImage(R.mipmap.ic_launcher)
                .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("thatskevinjain@gmail.com")
                .addWebsite("https://myfomo.000webhostapp.com/")
                .addFacebook("teamanifest")
                .addTwitter("TTrack11")
                .addPlayStore("com.manifest.fomo")
                .create();
    }
}