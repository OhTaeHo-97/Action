package com.example.action;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_my_info,container,false);

        ViewPager vp=rootview.findViewById(R.id.viewpager);
        VPAdapter adapter=new VPAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab=rootview.findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            email=user.getEmail();
        }*/

        Button info=(Button)rootview.findViewById(R.id.info);
        String token = getArguments().getString("token");
        String email=getArguments().getString("email");

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

    /*public interface CommunicateInterface {
        void CommunicateSet(String t, String e);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof CommunicateInterface){
            communicateInterface = (CommunicateInterface) context;
        }else{
            throw new RuntimeException(context.toString()
            +"must implement CommunciateInterface");
        }
    }

    public void onDetach(){
        super.onDetach();
        communicateInterface=null;
    }*/
}