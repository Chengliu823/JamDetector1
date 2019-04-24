package com.example.semesterprojekt;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class SQLiteHelper {

    private MySQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    public SQLiteHelper(Context context){
        // 连接数据库
        dbHelper = new MySQLiteOpenHelper(context, "diary.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public boolean login(String username, String password){
        Cursor cursor = db.rawQuery("SELECT password FROM User WHERE username = '"+username+"'", null);
        if (cursor.moveToFirst() && password.equals(cursor.getString(0))){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public List<HashMap<String, String>> get_track(String username){
        List<HashMap<String, String>> track = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT lat, lon, time FROM Track WHERE username = '"+username+"'", null);
        if (cursor.moveToFirst()){
            do{
                HashMap <String, String> map = new HashMap<>();
                map.put("location", cursor.getString(0)+", "+cursor.getString(1));
                map.put("time", cursor.getString(2));
                track.add(map);
            }while(cursor.moveToNext());

        }
        cursor.close();
        return track;
    }

    public void add_track(String username, double lat, double lon, String time){
        db.execSQL("INSERT INTO Track(username, lat, lon, time) VALUES ('"+username+"', "+lat+", "+lon+", '"+time+"')");
    }

}
