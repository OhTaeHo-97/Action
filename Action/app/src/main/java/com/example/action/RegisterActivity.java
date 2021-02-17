package com.example.action;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button close;
    CheckBox check1;

    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference reference = database.getReference();
    private FirebaseAuth firebaseAuth;
    EditText ID_text;
    Spinner spinner;
    EditText phone;
    EditText PW;
    EditText PW_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //user=firebaseAuth.getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        //firebaseDatabase=FirebaseDatabase.getInstance().getReference();

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
                        String id_text=ID_text.getText().toString().trim();
                        String domain = spinner.getSelectedItem().toString().trim();
                        String id=id_text+"@"+domain;
                        String phone_num=phone.getText().toString().trim();
                        String password=PW.getText().toString();
                        String password_confirm=PW_confirm.getText().toString();

                        if(password.equals(password_confirm)){

                            final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                            mDialog.setMessage("가입중입니다...");
                            mDialog.show();

                            firebaseAuth.createUserWithEmailAndPassword(id,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>(){
                                public void onComplete(@NonNull Task<AuthResult> task){
                                    if(task.isSuccessful()){
                                        mDialog.dismiss();

                                        FirebaseUser user=firebaseAuth.getCurrentUser();
                                        String email=user.getEmail();
                                        String uid=user.getUid();

                                        HashMap<Object,String> hashMap=new HashMap<>();
                                        hashMap.put("uid",uid);
                                        hashMap.put("email",email);
                                        hashMap.put("phone",phone_num);

                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
                                        databaseReference.child(uid).setValue(hashMap);

                                        new AlertDialog.Builder(RegisterActivity.this)
                                                .setTitle("회원가입 완료")
                                                .setMessage("회원가입이 완료되었습니다!")
                                                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dlg, int sumthin) {
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show(); // 팝업창 보여줌
                                    }
                                    else{
                                        mDialog.dismiss();
                                        new AlertDialog.Builder(RegisterActivity.this)
                                                .setTitle("아이디 오류")
                                                .setMessage("이미 존재하는 아이디입니다.")
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