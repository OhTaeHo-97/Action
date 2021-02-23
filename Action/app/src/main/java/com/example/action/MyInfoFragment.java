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
import java.util.StringTokenizer;

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
    List<ScriptLayout> scriptLayoutList=new ArrayList<ScriptLayout>();

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
                Log.e("wow",result);

                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("scripts");
                for(int i=0;i<jsonArray.length();i++){
                    try{
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        //String item=jsonObject
                    }catch(JSONException e){

                    }
                }
                String title_text=jsonObject.getString("scenarios");
                Log.e("asdf",title_text);



                if(result!=null||result.length()!=0){
                    StringTokenizer st = new StringTokenizer(result,"{}");
                    while(st.hasMoreTokens()){
                        pstr.add(st.nextToken());
                    }
                    arr=new String[pstr.size()];
                    arr=pstr.toArray(arr);

                    pstr.clear();
                    for(int i=0;i<arr.length;i++){
                        if(arr[i].contains("\"title\"")&&arr[i].contains("\"userId\""))
                            pstr.add(arr[i]);
                    }
                    String[] title=new String[pstr.size()];
                    ArrayList<String> title_list=new ArrayList<String>();
                    ArrayList<String> title_list2=new ArrayList<String>();
                    title=pstr.toArray(title);
                    int title_length=title.length;

                    for(int i=0;i<title_length;i++){
                        st=new StringTokenizer(title[i],":");
                        while(st.hasMoreTokens()){
                            title_list.add(st.nextToken());
                        }
                        String[] title_array=new String[title_list.size()];
                        title_list.toArray(title_array);
                        for(int j=0;j<title_array.length;j++){
                            if(title_array[j].contains("\"title\""))
                                title_list2.add(title_array[j+1]);
                        }
                        title_list.clear();
                    }

                    title=new String[title_list2.size()];
                    title_list2.toArray(title);

                    for(int i=0;i<title.length;i++)
                        title[i]=title[i].trim();

                    String[] title_array=new String[title.length];
                    int index_Of;
                    for(int i=0;i<title.length;i++){
                        index_Of=title[i].indexOf("\"",1);
                        title_array[i]=title[i].substring(1,index_Of);
                    }

                    ArrayList<Integer> index=new ArrayList<>();
                    int count=0;
                    for(int i=0;i<arr.length;i++){
                        if(arr[i].contains(title_array[count])){
                            index.add(i);
                            count++;
                        }
                        if(count==title_array.length){
                            break;
                        }
                    }

                    int[] index_list=new int[index.size()];
                    for(int i=0;i<index_list.length;i++)
                        index_list[i]=index.get(i);

                    ArrayList<String[]> script=new ArrayList<String[]>();
                    pstr.clear();
                    String[] script_list;

                    for(int i=0;i<index_list.length;i++){
                        for(int j=index_list[i]+1;j<arr.length;j++){
                            if(i==index_list.length-1)
                                pstr.add(arr[j]);
                            else{
                                if(j==index_list[i+1])
                                    break;
                                pstr.add(arr[j]);
                            }
                        }
                        script_list=new String[pstr.size()];
                        script_list=pstr.toArray(script_list);
                        pstr.clear();
                        for(int j=0;j<script_list.length;j++){
                            if(script_list[j].contains("\"scriptText\""))
                                pstr.add(script_list[j]);
                        }
                        script_list=new String[pstr.size()];
                        script_list=pstr.toArray(script_list);
                        script.add(script_list);
                        pstr.clear();
                    }

                    String[][] each_script=new String[script.size()][];
                    for(int i=0;i<script.size();i++){
                        String[] row=script.get(i);
                        each_script[i]=new String[row.length];
                        for(int j=0;j<row.length;j++)
                            each_script[i][j]=row[j];
                    }

                    LinearLayout script_layout=(LinearLayout)rootview.findViewById(R.id.script_layout);
                    //int btn_id=0;

                    for(int i=0;i<each_script.length;i++){
                        Button edit=new Button(getActivity());
                        edit.setId(i);
                        edit.setText("수정");
                        /*Button with=new Button(getActivity());
                        with.setId(btn_id+1);
                        btn_id=btn_id+1;
                        with.setText("함께 하기");*/
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        LinearLayout linear=new LinearLayout(getActivity());
                        edit.setLayoutParams(params);

                        linear.addView(edit);
                        script_layout.addView(linear);
                        linear=new LinearLayout(getActivity());

                        TextView view1=new TextView(getActivity());
                        view1.setText("제목: ");
                        view1.setTextSize(30);
                        TextView view2=new TextView(getActivity());
                        view2.setText(title_array[i]);
                        view2.setTextSize(30);

                        view1.setLayoutParams(params);
                        view2.setLayoutParams(params);

                        linear.addView(view1);
                        linear.addView(view2);

                        script_layout.addView(linear);
                        int total_count=0;

                        for(int j=0;j<each_script[i].length;j++){
                            ScriptLayout sl=new ScriptLayout(getActivity());
                            script_layout.addView(sl);
                            scriptLayoutList.add(sl);
                            pstr.clear();
                            String name;
                            String feeling="";
                            String script_text="";
                            st=new StringTokenizer(each_script[i][j],":");
                            while(st.hasMoreTokens())
                                pstr.add(st.nextToken());
                            String[] tokens=new String[pstr.size()];
                            pstr.toArray(tokens);
                            for(int l=0;l<tokens.length;l++){
                                if(tokens[l].contains("\"emotionName\""))
                                    feeling=tokens[l+1];
                                if(tokens[l].contains("\"scriptText\""))
                                    script_text=tokens[l+1];
                            }
                            ScriptLayout scriptLayout=scriptLayoutList.get(total_count);
                            Button script1=scriptLayout.findViewById(R.id.script1);
                            TextView emotion=scriptLayout.findViewById(R.id.emotion);
                            script1.setText(script_text);
                            emotion.setText(feeling);
                            total_count++;
                        }
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