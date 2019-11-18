package com.example.managementresiko.prosedur;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.managementresiko.R;
import com.example.managementresiko.app.AppConfig;
import com.example.managementresiko.app.AppController;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PenangananEskalasi extends Fragment {

    public PenangananEskalasi() {
        // Required empty public constructor
    }
    String kd_responden,jenis_tabel,kesesuaian_c41,kesesuaian_c42,kesesuaian_c43,kelengkapan_c41,kelengkapan_c42,kelengkapan_c43,deskripsi_c41,deskripsi_c42,deskripsi_c43;
    Spinner spn_kesesuaian_c41,spn_kesesuaian_c42,spn_kesesuaian_c43,spn_kelengkapan_c41,spn_kelengkapan_c42,spn_kelengkapan_c43;
    EditText txt_kondisi_c41,txt_kondisi_c42,txt_kondisi_c43;
    Button btn_simpan;

    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_penanganan_eskalasi, container, false);
        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_c41 = root.findViewById(R.id.c41_kelengkapan);
        spn_kelengkapan_c42 = root.findViewById(R.id.c42_kelengkapan);
        spn_kelengkapan_c43 = root.findViewById(R.id.c43_kelengkapan);

        spn_kesesuaian_c41 = root.findViewById(R.id.c41_kesesuaian);
        spn_kesesuaian_c42 = root.findViewById(R.id.c42_kesesuaian);
        spn_kesesuaian_c43 = root.findViewById(R.id.c43_kesesuaian);

        txt_kondisi_c41 = root.findViewById(R.id.c41_deskripsi);
        txt_kondisi_c42 = root.findViewById(R.id.c42_deskripsi);
        txt_kondisi_c43 = root.findViewById(R.id.c43_deskripsi);

        btn_simpan = root.findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createData();
            }
        });

        return root;
    }

    private void createData() {
        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        String[] selectionValKelenglapan = {"a","b","c","d","e"};
        String[] selectionVal = {"1","2","3","4","5"};
        kesesuaian_c41 = selectionVal[(int) spn_kesesuaian_c41.getSelectedItemId()];
        kesesuaian_c42 = selectionVal[(int) spn_kesesuaian_c42.getSelectedItemId()];
        kesesuaian_c43 = selectionVal[(int) spn_kesesuaian_c43.getSelectedItemId()];

        kelengkapan_c41 = selectionValKelenglapan[(int) spn_kelengkapan_c41.getSelectedItemId()];
        kelengkapan_c42 = selectionValKelenglapan[(int) spn_kelengkapan_c42.getSelectedItemId()];
        kelengkapan_c43 = selectionValKelenglapan[(int) spn_kelengkapan_c43.getSelectedItemId()];

        deskripsi_c41 = txt_kondisi_c41.getText().toString();
        deskripsi_c42 = txt_kondisi_c42.getText().toString();
        deskripsi_c43 = txt_kondisi_c43.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("c41_kesesuaian",kesesuaian_c41);
        map.put("c42_kesesuaian",kesesuaian_c42);
        map.put("c43_kesesuaian",kesesuaian_c43);

        map.put("c41_kelengkapan",kelengkapan_c41);
        map.put("c42_kelengkapan",kelengkapan_c42);
        map.put("c43_kelengkapan",kelengkapan_c43);

        map.put("c41_deskripsi",deskripsi_c41);
        map.put("c42_deskripsi",deskripsi_c42);
        map.put("c43_deskripsi",deskripsi_c43);

        wordList.add(map);
        params.put("tambahResponden",wordList);

        System.out.println(params);
        Log.d("Data : ", String.valueOf(params));
        client.post(AppConfig.URL_UPDATE, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                //dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean status = jObj.getBoolean("status");

                    Log.e("Status ", String.valueOf(status));

                    // Check for error node in json
                   /* if (status==true) {
                        String data = jObj.getString("data");
                        String kd_reesponden = data.toString();
                        Intent intent = new Intent(getActivity(),FormB.class);
                        intent.putExtra("no_responden",kd_reesponden);
                        startActivity(intent);

                    }*/

                    Toast.makeText(getContext(), "Data Tersimpan!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getContext(), "Data Tersimpan!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    /*startActivity(new Intent(getContext(),MainActivity.class));*/
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // TODO Auto-generated method stub
                //     pDialog.hide();
                if(statusCode == 404){
                    Toast.makeText(getContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }else if(statusCode == 500){
                    Toast.makeText(getContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void fetchData() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_DATA_PROSEDUR+kd_responden,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        dialog.hide();


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status= jsonObject.getBoolean("status");
                            if (status==true) {
                                // successfully received product details
                                JSONArray productObj = jsonObject.getJSONArray("data"); // JSON Array

                                // get first product object from JSON Array
                                for (int i = 0; i < productObj.length(); i++) {
                                    JSONObject c = productObj.getJSONObject(i);

                                    if (c.getString("c41_kelengkapan").equals("a")){
                                        spn_kelengkapan_c41.setSelection(0);
                                    }else if (c.getString("c41_kelengkapan").equals("b")){
                                        spn_kelengkapan_c41.setSelection(1);
                                    }else if (c.getString("c41_kelengkapan").equals("c")){
                                        spn_kelengkapan_c41.setSelection(2);
                                    }else if (c.getString("c41_kelengkapan").equals("d")){
                                        spn_kelengkapan_c41.setSelection(3);
                                    }else if (c.getString("c41_kelengkapan").equals("e")){
                                        spn_kelengkapan_c41.setSelection(4);
                                    }

                                    if (c.getString("c41_kesesuaian").equals("1")){
                                        spn_kesesuaian_c41.setSelection(0);
                                    }else if (c.getString("c41_kesesuaian").equals("2")){
                                        spn_kesesuaian_c41.setSelection(1);
                                    }else if (c.getString("c41_kesesuaian").equals("3")){
                                        spn_kesesuaian_c41.setSelection(2);
                                    }else if (c.getString("c41_kesesuaian").equals("4")){
                                        spn_kesesuaian_c41.setSelection(3);
                                    }else if (c.getString("c41_kesesuaian").equals("5")){
                                        spn_kesesuaian_c41.setSelection(4);
                                    }

                                    if (c.getString("c42_kelengkapan").equals("a")){
                                        spn_kelengkapan_c42.setSelection(0);
                                    }else if (c.getString("c42_kelengkapan").equals("b")){
                                        spn_kelengkapan_c42.setSelection(1);
                                    }else if (c.getString("c42_kelengkapan").equals("c")){
                                        spn_kelengkapan_c42.setSelection(2);
                                    }else if (c.getString("c42_kelengkapan").equals("d")){
                                        spn_kelengkapan_c42.setSelection(3);
                                    }else if (c.getString("c42_kelengkapan").equals("e")){
                                        spn_kelengkapan_c42.setSelection(4);
                                    }

                                    if (c.getString("c42_kesesuaian").equals("1")){
                                        spn_kesesuaian_c42.setSelection(0);
                                    }else if (c.getString("c42_kesesuaian").equals("2")){
                                        spn_kesesuaian_c42.setSelection(1);
                                    }else if (c.getString("c42_kesesuaian").equals("3")){
                                        spn_kesesuaian_c42.setSelection(2);
                                    }else if (c.getString("c42_kesesuaian").equals("4")){
                                        spn_kesesuaian_c42.setSelection(3);
                                    }else if (c.getString("c42_kesesuaian").equals("5")){
                                        spn_kesesuaian_c42.setSelection(4);
                                    }

                                    if (c.getString("c43_kelengkapan").equals("a")){
                                        spn_kelengkapan_c43.setSelection(0);
                                    }else if (c.getString("c43_kelengkapan").equals("b")){
                                        spn_kelengkapan_c43.setSelection(1);
                                    }else if (c.getString("c43_kelengkapan").equals("c")){
                                        spn_kelengkapan_c43.setSelection(2);
                                    }else if (c.getString("c43_kelengkapan").equals("d")){
                                        spn_kelengkapan_c43.setSelection(3);
                                    }else if (c.getString("c43_kelengkapan").equals("e")){
                                        spn_kelengkapan_c43.setSelection(4);
                                    }

                                    if (c.getString("c43_kesesuaian").equals("1")){
                                        spn_kesesuaian_c43.setSelection(0);
                                    }else if (c.getString("c43_kesesuaian").equals("2")){
                                        spn_kesesuaian_c43.setSelection(1);
                                    }else if (c.getString("c43_kesesuaian").equals("3")){
                                        spn_kesesuaian_c43.setSelection(2);
                                    }else if (c.getString("c43_kesesuaian").equals("4")){
                                        spn_kesesuaian_c43.setSelection(3);
                                    }else if (c.getString("c43_kesesuaian").equals("5")){
                                        spn_kesesuaian_c43.setSelection(4);
                                    }


                                    txt_kondisi_c41.setText(c.getString("c41_deskripsi"));
                                    txt_kondisi_c42.setText(c.getString("c42_deskripsi"));
                                    txt_kondisi_c43.setText(c.getString("c43_deskripsi"));

                                }

                                // product with this pid found
                                // Edit Text

                                // display product data in EditText


                            }else{
                                // product with pid not found
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getContext(), "Cek internet Anda!", Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    }
                }

        );

        AppController.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
