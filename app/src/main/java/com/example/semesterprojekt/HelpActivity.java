package com.example.semesterprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        text = (TextView) findViewById(R.id.textView1);
        text.setText("Wir sind Studierende des von Verkehr und Umwelt\n" +
                "\n" +
                "Kontakt:\n" +
                "Frau Peres Ferreira: vu17b038@technikum-wien.at \n" +
                "Herr Liu: vu17b026@technikum-wien.at \n" +
                "Herr Wagner: vu17b017@technikum-wien.at\n" +
                "\n" +
                "Adresse\n" +
                "Fachhochschule Technikum Wien \n" +
                "Höchstädtplatz 6 \n" +
                "1200 Wien \n" +
                "\n" +
                "© WPL Technologie.");
    }
}
