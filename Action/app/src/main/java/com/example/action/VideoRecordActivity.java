package com.example.action;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.Image;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VideoRecordActivity extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private boolean recording = false;
    private int startFlag = 0;

    String videoName = "test";

    Button recordStartBtn;
    CameraSurfaceView surfaceView;

    // Timer
    Timer myTimer;
    private int timer = 0;
    Button btn30, btn60, btn90, btn120;

    TextView remainTime;

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        // Permission for using camera
        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .check();

        // Back Button
        backBtn = (ImageButton)findViewById(R.id.video_record_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


        // Timer setting
        btn30 = (Button)findViewById(R.id.second_30_button);
        btn60 = (Button)findViewById(R.id.second_60_button);
        btn90 = (Button)findViewById(R.id.second_90_button);
        btn120 = (Button)findViewById(R.id.second_120_button);
        remainTime = (TextView)findViewById(R.id.remain_time);

        btn30.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(startFlag == 0){
                    timer = 30000;
                    btn30.setBackgroundColor(Color.YELLOW);
                    btn60.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn90.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn120.setBackgroundColor(getResources().getColor(R.color.gray_light));

                    remainTime.setText("남은시간 30초");
                }
            }
        });

        btn60.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(startFlag == 0){
                    timer = 60000;
                    btn30.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn60.setBackgroundColor(Color.YELLOW);
                    btn90.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn120.setBackgroundColor(getResources().getColor(R.color.gray_light));

                    remainTime.setText("남은시간 60초");
                }
            }
        });

        btn90.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(startFlag == 0){
                    timer = 90000;
                    btn30.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn60.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn90.setBackgroundColor(Color.YELLOW);
                    btn120.setBackgroundColor(getResources().getColor(R.color.gray_light));

                    remainTime.setText("남은시간 90초");
                }
            }
        });

        btn120.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(startFlag == 0){
                    timer = 120000;
                    btn30.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn60.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn90.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn120.setBackgroundColor(Color.YELLOW);

                    remainTime.setText("남은시간 120초");
                }
            }
        });


        // Start & Finish Record
        recordStartBtn = (Button)findViewById(R.id.record_start_button);

        recordStartBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(timer == 0){
                    new AlertDialog.Builder(VideoRecordActivity.this)
                            .setTitle("시작할수 없습니다")
                            .setMessage("타이머를 설정해주세요")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            })
                            .show();
                }
                // Tap complete button
                else if(recording){
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    recording=false;
                    Toast.makeText(VideoRecordActivity.this, "컷!", Toast.LENGTH_SHORT).show();
                    recordStartBtn.setText("시작");
                    recordStartBtn.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
                    startFlag = 0;

                    myTimer.cancel();
                    timer = 0;
                    remainTime.setText("남은시간 00초");

                    btn30.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn60.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn90.setBackgroundColor(getResources().getColor(R.color.gray_light));
                    btn120.setBackgroundColor(getResources().getColor(R.color.gray_light));
                }
                else{
                    runOnUiThread(new Runnable(){
                        @SuppressLint("SdCardPath")
                        public void run(){
                            Toast.makeText(VideoRecordActivity.this, "액션!", Toast.LENGTH_SHORT).show();
                            try {
                                mediaRecorder = new MediaRecorder();
                                mediaRecorder.setPreviewDisplay(surfaceView.holder.getSurface());
                                surfaceView.getCamera().startPreview();
                                surfaceView.getCamera().unlock();
                                mediaRecorder.setCamera(surfaceView.getCamera());
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mediaRecorder.setMaxDuration(timer); // timer
                                mediaRecorder.setOrientationHint(270);
                                mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                                mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/DCIM/Camera/"+ videoName +".mp4");
                                mediaRecorder.prepare();
                                mediaRecorder.start();

                                recording = true;

                                // Timer
                                myTimer = new Timer(timer, 1000);
                                myTimer.start();

                                recordStartBtn.setText("완료");
                                recordStartBtn.setBackgroundColor(getResources().getColor(R.color.buttonOrange));
                                startFlag = 1;


                            } catch (Exception e) {
                                e.printStackTrace();
                                mediaRecorder.release();
                            }


                        }
                    });
                }
            }
        });

    }

    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(VideoRecordActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();

            surfaceView = findViewById(R.id.surfaceView);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(VideoRecordActivity.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };

    public class Timer extends CountDownTimer {

        public Timer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainTime.setText("남은시간 " + millisUntilFinished/1000 + "초");
        }

        @Override
        public void onFinish() {
            remainTime.setText("남은시간 00초");
            mediaRecorder.stop();
            mediaRecorder.release();
            recording=false;
            Toast.makeText(VideoRecordActivity.this, "컷!", Toast.LENGTH_SHORT).show();
            recordStartBtn.setText("시작");
            recordStartBtn.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
            startFlag = 0;

            btn30.setBackgroundColor(getResources().getColor(R.color.gray_light));
            btn60.setBackgroundColor(getResources().getColor(R.color.gray_light));
            btn90.setBackgroundColor(getResources().getColor(R.color.gray_light));
            btn120.setBackgroundColor(getResources().getColor(R.color.gray_light));
        }

    }
}

