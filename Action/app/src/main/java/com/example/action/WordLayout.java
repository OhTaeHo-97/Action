package com.example.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

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

        // Spinner
        Spinner emotionSpinner =  inflater.inflate(R.layout.word_layout,this,true).findViewById(R.id.emotion_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.emotion_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotionSpinner.setAdapter(adapter);
    }
}

