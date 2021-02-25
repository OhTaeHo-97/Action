package com.example.action;

public class VideoCheckItem {
    private String createTime;
    private int videoId;

    public int getVideoID(){
        return videoId;
    }

    public String getCreateTime(){
        return createTime;
    }

    public void setVideoID(int videoId){
        this.videoId = videoId;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
}
