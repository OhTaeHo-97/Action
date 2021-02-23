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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.selectpopup_activity);

        record=(Button)findViewById(R.id.record);
        check=(Button)findViewById(R.id.check);

        record.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),VideoRecordActivity.class);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                /*Intent intent=new Intent(getApplicationContext(),BottomBarActivity.class);
                startActivity(intent);*/
            }
        });

    }
}
