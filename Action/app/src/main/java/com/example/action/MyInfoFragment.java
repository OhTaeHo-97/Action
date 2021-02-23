package com.example.action;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Use the {@link MyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private CommunicateInterface communicateInterface;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyInfoFragment newInstance(String param1, String param2) {
        MyInfoFragment fragment = new MyInfoFragment();
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

    //Button info;
    boolean responseResult;
    ArrayList<String> pstr = new ArrayList<String>();
    String[] arr;
    List<ScriptLayout[]> scriptLayoutList=new ArrayList<ScriptLayout[]>();
    List<Button> buttons=new ArrayList<Button>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_my_info,container,false);

        Button info=(Button)rootview.findViewById(R.id.info);
        String token = getArguments().getString("token");
        String email=getArguments().getString("email");
        String user_id=getArguments().getString("user_id");

        Thread thd = new Thread(new Runnable() { @SuppressLint("ResourceType")
        @Override public void run() {
            try {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://gateway.viewinter.ai/api/scenarioes/query/paging?search=userId:" + user_id + "&size=1000&page=0&sort=createTime,asc")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                Response response = client.newCall(request).execute();

                Log.e("TOKEN",token);
                Log.e("USER_ID",user_id);

                String result = response.body().string();
                //Log.e("wow",result);
                //List<JSONArray> jsonArray_list=new ArrayList<JSONArray>();
                /*for(int i=0;i<json_list.length;i++)
                    jsonArray_list.add(json_list[i].optJSONArray("scripts"));//

                JSONArray[] jsonArrays=new JSONArray[jsonArray_list.size()];
                jsonArray_list.toArray(jsonArrays);

                jsonObjectList=new ArrayList<JSONObject>();

                for(int i=0;i<title.length;i++){

                }
                for(int i=0;i<jsonArrays.length;i++){
                    jsonObjectList.add(jsonArrays[i].getJSONObject(i));
                    for(int j=0;j<jsonObjectList.size();j++){

                    }
                }*/

                /*for(int i=0;i<jsonArray.length();i++){
                    try{
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String title_text=jsonObject.getString("title");
                        Log.e("asdf",title_text);
                    }catch(JSONException e){

                    }
                }*/

                if(result!=null||result.length()!=0){
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject jsonObject1=jsonObject.optJSONObject("_embedded");
                    JSONArray jsonArray=jsonObject1.optJSONArray("scenarios");
                    List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();
                    for(int i=0;i<jsonArray.length();i++)
                        jsonObjectList.add(jsonArray.getJSONObject(i));//object로 scenario 2개

                    JSONObject[] json_list=new JSONObject[jsonObjectList.size()];
                    jsonObjectList.toArray(json_list);

                    String[] title=new String[jsonObjectList.size()];
                    for(int i=0;i<json_list.length;i++){
                        title[i]=json_list[i].getString("title");//title 뽑아냄
                    }

                    LinearLayout script_layout=(LinearLayout)rootview.findViewById(R.id.script_layout);
                    List<JSONArray> jsonArray_list=new ArrayList<JSONArray>();

                    for(int i=0;i<json_list.length;i++){
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        LinearLayout linear=new LinearLayout(getActivity());
                        TextView view1=new TextView(getActivity());
                        view1.setText("제목: ");
                        view1.setTextSize(30);
                        TextView view2=new TextView(getActivity());
                        view2.setText(title[i]);
                        view2.setTextSize(30);
                        view1.setLayoutParams(params);
                        view2.setLayoutParams(params);

                        linear.addView(view1);
                        linear.addView(view2);

                        script_layout.addView(linear);

                        JSONArray jsonArray1=json_list[i].optJSONArray("scripts");// 각 scenario 별 script에 대한 Array
                        jsonObjectList=new ArrayList<JSONObject>();
                        for(int j=0;j<jsonArray1.length();j++){
                            jsonObjectList.add(jsonArray1.getJSONObject(j)); // 각 scenario 별 script list
                        }
                        JSONObject[] jsonObjects=new JSONObject[jsonObjectList.size()];
                        jsonObjectList.toArray(jsonObjects);
                        String emotion;
                        String name;
                        String script_text;
                        ScriptLayout[] scriptLayouts=new ScriptLayout[jsonObjects.length];
                        for(int j=0;j<jsonObjects.length;j++){
                            emotion=jsonObjects[j].getString("emotionName");
                            name=jsonObjects[j].getString("roleName");
                            script_text=jsonObjects[j].getString("scriptText");
                            Log.e("SCRIPT",script_text);

                            ScriptLayout sl=new ScriptLayout(getActivity());
                            script_layout.addView(sl);
                            scriptLayouts[j]=sl;

                            ScriptLayout scriptLayout=scriptLayouts[j];

                            Button script1=scriptLayout.findViewById(R.id.script1);
                            TextView emotion_tv=scriptLayout.findViewById(R.id.emotion);
                            TextView name_tv=scriptLayout.findViewById(R.id.name);
                            buttons.add(script1);
                            script1.setText(script_text);
                            emotion_tv.setText(emotion);
                            name_tv.setText(name);
                        }
                        scriptLayoutList.add(scriptLayouts);
                    }

                }

                responseResult = response.isSuccessful();
            } catch (IOException | JSONException e) {
                Log.e("Response Error", "Response Error");
            }
        } });

        try {
            thd.start();

            // Wait Post process
            thd.join();
        } catch(InterruptedException e){
            Log.e("thread join Error","thread join Error");
        }

        if(buttons.size()!=0){
            for(int i=0;i<buttons.size();i++){
                buttons.get(i).setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        //communicateInterface.CommunicateSet(token,email);
                        Intent intent=new Intent(getActivity(), SelectPopUpActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        info.setText(email);
        info.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //communicateInterface.CommunicateSet(token,email);
                Intent intent=new Intent(getActivity(), EditInfoActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        return rootview;
    }

}