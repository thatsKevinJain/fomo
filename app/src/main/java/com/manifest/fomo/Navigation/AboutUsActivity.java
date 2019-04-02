package com.manifest.fomo.Navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.manifest.fomo.Utils.Utils;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Utils.createAboutUs(this));
    }
}
