package com.manifest.fomo.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.manifest.fomo.MainActivity;
import com.manifest.fomo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    Context context;
    SplashPresenter mPresenter;
    @BindView(R.id.helpSplashTV)
    TextView helpSplashTV;
    @BindView(R.id.proceedButton)
    AppCompatButton proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        context = this;

        mPresenter = new SplashPresenter(context);
        mPresenter.bindView(this);
        mPresenter.init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.checkForUsageAccess();
                } else {
                    showPermissionsNotGrantedError();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showPermissionsRequiredDialog() {
        new MaterialDialog.Builder(this)
                .title("Permissions Required")
                .content("You will have to grant permissions for the app to work!")
                .positiveText("Ok")
                .onPositive((dialog, which) -> {
                    mPresenter.requestPermissions();
                }).show();
    }

    @Override
    public void showPermissionsNotGrantedError() {
        new MaterialDialog.Builder(this)
                .title("Permissions Not Granted!")
                .content("App Doesnt Work Without Permissions")
                .positiveText("Ok")
                .onPositive((dialog, which) -> {
                    onBackPressed();
                }).show();
    }

    @Override
    public void openUsageAccessPage() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
        Toast.makeText(context, "Click on FOMO and turn on Usage Access", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        new Handler().postDelayed((Runnable) () -> {
            Intent e1 = new Intent(context, MainActivity.class);
            context.startActivity(e1);
        }, 1000);
    }

    @Override
    public void showHelpMessage() {
        helpSplashTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHelpMessage() {
        helpSplashTV.setVisibility(View.GONE);
    }

    @Override
    public void showProceedButton() {
        proceedButton.setVisibility(View.VISIBLE);
        proceedButton.setOnClickListener(view -> {
            mPresenter.requestPermissions();
        });
    }

    @Override
    public void hideProceedButton() {
        proceedButton.setVisibility(View.GONE);
    }
}
