package com.example.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class WithPopUpActivity extends Activity {
    Button complete;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.withpopup_activity);

        complete=(Button)findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                startActivity(intent);
            }
        });
    }
}
