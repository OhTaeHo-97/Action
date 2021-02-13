package com.example.action;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class StarRatingActivity extends Activity {
    RatingBar rating;
    TextView star_comment;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.star_rating_activity);

        star_comment=(TextView)findViewById(R.id.star_comment);
        finish=(Button)findViewById(R.id.finish);
        rating=(RatingBar)findViewById(R.id.rating);

        finish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                /*Intent intent=new Intent(getApplicationContext(),BottomBarActivity.class);
                startActivity(intent);*/
                finish();
            }
        });

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            public void onRatingChanged(RatingBar ratingBar,float rating, boolean fromUser){
                if(rating==1){
                    star_comment.setText("감정 표현이 많이 서툴러요! 분발하셔야겠어요!");
                }
                else if(rating==2){
                    star_comment.setText("감정 표현이 서투네요!");
                }
                else if(rating==3){
                    star_comment.setText("감정 표현이 좋네요!");
                }
                else if(rating==4){
                    star_comment.setText("감정 표현이 훌륭해요!");
                }
                else if(rating==5){
                    star_comment.setText("당신은 연기천재?!");
                }
            }
        });
    }
}