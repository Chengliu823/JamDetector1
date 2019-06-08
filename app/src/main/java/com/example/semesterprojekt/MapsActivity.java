package com.example.semesterprojekt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semesterprojekt.SQLiteHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LatLng last_point;
    private TextView user_status, user_location, user_acc;
    private String username;
    private SensorManager sensorManager;
    private DecimalFormat decimalFormat;
    private Button btnCalendar;
    private Button btnSend;
    private Button btnTraffic;

    private static SyncHttpClient client = new SyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnTraffic = findViewById(R.id.btn_traffic); //verbindung zum layout

        btnTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawtrafficdata();
            }
        });

        btnSend = findViewById(R.id.bt_send); //verbindung zum layout

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savetrack();
            }
        });

        btnCalendar = findViewById(R.id.btn_calendar);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入清理界面
                Intent intent = new Intent(MapsActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            //Logout
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,LoginActivity.class);
                intent.removeExtra("username");
                username = null;
                finish();
                startActivity(intent);
            }
        });

        //braucht man für zugriff auf location daten
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);


        // 通过GPS获取定位的位置数据 ich glaube es ist unnütig weil die variable nigens verwendet wird
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度 //für Präzision (hoche genauigkeit)
        criteria.setAltitudeRequired(false);// 设置不需要获取海拔方向数据 //höhe
        criteria.setBearingRequired(false);// 设置不需要获取方位数据/ /richtung
        criteria.setCostAllowed(true);// 设置允许产生资费 //maut/kosten
        criteria.setSpeedRequired(true);//设置是否需要速度 //geschwindigkeit erkennung
        criteria.setPowerRequirement(Criteria.POWER_HIGH);// 低功耗 //energie verbrauch

        Intent intent = getIntent(); //überspringe zu anderem Fenster
        //获取用户信息
        username = intent.getStringExtra("username"); //username mit dem man sich einloggt, arbeitet mit der PutExtra Methode in login zusammmen

        user_status = findViewById(R.id.user_status); //status von user
        user_location = findViewById(R.id.user_location); //position der users

        user_acc = findViewById(R.id.user_cceleration);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        //überpfüfung
        findViewById(R.id.bt_track_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MapsActivity.this, TrackInfoActivity.class); //wenn geklickt: zum trafic info fenster
                intent1.putExtra("username", username); //username in datenbank gespeichert
                startActivity(intent1);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //获取SupportMapFragment并在准备好使用地图的时候获取通知 //UI Element für Karteanzeigen in bereich
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //beschleunigungssensor
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accSensor!=null){
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                        double x = sensorEvent.values[0];
                        double y = sensorEvent.values[1];
                        double z = sensorEvent.values[2];
                        user_acc.setText(decimalFormat.format(Math.sqrt(x*x+y*y+z*z)));
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Do other setup activities here too, as described elsewhere in this tutorial.
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18f));
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        drawtrafficdata();
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {

        //zur sichert, damit keine Null Pointer exception
        if (mMap == null) {
            return;
        }

        //aktuelle position wird variblen zugewiesen
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location==null){
            return;
        }

        last_point = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(last_point));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(last_point));
        user_location.setText("Longitude:"+location.getLongitude()+", latitude:"+location.getLatitude());

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(last_point));
                user_location.setText("Longitude:"+location.getLongitude()+", latitude:"+location.getLatitude());
                mMap.addPolyline(new PolylineOptions().clickable(false).color(Color.RED).add(last_point, new LatLng(location.getLatitude(), location.getLongitude())));
                last_point = new LatLng(location.getLatitude(), location.getLongitude());
                float speed =location.getSpeed() * 3.6f ;//取得速度
                decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                if (speed == 0){
                    user_status.setText("REST");
                }else{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    user_status.setText((speed)+"km/m");
                    if (username != null) {
                        new SQLiteHelper(MapsActivity.this).add_track(username, location.getLatitude(),location.getLongitude(),format.format(date), speed);
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }

    private void drawtrafficdata(){

        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() { //neue instanz der kalsse ertsellt, behandelt ergebnis

            @Override //methode überschrien
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override //wenn der server retrun wert zurück schickt wird diese methode aufgerufen
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray c = response.getJSONArray("trafficdata");
                    for (int i = 0 ; i < c.length(); i++) {
                        JSONArray array = c.getJSONArray(i);
                        for (int k = 0 ; k < array.length() - 1; k++) {
                            JSONObject obj1 = array.getJSONObject(k);
                            double lat1 = obj1.getDouble("lat");
                            double lon1 = obj1.getDouble("lon");
                            double speed1 = obj1.getDouble("speed");

                            JSONObject obj2 = array.getJSONObject(k+1);
                            double lat2 = obj2.getDouble("lat");
                            double lon2 = obj2.getDouble("lon");
                            double speed2 = obj2.getDouble("speed");

                            LatLng point1 = new LatLng(lat1, lon1);
                            LatLng point2 = new LatLng(lat2, lon2);
                            if(speed2 <= 50) {
                                mMap.addPolyline(new PolylineOptions().clickable(false).color(Color.RED).add(point1, point2));
                            }else{
                                mMap.addPolyline(new PolylineOptions().clickable(false).color(Color.GREEN).add(point1, point2));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestParams rp = new RequestParams();

        client.post("https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/trafficdata.php", rp, handler); //json wird geschickt,
    }

    private void savetrack() {

        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() { //neue instanz der kalsse ertsellt, behandelt ergebnis

            @Override //methode überschrien
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MapsActivity.this, "Could not call login service", Toast.LENGTH_SHORT).show();
                btnSend.setEnabled(true);
                throwable.printStackTrace();
            }

            @Override //wenn der server retrun wert zurück schickt wird diese methode aufgerufen
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) { //response vom service
                try {
                    boolean registerok = response.getBoolean("result"); //wenn {result:true} dann true sonst false
                    if (registerok) {
                        Toast.makeText(MapsActivity.this, "success!", Toast.LENGTH_SHORT).show(); //meldung
                        new SQLiteHelper(MapsActivity.this).add_send_true();
                        btnSend.setEnabled(true);
                    } else {
                        Toast.makeText(MapsActivity.this, "sending failed", Toast.LENGTH_SHORT).show();
                        btnSend.setEnabled(true);
                    }
                } catch (JSONException e) {
                    btnSend.setEnabled(true);
                    e.printStackTrace();
                }
            }
        };

        SQLiteHelper s = new SQLiteHelper(getApplicationContext());
        JSONObject tracks = s.send();

        RequestParams rp = new RequestParams();
        rp.add("json", tracks.toString());

        client.post("https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/savetrack.php", rp, handler); //json wird geschickt,
        // return wert wird von handler verarbeitet
    }
}