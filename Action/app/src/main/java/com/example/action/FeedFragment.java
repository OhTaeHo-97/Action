package com.example.action;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    VideoView video;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_feed,container,false);

        Button evaluation=(Button)rootview.findViewById(R.id.evaluation);
        evaluation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getActivity(),StarRatingActivity.class);
                startActivity(intent);
            }
        });

        video=rootview.findViewById(R.id.video);
        Uri videoUri=Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

        //비디오뷰의 재생, 일시정지 등을 할 수 있는 컨트롤바를 붙여주는 작업
        video.setMediaController(new MediaController(getActivity()));
        video.setVideoURI(videoUri);

        //동영상 읽어오는데 시간 걸리므로
        //비디오 로딩 준비가 끝났을 때 씰행하도록
        //리스너 설정
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            public void onPrepared(MediaPlayer mediaPlayer){
                //비디오 시작
                video.start();
            }
        });

        return rootview;
    }

    //화면에 안 보일 때
    public void onPause(){
        super.onPause();

        //비디오 일시정지
        if(video!=null && video.isPlaying()) video.pause();
    }

    public void onDestroy() {
        super.onDestroy();
        //
        if(video!=null) video.stopPlayback();
    }
}