package com.manifest.fomo.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import com.manifest.fomo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DevelopersActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cardviewKevin)
    CardView cardviewKevin;
    @BindView(R.id.cardviewSemil)
    CardView cardviewSemil;
    @BindView(R.id.cardviewPushkar)
    CardView cardviewPushkar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        ButterKnife.bind(this);


        // Set up the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //LinkedIn Handle
        cardviewKevin.setOnClickListener(v -> {
            String facebookUrl = "https://www.linkedin.com/in/kevin-jain-415536139/";
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        });

        cardviewSemil.setOnClickListener(v -> {
            String facebookUrl = "https://www.linkedin.com/in/semil-jain-997ab9163/";
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        });

        cardviewPushkar.setOnClickListener(v -> {
            String facebookUrl = "https://www.linkedin.com/in/pshkrh";
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        });
    }
}
