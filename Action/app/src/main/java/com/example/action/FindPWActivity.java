package com.example.action;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FindPWActivity extends AppCompatActivity {
    Button close;
    //Button complete;
    EditText ID_text;
    Spinner spinner;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw_activity);

        ID_text=(EditText)findViewById(R.id.ID_text);
        spinner=(Spinner)findViewById(R.id.spinner);
        progressDialog=new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        //complete=(Button)findViewById(R.id.complete);
        close=(Button)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.complete).setOnClickListener(mClickListener);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.complete:
                    progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
                    progressDialog.show();

                    String id=ID_text.getText().toString().trim();
                    String domain=spinner.getSelectedItem().toString().trim();
                    String email=id+"@"+domain;

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>(){
                        public void onComplete(@NonNull Task<Void> task){
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                new AlertDialog.Builder(FindPWActivity.this)
                                        .setTitle("비밀번호 찾기 완료")
                                        .setMessage("이메일을 보냈습니다.")
                                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show(); // 팝업창 보여줌
                            }

                            else{
                                progressDialog.dismiss();
                                new AlertDialog.Builder(FindPWActivity.this)
                                        .setTitle("비밀번호 찾기 오류")
                                        .setMessage("메일 보내기에 실패하였습니다. 잠시 후에 다시 시도해주세요.")
                                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {
                                            /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);*/
                                            }
                                        })
                                        .show(); // 팝업창 보여줌
                            }
                        }
                    });

                    break;
            }
        }
    };
}
