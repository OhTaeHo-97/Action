package com.example.action.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.action.R;

public class WordLayout extends LinearLayout{

    public WordLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public WordLayout(Context context) {
        super(context);

        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.word_layout,this,true);
    }
}

