package com.example.managementresiko;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.managementresiko.app.AppConfig;
import com.example.managementresiko.helper.SQLiteHandler;
import com.example.managementresiko.helper.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class dialog_add extends AppCompatDialogFragment {
    private EditText txt_nama;
    private EditText txt_alamat;
    //private ExampleDialogListener listener;

    private SQLiteHandler db;
    private SessionManager session;
    String no_sur;

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

    String Latitude;
    String Longtitude;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);

        db = new SQLiteHandler(getActivity());
        HashMap<String, String> user = db.getUserDetails();

        String nama = user.get("nama");
        no_sur = user.get("username");

        init();

        txt_nama = view.findViewById(R.id.txt_nama);
        txt_alamat = view.findViewById(R.id.txt_alamat);
        builder.setView(view)
                .setTitle("Tambah Responden")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nama = txt_nama.getText().toString();
                        String alamat = txt_alamat.getText().toString();
                        //listener.applyTexts(username, password);
                        createData(nama, alamat);

                    }
                });


        return builder.create();
    }

    private void init() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mSettingsClient = LocationServices.getSettingsClient(getActivity());

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
            Latitude = String.valueOf(mLocation.getLatitude());
            Longtitude = String.valueOf(mLocation.getLongitude());

            Log.d("test long:",Longtitude);

            /*tv_locationresult.setText("Latitude: "+mLocation.getLatitude()+","+ "Longtitude: "+ mLocation.getLongitude());
            tv_updateon.setText("Last Update on: "+mLastUpdateTime);*/
        }
        //toggleButtons();

    }

    private void createData(String nama,String alamat) {

        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        db = new SQLiteHandler(getActivity());
        HashMap<String, String> user = db.getUserDetails();

        Log.d("Nama Perushaan",nama);


        no_sur = user.get("username");
        String latitude = "1.23243242";
        String longtitude = "132423";

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_surveyor", no_sur);
        map.put("latitude", latitude);
        map.put("longtitude", longtitude);
        map.put("nama_perusahaan",nama);
        map.put("alamat",alamat);
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
//                        String jn_tabel = jObj.getString("tabel");
                        String kd_reesponden = data.toString();
//                        String jenis_tabel = jn_tabel.toString();
                       /* if (tableku.equals("risk")){
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
                        }*/

                    }

                    //Toast.makeText(getActivity(), "Data Tersimpan!", Toast.LENGTH_LONG).show();
                    /*Intent intent =  new Intent(getActivity(),ListKuesioner.class);
                    startActivity(intent);*/
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    //Toast.makeText(activity, "Tidak Tersimpan!", Toast.LENGTH_LONG).show();
                    /*Intent intent =  new Intent(getActivity(),ListKuesioner.class);
                    startActivity(intent);*/
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // TODO Auto-generated method stub
                //     pDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
