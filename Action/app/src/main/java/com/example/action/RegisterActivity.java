package com.example.action;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        findViewById(R.id.reg).setOnClickListener(mClickListener);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reg:
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("회원가입 완료")
                            .setMessage("회원가입이 완료되었습니다!")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show(); // 팝업창 보여줌

                    break;
            }
        }
    };
}