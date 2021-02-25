package com.example.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class FeedLayout extends LinearLayout {
    public FeedLayout(Context context, AttributeSet attrs){
        super(context,attrs);

        init(context);
    }

    public FeedLayout(Context context){
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.feed_layout,this,true);
    }
}
