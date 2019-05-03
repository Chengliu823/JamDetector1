package com.example.semesterprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private TextView mTvGoToHelp, mTvGoToFAQ, mTvGoToAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mTvGoToHelp = findViewById(R.id.tv_go_to_help);
        mTvGoToFAQ = findViewById(R.id.tv_go_to_faq);
        mTvGoToAboutUs = findViewById(R.id.tv_go_to_aboutus);

        mTvGoToHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入注册界面
                Intent intent = new Intent(SettingActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        mTvGoToFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入注册界面
                Intent intent = new Intent(SettingActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        mTvGoToAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入注册界面
                Intent intent = new Intent(SettingActivity.this, AboutUSActivity.class);
                startActivity(intent);
            }
        });

    }
}
