package com.example.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StarRatingActivity extends Activity {
    RatingBar rating;
    TextView star_comment;
    Button finish;
    boolean responseResult;
    String token;
    int num_star=0;
    int file_id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.star_rating_activity);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        file_id = intent.getIntExtra("file_id",-1);

        star_comment=(TextView)findViewById(R.id.star_comment);
        finish=(Button)findViewById(R.id.finish);
        rating=(RatingBar)findViewById(R.id.rating);

        finish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Thread thd = new Thread(new Runnable() { @Override public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create(mediaType, "{\n    \"evaluation\": "+num_star+"\n}");
                        Request request = new Request.Builder()
                                .url("https://gateway.viewinter.ai/api/videos/"+file_id)
                                .method("PATCH", body)
                                .addHeader("Authorization", "Bearer "+token)
                                .build();
                        Response response = client.newCall(request).execute();

                        responseResult = response.isSuccessful();
                    } catch (IOException e) {
                        Log.e("Response Error", "Response Error");
                    }
                } });

                try {
                    thd.start();

                    // Wait Post process
                    thd.join();
                } catch(InterruptedException e){
                    Log.e("thread join Error","thread join Error");
                }
                /*Intent intent=new Intent(getApplicationContext(),BottomBarActivity.class);
                startActivity(intent);*/
                finish();
            }
        });

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            public void onRatingChanged(RatingBar ratingBar,float rating, boolean fromUser){
                if(rating==1){
                    star_comment.setText("감정 표현이 많이 서툴러요! 분발하셔야겠어요!");
                    num_star=1;
                }
                else if(rating==2){
                    star_comment.setText("감정 표현이 서투네요!");
                    num_star=2;
                }
                else if(rating==3){
                    star_comment.setText("감정 표현이 좋네요!");
                    num_star=3;
                }
                else if(rating==4){
                    star_comment.setText("감정 표현이 훌륭해요!");
                    num_star=4;
                }
                else if(rating==5){
                    star_comment.setText("당신은 연기천재?!");
                    num_star=5;
                }
            }
        });
    }
}