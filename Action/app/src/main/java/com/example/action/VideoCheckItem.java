package com.example.action;

import android.graphics.Bitmap;

public class VideoCheckItem {
    private String createTime;
    private Bitmap videoBitmap;
    private long emotionNeutral, emotionJoy, emotionSadness, emotionDisgust, emotionFear, emotionAnger, emotionSurprise;

    public Bitmap getVideoBitmap(){
        return videoBitmap;
    }

    public String getCreateTime(){
        return createTime;
    }

    public long getEmotionNeutral() {return emotionNeutral;}
    public long getEmotionJoy() {return emotionJoy;}
    public long getEmotionSadness() {return emotionSadness;}
    public long getEmotionDisgust() {return emotionDisgust;}
    public long getEmotionFear() {return emotionFear;}
    public long getEmotionAnger() {return emotionAnger;}
    public long getEmotionSurprise() {return emotionSurprise;}

    public void setVideoBitmap(Bitmap videoId){
        this.videoBitmap = videoBitmap;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public void setEmotionNeutral(Double emotionNeutral) {this.emotionNeutral = Math.round(emotionNeutral * 100);}
    public void setEmotionJoy(Double emotionJoy) {this.emotionJoy = Math.round(emotionJoy * 100);}
    public void setEmotionSadness(Double emotionSadness) {this.emotionSadness = Math.round(emotionSadness * 100);}
    public void setEmotionDisgust(Double emotionDisgust) {this.emotionDisgust = Math.round(emotionDisgust * 100);}
    public void setEmotionFear(Double emotionFear) {this.emotionFear = Math.round(emotionFear * 100);}
    public void setEmotionAnger(Double emotionAnger) {this.emotionAnger = Math.round(emotionAnger * 100);}
    public void setEmotionSurprise(Double emotionSurprise) {this.emotionSurprise = Math.round(emotionSurprise * 100);}
}
