package com.example.action;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindPWActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw_activity);

        findViewById(R.id.complete).setOnClickListener(mClickListener);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.complete:
                    new AlertDialog.Builder(FindPWActivity.this)
                            .setTitle("비밀번호 찾기 완료")
                            .setMessage("비밀번호: hong123@naver.com")
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
