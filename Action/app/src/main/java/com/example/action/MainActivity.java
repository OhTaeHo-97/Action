package com.example.action;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends Activity {
    public boolean responseResult;

    EditText id;
    EditText pw;
    Button login;
    Button find;
    Button register;
    ArrayList<String> pstr=new ArrayList<String>();
    String[] arr;
    String token;
    String user_id;

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
                    // Post id & pw
                     Thread thd1 = new Thread(new Runnable() { @Override public void run() {
                        try{
                            OkHttpClient client = new OkHttpClient().newBuilder().build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = RequestBody.create(mediaType, "");
                            Request request = new Request.Builder()
                                    .url("https://gateway.viewinter.ai/" +
                                            "oauth/token?grant_type=password&client_id=ui&" +
                                            "client_secret=d48ujflverdj348orj48dhwu43juw9u4fjklsdljfu9e409w0w94e8jidew89dl2&" +
                                            "username=" + id_text + "&password=" + pw_text)
                                    .method("POST", body)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String result = response.body().string();

                            int length=result.length();
                            String output=result.substring(1,length-1);

                            StringTokenizer st=new StringTokenizer(output,",:");
                            //ArrayList<String> pstr=new ArrayList<String>();
                            while(st.hasMoreTokens()){
                                pstr.add(st.nextToken());
                            }

                            arr=new String[pstr.size()];
                            arr=pstr.toArray(arr);

                            for(int i=0;i<arr.length;i++){
                                if(arr[i].equals("\"access_token\"")){
                                    token=arr[i+1];
                                }
                                else if(arr[i].equals("\"id\"")){
                                    user_id = arr[i+1];
                                }
                            }

                            if(token!=null){
                                length=token.length();
                                token=token.substring(1,length-1);
                            }

                            responseResult = response.isSuccessful();

                        }catch (IOException e) {
                            Log.e("Login Response Error", "Response Error");
                        }
                    } });


                    try {
                        thd1.start();
                        // Wait Post process
                        thd1.join();
                    } catch (InterruptedException e) {
                        Log.e("thread join Error", "thread join Error");
                    }

                    // If 200 OK,
                    if(responseResult){
                        Intent intent= new Intent(getApplicationContext(), BottomBarActivity.class);
                        intent.putExtra("token", token);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("email",id_text);
                        startActivity(intent);
                    }
                    // Bad request
                    else{
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("로그인 오류")
                                .setMessage("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")
                                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                            /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);*/
                                    }
                                })
                                .show(); // 팝업창 보여줌
                    }

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
