package com.manifest.fomo.DetailedTypes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manifest.fomo.Overview.OverviewEnum;
import com.manifest.fomo.R;
import com.manifest.fomo.Utils.Utils;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DetailedInfo> appUsageItemArrayList;
    private int type;

    DetailAdapter(Context context, ArrayList<DetailedInfo> appUsageItemArrayList, int type) {
        this.appUsageItemArrayList = appUsageItemArrayList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.detail_activity_card, parent, false);

        // Return a new holder instance
        return new DetailAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder holder, int position) {

        // Get the data model based on position
        DetailedInfo item = appUsageItemArrayList.get(position);

        // Set item views based on your views and data model
        TextView nameTV = holder.nameTV;
        TextView durationTV = holder.durationTV;
        ImageView imageView = holder.imageView;
        TextView launchCount = holder.launchCount;

        nameTV.setText(item.getAppName());
        if (type == OverviewEnum.MOST_CONTACTED_PERSON.getType()) {
            durationTV.setText(Long.toString(item.getTimeInForeground()));
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_contacts));
        } else if (type == OverviewEnum.MOST_USED_APP.getType()) {
            durationTV.setText(Utils.fetchFormattedTime(item.getTimeInForeground()));
            imageView.setImageDrawable(item.getAppIcon());
            launchCount.setVisibility(View.VISIBLE);
            launchCount.setText(String.format(context.getResources().getString(R.string.launch_count), item.getLaunchCount()));
        } else if (type == OverviewEnum.MOST_CALLED_PERSON.getType()) {
            durationTV.setText(Utils.fetchFormattedTime(item.getTimeInForeground()));
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_calls));
        }
    }

    @Override
    public int getItemCount() {
        return appUsageItemArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView nameTV;
        private TextView durationTV;
        private ImageView imageView;
        private TextView launchCount;

        private ViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.name);
            durationTV = itemView.findViewById(R.id.duration);
            imageView = itemView.findViewById(R.id.image);
            launchCount = itemView.findViewById(R.id.launchCount);
        }
    }
}
