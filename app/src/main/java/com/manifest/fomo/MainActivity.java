package com.manifest.fomo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.manifest.fomo.DetailedTypes.DetailActivity;
import com.manifest.fomo.DetailedTypes.HelpActivity;
import com.manifest.fomo.Navigation.AboutUsActivity;
import com.manifest.fomo.Navigation.DevelopersActivity;
import com.manifest.fomo.Overview.OverviewAdapter;
import com.manifest.fomo.Overview.OverviewContract;
import com.manifest.fomo.Overview.OverviewItem;
import com.manifest.fomo.Overview.OverviewPresenter;
import com.manifest.fomo.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OverviewContract.View {

    @BindView(R.id.todaysPhoneUsage)
    TextView todaysPhoneUsage;
    @BindView(R.id.todaysScore)
    TextView todaysScore;
    @BindView(R.id.todaysPhoneUsageCard)
    CardView todaysPhoneUsageCard;
    @BindView(R.id.helpIV)
    ImageView helpIV;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.facebook)
    ImageView facebook;
    @BindView(R.id.roundCornerProgress)
    RoundCornerProgressBar roundCornerProgress;

    private Context context;

    public static final String ITEM_TYPE = "item_type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        OverviewPresenter mPresenter = new OverviewPresenter(context);
        mPresenter.bindView(this);
        mPresenter.init();

        // Set up the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Setup the Drawer Layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        todaysPhoneUsageCard.setOnClickListener(view -> {
            mPresenter.openAppUsageActivity();
        });
        helpIV.setOnClickListener(view -> {
            Intent e1 = new Intent(context, HelpActivity.class);
            e1.putExtra(MainActivity.ITEM_TYPE, 5);
            startActivity(e1);
        });

        //Facebook Handle
        facebook.setOnClickListener(v -> {
            String facebookUrl = Utils.getFacebookPageURL(context.getPackageManager());

            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        });
    }

    @Override
    public void animateCards() {
        Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
        LinearLayout ll = findViewById(R.id.main_screen_linear_layout);
        ll.setAnimation(bottomUp);
        ll.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpOverviewAdapter(ArrayList<OverviewItem> overviewItems) {
        RecyclerView rvOverview = findViewById(R.id.overview_recycler);

        // Create adapter passing in the sample user data
        OverviewAdapter adapter = new OverviewAdapter(overviewItems, context);
        // Attach the adapter to the recyclerview to populate items
        rvOverview.setAdapter(adapter);
        // Set layout manager to position the items
        rvOverview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void setTodaysPhoneUsageScore(String text) {
        todaysPhoneUsage.setText(text);
    }

    @Override
    public void setTodaysScore(String score) {
        todaysScore.setText(score);
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
    public void showTodaysScore() {
        todaysScore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTodaysScore() {
        todaysScore.setVisibility(View.GONE);
    }

    @Override
    public void openAppUsageActivity() {
        Intent e1 = new Intent(context, DetailActivity.class);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((Activity) context, todaysPhoneUsageCard, getResources().getString(R.string.transition));
        startActivity(e1, options.toBundle());
    }

    @Override
    public void setProgressColor(int color) {
        roundCornerProgress.setProgressColor(color);
    }

    @Override
    public void setProgressValue(float number) {
        roundCornerProgress.setProgress(number);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            startActivity(new Intent(context, AboutUsActivity.class));
        }
        //else if (id == R.id.nav_developers) {
        //    startActivity(new Intent(context, DevelopersActivity.class));
        //}
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}