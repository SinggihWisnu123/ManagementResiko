package com.example.managementresiko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.managementresiko.adapter.ListAdapter;
import com.example.managementresiko.app.AppConfig;
import com.example.managementresiko.app.AppController;
import com.example.managementresiko.helper.DataKuesioner;
import com.example.managementresiko.helper.MyApplication;
import com.example.managementresiko.helper.MyDividerItemDecoration;
import com.example.managementresiko.helper.SQLiteHandler;
import com.example.managementresiko.helper.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListKuesioner extends AppCompatActivity implements ListAdapter.ContactsAdapterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<DataKuesioner> contactList;
    private ListAdapter mAdapter;
    private SearchView searchView;
    FloatingActionButton btn_add;

    // url to fetch contacts json
    private static final String URL = "https://api.androidhive.info/json/contacts.json";

    private SQLiteHandler db;
    private SessionManager session;
    String no_sur;

    androidx.appcompat.app.AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kuesioner);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        btn_add = findViewById(R.id.fab_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        String nama = user.get("nama");
        no_sur = user.get("username");

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ListAdapter(this, contactList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        fetchContacts();
    }

    /**
     * fetches json by making http calls
     */
    private void fetchContacts() {
        JsonArrayRequest request = new JsonArrayRequest(AppConfig.URL_DATA+no_sur,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<DataKuesioner> items = new Gson().fromJson(response.toString(), new TypeToken<List<DataKuesioner>>() {
                        }.getType());

                        // adding contacts to contacts list
                        contactList.clear();
                        contactList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @SuppressLint("ResourceAsColor")
    private void whiteNotificationBar(@NonNull View view ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(final DataKuesioner contact) {
        //Toast.makeText(getApplicationContext(), "Selected: " + contact.getNo_responden() + ", " + contact.getNama(), Toast.LENGTH_LONG).show();
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    DialogForm(contact.getNo_responden());
                    // onContactSelected(position);
                    /*Intent in = new Intent(getApplicationContext(),
                            FormKuisionerActivity.class);*/

                    // sending pid to next activity
                    /*in.putExtra("no_responden", contact.getNo_responden());
                    startActivityForResult(in, 100);*/
                } else {
                    Delete(contact.getNo_responden());
                    onRefresh();
                }
            }
        });
        builder.show();
    }

    public void openDialog() {
        dialog_add exampleDialog = new dialog_add();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    //@Override
    public void onRefresh() {
        fetchContacts();
    }
    private void Delete(String kd_responden) {

       /* final ProgressDialog dialog = ProgressDialog.show(getApplicationContext(), "Loading...", "Please wait...", true);
        dialog.show();*/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_DELETE+kd_responden,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        //dialog.hide();


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status= jsonObject.getBoolean("status");
                            Toast.makeText(ListKuesioner.this,"Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                            onRefresh();
                            /*if (status==true) {
                                // successfully received product details
                                //String productObj = jsonObject.getString("pesan"); // JSON Array
                                Toast.makeText(ListKuesioner.this,productObj, Toast.LENGTH_SHORT).show();
                                // get first product object from JSON Array

                                // product with this pid found
                                // Edit Text

                                // display product data in EditText
                                onRefresh();


                            }else{
                                // product with pid not found

                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListKuesioner.this,"Ada kesalahan pada Server", Toast.LENGTH_SHORT).show();
                            onRefresh();


                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

        );

        AppController.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void DialogForm(final String kd_responden) {
        dialog = new androidx.appcompat.app.AlertDialog.Builder(ListKuesioner.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Pilih Verifikasi");

        dialog.setNeutralButton("RISIKO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String risk = "tt_survey_risk";
                Intent intent = new Intent(ListKuesioner.this,MainActivity.class);
                intent.putExtra("no_responden",kd_responden);
                intent.putExtra("jenis_tabel",risk);
                startActivity(intent);
                //createData(risk);

                dialog.dismiss();


            }
        });

        dialog.setNegativeButton("PROSEDUR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String prosedur = "tt_survey_prosedur";
                Intent intent = new Intent(ListKuesioner.this, ProsedurActivity.class);
                intent.putExtra("no_responden",kd_responden);
                intent.putExtra("jenis_tabel",prosedur);
                startActivity(intent);
                //createData(prosedur);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}