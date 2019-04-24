package com.example.semesterprojekt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LatLng last_point;
    private TextView user_status, user_location;
    private String username;

    @Override //hallo noah
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        // 通过GPS获取定位的位置数据
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
        criteria.setAltitudeRequired(false);// 设置不需要获取海拔方向数据
        criteria.setBearingRequired(false);// 设置不需要获取方位数据
        criteria.setCostAllowed(true);// 设置允许产生资费
        criteria.setSpeedRequired(true);//设置是否需要速度
        criteria.setPowerRequirement(Criteria.POWER_HIGH);// 低功耗

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        user_status = findViewById(R.id.user_status);
        user_location = findViewById(R.id.user_location);

        findViewById(R.id.bt_track_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MapsActivity.this, TrackInfoActivity.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {


        if (mMap == null) {
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        last_point = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(last_point));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(last_point));
        user_location.setText("Longitude:"+location.getLongitude()+", latitude:"+location.getLatitude());

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                user_location.setText("Longitude:"+location.getLongitude()+", latitude:"+location.getLatitude());
                mMap.addPolyline(new PolylineOptions().clickable(false).color(Color.RED).add(last_point, new LatLng(location.getLatitude(), location.getLongitude())));
                last_point = new LatLng(location.getLatitude(), location.getLongitude());
                float  speed=location.getSpeed();//取得速度
                DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p=decimalFormat.format(speed*3.6);//format 返回的是字符串
                speed = Float.valueOf(p);
                if (speed == 0){
                    user_status.setText("REST");
                }else{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    user_status.setText((speed/3.6)+"m/s");
                    new SQLiteHelper(MapsActivity.this).add_track(username, location.getLatitude(), location.getLongitude(), format.format(date));
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

}
