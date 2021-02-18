package com.example.action;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FindPWActivity extends AppCompatActivity {
    public boolean responseResult;
    Button close;
    //Button complete;
    EditText ID_text;
    EditText phone;
    Spinner spinner;

    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw_activity);

        ID_text=(EditText)findViewById(R.id.ID_text);
        phone=(EditText)findViewById(R.id.phone);
        spinner=(Spinner)findViewById(R.id.spinner);
        progressDialog=new ProgressDialog(this);

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
                    String phone_num=phone.getText().toString().trim();

                    Thread thd2 = new Thread(new Runnable() { @Override public void run() {
                        try{
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType,
                                    "{\n    \"email\": \"" + email + "\",\n  " +
                                            "  \"phoneNumber\": \"" + phone_num + "\"\n}");
                            Request request = new Request.Builder()
                                    .url("https://gateway.viewinter.ai/api/users/find-password")
                                    .method("GET", null)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String result = response.body().string();
                            Log.e("Result",result);

                            /*OkHttpClient client = new OkHttpClient().newBuilder().build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = RequestBody.create(mediaType, "");
                            Request request = new Request.Builder()
                                    .url("https://gateway.viewinter.ai/" +
                                            "oauth/token?grant_type=password&client_id=ui&" +
                                            "client_secret=d48ujflverdj348orj48dhwu43juw9u4fjklsdljfu9e409w0w94e8jidew89dl2&" +
                                            "username=" + id_text + "&password=" + pw_text)
                                    .method("POST", body)
                                    .build();
                            Response response = client.newCall(request).execute();*/

                            responseResult = response.isSuccessful();

                        }catch (IOException e) {
                            Log.e("Login Response Error", "Response Error");
                        }
                    } });

                    /*firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>(){
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
                                            startActivity(intent);
                                            }
                                        })
                                        .show(); // 팝업창 보여줌
                            }
                        }
                    });*/

                    break;
            }
        }
    };
}
