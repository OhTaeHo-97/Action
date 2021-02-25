package com.example.action;

import android.graphics.Bitmap;

public class VideoCheckItem {
    private String createTime;
    private Bitmap videoBitmap;

    public Bitmap getVideoBitmap(){
        return videoBitmap;
    }

    public String getCreateTime(){
        return createTime;
    }

    public void setVideoBitmap(Bitmap videoId){
        this.videoBitmap = videoBitmap;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
}
