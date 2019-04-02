package com.manifest.fomo.Splash;

import com.manifest.fomo.BasePresenter;
import com.manifest.fomo.BaseView;

public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void showPermissionsRequiredDialog();
        void showPermissionsNotGrantedError();
        void openUsageAccessPage();
        void openMainActivity();

        void showHelpMessage();
        void hideHelpMessage();
        void showProceedButton();
        void hideProceedButton();
    }

    interface Presenter extends BasePresenter<View> {
        void init();
        boolean checkForPermissions();
        void requestPermissions();
        void checkForUsageAccess();
    }
}