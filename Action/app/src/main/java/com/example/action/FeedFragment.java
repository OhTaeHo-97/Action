package com.example.action;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    String token;
    String user_id;
    boolean responseResult;
    ArrayList<String> id_list = new ArrayList<String>();
    ArrayList<String> emotion_list = new ArrayList<String>();
    ArrayList<String> result_list = new ArrayList<String>();
    ArrayList<String> videos = new ArrayList<String>();
    String[] file;
    String[] video_id;
    ArrayList<String[]> evaluation_num=new ArrayList<String[]>();
    List<Button> buttons = new ArrayList<Button>();
    List<Button> start_buttons = new ArrayList<Button>();
    List<Button> stop_buttons = new ArrayList<Button>();
    List<VideoView> videoViews = new ArrayList<VideoView>();
    ArrayList<String[]> file_path = new ArrayList<String[]>();
    ArrayList<String[]> video_id_list = new ArrayList<String[]>();
    int total_size = 0;
    int count=0;

    //List<FeedLayout[]> feedLayoutList=new ArrayList<FeedLayout[]>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_feed, container, false);

        token = getArguments().getString("token");
        user_id = getArguments().getString("user_id");

        Thread thd = new Thread(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("https://gateway.viewinter.ai/api/scenarioes/query/paging?search=userId:" + user_id + "&size=1000&page=0&sort=createTime,asc")
                            .method("GET", null)
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    Response response = client.newCall(request).execute();
                    responseResult = response.isSuccessful();

                    Log.e("TOKEN", token);
                    Log.e("USER_ID", user_id);

                    String result = response.body().string();
                    Log.e("wow", result);

                    if (result != null || result.length() != 0) {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonObject1 = jsonObject.optJSONObject("_embedded");
                        JSONArray jsonArray = jsonObject1.optJSONArray("scenarios");


                        if (jsonArray == null) {
                            this.finalize();
                        }


                        List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
                        for (int i = 0; i < jsonArray.length(); i++)
                            jsonObjectList.add(jsonArray.getJSONObject(i));//object로 scenario 2개

                        JSONObject[] json_list = new JSONObject[jsonObjectList.size()];
                        jsonObjectList.toArray(json_list);

                        String[] title = new String[jsonObjectList.size()];
                        for (int i = 0; i < json_list.length; i++) {
                            title[i] = json_list[i].getString("title");//title 뽑아냄
                        }


                        List<JSONArray> jsonArray_list = new ArrayList<JSONArray>();
                        List<JSONArray> jsonArrays = new ArrayList<JSONArray>();

                        int c = 0;

                        for (int i = 0; i < json_list.length; i++) {
                            JSONArray jsonArray1 = json_list[i].optJSONArray("scripts");// 각 scenario 별 script에 대한 Array
                            jsonObjectList = new ArrayList<JSONObject>();
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                jsonObjectList.add(jsonArray1.getJSONObject(j)); // 각 scenario 별 script Object들 list
                            }
                            JSONObject[] jsonObjects = new JSONObject[jsonObjectList.size()];
                            jsonObjectList.toArray(jsonObjects);

                            for (int j = 0; j < jsonObjects.length; j++) {
                                String emotion = jsonObjects[j].getString("emotionName");
                                emotion_list.add(emotion);
                                JSONArray jsonArray2 = jsonObjects[j].optJSONArray("videos");
                                List<JSONObject> jsonObjectList1 = new ArrayList<JSONObject>();
                                for (int l = 0; l < jsonArray2.length(); l++){
                                    jsonObjectList1.add(jsonArray2.getJSONObject(l));
                                }

                                ArrayList<String> file_path_list = new ArrayList<String>();
                                ArrayList<String> evaluation_list = new ArrayList<String>();
                                ArrayList<String> video_id_arr=new ArrayList<String>();

                                for (int l = 0; l < jsonObjectList1.size(); l++) {
                                    video_id_arr.add(jsonObjectList1.get(l).getString("id"));
                                    String user_id = jsonObjectList1.get(l).getString("userId");
                                    String script_id = jsonObjectList1.get(l).getString("scriptId");
                                    String evaluation = jsonObjectList1.get(l).getString("evaluation");
                                    evaluation_list.add(evaluation);

                                    String videoName = user_id + script_id + "_video";
                                    String file_path_text = Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + videoName + ".mp4";

                                    file_path_list.add(file_path_text);
                                    total_size++;
                                }
                                if(file_path_list.size()!=0){
                                    String[] file_path_arr = new String[file_path_list.size()];// 한 script 별 파일 경로
                                    file_path_list.toArray(file_path_arr);
                                    file_path.add(file_path_arr);// scenario 별 파일 경로
                                }
                                //String[] file_path_arr = new String[file_path_list.size()];// 한 script 별 파일 경로
                                String[] eval=new String[evaluation_list.size()];
                                String[] video_id_array=new String[video_id_arr.size()];
                                evaluation_list.toArray(eval);
                                evaluation_num.add(eval);
                                video_id_arr.toArray(video_id_array);
                                video_id_list.add(video_id_array);

                                //file_path_list.toArray(file_path_arr);
                                //file_path.add(file_path_arr);// scenario 별 파일 경로

                            /*LinearLayout feed_layout=(LinearLayout)rootview.findViewById(R.id.feed_script);
                            FeedLayout[] feedLayouts=new FeedLayout[total_size];
                            total_size=0;

                            for(int l=0;l<file_path.size();l++){
                                for(int m=0;m<file_path.get(l).length;m++){
                                    FeedLayout fl=new FeedLayout(getActivity());
                                    feed_layout.addView(fl);
                                    feedLayouts[total_size]=fl;

                                    FeedLayout feedLayout=feedLayouts[total_size];
                                    VideoView video=feedLayout.findViewById(R.id.video);
                                    videoViews.add(video);
                                    MediaController mc=new MediaController(getActivity());
                                    video.setMediaController(mc);
                                    String path=file_path.get(l)[m];//경로
                                    video.setVideoPath(path);
                                    video.requestFocus();

                                    Button start=feedLayout.findViewById(R.id.start);
                                    start.setTag(Integer.parseInt(video_id_list.get(total_size)));
                                    start_buttons.add(start);
                                    start_buttons.get(total_size).setOnClickListener(new View.OnClickListener(){
                                        public void onClick(View v){
                                            videoViews.get(flag).seekTo(0);
                                            videoViews.get(flag).start();
                                        }
                                    });

                                    Button stop=feedLayout.findViewById(R.id.stop);
                                    stop.setTag(video_id[c]);
                                    stop_buttons.add(stop);
                                    stop_buttons.get(c).setOnClickListener(new View.OnClickListener(){
                                        public void onClick(View v){
                                            videoViews.get(flag).seekTo(0);
                                            videoViews.get(flag).start();
                                        }
                                    });

                                    TextView feeling=feedLayout.findViewById(R.id.feeling);
                                    feeling.setText(emotion_list.get(j));
                                    TextView score=feedLayout.findViewById(R.id.score);
                                    score.setText(evaluation_num[j]);
                                    Button evaluation=feedLayout.findViewById(R.id.evaluation);
                                    evaluation.setTag(video_id[c]);
                                    buttons.add(evaluation);

                                    buttons.get(c).setOnClickListener(new View.OnClickListener(){
                                        public void onClick(View v){
                                            int tag=(Integer)v.getTag();
                                            Log.e("TAG",Integer.toString(tag));

                                            Intent intent=new Intent(getActivity(), StarRatingActivity.class);
                                            intent.putExtra("file_id",tag);
                                            intent.putExtra("token",token);
                                            startActivity(intent);
                                        }
                                    });*/
                            }
                        }

                        /*Log.e("file_path",Integer.toString(file_path.size()));
                        for(int i=0;i<file_path.size();i++){
                            for(int j=0;j<file_path.get(i).length;j++){
                                Log.e("FILE",file_path.get(i)[j]);
                            }
                        }*/
                        //Log.e("total_size",Integer.toString(total_size));

                        LinearLayout feed_layout=(LinearLayout)rootview.findViewById(R.id.feed_script);
                        FeedLayout[] feedLayouts=new FeedLayout[total_size];
                        for(int i=0;i<file_path.size();i++){
                            for(int j=0;j<file_path.get(i).length;j++){
                                FeedLayout fl=new FeedLayout(getActivity());
                                feed_layout.addView(fl);
                                feedLayouts[count]=fl;

                                FeedLayout feedLayout=feedLayouts[count];
                                VideoView video=feedLayout.findViewById(R.id.video);
                                videoViews.add(video);
                                MediaController mc=new MediaController(getActivity());
                                video.setMediaController(mc);
                                String path=file_path.get(i)[j];//경로
                                video.setVideoPath(path);
                                video.requestFocus();


                                Button start=feedLayout.findViewById(R.id.start);
                                start.setTag(Integer.parseInt(video_id_list.get(i)[j]));
                                start_buttons.add(start);
                                start_buttons.get(total_size).setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        videoViews.get(count).seekTo(0);
                                        videoViews.get(count).start();
                                    }
                                });

                                Button stop=feedLayout.findViewById(R.id.stop);
                                stop.setTag(Integer.parseInt(video_id_list.get(i)[j]));
                                stop_buttons.add(stop);
                                stop_buttons.get(c).setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        videoViews.get(count).seekTo(0);
                                        videoViews.get(count).start();
                                    }
                                });

                                TextView feeling=feedLayout.findViewById(R.id.feeling);
                                feeling.setText(emotion_list.get(i));
                                TextView score=feedLayout.findViewById(R.id.score);
                                score.setText(evaluation_num.get(i)[j]);
                                Button evaluation=feedLayout.findViewById(R.id.evaluation);
                                evaluation.setTag(Integer.parseInt(video_id_list.get(i)[j]));
                                buttons.add(evaluation);

                                buttons.get(c).setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        int tag=(Integer)v.getTag();
                                        Log.e("TAG",Integer.toString(tag));

                                        Intent intent=new Intent(getActivity(), StarRatingActivity.class);
                                        intent.putExtra("file_id",tag);
                                        intent.putExtra("token",token);
                                        startActivity(intent);
                                    }
                                });

                                count++;
                            }

                        }

                        //jsonArrays.add(jsonArray2);
                    }
                } catch (IOException | JSONException e) {
                    Log.e("Response Error", "Response Error");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        try {
            thd.start();

            // Wait Post process
            thd.join();
        } catch (InterruptedException e) {
            Log.e("thread join Error", "thread join Error");
        }

        /*Thread thd1 = new Thread(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    for (int i = 0; i < id_list.size(); i++) {
                        Request request = new Request.Builder()
                                .url("https://gateway.viewinter.ai/api/videos/query/paging?search=scriptId:" + id_list.get(i) + "&size=1000&page=0&sort=createTime,desc")
                                .method("GET", null)
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        Response response = client.newCall(request).execute();
                        responseResult = response.isSuccessful();
                        String result = response.body().string();
                        result_list.add(result);
                    }

                    if (result_list.size() != 0) {
                        String[] results = new String[result_list.size()];
                        result_list.toArray(results);

                        int c = 0;
                        for (int i = 0; i < results.length; i++) {
                            JSONObject jsonObject = new JSONObject(results[i]);
                            JSONObject jsonObject1 = jsonObject.optJSONObject("_embedded");
                            JSONArray jsonArray = jsonObject1.optJSONArray("videos");

                            if (jsonArray == null) {
                                this.finalize();
                            }

                            List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
                            for (int j = 0; j < jsonArray.length(); j++)
                                jsonObjectList.add(jsonArray.getJSONObject(i));

                            JSONObject[] json_list = new JSONObject[jsonObjectList.size()];
                            jsonObjectList.toArray(json_list);

                            file = new String[jsonObjectList.size()];
                            video_id = new String[jsonObjectList.size()];
                            evaluation_num = new String[jsonObjectList.size()];
                            for (int j = 0; j < json_list.length; j++) {
                                file[j] = json_list[j].getString("fileLocation");
                                video_id[j] = json_list[j].getString("id");
                                evaluation_num[j] = json_list[j].getString("evaluation");
                            }

                            LinearLayout feed_layout = (LinearLayout) rootview.findViewById(R.id.feed_script);
                            FeedLayout[] feedLayouts = new FeedLayout[file.length];

                            for (int j = 0; j < file.length; j++) {
                                FeedLayout fl = new FeedLayout(getActivity());
                                feed_layout.addView(fl);
                                feedLayouts[j] = fl;

                                FeedLayout feedLayout = feedLayouts[j];
                                VideoView video = feedLayout.findViewById(R.id.video);
                                videoViews.add(video);
                                MediaController mc = new MediaController(getActivity());
                                video.setMediaController(mc);
                                String path = file[j];//경로
                                video.setVideoPath(path);
                                video.requestFocus();

                                flag = c;
                                Button start = feedLayout.findViewById(R.id.start);
                                start.setTag(video_id[c]);
                                start_buttons.add(start);
                                start_buttons.get(c).setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        videoViews.get(flag).seekTo(0);
                                        videoViews.get(flag).start();
                                    }
                                });

                                Button stop = feedLayout.findViewById(R.id.stop);
                                stop.setTag(video_id[c]);
                                stop_buttons.add(stop);
                                stop_buttons.get(c).setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        videoViews.get(flag).seekTo(0);
                                        videoViews.get(flag).start();
                                    }
                                });

                                TextView feeling = feedLayout.findViewById(R.id.feeling);
                                feeling.setText(emotion_list.get(j));
                                TextView score = feedLayout.findViewById(R.id.score);
                                score.setText(evaluation_num[j]);
                                Button evaluation = feedLayout.findViewById(R.id.evaluation);
                                evaluation.setTag(video_id[c]);
                                buttons.add(evaluation);

                                buttons.get(c).setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        int tag = (Integer) v.getTag();
                                        Log.e("TAG", Integer.toString(tag));

                                        Intent intent = new Intent(getActivity(), StarRatingActivity.class);
                                        intent.putExtra("file_id", tag);
                                        intent.putExtra("token", token);
                                        startActivity(intent);
                                    }
                                });
                                c++;
                            }

                        }
                    }
                } catch (IOException | JSONException e) {
                    Log.e("Response Error", "Response Error");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        try {
            thd1.start();

            // Wait Post process
            thd1.join();
        } catch (InterruptedException e) {
            Log.e("thread join Error", "thread join Error");
        }*/

        /*
        // MediaController : 특정 View 위에서 작동하는 미디어 컨트롤러 객체
        MediaController mc = new MediaController(this);
        vv.setMediaController(mc); // Video View 에 사용할 컨트롤러 지정

        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath(); // 기본적인 절대경로 얻어오기


        // 절대 경로 = SDCard 폴더 = "stroage/emulated/0"
        //          ** 이 경로는 폰마다 다를수 있습니다.**
        // 외부메모리의 파일에 접근하기 위한 권한이 필요 AndroidManifest.xml에 등록
        Log.d("test", "절대 경로 : " + path);

        vv.setVideoPath(path+"/video/kakaotalk_1458998519582.3gp");
                               // VideoView 로 재생할 영상
                               // 아까 동영상 [상세정보] 에서 확인한 경로
        vv.requestFocus(); // 포커스 얻어오기
        vv.start(); // 동영상 재생
         */

        /*video=rootview.findViewById(R.id.video);
        Uri videoUri=Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

        //비디오뷰의 재생, 일시정지 등을 할 수 있는 컨트롤바를 붙여주는 작업
        video.setMediaController(new MediaController(getActivity()));
        video.setVideoURI(videoUri);*/

        //동영상 읽어오는데 시간 걸리므로
        //비디오 로딩 준비가 끝났을 때 씰행하도록
        //리스너 설정
        /*video.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            public void onPrepared(MediaPlayer mediaPlayer){
                //비디오 시작
                video.start();
            }
        });*/

        return rootview;
    }

    //화면에 안 보일 때
    public void onPause() {
        super.onPause();

        //비디오 일시정지
        if (video != null && video.isPlaying()) video.pause();
    }

    public void onDestroy() {
        super.onDestroy();
        //
        if (video != null) video.stopPlayback();
    }
}