package com.example.semesterprojekt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


//datenbank, daten werden hier gespeichert
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER = "create table User(" +
            "username varchar(20), "+
            "password varchar(20))";

    private static final String CREATE_USER_TRACK = "create table Track(" +
            "username varchar(20)," +
            "lat text, " +
            "lon text, " +
            "time text)";

    private Context mContext;

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_USER_TRACK);
        initial_data(db);
        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
    }

    private void initial_data(SQLiteDatabase db){
        db.execSQL("insert into User(username, password) VALUES ('cheng', '123');");
        db.execSQL("insert into User(username, password) VALUES ('sammi', '1');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

}
