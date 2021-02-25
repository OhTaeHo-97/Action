package com.example.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
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

    String token, script_id;

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_check);

        Intent intent = getIntent();
        if(intent!=null){
            token = intent.getStringExtra("token");
            script_id = intent.getStringExtra("script_id");
        }

        // Set RecyclerView
        RecyclerView recyclerView = findViewById(R.id.video_check_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new VideoCheckRVAdapter();
        recyclerView.setAdapter(adapter);

        GETData();

        getRVItems();

        // Back Button
        backBtn = (ImageButton)findViewById(R.id.video_check_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    private void getRVItems() {

        VideoCheckItem items = new VideoCheckItem();

//        for(int i = 0; i < createTime.length; i++)
//        {
//            items.setCreateTime(createTime[i]);
//            items.setVideoID(R.drawable.exo_controls_next);
//        }
        items.setCreateTime("asdasd");
        items.setVideoID(R.drawable.exo_controls_next);

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
                            .addHeader("Authorization", "Bearer " +token)
                            .build();
                    Response response = client.newCall(request).execute();

                    responseResult = response.isSuccessful();

                    String resBody = response.body().string();

                    JSONObject jsonObject = new JSONObject(resBody);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("_embedded");
                    JSONArray jsonArray = jsonObject1.optJSONArray("videos");

                    // If no video data -> finish thread
                    if(jsonArray == null) {
                        responseResult = false;
                        this.finalize();
                    }

                    List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();

                    for(int i = 0; i < jsonArray.length(); i++)
                        jsonObjectList.add(jsonArray.getJSONObject(i));

                    JSONObject[] json_list=new JSONObject[jsonObjectList.size()];
                    jsonObjectList.toArray(json_list);

                    String[] videoIDList = new String[jsonObjectList.size()];
                    String[] createTimeList = new String[jsonObjectList.size()];

                    for(int i = 0; i < json_list.length; i++) {
                        videoIDList[i] = json_list[i].getString("id");
                        createTimeList[i] = json_list[i].getString("createTime");
                    }

                    GETVideo(videoIDList[0]);


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
                        .setMessage("영상 가져오기 실패!")
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


    private void GETVideo(String videoID) {
        Thread thd = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("https://gateway.viewinter.ai/api/videos/" + videoID + "/stream?token=" + token)
                            .method("GET", null)
                            .build();
                    Response response = client.newCall(request).execute();

                    InputStream is = response.body().byteStream();

                    Log.e("VideoData", response.header("Content-Length"));

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Video GET Error", "Error");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        try {
            thd.start();
            // Wait GET process
            thd.join();
        } catch (InterruptedException e) {
            Log.e("thread join Error", "thread join Error");
        }

    }
}