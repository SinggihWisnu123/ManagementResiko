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


public class PoskomandoLapangan extends Fragment {

    public PoskomandoLapangan() {
        // Required empty public constructor
    }
    String kd_responden,jenis_tabel,kesesuaian_c31,kesesuaian_c32,kesesuaian_c33,kesesuaian_c34,kelengkapan_c31,kelengkapan_c32,kelengkapan_c33,kelengkapan_c34,deskripsi_c31,deskripsi_c32,deskripsi_c33,deskripsi_c34;
    Spinner spn_kesesuaian_c31,spn_kesesuaian_c32,spn_kesesuaian_c33,spn_kesesuaian_c34,spn_kelengkapan_c31,spn_kelengkapan_c32,spn_kelengkapan_c33,spn_kelengkapan_c34;
    EditText txt_kondisi_c31,txt_kondisi_c32,txt_kondisi_c33,txt_kondisi_c34;
    Button btn_simpan;

    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_poskomando_lapangan, container, false);
        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_c31 = root.findViewById(R.id.c31_kelengkapan);
        spn_kelengkapan_c32 = root.findViewById(R.id.c32_kelengkapan);
        spn_kelengkapan_c33 = root.findViewById(R.id.c33_kelengkapan);
        spn_kelengkapan_c34 = root.findViewById(R.id.c34_kelengkapan);

        spn_kesesuaian_c31 = root.findViewById(R.id.c31_kesesuaian);
        spn_kesesuaian_c32 = root.findViewById(R.id.c32_kesesuaian);
        spn_kesesuaian_c33 = root.findViewById(R.id.c33_kesesuaian);
        spn_kesesuaian_c34 = root.findViewById(R.id.c34_kesesuaian);

        txt_kondisi_c31 = root.findViewById(R.id.c31_deskripsi);
        txt_kondisi_c32 = root.findViewById(R.id.c32_deskripsi);
        txt_kondisi_c33 = root.findViewById(R.id.c33_deskripsi);
        txt_kondisi_c34 = root.findViewById(R.id.c34_deskripsi);

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
        kesesuaian_c31 = selectionVal[(int) spn_kesesuaian_c31.getSelectedItemId()];
        kesesuaian_c32 = selectionVal[(int) spn_kesesuaian_c32.getSelectedItemId()];
        kesesuaian_c33 = selectionVal[(int) spn_kesesuaian_c33.getSelectedItemId()];
        kesesuaian_c34 = selectionVal[(int) spn_kesesuaian_c34.getSelectedItemId()];

        kelengkapan_c31 = selectionValKelenglapan[(int) spn_kelengkapan_c31.getSelectedItemId()];
        kelengkapan_c32 = selectionValKelenglapan[(int) spn_kelengkapan_c32.getSelectedItemId()];
        kelengkapan_c33 = selectionValKelenglapan[(int) spn_kelengkapan_c33.getSelectedItemId()];
        kelengkapan_c34 = selectionValKelenglapan[(int) spn_kelengkapan_c34.getSelectedItemId()];

        deskripsi_c31 = txt_kondisi_c31.getText().toString();
        deskripsi_c32 = txt_kondisi_c32.getText().toString();
        deskripsi_c33 = txt_kondisi_c33.getText().toString();
        deskripsi_c34 = txt_kondisi_c34.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("c31_kesesuaian",kesesuaian_c31);
        map.put("c32_kesesuaian",kesesuaian_c32);
        map.put("c33_kesesuaian",kesesuaian_c33);
        map.put("c34_kesesuaian",kesesuaian_c34);

        map.put("c31_kelengkapan",kelengkapan_c31);
        map.put("c32_kelengkapan",kelengkapan_c32);
        map.put("c33_kelengkapan",kelengkapan_c33);
        map.put("c34_kelengkapan",kelengkapan_c34);

        map.put("c31_deskripsi",deskripsi_c31);
        map.put("c32_deskripsi",deskripsi_c32);
        map.put("c33_deskripsi",deskripsi_c33);
        map.put("c34_deskripsi",deskripsi_c34);

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

                                    if (c.getString("c31_kelengkapan").equals("a")){
                                        spn_kelengkapan_c31.setSelection(0);
                                    }else if (c.getString("c31_kelengkapan").equals("b")){
                                        spn_kelengkapan_c31.setSelection(1);
                                    }else if (c.getString("c31_kelengkapan").equals("c")){
                                        spn_kelengkapan_c31.setSelection(2);
                                    }else if (c.getString("c31_kelengkapan").equals("d")){
                                        spn_kelengkapan_c31.setSelection(3);
                                    }else if (c.getString("c31_kelengkapan").equals("e")){
                                        spn_kelengkapan_c31.setSelection(4);
                                    }

                                    if (c.getString("c31_kesesuaian").equals("1")){
                                        spn_kesesuaian_c31.setSelection(0);
                                    }else if (c.getString("c31_kesesuaian").equals("2")){
                                        spn_kesesuaian_c31.setSelection(1);
                                    }else if (c.getString("c31_kesesuaian").equals("3")){
                                        spn_kesesuaian_c31.setSelection(2);
                                    }else if (c.getString("c31_kesesuaian").equals("4")){
                                        spn_kesesuaian_c31.setSelection(3);
                                    }else if (c.getString("c31_kesesuaian").equals("5")){
                                        spn_kesesuaian_c31.setSelection(4);
                                    }

                                    if (c.getString("c32_kelengkapan").equals("a")){
                                        spn_kelengkapan_c32.setSelection(0);
                                    }else if (c.getString("c32_kelengkapan").equals("b")){
                                        spn_kelengkapan_c32.setSelection(1);
                                    }else if (c.getString("c32_kelengkapan").equals("c")){
                                        spn_kelengkapan_c32.setSelection(2);
                                    }else if (c.getString("c32_kelengkapan").equals("d")){
                                        spn_kelengkapan_c32.setSelection(3);
                                    }else if (c.getString("c32_kelengkapan").equals("e")){
                                        spn_kelengkapan_c32.setSelection(4);
                                    }

                                    if (c.getString("c32_kesesuaian").equals("1")){
                                        spn_kesesuaian_c32.setSelection(0);
                                    }else if (c.getString("c32_kesesuaian").equals("2")){
                                        spn_kesesuaian_c32.setSelection(1);
                                    }else if (c.getString("c32_kesesuaian").equals("3")){
                                        spn_kesesuaian_c32.setSelection(2);
                                    }else if (c.getString("c32_kesesuaian").equals("4")){
                                        spn_kesesuaian_c32.setSelection(3);
                                    }else if (c.getString("c32_kesesuaian").equals("5")){
                                        spn_kesesuaian_c32.setSelection(4);
                                    }

                                    if (c.getString("c33_kelengkapan").equals("a")){
                                        spn_kelengkapan_c33.setSelection(0);
                                    }else if (c.getString("c33_kelengkapan").equals("b")){
                                        spn_kelengkapan_c33.setSelection(1);
                                    }else if (c.getString("c33_kelengkapan").equals("c")){
                                        spn_kelengkapan_c33.setSelection(2);
                                    }else if (c.getString("c33_kelengkapan").equals("d")){
                                        spn_kelengkapan_c33.setSelection(3);
                                    }else if (c.getString("c33_kelengkapan").equals("e")){
                                        spn_kelengkapan_c33.setSelection(4);
                                    }

                                    if (c.getString("c33_kesesuaian").equals("1")){
                                        spn_kesesuaian_c33.setSelection(0);
                                    }else if (c.getString("c33_kesesuaian").equals("2")){
                                        spn_kesesuaian_c33.setSelection(1);
                                    }else if (c.getString("c33_kesesuaian").equals("3")){
                                        spn_kesesuaian_c33.setSelection(2);
                                    }else if (c.getString("c33_kesesuaian").equals("4")){
                                        spn_kesesuaian_c33.setSelection(3);
                                    }else if (c.getString("c33_kesesuaian").equals("5")){
                                        spn_kesesuaian_c33.setSelection(4);
                                    }

                                    if (c.getString("c34_kelengkapan").equals("a")){
                                        spn_kelengkapan_c34.setSelection(0);
                                    }else if (c.getString("c34_kelengkapan").equals("b")){
                                        spn_kelengkapan_c34.setSelection(1);
                                    }else if (c.getString("c34_kelengkapan").equals("c")){
                                        spn_kelengkapan_c34.setSelection(2);
                                    }else if (c.getString("c34_kelengkapan").equals("d")){
                                        spn_kelengkapan_c34.setSelection(3);
                                    }else if (c.getString("c34_kelengkapan").equals("e")){
                                        spn_kelengkapan_c34.setSelection(4);
                                    }

                                    if (c.getString("c34_kesesuaian").equals("1")){
                                        spn_kesesuaian_c34.setSelection(0);
                                    }else if (c.getString("c34_kesesuaian").equals("2")){
                                        spn_kesesuaian_c34.setSelection(1);
                                    }else if (c.getString("c34_kesesuaian").equals("3")){
                                        spn_kesesuaian_c34.setSelection(2);
                                    }else if (c.getString("c34_kesesuaian").equals("4")){
                                        spn_kesesuaian_c34.setSelection(3);
                                    }else if (c.getString("c34_kesesuaian").equals("5")){
                                        spn_kesesuaian_c34.setSelection(4);
                                    }


                                    txt_kondisi_c31.setText(c.getString("c31_deskripsi"));
                                    txt_kondisi_c32.setText(c.getString("c32_deskripsi"));
                                    txt_kondisi_c33.setText(c.getString("c33_deskripsi"));
                                    txt_kondisi_c34.setText(c.getString("c34_deskripsi"));

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
