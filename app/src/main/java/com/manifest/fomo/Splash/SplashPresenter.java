package com.manifest.fomo.Splash;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;
    private Context context;

    SplashPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void init() {
        PackageManager pm = context.getPackageManager();
        int hasPerm1 = pm.checkPermission(
                android.Manifest.permission.READ_CONTACTS,
                context.getPackageName());
        int hasPerm2 = pm.checkPermission(
                android.Manifest.permission.WRITE_CONTACTS,
                context.getPackageName());
        int hasPerm3 = pm.checkPermission(
                android.Manifest.permission.READ_CALL_LOG,
                context.getPackageName());
        int hasPerm4 = pm.checkPermission(
                android.Manifest.permission.READ_PHONE_STATE,
                context.getPackageName());

        if (hasPerm1 == PackageManager.PERMISSION_GRANTED
                && hasPerm2 == PackageManager.PERMISSION_GRANTED
                && hasPerm3 == PackageManager.PERMISSION_GRANTED
                && hasPerm4 == PackageManager.PERMISSION_GRANTED) {
            checkForUsageAccess();
        } else {
            mView.showHelpMessage();
            mView.showProceedButton();
        }
    }

    @Override
    public boolean checkForPermissions() {
        return false;
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_CONTACTS
                }, 101);
    }

    @Override
    public void checkForUsageAccess() {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            assert appOpsManager != null;
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            if (mode == AppOpsManager.MODE_ALLOWED) {
                mView.openMainActivity();
                mView.hideHelpMessage();
                mView.hideProceedButton();
            } else
                mView.openUsageAccessPage();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindView(SplashContract.View view) {
        if (mView == null) {
            mView = view;
        }
    }

    @Override
    public void unBindView() {
        mView = null;
    }
}
