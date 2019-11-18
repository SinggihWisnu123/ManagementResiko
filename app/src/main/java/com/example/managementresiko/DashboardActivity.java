package com.example.managementresiko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.managementresiko.app.AppConfig;
import com.example.managementresiko.helper.SQLiteHandler;
import com.example.managementresiko.helper.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout ln_logout,ln_kuesioner;
    private SQLiteHandler db;
    private SessionManager session;
    String no_sur;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    String risk = "risk" ;
    String prosedur = "prosedur";

    private String mLastUpdateTime;
    private static final long UPDATE_INTERVAL_IN_MILISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mLocation;

    private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        String nama = user.get("nama");
        no_sur = user.get("username");

        init();
        periksaIzinLokasi();
        checkPermission();
        mRequestingLocationUpdates = true;
        startLocationUpdates();
        //startLocationUpdates();


        ln_kuesioner = findViewById(R.id.ln_kuesioner);
        ln_kuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,ListKuesioner.class);
                startActivity(intent);
                //openDialog();
            }
        });

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        ln_logout = findViewById(R.id.ln_logout);
        ln_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(DashboardActivity.this, Login_Activity.class);
        startActivity(intent);
        finish();
    }
    private void init() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateLocationUI() {
        if (mLocation!=null){
            Toast.makeText(getApplicationContext(),"Latitude: "+mLocation.getLatitude()+","+ "Longtitude: "+ mLocation.getLongitude(),Toast.LENGTH_SHORT).show();
        }

    }

    boolean periksaIzinLokasi(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return false;
            }
        }else {
            return true;
        }
    }
    private boolean checkPermission(){
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationUpdates(){
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Toast.makeText(getApplicationContext(),"Start Location Updates",Toast.LENGTH_SHORT).show();
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void openDialog() {
        dialog_add exampleDialog = new dialog_add();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    private void DialogForm() {
        dialog = new AlertDialog.Builder(DashboardActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Pilih Verifikasi");

        dialog.setNeutralButton("RISIKO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                intent.putExtra("table",risk);
                startActivity(intent);*/
                createData(risk);

                dialog.dismiss();


            }
        });

        dialog.setNegativeButton("PROSEDUR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Intent intent = new Intent(DashboardActivity.this, ProsedurActivity.class);
                intent.putExtra("table",prosedur);
                startActivity(intent);*/
                createData(prosedur);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void createData(final String tableku) {

        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();


        no_sur = user.get("username");
        String latitude = "1.23243242";
        String longtitude = "132423";

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tabel",tableku);
        map.put("no_surveyor", no_sur);
        map.put("latitude",latitude);
        map.put("longtitude",longtitude);
        wordList.add(map);

        Log.d("Datanya", data.toString());
        //showDialog();
        params.put("tambahResponden", wordList);

        System.out.println(params);
        Log.d("Data : ", String.valueOf(params));
        client.post(AppConfig.URL_TAMBAH, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                //dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean status = jObj.getBoolean("status");

                    Log.e("Status ", String.valueOf(status));

                    // Check for error node in json
                    if (status == true) {
                        String data = jObj.getString("data");
                        String jn_tabel = jObj.getString("tabel");
                        String kd_reesponden = data.toString();
                        String jenis_tabel = jn_tabel.toString();
                        if (tableku.equals("risk")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("no_responden", kd_reesponden);
                            intent.putExtra("jenis_tabel", jenis_tabel);
                            startActivity(intent);
                            //finish();
                        }else if (tableku.equals("prosedur")){
                            Intent intent = new Intent(getApplicationContext(), ProsedurActivity.class);
                            intent.putExtra("no_responden", kd_reesponden);
                            intent.putExtra("jenis_tabel", jenis_tabel);
                            startActivity(intent);
                            //finish();
                        }

                    }

                    Toast.makeText(getApplicationContext(), "Data Tersimpan!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Tidak Tersimpan!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // TODO Auto-generated method stub
                //     pDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
