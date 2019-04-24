package com.example.semesterprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.semesterprojekt.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackInfoActivity extends AppCompatActivity {

    private ListView lv_track;
    private String username;
    private List<HashMap<String, String>> track = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_info);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        lv_track = findViewById(R.id.track_info);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        track = new SQLiteHelper(this).get_track(username);
        SimpleAdapter adapter = new SimpleAdapter(this, track, android.R.layout.simple_list_item_2, new String[]{"location", "time"}, new int[]{android.R.id.text1, android.R.id.text2});
        lv_track.setAdapter(adapter);

    }
}
