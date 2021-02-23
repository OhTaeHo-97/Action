package com.example.action;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class VideoRecordActivity extends AppCompatActivity {

    Button recordStartBtn;
    CameraSurfaceView surfaceView;
    private MediaRecorder mediaRecorder;
    private boolean recording=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .check();

        recordStartBtn = (Button)findViewById(R.id.start);

        recordStartBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(recording){
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    recording=false;
                    Toast.makeText(VideoRecordActivity.this, "녹화파일이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else{
                    runOnUiThread(new Runnable(){
                        @SuppressLint("SdCardPath")
                        public void run(){
                            Toast.makeText(VideoRecordActivity.this, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                            try {
                                mediaRecorder = new MediaRecorder();
                                mediaRecorder.setPreviewDisplay(surfaceView.holder.getSurface());
                                surfaceView.getCamera().startPreview();
                                surfaceView.getCamera().unlock();
                                mediaRecorder.setCamera(surfaceView.getCamera());
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mediaRecorder.setOrientationHint(90);
                                mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                                mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/DCIM/Camera/test.mp4");
                                mediaRecorder.prepare();
                                mediaRecorder.start();

                                recording = true;
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
}