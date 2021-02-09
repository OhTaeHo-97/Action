package com.example.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    Button close;
    CheckBox check1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

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