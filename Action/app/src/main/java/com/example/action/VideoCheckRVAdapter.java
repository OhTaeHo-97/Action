package com.example.action;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoCheckRVAdapter extends RecyclerView.Adapter<VideoCheckRVAdapter.ItemViewHolder> {
    private ArrayList<VideoCheckItem> itemList = new ArrayList<>();

    private Context context;


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

        private ImageButton videoBtn;
        private TextView createTimeTV;
        private TextView emotionTV;
        private String videoPath;

        ItemViewHolder(View itemView) {
            super(itemView);

            videoBtn = itemView.findViewById(R.id.play_video_btn);
            createTimeTV = itemView.findViewById(R.id.create_time_tv);
            emotionTV = itemView.findViewById(R.id.emotion_list_view);
        }

        void onBind(VideoCheckItem items) {

            videoPath = items.getVideoPath();
            createTimeTV.setText("녹화날짜: " + items.getCreateTime());
            emotionTV.setText("평범:" + items.getEmotionNeutral() + "%" +
                    "  기쁨:"+ items.getEmotionJoy() + "%" +
                    "  슬픔:" + items.getEmotionSadness() + "%" +
                    " \n혐오:" + items.getEmotionDisgust() + "%" +
                    "  공포:" + items.getEmotionFear() + "%" +
                    "  화남:" + items.getEmotionAnger() + "%" +
                    "  놀람:" + items.getEmotionSurprise() + "%");

            videoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),PlayVideoActivity.class);
                    intent.putExtra("video_path", videoPath);
                    v.getContext().startActivity(intent);

                }
            });

        }
    }

}
