package com.example.semesterprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calender = (CalendarView) findViewById(R.id.calendarView1);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                Intent intent = new Intent(CalendarActivity.this, MapsActivity.class);
                intent.putExtra("selectedyear", year);
                intent.putExtra("selectedmonth", month);
                intent.putExtra("selectedday", dayOfMonth);
                startActivity(intent);

            }
        });
    }
}
