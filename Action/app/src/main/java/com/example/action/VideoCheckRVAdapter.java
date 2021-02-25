package com.example.action;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoCheckRVAdapter extends RecyclerView.Adapter<VideoCheckRVAdapter.ItemViewHolder> {
    private ArrayList<VideoCheckItem> itemList = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_check_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    void addItem(VideoCheckItem items) {
        itemList.add(items);
    }

    // Set subviews
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView videoThumbnailIV;
        private TextView createTimeTV;

        ItemViewHolder(View itemView) {
            super(itemView);

            videoThumbnailIV = itemView.findViewById(R.id.video_thumbnail);
            createTimeTV = itemView.findViewById(R.id.create_time_tv);
        }

        void onBind(VideoCheckItem items) {

            videoThumbnailIV.setImageResource(items.getVideoID());
            createTimeTV.setText(items.getCreateTime());

        }
    }

}
