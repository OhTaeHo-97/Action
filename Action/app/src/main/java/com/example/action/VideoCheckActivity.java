package com.example.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoCheckActivity extends AppCompatActivity {

    private VideoCheckRVAdapter adapter;
    private boolean responseResult = false;

    String token, script_id, user_id;
    int numOfVideos;

    ImageButton backBtn;

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_check);

        Intent intent = getIntent();
        if(intent!=null){
            user_id = intent.getStringExtra("user_id");
            token = intent.getStringExtra("token");
            script_id = intent.getStringExtra("script_id");
            numOfVideos = intent.getIntExtra("number_of_videos", -1);
        }

        // Set RecyclerView
        RecyclerView recyclerView = findViewById(R.id.video_check_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new VideoCheckRVAdapter();
        recyclerView.setAdapter(adapter);

        GETData();


        // Back Button
        backBtn = (ImageButton)findViewById(R.id.video_check_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    private void getRVItems(String[] createTime, Bitmap[] videoBitmap, Double[][] emotionList) {

        VideoCheckItem items = new VideoCheckItem();

        Log.e("Length", String.valueOf(videoBitmap.length));

        for(int i = 0; i < createTime.length; i++)
        {
            items.setCreateTime(createTime[i]);

            items.setEmotionNeutral(emotionList[i][0]);
            items.setEmotionJoy(emotionList[i][1]);
            items.setEmotionSadness(emotionList[i][2]);
            items.setEmotionDisgust(emotionList[i][3]);
            items.setEmotionFear(emotionList[i][4]);
            items.setEmotionAnger(emotionList[i][5]);
            items.setEmotionSurprise(emotionList[i][6]);
        }

        for(int i = 0; i < numOfVideos; i++)
        {
            items.setVideoBitmap(videoBitmap[i]);
        }

        adapter.addItem(items);
        adapter.notifyDataSetChanged();
    }


    // Get video data info
    private void GETData(){
        Thread thd = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("https://gateway.viewinter.ai/api/videos/query/paging?" +
                                    "search=scriptId:" + script_id + "&size=1000&page=0&sort=createTime,desc")
                            .method("GET", null)
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    Response response = client.newCall(request).execute();

                    responseResult = response.isSuccessful();

                    String resBody = response.body().string();

                    Log.e("Body", resBody);
                    JSONObject jsonObject = new JSONObject(resBody);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("_embedded");

                    try{
                        jsonArray = jsonObject1.optJSONArray("videos");
                    }
                    catch (Exception e){
                        responseResult = false;
                        this.finalize();
                    }


                    // If no video data -> finish thread
                    List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();

                    for(int i = 0; i < jsonArray.length(); i++)
                        jsonObjectList.add(jsonArray.getJSONObject(i));

                    JSONObject[] json_list=new JSONObject[jsonObjectList.size()];
                    jsonObjectList.toArray(json_list);

                    //String[] videoIDList = new String[jsonObjectList.size()];
                    String[] createTimeList = new String[jsonObjectList.size()];
                    Double[][] emotionList = new Double[jsonObjectList.size()][7];

                    for(int i = 0; i < json_list.length; i++) {
                        //videoIDList[i] = json_list[i].getString("id");
                        createTimeList[i] = json_list[i].getString("createTime");
                        emotionList[i][0] = json_list[i].getDouble("emotionNeutral");
                        emotionList[i][1] = json_list[i].getDouble("emotionJoy");
                        emotionList[i][2] = json_list[i].getDouble("emotionSadness");
                        emotionList[i][3] = json_list[i].getDouble("emotionDisgust");
                        emotionList[i][4] = json_list[i].getDouble("emotionFear");
                        emotionList[i][5] = json_list[i].getDouble("emotionAnger");
                        emotionList[i][6] = json_list[i].getDouble("emotionSurprise");
                    }

                    // Thumbnail
                    Bitmap[] videoBitmapList = new Bitmap[numOfVideos];
                    for(int i = 0; i < numOfVideos; i++){
                        int num = i + 1;
                        String videoName = user_id + script_id + num + "_video";
                        String videoPath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/"+ videoName +".mp4";

                        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MINI_KIND);

                        videoBitmapList[i] = bitmap;
                    }

                    getRVItems(createTimeList, videoBitmapList, emotionList);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Log.e("Video Info GET Error", "Error");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        try {
            thd.start();

            // Wait GET process
            thd.join();

            // Fail GET Method
            if(!responseResult) {
                new AlertDialog.Builder(this)
                        .setTitle("실패")
                        .setMessage("영상 가져오기 실패!\n녹화영상이 없습니다. ")
                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        })
                        .show();
            }

        } catch (InterruptedException e) {
            Log.e("thread join Error", "thread join Error");
        }
    }


//    private void GETVideo(String videoID) {
//        Thread thd = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient().newBuilder()
//                            .build();
//                    Request request = new Request.Builder()
//                            .url("https://gateway.viewinter.ai/api/videos/" + videoID + "/stream?token=" + token)
//                            .method("GET", null)
//                            .build();
//                    Response response = client.newCall(request).execute();
//
//                    InputStream is = response.body().byteStream();
//
//                    Log.e("VideoData", response.header("Content-Length"));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("Video GET Error", "Error");
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//        });
//
//        try {
//            thd.start();
//            // Wait GET process
//            thd.join();
//        } catch (InterruptedException e) {
//            Log.e("thread join Error", "thread join Error");
//        }
//
//    }
}