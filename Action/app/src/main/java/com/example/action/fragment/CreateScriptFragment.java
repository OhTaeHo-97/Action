package com.example.action.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.action.R;


public class CreateScriptFragment extends Fragment {

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_create_script, container, false);
        Context ct = container.getContext();

        // Spinner
        Spinner genreSpinner = rootView.findViewById(R.id.genre_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(ct, R.array.genre_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}