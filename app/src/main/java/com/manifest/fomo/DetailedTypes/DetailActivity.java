package com.manifest.fomo.DetailedTypes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manifest.fomo.MainActivity;
import com.manifest.fomo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    @BindView(R.id.todaysPhoneUsage)
    TextView todaysPhoneUsage;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.detailed_recycler)
    RecyclerView detailedRV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.helpIV)
    ImageView helpIV;

    DetailPresenter mPresenter;
    int type;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        context = this;

        type = getIntent().getIntExtra(MainActivity.ITEM_TYPE, 1);

        mPresenter = new DetailPresenter(context, type);
        mPresenter.bindView(this);
        mPresenter.init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        helpIV.setOnClickListener(view -> {
            Intent e1 = new Intent(context, HelpActivity.class);
            e1.putExtra(MainActivity.ITEM_TYPE, type);
            startActivity(e1);
        });
    }

    @Override
    public void showProgressBar() {
        progressBar1.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar1.setVisibility(View.GONE);
    }

    @Override
    public void setTodaysPhoneUsageScore(String duration) {
        todaysPhoneUsage.setText(duration);
    }

    @Override
    public void showTodaysPhoneUsage() {
        todaysPhoneUsage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTodaysPhoneUsage() {
        todaysPhoneUsage.setVisibility(View.GONE);
    }

    @Override
    public void setUpRecyclerView(ArrayList<DetailedInfo> appUsageItems) {
        // Create adapter passing in the sample user data
        DetailAdapter adapter = new DetailAdapter(context, appUsageItems, type);
        // Attach the adapter to the recyclerview to populate items
        detailedRV.setAdapter(adapter);
        // Set layout manager to position the items
        detailedRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setScreenTitle(String title) {
        titleTV.setText(title);
    }
}
