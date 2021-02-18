package com.example.action;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//implements MyInfoFragment.CommunicateInterface

public class EditInfoActivity extends AppCompatActivity {
    Button close;
    Button edit_info;
    Button info;
    EditText phone;
    EditText original_pw;
    EditText new_pw;
    EditText confirm_pw;
    String email;
    String token;
    ArrayList<String> pstr = new ArrayList<String>();
    String[] arr;
    String phone_num;
    int id;
    boolean responseResult;

    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editinfo_activity);

        close = (Button) findViewById(R.id.close);
        edit_info = (Button) findViewById(R.id.edit_info);
        info = (Button) findViewById(R.id.info);
        phone = (EditText) findViewById(R.id.phone);
        original_pw = (EditText) findViewById(R.id.original_pw);
        new_pw = (EditText) findViewById(R.id.new_pw);
        confirm_pw = (EditText) findViewById(R.id.confirm_pw);
        progressDialog = new ProgressDialog(this);

        //String email="";
        //String uid="";

        progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            email=user.getEmail();
            uid=user.getUid();
        }

        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference("Users/"+uid+"/phone");
        mReference.addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot dataSnapshot){
                String phone_num=dataSnapshot.getValue().toString();
                phone.setText(phone_num);
            }

            public void onCancelled(DatabaseError databaseError){

            }
        });*/

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        email = intent.getStringExtra("email");

        Thread thd2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("https://gateway.viewinter.ai/api/users/me")
                            .method("GET", null)
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    Response response = client.newCall(request).execute();

                    String result = response.body().string();
                    Log.e("Result", result);

                    int length = result.length();
                    String output = result.substring(1, length - 1);

                    StringTokenizer st = new StringTokenizer(output, ",:");
                    //ArrayList<String> pstr=new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        pstr.add(st.nextToken());
                    }

                    arr = new String[pstr.size()];
                    arr = pstr.toArray(arr);

                    for (int i = 0; i < arr.length; i++) {
                        if(arr[i].equals("\"id\"")){
                            id = Integer.parseInt(arr[i+1]);
                        }
                        if (arr[i].equals("\"phoneNumber\"")) {
                            phone_num = arr[i + 1];
                            break;
                        }
                    }

                    length = phone_num.length();
                    phone_num = phone_num.substring(1, length - 1);

                    //Log.e("Result",phone_num);

                    responseResult = response.isSuccessful();

                } catch (IOException e) {
                    Log.e("Login Response Error", "Response Error");
                }
            }
        });

        try {
            thd2.start();
            // Wait Post process
            thd2.join();
        } catch (InterruptedException e) {
            Log.e("thread join Error", "thread join Error");
        }

        phone.setText(phone_num);
        info.setText(email);
        progressDialog.dismiss();

        //final String uid_final=uid;
        edit_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String phone_number = phone.getText().toString().trim();
                String prev_password = original_pw.getText().toString().trim();
                String new_password = new_pw.getText().toString().trim();
                String confirm_password = confirm_pw.getText().toString().trim();

                if(new_password.length()==0&&confirm_password.length()==0){

                    Thread thd3 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient().newBuilder()
                                        .build();
                                MediaType mediaType = MediaType.parse("application/json");
                                RequestBody body = RequestBody.create(mediaType, "{\n    \"phoneNumber\": \""+phone_number+"\",\n    " +
                                        "\"prevPassword\": \""+prev_password+"\",\n    " +
                                        "\"password\": \""+prev_password+"\"\n}");
                                Request request = new Request.Builder()
                                        .url("https://gateway.viewinter.ai/api/users/"+id+"/modify")
                                        .method("PATCH", body)
                                        .addHeader("Authorization", "Bearer "+token)
                                        .addHeader("Content-Type", "application/json")
                                        .build();
                                Response response = client.newCall(request).execute();

                                responseResult = response.isSuccessful();

                            } catch (IOException e) {
                                Log.e("Login Response Error", "Response Error");
                            }
                        }
                    });

                    try {
                        thd3.start();
                        // Wait Post process
                        thd3.join();
                    } catch (InterruptedException e) {
                        Log.e("thread join Error", "thread join Error");
                    }

                    new AlertDialog.Builder(EditInfoActivity.this)
                            .setTitle("정보 변경 완료")
                            .setMessage("입력하신 정보로 수정이 완료되었습니다.")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                    Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                                    intent.putExtra("token", token);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                }
                            })
                            .show(); // 팝업창 보여줌*/

                } else{
                    if ((new_password.equals(confirm_password))) {
                        Thread thd4 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient().newBuilder()
                                            .build();
                                    MediaType mediaType = MediaType.parse("application/json");
                                    RequestBody body = RequestBody.create(mediaType, "{\n    \"phoneNumber\": \""+phone_number+"\",\n    " +
                                            "\"prevPassword\": \""+prev_password+"\",\n    " +
                                            "\"password\": \""+new_password+"\"\n}");
                                    Request request = new Request.Builder()
                                            .url("https://gateway.viewinter.ai/api/users/"+id+"/modify")
                                            .method("PATCH", body)
                                            .addHeader("Authorization", "Bearer "+token)
                                            .addHeader("Content-Type", "application/json")
                                            .build();
                                    Response response = client.newCall(request).execute();

                                    responseResult = response.isSuccessful();

                                } catch (IOException e) {
                                    Log.e("Login Response Error", "Response Error");
                                }
                            }
                        });

                        try {
                            thd4.start();
                            // Wait Post process
                            thd4.join();
                        } catch (InterruptedException e) {
                            Log.e("thread join Error", "thread join Error");
                        }

                        new AlertDialog.Builder(EditInfoActivity.this)
                                .setTitle("정보 변경 완료")
                                .setMessage("입력하신 정보로 수정이 완료되었습니다.")
                                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                        Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                                        intent.putExtra("token", token);
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                    }
                                })
                                .show(); // 팝업창 보여줌*/
                    } else {
                        new AlertDialog.Builder(EditInfoActivity.this)
                                .setTitle("비밀번호 불일치")
                                .setMessage("입력하신 비밀번호가 일치하지 않습니다.")
                                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    /*Intent intent=new Intent(getApplicationContext(), BottomBarActivity.class);
                                    startActivity(intent);*/
                                    }
                                })
                                .show(); // 팝업창 보여줌*/
                    }
                }

            }

        });

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }

    /*public void CommunicateSet(String t, String e){
        token=t;
        email=e;
    }*/
}
