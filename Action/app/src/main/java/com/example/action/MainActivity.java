package com.example.action;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText id;
    EditText pw;
    Button login;
    Button find;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id=(EditText)findViewById(R.id.ID);
        pw=(EditText)findViewById(R.id.PW);
        login=(Button)findViewById(R.id.log_in);
        register=(Button)findViewById(R.id.register);
        find=(Button)findViewById(R.id.find);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //String id_text=id.getText().toString();
                //String pw_text=id.getText().toString();
                Intent intent= new Intent(getApplicationContext(), MyInfoActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        find.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent= new Intent(getApplicationContext(), FindPWActivity.class);
                startActivity(intent);
            }
        });

    }
}