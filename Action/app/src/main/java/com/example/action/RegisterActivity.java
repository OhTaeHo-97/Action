package com.example.action;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    public boolean responseResult;

    Button close;
    CheckBox check1;
    EditText ID_text;
    Spinner spinner;
    EditText phone;
    EditText PW;
    EditText PW_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ID_text=(EditText)findViewById(R.id.ID_text);
        spinner=(Spinner)findViewById(R.id.spinner);
        phone=(EditText)findViewById(R.id.phone);
        PW=(EditText)findViewById(R.id.PW);
        PW_confirm=(EditText)findViewById(R.id.PW_confirm);
        check1=(CheckBox)findViewById(R.id.check1);
        close=(Button)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.reg).setOnClickListener(mClickListener);

    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reg:
                    if(check1.isChecked()){
                        String id_text = ID_text.getText().toString().trim();
                        String domain = spinner.getSelectedItem().toString().trim();
                        String id = id_text+"@"+domain;
                        String phone_num = phone.getText().toString().trim();
                        String password = PW.getText().toString();
                        String password_confirm = PW_confirm.getText().toString();

                        if(password.equals(password_confirm)){

                            Thread thd = new Thread(new Runnable() { @Override public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient().newBuilder().build();
                                    MediaType mediaType = MediaType.parse("application/json");
                                    RequestBody body = RequestBody.create(mediaType,
                                            "{\n    \"name\": \"test\",\n " +
                                                    "   \"email\": \"" + id + "\",\n  " +
                                                    "  \"phoneNumber\": \"" + phone_num + "\",\n  " +
                                                    "  \"password\": \"" + password + "\"\n}");
                                    Request request = new Request.Builder()
                                            .url("https://gateway.viewinter.ai/api/users")
                                            .method("POST", body)
                                            .addHeader("Content-Type", "application/json")
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

                            // If 200 OK,
                            if(responseResult){
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setTitle("회원가입 완료")
                                        .setMessage("회원가입이 완료되었습니다!")
                                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                            // Bad request
                            else{
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setTitle("회원가입 중복")
                                        .setMessage("이미 가입한 회원입니다.")
                                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {
                                            /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);*/
                                            }
                                        })
                                        .show(); // 팝업창 보여줌
                            }

                        }

                        else{
                            //databaseReference.child("message").push().setValue("2");
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("비밀번호 오류")
                                    .setMessage("비밀번호가 일치하지 않습니다.")
                                    .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dlg, int sumthin) {
                                            /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);*/
                                        }
                                    })
                                    .show(); // 팝업창 보여줌
                        }
                    }
                    else{
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("약관동의")
                                .setMessage("약관 동의가 필요합니다.")
                                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {

                                    }
                                })
                                .show(); // 팝업창 보여줌

                    }
                    break;
            }
        }
    };
}