package com.example.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SelectPopUpActivity extends Activity {
    Button record;
    Button check;
    String token, user_id, script_id, script_text;
    int numOfVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.selectpopup_activity);

        Intent intent = getIntent();
        if(intent!=null){
            token = intent.getStringExtra("token");
            user_id = intent.getStringExtra("user_id");
            script_id = intent.getStringExtra("script_id");
            script_text = intent.getStringExtra("script_text");
            numOfVideos = intent.getIntExtra("number_of_videos", -1);
        }

        record=(Button)findViewById(R.id.record);
        check=(Button)findViewById(R.id.check);

        record.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),VideoRecordActivity.class);

                intent.putExtra("token", token);
                intent.putExtra("user_id", user_id);
                intent.putExtra("script_id", script_id);
                intent.putExtra("script_text", script_text);
                intent.putExtra("number_of_videos", numOfVideos);

                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),VideoCheckActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("user_id", user_id);
                intent.putExtra("script_id", script_id);
                intent.putExtra("number_of_videos", numOfVideos);
                startActivity(intent);
            }
        });

    }
}
