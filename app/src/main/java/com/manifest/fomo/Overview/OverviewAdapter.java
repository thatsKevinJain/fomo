package com.manifest.fomo.Overview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manifest.fomo.DetailedTypes.DetailActivity;
import com.manifest.fomo.MainActivity;
import com.manifest.fomo.R;

import java.util.ArrayList;

/**
 * Created by Kevin on 09/03/18.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<OverviewItem> overviewItemList;
    private Context context;

    public OverviewAdapter(ArrayList<OverviewItem> overviewItemList, Context context) {
        this.overviewItemList = overviewItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.overview_card_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        OverviewItem item = overviewItemList.get(position);

        // Set item views based on your views and data model
        TextView nameTV = holder.nameTV;
        TextView titleTV = holder.titleTV;
        CardView cardView = holder.cardView;
        ImageView imageIV = holder.imageIV;

        nameTV.setText(item.getName());
        titleTV.setText(item.getTitle());
        cardView.setCardBackgroundColor(item.getColor());
        cardView.setOnClickListener(view -> {
            Intent e1 = new Intent(context, DetailActivity.class);
            e1.putExtra(MainActivity.ITEM_TYPE, item.getType());
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, cardView, context.getResources().getString(R.string.transition));
            context.startActivity(e1, options.toBundle());
        });

        if (item.getType() == OverviewEnum.MOST_CONTACTED_PERSON.getType()) {
            imageIV.setImageResource(R.drawable.ic_contacts);
        }

        if (item.getType() == OverviewEnum.MOST_CALLED_PERSON.getType()) {
            imageIV.setImageResource(R.drawable.ic_calls);
        }

        if (item.getType() == OverviewEnum.MOST_USED_APP.getType()) {
            imageIV.setImageResource(R.drawable.ic_apps);
        }
    }

    @Override
    public int getItemCount() {
        return overviewItemList.size();
    }

    @Override
    public void onClick(View view) {

    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView nameTV;
        private TextView titleTV;
        private CardView cardView;
        private ImageView imageIV;

        private ViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.name);
            titleTV = itemView.findViewById(R.id.title);
            imageIV = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.overview_cardview);
        }
    }
}
