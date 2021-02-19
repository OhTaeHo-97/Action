package com.example.action.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.action.R;
import com.example.action.fragment.WordLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class CreateScriptFragment extends Fragment {

    FloatingActionButton add_word_btn;
    Button create_btn;
    EditText edit_title;

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

        List<WordLayout> wordLayoutList = new ArrayList<WordLayout>();

        // Get user inform
        String token = getArguments().getString("token");
        String email = getArguments().getString("email");
        String user_id = getArguments().getString("user_id");

        create_btn = (Button)rootView.findViewById(R.id.create_button);
        edit_title = (EditText)rootView.findViewById(R.id.edit_title);

        // Spinner
        Spinner genreSpinner = rootView.findViewById(R.id.genre_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(ct, R.array.genre_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        String title = edit_title.getText().toString();
        String genre = genreSpinner.getSelectedItem().toString();

        // Add word button(Floating)
        add_word_btn = (FloatingActionButton) rootView.findViewById(R.id.add_words_button);

        add_word_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WordLayout n_layout = new WordLayout(getContext());
                wordLayoutList.add(n_layout);
                LinearLayout word_layout = (LinearLayout) rootView.findViewById(R.id.word_layout);
                word_layout.addView(n_layout);
            }
        });

        // Create Script complete button listener
        create_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Spinner emotionSpinner;
                EditText script;

                for(int i = 0; i < wordLayoutList.size(); i++)
                {
                    WordLayout n_wordLayout = wordLayoutList.get(i);
                    emotionSpinner = n_wordLayout.findViewById(R.id.emotion_spinner);

                    String emotion = emotionSpinner.getSelectedItem().toString();
                    Log.e("emotion", emotion);

                }
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }
}