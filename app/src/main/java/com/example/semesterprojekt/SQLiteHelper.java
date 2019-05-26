package com.example.semesterprojekt;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//wie die datenbank funktioniert
public class SQLiteHelper {

    private MySQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;



    public SQLiteHelper(Context context){
        // 连接数据库
        dbHelper = new MySQLiteOpenHelper(context, "diary.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    //lingin über lokalen server, verwenden wir nicht mehr
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

    //sendet daten an server
    public JSONObject send(){
        Cursor cursor = db.rawQuery("SELECT username, lat, lon, time FROM Track", null); //daten werden aus dantenank ausgelesen
        try {
            JSONObject tracks = new JSONObject(); //daten werden in json format gebracht
            JSONArray tlist = new JSONArray();
            while (cursor.moveToNext()) {
                JSONObject track = new JSONObject();
                track.put("username", cursor.getString(0));
                track.put("lat", cursor.getString(1));
                track.put("lon", cursor.getString(2));
                track.put("time", cursor.getString(3));
                tlist.put(track);
            }
            tracks.put("track", tlist);

            return tracks;

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null; //auchtung null pointer exception
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
    public void initial_data(String username, String password){
        db.execSQL("INSERT INTO User(username,password) VALUES('"+username+"',"+password+")");
    }

}