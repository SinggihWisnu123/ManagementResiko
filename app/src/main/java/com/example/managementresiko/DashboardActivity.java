package com.example.managementresiko;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.managementresiko.helper.SQLiteHandler;
import com.example.managementresiko.helper.SessionManager;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout ln_logout,ln_kuesioner;
    private SQLiteHandler db;
    private SessionManager session;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("PROSEDUR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
