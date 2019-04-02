package com.manifest.fomo.DetailedTypes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manifest.fomo.MainActivity;
import com.manifest.fomo.Overview.OverviewEnum;
import com.manifest.fomo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.todaysPhoneUsage)
    TextView todaysPhoneUsage;
    @BindView(R.id.helpIV)
    ImageView helpIV;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        context = this;

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        todaysPhoneUsage.setTextSize(16);
        todaysPhoneUsage.setTextColor(ContextCompat.getColor(context, R.color.BLACK));

        progressBar1.setVisibility(View.GONE);
        todaysPhoneUsage.setVisibility(View.VISIBLE);
        helpIV.setVisibility(View.GONE);

        int type = getIntent().getIntExtra(MainActivity.ITEM_TYPE, 1);
        if (type == OverviewEnum.MOST_USED_APP.getType()) {
            todaysPhoneUsage.setText(getResources().getString(R.string.help_todays_phone_usage));

        } else if (type == OverviewEnum.MOST_CONTACTED_PERSON.getType()) {
            title.setText(context.getResources().getString(R.string.total_times_contacted));
            todaysPhoneUsage.setText(getResources().getString(R.string.help_total_times_contacted));

        }
        //else if (type == OverviewEnum.MOST_CALLED_PERSON.getType()) {
        //    title.setText(context.getResources().getString(R.string.total_phone_call_duration));
        //    todaysPhoneUsage.setText(getResources().getString(R.string.help_total_phone_call_duration));
        //
        //}
        else if(type == 5){
            title.setText(context.getResources().getString(R.string.fomo_score));
            todaysPhoneUsage.setText(getResources().getString(R.string.help_fomo_score));

        }
    }
}
