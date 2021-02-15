package com.example.action.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.action.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CreateScriptFragment extends Fragment {

    FloatingActionButton add_word_btn;

    public CreateScriptFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_create_script, container, false);
        Context ct = container.getContext();

        // Add word button
        add_word_btn = (FloatingActionButton) rootView.findViewById(R.id.add_words_button);

        add_word_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WordLayout n_layout = new WordLayout(getContext());
                LinearLayout word_layout = (LinearLayout) rootView.findViewById(R.id.word_layout);
                word_layout.addView(n_layout);
            }
        });

        // Spinner
        Spinner genreSpinner = rootView.findViewById(R.id.genre_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(ct, R.array.genre_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}