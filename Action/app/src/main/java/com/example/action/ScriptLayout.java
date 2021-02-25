package com.example.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ScriptLayout extends LinearLayout {
    public ScriptLayout(Context context, AttributeSet attrs){
        super(context,attrs);

        init(context);
    }

    public ScriptLayout(Context context){
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.script_layout,this,true);
    }
}
