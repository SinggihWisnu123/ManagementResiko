package com.example.managementresiko;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.managementresiko.app.AppConfig;
import com.example.managementresiko.helper.SQLiteHandler;
import com.example.managementresiko.helper.SessionManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout ln_logout,ln_kuesioner;
    private SQLiteHandler db;
    private SessionManager session;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    String risk = "risk" ;
    String prosedur = "prosedur";

    String no_sur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /*HashMap<String, String> user = db.getUserDetails();

        String nama = user.get("nama");
        no_sur = user.get("username");*/

        ln_kuesioner = findViewById(R.id.ln_kuesioner);
        ln_kuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
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
                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                intent.putExtra("table",risk);
                startActivity(intent);
                //createData();

                dialog.dismiss();


            }
        });

        dialog.setNegativeButton("PROSEDUR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DashboardActivity.this, ProsedurActivity.class);
                intent.putExtra("table",prosedur);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void createData() {

        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("no_surveyor", no_sur);
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
                        String kd_reesponden = data.toString();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("no_responden", kd_reesponden);
                        startActivity(intent);
                        finish();
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
