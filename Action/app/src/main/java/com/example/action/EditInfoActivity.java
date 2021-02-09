package com.example.action;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EditInfoActivity extends AppCompatActivity {
    Button close;
    Button edit_info;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editinfo_activity);

        close=(Button)findViewById(R.id.close);
        edit_info=(Button)findViewById(R.id.edit_info);

        edit_info.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                startActivity(intent);
            }
        });
    }
}
