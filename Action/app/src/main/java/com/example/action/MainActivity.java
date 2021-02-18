package com.example.action;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends Activity {
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
                String id_text=id.getText().toString().trim();
                String pw_text=pw.getText().toString().trim();

                if(id_text.length()==0||id_text==null||pw_text.length()==0||pw_text==null){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("로그인 오류")
                            .setMessage("아이디 또는 비밀번호를 입력해주세요.")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                            /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);*/
                                }
                            })
                            .show(); // 팝업창 보여줌
                }
                else{
//                    firebaseAuth.signInWithEmailAndPassword(id_text,pw_text).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>(){
//                        public void onComplete(@NonNull Task<AuthResult> task){
//                            if(task.isSuccessful()){
//                                Intent intent= new Intent(getApplicationContext(), BottomBarActivity.class);
//                                startActivity(intent);
//                            }
//                            else{
//                                new AlertDialog.Builder(MainActivity.this)
//                                        .setTitle("로그인 오류")
//                                        .setMessage("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")
//                                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dlg, int sumthin) {
//                                            /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                                            startActivity(intent);*/
//                                            }
//                                        })
//                                        .show(); // 팝업창 보여줌
//                            }
//                        }
//                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), RegisterActivity.class);
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