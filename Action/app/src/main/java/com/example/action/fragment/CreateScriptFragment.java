package com.example.action.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.action.MainActivity;
import com.example.action.R;
import com.example.action.fragment.WordLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CreateScriptFragment extends Fragment {

    public boolean responseResult;

    FloatingActionButton add_word_btn;
    Button create_btn;
    Button refresh_btn;
    EditText edit_title;
    Spinner genreSpinner;

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

        create_btn = (Button) rootView.findViewById(R.id.create_button);
        refresh_btn = (Button) rootView.findViewById(R.id.refresh_button);
        edit_title = (EditText) rootView.findViewById(R.id.edit_title);

        // Spinner
        genreSpinner = rootView.findViewById(R.id.genre_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(ct, R.array.genre_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Add word button(Floating)
        add_word_btn = (FloatingActionButton) rootView.findViewById(R.id.add_words_button);

        add_word_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordLayout n_layout = new WordLayout(getContext());
                wordLayoutList.add(n_layout);
                LinearLayout word_layout = (LinearLayout) rootView.findViewById(R.id.word_layout);
                word_layout.addView(n_layout);
            }
        });

        // Intialize fragment with refresh function
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        // Create Script complete button listener
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edit_title.getText().toString();
                String genre = genreSpinner.getSelectedItem().toString();
                Spinner emotionSpinner;
                EditText roleNameEditText, scriptEditText;
                String script_info = "";

                if (title.equals("")) {
                    new AlertDialog.Builder(ct)
                            .setTitle("대본생성 실패")
                            .setMessage("제목을 입력해주세요")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            })
                            .show(); // 팝업창 보여줌
                } else if (wordLayoutList.size() > 0) {
                    for (int i = 0; i < wordLayoutList.size(); i++) {
                        WordLayout n_wordLayout = wordLayoutList.get(i);
                        emotionSpinner = n_wordLayout.findViewById(R.id.emotion_spinner);
                        scriptEditText = n_wordLayout.findViewById(R.id.script_edit_text);
                        roleNameEditText = n_wordLayout.findViewById(R.id.edit_cast);

                        String emotion = emotionSpinner.getSelectedItem().toString();
                        String script = scriptEditText.getText().toString();
                        String roleName = roleNameEditText.getText().toString();

                        script_info = script_info + "{\n\"roleName\": \"" + roleName + "\"," +
                                "\n\"emotionName\": \"" + emotion + "\"," +
                                "\n\"scriptText\": \"" + script + "\"\n}";

                        if (i < wordLayoutList.size() - 1) {
                            script_info = script_info + ", ";
                        }

                    }

                    String finalScript_info = script_info;

                    Log.e("script", finalScript_info);

                    Thread thd = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient().newBuilder()
                                        .build();
                                MediaType mediaType = MediaType.parse("application/json");
                                RequestBody body = RequestBody.create(mediaType,
                                        "{\n    \"userId\": \"" + user_id + "\"," +
                                                "\n    \"title\": \"" + title + "\"," +
                                                "\n    \"genre\": \"" + genre + "\"," +
                                                "\n    \"scripts\": [\n        " +
                                                finalScript_info +
                                                "\n    ]\n}");
                                Request request = new Request.Builder()
                                        .url("https://gateway.viewinter.ai/api/scenarioes")
                                        .method("POST", body)
                                        .addHeader("Authorization", "Bearer " + token)
                                        .addHeader("Content-Type", "application/json")
                                        .build();
                                Response response = client.newCall(request).execute();

                                responseResult = response.isSuccessful();

                            } catch (IOException e) {
                                Log.e("Scenario Response Error", "Response Error");
                            }
                        }
                    });

                    try {
                        thd.start();

                        // Wait Post process
                        thd.join();
                    } catch (InterruptedException e) {
                        Log.e("thread join Error", "thread join Error");
                    }

                    new AlertDialog.Builder(ct)
                            .setTitle("대본작성 완료")
                            .setMessage("대본작성이 완료되었습니다!")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                    refresh();
                                }
                            })
                            .show();

                }
                // If the number of script is 0
                else {
                    new AlertDialog.Builder(ct)
                            .setTitle("대본생성 실패")
                            .setMessage("대사를 추가해주세요")
                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            })
                            .show(); // 팝업창 보여줌
                }
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    // Initialize fragment
    public void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        edit_title.setText("");
        genreSpinner.setSelection(0);
    }

}
